package com.jy.recycle.ui.eval.part;

/**
 * 选择零件配件页面
 */
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.mobile.dto.FitsDTO;
import com.jy.mobile.response.SpPartListDTO;
import com.jy.recycle.R;
import com.jy.recycle.client.response.EvalPartInfo;
import com.jy.recycle.common.DefaultDoubleValueListener;
import com.jy.recycle.common.DefaultIntegerValueListener;
import com.jy.recycle.dao.EvalPartDao;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.ui.view.CustomepzbwMustSpinner;
import com.jy.recycle.ui.view.CustomepzcdMustSpinner;
import com.jy.recycle.util.MathUtil;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.SimpleTextWatcher;
import com.jy.recycle.util.SpinnerItem;
import com.jy.recycle.util.StaticCode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EvalPartActivity extends JyBaseActivity {
	private Context context;
	private EvalPartDao evalPartAction;
	private SharedData share;
	private String inputNum, inputMark;
	private EditText ed;
	private Dialog inputDialog;
	private Button partGroupBtn;
	private String ljzId;
	private String ljzName;
	private Long evalId;
	private String vehCertainId;
	private String pageInfo;
	private String keyPart;
	private String cxLjmc;
	private String cxYcljh;
	private String pzbwStr;
	private String pzcdStr;
	private String titleFlag;

	private static final int MESSAGETYPE_01 = 0x0001;
	private static final int MESSAGETYPE_02 = 0x0002;
	private static final int MESSAGETYPE_03 = 0x0003;
	private static final int MESSAGETYPE_04 = 0x0004;
	private static final int MESSAGETYPE_05 = 0x0005;
	private int count = 0, total = 0;// partList中已经列出的零件的数量

	private PartAdapter adapter;

	private ListView partListLv;

	private CustomepzbwMustSpinner pzbw;
	private CustomepzcdMustSpinner pzcd;

	private TextView diaPrice;
	private TextView diaNum;
	private EditText ycljh;
	private EditText ljmc;
	private TextView pageNo; // 隐藏页码,同时查询类型及参数都暂存
	private View keyword;
	private View view;
	private ProgressDialog progressDialog = null;
	private PopupWindow mPopupWindow;
	private Button partNameBtn;
	private Button partCommonBtn;
	private Button btnPz;
	private Button btn_back;
	private Button keywordBtn;
	private Button defineBtn;
	private ImageButton batchAddBtn;

	private List<EvalPartInfo> evalPartList;
	private boolean[] chkb; // 记录checkbox选中数据

	private LayoutInflater inflater;

	private static final int BIGGER = 1;
	private static final int MSG_RESIZE = 1;
	InputMethodManager imm = null;

	class InputHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_RESIZE: {
					if (msg.arg1 == BIGGER) {
						findViewById(R.id.partcontrollayout).setVisibility(
								View.VISIBLE);
					} else {
						findViewById(R.id.partcontrollayout).setVisibility(
								View.GONE);
					}
				}
				break;

				default:
					break;
			}
			super.handleMessage(msg);
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lj_evalpart);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 读参数
		if (!readArgs()) {
			return;
		}
		inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		evalPartList = new ArrayList<EvalPartInfo>();

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
		partCommonBtn.setOnClickListener(this);
		btnPz.setOnClickListener(this);
	}

	private void findView() {
		partListLv = (ListView) findViewById(R.id.part_partList);
		adapter = new PartAdapter();
		view = findViewById(R.id.showList);
		partGroupBtn = (Button) findViewById(R.id.part_ljfz);
		pageNo = (TextView) findViewById(R.id.pageNo);
		pageNo.setText("1");
		partNameBtn = (Button) findViewById(R.id.part_btn_ljmc);
		partCommonBtn = (Button) findViewById(R.id.part_btn_cy);
		// 按碰撞程度查询
		btnPz = (Button) findViewById(R.id.part_btn_pz);
		btn_back = (Button) findViewById(R.id.menu_title_back);
		btn_back.setText(titleFlag);
		// 查询获取零件数据 改为关键字查询，用弹出窗口
		keywordBtn = (Button) findViewById(R.id.part_btn_gjz);
		// 增加自定义零件
		defineBtn = (Button) findViewById(R.id.part_btn_zdy);
		// 批量增加
		batchAddBtn = (ImageButton) findViewById(R.id.part_btn_add);

		final TextView btnLinkType = (TextView) findViewById(R.id.eval_btn_outLine);
		btnLinkType.setText("添加");
		btnLinkType.setOnClickListener(this);
		bindView();
	}

	private void initBaseData() {
		context = this;
		share = SharedData.data();
		// claimEvalAction = ClaimEvalDao.getInstance();
		evalPartAction = EvalPartDao.getInstance();

		TextView titlename = (TextView) findViewById(R.id.menu_title_name);
		titlename.setText("换件项目");

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
						if (ljzId != null) {

							new AsyncTask<Void, Void, Void>() {
								@Override
								public void onPreExecute() {
									progressDialog = ProgressDialog.show(context,
											"请稍等", "正在查询换件信息,请稍候!");
								}

								@Override
								protected Void doInBackground(Void... params) {
									String jgfaId = null;
									SpPartListDTO jtljData = ServerApiManager
											.getJtljData(vehCertainId, jgfaId, ljzId,
													"1");
									doJson(jtljData);
									return null;
								}

								@Override
								public void onPostExecute(Void result) {
									progressDialog.dismiss(); // 关闭进度条

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
	 * @param spPartListDTO
	 */
	private void doJson(SpPartListDTO spPartListDTO) {

		if (spPartListDTO != null && spPartListDTO.getPartList() != null) {
			total = spPartListDTO.getTotal();// json.getInt("total");\
			count = spPartListDTO.getPartList().size();
			share.savePartCount(total);
			if (spPartListDTO.getPartList().size() > 0) {
				chkb = new boolean[spPartListDTO.getPartList().size()];
				evalPartList = new ArrayList<EvalPartInfo>();
				for (FitsDTO fits : spPartListDTO.getPartList()) {
					EvalPartInfo evalPartInfo = new EvalPartInfo(fits);
					evalPartInfo.setRemnant(0D);
					double refPrice = (evalPartInfo.getChgLocPrice() == null && evalPartInfo
							.getChgLocPrice() > 0) ? evalPartInfo
							.getChgLocPrice()
							: (evalPartInfo.getChgRefPrice() == null ? 0
							: evalPartInfo.getChgRefPrice());
					evalPartInfo.setRefPrice1(fits.getRefPrice1());
					evalPartInfo.setRefPrice2(fits.getRefPrice2());
					evalPartInfo.setRefPrice3(fits.getRefPrice3());
					evalPartInfo.setLocPrice1(fits.getLocPrice1());
					evalPartInfo.setLocPrice2(fits.getLocPrice2());
					evalPartInfo.setLocPrice3(fits.getLocPrice3());
					evalPartInfo.setLossPrice(refPrice);
					evalPartInfo.setChgRefPrice(fits.getXtjg());
					if (fits.getBdjg() != null) {
						evalPartInfo.setChgLocPrice(fits.getBdjg());
					} else {
						evalPartInfo.setChgLocPrice(0.0);
					}
					evalPartInfo.setSumPrice(refPrice);
					evalPartInfo.setJySystemId(fits.getLjid());
					evalPartInfo.setRemark(fits.getBz());
					evalPartInfo.setOldDetail("1");
					// 中路没有精米标记，全记为0
					evalPartInfo.setLocalFlag("0");
					evalPartList.add(evalPartInfo);
				}
			}
		}
	}

	/**
	 * 将结果解析
	 *
	 * @param spPartListDTO
	 */
	private List<EvalPartInfo> doJsonMore(SpPartListDTO spPartListDTO) {
		List<EvalPartInfo> data = new ArrayList<EvalPartInfo>();
		if (spPartListDTO != null && spPartListDTO.getPartList() != null) {
			if (spPartListDTO.getPartList().size() > 0) {
				chkb = new boolean[spPartListDTO.getPartList().size()];
				for (FitsDTO fits : spPartListDTO.getPartList()) {
					EvalPartInfo evalPartInfo = new EvalPartInfo(fits);
					evalPartInfo.setRemnant(0D);
					double refPrice = (evalPartInfo.getChgLocPrice() == null && evalPartInfo
							.getChgLocPrice() > 0) ? evalPartInfo
							.getChgLocPrice()
							: (evalPartInfo.getChgRefPrice() == null ? 0
							: evalPartInfo.getChgRefPrice());
					evalPartInfo.setLossPrice(refPrice);
					evalPartInfo.setSumPrice(refPrice);
					evalPartInfo.setJySystemId(fits.getLjid());
					evalPartInfo.setRemark(fits.getBz());
					evalPartInfo.setRefPrice1(fits.getRefPrice1());
					evalPartInfo.setRefPrice2(fits.getRefPrice2());
					evalPartInfo.setRefPrice3(fits.getRefPrice3());
					evalPartInfo.setLocPrice1(fits.getLocPrice1());
					evalPartInfo.setLocPrice2(fits.getLocPrice2());
					evalPartInfo.setLocPrice3(fits.getLocPrice3());
					evalPartInfo.setLossPrice(refPrice);
					evalPartInfo.setChgRefPrice(fits.getXtjg());
					evalPartInfo.setChgLocPrice(fits.getBdjg());
					evalPartInfo.setLocalFlag(fits.getState() == null ? "0"
							: fits.getState());
					if (share.getPowerXTPrice().equals("0")) {
						evalPartInfo.setChgRefPrice(0D);
					}
					if (share.getPowerBDPrice().equals("0")) {
						evalPartInfo.setChgLocPrice(0D);
					}
					data.add(evalPartInfo);
				}
			}
		}
		return data;
	}

	/**
	 * 按碰撞查询对话框
	 */
	protected void showPzDialog() {
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.lj_eval_pzh, null);
		pzcd = (CustomepzcdMustSpinner) layout.findViewById(R.id.lj_pzcd);
		pzbw = (CustomepzbwMustSpinner) layout.findViewById(R.id.lj_pzbw);
		pzbw.setText("碰撞部位");
		pzcd.setText("碰撞程度");
		pzcd.setList(StaticCode.pzcdList);
		pzbw.setList(StaticCode.pzbwList);
		ArrayAdapter<SpinnerItem> pzcdAdapter = new ArrayAdapter<SpinnerItem>(
				this, android.R.layout.simple_spinner_item, StaticCode.pzcdList);
		ArrayAdapter<SpinnerItem> pzbwAdapter = new ArrayAdapter<SpinnerItem>(
				this, android.R.layout.simple_spinner_item, StaticCode.pzbwList);
		pzcd.setAdapter(pzcdAdapter);
		pzbw.setAdapter(pzbwAdapter);

		Button dialogOk = (Button) layout.findViewById(R.id.dialog_ok);
		Button dialogCancle = (Button) layout.findViewById(R.id.dialog_cancle);

		dialogOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				// TODO Auto-generated method stub
				// pzcdStr = pzcdType.getID();
				// pzbwStr =pzbwType.getID();
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
						String jgfaId = null;
						SpPartListDTO pzJtljData = ServerApiManager
								.getPzJtljData(vehCertainId, jgfaId, pzcdStr,
										pzbwStr, "1");
						doJson(pzJtljData);
						return null;
					}

					@Override
					public void onPostExecute(Void result) {
						progressDialog.dismiss(); // 关闭进度条
						handler.sendEmptyMessage(MESSAGETYPE_04);
					}

				}.execute();
			}
		});
		dialogCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.setContentView(layout);
		Window window = dialog.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		Display dp = getWindowManager().getDefaultDisplay();
		lp.width = dp.getWidth() * 5 / 6;
		window.setAttributes(lp);
		dialog.show();
	}

	private void pageOperation() {
		final String[] page = pageNo.getText().toString().split("#");
		// share.saveEvalJgfaPosition(priceTypeSp.getSelectedItemPosition());
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				progressDialog = ProgressDialog.show(context, "请稍等",
						"正在查询换件信息,请稍候!");
			}

			@Override
			protected Void doInBackground(Void... params) {
				// 查询车型信息
				String jgfaId = null;
				SpPartListDTO response = null;
				// 页号#类型#参数
				if (page[1] != null && page[1].equals("1")) { // 零件组方式
					ServerApiManager.getJtljData(vehCertainId, jgfaId, page[2],
							page[0]);
				}
				if (page[1] != null && page[1].equals("2")) { // 关键字方式
					response = ServerApiManager.getJtljData(vehCertainId,
							jgfaId, page[2], null, page[0]);
				}
				if (page[1] != null && page[1].equals("3")) { // 零件名称及原厂零件号方式
					if (page.length == 3) {
						// 当没有输入原厂零件号时
						response = ServerApiManager.getJtljData(vehCertainId,
								jgfaId, page[2], "", page[0]);
					} else {
						response = ServerApiManager.getJtljData(vehCertainId,
								jgfaId, page[2], page[3], page[0]);
					}
				}
				if (page[1] != null && page[1].equals("4")) { // 碰撞程度与碰撞方式
					response = ServerApiManager.getPzJtljData(vehCertainId,
							jgfaId, page[2], page[3], page[0]);
				}
				doJson(response);
				return null;
			}

			@Override
			public void onPostExecute(Void result) {
				progressDialog.dismiss(); // 关闭进度条

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
			Log.e("KEYKEY", key + "TTTTTT");
			keyPart = key;
			mPopupWindow.dismiss();

			new AsyncTask<Void, Void, Void>() {
				@Override
				protected void onPreExecute() {
					progressDialog = ProgressDialog.show(context, "请稍等",
							"正在查询换件信息,请稍候!");
				}

				@Override
				protected Void doInBackground(Void... params) {
					String jgfaId = null;
					SpPartListDTO response = ServerApiManager.getJtljData(
							vehCertainId, jgfaId, key, null, "1");
					doJson(response);
					return null;
				}

				@Override
				public void onPostExecute(Void result) {
					progressDialog.dismiss(); // 关闭进度条

					Message msg_listData = new Message();
					msg_listData.what = MESSAGETYPE_02;
					msg_listData.obj = key;
					handler.sendMessage(msg_listData);
				}
			}.execute();
		}
	}

	protected void limcDialog() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.lj_eval_ljmc, null);
		ycljh = (EditText) layout.findViewById(R.id.lj_key_ycljh);
		ljmc = (EditText) layout.findViewById(R.id.lj_key_ljmc);
		Button dialogOk = (Button) layout.findViewById(R.id.dialog_ok);
		Button dialogCancle = (Button) layout.findViewById(R.id.dialog_cancle);
		cxYcljh = ycljh.getText().toString().trim();
		cxLjmc = ljmc.getText().toString().trim();
		dialogOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				// TODO Auto-generated method stub
				//
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected void onPreExecute() {
						progressDialog = ProgressDialog.show(context, "请稍等",
								"正在查询换件信息,请稍候!");
					}

					@Override
					protected Void doInBackground(Void... params) {
						String jgfaId = null;
						SpPartListDTO response = ServerApiManager.getJtljData(
								vehCertainId, jgfaId, cxLjmc, cxYcljh, "1");
						doJson(response);
						return null;
					}

					@Override
					public void onPostExecute(Void result) {
						progressDialog.dismiss(); // 关闭进度条

						Message msg_listData = new Message();
						msg_listData.what = MESSAGETYPE_03;
						handler.sendMessage(msg_listData);
					}

				}.execute();
			}
		});
		dialogCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.setContentView(layout);
		Window window = dialog.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		Display dp = getWindowManager().getDefaultDisplay();
		lp.width = dp.getWidth() * 5 / 6;
		window.setAttributes(lp);
		dialog.show();

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
	private TextView diaSum;
	private EditText diaCustomName;
	/**
	 * 零件列表适配器
	 *
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
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.part_sel_list, null);
				holder.checkBox = (CheckBox) convertView
						.findViewById(R.id.list_ablbox);
				holder.tvname = (TextView) convertView
						.findViewById(R.id.list_abl1);
				holder.tvxtjg = (TextView) convertView
						.findViewById(R.id.list_abl2);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.checkBox.setChecked(chkb[position]);
			holder.tvname.setText(info.getPartStandard());

			holder.tvxtjg.setText(info.getRemark());
			holder.checkBox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					chkb[position] = !chkb[position];

				}
			});
			return convertView;

		}

		public class ViewHolder {
			CheckBox checkBox;
			TextView tvname, tvxtjg;
		}
	}

	// 进入配件页面时，调用004010的查询常用配件
	private class AutoPartSeachTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "请稍等",
					"正在查询换件信息,请稍候!");
		}

		@Override
		protected Void doInBackground(Void... params) {
			String jgfaId = null;
			SpPartListDTO autoPjData = ServerApiManager.getPjDataAuto(
					vehCertainId, jgfaId, "1");
			doJson(autoPjData);
			return null;
		}

		@Override
		public void onPostExecute(Void result) {
			progressDialog.dismiss(); //
			Message msg_listData = new Message();
			msg_listData.what = MESSAGETYPE_01;
			handler.sendMessage(msg_listData);
		}

	}

	private void showViewItem() {
		if (ljzName != null) {
			partGroupBtn.setText(ljzName);
		}
		if (evalPartList != null) {
//			partListLv = (ListView) findViewById(R.id.part_partList);
//			adapter = new PartAdapter();
			adapter.notifyDataSetChanged();
			partListLv.setAdapter(adapter);
			partListLv.setOnScrollListener(new ListScrollListener());
			if (mPopupWindow != null && mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}
		} else {
			showDialog("该价格方案及零件组下没有对应的零件，请确认查询条件！");
		}
	}

	/**
	 * ListView 滚动监听器（动态加载数据）
	 *
	 * @author iStar,2011-7-5
	 *
	 */
	class ListScrollListener implements OnScrollListener {

		private boolean mTopFreeDisplayFoot = false;

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& mTopFreeDisplayFoot) {
				if (count < total) {
					// new GetMorePartListTask().execute();
				} else {
				}

			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
							 int visibleItemCount, int totalItemCount) {
			// reach bottom of the ListView
			if (firstVisibleItem + visibleItemCount >= totalItemCount) {
				mTopFreeDisplayFoot = true;
			} else {
				mTopFreeDisplayFoot = false;
			}
		}

	}


	@Override
	protected void onDestroy() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		super.onDestroy();
	}

	// 自定义零件对话框
	private void mydialog() {
		if (dialog == null) {
			dialog = new Dialog(this, R.style.DialogStyle);
		}
		final View layout = LayoutInflater.from(context).inflate(
				R.layout.dag_custompart, null);

		Button dialogOk = (Button) layout.findViewById(R.id.dialog_ok);
		Button dialogCancle = (Button) layout.findViewById(R.id.dialog_cancle);
		diaPrice = (TextView) layout.findViewById(R.id.eval_part_loss_price);

		diaPrice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showInputDialog(diaPrice, "定损单价", "定损单价");
			}
		});
		diaNum = (TextView) layout.findViewById(R.id.eval_part_loss_count);
		diaNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showInputDialog(diaNum, "定损数量", "定损数量");
			}
		});
		diaSum = (TextView) layout.findViewById(R.id.eval_part_loss_sum);
		diaCustomName = (EditText) layout.findViewById(R.id.lj_dia_customName);
		final EditText originPartNo = (EditText) layout
				.findViewById(R.id.lj_dia_original); // 零件号
		final TextView bz = (TextView) layout.findViewById(R.id.lj_dia_mark); // 备注
		String remark = bz.getText().toString();
		if (remark != null && remark.length() > 15) {
			remark = remark.substring(0, 15) + "...";
		}
		bz.setText(remark);
		bz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textContentDialog(bz, "备注信息", bz.getText().toString());
			}
		});

		diaPrice.setText("0.0");
		diaNum.setText("1");

		final EditText rem = (EditText) layout.findViewById(R.id.lj_dia_rem); // 殖值
		rem.setText("0.0");
		final CheckBox cbxIfRemain = (CheckBox) layout
				.findViewById(R.id.lj_dia_use); // 是否回收

		cbxIfRemain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (isChecked) {
					rem.setText("0.0");
					rem.clearFocus();
				}
			}
		});
		rem.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				cbxIfRemain.setChecked(false);
				return false;
			}
		});

		EvalPartWatcher partTextWatcher = new EvalPartWatcher(diaPrice, diaNum,
				diaSum, null);
		diaPrice.addTextChangedListener(partTextWatcher);
		diaNum.addTextChangedListener(partTextWatcher);

		diaPrice.setOnFocusChangeListener(new DefaultIntegerValueListener("1"));
		diaNum.setOnFocusChangeListener(new DefaultDoubleValueListener("0"));

		dialogOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String price = diaPrice.getText().toString().trim();
				String num = diaNum.getText().toString().trim();
				String ljmc = diaCustomName.getText().toString().trim();
				if (ljmc.equals("")) {
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
				}

				else {
					if (evalPartAction.getExistsEvalFits(evalId, ljmc)) {
						try {
							toast("重复添加失败");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {

						EditText rem = (EditText) layout
								.findViewById(R.id.lj_dia_rem); // 殖值
						CheckBox use = (CheckBox) layout
								.findViewById(R.id.lj_dia_use); // 是否回收

						String strCz = rem.getText().toString();
						double jg = Double.valueOf(price);
						int sl = Integer.valueOf(num);
						double Sum = jg * sl;
						diaSum.setText(String.valueOf(Sum));
						double cz = 0;
						if (!strCz.equals("")) {
							cz = Double.valueOf(strCz);
						}

						EvalPartInfo partInfo = new EvalPartInfo();
						partInfo.setEvalId(Long.valueOf(evalId));
						// 自动生成唯一的PartId - 20130220 zwb 理赔系统中需要这个字段
						partInfo.setPartId(UUID.randomUUID().toString()
								.replaceAll("-", ""));

						partInfo.setPartStandard(ljmc);
						partInfo.setOriginalId(originPartNo.getText()
								.toString());
						partInfo.setOriginalName(ljmc);
						partInfo.setSelfConfigFlag("1");
						partInfo.setLossPrice(Double.valueOf(price));
						partInfo.setLossCount(Integer.valueOf(num));
						partInfo.setSumPrice(Sum);
						partInfo.setRemnant(cz);
						partInfo.setOldDetail("1");
						partInfo.setGoodListId("");
						partInfo.setHuishouFlag("0");
						partInfo.setIfRemain(use.isChecked() ? "1"
								: "0");
						String repairRemark = "";
						if (bz.getTag() != null) {
							repairRemark = bz.getTag().toString();
						}
						partInfo.setRemark(repairRemark);
						partInfo.setChgRefPrice(0D);
						partInfo.setChgLocPrice(0D);
						partInfo.setPartStandardCode("999999");
						partInfo.setLocalFlag("0");
						partInfo.setCareState("0");// 添加换件时初始化不关注
						String isInsert = check(partInfo);
						if (isInsert.equals("1")) {
							evalPartAction.insertEvalFits(partInfo);
							Toast.makeText(EvalPartActivity.this, "添加成功", Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						} else {
							toast(isInsert);
						}
						// finish();
					}
				}
			}

		});
		dialogCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.setContentView(layout);
		Window window = dialog.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		Display dp = getWindowManager().getDefaultDisplay();
		lp.width = dp.getWidth() * 5 / 6;
		dialog.show();
	}

	private Dialog dialog;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.part_ljfz:// 选择零件组
				Intent intent = new Intent(context, PartGroupSelectActivity.class);
				intent.putExtra("vehicelId", vehCertainId);
				startActivityForResult(intent, 1);
				// }
				break;
			case R.id.part_btn_pz:
				showPzDialog();
				break;
			case R.id.part_btn_ljmc:
				limcDialog();
				break;
			case R.id.part_btn_cy:
				new AutoPartSeachTask().execute();
				break;
			case R.id.menu_title_back:
				finish();
				break;
			case R.id.part_btn_zdy:// 自定义零件组
				mydialog();
				break;
			case R.id.eval_btn_outLine:// 批量增加零件
				batchAddPart();
				break;
			case R.id.part_btn_gjz:// 通过关键字查找零件
				selectPartByKeyword();
				break;
			default:
				break;
		}
	}

	//
	// /**
	// * 批量保存零件
	// */
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
						partInfo.setEvalId(Long.valueOf(evalId));
						// 自动生成唯一的PartId - 20130220 zwb 理赔系统中需要这个字段
						partInfo.setPartId(UUID.randomUUID().toString()
								.replaceAll("-", ""));

						partInfo.setSelfConfigFlag("0");
						partInfo.setIfRemain("0");
						partInfo.setCareState("0");// 添加换件时初始化不关注
						partInfo.setOldDetail("1");
						partInfo.setGoodListId("");
						partInfo.setHuishouFlag("0");
						String isInsert = check(partInfo);

						if (isInsert.equals("1")) {
							evalPartAction.insertEvalFits(partInfo);
							toast("添加成功");
						} else {
							toast(isInsert);
						}
					}
				}

			}

			if (j > 0) {// 标志有重复添加失败的，
				toast("重复添加失败数量为" + j);
			}
		} else {
			toast("请先选择零件");
		}
	}

	private String check(EvalPartInfo partInfo) {
		return "1";
	}

	private void selectPartByKeyword() {
		keyword = inflater.inflate(R.layout.lj_eval_keyword, null);
		mPopupWindow = new PopupWindow(keyword, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
//		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
//				R.drawable.rounded_corners_pop));
		ImageView closeImg = (ImageView) keyword.findViewById(R.id.close_img);
		// 初始化按钮
		for (int i = 1; i <= 40; i++) {
			int id = getResources().getIdentifier("lj_key" + i, "id",
					getPackageName());
			Button keyBtn = (Button) keyword.findViewById(id);
			keyPart = keyBtn.getText().toString();
			keyBtn.setOnClickListener(new KeyBtnClickListener(keyPart));
		}
		closeImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mPopupWindow.dismiss();
			}
		});
		mPopupWindow.showAtLocation(keyword.findViewById(R.id.TableLayout01),
				Gravity.CENTER | Gravity.TOP, 0, 2000);
	}


	protected void showInputDialog(TextView text, String name, String title) {
		// TODO Auto-generated method stub
		inputDialog = new Dialog(this, R.style.DialogStyle);
		View v = LayoutInflater.from(context).inflate(
				R.layout.pop_keyboard_view, null);
		Button closeImg = (Button) v.findViewById(R.id.close_btn);
		closeImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inputDialog.dismiss();
			}
		});
		inputDialog.setContentView(v);
		OnClickListener l = new OnClickListenerNumber(text);
		TextView tTitle = (TextView) v.findViewById(R.id.dialog_tilte);
		tTitle.setText(title);

		ed = (EditText) v.findViewById(R.id.edit_input_dialog);

		Class<EditText> cls = EditText.class;
		Method method;
		try {
			method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
			method.setAccessible(true);
			method.invoke(ed, false);
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
			method.setAccessible(true);
			method.invoke(ed, false);
		} catch (Exception e) {
			// TODO: handle exception
		}

		ed.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		ed.setText(String.valueOf(text.getText().toString()));
		Button button1 = (Button) v.findViewById(R.id.keyboard_button1);
		Button button2 = (Button) v.findViewById(R.id.keyboard_button2);
		Button button3 = (Button) v.findViewById(R.id.keyboard_button3);
		Button button4 = (Button) v.findViewById(R.id.keyboard_button4);
		Button button5 = (Button) v.findViewById(R.id.keyboard_button5);
		Button button6 = (Button) v.findViewById(R.id.keyboard_button6);
		Button button7 = (Button) v.findViewById(R.id.keyboard_button7);
		Button button8 = (Button) v.findViewById(R.id.keyboard_button8);
		Button button9 = (Button) v.findViewById(R.id.keyboard_button9);
		Button button0 = (Button) v.findViewById(R.id.keyboard_button0);
		Button buttondian = (Button) v.findViewById(R.id.keyboard_dian);
		Button buttonfu = (Button) v.findViewById(R.id.keyboard_button_);
		Button buttonOk = (Button) v.findViewById(R.id.keyboard_ok);
		Button buttonDelete = (Button) v.findViewById(R.id.keyboard_delete);
		Button buttonLeft = (Button) v.findViewById(R.id.keyboard_left);
		Button buttonright = (Button) v.findViewById(R.id.keyboard_right);

		buttondian.setOnClickListener(l);
		buttonfu.setOnClickListener(l);
		button1.setOnClickListener(l);
		button2.setOnClickListener(l);
		button3.setOnClickListener(l);
		button4.setOnClickListener(l);
		button5.setOnClickListener(l);
		button6.setOnClickListener(l);
		button7.setOnClickListener(l);
		button8.setOnClickListener(l);
		button9.setOnClickListener(l);
		button0.setOnClickListener(l);
		buttonOk.setOnClickListener(l);
		buttonDelete.setOnClickListener(l);
		buttonLeft.setOnClickListener(l);
		buttonright.setOnClickListener(l);

		Window window = inputDialog.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		Display dp = getWindowManager().getDefaultDisplay();
		lp.width = dp.getWidth() * 5 / 6;
		window.setAttributes(lp);
		inputDialog.show();

	}

	private class OnClickListenerNumber implements
			OnClickListener {

		TextView t = new TextView(context);

		public OnClickListenerNumber(TextView text) {
			t = text;
		}

		@Override
		public void onClick(View v) {
			String str = ed.getText().toString();
			int iSelection = ed.getSelectionEnd();
			int iLen = str.length();
			Editable editable = ed.getText();
			int start = ed.getSelectionStart();
			switch (v.getId()) {
				case R.id.keyboard_delete:
					if (editable != null && editable.length() > 0 && iSelection > 0) {
						editable.delete(iSelection - 1, iSelection);
					}
					break;
				case R.id.keyboard_ok:
					inputNum = ed.getText().toString();

					inputDialog.dismiss();
					t.setText(inputNum);
					// ed.setSelection(ed.getText().length());
					break;
				case R.id.keyboard_button1:
					if (iSelection == iLen) {
						ed.append("1");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "1"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button2:
					if (iSelection == iLen) {
						ed.append("2");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "2"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button3:
					if (iSelection == iLen) {
						ed.append("3");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "3"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button4:
					if (iSelection == iLen) {
						ed.append("4");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "4"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button5:
					if (iSelection == iLen) {
						ed.append("5");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "5"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button6:
					if (iSelection == iLen) {
						ed.append("6");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "6"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button7:
					if (iSelection == iLen) {
						ed.append("7");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "7"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button8:
					if (iSelection == iLen) {
						ed.append("8");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "8"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button9:
					if (iSelection == iLen) {
						ed.append("9");
					} else {
						ed.setText("");
						ed.setText(str.substring(0, iSelection) + "9"
								+ str.substring(iSelection,

								iLen));
						ed.setSelection(iSelection + 1);
					}
					break;
				case R.id.keyboard_button0:
					if (iSelection != 0) {
						if (iSelection == iLen) {
							ed.append("0");
						} else {
							ed.setText("");
							ed.setText(str.substring(0, iSelection) + "0"
									+ str.substring(iSelection,

									iLen));
							ed.setSelection(iSelection + 1);
						}
					}
					break;
				case R.id.keyboard_button_:
					if (iSelection == 0) {
						Boolean isHas_ = false;
						for (int i = 0; i < str.length(); i++) {
							String a = str.substring(i, i + 1);
							if (a.equals("-")) {
								isHas_ = true;
							}
						}
						if (!isHas_) {
							ed.setText("");
							ed.setText(str.substring(0, iSelection)

									+ "-" + str.substring

									(iSelection, iLen));
							ed.setSelection(iSelection + 1);
						}
					}
					break;
				case R.id.keyboard_dian:
					if (iSelection != 0) {
						Boolean isHasdian = false;
						for (int i = 0; i < str.length(); i++) {
							String a = str.substring(i, i + 1);
							if (a.equals(".")) {
								isHasdian = true;
							}
						}
						if (!isHasdian) {
							if (!str.substring(iSelection - 1,

									iSelection).equals("-")) {
								ed.setText("");
								ed.setText(str.substring(0,

										iSelection) + "." + str.substring

										(iSelection, iLen));
								ed.setSelection(iSelection + 1);
							}
						}
					}
					break;
				case R.id.keyboard_left:
					if (start > 0) {
						ed.setSelection(start - 1);
					}
					break;
				case R.id.keyboard_right:
					if (start < ed.length()) {
						ed.setSelection(start + 1);
					}
					break;
				default:
					break;
			}

		}
	}

	// 换件备注对话框
	private void textContentDialog(final TextView text, String title,
								   String content) {
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.remark_dialog, null);
		TextView textTitle = (TextView) layout.findViewById(R.id.dialog_tilte);
		textTitle.setText(title);
		Button btnOk = (Button) layout.findViewById(R.id.btn_ok);
		Button closeImg = (Button) layout.findViewById(R.id.dialog_giveUp);
		final EditText txtRemark = (EditText) layout
				.findViewById(R.id.remark_dia);
		txtRemark.setText(content);

		closeImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String s = "";
				inputMark = txtRemark.getText().toString();
				if (inputMark != null && inputMark.length() > 15) {
					s = inputMark.substring(0, 15) + "...";
				}
				if (s.equals("")) {
					text.setText(inputMark);
				} else {
					text.setText(s);
				}
				text.setTag(inputMark);
				dialog.dismiss();
			}
		});
		dialog.setContentView(layout);
		Window window = dialog.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		Display dp = getWindowManager().getDefaultDisplay();
		lp.width = dp.getWidth() * 5 / 6;
		window.setAttributes(lp);
		dialog.show();
	}

	/**
	 * 自动计算费用
	 *
	 * @author Administrator
	 *
	 */
	private class EvalPartWatcher extends SimpleTextWatcher {

		TextView lossPrice;
		TextView lossSum;
		TextView lossCount;
		private EvalPartInfo partInfo;

		public EvalPartWatcher(TextView tv1, TextView tvCount, TextView result,
							   EvalPartInfo partInfo) {
			this.lossPrice = tv1;
			this.lossSum = result;
			this.lossCount = tvCount;
			this.partInfo = partInfo;
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
								  int count) {
			double lossPrice = Double
					.parseDouble(diaPrice.getText().toString());
			int lossCount = Integer.parseInt(diaNum.getText().toString());
			double lossSum = MathUtil.setScale(lossPrice * lossCount);
			diaSum.setText(String.valueOf(lossSum));
			if (this.partInfo != null) {
				partInfo.setLossPrice(lossPrice);
				partInfo.setLossCount(lossCount);
				partInfo.setSumPrice(MathUtil.setScale(lossSum));
			}

		}
	}

}