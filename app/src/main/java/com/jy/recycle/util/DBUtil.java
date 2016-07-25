package com.jy.recycle.util;

import android.database.Cursor;
import android.util.Log;

public class DBUtil {
	public static String getString(Cursor cursor, String columnName) {
		int index = cursor.getColumnIndex(columnName);
		if (index != -1) {
			return cursor.getString(index);
		} else {
			Log.e("≤ª¥Ê‘⁄¡–£∫", columnName);
		}
		return null;
	}
}
