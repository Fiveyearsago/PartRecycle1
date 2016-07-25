package com.jy.recycle.ui;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.JsonObject;
import com.jy.framework.android.util.AppUtil;
import com.jy.recycle.R;
import com.jy.recycle.dao.DBTools;
import com.jy.recycle.ui.eval.EvalActivity;
import com.jy.recycle.util.ClaimFlag;
import com.jy.recycle.util.Constants;
import com.jy.recycle.util.HttpUtil;
import com.jy.recycle.util.Loger;
import com.jy.recycle.util.SharedData;

/**
 * 设置全局变量，将GPS启动，经纬度等全局变量
 * 
 * @author Administrator
 * 
 */
public class EvalApplication extends Application {
	// 网络不稳定事件
	private boolean gpsFlag; // 是否启动GPS服务
	public static boolean isTakingPicture = false;
	private double lat; // 经度
	private double lng; // 纬度
	public static long a = 0, b = 0;

	private String addr; //
	private String imgFile;
	public double lat02;
	public double lng_02;
	public float radius;
	public double altitude;
	public float direction;
	public double speed;
	public long time;
	private static EvalApplication instance;
	private List<Activity> activityList = new LinkedList<Activity>();
	public static Map<String, String> upLoading = new HashMap<String, String>();
	private static Context context;
	public static Context getAppContext() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		FIR.init(this);
		context = this;
		if (Constants.DEBUG) {
//			Toast.makeText(this, "EvalApplication - 程序启动", Toast.LENGTH_LONG).show();
		}
		checkSystemDir();

		// 防止AsyncTask不能调用onPostExecute
		try {
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			Loger.e("JyBaseActivity", "init android.os.AsyncTask");
		}
		// 初始化共享数据对象
		SharedData.init(this);
		if (Constants.DEBUG) {
//			Toast.makeText(this, "EvalApplication - 初始化共享数据对象",
//					Toast.LENGTH_LONG).show();
		}
		// 初始化数据库工具
		try {
			DBTools.init(this);
			if (Constants.DEBUG) {
//				Toast.makeText(this, "EvalApplication - 初始化数据库",
//						Toast.LENGTH_LONG);
			}
		} catch (Exception e) {
			Loger.e("EvalApplication", "DBTools.init", e);
//			Toast.makeText(this, "EvalApplication - 初始化数据库错误",
//					Toast.LENGTH_LONG).show();

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("请插入内存卡，再重新启动系统").setPositiveButton("",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Process.killProcess(Process.myPid());
						}
					});
		}
		// 异常捕捉
		setDefaultUncaughtExceptionHandler();
		// 统计对象初始化
		AppUtil.init(this, Constants.MOBILE_STAT_SERVER);
		super.onCreate();
		
	}

	
	
	private void checkSystemDir() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + getString(R.string.dir);
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		dir = new File(path + getString(R.string.app_dir));
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		dir = new File(path + getString(R.string.db_dir));
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		dir = new File(path + getString(R.string.img_dir));
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}

		dir = new File(path + getString(R.string.log_dir));// R.string.dir+
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		dir = new File(path + getString(R.string.dump_dir));// R.string.dir+
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		dir = new File(path + getString(R.string.temp_dir));// R.string.dir+
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
	}

	private void setDefaultUncaughtExceptionHandler() {

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Loger.e("EvalApplication", "DefaultUncaughtException", ex);
				Process.killProcess(Process.myPid());
			}
		});
	}

	public boolean isGpsFlag() {
		return gpsFlag;
	}

	public void setGpsFlag(boolean gpsFlag) {
		this.gpsFlag = gpsFlag;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	// 单例模式：获取 MyApplication 的实例
	public static EvalApplication getInstance() {
		if (null == instance) {
			instance = new EvalApplication();
		}
		return instance;
	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity 并finish
	public void exit() {
		for (Activity activity : instance.activityList) {
			if (activity != null) {
				activity.finish();
			}
		}

		System.exit(0);
	}

	/**
	 * 
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			lat02 = location.getLatitude();
			lng_02 = location.getLongitude();
			radius = location.getRadius();
			altitude = location.getAltitude();
			direction = location.getDirection();
			speed = location.getSpeed();
			time = System.currentTimeMillis();
		}

	}

}
