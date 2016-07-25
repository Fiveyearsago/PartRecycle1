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

	private boolean seled = true; // �ؼ��۸񷽰�ѡ��Ƿ񱣴�

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
	private TextView pageNo; // ����ҳ��,ͬʱ��ѯ���ͼ��������ݴ�
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
	private boolean[] chkb; // ��¼checkboxѡ������

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
		
		//����ѡ���ѡ��Ŀռ�
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

		
		// ������
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
	 * ��ȡ�������
	 * 
	 * @return
	 */
	private boolean readArgs() {
		// ������Ϣ
		evalId = getIntent().getLongExtra("evalId", -1);
		if (evalId == -1) {
			toast("û�л�ȡ���������evalId");
			finish();
			return false;
		}
		// ����Id
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
			// �۸񷽰�
			priceTypeSp = (Spinner) findViewById(R.id.part_spnJgfa);
			priceTypeSp.setAdapter(StaticCode
					.getAdapter(context, priceTypeList));
			// // ��ѡģʽ
			spnInsuranceTerm = (Spinner) findViewById(R.id.eval_insurance_term);
			InsuranceItemAdapter adapter = new InsuranceItemAdapter();
			spnInsuranceTerm.setAdapter(adapter);

			// ʹ���ѱ���ļ۸񷽰�,���һ�����Ҳû�в���д��
			if (evalPartAction.getListEvalPart(evalId).size() > 0) {
				priceTypeSp.setSelection(StaticCode.getIndex(priceTypeList,
						priceCode));
				seled = false;
			}
			if (seled) {
				priceTypeSp.setSelection(share.getEvalJgfaPosition());
			}

			// ����ǵ�һ�۸���Դ,��ô����Ѿ�ѡ�������,�������޸ļ۸���Դ
			if (EvalPartSettings.SINGLE_PRICE_SOURCE) {
				priceTypeSp.setEnabled(seled);// ������б���ļ۸񷽰��������ٸ���
			}

			partGroupBtn = (Button) findViewById(R.id.part_ljfz);
			pageNo = (TextView) findViewById(R.id.pageNo);
			pageNo.setText("1");
			partNameBtn = (Button) findViewById(R.id.part_btn_ljmc);

			// ����ײ�̶Ȳ�ѯ
			btnPz = (Button) findViewById(R.id.part_btn_pz);
			btn_back = (Button) findViewById(R.id.menu_title_back);
			btn_back.setText(titleFlag);
			// ��ѯ��ȡ������� ��Ϊ�ؼ��ֲ�ѯ���õ�������
			keywordBtn = (Button) findViewById(R.id.part_btn_gjz);
			// �����Զ������
			defineBtn = (ImageButton) findViewById(R.id.part_btn_zdy);
			// ��������
			batchAddBtn = (ImageButton) findViewById(R.id.part_btn_add);
			bindView();
		} else {
			showDialog("��֯����û�����ü۸񷽰���˳���û�������");
			// ���ص���ҳ��ȥ
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("�˳��޷���ѡ���");
			dialog.setMessage("��֯����û�����ü۸񷽰���˳���û�������");
			dialog.setPositiveButton("ȷ��",
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
		// ��ѡģʽ
		// selectionModeList = new ArrayList<SpinnerItem>();
		// selectionModeList.add(new SpinnerItem("1", "ϵͳ��ѡ"));
		// selectionModeList.add(new SpinnerItem("2", "��׼��ѡ"));
		// selectionModeList.add(new SpinnerItem("3", "ͼ�ε�ѡ"));

		TextView titlename = (TextView) findViewById(R.id.menu_title_name);
		titlename.setText("����");

		Button freshButton = (Button) findViewById(R.id.menu_title_flash);
		freshButton.setText("��׼��ѡ");
		freshButton.setOnClickListener(this);
		priceTypeList = (ArrayList<SpinnerItem>) share.getJgfa();// �۸񷽰��б�

		Intent it = this.getIntent();
		titleFlag = it.getStringExtra("title_flag");

		pageInfo = it.getStringExtra("PageInfo"); // ҳ��#����#����#�۸񷽰���λ��
		if (pageInfo != null) { // ��ҳ����
			pageNo.setText(pageInfo);
			pageOperation();
		} else {

			String strLj = it.getStringExtra("strLj"); // ��������ƺ�ID
			if (strLj != null) {
				String[] ljz = strLj.split(";");
				ljzName = ljz[0];
				ljzId = ljz[1];
			}

			// ����������ID���۸񷽰�ID����ֱ�ӽ��в�ѯ
			// TODO ��δ��벻�ᱻִ��
			if (hasJgfaLjId() && ljzId != null) {

				new AsyncTask<Void, Void, Void>() {
					@Override
					protected void onPreExecute() {

						progressDialog = ProgressDialog.show(context, "���Ե�",
								"���ڲ�ѯ������Ϣ,���Ժ�!");
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
				String strLj = datax.getStringExtra("strLj"); // ��������ƺ�ID
				if (strLj != null) {
					String[] ljz = strLj.split(";");
					ljzName = ljz[0];
					ljzId = ljz[1];
				}

				// ����������ID���۸񷽰�ID����ֱ�ӽ��в�ѯ
				if (hasJgfaLjId() && ljzId != null) {

					new AsyncTask<Void, Void, Void>() {
						@Override
						public void onPreExecute() {
							progressDialog = ProgressDialog.show(context,
									"���Ե�", "���ڲ�ѯ������Ϣ,���Ժ�!");
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
	 * ���������
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
	 * ����ײ��ѯ�Ի���
	 */
	protected void showPzDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.lj_eval_pzh, null);
		dialog.setTitle("��ײ��λ����ײ�̶Ȳ�ѯ");
		dialog.setView(layout);
		pzcd = (Spinner) layout.findViewById(R.id.lj_pzcd);
		pzbw = (Spinner) layout.findViewById(R.id.lj_pzbw);

		pzcd.setAdapter(StaticCode.getAdapter(context, StaticCode.pzcdList));
		pzbw.setAdapter(StaticCode.getAdapter(context, StaticCode.pzbwList));

		dialog.setPositiveButton("��ѯ", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				pzcdStr = ((SpinnerItem) pzcd.getSelectedItem()).getID();
				pzbwStr = ((SpinnerItem) pzbw.getSelectedItem()).getID();

				new AsyncTask<Void, Void, Void>() {
					@Override
					protected void onPreExecute() {
						progressDialog = ProgressDialog.show(context, "���Ե�",
								"���ڲ�ѯ�����Ϣ,���Ժ�!");
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
		dialog.setNegativeButton("ȡ��", null);
		dialog.create().show();
	}

	private void pageOperation() {
		share.saveEvalJgfaPosition(priceTypeSp.getSelectedItemPosition());
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				progressDialog = ProgressDialog.show(context, "���Ե�",
						"���ڲ�ѯ������Ϣ,���Ժ�!");
			}

			@Override
			protected Void doInBackground(Void... params) {
				// ��ѯ������Ϣ
				SpinnerItem jgfa = (SpinnerItem) priceTypeSp.getSelectedItem();
				String jgfaId = jgfa.getID();
				Response response = null;
				// ҳ��#����#����
				String[] page = pageNo.getText().toString().split("#");
				if (page[1] != null && page[1].equals("1")) { // ����鷽ʽ
					response = ServerApiManager.getJtljDataB(vehCertainId,
							jgfaId, page[2], page[0],"1");
				}
				if (page[1] != null && page[1].equals("2")) { // �ؼ��ַ�ʽ
					response = ServerApiManager.getJtljDataB(vehCertainId,
							jgfaId, page[2], null, page[0],"1");
				}
				if (page[1] != null && page[1].equals("3")) { // ������Ƽ�ԭ������ŷ�ʽ
					if (page.length == 3) {
						// ��û������ԭ�������ʱ
						response = ServerApiManager.getJtljDataB(vehCertainId,
								jgfaId, page[2], "", page[0],"1");
					} else {
						response = ServerApiManager.getJtljDataB(vehCertainId,
								jgfaId, page[2], page[3], page[0],"1");
					}
				}
				if (page[1] != null && page[1].equals("4")) { // ��ײ�̶�����ײ��ʽ
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
						progressDialog = ProgressDialog.show(context, "���Ե�",
								"���ڲ�ѯ������Ϣ,���Ժ�!");
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

	// ������Ƽ�ԭ������Ų�ѯ
	private void limcDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.lj_eval_ljmc, null);
		dialog.setTitle("������Ƽ�ԭ������Ų�ѯ");
		dialog.setView(layout);
		ycljh = (EditText) layout.findViewById(R.id.lj_key_ycljh);
		ljmc = (EditText) layout.findViewById(R.id.lj_key_ljmc);

		dialog.setPositiveButton("��ѯ", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (hasJgfa()) {

					new AsyncTask<Void, Void, Void>() {
						@Override
						protected void onPreExecute() {
							progressDialog = ProgressDialog.show(context,
									"���Ե�", "���ڲ�ѯ������Ϣ,���Ժ�!");
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
		dialog.setNegativeButton("ȡ��", null);
		dialog.create().show();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGETYPE_01:
				// ˢ��UI����ʾ���ݣ����رս�����
				progressDialog.dismiss(); // �رս�����
				view.setVisibility(View.VISIBLE);
				showViewItem();
				break;
			case MESSAGETYPE_02:
				// ˢ��UI����ʾ���ݣ����رս�����
				progressDialog.dismiss(); // �رս�����
				view.setVisibility(View.VISIBLE);
				showViewItem();
				String key = (String) message.obj;
				pageNo.setText("1#2#" + key);
				break;
			case MESSAGETYPE_03:
				// ˢ��UI����ʾ���ݣ����رս�����
				progressDialog.dismiss(); // �رս�����
				view.setVisibility(View.VISIBLE);
				showViewItem();
				pageNo.setText("1#3#" + (cxLjmc == null ? " " : cxLjmc) + "#"
						+ (cxYcljh == null ? " " : cxYcljh));
				break;
			case MESSAGETYPE_04:
				// ˢ��UI����ʾ���ݣ����رս�����
				progressDialog.dismiss(); // �رս�����
				view.setVisibility(View.VISIBLE);
				showViewItem();
				pageNo.setText("1#4#" + pzcdStr + "#" + pzbwStr);
				break;
			case MESSAGETYPE_05:
				// ˢ��UI����ʾ���ݣ����رս�����
				progressDialog.dismiss(); // �رս�����
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
				showDialog("��ѡ��۸񷽰���");
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
			showDialog("��ѡ��۸񷽰���");
		}
		return flag;
	}

	/**
	 * ����б�������
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

			// �������
			TextView tvname = (TextView) convertView
					.findViewById(R.id.list_abl1);
			tvname.setText(info.getPartStandard());
			// ϵͳ�۸�
			TextView tvxtjg = (TextView) convertView
					.findViewById(R.id.list_abl2);
			tvxtjg.setText(String.valueOf(info.getChgRefPrice() == null ? "0"
					: info.getChgRefPrice()));
			// ���ؼ۸�
			TextView tvbdjg = (TextView) convertView
					.findViewById(R.id.list_abl3);
			tvbdjg.setText(String.valueOf(info.getChgLocPrice() == null ? "0"
					: info.getChgLocPrice()));
			// ����۸�-������ڱ��ؼ۸�,��ȡ���ؼ۸�;��������ڱ��ؼ۸�,�ж��Ƿ����ϵͳ�۸�.�������ϵͳ�۸�,��ȡϵͳ�۸�,����Ĭ�ϼ۸�Ϊ0
			TextView tvLossPrice = (TextView) convertView
					.findViewById(R.id.eval_part_loss_price);
			tvLossPrice.setText(String.valueOf(info.getLossPrice()));
			tvLossPrice
					.setOnFocusChangeListener(new DefaultDoubleValueListener(
							"0"));
			// ����
			TextView tvLossCount = (TextView) convertView
					.findViewById(R.id.eval_part_loss_count);
			tvLossCount
					.setOnFocusChangeListener(new DefaultIntegerValueListener(
							"1"));
			// ��ֵ
			TextView tvRemant = (TextView) convertView
					.findViewById(R.id.eval_part_remant);
			tvRemant.setOnFocusChangeListener(new DefaultDoubleValueListener(
					"0"));

			// �ı�����
			tvLossCount.addTextChangedListener(evalPartChangeWatcher);
			tvLossPrice.addTextChangedListener(evalPartChangeWatcher);
			tvRemant.addTextChangedListener(evalPartChangeWatcher);

			// С��
			TextView tvLossSum = (TextView) convertView
					.findViewById(R.id.eval_part_loss_sum);
			tvLossSum.setText(String.valueOf(info.getSumPrice()));
			// ����ѡ���¼�
			CheckBox checkBox = (CheckBox) convertView
					.findViewById(R.id.list_ablbox);
			// ��ʾ��������
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
//			// ���������ձ��б�
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
			// �ı��ֵ
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

			// �����Ի�����ӵ�������
			partListLv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					EvalPartInfo evalP = evalPartList.get(position);
					partDialog(evalP);
				}
			});
			// seletedPartData = new ArrayList<HashMap<String, Object>>(); //
			// �����ǵ�����л�����ѡcheckBoxʱ�����������ݴ浽�˼�¼��
		} else {
			showDialog("�ü۸񷽰����������û�ж�Ӧ���������ȷ�ϲ�ѯ������");
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
		dialog.setNegativeButton("ȷ��", null);
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

	// �Զ�������Ի���
	private AlertDialog.Builder mydialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.dag_custompart, null);
		dialog.setTitle("�Զ���������");
		dialog.setView(layout);
		diaPrice = (EditText) layout.findViewById(R.id.eval_part_loss_price);
		diaNum = (EditText) layout.findViewById(R.id.eval_part_loss_count);
		diaCustomName = (EditText) layout.findViewById(R.id.lj_dia_customName);
		diaPrice.setText("0.0");
		diaNum.setText("1");
		dialog.setNegativeButton("ȡ��", mOnDialogClickListener)
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int arg1) {
						String price = diaPrice.getText().toString().trim();
						String num = diaNum.getText().toString().trim();
						String ljmc = diaCustomName.getText().toString().trim();
						if (ljmc == null || ljmc.equals("")) {
							// Toast.makeText(context, "�Զ���������Ʋ���Ϊ�գ�",
							// 1500).show();
							diaCustomName.setError("�Զ���������Ʋ���Ϊ�գ�");
							// �����������ý����ܹر�dialog
							try {
								Field field = dialog.getClass().getSuperclass()
										.getDeclaredField("mShowing");
								field.setAccessible(true);
								field.set(dialog, false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (share.getFloatByStr(price) <= 0) {
							diaPrice.setError("�۸���Ϊ��");
							diaPrice.requestFocus();
							diaPrice.setFocusable(true);
							// �����������ý����ܹر�dialog
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
									toast("�ظ����ʧ��");
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								EditText originPartNo = (EditText) layout
										.findViewById(R.id.lj_dia_original); // �����
								EditText rem = (EditText) layout
										.findViewById(R.id.lj_dia_rem); // ֳֵ
								CheckBox use = (CheckBox) layout
										.findViewById(R.id.lj_dia_use); // �Ƿ����
								EditText bz = (EditText) layout
										.findViewById(R.id.lj_dia_mark); // ��ע
								String strCz = rem.getText().toString();
								float jg = share.getFloatByStr(price);
								float sl = share.getFloatByStr(num);

								// TODO �����ֵ
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
								// TODO �����ֵ
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
			value = "�޶���ϵͳ���ΨһID�����ʧ��";
		}
		// if(partInfo.getSerialNo().equals("")&&partInfo.getSerialNo()==null){
		// value = "����������Ψһ��ţ����ʧ��";
		// }
		// if(partInfo.getOriginalName()==null&&partInfo.getOriginalName().equals("")){
		// partInfo.setOriginalName(partInfo.getPartStandard());
		// value = "�������ԭ�����ƣ����ʧ��";
		// }
		if (partInfo.getPartStandard().equals("")
				&& partInfo.getPartStandard() == null) {
			value = "�������׼���ƣ����ʧ��";
		}
		if (partInfo.getLossPrice() == 0d) {
			value = "�޶���۸����ʧ��";
		}
		// if(partInfo.getRepairSitePrice()==0d){
		// value = "�������۸����ʧ��";
		// }
		if (partInfo.getLossCount() == 0d) {
			value = "�޻������������ʧ��";
		}
		if (partInfo.getSumPrice() == 0d) {
			value = "�޻������С�ƣ����ʧ��";
		}
		if (partInfo.getSelfConfigFlag().equals("")
				&& partInfo.getSelfConfigFlag() == null) {
			value = "���Զ��������ǣ����ʧ��";
		}
		if (partInfo.getChgCompSetCode().equals("")
				&& partInfo.getChgCompSetCode() == null) {
			value = "�޼۸񷽰����룬���ʧ��";
		}
		if (partInfo.getChgCompSetName().equals("")
				&& partInfo.getChgCompSetName() == null) {
			value = "�޼۸񷽰����ƣ����ʧ��";
		}
		if (partInfo.getIfRemain().equals("") && partInfo.getIfRemain() == null) {
			value = "���Ƿ�������գ����ʧ��";
		}
		if (partInfo.getRemnant().equals("") && partInfo.getRemnant() == null) {
			value = "�޲�ֵ�����ʧ��";
		}
		if (partInfo.getInsureTerm().equals("")
				&& partInfo.getInsureTerm() == null) {
			value = "���ձ����ʧ��";
		}
		if (partInfo.getInsureTermCode().equals("")
				&& partInfo.getInsureTermCode() == null) {
			value = "���ձ���룬���ʧ��";
		}
		if (MathUtil.setScale(partInfo.getRemnant())>MathUtil.setScale(partInfo.getLossPrice())) {
			value = "��ֵ���ڶ���۸����ʧ��";
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
		case R.id.part_ljfz:// ѡ�������
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
			intentEval.putExtra("title_flag", "������Ϣ");
			intentEval.putExtra("evalId", evalId);
			intentEval.putExtra("vehCertainId", vehCertainId);
			startActivity(intentEval);
			finish();
			break;
		case R.id.part_btn_zdy:// �Զ��������
			popUpDialog(mydialog());
			break;
		case R.id.part_btn_add:// �����������
			batchAddPart();
			break;
		case R.id.part_btn_gjz:// ͨ���ؼ��ֲ������
			selectPartByKeyword();
			break;
		default:
			break;
		}
	}

	/**
	 * �����������
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

			if (j > 0) {// ��־���ظ����ʧ�ܵģ�
				Toast.makeText(context, "�ظ����ʧ������Ϊ" + j, Toast.LENGTH_SHORT)
						.show();
			}
			finish();
		} else {
			Toast.makeText(context, "����ѡ�����", Toast.LENGTH_SHORT).show();
		}
	}

	private void selectPartByKeyword() {
		keyword = inflater.inflate(R.layout.lj_eval_keyword, null);
		mPopupWindow = new PopupWindow(keyword, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.rounded_corners_pop));
		// ��ʼ����ť
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
	 * �����༭��Ŀ�ı�ļ�����:�������ۡ���������ֵ��ֵ
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
	 * �ձ��б�������
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