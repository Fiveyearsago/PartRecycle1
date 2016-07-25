package com.jy.recycle.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Timestamp;

import android.util.Log;

/**
 * 
 * 控制Log的打印
 * 
 * @author Administrator
 * 
 */
public class Loger {
	private final static int flag = 0;
	private final static int e = 5;
	private final static int w = 4;
	private final static int i = 3;
	private final static int d = 2;
	private final static int v = 1;

	public static void e(String tag, String msg) {
		if (e > flag) {
			Log.e(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (w > flag) {
			Log.w(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (i > flag) {
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (d > flag) {
			Log.d(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (v > flag) {
			Log.v(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (e > flag) {
			Log.e(tag, msg, tr);
		}
		saveError(tag, msg, tr);
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (w > flag) {
			Log.w(tag, msg, tr);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (i > flag) {
			Log.i(tag, msg, tr);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (d > flag) {
			Log.d(tag, msg, tr);
		}
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (v > flag) {
			Log.v(tag, msg, tr);
		}
	}

	public static void saveError(String tag, String msg, Throwable tr) {
		try {
			String strDate = new Timestamp(System.currentTimeMillis())
					.toString().substring(0, 10);
			String fileName = Constants.DIR_LOG + "/" + strDate + ".log";
			Log.e("fileName", fileName);
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter bufw = new BufferedWriter(new FileWriter(fileName,
					true));
			bufw.write("时间:"
					+ new Timestamp(System.currentTimeMillis()).toString());
			bufw.newLine(); // 换行
			StringBuffer sb = new StringBuffer("\t");
			sb.append(tag).append("\n\t").append(msg);
			if (tr != null) {
				sb.append(":").append(tr.getMessage());
			}
			bufw.write(sb.toString());
			bufw.newLine(); // 换行
			bufw.flush();
			bufw.close();
		} catch (Exception e) {

		}
	}
	public static void saveResponse(String tag, String msg, Throwable tr) {
		try {
			
			
			String strDate = new Timestamp(System.currentTimeMillis())
					.toString().substring(0, 10);
			String fileName = Constants.RES_LOG + "/" + strDate + ".log";
			Log.e("fileName", fileName);
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream bufw = new FileOutputStream(fileName,
					true);
			StringBuffer sb = new StringBuffer("\t");
			sb.append("时间:"
					+ new Timestamp(System.currentTimeMillis()).toString()+tag).append("\n\t").append(msg);
			bufw.write(sb.toString().getBytes("UTF-8"));
			bufw.flush();
			bufw.close();
		} catch (Exception e) {

		}
	}


}
