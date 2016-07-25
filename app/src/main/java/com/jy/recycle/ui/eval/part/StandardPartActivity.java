package com.jy.recycle.ui.eval.part;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jy.ah.bus.data.Response;
import com.jy.recycle.R;
import com.jy.mobile.dto.FitsDTO;
import com.jy.mobile.response.SpPartListDTO;
import com.jy.recycle.client.request.InsuranceItem;
import com.jy.recycle.client.response.EvalPartInfo;
import com.jy.recycle.common.DefaultDoubleValueListener;
import com.jy.recycle.common.DefaultIntegerValueListener;
import com.jy.recycle.dao.EvalPartDao;
import com.jy.recycle.dao.InsuranceItemDao;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.settings.EvalPartSettings;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.ui.eval.part.EvalPartActivity.InputHandler;
import com.jy.recycle.ui.view.ResizeLayout;
import com.jy.recycle.util.JsonUtil;
import com.jy.recycle.util.MathUtil;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.SimpleTextWatcher;
import com.jy.recycle.util.SpinnerItem;
import com.jy.recycle.util.StaticCode;

public class StandardPartActivity  extends JyBaseActivity {
	private Context context;
	private EvalPartDao evalPartAction;
	private SharedData share;

	private ArrayList<SpinnerItem> priceTypeList;

	private Button partGroupBtn;
	private String ljname;
	private String ljzId;
	private String ljzName;
	private Long evalId;
	private String vehCertainId;
	private String priceCode;
	private String pageInfo;
	private String keyPart;
	private String cxLjmc;
	private String cxYcljh;
	private String pzbwStr;
	private String pzcdStr;
	private String titleFlag;

	private boolean seled = true; // 控件价格方案选项及是否保存

	private static final int MESSAGETYPE_01 = 0x0001;
	private static final int MESSAGETYPE_02 = 0x0002;
	private static final int MESSAGETYPE_03 = 0x0003;
	private static final int MESSAGETYPE_04 = 0x0004;
	private static final int MESSAGETYPE_05 = 0x0005;

	private ListView partListLv;

	private Spinner priceTypeSp;
	private Spinner spnInsuranceTerm;
	private Spinner pzbw;
	private Spinner pzcd;

	private EditText diaPrice;
	private EditText diaNum;
	private EditText diaCustomName;
	private EditText ycljh;
	private EditText ljmc;
	private TextView pageNo; // 隐藏页码,同时查询类型及参数都暂存
	private View keyword;
	private View view;

	private ProgressDialog progressDialog = null;
	private PopupWindow mPopupWindow;
	private Button partNameBtn;
	private Button btnPz;
	private Button btn_back;
	private Button keywordBtn;
	private ImageButton defineBtn;
	private ImageButton batchAddBtn;

	private List<EvalPartInfo> evalPartList;
	private boolean[] chkb; // 记录checkbox选中数据

	private LayoutInflater inflater;
	private List<InsuranceItem> insuranceItemList;
	
	private LinearLayout controlllayout;
	private static final int BIGGER = 1;
	private static final int SMALLER = 2;
	private static final int MSG_RESIZE = 1;
	
	class InputHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_RESIZE: {
				if (msg.arg1 == BIGGER) {
					findViewById(R.id.partcontrollayout).setVisibility(View.VISIBLE);
				} else {
					findViewById(R.id.partcontrollayout).setVisibility(View.GONE);
				}
			}
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	}

	private InputHandler mHandler = new InputHandler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lj_evalpart);
		
		//增大复选框点选后的空间
		 ResizeLayout layout = (ResizeLayout) findViewById(R.id.keyboardLayout1);   
	       layout.setOnResizeListener(new ResizeLayout.OnResizeListener() {   
	                
	             public void OnResize(int w, int h, int oldw, int oldh) {   
	                  int change = BIGGER;   
	                if (h < oldh) {   
	                      change = SMALLER;   
	                }   
	                                     
	                  Message msg = new Message();   
	                  msg.what = 1;   
	                  msg.arg1 = change;   
	                  mHandler.sendMessage(msg);   
	              }   
	          });   

		
		// 读参数
		if (!readArgs()) {
			return;
		}
		insuranceItemList = InsuranceItemDao.getInstance()
				.getListInsuranceItem(evalId);
		inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		initBaseData();
		findView();
	}

	/**
	 * 读取请求参数
	 * 
	 * @return
	 */
	private boolean readArgs() {
		// 定损单信息
		evalId = getIntent().getLongExtra("evalId", -1);
		if (evalId == -1) {
			toast("没有获取到请求参数evalId");
			finish();
			return false;
		}
		// 车型Id
		this.vehCertainId = getIntent().getStringExtra("vehCertainId");
		return true;
	}

	private void bindView() {
		partGroupBtn.setOnClickListener(this);
		keywordBtn.setOnClickListener(this);
		defineBtn.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		batchAddBtn.setOnClickListener(this);
		partNameBtn.setOnClickListener(this);
		btnPz.setOnClickListener(this);
		priceTypeSp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (partListLv != null && partListLv.getCount() > 0) {
					pageOperation();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void findView() {
		if (priceTypeList != null && priceTypeList.size() > 0) {
			view = findViewById(R.id.showList);
			// 价格方案
			priceTypeSp = (Spinner) findViewById(R.id.part_spnJgfa);
			priceTypeSp.setAdapter(StaticCode
					.getAdapter(context, priceTypeList));
			// // 点选模式
			spnInsuranceTerm = (Spinner) findViewById(R.id.eval_insurance_term);
			InsuranceItemAdapter adapter = new InsuranceItemAdapter();
			spnInsuranceTerm.setAdapter(adapter);

			// 使用已保存的价格方案,如果一个零件也没有不能写死
			if (evalPartAction.getListEvalPart(evalId).size() > 0) {
				priceTypeSp.setSelection(StaticCode.getIndex(priceTypeList,
						priceCode));
				seled = false;
			}
			if (seled) {
				priceTypeSp.setSelection(share.getEvalJgfaPosition());
			}

			// 如果是单一价格来源,那么如果已经选择了零件,不可以修改价格来源
			if (EvalPartSettings.SINGLE_PRICE_SOURCE) {
				priceTypeSp.setEnabled(seled);// 如果已有保存的价格方案则不允许再更换
			}

			partGroupBtn = (Button) findViewById(R.id.part_ljfz);
			pageNo = (TextView) findViewById(R.id.pageNo);
			pageNo.setText("1");
			partNameBtn = (Button) findViewById(R.id.part_btn_ljmc);

			// 按碰撞程度查询
			btnPz = (Button) findViewById(R.id.part_btn_pz);
			btn_back = (Button) findViewById(R.id.menu_title_back);
			btn_back.setText(titleFlag);
			// 查询获取零件数据 改为关键字查询，用弹出窗口
			keywordBtn = (Button) findViewById(R.id.part_btn_gjz);
			// 增加自定义零件
			defineBtn = (ImageButton) findViewById(R.id.part_btn_zdy);
			// 批量增加
			batchAddBtn = (ImageButton) findViewById(R.id.part_btn_add);
			bindView();
		} else {
			showDialog("组织机构没有设置价格方案或此车型没有零件！");
			// 返回到首页上去
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("此车无法点选零件");
			dialog.setMessage("组织机构没有设置价格方案或此车型没有零件！");
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			dialog.setCancelable(true);
			dialog.show();
		}
	}

	private void initBaseData() {
		context = this;
		share = SharedData.data();
		// claimEvalAction = ClaimEvalDao.getInstance();
		evalPartAction = EvalPartDao.getInstance();
		// 点选模式
		// selectionModeList = new ArrayList<SpinnerItem>();
		// selectionModeList.add(new SpinnerItem("1", "系统点选"));
		// selectionModeList.add(new SpinnerItem("2", "标准点选"));
		// selectionModeList.add(new SpinnerItem("3", "图形点选"));

		TextView titlename = (TextView) findViewById(R.id.menu_title_name);
		titlename.setText("换件");

		Button freshButton = (Button) findViewById(R.id.menu_title_flash);
		freshButton.setText("标准点选");
		freshButton.setOnClickListener(this);
		priceTypeList = (ArrayList<SpinnerItem>) share.getJgfa();// 价格方案列表

		Intent it = this.getIntent();
		titleFlag = it.getStringExtra("title_flag");

		pageInfo = it.getStringExtra("PageInfo"); // 页码#类型#参数#价格方案的位置
		if (pageInfo != null) { // 翻页操作
			pageNo.setText(pageInfo);
			pageOperation();
		} else {

			String strLj = it.getStringExtra("strLj"); // 零件组名称和ID
			if (strLj != null) {
				String[] ljz = strLj.split(";");
				ljzName = ljz[0];
				ljzId = ljz[1];
			}

			// 如果有零件组ID及价格方案ID，则直接进行查询
			// TODO 这段代码不会被执行
			if (hasJgfaLjId() && ljzId != null) {

				new AsyncTask<Void, Void, Void>() {
					@Override
					protected void onPreExecute() {

						progressDialog = ProgressDialog.show(context, "请稍等",
								"正在查询换件信息,请稍候!");
					}

					@Override
					protected Void doInBackground(Void... params) {
						SpinnerItem jgfa = (SpinnerItem) priceTypeSp
								.getSelectedItem();
						String jgfaId = jgfa.getID();

						Response response = ServerApiManager.getJtljDataB(
								vehCertainId, jgfaId, ljzId, "1","1");
						doJson(response);
						return null;
					}

					@Override
					public void onPostExecute(Void result) {
						Message msg_listData = new Message();
						msg_listData.what = MESSAGETYPE_05;
						handler.sendMessage(msg_listData);
					}

				}.execute();
			}

		}
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent datax) {
		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case RESULT_OK:
				String strLj = datax.getStringExtra("strLj"); // 零件组名称和ID
				if (strLj != null) {
					String[] ljz = strLj.split(";");
					ljzName = ljz[0];
					ljzId = ljz[1];
				}

				// 如果有零件组ID及价格方案ID，则直接进行查询
				if (hasJgfaLjId() && ljzId != null) {

					new AsyncTask<Void, Void, Void>() {
						@Override
						public void onPreExecute() {
							progressDialog = ProgressDialog.show(context,
									"请稍等", "正在查询换件信息,请稍候!");
						}

						@Override
						protected Void doInBackground(Void... params) {
							SpinnerItem jgfa = (SpinnerItem) priceTypeSp
									.getSelectedItem();
							String jgfaId = jgfa.getID();
							Response response = ServerApiManager.getJtljDataB(
									vehCertainId, jgfaId, ljzId, "1","1");
							doJson(response);
							return null;
						}

						@Override
						public void onPostExecute(Void result) {
							Message msg_listData = new Message();
							msg_listData.what = MESSAGETYPE_05;
							handler.sendMessage(msg_listData);
						}
					}.execute();
				}
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, datax);
	}

	/**
	 * 将结果解析
	 * 
	 * @param response
	 */
	private void doJson(Response response) {
		if (response != null && "1".equals(response.getResponseCode())) {
			SpPartListDTO spPartListDTO = (SpPartListDTO) JsonUtil.getSpDto(
					response, SpPartListDTO.class);

			if (spPartListDTO != null && spPartListDTO.getPartList() != null) {
				int total = spPartListDTO.getTotal();// json.getInt("total");
				share.savePartCount(total);
				if (spPartListDTO.getPartList().size() > 0) {
					chkb = new boolean[spPartListDTO.getPartList().size()];
					evalPartList = new ArrayList<EvalPartInfo>();
					InsuranceItem insuranceItem = insuranceItemList.get(0);
					for (FitsDTO fits : spPartListDTO.getPartList()) {
						EvalPartInfo evalPartInfo = new EvalPartInfo(fits);
						evalPartInfo.setInsureTerm(insuranceItem
								.getInsureTerm());
						evalPartInfo.setInsureTermCode(insuranceItem
								.getInsureTermCode());
						evalPartInfo.setRemnant(0D);
						double refPrice = (evalPartInfo.getChgLocPrice() == null && evalPartInfo
								.getChgLocPrice() > 0) ? evalPartInfo
								.getChgLocPrice() : (evalPartInfo
								.getChgRefPrice() == null ? 0 : evalPartInfo
								.getChgRefPrice());
						evalPartInfo.setLossPrice(refPrice);
						evalPartInfo.setSumPrice(refPrice);

						evalPartList.add(evalPartInfo);
					}
				}
			}
		}
	}

	/**
	 * 按碰撞查询对话框
	 */
	protected void showPzDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.lj_eval_pzh, null);
		dialog.setTitle("碰撞部位及碰撞程度查询");
		dialog.setView(layout);
		pzcd = (Spinner) layout.findViewById(R.id.lj_pzcd);
		pzbw = (Spinner) layout.findViewById(R.id.lj_pzbw);

		pzcd.setAdapter(StaticCode.getAdapter(context, StaticCode.pzcdList));
		pzbw.setAdapter(StaticCode.getAdapter(context, StaticCode.pzbwList));

		dialog.setPositiveButton("查询", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				pzcdStr = ((SpinnerItem) pzcd.getSelectedItem()).getID();
				pzbwStr = ((SpinnerItem) pzbw.getSelectedItem()).getID();

				new AsyncTask<Void, Void, Void>() {
					@Override
					protected void onPreExecute() {
						progressDialog = ProgressDialog.show(context, "请稍等",
								"正在查询零件信息,请稍候!");
					}

					@Override
					protected Void doInBackground(Void... params) {
						SpinnerItem jgfa = (SpinnerItem) priceTypeSp
								.getSelectedItem();
						String jgfaId = jgfa.getID();
						Response response = ServerApiManager.getPzJtljDataB(
								vehCertainId, jgfaId, pzcdStr, pzbwStr, "1","1");
						doJson(response);
						return null;
					}

					@Override
					public void onPostExecute(Void result) {
						handler.sendEmptyMessage(MESSAGETYPE_04);
					}

				}.execute();
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
	}

	private void pageOperation() {
		share.saveEvalJgfaPosition(priceTypeSp.getSelectedItemPosition());
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				progressDialog = ProgressDialog.show(context, "请稍等",
						"正在查询换件信息,请稍候!");
			}

			@Override
			protected Void doInBackground(Void... params) {
				// 查询车型信息
				SpinnerItem jgfa = (SpinnerItem) priceTypeSp.getSelectedItem();
				String jgfaId = jgfa.getID();
				Response response = null;
				// 页号#类型#参数
				String[] page = pageNo.getText().toString().split("#");
				if (page[1] != null && page[1].equals("1")) { // 零件组方式
					response = ServerApiManager.getJtljDataB(vehCertainId,
							jgfaId, page[2], page[0],"1");
				}
				if (page[1] != null && page[1].equals("2")) { // 关键字方式
					response = ServerApiManager.getJtljDataB(vehCertainId,
							jgfaId, page[2], null, page[0],"1");
				}
				if (page[1] != null && page[1].equals("3")) { // 零件名称及原厂零件号方式
					if (page.length == 3) {
						// 当没有输入原厂零件号时
						response = ServerApiManager.getJtljDataB(vehCertainId,
								jgfaId, page[2], "", page[0],"1");
					} else {
						response = ServerApiManager.getJtljDataB(vehCertainId,
								jgfaId, page[2], page[3], page[0],"1");
					}
				}
				if (page[1] != null && page[1].equals("4")) { // 碰撞程度与碰撞方式
					response = ServerApiManager.getPzJtljDataB(vehCertainId,
							jgfaId, page[2], page[3], page[0],"1");
				}
				doJson(response);
				return null;
			}

			@Override
			public void onPostExecute(Void result) {
				Message msg_listData = new Message();
				msg_listData.what = MESSAGETYPE_01;
				handler.sendMessage(msg_listData);
			}
		}.execute();
	}

	private class KeyBtnClickListener implements OnClickListener {
		private String key;

		public KeyBtnClickListener(String key) {
			super();
			this.key = key;
		}

		@Override
		public void onClick(View arg0) {
			if (hasJgfa()) {
				Log.e("KEYKEY", key + "TTTTTT");
				mPopupWindow.dismiss();

				new AsyncTask<Void, Void, Void>() {
					@Override
					protected void onPreExecute() {
						progressDialog = ProgressDialog.show(context, "请稍等",
								"正在查询换件信息,请稍候!");
					}

					@Override
					protected Void doInBackground(Void... params) {
						SpinnerItem jgfa = (SpinnerItem) priceTypeSp
								.getSelectedItem();
						String jgfaId = jgfa.getID();
						Response response = ServerApiManager.getJtljDataB(
								vehCertainId, jgfaId, key, null, "1","1");
						doJson(response);
						return null;
					}

					@Override
					public void onPostExecute(Void result) {
						Message msg_listData = new Message();
						msg_listData.what = MESSAGETYPE_02;
						msg_listData.obj = key;
						handler.sendMessage(msg_listData);
					}
				}.execute();
			}
		}
	}

	// 零件名称及原厂零件号查询
	private void limcDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.lj_eval_ljmc, null);
		dialog.setTitle("零件名称及原厂零件号查询");
		dialog.setView(layout);
		ycljh = (EditText) layout.findViewById(R.id.lj_key_ycljh);
		ljmc = (EditText) layout.findViewById(R.id.lj_key_ljmc);

		dialog.setPositiveButton("查询", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (hasJgfa()) {

					new AsyncTask<Void, Void, Void>() {
						@Override
						protected void onPreExecute() {
							progressDialog = ProgressDialog.show(context,
									"请稍等", "正在查询换件信息,请稍候!");
						}

						@Override
						protected Void doInBackground(Void... params) {
							SpinnerItem jgfa = (SpinnerItem) priceTypeSp
									.getSelectedItem();
							String jgfaId = jgfa.getID();
							cxYcljh = ycljh.getText().toString().trim();
							cxLjmc = ljmc.getText().toString().trim();
							Response response = ServerApiManager.getJtljDataB(
									vehCertainId, jgfaId, cxLjmc, cxYcljh, "1","1");

							doJson(response);
							return null;
						}

						@Override
						public void onPostExecute(Void result) {
							Message msg_listData = new Message();
							msg_listData.what = MESSAGETYPE_03;
							handler.sendMessage(msg_listData);
						}

					}.execute();
				}
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGETYPE_01:
				// 刷新UI，显示数据，并关闭进度条
				progressDialog.dismiss(); // 关闭进度条
				view.setVisibility(View.VISIBLE);
				showViewItem();
				break;
			case MESSAGETYPE_02:
				// 刷新UI，显示数据，并关闭进度条
				progressDialog.dismiss(); // 关闭进度条
				view.setVisibility(View.VISIBLE);
				showViewItem();
				String key = (String) message.obj;
				pageNo.setText("1#2#" + key);
				break;
			case MESSAGETYPE_03:
				// 刷新UI，显示数据，并关闭进度条
				progressDialog.dismiss(); // 关闭进度条
				view.setVisibility(View.VISIBLE);
				showViewItem();
				pageNo.setText("1#3#" + (cxLjmc == null ? " " : cxLjmc) + "#"
						+ (cxYcljh == null ? " " : cxYcljh));
				break;
			case MESSAGETYPE_04:
				// 刷新UI，显示数据，并关闭进度条
				progressDialog.dismiss(); // 关闭进度条
				view.setVisibility(View.VISIBLE);
				showViewItem();
				pageNo.setText("1#4#" + pzcdStr + "#" + pzbwStr);
				break;
			case MESSAGETYPE_05:
				// 刷新UI，显示数据，并关闭进度条
				progressDialog.dismiss(); // 关闭进度条
				view.setVisibility(View.VISIBLE);
				showViewItem();
				pageNo.setText("1#1#" + ljzId + "#" + ljzName);
				break;
			}
		}
	};

	private boolean hasJgfaLjId() {
		boolean flag = true;
		if (priceTypeSp != null) {
			SpinnerItem jgfa = (SpinnerItem) priceTypeSp.getSelectedItem();
			String jgfaId = jgfa.getID();
			if (jgfaId.equals("-1")) {
				flag = false;
				showDialog("请选择价格方案！");
			} else if (ljzId == null) {
				flag = false;
			}
		}
		return flag;
	}

	private boolean hasJgfa() {
		boolean flag = true;
		SpinnerItem jgfa = (SpinnerItem) priceTypeSp.getSelectedItem();
		String jgfaId = jgfa.getID();
		if (jgfaId.equals("-1")) {
			flag = false;
			showDialog("请选择价格方案！");
		}
		return flag;
	}

	/**
	 * 零件列表适配器
	 * 
	 * @author zhaowenbin
	 * 
	 */
	private class PartAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return evalPartList.size();
		}

		@Override
		public Object getItem(int position) {
			return evalPartList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final EvalPartInfo info = evalPartList.get(position);

			convertView = inflater.inflate(R.layout.part_sel_list, null);

			TextWatcher evalPartChangeWatcher = new EvalPartChangeWatcher(
					convertView, info);

			// 零件名称
			TextView tvname = (TextView) convertView
					.findViewById(R.id.list_abl1);
			tvname.setText(info.getPartStandard());
			// 系统价格
			TextView tvxtjg = (TextView) convertView
					.findViewById(R.id.list_abl2);
			tvxtjg.setText(String.valueOf(info.getChgRefPrice() == null ? "0"
					: info.getChgRefPrice()));
			// 本地价格
			TextView tvbdjg = (TextView) convertView
					.findViewById(R.id.list_abl3);
			tvbdjg.setText(String.valueOf(info.getChgLocPrice() == null ? "0"
					: info.getChgLocPrice()));
			// 定损价格-如果存在本地价格,则取本地价格;如果不存在本地价格,判断是否存在系统价格.如果存在系统价格,则取系统价格,否则默认价格为0
			TextView tvLossPrice = (TextView) convertView
					.findViewById(R.id.eval_part_loss_price);
			tvLossPrice.setText(String.valueOf(info.getLossPrice()));
			tvLossPrice
					.setOnFocusChangeListener(new DefaultDoubleValueListener(
							"0"));
			// 数量
			TextView tvLossCount = (TextView) convertView
					.findViewById(R.id.eval_part_loss_count);
			tvLossCount
					.setOnFocusChangeListener(new DefaultIntegerValueListener(
							"1"));
			// 残值
			TextView tvRemant = (TextView) convertView
					.findViewById(R.id.eval_part_remant);
			tvRemant.setOnFocusChangeListener(new DefaultDoubleValueListener(
					"0"));

			// 文本监听
			tvLossCount.addTextChangedListener(evalPartChangeWatcher);
			tvLossPrice.addTextChangedListener(evalPartChangeWatcher);
			tvRemant.addTextChangedListener(evalPartChangeWatcher);

			// 小计
			TextView tvLossSum = (TextView) convertView
					.findViewById(R.id.eval_part_loss_sum);
			tvLossSum.setText(String.valueOf(info.getSumPrice()));
			// 监听选中事件
			CheckBox checkBox = (CheckBox) convertView
					.findViewById(R.id.list_ablbox);
			// 显示隐藏内容
			View partOtherRL = convertView.findViewById(R.id.rl_part);
			if (chkb[position]) {
				checkBox.setChecked(true);
				partOtherRL.setVisibility(View.VISIBLE);
				convertView.setBackgroundResource(R.color.item_selected);
			} else {
				checkBox.setChecked(false);
				partOtherRL.setVisibility(View.GONE);
				convertView.setBackgroundResource(R.color.white);
			}
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					LinearLayout parent = (LinearLayout) buttonView.getParent()
							.getParent();
					View partOtherRL = parent.findViewById(R.id.rl_part);

					chkb[position] = isChecked;
					if (isChecked) {
						parent.setBackgroundResource(R.color.item_selected);
						partOtherRL.setVisibility(View.VISIBLE);

						AnimationSet set = new AnimationSet(true);

						Animation animation = new TranslateAnimation(
								Animation.RELATIVE_TO_SELF, 0.0f,
								Animation.RELATIVE_TO_SELF, 0.0f,
								Animation.RELATIVE_TO_SELF, -1.0f,
								Animation.RELATIVE_TO_SELF, 0.0f);
						animation.setDuration(500);
						set.addAnimation(animation);

						partOtherRL.startAnimation(set);

					} else {
						parent.setBackgroundResource(R.color.white);
						partOtherRL.setVisibility(View.GONE);
					}
				}

			});
//			// 单击弹出险别列表
//			TextView insureTermTv = (TextView) convertView
//					.findViewById(R.id.tv_insureTermCode);
//			insureTermTv.setText(info.getInsureTerm());
//			insureTermTv.setTag(info.getInsureTermCode());
//			insureTermTv.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					insureTermPopup(v, info);
//				}
//
//			});
			// 改变残值
			EditText remainEt = (EditText) convertView
					.findViewById(R.id.eval_part_remant);
			remainEt.setText(String.valueOf(info.getRemnant() == null ? 0
					: info.getRemnant()));
			remainEt.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					info.setRemnant(MathUtil.getDouble(s.toString(), 0));
				}
			});

			return convertView;
		}
	}

	private void showViewItem() {
		if (ljzName != null) {
			partGroupBtn.setText(ljzName);
		}
		if (evalPartList != null) {
			partListLv = (ListView) findViewById(R.id.part_partList);
			PartAdapter adapter = new PartAdapter();
			partListLv.setAdapter(adapter);
			if (mPopupWindow != null && mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}

			// 弹出对话框添加到定损单中
			partListLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					EvalPartInfo evalP = evalPartList.get(position);
					partDialog(evalP);
				}
			});
			// seletedPartData = new ArrayList<HashMap<String, Object>>(); //
			// 无论是点击整行还是挑选checkBox时，将内容先暂存到此记录中
		} else {
			showDialog("该价格方案及零件组下没有对应的零件，请确认查询条件！");
		}
	}

	private void partDialog(EvalPartInfo evalP) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.dag_addpart, null);
		dialog.setTitle(ljname);
		dialog.setView(layout);
		TextView xtjg = (TextView) layout.findViewById(R.id.addpart_xtjg);
		TextView bdjg = (TextView) layout.findViewById(R.id.addpart_bdjg);
		TextView ycljh = (TextView) layout.findViewById(R.id.addpart_ycljh);
		TextView ljbz = (TextView) layout.findViewById(R.id.addpart_ljbz);
		TextView xtjg1 = (TextView) layout.findViewById(R.id.addpart_xtjg1);
		TextView xtjg2 = (TextView) layout.findViewById(R.id.addpart_xtjg2);
		TextView bdjg1 = (TextView) layout.findViewById(R.id.addpart_bdjg1);
		TextView bdjg2 = (TextView) layout.findViewById(R.id.addpart_bdjg2);
		TextView bdjg3 = (TextView) layout.findViewById(R.id.addpart_bdjg3);

		xtjg.setText(String.valueOf(evalP.getChgRefPrice()));
		bdjg.setText(String.valueOf(evalP.getChgLocPrice()));
		ycljh.setText(evalP.getOriginalId());
		// xtjg1.setText(jg[2]);
		// xtjg2.setText(jg[3]);
		// bdjg1.setText(jg[4]);
		// bdjg2.setText(jg[5]);
		// bdjg3.setText(jg[6]);
		ljbz.setText(evalP.getRemark());
		dialog.setNegativeButton("确定", null);
		dialog.show();
	}

	@Override
	protected void onDestroy() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		super.onDestroy();
	}

	private void popUpDialog(AlertDialog.Builder dialog) {
		dialog.show();
	}

	// 自定义零件对话框
	private AlertDialog.Builder mydialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.dag_custompart, null);
		dialog.setTitle("自定义零件添加");
		dialog.setView(layout);
		diaPrice = (EditText) layout.findViewById(R.id.eval_part_loss_price);
		diaNum = (EditText) layout.findViewById(R.id.eval_part_loss_count);
		diaCustomName = (EditText) layout.findViewById(R.id.lj_dia_customName);
		diaPrice.setText("0.0");
		diaNum.setText("1");
		dialog.setNegativeButton("取消", mOnDialogClickListener)
				.setPositiveButton("保存", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
						String price = diaPrice.getText().toString().trim();
						String num = diaNum.getText().toString().trim();
						String ljmc = diaCustomName.getText().toString().trim();
						if (ljmc == null || ljmc.equals("")) {
							// Toast.makeText(context, "自定义零件名称不能为空！",
							// 1500).show();
							diaCustomName.setError("自定义零件名称不能为空！");
							// 进行以下设置将不能关闭dialog
							try {
								Field field = dialog.getClass().getSuperclass()
										.getDeclaredField("mShowing");
								field.setAccessible(true);
								field.set(dialog, false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (share.getFloatByStr(price) <= 0) {
							diaPrice.setError("价格不能为零");
							diaPrice.requestFocus();
							diaPrice.setFocusable(true);
							// 进行以下设置将不能关闭dialog
							try {
								Field field = dialog.getClass().getSuperclass()
										.getDeclaredField("mShowing");
								field.setAccessible(true);
								field.set(dialog, false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							if (evalPartAction.getExistsEvalFits(evalId, ljmc)) {
								try {
									Field field = dialog.getClass()
											.getSuperclass()
											.getDeclaredField("mShowing");
									field.setAccessible(true);
									field.set(dialog, false);
									toast("重复添加失败");
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								EditText originPartNo = (EditText) layout
										.findViewById(R.id.lj_dia_original); // 零件号
								EditText rem = (EditText) layout
										.findViewById(R.id.lj_dia_rem); // 殖值
								CheckBox use = (CheckBox) layout
										.findViewById(R.id.lj_dia_use); // 是否回收
								EditText bz = (EditText) layout
										.findViewById(R.id.lj_dia_mark); // 备注
								String strCz = rem.getText().toString();
								float jg = share.getFloatByStr(price);
								float sl = share.getFloatByStr(num);

								// TODO 处理残值
								float cz = share.getFloatByStr("0");

								EvalPartInfo partInfo = new EvalPartInfo();
								partInfo.setEvalId(Long.valueOf(evalId));
								partInfo.setPartStandard(ljmc);
								partInfo.setOriginalId(originPartNo.getText()
										.toString());
								partInfo.setSelfConfigFlag("1");
								partInfo.setLossPrice(Double.valueOf(price));
								partInfo.setLossCount(Integer.valueOf(num));
								partInfo.setSumPrice(Double.valueOf(jg * sl
										- cz));
								// TODO 处理残值
								partInfo.setRemnant(Double.valueOf("0"));
								partInfo.setIfRemain(use.isChecked() == true ? "1"
										: "0");
								partInfo.setRemark(bz.getText().toString());
								partInfo.setChgRefPrice(0D);
								partInfo.setChgLocPrice(0D);

								InsuranceItem insuranceItem = (InsuranceItem) spnInsuranceTerm
										.getSelectedItem();
								partInfo.setInsureTerm(insuranceItem
										.getInsureTerm());
								partInfo.setInsureTermCode(insuranceItem
										.getInsureTermCode());
								
								SpinnerItem jgfa = (SpinnerItem) priceTypeSp
										.getSelectedItem();
								String jgfaId = SharedData.data().getJgfaidByJgfabm(jgfa.getID());
								partInfo.setChgCompSetCode(jgfaId);
								partInfo.setChgCompSetName(jgfa.getValue());
								partInfo.setPartStandardCode("999999");
								String isInsert = check(partInfo);
								if (isInsert.equals("1")) {
									evalPartAction.insertEvalFits(partInfo);
								} else {
									toast(isInsert);
								}
								evalPartAction.insertEvalFits(partInfo);

								finish();
							}
						}
					}
				}).create();
		return dialog;
	}

	private String check(EvalPartInfo partInfo) {
		String value = "1";
		if (partInfo.getPartId().equals("") && partInfo.getPartId() == null) {
			value = "无定损系统零件唯一ID，添加失败";
		}
		// if(partInfo.getSerialNo().equals("")&&partInfo.getSerialNo()==null){
		// value = "无中银估损单唯一序号，添加失败";
		// }
		// if(partInfo.getOriginalName()==null&&partInfo.getOriginalName().equals("")){
		// partInfo.setOriginalName(partInfo.getPartStandard());
		// value = "无零配件原厂名称，添加失败";
		// }
		if (partInfo.getPartStandard().equals("")
				&& partInfo.getPartStandard() == null) {
			value = "无配件标准名称，添加失败";
		}
		if (partInfo.getLossPrice() == 0d) {
			value = "无定损价格，添加失败";
		}
		// if(partInfo.getRepairSitePrice()==0d){
		// value = "无修理厂价格，添加失败";
		// }
		if (partInfo.getLossCount() == 0d) {
			value = "无换件数量，添加失败";
		}
		if (partInfo.getSumPrice() == 0d) {
			value = "无换件金额小计，添加失败";
		}
		if (partInfo.getSelfConfigFlag().equals("")
				&& partInfo.getSelfConfigFlag() == null) {
			value = "无自定义配件标记，添加失败";
		}
		if (partInfo.getChgCompSetCode().equals("")
				&& partInfo.getChgCompSetCode() == null) {
			value = "无价格方案编码，添加失败";
		}
		if (partInfo.getChgCompSetName().equals("")
				&& partInfo.getChgCompSetName() == null) {
			value = "无价格方案名称，添加失败";
		}
		if (partInfo.getIfRemain().equals("") && partInfo.getIfRemain() == null) {
			value = "无是否损余回收，添加失败";
		}
		if (partInfo.getRemnant().equals("") && partInfo.getRemnant() == null) {
			value = "无残值，添加失败";
		}
		if (partInfo.getInsureTerm().equals("")
				&& partInfo.getInsureTerm() == null) {
			value = "无险别，添加失败";
		}
		if (partInfo.getInsureTermCode().equals("")
				&& partInfo.getInsureTermCode() == null) {
			value = "无险别代码，添加失败";
		}
		if (MathUtil.setScale(partInfo.getRemnant())>MathUtil.setScale(partInfo.getLossPrice())) {
			value = "残值大于定损价格，添加失败";
		}
		return value;
	}

	private DialogInterface.OnClickListener mOnDialogClickListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int buttonId) {
			try {
				Field field = dialog.getClass().getSuperclass()
						.getDeclaredField("mShowing");
				field.setAccessible(true);
				field.set(dialog, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.part_ljfz:// 选择零件组
			share.saveEvalJgfaPosition(priceTypeSp.getSelectedItemPosition());
			Intent intent = new Intent(context, PartGroupSelectActivity.class);
			intent.putExtra("vehicelId", vehCertainId);
			startActivityForResult(intent, 1);
			break;
		case R.id.part_btn_pz:
			showPzDialog();
			break;
		case R.id.part_btn_ljmc:
			limcDialog();
			break;
		case R.id.menu_title_back:
			finish();
			break;
		case R.id.menu_title_flash:
			Intent intentEval = new Intent(context, EvalPartActivity.class);
			intentEval.putExtra("title_flag", "定损信息");
			intentEval.putExtra("evalId", evalId);
			intentEval.putExtra("vehCertainId", vehCertainId);
			startActivity(intentEval);
			finish();
			break;
		case R.id.part_btn_zdy:// 自定义零件组
			popUpDialog(mydialog());
			break;
		case R.id.part_btn_add:// 批量增加零件
			batchAddPart();
			break;
		case R.id.part_btn_gjz:// 通过关键字查找零件
			selectPartByKeyword();
			break;
		default:
			break;
		}
	}

	/**
	 * 批量保存零件
	 */
	private void batchAddPart() {
		int j = 0;
		if (chkb != null && chkb.length > 0) {
			for (int i = 0, len = chkb.length; i < len; i++) {
				if (chkb[i]) {
					EvalPartInfo partInfo = evalPartList.get(i);

					if (evalPartAction.getExistsEvalFits(evalId,
							partInfo.getPartStandard())) {
						j++;
					} else {
						// if (partInfo.getChgLocPrice() == null
						// || partInfo.getChgLocPrice() <= 0.0) {
						// partInfo.setChgLocPrice(partInfo.getChgRefPrice());
						// }
						partInfo.setEvalId(Long.valueOf(evalId));
						partInfo.setSelfConfigFlag("2");
						// partInfo.setLossCount(Integer.valueOf(1));
						// partInfo.setRemnant(0D);
						if (partInfo.getRemnant() != null
								&& partInfo.getRemnant() > 0) {
							partInfo.setIfRemain("1");
						} else {
							partInfo.setIfRemain("0");
						}

						SpinnerItem jg = (SpinnerItem) priceTypeSp
								.getSelectedItem();
						String jgfaId = SharedData.data().getJgfaidByJgfabm(jg.getID());
						partInfo.setChgCompSetCode(jgfaId);
//						partInfo.setChgCompSetCode(jg.getID());
						partInfo.setChgCompSetName(jg.getValue());

						InsuranceItem insuranceItem = (InsuranceItem) spnInsuranceTerm
								.getSelectedItem();
						partInfo.setInsureTerm(insuranceItem.getInsureTerm());
						partInfo.setInsureTermCode(insuranceItem
								.getInsureTermCode());

						evalPartAction.insertEvalFits(partInfo);
					}
				}

			}

			if (j > 0) {// 标志有重复添加失败的，
				Toast.makeText(context, "重复添加失败数量为" + j, Toast.LENGTH_SHORT)
						.show();
			}
			finish();
		} else {
			Toast.makeText(context, "请先选择零件", Toast.LENGTH_SHORT).show();
		}
	}

	private void selectPartByKeyword() {
		keyword = inflater.inflate(R.layout.lj_eval_keyword, null);
		mPopupWindow = new PopupWindow(keyword, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.rounded_corners_pop));
		// 初始化按钮
		for (int i = 1; i <= 40; i++) {
			int id = getResources().getIdentifier("lj_key" + i, "id",
					getPackageName());
			Button keyBtn = (Button) keyword.findViewById(id);
			keyPart = keyBtn.getText().toString();
			keyBtn.setOnClickListener(new KeyBtnClickListener(keyPart));
		}
		Button cancleBtn = (Button) keyword.findViewById(R.id.lj_cancle);
		cancleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mPopupWindow.dismiss();
			}
		});
		mPopupWindow.showAtLocation(keyword.findViewById(R.id.TableLayout01),
				Gravity.CENTER | Gravity.TOP, 0, 2000);
	}

//	private void insureTermPopup(final View v, final EvalPartInfo evalP) {
//		View popupview = View.inflate(this, R.layout.part_insureterm_popup,
//				null);
//		final ListView lvInsuranceItem = (ListView) popupview
//				.findViewById(R.id.lv_1);
//		final PopupWindow insureTermP = new PopupWindow(popupview,
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//
//		InsuranceItemAdapter adapter = new InsuranceItemAdapter();
//		lvInsuranceItem.setAdapter(adapter);
//
//		lvInsuranceItem.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				InsuranceItem in = insuranceItemList.get(position);
//				TextView tv = (TextView) v;
//				tv.setText(in.getInsureTerm());
//
//				evalP.setInsureTerm(in.getInsureTerm());
//				evalP.setInsureTermCode(in.getInsureTermCode());
//
//				if (insureTermP != null && insureTermP.isShowing()) {
//					insureTermP.dismiss();
//				}
//			}
//		});
//
//		insureTermP.setFocusable(true);
//		Drawable background = getResources().getDrawable(
//				R.drawable.local_popup_bg);
//		insureTermP.setBackgroundDrawable(background);
//		// insureTermP.showAtLocation(popupview, Gravity.LEFT | Gravity.TOP,
//		// (int) cLeft + 50, (int) cTop + 100);
//		insureTermP.showAsDropDown(v);
//
//	}

	/**
	 * 换件编辑项目改变的监听器:监听单价、数量、残值等值
	 * 
	 * @author zhaowenbin
	 * 
	 */
	private class EvalPartChangeWatcher extends SimpleTextWatcher {

		private View containerView;
		private EvalPartInfo partInfo;

		public EvalPartChangeWatcher(View containerView, EvalPartInfo partInfo) {
			this.containerView = containerView;
			this.partInfo = partInfo;
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			EditText etPartLossCount = (EditText) containerView
					.findViewById(R.id.eval_part_loss_count);
			EditText etPartLossPrice = (EditText) containerView
					.findViewById(R.id.eval_part_loss_price);
			EditText etPartRemant = (EditText) containerView
					.findViewById(R.id.eval_part_remant);
			TextView tvLossSum = (TextView) containerView
					.findViewById(R.id.eval_part_loss_sum);

			double lossPrice = MathUtil.getDouble(etPartLossPrice.getText()
					.toString(), 0);
			double remant = MathUtil.getDouble(etPartRemant.getText()
					.toString(), 0);
			int lossCount = MathUtil.getInt(etPartLossCount.getText()
					.toString(), 1);

			double lossSum = lossPrice * lossCount - remant;
			tvLossSum.setText(String.valueOf(lossSum));

			partInfo.setLossPrice(lossPrice);
			partInfo.setRemnant(remant);
			partInfo.setLossCount(lossCount);
			partInfo.setSumPrice(lossSum);
		}
	}

	/**
	 * 险别列表适配器
	 * 
	 * @author zhaowenbin
	 * 
	 */
	private class InsuranceItemAdapter extends BaseAdapter {

		public InsuranceItemAdapter() {

		}

		@Override
		public int getCount() {
			return insuranceItemList.size();
		}

		@Override
		public InsuranceItem getItem(int position) {
			return insuranceItemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			InsuranceItem item = insuranceItemList.get(position);
			TextView tv = new TextView(context);
			tv.setPadding(5, 5, 5, 5);
			tv.setTextColor(0xff000000);
			tv.setText(item.getInsureTermCode() + " - " + item.getInsureTerm());
			return tv;
		}

	}
}