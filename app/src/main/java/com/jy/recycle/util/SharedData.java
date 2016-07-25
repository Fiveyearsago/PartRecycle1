package com.jy.recycle.util;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jy.mobile.dto.ManHourDTO;
import com.jy.mobile.dto.PjJgfaxxbBdDTO;
import com.jy.mobile.response.SpUserSettingsDTO;

public class SharedData {
	public Context context;

	/**
	 * 单例
	 */
	private static SharedData sharedData = null;

	public static SharedData data() {
		return sharedData;
	}

	public static void init(Context context) {
		if (sharedData == null) {
			sharedData = new SharedData(context);
		}
	}

	/**
	 * 实现
	 */
	public SharedData(Context _context) {
		context = _context;
	}
	public void saveState(String state){
		SharedPreferences eval = context.getSharedPreferences("MSTATE", 0);
		eval.edit().putString("MSTATE", state).commit();
	}
	public String getState() {
		SharedPreferences eval = context.getSharedPreferences("MSTATE", 0);
		return eval.getString("MSTATE", null);
	}
	/**
	 * 存入更新日期
	 */
	public void saveUpdateTime(String updateTime) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("UPDATE_TIME", updateTime).commit();
	}

	/**
	 * 获取更新日期
	 */
	public String getUpdateTime() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("UPDATE_TIME", null);
	}
	
	/**
	 * 存入是否记录用户信息
	 */
	public void saveEvalChk() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_CHK", "1").commit();
	}

	/**
	 * 获取是否记录用户信息
	 * 
	 * @return 记录用户信息
	 */
	public String getEvalChk() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_CHK", null);
	}

	/**
	 * 存入用户登录名
	 * 
	 * @param userName
	 */
	public void saveEvalUid(String userName) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_UID", userName).commit();
	}

	/**
	 * 获取用户登录名
	 * 
	 * @return 用户登录名
	 */
	public String getEvalUid() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_UID", null);
	}

	/**
	 * 存入用户ID
	 * 
	 * @param userId
	 */
	public void saveEvalUserId(String userId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_USERID", userId).commit();
	}

	/**
	 * 获取用户ID
	 * 
	 * @return 用户ID
	 */
	public String getEvalUserId() {
		// SharedPreferences eval = context.getSharedPreferences("JYEVALUSER",
		// 0);
		// return eval.getString("EVAL_USERID",null);
		return getEvalUid();
	}
	/**
	 * 存入是否用本地库
	 */
	public void saveIsLocal(Boolean isLocal) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putBoolean("IS_LOCAL", isLocal).commit();
	}

	/**
	 * 是否使用离线数据
	 */
	public Boolean getIsLocal() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getBoolean("IS_LOCAL", false);
	}
	/**
	 * 存入evalUrl
	 * 
	 */
	public void saveEvalUrl(String evalUrl) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_URL", evalUrl).commit();
	}

	/**
	 * 获取evalUrl
	 * 
	 */
	public String getEvalUrl() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_URL", null);
	}
	/**
	 * 存入uploadUrl
	 * 
	 * @param password
	 */
	public void saveUploadUrl(String uploadUrl) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("UPLOAD_URL", uploadUrl).commit();
	}

	/**
	 * 获取uploadUrl
	 * 
	 * @return 用户密码
	 */
	public String getUploadUrl() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("UPLOAD_URL", null);
	}
	
	/**
	 * 存入用户密码
	 * 
	 * @param password
	 */
	public void saveEvalPwd(String password) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_PWD", password).commit();
	}

	/**
	 * 获取用户密码
	 * 
	 * @return 用户密码
	 */
	public String getEvalPwd() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_PWD", null);
	}

	/**
	 * 存入serialNo
	 * 
	 * @param serivalNo
	 */
	public void saveSerialNo(int serivalNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("SERIAL_NO", serivalNo).commit();
	}

	/**
	 * 获取serialNo
	 * 
	 * @return serivalNo
	 */
	public int getSerialNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("SERIAL_NO", 0);
	}

	/**
	 * 存入任务ID
	 * 
	 * @param taskId
	 */
	public void saveTaskId(String taskId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_TASK_ID", taskId).commit();
	}
	/**
	 * 获取LossNo
	 * 
	 * @return LossNo
	 */
	public String getLossNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_LOSS_NO", null);
	}

	/**
	 * 存入LossNo
	 * 
	 * @param LossNo
	 */
	public void saveLossNo(String estimateNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_LOSS_NO", estimateNo).commit();
	}
	/**
	 * 获取EstimateNo
	 * 
	 * @return EstimateNo
	 */
	public String getEstimateNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_ESTIMATE_NO", null);
	}

	/**
	 * 存入EstimateNo
	 * 
	 * @param EstimateNo
	 */
	public void saveEstimateNo(String estimateNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_ESTIMATE_NO", estimateNo).commit();
	}
	/**
	 * 获取任务ID
	 * 
	 * @return 任务ID
	 */
	public String getTaskId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_TASK_ID", null);
	}

	/**
	 * 存入任务号
	 * 
	 * @param taskNo
	 */
	public void saveTaskNo(String taskNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_TASK_NO", taskNo).commit();
	}

	/**
	 * 获取任务号
	 * 
	 * @return 任务号
	 */
	public String getTaskNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_TASK_NO", null);
	}

	/**
	 * 存入报案号ID
	 * 
	 * @param evalId
	 */
	public void saveEvalId(String evalId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_ID", evalId).commit();
	}

	/**
	 * 获取报案号ID
	 * 
	 * @return 报案号ID
	 */
	public String getEvalId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_ID", null);
	}

	/**
	 * 存入车型ID
	 * 
	 * @param evalId
	 */
	public void saveEvalVehId(String evalId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_VEHICLE_ID", evalId).commit();
	}

	/**
	 * 获取车型ID
	 * 
	 * @return 车型ID
	 */
	public String getEvalVehId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_VEHICLE_ID", null);
	}

	/**
	 * 存入组织编码
	 * 
	 * @param comcode
	 */
	public void saveEvalComCode(String comcode) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_VEHCOMCODE", comcode).commit();
	}

	/**
	 * 获取组织编码
	 * 
	 * @return 组织编码
	 */
	public String getEvalComCode() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_VEHCOMCODE", null);
	}

	/**
	 * 存入权限查看系统价格
	 * 
	 * @param comcode
	 */
	public void savePowerXTPrice(String xtPrice) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_XTPRICE", xtPrice).commit();
	}

	/**
	 * 获取权限查看系统价格
	 * 
	 * @return 
	 * 
	 * 	 */
	public String getPowerXTPrice() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_XTPRICE", null);
	}
	
	/**
	 * 存入权限查看本地价格
	 * 
	 * @param comcode
	 */
	public void savePowerBDPrice(String bdPrice) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_BDPRICE", bdPrice).commit();
	}

	/**
	 * 获取权限查看本地价格
	 * 
	 * @return 组织编码
	 */
	public String getPowerBDPrice() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_BDPRICE", null);
	}
	
	/**
	 * 存入手工添加配件权限
	 * 
	 * @param comcode
	 */
	public void savePowerMHPart(String mhPart) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHPART", mhPart).commit();
	}

	/**
	 * 获取手工添加配件权限
	 * 
	 * @return 组织编码
	 */
	public String getPowerMHPart() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHPART", null);
	}
	
	/**
	 * 存入手工添加维修权限
	 * 
	 * @param 
	 */
	public void savePowerMHRepair(String mhRepair) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHREPAIR", mhRepair).commit();
	}

	/**
	 * 获取手工添加维修权限
	 * 
	 * @return 
	 */
	public String getPowerMHRepair() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHREPAIR", null);
	}
	
	/**
	 * 存入手工添加辅料权限
	 * 
	 * @param 
	 */
	public void savePowerMHAssist(String mhAssist) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHASSIST", mhAssist).commit();
	}

	/**
	 * 获取手工添加辅料权限
	 * 
	 * @return 
	 */
	public String getPowerMHAssist() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHASSIST", null);
	}
	
	/**
	 * 存入手工添加辅料权限
	 * 
	 * @param 
	 */
	public void savePowerMHOther(String mhOther) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHOTHER", mhOther).commit();
	}

	/**
	 * 获取手工添加辅料权限
	 * 
	 * @return 
	 */
	public String getPowerMHOther() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHOTHER", null);
	}
	
	/**
	 * 存入价格方案一
	 * 
	 * @param 
	 */
	public void savePowerPriceSource1(String priceSource) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_PRICE_SOURCE_1", priceSource).commit();
	}

	/**
	 * 获取价格方案一
	 * 
	 * @return 
	 */
	public String getPowerPriceSource1() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_PRICE_SOURCE_1", "");
	}
	
	/**
	 * 存入价格方案二
	 * 
	 * @param 
	 */
	public void savePowerPriceSource2(String priceSource) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_PRICE_SOURCE_2", priceSource).commit();
	}

	/**
	 * 获取价格方案二
	 * 
	 * @return 
	 */
	public String getPowerPriceSource2() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_PRICE_SOURCE_2", "");
	}
	
	/**
	 * 存入价格方案三
	 * 
	 * @param 
	 */
	public void savePowerPriceSource3(String priceSource) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_PRICE_SOURCE_3", priceSource).commit();
	}

	/**
	 * 获取价格方案三
	 * 
	 * @return 
	 */
	public String getPowerPriceSource3() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_PRICE_SOURCE_3", "");
	}
	
	/**
	 * 存入价格方案名称
	 * 
	 * @param jgfamc
	 */
	public void saveEvalJgfa(String jgfamc) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_VEHJGFAMC", jgfamc).commit();
	}

	/**
	 * 获取价格方案名称
	 * 
	 * @return 价格方案名称
	 */
	public String getEvalJgfa() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_VEHJGFAMC", "");
	}

	/**
	 * 存入价格方案列表位置
	 * 
	 * @param position
	 */
	public void saveEvalJgfaPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_VEHJGFAPOSITION", position).commit();
	}

	/**
	 * 获取价格方案列表位置
	 * 
	 * @return
	 */
	public int getEvalJgfaPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_VEHJGFAPOSITION", 0);
	}

	
	/**
	 * 存入价格方案列表位置
	 * 
	 * @param position
	 */
	public void saveEvalManHourPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_VEHMANHOURPOSITION", position).commit();
	}

	/**
	 * 获取价格方案列表位置
	 * 
	 * @return
	 */
	public int getEvalManHourPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_VEHMANHOURPOSITION", 0);
	}

	/**
	 * 存入维修工种列表位置
	 * 
	 * @param position
	 */
	public void saveEvalRepairItemPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_REPAIRITEMPOSITION", position).commit();
	}

	/**
	 * 获取维修工种列表位置
	 * 
	 * @return
	 */
	public int getEvalRepairItemPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_REPAIRITEMPOSITION", 0);
	}

	
	/**
	 * 存入换件工种列表位置
	 * 
	 * @param position
	 */
	public void saveEvalPartItemPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_REPAIRITEMPOSITION", position).commit();
	}
	/**
	 * 获取换件列表位置
	 * 
	 * @return
	 */
	public int getEvalPartItemPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_REPAIRITEMPOSITION", 0);
	}

	/**
	 * 存入维修部位列表位置
	 * 
	 * @param position
	 */
	public void saveEvalRepairFzPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_REPAIRFZPOSITION", position).commit();
	}

	/**
	 * 获取维修部位列表位置
	 * 
	 * @return
	 */
	public int getEvalRepairFzPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_REPAIRFZPOSITION", 0);
	}

	public float getFloatByStr(String str) {
		float d = 0;
		try {
			d = Float.parseFloat(str);
		} catch (NumberFormatException e) {
			Loger.e("SharedData", "getFloatByStr:", e);
			d = 0f;
		}
		return d;
	}

	/**
	 * 获取本机拍照质量设置
	 * 
	 * @return 默认2 精细 1 极精细 2 精细 3 正常
	 */
	public int getPhoQuality() {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		return eval.getInt("PHOTO_QUALITY", 2);
	}

	/**
	 * 保存本机拍照质量设置 1 极精细 2 精细 3 正常
	 */
	public void savePhoQuality(int quality) {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		eval.edit().putInt("PHOTO_QUALITY", quality).commit();
	}

	/**
	 * 获取本机拍照大小设置
	 * 
	 * @return 默认3 200万像素 1 500万像素 2 300万像素 3 200万像素 4 100万像素
	 */
	public int getPhoSize() {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		return eval.getInt("PHOTO_SIZE", 3);
	}

	/**
	 * 保存本机拍照大小设置 1 500万像素 2 300万像素 3 200万像素 4 100万像素
	 */
	public void savePhoSize(int size) {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		eval.edit().putInt("PHOTO_SIZE", size).commit();
	}

	/**
	 * 存入车型ID
	 * 
	 * @param evalId
	 */
	public void saveTempVehId(String evalId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("TEMP_VEHICLE_ID", evalId).commit();
	}

	/**
	 * 获取车型ID
	 * 
	 * @return 车型ID
	 */
	public String getTempVehId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("TEMP_VEHICLE_ID", null);
	}

	// 登录后用一个线程将查勘员对应的价格方案保存到共享内存中
	public void saveJgfa(String jglist) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_JGFALIST", jglist).commit();
	}
	
	// 登录后用一个线程将查勘员对应的价格方案保存到共享内存中
		public void saveManHourList(String manHourList) {
			SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
			eval.edit().putString("EVAL_MANHOURLIST", manHourList).commit();
		}

	// 获取价格方案
	public List<SpinnerItem> getJgfa() {
		List<SpinnerItem> jgfalst = null;
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		String userSettings = eval.getString("EVAL_JGFALIST", null);

		if (userSettings != null) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			SpUserSettingsDTO spUserSettingsDTO = gson.fromJson(userSettings,
					SpUserSettingsDTO.class);
			if (spUserSettingsDTO.getJgfaList() != null) {
				jgfalst = new ArrayList<SpinnerItem>();
				SpinnerItem si0 = new SpinnerItem("0","请选方案");
				jgfalst.add(si0);
				for(int i = 0;i<spUserSettingsDTO.getJgfaList().size();i++){
					if(getPowerPriceSource1().equals("0")){
						if(i==0){
							continue;
						}
					}
					if(getPowerPriceSource2().equals("0")){
						if(i==1){
							continue;
						}
					}
					if(getPowerPriceSource3().equals("0")){
						if(i==2){
							continue;
						}
					}
					PjJgfaxxbBdDTO jgfaDTO = spUserSettingsDTO.getJgfaList().get(i);
						SpinnerItem si = new SpinnerItem(jgfaDTO.getJgfabm(),
								jgfaDTO.getJgfamc());
						jgfalst.add(si);
				}
			}
		}

		return jgfalst;
	}
	// 获取维修方案
		public List<ManHourDTO> getManHourList() {
			List<ManHourDTO> manHourList = null;
			SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
			String userSettings = eval.getString("EVAL_JGFALIST", null);

			if (userSettings != null) {
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();

				SpUserSettingsDTO spUserSettingsDTO = gson.fromJson(userSettings,
						SpUserSettingsDTO.class);
				manHourList = spUserSettingsDTO.getManHourSourceList();
			}

			return manHourList;
		}
	// 获取价格方案Id
	public String getJgfaidByJgfabm(String jgfabm) {
		List<SpinnerItem> jgfalst = null;
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		String userSettings = eval.getString("EVAL_JGFALIST", null);

		if (userSettings != null) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			SpUserSettingsDTO spUserSettingsDTO = gson.fromJson(userSettings,
					SpUserSettingsDTO.class);
			if (spUserSettingsDTO.getJgfaList() != null) {
				jgfalst = new ArrayList<SpinnerItem>();
				for (PjJgfaxxbBdDTO jgfaDTO : spUserSettingsDTO.getJgfaList()) {
					if(jgfabm.equals(jgfaDTO.getJgfabm())){
						return jgfaDTO.getId();
					}
				}
			}
		}

		return null;
	}
	public String getChgCode(String id) {
		List<SpinnerItem> jgfalst = null;
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		String userSettings = eval.getString("EVAL_JGFALIST", null);

		if (userSettings != null) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			SpUserSettingsDTO spUserSettingsDTO = gson.fromJson(userSettings,
					SpUserSettingsDTO.class);
			if (spUserSettingsDTO.getJgfaList() != null) {
				jgfalst = new ArrayList<SpinnerItem>();
				for (PjJgfaxxbBdDTO jgfaDTO : spUserSettingsDTO.getJgfaList()) {
					if(id.equals(jgfaDTO.getId())){
						return jgfaDTO.getJgfabm();
					}
				}
			}
		}

		return null;
	}

	/**
	 * 返回缓存的品牌名
	 * 
	 * @return
	 */
	public String getBrand() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("brand", null);
	}

	public void saveBrand(String brand) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("brand", brand).commit();
	}

	/**
	 * 返回缓存的车组名
	 * 
	 * @return
	 */
	public String getGroupName() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("group", null);
	}

	public void saveGroupName(String brand) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("group", brand).commit();
	}

	public String getVehicl() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("vehicl", null);
	}

	public void saveVehicl(String vehicl) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("vehicl", vehicl).commit();
	}

	// 联通卡可以获取手机号，移动的神州行（畅听卡）获取不了,如果非要获得手机号，让用户输入号码发送短信获取
	public String getPhoneNumber() {
		// 创建电话管理与手机建立连接
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// 获取手机号码
		return tm.getLine1Number();
	}

	/**
	 * 保存reportNO
	 * 
	 * @param reportNo
	 */
	public void saveReportNo(String reportNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("reportNo", reportNo).commit();
	}

	/**
	 * 得到缓存的reportNO
	 * 
	 * @return
	 */
	public String getReportNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("reportNo", null);
	}

	/**
	 * 保存案件查询条件
	 * 
	 * @param where
	 */
	public void saveWhere(String where) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("where", where).commit();
	}

	public String getWhere() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("where", null);
	}

	public void saveTimeType(String timeType) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("timeType", timeType).commit();
	}

	public String getTimeType() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("timeType", null);
	}

	public void saveStartDate(String startDate) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("startDate", startDate).commit();
	}

	public void saveEndDate(String endDate) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("endDate", endDate).commit();
	}

	public String getStartDate() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("startDate", null);
	}

	public String getEndDate() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("endDate", null);
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param activity
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */

	public boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 存入需要升级信息
	 */
	public void saveUpdateVer(String ver) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_VER", ver).commit();
	}

	/**
	 * 获取需要升级信息
	 */
	public String getUpdateVer() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_VER", null);
	}

	/**
	 * 暂存零件总数
	 * 
	 * @param total
	 */
	public void savePartCount(int total) {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		eval.edit().putInt("PART_COUNT", total).commit();
	}

	/**
	 * 获取需要升级信息
	 */
	public int getPartCount() {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		return eval.getInt("PART_COUNT", 0);
	}

	/**
	 * 纬度临时数据
	 * 
	 * @param total
	 */
	public void saveTmpLat(float lat) {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		eval.edit().putFloat("TMP_LAT", lat).commit();
	}

	/**
	 * 获取纬度临时数据
	 */
	public float getTmpLat() {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		return eval.getFloat("TMP_LAT", 0);
	}

	/**
	 * 持久化数据
	 */
	public void saveCookie(String key, String value) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString(key, value).commit();
	}

	/**
	 * 获取持久化数据
	 */
	public String getCookie(String key) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString(key, null);
	}

	public void saveUserName(String name) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("USER_NAME", name).commit();
	}

	public String getUserName() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("USER_NAME", "");
	}
	
	
	public void saveUserPwd(String pwd) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("USER_PWD", pwd).commit();
	}

	public String getUserPwd() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("USER_PWD", "");
	}
	// 存入退回后车型修改标记
	public void saveDanWeiFlag(String flag) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_CARTYPE", flag).commit();
	}

	// 获取退回后车型修改标记
	public String getDanWeiFlag() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_CARTYPE", "");
	}

	public void saveBxgsgz(String bxgsgz) {
		// TODO 保存报案号规则
		SharedPreferences eval = context.getSharedPreferences("bxgsgz", 0);
		eval.edit().putString("bxgsgz", bxgsgz).commit();
	}
	public String getBxgsgz() {
		SharedPreferences eval = context.getSharedPreferences("bxgsgz", 0);
		return eval.getString("bxgsgz", null);
	}
	public void saveSfcp(String sfcp) {
		// TODO 保存省份车牌号
		SharedPreferences eval = context.getSharedPreferences("sfcp", 0);
		eval.edit().putString("sfcp", sfcp).commit();
	}
	public String getSfcp() {
		SharedPreferences eval = context.getSharedPreferences("sfcp", 0);
		return eval.getString("sfcp", null);
	}
}
