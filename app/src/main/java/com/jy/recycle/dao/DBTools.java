package com.jy.recycle.dao;

/**
 * ���ݿ����������
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
	// ���ڴ�ӡlog
	private static final String TAG = "DBTools";
	// ���ݿ�����Ϊdata
	private static final String DB_NAME = "recycle.db";
	// ���ݿ�汾
	private static final int DB_VERSION =13;
	// ����һ����
	// �û���ر�
	// ִ��open���������ݿ�ʱ�����淵�ص����ݿ����
	private SQLiteDatabase db = null;
	// ��SQLiteOpenHelper�̳й���
	private static DatabaseHelper dbHelper = null;

	private static Context context;

	private static class DatabaseHelper extends SDSQLiteOpenHelper {
		/** ���캯��-����һ�����ݿ� */
		DatabaseHelper(Context context) {

			// ������getWritableDatabase()
			// �� getReadableDatabase()����ʱ
			// �򴴽�һ�����ݿ�
			super(context, DB_NAME, null, DB_VERSION);
		}

		/** ����ģʽ **/
		static synchronized DatabaseHelper getInstance(Context context) {
			if (dbHelper == null) {
				dbHelper = new DatabaseHelper(context);
			}
			return dbHelper;
		}

		/** ����һ���� */
		@Override
		public void onCreate(SQLiteDatabase db) {
			String[] v1Sqls = context.getResources().getStringArray(
					R.array.v1_sqls);
			for (String sql : v1Sqls) {
				db.execSQL(sql);
			}
		}

		/** �������ݿ� */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Loger.e(TAG, "Upgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");
			
			try{
				if(oldVersion < 2 ){//���ڰ汾2�����ݣ��������汾2
					String[] v2Sqls = context.getResources().getStringArray(
							R.array.v2_sqls);
					for (String sql : v2Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			
			try{
				if(oldVersion < 3 ){//���ڰ汾3�����ݣ��������汾3
					String[] v3Sqls = context.getResources().getStringArray(
							R.array.v3_sqls);
					for (String sql : v3Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 4 ){//���ڰ汾4�����ݣ��������汾4
					String[] v4Sqls = context.getResources().getStringArray(
							R.array.v4_sqls);
					for (String sql : v4Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 5 ){//���ڰ汾5�����ݣ��������汾5
					String[] v5Sqls = context.getResources().getStringArray(
							R.array.v5_sqls);
					for (String sql : v5Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 6 ){//���ڰ汾6�����ݣ��������汾6
					String[] v6Sqls = context.getResources().getStringArray(
							R.array.v6_sqls);
					for (String sql : v6Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 7 ){//���ڰ汾7�����ݣ��������汾7
					String[] v7Sqls = context.getResources().getStringArray(
							R.array.v7_sqls);
					for (String sql : v7Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 9){//���ڰ汾9�����ݣ��������汾9
					String[] v8Sqls = context.getResources().getStringArray(
							R.array.v8_sqls);
					for (String sql : v8Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 10){//���ڰ汾10�����ݣ��������汾10
					String[] v9Sqls = context.getResources().getStringArray(
							R.array.v9_sqls);
					for (String sql : v9Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 11){//���ڰ汾11�����ݣ��������汾11
					String[] v11Sqls = context.getResources().getStringArray(
							R.array.v11_sqls);
					for (String sql : v11Sqls) {
						db.execSQL(sql);
					}
				}
			}catch(Exception ex){
				
			}
			try{
				if(oldVersion < 13){//���ڰ汾12�����ݣ��������汾12
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

	/** ���캯��-ȡ��Context */
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
	 * �����ݿ⣬�������ݿ����
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * �ر����ݿ�
	 */
	public void close() {
		db.close();
	}

	/**
	 * ��ʼ����
	 */
	public void beginTran() {
		db.beginTransaction();
	}

	/**
	 * ��������ɹ���־
	 */
	public void setTranSuc() {
		db.setTransactionSuccessful();
	}

	/**
	 * �����¼�
	 */
	public void endTran() {
		try {
			db.endTransaction();
		} catch (IllegalStateException e) {
			Loger.w("DBTool", "endTran", e);
		}
	}

	/**
	 * ��ѯ��������
	 * 
	 * @param table
	 * @return
	 */
	public Cursor getAllData(String table) {
		return db.query(table, null, null, null, null, null, null);
	}

	/**
	 * �������ݼ�¼
	 * 
	 * @param table
	 *            Ҫ��¼�ı���
	 * @param nullId
	 *            ����ֵ��һ��ָ�����ɣģ�SQLite��������Ϊinteger primary key ������Ϊ����������
	 * @param newValue
	 *            �ֶε���ֵ�����ݡ�
	 * @return
	 */
	public long insert(String table, String nullId, ContentValues value) {
		long longret = db.insert(table, nullId, value);
		if (longret == -1) {
			for (Entry<String, Object> item : value.valueSet()) {
				if (item.getValue() == null) {
					Log.e(table, "nullֵ===========>" + item.getKey());
				}
			}
		}
		return longret;
	}

	/**
	 * ���¼�¼
	 * 
	 * @param table
	 *            ����
	 * @param value
	 *            �ֶε���ֵ������
	 * @param where
	 *            �ַ�������where a=? and b=?
	 * @param whereArgs
	 *            where ��Ӧ�Ĳ���
	 * @return
	 */
	public long update(String table, ContentValues value, String where,
			String[] whereArgs) {
		long longret = 0;
		longret = db.update(table, value, where, whereArgs);
		return longret;
	}

	/**
	 * ɾ����¼
	 * 
	 * @param table
	 *            ����
	 * @param where
	 *            �ַ�������where a=? and b=?
	 * @param whereArgs
	 *            where ��Ӧ�Ĳ���
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
		 * int num = cs.getCount(); if (num > 0) { // �����α���ʼλ�� cs.moveToFirst();
		 * } else { cs.close(); cs = null; }
		 */
		return cs;
	}

	/**
	 * �ж����ݿ��Ƿ�ر�
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