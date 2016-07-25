package com.jy.recycle.dao;

import java.io.File;
import java.util.Map.Entry;

import com.jy.recycle.util.ClaimFlag;
import com.jy.recycle.util.Constants;
import com.jy.recycle.util.Loger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class LocalDb {

	private static LocalDb localDb = new LocalDb();
	
	private static boolean goFlag;

	private LocalDb() {
	}


	private static SQLiteDatabase database;

	public static synchronized LocalDb openDb(String path) {
		while(goFlag){
		}
		try {
			// 自动定型与获取本地价格方案冲突
			if(new File(DbConstants.BASE_PATH.concat(path)).exists()){
				database = SQLiteDatabase.openDatabase(DbConstants.BASE_PATH.concat(path),
						null, SQLiteDatabase.OPEN_READONLY);
				if (database != null && database.isOpen()) {
					goFlag = true;
					return localDb;
				}
			}else{
				Loger.e("xx", "找不到数据库" + path, null);
			}
			
		} catch (Exception e) {
			Loger.e("xx", "找不到数据库" + path, e);
		}

		return null;
	}

	public static void close() {
		if (database != null) {
			database.close();
			goFlag = false;
		}
	}

	/**
	 * 查询所有数据
	 * 
	 * @param table
	 * @return
	 */
	public Cursor getAllData(String table) {
		return database.query(table, null, null, null, null, null, null);
	}

	/**
	 * 插入数据记录
	 * 
	 * @param table
	 *            要记录的表名
	 * @param nullId
	 *            　空值，一般指主键ＩＤ，SQLite数据主键为integer primary key 主键是为自增加数据
	 * @param newValue
	 *            字段的名值对数据。
	 * @return
	 */
	public long insert(String table, String nullId, ContentValues value) {
		long longret = database.insert(table, nullId, value);
		if (longret == -1) {
			for (Entry<String, Object> item : value.valueSet()) {
				if (item.getValue() == null) {
					Log.e(table, "null值===========>" + item.getKey());
				}
			}
		}
		return longret;
	}

	/**
	 * 更新记录
	 * 
	 * @param table
	 *            表名
	 * @param value
	 *            字段的名值对数据
	 * @param where
	 *            字符串，如where a=? and b=?
	 * @param whereArgs
	 *            where 对应的参数
	 * @return
	 */
	public long update(String table, ContentValues value, String where,
			String[] whereArgs) {
		return database.update(table, value, where, whereArgs);
	}

	/**
	 * 删除记录
	 * 
	 * @param table
	 *            表名
	 * @param where
	 *            字符串，如where a=? and b=?
	 * @param whereArgs
	 *            where 对应的参数
	 */
	public long delete(String table, String where, String[] whereArgs) {
		return database.delete(table, where, whereArgs);
	}

	/**
	 * 
	 * @param table
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @return
	 */
	public Cursor queryWhere(String table, String selection,
			String[] selectionArgs, String groupBy) {
		return database.query(table, null, selection, selectionArgs, groupBy,
				null, null);
	}

	public Cursor querySql(String sql, String[] selectionArgs) {
		return database.rawQuery(sql, selectionArgs);
	}

	public void execSQL(String sql) {
		database.execSQL(sql);
	}
}
