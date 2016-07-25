package com.jy.recycle.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import com.jy.recycle.util.DBUtil;

import android.database.Cursor;

public class ClaimEval implements Serializable {

	private static final long serialVersionUID = 1L;

	public long _id;
	/** 定损单号 */
	public String taskNo;
	/** 险别代码 */
	public String itemCode;
	/** 车牌号 */
	public String plateNo;
	/** 号牌种类 */
	public String plateType;
	/** 损失程度 */
	public String lossLevel;
	/** 是否\n现场定损 */
	public String repairSiteType;
	/** 修理厂名称 */
	public String repairTypeName;
	/** 定损时间 */
	public String evalDate;
	/**	 */
	public String vehicleId;
	/** 整车编码 */
	public String vehicleCode;
	/**	 */
	public String vehicleName;
	/** 品牌 */
	public String brandName;
	/** 车系名称 */
	public String familyName;
	/** 车系名称 */
	public String familyType;
	/** 车组名称 */
	public String groupName;
	/** 价格方案 */
	public String priceCode;
	/**	 */
	public String priceName;
	/** 管理费比例 */
	public double manageRate;
	/**	 */
	public double fitSum;
	/**	 */
	public double repairSum;
	/**	 */
	public double remainsSum;
	/**	 */
	public double evalSum;
	/** 排量 */
	public String vehiclePail;
	/** vin */
	public String vinCode;
	/** 驾驶员姓名 */
	public String driverName;
	/** 驾驶员身份证号 */
	public String driverCredNo;

	/** 定损方式 */
	public String evalType;// 首次定损，二次定损
	/** 快赔标志 */
	public String quickPayFlag;
	/** 代位求偿标记 */
	public String subrogationFlag;//
	/** 第二定损人 */
	public String secondEvalPerson;//
	/** 组织代码 */
	public String orgCode;
	/** 是否追债责任方 */
	public String responsibleFlag;


	/** 定损员代码 */
	public String handlerCode;
	/** 价格来源名称 */
	public String priceSourceName;
	/** 价格来源代码 */
	public String priceSource;
	/** 车型发动机类型 */
	public String vehicleFadong;
	/** 车型变速器类型 */
	public String vehicleBian;
	/** 车型备注 */
	public String vehicleMark;
	/** 年款 */
	public String vehYearType;

	public ClaimEval() {

	}

	public ClaimEval(Cursor cursor) {
		_id = cursor.getLong(cursor.getColumnIndex("_id"));
		// 定型信息
		vehicleId = cursor.getString(cursor.getColumnIndex("VEHICLE_ID"));
		vehicleName = cursor.getString(cursor.getColumnIndex("VEHICLE_NAME"));
		vehicleCode = cursor.getString(cursor.getColumnIndex("VEHICLE_CODE"));
		brandName = cursor.getString(cursor.getColumnIndex("BRAND_NAME"));
		familyName = cursor.getString(cursor.getColumnIndex("FAMILY_NAME"));
		groupName = cursor.getString(cursor.getColumnIndex("GROUP_NAME"));

		vehiclePail = cursor.getString(cursor.getColumnIndex("VEHICLE_PAIL"));

		plateNo = cursor.getString(cursor.getColumnIndex("PLATE_NO"));

		lossLevel = cursor.getString(cursor.getColumnIndex("LOSS_LEVEL"));
		repairSiteType = cursor.getString(cursor
				.getColumnIndex("REPAIR_SITE_TYPE"));
		evalDate = cursor.getString(cursor.getColumnIndex("EVAL_DATE"));
		evalType = cursor.getString(cursor.getColumnIndex("EVAL_TYPE"));
		manageRate = cursor.getDouble(cursor.getColumnIndex("MANAGE_RATE"));
		quickPayFlag = cursor.getString(cursor.getColumnIndex("QUICKPAY_FLAG"));
		subrogationFlag = cursor.getString(cursor
				.getColumnIndex("SUBROGATION_FLAG"));
		secondEvalPerson = cursor.getString(cursor
				.getColumnIndex("SECOND_EVAL_PERSON"));
		plateType = cursor.getString(cursor.getColumnIndex("PLATE_TYPE"));
		// 合计信息
		evalSum = cursor.getDouble(cursor.getColumnIndex("EVAL_SUM"));
		fitSum = cursor.getDouble(cursor.getColumnIndex("FIT_SUM"));
		repairSum = cursor.getDouble(cursor.getColumnIndex("REPAIR_SUM"));
		remainsSum = cursor.getDouble(cursor.getColumnIndex("REMAINS_SUM"));

		itemCode = cursor.getString(cursor.getColumnIndex("ITEM_CODE"));
		repairTypeName = cursor.getString(cursor
				.getColumnIndex("REPAIR_SITE_NAME"));
		vinCode = cursor.getString(cursor.getColumnIndex("VIN_CODE"));

		priceCode = cursor.getString(cursor.getColumnIndex("PRICE_CODE"));
		priceName = cursor.getString(cursor.getColumnIndex("PRICE_NAME"));

		taskNo = cursor.getString(cursor.getColumnIndex("TASK_NO"));
		driverName = cursor.getString(cursor.getColumnIndex("DRIVER_NAME"));
		driverCredNo = cursor
				.getString(cursor.getColumnIndex("DRIVER_CRED_NO"));
		orgCode = cursor
				.getString(cursor.getColumnIndex("REPAIR_FACTORY_CODE"));
		responsibleFlag = cursor.getString(cursor
				.getColumnIndex("RESPONSIBLE_FLAG"));
		handlerCode = DBUtil.getString(cursor, "HANDLER_CODE");
		priceSource = DBUtil.getString(cursor, "PRICE_SOURCE");
		priceSourceName = DBUtil.getString(cursor, "PRICE_SOURCE_NAME");
		vehicleFadong = DBUtil.getString(cursor, "VEHICLE_FADONG");
		vehicleBian = DBUtil.getString(cursor, "VEHICLE_BIAN");
		vehicleMark = DBUtil.getString(cursor, "VEHICLE_MARK");
		vehYearType = DBUtil.getString(cursor, "VEHYEAR_TYPE");
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}




	public void setVehYearType(String vehYearType) {
		this.vehYearType = vehYearType;
	}

	
}
