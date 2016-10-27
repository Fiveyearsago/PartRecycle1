package com.jy.recycle.ui.eval.dx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.ah.bus.data.Response;
import com.jy.recycle.R;
import com.jy.mobile.dto.EvalLossInfoDTO;
import com.jy.mobile.response.SpAutoVehicleDTO;
import com.jy.mobile.response.SpVehicleListDTO;
import com.jy.recycle.action.EvalLossInfoAction;
import com.jy.recycle.client.request.ActingBrandItem;
import com.jy.recycle.client.response.EvalLossInfo;
import com.jy.recycle.dao.ActingBrandItemDao;
import com.jy.recycle.dao.EvalLossInfoDao;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.util.JsonUtil;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.ValidateUtil;

/**
 * 车辆定损页面，如果整车编码能找到车型，直接列出来，不行则通车品牌来查找
 */
public class EvalVehiclActivity extends JyBaseActivity {
	private Context context;
	private SharedData share;
	private EvalLossInfoAction evalLossInfoAction;

	private List<Map<String, String>> data;

	private String groupId;
	private Long evalId;
	private String newCxid;
	private String oldVehCertainId;
	private String brandName;
	private String titleFlag;

	private int indexOf;
	private boolean forQuery;// 标记是否只查看车型

	private ImageButton btnOk;
	private Button btnBack;
	private Button btnBrand;

	private TextView vehCxid;

	private ProgressDialog progressDialog = null;
	private String actingBrand;
	private Button btnBrandSearche;
	private Button btnVinSearche;
	private Button btnVehId;
	private TextView btnVehVin;
	private EditText vehNameEt;
	private String vehName;
	private static int pageno = 1;
	EvalLossInfo evalLossInfo = null;
	private String mState = "";
	private String remId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eval_vehicle);
		context = this;
		share = SharedData.data();
		evalLossInfoAction = new EvalLossInfoAction(this);

		Intent it = this.getIntent();
		titleFlag = it.getStringExtra("title_flag");
		forQuery = it.getBooleanExtra("forQuery", false);
		mState = it.getStringExtra("state");
		if (!mState.equals("")) {
			remId = it.getStringExtra("remId");
		}
		if (!forQuery) {
			evalId = it.getLongExtra("evalId", -1); // 从首页传的任务号
			if (evalId != null && evalId != -1) {
				share.saveTaskNo(String.valueOf(evalId));
			}
		}
		evalLossInfo = evalLossInfoAction.getEvalLossInfoByEvalId(evalId);
		findViews();
	}

	protected void findViews() {
		TextView titlename = (TextView) findViewById(R.id.menu_title_name);
		titlename.setText("选择车型");
		// findViewById(R.id.menu_title_flash);

		TextView vehBrand = (TextView) findViewById(R.id.veh_vehBrand);
		TextView vehInfo = (TextView) findViewById(R.id.veh_vehInfo);
		// TextView vehCheXing = (TextView) findViewById(R.id.veh_cheXing);
		// 将定损单中车型信息展出来
		EvalLossInfo evalLossInfo = null;
		if (evalId != null) {
			evalLossInfo = evalLossInfoAction.getEvalLossInfoByEvalId(evalId);
			if (evalLossInfo != null)
				oldVehCertainId = evalLossInfo.getVehCertainId();
		}
		if (evalLossInfo != null) {
			String ppmc = evalLossInfo.getVehBrandName();
			String cxmc = evalLossInfo.getVehCertainName();
			// 以下两行在插入定损记录时用到
			vehBrand.setText(ppmc == null ? "" : ppmc);
			vehBrand.setTextColor(getResources().getColor(R.color.blueMainText));
			vehInfo.setText(cxmc == null ? "" : cxmc);
			vehInfo.setTextColor(getResources().getColor(R.color.blueMainText));
		}

		btnOk = (ImageButton) findViewById(R.id.ok_button);
		btnOk.setOnClickListener(this);

		btnBack = (Button) findViewById(R.id.menu_title_back);
		btnBack.setText(titleFlag);
		btnBack.setOnClickListener(this);
		btnBrandSearche = (Button) findViewById(R.id.chex_btn_brand);
		btnBrandSearche.setOnClickListener(this);
		btnVehId = (Button) findViewById(R.id.veh_imgBrand_search);
		btnVehVin = (TextView) findViewById(R.id.veh_imgVin_search);
		btnVehId.setOnClickListener(this);
		btnVehVin.setOnClickListener(this);

		vehNameEt = (EditText) findViewById(R.id.veh_editText_name);
		btnVinSearche = (Button) findViewById(R.id.chex_btn_vin);
		btnVinSearche.setOnClickListener(this);
		btnBrand = (Button) findViewById(R.id.veh_imgBrand);
		btnBrand.setText("选择");
		btnBrand.setTextSize(18);
		btnBrand.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_title_back:// 返回
			finish();
			break;
		case R.id.chex_btn_brand:// 打开品牌
			openBrandDialog();
			break;
		case R.id.veh_imgBrand_search:// 车型型号搜索
			// 每次点击按钮都清空全局变量adapter和data内容
			vehName = vehNameEt.getText().toString().trim().toUpperCase();
			evalLossInfo.setVinNo(vehName);
			if (validate() && ValidateUtil.isNO(vehName)) {
				VINSeachDialog();
			} else if (validate()) {
				data = new ArrayList<Map<String, String>>();
				new GetVehicleListByVehicleNameTask().execute();
			} else {
				Toast.makeText(getApplicationContext(), "请输入名称或VIN码", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.ok_button: // 返回
			finish();
			break;
		}
	}

	/**
	 * 打开品牌对话框
	 */
	private void openBrandDialog() {
		Intent intent = new Intent();
		intent.putExtra("title_flag", "");
		intent.putExtra("forQuery", forQuery);
		intent.putExtra("evalId", String.valueOf(evalId));
		intent.putExtra("state", mState);
		if(!mState.equals("")){
			intent.putExtra("remId", remId);
		}
		intent.setClass(context, BrandDialogActivity.class);
		startActivityForResult(intent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent datax) {
		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case RESULT_OK:
				brandName = datax.getStringExtra("BrandName"); // 零件组名称和ID

				if (brandName != null && !brandName.equals("")) {
					Intent intent = new Intent();
					intent.setClass(context, GroupSelectActivity.class);
					Bundle bundle = new Bundle();
					// 进入车系选择页面
					bundle.putString("BrandName", brandName);
					intent.putExtras(bundle);
					startActivityForResult(intent, 2);

					TextView vehBrand = (TextView) findViewById(R.id.veh_vehBrand);
					vehBrand.setText(brandName);
				} else {
					finish();
				}
			}
			break;
		case 2:
			switch (resultCode) {
			case RESULT_OK:
				groupId = datax.getStringExtra("GroupId"); // 零件组名称和ID
				String groupName = datax.getStringExtra("GroupName"); // 零件组名称和ID

				if (groupName != null && !"".equals(groupName.trim())) {
					TextView vehGroup = (TextView) findViewById(R.id.veh_vehInfo);
					vehGroup.setText(groupName);
					vehGroup.setTextSize(16);

				}
				if (groupId != null && !groupId.equals("")) {
					new GetVehicleListByGroupIdTask().execute();
				}
			}
			break;

		case 3:
			switch (resultCode) {
			case RESULT_OK:
				finish();
			}

		}
		super.onActivityResult(requestCode, resultCode, datax);
	}

	/**
	 * 显示车型列表信息
	 */
	private void displayCxlist() {
		View view = findViewById(R.id.bandlistlayout);
		view.setVisibility(View.VISIBLE);
		ListView vehList = (ListView) findViewById(R.id.veh_vehList);
		if (data != null) {
			SimpleAdapter adapter = new SimpleAdapter(this, data,
					R.layout.vehicle_list,
					new String[] { "Name", "Bsxlx", "Pl" }, new int[] {
							R.id.list_abl1, R.id.list_abl2, R.id.list_abl3 });
			vehList.setAdapter(adapter);
			// 查看车型详细并加入到定损单中
			vehList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					indexOf = position;
					vehicleDialog();
				}
			});
		}
	}

	/**
	 * 车型对话框
	 * 
	 * @return
	 */
	private void vehicleDialog() {
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.dag_addvehicle, null);
		Map<String, String> mp=null;
		try {
			 mp = data.get(indexOf);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// dialog.setTitle(mp.get("Name"));

		Button dialogOk = (Button) layout.findViewById(R.id.dialog_ok);
		TextView dialogTilte = (TextView) layout
				.findViewById(R.id.dialog_tilte);
		// dialogTilte.setText(mp.get("Name"));
		Button dialogCancle = (Button) layout.findViewById(R.id.dialog_cancle);
		((TextView) layout.findViewById(R.id.addveh_sermc)).setText(mp
				.get("Cxmc"));
		((TextView) layout.findViewById(R.id.addveh_czmc)).setText(mp
				.get("Czmc"));
		((TextView) layout.findViewById(R.id.addveh_cxmc)).setText(mp
				.get("Name"));
		((TextView) layout.findViewById(R.id.addveh_bsq)).setText(mp
				.get("Bsxlx"));
		((TextView) layout.findViewById(R.id.addveh_pl)).setText(mp.get("Pl"));
		((TextView) layout.findViewById(R.id.addveh_pp))
				.setText(mp.get("Ppmc"));
		((TextView) layout.findViewById(R.id.addveh_fdj)).setText(mp
				.get("Fdjxh"));
		((TextView) layout.findViewById(R.id.addveh_bz))
				.setText(mp.get("Cxbz"));

		if ("配件查询".equals(titleFlag)) {
			((TextView) layout.findViewById(R.id.tsinfo)).setText("");
		}
		vehCxid = ((TextView) layout.findViewById(R.id.addveh_cxid));
		newCxid = mp.get("Cxid1");
		vehCxid.setText(newCxid);
		dialogOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 如果没有旧车型，直接更新车型信息
				dialog.dismiss();
				if (oldVehCertainId == null
						|| "".equals(oldVehCertainId.trim())) {
					actingBrand = "0";
					Map<String, String> mp = data.get(indexOf);
					List<ActingBrandItem> insuranceItemList = ActingBrandItemDao
							.getInstance().getListActingBrandItem(evalId);
					for (ActingBrandItem item : insuranceItemList) {
						if (item.getBrandCode()
								.equals(mp.get("VEH_BRAND_CODE"))) {
							actingBrand = "1";
						}
					}
					updateVehicleInfo();
					EvalVehiclActivity.this.finish();
					// 如果存在旧车型，则提醒更新
				} else if (oldVehCertainId != null
						&& !oldVehCertainId.equals(newCxid)) {
					Map<String, String> mp = data.get(indexOf);
					List<ActingBrandItem> insuranceItemList = ActingBrandItemDao
							.getInstance().getListActingBrandItem(evalId);
					actingBrand = "0";
					for (ActingBrandItem item : insuranceItemList) {
						if (item.getBrandCode()
								.equals(mp.get("VEH_BRAND_CODE"))) {
							actingBrand = "1";
						}
					}
					updateVehicleInfo();
					// 删除其他信息
					if (!mState.equals("")) {
						// 若是暂存或者供货状态下删除服务器上的数据
						deleteServerPartInfo(evalId, remId);
					}
					deleteEvalInfo(evalId);
					finish();
				} else {
					Map<String, String> mp = data.get(indexOf);
					Intent data = new Intent();
					Bundle value = new Bundle();
					value.putString("VEHICLE_ID", newCxid);
					value.putString("VEHICLE_CODE", mp.get("Code"));
					value.putString("VEHICLE_NAME", mp.get("Name"));
					value.putString("BRAND_NAME", mp.get("Ppmc"));
					value.putString("FAMILY_NAME", mp.get("Cxmc"));
					value.putString("GROUP_NAME", mp.get("Czmc"));
					value.putString("VEHICLE_PAIL", mp.get("Pl"));
					data.putExtra("mp", value);
					setResult(RESULT_OK, data);
					EvalVehiclActivity.this.finish();
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
		window.setAttributes(lp);
		dialog.show();
	}

	/**
	 * 更新车型信息
	 */
	private Long updateVehicleInfo() {
		Map<String, String> mp = data.get(indexOf);
		EvalLossInfo vehicleInfo = new EvalLossInfo();
		vehicleInfo.set_id(evalId);
		// 厂家信息
		vehicleInfo.setVehFactoryCode(mp
				.get(EvalLossInfoDao.Columns.VEH_FACTORY_CODE));
		vehicleInfo.setVehFactoryName(mp
				.get(EvalLossInfoDao.Columns.VEH_FACTORY_NAME));
		// 品牌信息
		vehicleInfo.setVehBrandCode(mp
				.get(EvalLossInfoDao.Columns.VEH_BRAND_CODE));
		vehicleInfo.setVehBrandName(mp.get("Ppmc"));
		// 车系信息
		vehicleInfo.setVehSeriCode(mp
				.get(EvalLossInfoDao.Columns.VEH_SERI_CODE));
		vehicleInfo.setVehSeriName(mp.get("Cxmc"));
		vehicleInfo.setVehSeriId(mp.get(EvalLossInfoDao.Columns.VEH_SERI_ID));
		vehicleInfo.setVehSeriId(mp.get("Cxid"));
		// 车组信息
		vehicleInfo.setVehGroupCode(mp
				.get(EvalLossInfoDao.Columns.VEH_GROUP_CODE));
		vehicleInfo.setVehGroupName(mp.get("Czmc"));
		// 车型信息
		vehicleInfo.setVehCertainId(newCxid);
		vehicleInfo.setVehCertainCode(mp.get("Code"));
		vehicleInfo.setVehCertainName(mp.get("Name"));
		// 车辆种类信息
		vehicleInfo.setVehKindCode(mp
				.get(EvalLossInfoDao.Columns.VEH_KIND_CODE));
		vehicleInfo.setVehStandCertainId(mp
				.get(EvalLossInfoDao.Columns.VEH_KIND_ID));
		vehicleInfo.setVehKindName(mp
				.get(EvalLossInfoDao.Columns.VEH_KIND_NAME));
		vehicleInfo.setVehYearType(mp
				.get(EvalLossInfoDao.Columns.VEH_YEAR_TYPE));
		vehicleInfo.setSelfConfigFlag("0");
		vehicleInfo.setActingBrand(actingBrand);
		vehicleInfo.setVinNo(vehName);
		return evalLossInfoAction.updateVehicleInfo(vehicleInfo);
	}

	/**
	 * 删除服务器上配件数据
	 * 
	 * @param evalId
	 */
	private void deleteServerPartInfo(Long evalId, String remId) {
		// TODO Auto-generated method stub
		evalLossInfoAction.deleteServerPartInfo(evalId, remId);
	}

	/**
	 * 删除定损信息
	 * 
	 * 
	 */
	private void deleteEvalInfo(Long evalId) {
		evalLossInfoAction.deleteEvalLossInfo(evalId);
	}

	/**
	 * 根据车组Id，查询车型列表
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetVehicleListByGroupIdTask extends
			AsyncTask<Void, Void, SpVehicleListDTO> {

		@Override
		public void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "请稍候", "正在查询...");
		}

		@Override
		protected SpVehicleListDTO doInBackground(Void... params) {
			SpVehicleListDTO response = ServerApiManager
					.getListVehData(groupId);
			return response;
		}

		@Override
		public void onPostExecute(SpVehicleListDTO vehicleListDto) {
			progressDialog.dismiss();

			if (vehicleListDto == null
					|| vehicleListDto.getVehicleList() == null
					|| vehicleListDto.getVehicleList().size() == 0) {
				toast("未查询到该车组下的车型数据");
				return;
			} else {
				List<EvalLossInfoDTO> mList = vehicleListDto.getVehicleList();
				data = new ArrayList<Map<String, String>>();
				for (EvalLossInfoDTO eDto : mList) {
					HashMap<String, String> map = new HashMap<String, String>();
					// 厂家
					eDto.getId();
					map.put(EvalLossInfoDao.Columns.VEH_FACTORY_NAME,
							eDto.getVehFactoryName());
					map.put(EvalLossInfoDao.Columns.VEH_FACTORY_CODE,
							eDto.getVehFactoryCode());
					// 品牌
					map.put("Ppmc", eDto.getVehBrandName());
					map.put(EvalLossInfoDao.Columns.VEH_BRAND_CODE,
							eDto.getVehBrandCode());
					// 车系
					map.put("Cxmc", eDto.getVehSeriName());
					map.put("Cxid", eDto.getVehSeriId());
					map.put(EvalLossInfoDao.Columns.VEH_SERI_CODE,
							eDto.getVehSeriCode());
					map.put(EvalLossInfoDao.Columns.VEH_SERI_ID,
							eDto.getVehSeriId());
					// 车组
					map.put(EvalLossInfoDao.Columns.VEH_GROUP_CODE,
							eDto.getVehGroupCode());
					map.put("Czmc", eDto.getVehGroupName());
					// 车型
					map.put("Name", eDto.getVehCertainName());
					map.put("Code", eDto.getVehCertainCode());
					map.put("Cxid1", eDto.getVehCertainId());
					map.put("Cxbz", eDto.getBz());
					// 种类
					map.put(EvalLossInfoDao.Columns.VEH_KIND_CODE,
							eDto.getVehKindCode());
					map.put(EvalLossInfoDao.Columns.VEH_KIND_ID,
							eDto.getVehCertainId());
					map.put(EvalLossInfoDao.Columns.VEH_KIND_NAME,
							eDto.getVehKindName());
					// 年款
					map.put(EvalLossInfoDao.Columns.VEH_YEAR_TYPE,
							eDto.getVehYearType());
					map.put("Fdjxh", eDto.getFdjxh());
					map.put("Bsxlx", eDto.getBsxlx());
					map.put("Pl", eDto.getDisplacement());
					data.add(map);
				}
			}

			displayCxlist();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (forQuery && keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(KeyEvent.KEYCODE_BACK);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 非空判断
	private boolean validate() {
		if (vehName == null || "".equals(vehName)) {
			vehNameEt.requestFocus();
			vehNameEt.setError("请输入名称");
			return false;
		}
		return true;
	}

	/**
	 * 换VIN
	 * 
	 * 
	 */
	private void VINSeachDialog() {
		final Dialog dialog = new Dialog(this, R.style.DialogStyle);
		View view = LayoutInflater.from(this).inflate(
				R.layout.dialog_vin_seach, null);
		TextView titleView = (TextView) view.findViewById(R.id.dialog_tilte);
		TextView messageView = (TextView) view
				.findViewById(R.id.dialog_content);
		if (ValidateUtil.isNO(vehName)) {
			messageView.setText("您是否使用vin码查询！ 【是】按vin码查询，【否】按名称查询 ");
		} else {
			messageView
					.setText("您是否使用vin码查询！（vin码有误，将按名称查询）， 【是】按vin码查询，【否】按名称查询");
		}

		Button dialogOk = (Button) view.findViewById(R.id.dialog_ok);
		Button dialogCancle = (Button) view.findViewById(R.id.dialog_cancle);
		dialogOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();

				if (ValidateUtil.isNO(vehName)) {
					data = new ArrayList<Map<String, String>>();
					new GetVehicleListByVehicleNameTask().execute("1");
				} else {
					data = new ArrayList<Map<String, String>>();
					new GetVehicleListByVehicleNameTask().execute();
				}

			}

		});

		dialogCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				// if(!ValidateUtil.isNO(vehName)){
				// adapter = null;
				// data = new ArrayList<Map<String, String>>();
				// new GetVehicleListByVehicleNameTask().execute();
				// }else {
				data = new ArrayList<Map<String, String>>();
				new GetVehicleListByVehicleNameTask().execute();
				// }

			}
		});
		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * 根据车型名称，查询车型列表
	 * 
	 * @author Administrator
	 * 
	 */
	private class GetVehicleListByVehicleNameTask extends
			AsyncTask<String, Void, Response> {

		@Override
		public void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "请稍候", "正在查询...");
			int queryType = 2;
		}

		@Override
		protected Response doInBackground(String... params) {
			String flag = null;
			if (params != null && params.length > 0) {

				flag = params[0];
			}
			Response response = ServerApiManager.getListVehDataByName(vehName,
					flag, pageno);
//			Response response = ServerApiManager.getNewListVehDataByName(vehName,
//					flag, pageno);
			return response;
		}

		@Override
		public void onPostExecute(Response response) {
			progressDialog.dismiss();
			if (response != null && "1".equals(response.getResponseCode())) {
				SpAutoVehicleDTO vehicleListDto = (SpAutoVehicleDTO) JsonUtil
						.getSpDto(response, SpAutoVehicleDTO.class);
				if (vehicleListDto == null
						|| vehicleListDto.getSpEvalLossInfoListDTO() == null
						|| vehicleListDto.getSpEvalLossInfoListDTO().size() == 0) {
					toast("未查询到该相关的车型数据");
					return;
				} else {
					List<EvalLossInfoDTO> mList = vehicleListDto
							.getSpEvalLossInfoListDTO();
					// total = vehicleListDto.getTotal();
					for (EvalLossInfoDTO eDto : mList) {
						HashMap<String, String> map = new HashMap<String, String>();
						// 厂家
						map.put("Sccjmc", eDto.getVehFactoryName());
						map.put(EvalLossInfoDao.Columns.VEH_FACTORY_CODE,
								eDto.getVehFactoryCode());
						// 品牌
						map.put("Ppmc", eDto.getVehBrandName());
						map.put("ppbm", eDto.getVehBrandCode());
						// 车系
						map.put("Cxmc", eDto.getVehSeriName());
						// map.put("FamilyId", eDto.getFamilyId());
						map.put(EvalLossInfoDao.Columns.VEH_SERI_CODE,
								eDto.getVehSeriCode());
						// 车组
						map.put("vehGroupId", groupId);
						map.put("Czmc", eDto.getVehGroupName());
						// 车型
						map.put("Name", eDto.getVehCertainName());
						map.put("Code", eDto.getVehCertainCode());
						map.put("Cxid1", eDto.getVehCertainId());
						map.put("Cxbz", eDto.getRemark());
						// 种类
						map.put("VehKindCode", eDto.getVehKindCode());
						map.put("VehKindName", eDto.getVehKindName());
						// 年款
						map.put("modelYear", eDto.getVehYearType());
						// map.put("groupGradeId",eDto.getGroupGradeId());
						// map.put("groupGradeName",eDto.getGroupGradeName());
						// map.put("seaCount",eDto.getSeatCount());
						map.put("Fdjxh", eDto.getFdjxh());
						map.put("Bsxlx", eDto.getBsxlx());
						map.put("Pl", eDto.getDisplacement());
						// map.put("importFlag", eDto.getImportantMark());
						map.put("Czbm", eDto.getVehGroupCode());
						data.add(map);
					}
				}
				displayCxlist();
			} else {
				toast("查询车型数据时发生错误：" + response.getErrorMessage());
			}
		}
	}

}