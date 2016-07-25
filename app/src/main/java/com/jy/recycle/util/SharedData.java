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
	 * ����
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
	 * ʵ��
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
	 * �����������
	 */
	public void saveUpdateTime(String updateTime) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("UPDATE_TIME", updateTime).commit();
	}

	/**
	 * ��ȡ��������
	 */
	public String getUpdateTime() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("UPDATE_TIME", null);
	}
	
	/**
	 * �����Ƿ��¼�û���Ϣ
	 */
	public void saveEvalChk() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_CHK", "1").commit();
	}

	/**
	 * ��ȡ�Ƿ��¼�û���Ϣ
	 * 
	 * @return ��¼�û���Ϣ
	 */
	public String getEvalChk() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_CHK", null);
	}

	/**
	 * �����û���¼��
	 * 
	 * @param userName
	 */
	public void saveEvalUid(String userName) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_UID", userName).commit();
	}

	/**
	 * ��ȡ�û���¼��
	 * 
	 * @return �û���¼��
	 */
	public String getEvalUid() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_UID", null);
	}

	/**
	 * �����û�ID
	 * 
	 * @param userId
	 */
	public void saveEvalUserId(String userId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_USERID", userId).commit();
	}

	/**
	 * ��ȡ�û�ID
	 * 
	 * @return �û�ID
	 */
	public String getEvalUserId() {
		// SharedPreferences eval = context.getSharedPreferences("JYEVALUSER",
		// 0);
		// return eval.getString("EVAL_USERID",null);
		return getEvalUid();
	}
	/**
	 * �����Ƿ��ñ��ؿ�
	 */
	public void saveIsLocal(Boolean isLocal) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putBoolean("IS_LOCAL", isLocal).commit();
	}

	/**
	 * �Ƿ�ʹ����������
	 */
	public Boolean getIsLocal() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getBoolean("IS_LOCAL", false);
	}
	/**
	 * ����evalUrl
	 * 
	 */
	public void saveEvalUrl(String evalUrl) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_URL", evalUrl).commit();
	}

	/**
	 * ��ȡevalUrl
	 * 
	 */
	public String getEvalUrl() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_URL", null);
	}
	/**
	 * ����uploadUrl
	 * 
	 * @param password
	 */
	public void saveUploadUrl(String uploadUrl) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("UPLOAD_URL", uploadUrl).commit();
	}

	/**
	 * ��ȡuploadUrl
	 * 
	 * @return �û�����
	 */
	public String getUploadUrl() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("UPLOAD_URL", null);
	}
	
	/**
	 * �����û�����
	 * 
	 * @param password
	 */
	public void saveEvalPwd(String password) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_PWD", password).commit();
	}

	/**
	 * ��ȡ�û�����
	 * 
	 * @return �û�����
	 */
	public String getEvalPwd() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_PWD", null);
	}

	/**
	 * ����serialNo
	 * 
	 * @param serivalNo
	 */
	public void saveSerialNo(int serivalNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("SERIAL_NO", serivalNo).commit();
	}

	/**
	 * ��ȡserialNo
	 * 
	 * @return serivalNo
	 */
	public int getSerialNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("SERIAL_NO", 0);
	}

	/**
	 * ��������ID
	 * 
	 * @param taskId
	 */
	public void saveTaskId(String taskId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_TASK_ID", taskId).commit();
	}
	/**
	 * ��ȡLossNo
	 * 
	 * @return LossNo
	 */
	public String getLossNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_LOSS_NO", null);
	}

	/**
	 * ����LossNo
	 * 
	 * @param LossNo
	 */
	public void saveLossNo(String estimateNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_LOSS_NO", estimateNo).commit();
	}
	/**
	 * ��ȡEstimateNo
	 * 
	 * @return EstimateNo
	 */
	public String getEstimateNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_ESTIMATE_NO", null);
	}

	/**
	 * ����EstimateNo
	 * 
	 * @param EstimateNo
	 */
	public void saveEstimateNo(String estimateNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_ESTIMATE_NO", estimateNo).commit();
	}
	/**
	 * ��ȡ����ID
	 * 
	 * @return ����ID
	 */
	public String getTaskId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_TASK_ID", null);
	}

	/**
	 * ���������
	 * 
	 * @param taskNo
	 */
	public void saveTaskNo(String taskNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_TASK_NO", taskNo).commit();
	}

	/**
	 * ��ȡ�����
	 * 
	 * @return �����
	 */
	public String getTaskNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_TASK_NO", null);
	}

	/**
	 * ���뱨����ID
	 * 
	 * @param evalId
	 */
	public void saveEvalId(String evalId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_ID", evalId).commit();
	}

	/**
	 * ��ȡ������ID
	 * 
	 * @return ������ID
	 */
	public String getEvalId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_ID", null);
	}

	/**
	 * ���복��ID
	 * 
	 * @param evalId
	 */
	public void saveEvalVehId(String evalId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_VEHICLE_ID", evalId).commit();
	}

	/**
	 * ��ȡ����ID
	 * 
	 * @return ����ID
	 */
	public String getEvalVehId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_VEHICLE_ID", null);
	}

	/**
	 * ������֯����
	 * 
	 * @param comcode
	 */
	public void saveEvalComCode(String comcode) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_VEHCOMCODE", comcode).commit();
	}

	/**
	 * ��ȡ��֯����
	 * 
	 * @return ��֯����
	 */
	public String getEvalComCode() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_VEHCOMCODE", null);
	}

	/**
	 * ����Ȩ�޲鿴ϵͳ�۸�
	 * 
	 * @param comcode
	 */
	public void savePowerXTPrice(String xtPrice) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_XTPRICE", xtPrice).commit();
	}

	/**
	 * ��ȡȨ�޲鿴ϵͳ�۸�
	 * 
	 * @return 
	 * 
	 * 	 */
	public String getPowerXTPrice() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_XTPRICE", null);
	}
	
	/**
	 * ����Ȩ�޲鿴���ؼ۸�
	 * 
	 * @param comcode
	 */
	public void savePowerBDPrice(String bdPrice) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_BDPRICE", bdPrice).commit();
	}

	/**
	 * ��ȡȨ�޲鿴���ؼ۸�
	 * 
	 * @return ��֯����
	 */
	public String getPowerBDPrice() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_BDPRICE", null);
	}
	
	/**
	 * �����ֹ�������Ȩ��
	 * 
	 * @param comcode
	 */
	public void savePowerMHPart(String mhPart) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHPART", mhPart).commit();
	}

	/**
	 * ��ȡ�ֹ�������Ȩ��
	 * 
	 * @return ��֯����
	 */
	public String getPowerMHPart() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHPART", null);
	}
	
	/**
	 * �����ֹ����ά��Ȩ��
	 * 
	 * @param 
	 */
	public void savePowerMHRepair(String mhRepair) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHREPAIR", mhRepair).commit();
	}

	/**
	 * ��ȡ�ֹ����ά��Ȩ��
	 * 
	 * @return 
	 */
	public String getPowerMHRepair() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHREPAIR", null);
	}
	
	/**
	 * �����ֹ���Ӹ���Ȩ��
	 * 
	 * @param 
	 */
	public void savePowerMHAssist(String mhAssist) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHASSIST", mhAssist).commit();
	}

	/**
	 * ��ȡ�ֹ���Ӹ���Ȩ��
	 * 
	 * @return 
	 */
	public String getPowerMHAssist() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHASSIST", null);
	}
	
	/**
	 * �����ֹ���Ӹ���Ȩ��
	 * 
	 * @param 
	 */
	public void savePowerMHOther(String mhOther) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_MHOTHER", mhOther).commit();
	}

	/**
	 * ��ȡ�ֹ���Ӹ���Ȩ��
	 * 
	 * @return 
	 */
	public String getPowerMHOther() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_MHOTHER", null);
	}
	
	/**
	 * ����۸񷽰�һ
	 * 
	 * @param 
	 */
	public void savePowerPriceSource1(String priceSource) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_PRICE_SOURCE_1", priceSource).commit();
	}

	/**
	 * ��ȡ�۸񷽰�һ
	 * 
	 * @return 
	 */
	public String getPowerPriceSource1() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_PRICE_SOURCE_1", "");
	}
	
	/**
	 * ����۸񷽰���
	 * 
	 * @param 
	 */
	public void savePowerPriceSource2(String priceSource) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_PRICE_SOURCE_2", priceSource).commit();
	}

	/**
	 * ��ȡ�۸񷽰���
	 * 
	 * @return 
	 */
	public String getPowerPriceSource2() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_PRICE_SOURCE_2", "");
	}
	
	/**
	 * ����۸񷽰���
	 * 
	 * @param 
	 */
	public void savePowerPriceSource3(String priceSource) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_POWER_PRICE_SOURCE_3", priceSource).commit();
	}

	/**
	 * ��ȡ�۸񷽰���
	 * 
	 * @return 
	 */
	public String getPowerPriceSource3() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_POWER_PRICE_SOURCE_3", "");
	}
	
	/**
	 * ����۸񷽰�����
	 * 
	 * @param jgfamc
	 */
	public void saveEvalJgfa(String jgfamc) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_VEHJGFAMC", jgfamc).commit();
	}

	/**
	 * ��ȡ�۸񷽰�����
	 * 
	 * @return �۸񷽰�����
	 */
	public String getEvalJgfa() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_VEHJGFAMC", "");
	}

	/**
	 * ����۸񷽰��б�λ��
	 * 
	 * @param position
	 */
	public void saveEvalJgfaPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_VEHJGFAPOSITION", position).commit();
	}

	/**
	 * ��ȡ�۸񷽰��б�λ��
	 * 
	 * @return
	 */
	public int getEvalJgfaPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_VEHJGFAPOSITION", 0);
	}

	
	/**
	 * ����۸񷽰��б�λ��
	 * 
	 * @param position
	 */
	public void saveEvalManHourPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_VEHMANHOURPOSITION", position).commit();
	}

	/**
	 * ��ȡ�۸񷽰��б�λ��
	 * 
	 * @return
	 */
	public int getEvalManHourPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_VEHMANHOURPOSITION", 0);
	}

	/**
	 * ����ά�޹����б�λ��
	 * 
	 * @param position
	 */
	public void saveEvalRepairItemPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_REPAIRITEMPOSITION", position).commit();
	}

	/**
	 * ��ȡά�޹����б�λ��
	 * 
	 * @return
	 */
	public int getEvalRepairItemPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_REPAIRITEMPOSITION", 0);
	}

	
	/**
	 * ���뻻�������б�λ��
	 * 
	 * @param position
	 */
	public void saveEvalPartItemPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_REPAIRITEMPOSITION", position).commit();
	}
	/**
	 * ��ȡ�����б�λ��
	 * 
	 * @return
	 */
	public int getEvalPartItemPosition() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getInt("EVAL_REPAIRITEMPOSITION", 0);
	}

	/**
	 * ����ά�޲�λ�б�λ��
	 * 
	 * @param position
	 */
	public void saveEvalRepairFzPosition(int position) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putInt("EVAL_REPAIRFZPOSITION", position).commit();
	}

	/**
	 * ��ȡά�޲�λ�б�λ��
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
	 * ��ȡ����������������
	 * 
	 * @return Ĭ��2 ��ϸ 1 ����ϸ 2 ��ϸ 3 ����
	 */
	public int getPhoQuality() {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		return eval.getInt("PHOTO_QUALITY", 2);
	}

	/**
	 * ���汾�������������� 1 ����ϸ 2 ��ϸ 3 ����
	 */
	public void savePhoQuality(int quality) {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		eval.edit().putInt("PHOTO_QUALITY", quality).commit();
	}

	/**
	 * ��ȡ�������մ�С����
	 * 
	 * @return Ĭ��3 200������ 1 500������ 2 300������ 3 200������ 4 100������
	 */
	public int getPhoSize() {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		return eval.getInt("PHOTO_SIZE", 3);
	}

	/**
	 * ���汾�����մ�С���� 1 500������ 2 300������ 3 200������ 4 100������
	 */
	public void savePhoSize(int size) {
		SharedPreferences eval = context.getSharedPreferences("PHOTO", 0);
		eval.edit().putInt("PHOTO_SIZE", size).commit();
	}

	/**
	 * ���복��ID
	 * 
	 * @param evalId
	 */
	public void saveTempVehId(String evalId) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("TEMP_VEHICLE_ID", evalId).commit();
	}

	/**
	 * ��ȡ����ID
	 * 
	 * @return ����ID
	 */
	public String getTempVehId() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("TEMP_VEHICLE_ID", null);
	}

	// ��¼����һ���߳̽��鿱Ա��Ӧ�ļ۸񷽰����浽�����ڴ���
	public void saveJgfa(String jglist) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_JGFALIST", jglist).commit();
	}
	
	// ��¼����һ���߳̽��鿱Ա��Ӧ�ļ۸񷽰����浽�����ڴ���
		public void saveManHourList(String manHourList) {
			SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
			eval.edit().putString("EVAL_MANHOURLIST", manHourList).commit();
		}

	// ��ȡ�۸񷽰�
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
				SpinnerItem si0 = new SpinnerItem("0","��ѡ����");
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
	// ��ȡά�޷���
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
	// ��ȡ�۸񷽰�Id
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
	 * ���ػ����Ʒ����
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
	 * ���ػ���ĳ�����
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

	// ��ͨ�����Ի�ȡ�ֻ��ţ��ƶ��������У�����������ȡ����,�����Ҫ����ֻ��ţ����û�������뷢�Ͷ��Ż�ȡ
	public String getPhoneNumber() {
		// �����绰�������ֻ���������
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// ��ȡ�ֻ�����
		return tm.getLine1Number();
	}

	/**
	 * ����reportNO
	 * 
	 * @param reportNo
	 */
	public void saveReportNo(String reportNo) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("reportNo", reportNo).commit();
	}

	/**
	 * �õ������reportNO
	 * 
	 * @return
	 */
	public String getReportNo() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("reportNo", null);
	}

	/**
	 * ���永����ѯ����
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
	 * �����жϷ����Ƿ�����.
	 * 
	 * @param activity
	 * @param className
	 *            �жϵķ�������
	 * @return true ������ false ��������
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
	 * ������Ҫ������Ϣ
	 */
	public void saveUpdateVer(String ver) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString("EVAL_VER", ver).commit();
	}

	/**
	 * ��ȡ��Ҫ������Ϣ
	 */
	public String getUpdateVer() {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		return eval.getString("EVAL_VER", null);
	}

	/**
	 * �ݴ��������
	 * 
	 * @param total
	 */
	public void savePartCount(int total) {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		eval.edit().putInt("PART_COUNT", total).commit();
	}

	/**
	 * ��ȡ��Ҫ������Ϣ
	 */
	public int getPartCount() {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		return eval.getInt("PART_COUNT", 0);
	}

	/**
	 * γ����ʱ����
	 * 
	 * @param total
	 */
	public void saveTmpLat(float lat) {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		eval.edit().putFloat("TMP_LAT", lat).commit();
	}

	/**
	 * ��ȡγ����ʱ����
	 */
	public float getTmpLat() {
		SharedPreferences eval = context.getSharedPreferences("PART", 0);
		return eval.getFloat("TMP_LAT", 0);
	}

	/**
	 * �־û�����
	 */
	public void saveCookie(String key, String value) {
		SharedPreferences eval = context.getSharedPreferences("JYEVALUSER", 0);
		eval.edit().putString(key, value).commit();
	}

	/**
	 * ��ȡ�־û�����
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
	// �����˻غ����޸ı��
	public void saveDanWeiFlag(String flag) {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		eval.edit().putString("EVAL_CARTYPE", flag).commit();
	}

	// ��ȡ�˻غ����޸ı��
	public String getDanWeiFlag() {
		SharedPreferences eval = context.getSharedPreferences("JYEVAL", 0);
		return eval.getString("EVAL_CARTYPE", "");
	}

	public void saveBxgsgz(String bxgsgz) {
		// TODO ���汨���Ź���
		SharedPreferences eval = context.getSharedPreferences("bxgsgz", 0);
		eval.edit().putString("bxgsgz", bxgsgz).commit();
	}
	public String getBxgsgz() {
		SharedPreferences eval = context.getSharedPreferences("bxgsgz", 0);
		return eval.getString("bxgsgz", null);
	}
	public void saveSfcp(String sfcp) {
		// TODO ����ʡ�ݳ��ƺ�
		SharedPreferences eval = context.getSharedPreferences("sfcp", 0);
		eval.edit().putString("sfcp", sfcp).commit();
	}
	public String getSfcp() {
		SharedPreferences eval = context.getSharedPreferences("sfcp", 0);
		return eval.getString("sfcp", null);
	}
}
