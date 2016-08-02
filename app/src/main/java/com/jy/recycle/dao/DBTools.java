package com.jy.recycle.dao;

/**
 * 数据库操作工具类
 * @author jyxiongw
 * @date 2011-3-25
 */

import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jy.recycle.R;
import com.jy.recycle.util.Loger;

public class DBTools {
	// 用于打印log
	private static final String TAG = "DBTools";
	// 数据库名称为data
	private static final String DB_NAME = "recycle.db";
	// 数据库版本
	private static final int DB_VERSION =13;
	// 创建一个表
	// 用户相关表
	// 执行open（）打开数据库时，保存返回的数据库对象
	private SQLiteDatabase db = null;
	// 由SQLiteOpenHelper继承过来
	private static DatabaseHelper dbHelper = null;

	private static Context context;

	private static class DatabaseHelper extends SDSQLiteOpenHelper {
		/** 构造函数-创建一个数据库 */
		DatabaseHelper(Context context) {

			// 当调用getWritableDatabase()
			// 或 getReadableDatabase()方法时
			// 则创建一个数据库
			super(context, DB_NAME, null, DB_VERSION);
		}

		/** 单例模式 **/
		static synchronized DatabaseHelper getInstance(Context context) {
			if (dbHelper == null) {
				dbHelper = new DatabaseHelper(context);
			}
			return dbHelper;
		}

		/** 创建一个表 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			String[] v1Sqls = context.getResources().getStringArray(
					R.array.v1_sqls);
			for (String sql : v1Sqls) {
				db.execSQL(sql);
			}
		}

		/** 升级数据库 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Loger.e(TAG, "Upgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");
			
			try{
				if(oldVersion < 2 ){//低于版本2的数据，升级到版本2
					String[] v2Sqls = context.getResources().getStringArray(
							R.array.v2_sqls);
					for (String sql : v2Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			
			try{
				if(oldVersion < 3 ){//低于版本3的数据，升级到版本3
					String[] v3Sqls = context.getResources().getStringArray(
							R.array.v3_sqls);
					for (String sql : v3Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 4 ){//低于版本4的数据，升级到版本4
					String[] v4Sqls = context.getResources().getStringArray(
							R.array.v4_sqls);
					for (String sql : v4Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 5 ){//低于版本5的数据，升级到版本5
					String[] v5Sqls = context.getResources().getStringArray(
							R.array.v5_sqls);
					for (String sql : v5Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 6 ){//低于版本6的数据，升级到版本6
					String[] v6Sqls = context.getResources().getStringArray(
							R.array.v6_sqls);
					for (String sql : v6Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 7 ){//低于版本7的数据，升级到版本7
					String[] v7Sqls = context.getResources().getStringArray(
							R.array.v7_sqls);
					for (String sql : v7Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 9){//低于版本9的数据，升级到版本9
					String[] v8Sqls = context.getResources().getStringArray(
							R.array.v8_sqls);
					for (String sql : v8Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 10){//低于版本10的数据，升级到版本10
					String[] v9Sqls = context.getResources().getStringArray(
							R.array.v9_sqls);
					for (String sql : v9Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 11){//低于版本11的数据，升级到版本11
					String[] v11Sqls = context.getResources().getStringArray(
							R.array.v11_sqls);
					for (String sql : v11Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 13){//低于版本12的数据，升级到版本12
					String[] v12Sqls = context.getResources().getStringArray(
							R.array.v12_sqls);
					for (String sql : v12Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){

			}
		}
	}

	private static DBTools tools = null;

	public static DBTools init(Context context) {
		return tools = new DBTools(context);
	}

	public static DBTools getInstance() {
		assert tools != null : "Please init DBTools First";
		return tools;
	}

	/** 构造函数-取得Context */
	private DBTools(Context c) {
		context = c;
		dbHelper = DatabaseHelper.getInstance(c);
		db = dbHelper.getWritableDatabase();
	}

	public void updateDB(int oldVersion, int newVersion) {
		db = dbHelper.getWritableDatabase();
		dbHelper.onUpgrade(db, oldVersion, newVersion);
	}

	/**
	 * 打开数据库，返回数据库对象
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		db.close();
	}

	/**
	 * 开始事务
	 */
	public void beginTran() {
		db.beginTransaction();
	}

	/**
	 * 设置事务成功标志
	 */
	public void setTranSuc() {
		db.setTransactionSuccessful();
	}

	/**
	 * 结束事件
	 */
	public void endTran() {
		try {
			db.endTransaction();
		} catch (IllegalStateException e) {
			Loger.w("DBTool", "endTran", e);
		}
	}

	/**
	 * 查询所有数据
	 * 
	 * @param table
	 * @return
	 */
	public Cursor getAllData(String table) {
		return db.query(table, null, null, null, null, null, null);
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
		long longret = db.insert(table, nullId, value);
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
		long longret = 0;
		longret = db.update(table, value, where, whereArgs);
		return longret;
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
		long longret = 0;
		longret = db.delete(table, where, whereArgs);
		return longret;
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
		return db.query(table, null, selection, selectionArgs, groupBy, null,
				null);
	}

	public Cursor querySql(String sql, String[] selectionArgs) {
		Cursor cs = db.rawQuery(sql, selectionArgs);
		/*
		 * int num = cs.getCount(); if (num > 0) { // 设置游标起始位置 cs.moveToFirst();
		 * } else { cs.close(); cs = null; }
		 */
		return cs;
	}

	/**
	 * 判断数据库是否关闭
	 * 
	 * @return
	 */
	public boolean isClose() {
		return !db.isOpen();
	}

	public Cursor querySql(String sql) {
		Cursor cs = db.rawQuery(sql, null);
		return cs;
	}

}