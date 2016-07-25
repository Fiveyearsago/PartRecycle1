package com.jy.recycle.ui.eval.dx;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.action.EvalLossInfoAction;
import com.jy.recycle.client.response.EvalLossInfo;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.ui.view.CustomerVehSpinner;
import com.jy.recycle.util.CustomDialog;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.SpinnerItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 
 * @author Administrator ������ʾƷ��
 */
public class BrandDialogActivity extends JyBaseActivity {
	private Context context;
	private SharedData share;
	private String inputMark;

	private int[] brandImg0;
	private int[] brandImg1;

	private String[] brandStr0;
	private String[] brandStr1;

	private String flages = "protone";
	private String titleFlag;
	private String taskNo;
	private String evalId;
	private int width;

	private LinearLayout contentLayout1;
	private ScrollView contentLayout2;
	private LinearLayout layout1,layout2;
	private GridView gridview0;
	private TableLayout custom;
//	private TextView btnAdd;

	private CustomerVehSpinner chex;
	private String carType;
	private CustomDialog carTypeDialog;

	private TextView txtProtone;
	private TextView txtBrands;
	private TextView txtUserDefined;
	private TextView changs;
	private EditText pingp;
	private EditText chexi;
	private EditText cheGroupEditText;
	private EditText cheXingEditText;
	private TextView chekuan;
	private boolean forQuery;
	private String mState,remId="";
	
	private EvalLossInfoAction evalLossInfoAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		share = SharedData.data();
		setContentView(R.layout.chose_brand_layout);
		Intent intent = this.getIntent();
		titleFlag = intent.getStringExtra("title_flag");
		forQuery = intent.getBooleanExtra("forQuery", false);
		evalId = intent.getStringExtra("evalId");
		mState = intent.getStringExtra("state");
		if (!mState.equals("")) {
			remId=intent.getStringExtra("remId");
		}
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		prepareData();
		findView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		flages = "protone";
		txtProtone.setBackgroundResource(R.color.surveyEvalBg);
		txtProtone.setTextColor(getResources().getColor(R.color.blueMainText));
		txtBrands.setBackgroundResource(R.color.color2);
		txtBrands.setTextColor(getResources().getColor(R.color.white));
		txtUserDefined.setBackgroundResource(R.color.color2);
		txtUserDefined.setTextColor(getResources().getColor(R.color.white));
		contentLayout1.removeAllViews();
		contentLayout2.removeAllViews();
		gridview0.setVisibility(View.VISIBLE);
		gridview0.setAdapter(new ImageAdapter(BrandDialogActivity.this,
				brandImg1, brandStr1));
		gridview0.setBackgroundResource(R.color.surveyEvalBg);
		contentLayout1.addView((View) gridview0.getParent());
	}

	private void findView() {
		TextView titlename = (TextView) findViewById(R.id.menu_title_name);
		titlename.setText("ѡ��Ʒ��");
		
		
		final TextView btnLinkType = (TextView) findViewById(R.id.eval_btn_outLine);
//		if (share.getIsLocal()) {
//			btnLinkType.setText("����");
//		} else {
//			btnLinkType.setText("����");
//		}
//		btnLinkType.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (share.getIsLocal()) {
//					share.saveIsLocal(false);
//					btnLinkType.setText("����");
//				} else {
//					share.saveIsLocal(true);
//					btnLinkType.setText("����");
//				}
//			}
//		});

		LayoutInflater vi = (LayoutInflater) getBaseContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		layout1 = (LinearLayout) vi.inflate(R.layout.dx_dag_brandimg, null);
		gridview0 = (GridView) layout1.findViewById(R.id.brand_view0);
		gridview0.setOnItemClickListener(onItemClickListener);

		layout2 = (LinearLayout) vi.inflate(R.layout.dx_custom_dag_brandimg, null);
		contentLayout1 = (LinearLayout) findViewById(R.id.brands_content1);
		contentLayout2 = (ScrollView) findViewById(R.id.brands_content2);
		custom = (TableLayout) layout2.findViewById(R.id.brand_custom);
		txtProtone = (TextView) findViewById(R.id.protone_button);
		txtProtone.setOnClickListener(this);

		txtBrands = (TextView) findViewById(R.id.brands_button);
		txtBrands.setOnClickListener(this);

		Button btnBack = (Button) findViewById(R.id.menu_title_back);
		btnBack.setText(titleFlag);
		btnBack.setOnClickListener(this);

		txtUserDefined = (TextView) findViewById(R.id.user_defined_button);
		txtUserDefined.setOnClickListener(this);

//		TextView btnOk = (TextView)findViewById(R.id.eval_btn_save);
//		btnOk.setOnClickListener(this);
//		if (forQuery) {
//			txtUserDefined.setVisibility(View.GONE);
//		}
	}

	private void prepareData() {
		Resources res = getResources();
		brandStr0 = res.getStringArray(R.array.jkBrandName);
		brandStr1 = res.getStringArray(R.array.gcBrandName);

		brandImg0 = new int[32];
		for (int i = 1; i <= 32; i++) {
			int id = getResources().getIdentifier("jk" + i, "drawable",
					getPackageName());
			brandImg0[i - 1] = id;
		}
		brandImg1 = new int[74];
		for (int i = 1; i <= 74; i++) {
			int id = getResources().getIdentifier("gc" + i, "drawable",
					getPackageName());
			brandImg1[i - 1] = id;
		}
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String brandName = "";
			if ("protone".equals(flages)) {
				brandName = brandStr1[arg2];
			} else {
				brandName = brandStr0[arg2];
			}
			Intent it = new Intent();
			it.putExtra("BrandName", brandName);
			setResult(RESULT_OK, it);
			finish();
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.eval_btn_save: // ѡ��Ʒ��
			String id = ((SpinnerItem)chex.getSelectedItem()).getID();
			String pinPai = pingp.getText().toString().trim();
			String cheXi= chexi.getText().toString().trim();
			if (pinPai == null || pinPai.equals("")) {
				pingp.setError("Ʒ�����Ʋ���Ϊ�գ�");
				pingp.requestFocus();
				pingp.setFocusable(true);
				// �����������ý����ܹر�dialog
				try {
					Field field = dialog.getClass().getSuperclass()
							.getDeclaredField("mShowing");
					field.setAccessible(true);
					field.set(dialog, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (cheXi == null || cheXi.equals("")) {
				chexi.setError("�������Ʋ���Ϊ��");
				chexi.requestFocus();
				chexi.setFocusable(true);
				// �����������ý����ܹر�dialog
				try {
					Field field = dialog.getClass().getSuperclass()
							.getDeclaredField("mShowing");
					field.setAccessible(true);
					field.set(dialog, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (id.equals("0")) {
				SpinnerDialog();
			}else{
//			comfershowDialog("�Ƿ�������ͣ�",
//					"���ѣ������ȷ������ɾ��ȫ��������Ŀ��");
				//ɾ��������Ϣ
				deleteEvalInfo(Long.parseLong(evalId));
				// ���������еĳ�����Ϣ
				updateVehicleInfo();
				finish();
			}
			break;
		case R.id.user_defined_button: // �Զ��峵��
			userDefined();
			break;
		case R.id.menu_title_back:// ����
			finish();
			break;
		case R.id.brands_button: // ����Ʒ��
			brands();
			break;
		case R.id.protone_button:// ����Ʒ��
			protone();
			break;
		}
	}

	/**
	 * ����Ʒ��
	 */
	
	private void protone() {
		flages = "protone";
//		btnAdd.setVisibility(View.GONE);
		txtProtone.setBackgroundResource(R.color.surveyEvalBg);
		txtProtone.setTextColor(getResources().getColor(R.color.blueMainText));
		txtBrands.setBackgroundResource(R.color.color2);
		txtBrands.setTextColor(getResources().getColor(R.color.white));
		txtUserDefined.setBackgroundResource(R.color.color2);
		txtUserDefined.setTextColor(getResources().getColor(R.color.white));
		contentLayout1.removeAllViews();
		contentLayout2.removeAllViews();
		gridview0.setVisibility(View.VISIBLE);
		gridview0.setAdapter(new ImageAdapter(BrandDialogActivity.this,
				brandImg1, brandStr1));
		gridview0.setBackgroundResource(R.color.surveyEvalBg);
		contentLayout1.setVisibility(View.VISIBLE);
		contentLayout2.setVisibility(View.GONE);
		contentLayout1.addView((View) gridview0.getParent());
	}
	/**
	 * ����Ʒ��
	 */
	private void brands() {
		flages = "brands";
//		btnAdd.setVisibility(View.GONE);
		txtBrands.setBackgroundResource(R.color.surveyEvalBg);
		txtBrands.setTextColor(getResources().getColor(R.color.blueMainText));
		txtProtone.setBackgroundResource(R.color.color2);
		txtProtone.setTextColor(getResources().getColor(R.color.white));
		txtUserDefined.setBackgroundResource(R.color.color2);
		txtUserDefined.setTextColor(getResources().getColor(R.color.white));
		contentLayout1.removeAllViews();
		contentLayout2.removeAllViews();
		gridview0.setVisibility(View.VISIBLE);
		gridview0.setAdapter(new ImageAdapter(BrandDialogActivity.this,
				brandImg0, brandStr0));
		gridview0.setBackgroundResource(R.color.surveyEvalBg);
		contentLayout1.setVisibility(View.VISIBLE);
		contentLayout2.setVisibility(View.GONE);
		contentLayout1.addView((View) gridview0.getParent());
	}

	/**
	 * �Զ��峵��
	 */
	private void userDefined() {
		 final Dialog dialog = new Dialog(this, R.style.DialogStyle);
			View layout = LayoutInflater.from(context).inflate(
					R.layout.dx_custom_dag_brandimg, null);
//		btnAdd.setVisibility(View.VISIBLE);
		txtUserDefined.setBackgroundResource(R.color.surveyEvalBg);
		txtUserDefined.setTextColor(getResources().getColor(R.color.blueMainText));
		txtProtone.setBackgroundResource(R.color.color2);
		txtProtone.setTextColor(getResources().getColor(R.color.white));
		txtBrands.setBackgroundResource(R.color.color2);
		txtBrands.setTextColor(getResources().getColor(R.color.white));
		changs = (TextView) layout.findViewById(R.id.bp_changs);
		Button dialog_Ok= (Button) layout.findViewById(R.id.dialog_ok);
		Button dialog_Cancle= (Button) layout.findViewById(R.id.dialog_delete);
		
		changs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				remarkDialog(changs);
			}
		});
		pingp = (EditText) layout.findViewById(R.id.bp_pingp);
		cheGroupEditText = (EditText) layout.findViewById(R.id.bp_VehGroup);
		cheXingEditText = (EditText) layout.findViewById(R.id.bp_VehCertain);
//		pingp.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				remarkDialog(pingp);
//			}
//		});
		chexi = (EditText) layout.findViewById(R.id.bp_VehSeri);
//		chexi.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				remarkDialog(chexi);
//			}
//		});
		chekuan = (TextView) layout2.findViewById(R.id.bp_VehGroup);
		chekuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				remarkDialog(chekuan);
			}
		});
		changs.setInputType(InputType.TYPE_CLASS_TEXT);
		pingp.setInputType(InputType.TYPE_CLASS_TEXT);


	
//		pingp.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				// TODO Auto-generated method stub
//				String value = ((EditText)v).getText().toString();
//				if(value!=null&&!"".equals(value)){
//					((EditText)v).setGravity(Gravity.LEFT);
//				}else{
//					((EditText)v).setGravity(Gravity.CENTER);
//				}
//			}
//		});
		chexi.setInputType(InputType.TYPE_CLASS_TEXT);
//		chexi.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				// TODO Auto-generated method stub
//				String value = ((EditText)v).getText().toString();
//				if(value!=null&&!"".equals(value)){
//					((EditText)v).setGravity(Gravity.LEFT);
//				}else{
//					((EditText)v).setGravity(Gravity.CENTER);
//				}
//			}
//		});
		chekuan.setInputType(InputType.TYPE_CLASS_TEXT);
		chex = (CustomerVehSpinner) layout.findViewById(R.id.bp_chex);
//		chex.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				carType();
//			}
//		});
//		chex = (Spinner) layout2.findViewById(R.id.bp_chex);
		ArrayList<SpinnerItem> clist = new ArrayList<SpinnerItem>();
		clist.add(new SpinnerItem("0", "          ��ѡ"));
		clist.add(new SpinnerItem("C", "��׼�γ�"));
		clist.add(new SpinnerItem("B", "��׼����"));
		clist.add(new SpinnerItem("A", "��׼�ͳ�"));
//		clist.add(new SpinnerItem("D", "��������"));
		chex.setText("��������");
		chex.setList(clist);
		ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(this, android.R.layout.simple_spinner_item,clist);
        chex.setAdapter(adapter);
        
        
//		chex.setAdapter(StaticCode.getAdapter(this, clist));
		contentLayout1.removeAllViews();
		contentLayout2.removeAllViews();

		contentLayout1.setVisibility(View.GONE);
		contentLayout2.setVisibility(View.VISIBLE);
//		contentLayout2.addView((View) custom.getParent());
		
		dialog_Ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String id = ((SpinnerItem)chex.getSelectedItem()).getID();
				String pinPai = pingp.getText().toString().trim();
				String cheXi= chexi.getText().toString().trim();
				String cheGroup = cheGroupEditText.getText().toString().trim();
				String cheXing= cheXingEditText.getText().toString().trim();
				if (pinPai == null || pinPai.equals("")) {
					pingp.setError("Ʒ�����Ʋ���Ϊ�գ�");
					pingp.requestFocus();
					pingp.setFocusable(true);
					// �����������ý����ܹر�dialog
					try {
						Field field = dialog.getClass().getSuperclass()
								.getDeclaredField("mShowing");
						field.setAccessible(true);
						field.set(dialog, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (cheXing == null || cheXing.equals("")) {
					cheXingEditText.setError("�������Ʋ���Ϊ��");
					cheXingEditText.requestFocus();
					cheXingEditText.setFocusable(true);
					// �����������ý����ܹر�dialog
					try {
						Field field = dialog.getClass().getSuperclass()
								.getDeclaredField("mShowing");
						field.setAccessible(true);
						field.set(dialog, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (cheXi == null || cheXi.equals("")) {
					chexi.setError("��ϵ���Ʋ���Ϊ��");
					chexi.requestFocus();
					chexi.setFocusable(true);
					// �����������ý����ܹر�dialog
					try {
						Field field = dialog.getClass().getSuperclass()
								.getDeclaredField("mShowing");
						field.setAccessible(true);
						field.set(dialog, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (cheGroup == null || cheGroup.equals("")) {
					cheGroupEditText.setError("�������Ʋ���Ϊ��");
					cheGroupEditText.requestFocus();
					cheGroupEditText.setFocusable(true);
					// �����������ý����ܹر�dialog
					try {
						Field field = dialog.getClass().getSuperclass()
								.getDeclaredField("mShowing");
						field.setAccessible(true);
						field.set(dialog, false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (id.equals("0")) {
					SpinnerDialog();
				}else{
//				comfershowDialog("�Ƿ�������ͣ�",
//						"���ѣ������ȷ������ɾ��ȫ��������Ŀ��");
					//ɾ���������������
					if (!mState.equals("")) {
						// �����ݴ���߹���״̬��ɾ���������ϵ�����
						//deleteServerPartInfo(evalId, remId);
						evalLossInfoAction = new EvalLossInfoAction(BrandDialogActivity.this);
						evalLossInfoAction.deleteServerPartInfo(Long.parseLong(evalId), remId);
					}
					//ɾ��������Ϣ
					deleteEvalInfo(Long.parseLong(evalId));
					// ���������еĳ�����Ϣ
					updateVehicleInfo();
					finish();
				}
			}
		});
		
		dialog_Cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				protone();
				dialog.dismiss();
			}
		});
		 dialog.setContentView(layout);  
         Window window = dialog.getWindow();
         android.view.WindowManager.LayoutParams lp = window.getAttributes();
         Display dp = getWindowManager().getDefaultDisplay();
         lp.width=dp.getWidth()*5/6;
         window.setAttributes(lp);
          dialog.show();  
	}

	/**
	 * ���³�����Ϣ
	 */
	private void selectedBrand() {
//		taskNo = share.getTaskNo();
//		// �������г�����Ϣչ����
//		String id = "";
//		ClaimEval cs = null;
//		if (taskNo != null && !"".equals(taskNo))
//			cs = es.getTaskByTaskNo(taskNo);
//		if (cs != null)
//			id = String.valueOf(cs._id);
//
//		if (id != null && !id.equals("")) {
//			evalId = id;
//		}
		// ���������еĳ�����Ϣ
//		String cxId = carType;
//		String cxMc = chex.getText().toString();
		
		String cxId = ((SpinnerItem) chex.getSelectedItem()).getID();
		String cxMc = ((SpinnerItem) chex.getSelectedItem()).getValue();
		String chj = changs.getText().toString();
		String pp = pingp.getText().toString();//Ʒ������
		String cx = chexi.getText().toString();//��ϵ����
		String cheXing = cheXingEditText.getText().toString();//��������
		String cheGroup = cheGroupEditText.getText().toString();//��������
		
		
//		ContentValues value = new ContentValues();
//		value.put("VEHICLE_ID", cxId);
//		value.put("VEHICLE_CODE", cxId);
//		value.put("VEHICLE_NAME", cxMc);
//		value.put("BRAND_NAME", pp);
//		value.put("FAMILY_NAME", chj);
//		value.put("GROUP_NAME", chj);
//		if (evalId != null && !"".equals(evalId))
//			es.updateEvalChangCar(evalId, value);
		EvalLossInfo vehicleInfo = new EvalLossInfo();
		vehicleInfo.set_id(Long.parseLong(evalId));
		//������Ϣ
//		vehicleInfo.setVehFactoryCode(mp.get(EvalLossInfoDao.Columns.VEH_FACTORY_CODE));
		vehicleInfo.setVehFactoryName(chj);
		//Ʒ����Ϣ
//		vehicleInfo.setVehBrandCode(mp.get(EvalLossInfoDao.Columns.VEH_BRAND_CODE));
		vehicleInfo.setVehBrandName(pp);
		//��ϵ��Ϣ
//		vehicleInfo.setVehSeriCode(mp.get(EvalLossInfoDao.Columns.VEH_SERI_CODE));
//		vehicleInfo.setVehSeriName(cxMc);cx
		vehicleInfo.setVehSeriName(cheGroup);
		//������Ϣ
//		vehicleInfo.setVehGroupCode(mp.get(EvalLossInfoDao.Columns.VEH_GROUP_CODE));
		vehicleInfo.setVehGroupName(cx);
		//������Ϣ
		vehicleInfo.setVehCertainId(cxId);
		vehicleInfo.setVehCertainCode(UUID.randomUUID().toString().replaceAll("-", ""));   //TODO �������
		vehicleInfo.setVehCertainName(cheXing);
		//����������Ϣ
		vehicleInfo.setVehKindCode(cxId);
		vehicleInfo.setVehKindName(cxMc);
//		vehicleInfo.setVehYearType(mp.get(EvalLossInfoDao.Columns.VEH_YEAR_TYPE));
		vehicleInfo.setSelfConfigFlag("1");
		evalLossInfoAction = new EvalLossInfoAction(this);
		evalLossInfoAction.updateVehicleInfo(vehicleInfo);
	
	}
	/**
	 * ����ȷ�϶Ի���
	 * 
	 * @param title
	 * @param msg
	 */
	private void comfershowDialog(String title, String msg) {
		final Dialog dialog = new Dialog(this,R.style.DialogStyle);
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_style_layout, null);
        TextView titleView=(TextView)view.findViewById(R.id.dialog_tilte);
        Button dialogOk=(Button)view.findViewById(R.id.dialog_ok);
        Button dialogCancle=(Button)view.findViewById(R.id.dialog_cancle);
        dialogOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				//ɾ��������Ϣ
				deleteEvalInfo(Long.parseLong(evalId));
				// ���������еĳ�����Ϣ
				updateVehicleInfo();
				finish();
			}
		});
        
        dialogCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		 dialog.setContentView(view);
	     dialog.show();
	}
	/**
	 * ���³�����Ϣ
	 */
	private void updateVehicleInfo(){
		 selectedBrand();
		 Intent it = new Intent();
		 setResult(RESULT_OK, it);
		 finish();
	}
	/**
	 * ɾ��������Ϣ
	 * 
	 * 
	 */
	private void deleteEvalInfo(Long evalId){
		evalLossInfoAction = new EvalLossInfoAction(this);
		evalLossInfoAction.deleteEvalLossInfo(evalId);
	}

	/**
	 * Ʒ��ͼ��������
	 * 
	 * @author Administrator
	 * 
	 */
	private class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private int img[];
		private String des[];

		public ImageAdapter(Context c, int brandImg[], String brandStr[]) {
			mContext = c;
			img = brandImg;
			des = brandStr;
		}

		public int getCount() {
			return img.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(width / 6,
						width / 6));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//				imageView.setPadding(6, 6, 6, 6);
				imageView.setBackgroundResource(R.color.surveyEvalBg);
				imageView.setContentDescription(des[position]);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(img[position]);
			return imageView;
		}
	}
	
//	private void carType() {
//		carTypeDialog = new CustomDialog(this,R.layout.dialog_car_type,
//				R.style.Dialog);
//
//		TextView tvJiaoche = (TextView) carTypeDialog
//				.findViewById(R.id.tv_jiaoche);
//		TextView tvHuoche = (TextView) carTypeDialog
//				.findViewById(R.id.tv_huoche);
//		TextView tvKeche = (TextView) carTypeDialog
//				.findViewById(R.id.tv_keche);
//		TextView tvQita = (TextView) carTypeDialog
//				.findViewById(R.id.tv_qita);
//
//		OnClickListener listener = new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				switch (v.getId()) {
//				case R.id.tv_jiaoche:
//					carType="C";
//					chex.setText("��׼�γ�");
//					break;
//					
//				case R.id.tv_huoche:
//					carType="B";
//					chex.setText("��׼����");
//					break;
//
//				case R.id.tv_keche:
//					carType="A";
//					chex.setText("��׼�ͳ�");
//					break;
//
//				case R.id.tv_qita:
//					carType="D";
//					chex.setText("��������");
//					break;
//
//				default:
//					break;
//					
//				}
//				carTypeDialog.dismiss();
//			}
//		};
//		tvJiaoche.setOnClickListener(listener);
//		tvHuoche.setOnClickListener(listener);
//		tvKeche.setOnClickListener(listener);
//		tvQita.setOnClickListener(listener);
//		
//		carTypeDialog.show();
//	}
	private void SpinnerDialog(){
		final Dialog dialog = new Dialog(this,R.style.DialogStyle);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.dialog_spinner_must, null);
		TextView dialogTitle=(TextView)layout.findViewById(R.id.dialog_tilte);
		dialogTitle.setText("��ѡ����");
		Button closeImg = (Button)layout.findViewById(R.id.close_ok);
		TextView dialogContent=(TextView)layout.findViewById(R.id.dialog_content);
		dialogContent.setText("��ѡ���ͣ������ܽ�����ӣ�");
		closeImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setContentView(layout); 
		 Window window = dialog.getWindow();
       android.view.WindowManager.LayoutParams lp = window.getAttributes();
       Display dp = getWindowManager().getDefaultDisplay();
       lp.width=dp.getWidth()*5/6;
       window.setAttributes(lp);
		dialog.show();
	}
	
	
	private void remarkDialog(final TextView text){
		final Dialog dialog = new Dialog(this,R.style.DialogStyle);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.remark_dialog, null);
		ImageView closeImg = (ImageView)layout.findViewById(R.id.close_img);
		final EditText txtRemark = (EditText) layout.findViewById(R.id.remark_dia);
		Button btnOk = (Button) layout.findViewById(R.id.btn_ok);
		
		closeImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				inputMark = txtRemark.getText().toString();
				text.setText(inputMark);
			}
		});
		dialog.setContentView(layout); 
		 Window window = dialog.getWindow();
       android.view.WindowManager.LayoutParams lp = window.getAttributes();
       Display dp = getWindowManager().getDefaultDisplay();
       lp.width=dp.getWidth()*5/6;
       window.setAttributes(lp);
		dialog.show();
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
	private Dialog dialog;
}
