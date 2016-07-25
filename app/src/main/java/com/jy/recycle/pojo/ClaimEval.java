package com.jy.recycle.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import com.jy.recycle.util.DBUtil;

import android.database.Cursor;

public class ClaimEval implements Serializable {

	private static final long serialVersionUID = 1L;

	public long _id;
	/** ���𵥺� */
	public String taskNo;
	/** �ձ���� */
	public String itemCode;
	/** ���ƺ� */
	public String plateNo;
	/** �������� */
	public String plateType;
	/** ��ʧ�̶� */
	public String lossLevel;
	/** �Ƿ�\n�ֳ����� */
	public String repairSiteType;
	/** �������� */
	public String repairTypeName;
	/** ����ʱ�� */
	public String evalDate;
	/**	 */
	public String vehicleId;
	/** �������� */
	public String vehicleCode;
	/**	 */
	public String vehicleName;
	/** Ʒ�� */
	public String brandName;
	/** ��ϵ���� */
	public String familyName;
	/** ��ϵ���� */
	public String familyType;
	/** �������� */
	public String groupName;
	/** �۸񷽰� */
	public String priceCode;
	/**	 */
	public String priceName;
	/** ����ѱ��� */
	public double manageRate;
	/**	 */
	public double fitSum;
	/**	 */
	public double repairSum;
	/**	 */
	public double remainsSum;
	/**	 */
	public double evalSum;
	/** ���� */
	public String vehiclePail;
	/** vin */
	public String vinCode;
	/** ��ʻԱ���� */
	public String driverName;
	/** ��ʻԱ���֤�� */
	public String driverCredNo;

	/** ����ʽ */
	public String evalType;// �״ζ��𣬶��ζ���
	/** �����־ */
	public String quickPayFlag;
	/** ��λ�󳥱�� */
	public String subrogationFlag;//
	/** �ڶ������� */
	public String secondEvalPerson;//
	/** ��֯���� */
	public String orgCode;
	/** �Ƿ�׷ծ���η� */
	public String responsibleFlag;


	/** ����Ա���� */
	public String handlerCode;
	/** �۸���Դ���� */
	public String priceSourceName;
	/** �۸���Դ���� */
	public String priceSource;
	/** ���ͷ��������� */
	public String vehicleFadong;
	/** ���ͱ��������� */
	public String vehicleBian;
	/** ���ͱ�ע */
	public String vehicleMark;
	/** ��� */
	public String vehYearType;

	public ClaimEval() {

	}

	public ClaimEval(Cursor cursor) {
		_id = cursor.getLong(cursor.getColumnIndex("_id"));
		// ������Ϣ
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
		// �ϼ���Ϣ
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
