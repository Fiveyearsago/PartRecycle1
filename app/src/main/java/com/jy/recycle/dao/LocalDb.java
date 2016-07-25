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
			// �Զ��������ȡ���ؼ۸񷽰���ͻ
			if(new File(DbConstants.BASE_PATH.concat(path)).exists()){
				database = SQLiteDatabase.openDatabase(DbConstants.BASE_PATH.concat(path),
						null, SQLiteDatabase.OPEN_READONLY);
				if (database != null && database.isOpen()) {
					goFlag = true;
					return localDb;
				}
			}else{
				Loger.e("xx", "�Ҳ������ݿ�" + path, null);
			}
			
		} catch (Exception e) {
			Loger.e("xx", "�Ҳ������ݿ�" + path, e);
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
	 * ��ѯ��������
	 * 
	 * @param table
	 * @return
	 */
	public Cursor getAllData(String table) {
		return database.query(table, null, null, null, null, null, null);
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
		long longret = database.insert(table, nullId, value);
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
		return database.update(table, value, where, whereArgs);
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
