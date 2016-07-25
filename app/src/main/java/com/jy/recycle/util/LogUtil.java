package com.jy.recycle.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

import android.util.Log;


public class LogUtil {

	public static void log2File(String message , String filePath){
		File file = new File(filePath);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(message.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			Log.i("Log Exception", e.getMessage());
		}
		
	}
	
	public static void logObject(String filePath  , Object bundle){
		try{
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath));
			os.writeObject(bundle);
			os.flush();
			os.close();
			os = null ;
		}catch(Exception ex){
			Log.e("WRITE OBJECT" , ex.getMessage());
		}
	}
	
	public static Object readObject(String fileName){
		Object obj = null ; 
		try{
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			obj = is.readObject();
			is.close();
		}catch(Exception ex){
			Log.e("READ OBJECT ", ex.getMessage());
		}
		return obj;
	}
	public static void saveBack(String caseNo, String msg) {
		try {
			String strDate = new Timestamp(System.currentTimeMillis())
					.toString().substring(0, 10);
			String fileName = Constants.DIR_LOG + "/" + strDate + ".log";
//			Log.e("fileName", fileName);
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter bufw = new BufferedWriter(new FileWriter(fileName,
					true));
			bufw.write("时间:"
					+ new Timestamp(System.currentTimeMillis()).toString());
			bufw.write("定损退回:"+ caseNo);
			bufw.newLine(); // 换行
			StringBuffer sb = new StringBuffer("\t");
			sb.append(msg).append("\n\t");
			
			bufw.write(sb.toString());
			bufw.newLine(); // 换行
			bufw.flush();
			bufw.close();
		} catch (Exception e) {

		}
	}
	public static void saveInEval(String caseNo, String msg) {
		try {
			String strDate = new Timestamp(System.currentTimeMillis())
					.toString().substring(0, 10);
			String fileName = Constants.DIR_LOG + "/" + strDate + ".log";
//			Log.e("fileName", fileName);
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter bufw = new BufferedWriter(new FileWriter(fileName,
					true));
			bufw.write("时间:"
					+ new Timestamp(System.currentTimeMillis()).toString());
			bufw.write("进入定损:"+ caseNo);
			bufw.newLine(); // 换行
			StringBuffer sb = new StringBuffer("\t");
			sb.append(msg).append("\n\t");
			
			bufw.write(sb.toString());
			bufw.newLine(); // 换行
			bufw.flush();
			bufw.close();
		} catch (Exception e) {

		}
	}
	public static void saveFinish(String caseNo, String msg) {
		try {
			String strDate = new Timestamp(System.currentTimeMillis())
					.toString().substring(0, 10);
			String fileName = Constants.DIR_LOG + "/" + strDate + ".log";
//			Log.e("fileName", fileName);
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter bufw = new BufferedWriter(new FileWriter(fileName,
					true));
			bufw.write("时间:"
					+ new Timestamp(System.currentTimeMillis()).toString());
			bufw.write("定损完成:"+ caseNo);
			bufw.newLine(); // 换行
			StringBuffer sb = new StringBuffer("\t");
			sb.append(msg).append("\n\t");
			
			bufw.write(sb.toString());
			bufw.newLine(); // 换行
			bufw.flush();
			bufw.close();
		} catch (Exception e) {

		}
	}
	public static void saveUpload(String caseNo, String msg,String mm) {
		try {
			String strDate = new Timestamp(System.currentTimeMillis())
					.toString().substring(0, 10);
			String fileName = Constants.DIR_LOG + "/" + strDate + ".log";
//			Log.e("fileName", fileName);
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter bufw = new BufferedWriter(new FileWriter(fileName,
					true));
			bufw.write("时间:"
					+ new Timestamp(System.currentTimeMillis()).toString());
			bufw.write("定损提交:"+ caseNo);
			bufw.newLine(); // 换行
			StringBuffer sb = new StringBuffer("\t");
			sb.append(msg).append("\n\t");
			sb.append("提交结果："+mm).append("\n\t");
			bufw.write(sb.toString());
			bufw.newLine(); // 换行
			bufw.flush();
			bufw.close();
		} catch (Exception e) {

		}
	}
	public static void saveUpdateVehicle(String caseNo, String msg) {
		try {
			String strDate = new Timestamp(System.currentTimeMillis())
					.toString().substring(0, 10);
			String fileName = Constants.DIR_LOG + "/" + strDate + ".log";
//			Log.e("fileName", fileName);
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter bufw = new BufferedWriter(new FileWriter(fileName,
					true));
			bufw.write("时间:"
					+ new Timestamp(System.currentTimeMillis()).toString());
			bufw.write("换车:"+ caseNo);
			bufw.newLine(); // 换行
			StringBuffer sb = new StringBuffer("\t");
			sb.append(msg).append("\n\t");
			
			bufw.write(sb.toString());
			bufw.newLine(); // 换行
			bufw.flush();
			bufw.close();
		} catch (Exception e) {

		}
	}
}
