package com.jy.recycle.pojo;

import java.io.Serializable;

import android.database.Cursor;

public class ClaimImage implements Serializable {
	private static final long serialVersionUID = 1L;

	public long _id;
	public String taskNo;
	public String reportNo;
	public String imgName;
	public String imgPath;
	public String imgSuffix;
	public String imgDesc;
	public String imgLatitude;
	public String imgLongitude;
	public String imgDate;

	/** 任务类型 */
	public String taskType;


	public ClaimImage(Cursor cursor) {
		_id = cursor.getLong(cursor.getColumnIndex("_id"));
		taskNo = cursor.getString(cursor.getColumnIndex("TASK_NO"));
		reportNo = cursor.getString(cursor.getColumnIndex("REPORT_NO"));
		imgName = cursor.getString(cursor.getColumnIndex("IMG_NAME"));
		imgPath = cursor.getString(cursor.getColumnIndex("IMG_PATH"));
		imgSuffix = cursor.getString(cursor.getColumnIndex("IMG_SUFFIX"));
		imgDesc = cursor.getString(cursor.getColumnIndex("IMG_DESC"));
		imgLatitude = cursor.getString(cursor.getColumnIndex("IMG_LATITUDE"));
		imgLongitude = cursor.getString(cursor.getColumnIndex("IMG_LONGITUDE"));
		imgDate = cursor.getString(cursor.getColumnIndex("IMG_DATE"));
		taskType = cursor.getString(cursor.getColumnIndex("IMG_TASK_TYPE"));
	}
}
