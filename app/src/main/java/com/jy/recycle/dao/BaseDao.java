package com.jy.recycle.dao;

import android.database.Cursor;

public class BaseDao {
	protected DBTools db;

	public BaseDao() {
		db = DBTools.getInstance();
	}


	/**
	 * @param evalId
	 * @return
	 */
	protected int getSerialNo(String table, Long evalId) {
		Cursor cursor = db.querySql("select max(SERIAL_NO) from ".concat(table)
				.concat(" where EVAL_ID = ?"), new String[] { String
				.valueOf(evalId) });
		if (cursor != null && cursor.moveToFirst()) {
			return cursor.getInt(0) + 1;
		}
		return 1;
	}
}
