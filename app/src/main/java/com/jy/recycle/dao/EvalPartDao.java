package com.jy.recycle.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.jy.recycle.client.response.EvalPartInfo;
import com.jy.recycle.util.SharedData;

public class EvalPartDao extends BaseDao {

	public static final String TABLE = "M_EVAL_PART";

	private static EvalPartDao epd;

	public static EvalPartDao getInstance() {
		if (epd == null) {
			epd = new EvalPartDao();
		}
		return epd;
	}

	private EvalPartDao() {
	}

	/**
	 * 增加零件先判断是否已经有此零件了
	 * 
	 * @param evalId
	 * @param partName
	 * @return
	 */
	public boolean getExistsEvalFits(Long evalId, String partName) {
		boolean flag = false;
		if (evalId != null && !evalId.equals("")) {
			String[] arg = { String.valueOf(evalId), partName };
			Cursor cur = db.querySql("select * from " + TABLE + " where "
					+ Columns.EVAL_ID + " =? and " + Columns.PART_STANDARD
					+ "=?", arg);
			if (cur != null && cur.getCount() > 0) {
				flag = true;
			}
			if (cur != null) {
				cur.close();
			}
		}
		return flag;
	}

	/**
	 * 新增零件
	 * 
	 * @param value
	 */
	public long insertEvalFits(EvalPartInfo evalPartInfo) {
		ContentValues values = new ContentValues();
		// values.put(Columns._id, evalPartInfo.get_id());
		values.put(Columns.EVAL_ID, evalPartInfo.getEvalId());
		values.put(Columns.PART_ID, evalPartInfo.getPartId());
		int i = SharedData.data().getSerialNo() + 1;
		SharedData.data().saveSerialNo(i);
		values.put(Columns.SERIAL_NO, i);

		values.put(Columns.ORIGINAL_ID, evalPartInfo.getOriginalId());
		values.put(Columns.ORIGINAL_NAME, evalPartInfo.getOriginalName());
		values.put(Columns.PART_STANDARD_CODE,
				evalPartInfo.getPartStandardCode());
		values.put(Columns.PART_STANDARD, evalPartInfo.getPartStandard());
		values.put(Columns.PART_GROUP_COD, evalPartInfo.getPartGroupCode());
		values.put(Columns.PART_GROUP_NAME, evalPartInfo.getPartGroupName());
		values.put(Columns.LOSS_PRICE, evalPartInfo.getLossPrice());
		values.put(Columns.REPAIR_SITE_PRICE, evalPartInfo.getRepairSitePrice());
		values.put(Columns.LOSS_COUNT, evalPartInfo.getLossCount());
		values.put(Columns.SUM_PRICE, evalPartInfo.getSumPrice());
		values.put(Columns.SELF_CONFIG_FLAG, evalPartInfo.getSelfConfigFlag());
		values.put(Columns.CHG_COMP_SET_CODE, evalPartInfo.getChgCompSetCode());
		values.put(Columns.CHG_COMP_SET_ID, evalPartInfo.getChgCompSetId());
		values.put(Columns.CHG_COMP_SET_NAME, evalPartInfo.getChgCompSetName());
		values.put(Columns.CHG_REF_PRICE, evalPartInfo.getChgRefPrice());
		values.put(Columns.CHG_LOC_PRICE, evalPartInfo.getChgLocPrice());

		values.put(Columns.REF_PRICE1, evalPartInfo.getRefPrice1());
		values.put(Columns.REF_PRICE2, evalPartInfo.getRefPrice2());
		values.put(Columns.REF_PRICE3, evalPartInfo.getRefPrice3());
		values.put(Columns.LOC_PRICE1, evalPartInfo.getLocPrice1());
		values.put(Columns.LOC_PRICE2, evalPartInfo.getLocPrice2());
		values.put(Columns.LOC_PRICE3, evalPartInfo.getLocPrice3());

		values.put(Columns.IF_REMAIN, evalPartInfo.getIfRemain());
		values.put(Columns.REMNANT, evalPartInfo.getRemnant());
		values.put(Columns.INSURE_TERM, evalPartInfo.getInsureTerm());
		values.put(Columns.INSURE_TERM_CODE, evalPartInfo.getInsureTermCode());
		values.put(Columns.REMARK, evalPartInfo.getRemark());
		values.put(Columns.JY_SYSTEM_ID, evalPartInfo.getJySystemId());

		values.put(Columns.HEJIA_PRICE, evalPartInfo.getAuditPrice());
		// values.put(Columns.HEJIA_SUM, evalPartInfo.getHejiaSum());
		values.put(Columns.HEJIA_REMARK, evalPartInfo.getAuditMemo());
		values.put(Columns.HEJIA_STATUS, evalPartInfo.getAuditState());
		values.put(Columns.FORHEJIA_REMARK, evalPartInfo.getLossOption());

		values.put(Columns.LOCAL_FLAG, evalPartInfo.getLocalFlag());
		values.put(Columns.DELETE_FLAG, evalPartInfo.getDeleteFlag());
		values.put(Columns.CARE_FLAG, evalPartInfo.getCareState());
		values.put(Columns.OLD_DETAIL, evalPartInfo.getOldDetail());
		values.put(Columns.GOOD_LIST_ID, evalPartInfo.getGoodListId());
		values.put(Columns.EDIT_FLAG, evalPartInfo.getEditFlag());
		values.put(Columns.HUISHOU_FLAG, evalPartInfo.getHuishouFlag());

		long partId = db.insert(TABLE, null, values);
		return partId;
	}

	/**
	 * 获取零件列表
	 * 
	 * @param evalId
	 * @return
	 */
	public ArrayList<EvalPartInfo> getListEvalPart(Long evalId) {
		ArrayList<EvalPartInfo> partList = new ArrayList<EvalPartInfo>();

		String[] arg = { String.valueOf(evalId) };

		Cursor cursor = db.querySql("select * from " + TABLE + " where "
				+ Columns.EVAL_ID + " =?", arg);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				EvalPartInfo partInfo = getEvalPartByCursor(cursor);
				partList.add(partInfo);
			}
			cursor.close();
		}

		return partList;
	}

	/**
	 * 读取配件数据记录了
	 * 
	 * @param cursor
	 * @return
	 */
	private EvalPartInfo getEvalPartByCursor(Cursor cursor) {
		EvalPartInfo partInfo = new EvalPartInfo();

		partInfo.set_id(cursor.getLong(cursor.getColumnIndex(Columns._id)));
		partInfo.setEvalId(cursor.getLong(cursor
				.getColumnIndex(Columns.EVAL_ID)));
		partInfo.setPartId(cursor.getString(cursor
				.getColumnIndex(Columns.PART_ID)));
		partInfo.setSerialNo(cursor.getInt(cursor
				.getColumnIndex(Columns.SERIAL_NO)));
		partInfo.setOriginalId(cursor.getString(cursor
				.getColumnIndex(Columns.ORIGINAL_ID)));
		partInfo.setOriginalName(cursor.getString(cursor
				.getColumnIndex(Columns.ORIGINAL_NAME)));
		partInfo.setPartStandardCode(cursor.getString(cursor
				.getColumnIndex(Columns.PART_STANDARD_CODE)));
		partInfo.setPartStandard(cursor.getString(cursor
				.getColumnIndex(Columns.PART_STANDARD)));
		partInfo.setPartGroupCode(cursor.getString(cursor
				.getColumnIndex(Columns.PART_GROUP_COD)));
		partInfo.setPartGroupName(cursor.getString(cursor
				.getColumnIndex(Columns.PART_GROUP_NAME)));
		partInfo.setLossPrice(cursor.getDouble(cursor
				.getColumnIndex(Columns.LOSS_PRICE)));
		partInfo.setRepairSitePrice(cursor.getDouble(cursor
				.getColumnIndex(Columns.REPAIR_SITE_PRICE)));
		partInfo.setLossCount(cursor.getInt(cursor
				.getColumnIndex(Columns.LOSS_COUNT)));
		partInfo.setSumPrice(cursor.getDouble(cursor
				.getColumnIndex(Columns.SUM_PRICE)));
		partInfo.setSelfConfigFlag(cursor.getString(cursor
				.getColumnIndex(Columns.SELF_CONFIG_FLAG)));
		partInfo.setChgCompSetCode(cursor.getString(cursor
				.getColumnIndex(Columns.CHG_COMP_SET_CODE)));
		partInfo.setChgCompSetId(cursor.getString(cursor
				.getColumnIndex(Columns.CHG_COMP_SET_ID)));
		partInfo.setChgCompSetName(cursor.getString(cursor
				.getColumnIndex(Columns.CHG_COMP_SET_NAME)));
		partInfo.setChgRefPrice(cursor.getDouble(cursor
				.getColumnIndex(Columns.CHG_REF_PRICE)));
		partInfo.setChgLocPrice(cursor.getDouble(cursor
				.getColumnIndex(Columns.CHG_LOC_PRICE)));

		partInfo.setRefPrice1(cursor.getDouble(cursor
				.getColumnIndex(Columns.REF_PRICE1)));
		partInfo.setRefPrice2(cursor.getDouble(cursor
				.getColumnIndex(Columns.REF_PRICE2)));
		partInfo.setRefPrice3(cursor.getDouble(cursor
				.getColumnIndex(Columns.REF_PRICE3)));
		partInfo.setLocPrice1(cursor.getDouble(cursor
				.getColumnIndex(Columns.LOC_PRICE1)));
		partInfo.setLocPrice2(cursor.getDouble(cursor
				.getColumnIndex(Columns.LOC_PRICE2)));
		partInfo.setLocPrice3(cursor.getDouble(cursor
				.getColumnIndex(Columns.LOC_PRICE3)));

		partInfo.setIfRemain(cursor.getString(cursor
				.getColumnIndex(Columns.IF_REMAIN)));
		partInfo.setRemnant(cursor.getDouble(cursor
				.getColumnIndex(Columns.REMNANT)));
		partInfo.setInsureTerm(cursor.getString(cursor
				.getColumnIndex(Columns.INSURE_TERM)));
		partInfo.setInsureTermCode(cursor.getString(cursor
				.getColumnIndex(Columns.INSURE_TERM_CODE)));
		partInfo.setRemark(cursor.getString(cursor
				.getColumnIndex(Columns.REMARK)));
		partInfo.setJySystemId(cursor.getString(cursor
				.getColumnIndex(Columns.JY_SYSTEM_ID)));

		partInfo.setLossOption(cursor.getString(cursor
				.getColumnIndex(Columns.FORHEJIA_REMARK)));
		partInfo.setAuditPrice(cursor.getDouble(cursor
				.getColumnIndex(Columns.HEJIA_PRICE)));
		partInfo.setAuditMemo(cursor.getString(cursor
				.getColumnIndex(Columns.HEJIA_REMARK)));
		partInfo.setAuditState(cursor.getString(cursor
				.getColumnIndex(Columns.HEJIA_STATUS)));

		partInfo.setLocalFlag(cursor.getString(cursor
				.getColumnIndex(Columns.LOCAL_FLAG)));
		partInfo.setLocPriceJmFlag(cursor.getString(cursor
				.getColumnIndex(Columns.LOCAL_FLAG)));
		partInfo.setCareState(cursor.getString(cursor
				.getColumnIndex(Columns.CARE_FLAG)));
		partInfo.setDeleteFlag(cursor.getString(cursor
				.getColumnIndex(Columns.DELETE_FLAG)));
		partInfo.setOldDetail(cursor.getString(cursor
				.getColumnIndex(Columns.OLD_DETAIL)));
		partInfo.setGoodListId(cursor.getString(cursor
				.getColumnIndex(Columns.GOOD_LIST_ID)));
		partInfo.setEditFlag(cursor.getString(cursor
				.getColumnIndex(Columns.EDIT_FLAG)));
		partInfo.setHuishouFlag(cursor.getString(cursor
				.getColumnIndex(Columns.HUISHOU_FLAG)));
		return partInfo;
	}

	/**
	 * 获取换件信息
	 * 
	 * @param id
	 * @return
	 */
	public EvalPartInfo getEvalPartById(String id) {
		String[] arg = { id };
		EvalPartInfo partInfo = null;
		Cursor cursor = db.querySql("select * from " + TABLE
				+ " where PART_ID=? ", arg);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				partInfo = getEvalPartByCursor(cursor);
			}
			cursor.close();
		}
		return partInfo;
	}

	/**
	 * 修改换件信息
	 * 
	 * @param evalPartInfo
	 */
	public void updateEvalPart(EvalPartInfo evalPartInfo) {

		ContentValues values = new ContentValues();
		// 二次点选件和自定义件允许修改原厂零件号
		if ("1".equals(evalPartInfo.getSelfConfigFlag())
				|| "2".equals(evalPartInfo.getSelfConfigFlag())) {
			values.put(Columns.ORIGINAL_ID, evalPartInfo.getOriginalId());
		}
		// 自定义件允许修改零件名称
		if ("1".equals(evalPartInfo.getSelfConfigFlag())) {
			values.put(Columns.PART_STANDARD, evalPartInfo.getPartStandard());
		}

		values.put(Columns.LOSS_PRICE, evalPartInfo.getLossPrice());
		values.put(Columns.REPAIR_SITE_PRICE, evalPartInfo.getRepairSitePrice());
		values.put(Columns.LOSS_COUNT, evalPartInfo.getLossCount());
		values.put(Columns.SUM_PRICE, evalPartInfo.getSumPrice());

		values.put(Columns.CHG_COMP_SET_CODE, evalPartInfo.getChgCompSetCode());
		values.put(Columns.CHG_COMP_SET_NAME, evalPartInfo.getChgCompSetName());

		values.put(Columns.IF_REMAIN, evalPartInfo.getIfRemain());
		values.put(Columns.REMNANT, evalPartInfo.getRemnant());

		values.put(Columns.INSURE_TERM, evalPartInfo.getInsureTerm());
		values.put(Columns.INSURE_TERM_CODE, evalPartInfo.getInsureTermCode());
		values.put(Columns.REMARK, evalPartInfo.getRemark());
		values.put(Columns.FORHEJIA_REMARK, evalPartInfo.getLossOption());
		values.put(Columns.DELETE_FLAG, evalPartInfo.getDeleteFlag());
		values.put(Columns.CARE_FLAG, evalPartInfo.getCareState());

		String[] arg = { String.valueOf(evalPartInfo.get_id()) };
		try {
			db.update(TABLE, values, "_id=?", arg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	/**
	 * 上传成功后更新换件信息
	 * 
	 * @param part
	 */
	public boolean updateAfterUploadFinish(EvalPartInfo part) {
		long result = -1;
		ContentValues values = new ContentValues();
		// 自定义件允许修改零件名称
		values.put(Columns.PART_ID, part.getPartId());

		String[] arg = { String.valueOf(part.get_id()) };
		try {
			result = db.update(TABLE, values, "_id=?", arg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return result > 0;
	}

	/**
	 * 删除换件信息
	 * 
	 * @param id
	 */
	public void delEvalPartByEvalId(long evalId) {
		String[] arg = { String.valueOf(evalId) };
		try {
			db.delete(TABLE, "EVAL_ID=?", arg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 获取换件合计
	 * 
	 * @param evalId
	 * @return
	 */
	public double getPartLossSum(Long evalId) {
		double partLossSum = 0;

		String[] arg = { String.valueOf(evalId) };
		Cursor cursor = db.querySql("select sum(" + Columns.SUM_PRICE + ") as "
				+ Columns.SUM_PRICE + " from " + TABLE + " where "
				+ Columns.EVAL_ID + " =?", arg);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				partLossSum = cursor.getDouble(cursor
						.getColumnIndex(Columns.SUM_PRICE));
			}
			cursor.close();
		}
		return partLossSum;
	}

	/**
	 * 获取残值合计
	 * 
	 * @param evalId
	 * @return
	 */
	public double geRemainsSum(Long evalId) {
		double partLossSum = 0;

		String[] arg = { String.valueOf(evalId) };
		Cursor cursor = db.querySql("select sum(" + Columns.REMNANT + ") as "
				+ Columns.REMNANT + " from " + TABLE + " where "
				+ Columns.EVAL_ID + " =?", arg);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				partLossSum = cursor.getDouble(cursor
						.getColumnIndex(Columns.REMNANT));
			}
			cursor.close();
		}
		return partLossSum;
	}

	/**
	 * 删除换件信息
	 * 
	 * @param id
	 */
	public void delEvalPart(String id) {
		String[] arg = { id };
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
	 * 修改换件信息
	 * 
	 * @param evalPartInfo
	 */
	public void updatePartTotal(String partTotal, String id) {

		ContentValues values = new ContentValues();
		// 二次点选件和自定义件允许修改原厂零件号
		values.put(Columns.SUM_PRICE, partTotal);

		// 修理厂报价没写
		String[] arg = { id };
		try {
			db.update(TABLE, values, "_id=?", arg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	public static final class Columns {
		public static final String HUISHOU_FLAG = "HUISHOU_FLAG";
		public static final String EDIT_FLAG = "EDIT_FLAG";
		public static final String GOOD_LIST_ID = "GOOD_LIST_ID";
		public static final String OLD_DETAIL = "OLD_DETAIL";
		public static final String _id = "_id";
		public static final String EVAL_ID = "EVAL_ID";
		public static final String PART_ID = "PART_ID";
		static final String SERIAL_NO = "SERIAL_NO";
		public static final String ORIGINAL_ID = "ORIGINAL_ID";
		public static final String ORIGINAL_NAME = "ORIGINAL_NAME";
		public static final String PART_STANDARD_CODE = "PART_STANDARD_CODE";
		public static final String PART_STANDARD = "PART_STANDARD";
		public static final String PART_GROUP_COD = "PART_GROUP_COD";
		public static final String PART_GROUP_NAME = "PART_GROUP_NAME";
		public static final String LOSS_PRICE = "LOSS_PRICE";
		public static final String REPAIR_SITE_PRICE = "REPAIR_SITE_PRICE";
		public static final String LOSS_COUNT = "LOSS_COUNT";
		public static final String SUM_PRICE = "SUM_PRICE";
		public static final String SELF_CONFIG_FLAG = "SELF_CONFIG_FLAG";
		public static final String CHG_COMP_SET_CODE = "CHG_COMP_SET_CODE";
		public static final String CHG_COMP_SET_ID = "CHG_COMP_SET_ID";
		public static final String CHG_COMP_SET_NAME = "CHG_COMP_SET_NAME";

		public static final String CHG_REF_PRICE = "CHG_REF_PRICE";
		public static final String CHG_LOC_PRICE = "CHG_LOC_PRICE";

		public static final String REF_PRICE1 = "REF_PRICE1";
		public static final String REF_PRICE2 = "REF_PRICE2";
		public static final String REF_PRICE3 = "REF_PRICE3";
		public static final String LOC_PRICE1 = "LOC_PRICE1";
		public static final String LOC_PRICE2 = "LOC_PRICE2";
		public static final String LOC_PRICE3 = "LOC_PRICE3";

		public static final String IF_REMAIN = "IF_REMAIN";
		public static final String REMNANT = "REMNANT";
		public static final String INSURE_TERM = "INSURE_TERM";
		public static final String INSURE_TERM_CODE = "INSURE_TERM_CODE";
		public static final String REMARK = "REMARK";
		public static final String JY_SYSTEM_ID = "JY_SYSTEM_ID";

		public static final String HEJIA_PRICE = "HEJIA_PRICE";
		public static final String HEJIA_SUM = "HEJIA_SUM";
		public static final String HEJIA_REMARK = "HEJIA_REMARK";
		public static final String HEJIA_STATUS = "HEJIA_STATUS";
		public static final String FORHEJIA_REMARK = "FORHEJIA_REMARK";
		public static final String LOCAL_FLAG = "LOCAL_FLAG";
		public static final String DELETE_FLAG = "DELETE_FLAG";
		public static final String CARE_FLAG = "CARE_FLAG";

	}

	/**
	 * 增加二维码先判断是否已经有此二维码了
	 * 
	 * @param evalId
	 * @param partName
	 * @return
	 */
	public boolean getExistsErWeiMa(Long evalId, String name) {
		boolean flag = false;
		if (evalId != null && !evalId.equals("")) {
			Cursor cur = null;
			try {
				String[] arg = { name };
				cur = db.querySql("select * from " + TABLE + " where "
						+ Columns.INSURE_TERM + "=?", arg);
				if (cur != null && cur.getCount() > 0) {
					flag = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (cur != null) {
					cur.close();
					cur = null;
				}
			}
		}
		return flag;
	}
	/**
	 * 增加二维码先判断是否已经有此二维码了
	 * 
	 * @param evalId
	 * @param partName
	 * @return
	 */
	public boolean getExistsErWeiMa(Long evalId,String partId, String name) {
		boolean flag = false;
		if (evalId != null && !evalId.equals("")) {
			Cursor cur = null;
			try {
				String[] arg = { String.valueOf(evalId),name,partId };
				cur = db.querySql("select * from " + TABLE + " where "
						+ Columns.EVAL_ID + " =? and "+Columns.INSURE_TERM + " =? and "+Columns.PART_ID+" !=?" , arg);
				Log.i("partId2", partId);
				if (cur != null && cur.getCount() > 0) {
					Log.i("partId2", partId);
					flag = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (cur != null) {
					cur.close();
					cur = null;
				}
			}
		}
		return flag;
	}

	public void delEvalPart(long evalId, String partId) {
		// TODO Auto-generated method stub
		String[] arg = { String.valueOf(evalId),partId };
		try {
			db.delete(TABLE, "EVAL_ID=?  and" +" PART_ID=?", arg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

}
