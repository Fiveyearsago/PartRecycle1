package com.jy.recycle.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.jy.recycle.client.response.EvalLossInfo;

public class EvalLossInfoDao extends BaseDao {

	private final static String TABLE = "M_EVAL_LOSS_INFO";
	private final static String TABLE_PHPTO = "M_VINNO_PHONE";
	private static final String PART_TABLE = "M_EVAL_PART";

	/**
	 * 保存定损请求信息
	 * 
	 * @param evalLossInfo
	 * @return
	 */
	public long insertEvalInfo(EvalLossInfo evalLossInfo) {
		ContentValues values = new ContentValues();
		// values.put(Columns.USERNAME, evalLossInfo.getUserName());
		values.put(Columns.REPORT_ONE, evalLossInfo.getReportOne());
		values.put(Columns.REPORT_TWO, evalLossInfo.getReportTwo());
		values.put(Columns.REPORT_THREE, evalLossInfo.getReportThree());
		values.put(Columns.REPORT_FOUR, evalLossInfo.getReportFour());
		values.put(Columns.CARTEXT, evalLossInfo.getCarText());
		values.put(Columns.CARNO, evalLossInfo.getCarNo());
		values.put(Columns.PERSON_NAME, evalLossInfo.getPersonName());
		values.put(Columns.PERSON_TEL, evalLossInfo.getPersonTel());
		values.put(Columns.PERSON_ADD, evalLossInfo.getPersonAdd());
		values.put(Columns.VEH_BRAND_NAME, evalLossInfo.getVehBrandName());
		values.put(Columns.VEH_BRAND_CODE, evalLossInfo.getVehBrandCode());
		values.put(Columns.VEH_CERTAIN_ID, evalLossInfo.getVehCertainId());
		values.put(Columns.VEH_CERTAIN_NAME, evalLossInfo.getVehCertainName());
		values.put(Columns.VEH_CERTAIN_CODE, evalLossInfo.getVehCertainCode());
		values.put(Columns.VEH_SERI_NAME, evalLossInfo.getVehSeriName());
		values.put(Columns.VEH_SERI_ID, evalLossInfo.getVehSeriId());
		values.put(Columns.PLATE_NO, evalLossInfo.getPlateNo());
		values.put(Columns.VIN_NO, evalLossInfo.getVinNo());
		values.put(Columns.TI_HUO_SHANG, evalLossInfo.getTiHuoShang());
		values.put(Columns.CAR_TYPE, evalLossInfo.getCarProperty());
		values.put(Columns.STATE, evalLossInfo.getHsdState());
		values.put(Columns.REPAIR_FLAG, evalLossInfo.getRepairFlag());
		long evalId = db.insert(TABLE, null, values);
		return evalId;
	}

	/**
	 * 更新车型信息
	 * 
	 * @param evalLossInfo
	 * @return
	 */
	public long updateVehicleInfo(EvalLossInfo evalLossInfo) {
		ContentValues values = new ContentValues();
		// 厂家信息
		values.put(Columns.VEH_FACTORY_CODE,
				assureNotNull(evalLossInfo.getVehFactoryCode()));
		values.put(Columns.VEH_FACTORY_NAME,
				assureNotNull(evalLossInfo.getVehFactoryName()));
		// 品牌信息
		values.put(Columns.VEH_BRAND_CODE,
				assureNotNull(evalLossInfo.getVehBrandCode()));
		values.put(Columns.VEH_BRAND_NAME,
				assureNotNull(evalLossInfo.getVehBrandName()));
		// 车系信息
		values.put(Columns.VEH_SERI_CODE,
				assureNotNull(evalLossInfo.getVehSeriCode()));
		values.put(Columns.VEH_SERI_NAME,
				assureNotNull(evalLossInfo.getVehSeriName()));
		// 车组信息
		values.put(Columns.VEH_GROUP_CODE,
				assureNotNull(evalLossInfo.getVehGroupCode()));
		values.put(Columns.VEH_GROUP_NAME,
				assureNotNull(evalLossInfo.getVehGroupName()));
		// 车型信息
		values.put(Columns.VEH_CERTAIN_ID,
				assureNotNull(evalLossInfo.getVehCertainId()));
		values.put(Columns.VEH_CERTAIN_CODE,
				assureNotNull(evalLossInfo.getVehCertainCode()));
		values.put(Columns.VEH_CERTAIN_NAME,
				assureNotNull(evalLossInfo.getVehCertainName()));
		// 车辆种类信息
		values.put(Columns.VEH_KIND_CODE,
				assureNotNull(evalLossInfo.getVehKindCode()));
		values.put(Columns.VEH_KIND_ID,
				assureNotNull(evalLossInfo.getVehStandCertainId()));
		values.put(Columns.VEH_KIND_NAME,
				assureNotNull(evalLossInfo.getVehKindName()));
		values.put(Columns.VEH_YEAR_TYPE,
				assureNotNull(evalLossInfo.getVehYearType()));
		// 自定义标志
		values.put(Columns.SELF_CONFIG_FLAG,
				assureNotNull(evalLossInfo.getSelfConfigFlag()));
		values.put(Columns.ACTING_BRAND,
				assureNotNull(evalLossInfo.getActingBrand()));
		values.put(Columns.VIN_NO, assureNotNull(evalLossInfo.getVinNo()));
		// 更新
		String args[] = { String.valueOf(evalLossInfo.get_id()) };

		long evalId = db.update(TABLE, values, "_id = ? ", args);
		return evalId;
	}

	/**
	 * 确保不为null
	 * 
	 * @param value
	 * @return
	 */
	private String assureNotNull(String value) {
		return value == null ? "" : value;
	}

	/**
	 * 根据TaskId和CaseNo，获取定损单Id.如果没有，返回-1
	 * 
	 * @param taskId
	 * @return
	 */
	public long getEvalIdByTaskIdAndCaseNo(String taskId, String caseNo) {
		long evalId = -1;
		String[] arg = { taskId, caseNo };
		Cursor cursor = db
				.querySql("select _id from " + TABLE + " where "
						+ Columns.TASK_ID + " =? and " + Columns.CASE_NO
						+ " = ? ", arg);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				evalId = cursor.getLong(cursor.getColumnIndex(Columns._id));
			}
			cursor.close();
		}
		return evalId;
	}

	/**
	 * 根据LossNo和CaseNo，获取定损单Id.如果没有，返回-1
	 * 
	 * @param estimateNo
	 * @return
	 */
	public long getEvalIdByEstimateNoAndCaseNo(String estimateNo, String caseNo) {
		long evalId = -1;
		String[] arg = { estimateNo, caseNo };
		Cursor cursor = db.querySql("select _id from " + TABLE + " where "
				+ Columns.ESTIMATE_NO + " =? and " + Columns.CASE_NO + " = ? ",
				arg);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				evalId = cursor.getLong(cursor.getColumnIndex(Columns._id));
			}
			cursor.close();
		}
		return evalId;
	}

	/**
	 * 根据定损主键，获取定损信息
	 * 
	 * @param evalId
	 * @return
	 */
	public EvalLossInfo getEvalLossInfoByEvalId(long evalId) {
		EvalLossInfo evalLossInfo = null;
		String[] arg = { String.valueOf(evalId) };
		Cursor cursor = db.querySql(
				"select * from M_EVAL_LOSS_INFO where _id =?", arg);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				evalLossInfo = new EvalLossInfo();
				// 厂家信息
				evalLossInfo.setVehFactoryCode(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_FACTORY_CODE)));
				evalLossInfo.setVehFactoryName(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_FACTORY_NAME)));
				// 品牌信息
				evalLossInfo.setVehBrandCode(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_BRAND_CODE)));
				evalLossInfo.setVehBrandName(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_BRAND_NAME)));
				// 车系信息
				evalLossInfo.setVehSeriCode(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_SERI_CODE)));
				evalLossInfo.setVehSeriName(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_SERI_NAME)));
				evalLossInfo.setVehSeriId(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_SERI_ID)));
				// 车组信息
				evalLossInfo.setVehGroupCode(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_GROUP_CODE)));
				evalLossInfo.setVehGroupName(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_GROUP_NAME)));
				// 车型信息
				evalLossInfo.setVehCertainId(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_CERTAIN_ID)));
				evalLossInfo.setVehCertainCode(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_CERTAIN_CODE)));
				evalLossInfo.setVehCertainName(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_CERTAIN_NAME)));
				evalLossInfo.setVehBrandCode(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_BRAND_CODE)));
				// 车辆种类信息
				evalLossInfo.setVehKindCode(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_KIND_CODE)));
				evalLossInfo.setVehStandCertainId(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_KIND_ID)));
				evalLossInfo.setPlateNo(cursor.getString(cursor
						.getColumnIndex(Columns.PLATE_NO)));
				evalLossInfo.setVehKindName(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_KIND_NAME)));
				evalLossInfo.setVehYearType(cursor.getString(cursor
						.getColumnIndex(Columns.VEH_YEAR_TYPE)));
				evalLossInfo.setSelfConfigFlag(cursor.getString(cursor
						.getColumnIndex(Columns.SELF_CONFIG_FLAG)));
				// 基本信息
				evalLossInfo.setCarNo(cursor.getString(cursor
						.getColumnIndex(Columns.CARNO)));
				evalLossInfo.setPersonName(cursor.getString(cursor
						.getColumnIndex(Columns.PERSON_NAME)));
				evalLossInfo.setPersonTel(cursor.getString(cursor
						.getColumnIndex(Columns.PERSON_TEL)));
				evalLossInfo.setPersonAdd(cursor.getString(cursor
						.getColumnIndex(Columns.PERSON_ADD)));
				evalLossInfo.setRemark(cursor.getString(cursor
						.getColumnIndex(Columns.REMARKE)));
				evalLossInfo.setReportOne(cursor.getString(cursor
						.getColumnIndex(Columns.REPORT_ONE)));
				evalLossInfo.setReportTwo(cursor.getString(cursor
						.getColumnIndex(Columns.REPORT_TWO)));
				evalLossInfo.setReportThree(cursor.getString(cursor
						.getColumnIndex(Columns.REPORT_THREE)));
				evalLossInfo.setReportFour(cursor.getString(cursor
						.getColumnIndex(Columns.REPORT_FOUR)));
				evalLossInfo.setCarText(cursor.getString(cursor
						.getColumnIndex(Columns.CARTEXT)));
				evalLossInfo.setVinNo(cursor.getString(cursor
						.getColumnIndex(Columns.VIN_NO)));
				evalLossInfo.setTiHuoShang(cursor.getString(cursor
						.getColumnIndex(Columns.TI_HUO_SHANG)));
				evalLossInfo.setCarProperty(cursor.getString(cursor
						.getColumnIndex(Columns.CAR_TYPE)));
				evalLossInfo.setRepairFlag(cursor.getString(cursor
						.getColumnIndex(Columns.REPAIR_FLAG)));
			}
			cursor.close();
		}
		return evalLossInfo;
	}

	/**
	 * 定损单的字段信息
	 */
	public static class Columns {

		public final static String _id = "_id";
		// 请求信息
		public final static String USER_CODE = "USER_CODE";
		public final static String PASSWORD = "PASSWORD ";
		public final static String REQUEST_TYPE = "REQUEST_TYPE";
		public final static String POWER = "POWER";
		public final static String EVAL_RETURN_PAK_URL = "EVAL_RETURN_PAK_URL";
		public final static String EVAL_RETURN_CLA_URL = "EVAL_RETURN_CLA_URL";
		// 退回标记
		public final static String REBACK_FLAG = "REBACK_FLAG";
		// 请求定损单信息
		public final static String LOSS_NO = "LOSS_NO";
		public final static String CASE_NO = "CASE_NO";
		public final static String ESTIMATE_NO = "ESTIMATE_NO";
		public final static String INSURE_VEHICLE_NAME = "INSURE_VEHICLE_NAME";
		public final static String INSURE_VEHICLE_CODE = "INSURE_VEHICLE_CODE";
		public final static String COM_CODE = "COM_CODE";
		public final static String HANDLER_CODE = "HANDLER_CODE";
		public final static String MAN_HOUR = "MAN_HOUR";
		// 车型信息
		public final static String VEH_KIND_CODE = "VEH_KIND_CODE";
		public final static String VEH_KIND_NAME = "VEH_KIND_NAME";
		public final static String VEH_CERTAIN_ID = "VEH_CERTAIN_ID";
		public final static String VEH_CERTAIN_CODE = "VEH_CERTAIN_CODE";
		public final static String VEH_CERTAIN_NAME = "VEH_CERTAIN_NAME";
		public final static String VEH_GROUP_NAME = "VEH_GROUP_NAME";
		public final static String VEH_GROUP_CODE = "VEH_GROUP_CODE";
		public final static String VEH_SERI_CODE = "VEH_SERI_CODE";
		public final static String VEH_SERI_NAME = "VEH_SERI_NAME";
		public static final String VEH_SERI_ID = "VEH_SERI_ID";
		public final static String VEH_YEAR_TYPE = "VEH_YEAR_TYPE";
		public final static String VEH_FACTORY_CODE = "VEH_FACTORY_CODE";
		public final static String VEH_FACTORY_NAME = "VEH_FACTORY_NAME";
		public final static String VEH_BRAND_CODE = "VEH_BRAND_CODE";
		public final static String VEH_BRAND_NAME = "VEH_BRAND_NAME";
		public final static String SELF_CONFIG_FLAG = "SELF_CONFIG_FLAG";
		public final static String REPORT_FOUR = "REPORT_FOUR";
		// 合计信息
		public final static String SUM_REMNANT = "SUM_REMNANT";
		public final static String MANAGE_FEE_RATE = "MANAGE_FEE_RATE";
		public final static String REMARKE = "REMARKE";
		public final static String REPAIR_MANAGEMENT = "REPAIR_MANAGEMENT";
		public final static String COMPANY_TYPE = "COMPANY_TYPE";
		public final static String ACTING_BRAND = "ACTING_BRAND";
		public final static String TASK_ID = "TASK_ID";
		public final static String PLATE_NO = "PLATE_NO";
		public final static String VEH_KIND_ID = "VEH_KIND_ID";

		public final static String CARNO = "CARNO";
		public final static String PERSON_NAME = "PERSON_NAME";
		public final static String PERSON_TEL = "PERSON_TEL";
		public final static String PERSON_ADD = "PERSON_ADD";
		public final static String REPORT_ONE = "REPORT_ONE";
		public final static String REPORT_TWO = "REPORT_TWO";
		public final static String REPORT_THREE = "REPORT_THREE";
		public final static String LOSS_REMNANT = "LOSS_REMNANT";
		public final static String CARTEXT = "CARTEXT";
		public final static String VIN_NO = "VIN_NO";
		public final static String TI_HUO_SHANG = "TI_HUO_SHANG";
		public final static String CAR_TYPE = "CAR_TYPE";
		public final static String REPAIR_FLAG="REPAIR_FLAG";
		public final static String STATE = "MSTATE";
	}

	public long updateRemains(Long evalId, double remains) {
		ContentValues values = new ContentValues();
		// 合计残值信息
		values.put(Columns.SUM_REMNANT, remains);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updateLossNoAndEstimateNo(long evalId, String estimateNo) {
		ContentValues values = new ContentValues();
		values.put(Columns.LOSS_NO, estimateNo);
		values.put(Columns.ESTIMATE_NO, estimateNo);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;

	}


	public long updateRebackFlag(long evalId, String value) {
		ContentValues values = new ContentValues();
		values.put(Columns.REBACK_FLAG, value);
		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id=?", args);
		return 0;
	}

	public long updatercarText(long evalId, String resport) {
		ContentValues values = new ContentValues();
		// 更新管理费率
		values.put(Columns.CARTEXT, resport);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updateregist1(long evalId, String resport) {
		ContentValues values = new ContentValues();
		// 更新管理费率
		values.put(Columns.REPORT_ONE, resport);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updateregist2(long evalId, String resport) {
		ContentValues values = new ContentValues();
		// 更新管理费率
		values.put(Columns.REPORT_TWO, resport);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updateregist3(long evalId, String resport) {
		ContentValues values = new ContentValues();
		// 更新管理费率
		values.put(Columns.REPORT_THREE, resport);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updateCarNo(long evalId, String manageRate) {
		ContentValues values = new ContentValues();
		// 更新车牌号
		values.put(Columns.CARNO, manageRate);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updatePerson(long evalId, String person) {
		ContentValues values = new ContentValues();
		// 更新管理费率
		values.put(Columns.PERSON_NAME, person);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updatePersonTel(long evalId, String personTeL) {
		ContentValues values = new ContentValues();
		// 更新管理费率
		values.put(Columns.PERSON_TEL, personTeL);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	public long updateAddress(long evalId, String personAddress) {
		ContentValues values = new ContentValues();
		// 更新管理费率
		values.put(Columns.PERSON_ADD, personAddress);

		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
		return 0;
	}

	/**
	 * 删除定损单信息
	 * 
	 * @param evalId
	 */
	public void deleteEvalInfo(long evalId) {
		String[] arg = { String.valueOf(evalId) };
		try {
			db.beginTran();
			db.delete(TABLE, "_id=?", arg);
			db.setTranSuc();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTran();
		}
	}

	/**
	 * 删除suoyou定损单信息
	 * 
	 */
	public void deleteAllEvalInfo() {
		String[] arg = { String.valueOf(-1) };
		try {
			db.beginTran();
			db.delete(TABLE, "_id<>?", arg);
			db.delete(PART_TABLE, "_id<>?", arg);
			db.delete(TABLE_PHPTO, "_id<>?", arg);
			db.setTranSuc();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db!=null)
			db.endTran();
		}
	}

	public long initInsertEvalInfo() {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(Columns.STATE, "");
		long evalId = db.insert(TABLE, null, values);
		return evalId;
	}

	public long getHsdState() {
		String[] arg = { "" };
		Cursor cursor = db.querySql("select _id from " + TABLE + " where "
				+ Columns.STATE + " =? ", arg);

		long evalId = -1;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				evalId = cursor.getLong(cursor.getColumnIndex(Columns._id));
			}
			cursor.close();
		}

		return evalId;

	}

	public void updateTihuo(long evalId, String tihuoshangName) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		// 厂家信息
		values.put(Columns.TI_HUO_SHANG, tihuoshangName);
		// 更新
		String args[] = { String.valueOf(evalId) };
		db.update(TABLE, values, "_id = ? ", args);
	}

	public long updateregist4(long evalId, String string) {
		// TODO Auto-generated method stub
			ContentValues values = new ContentValues();
			values.put(Columns.REPORT_FOUR, string);

			String args[] = { String.valueOf(evalId) };
			db.update(TABLE, values, "_id = ? ", args);
			return 0;
	}
}
