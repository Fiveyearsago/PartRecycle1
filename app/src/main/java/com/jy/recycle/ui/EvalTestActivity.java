package com.jy.recycle.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jy.recycle.R;
import com.jy.recycle.action.EvalLossInfoAction;
import com.jy.recycle.client.response.EvalResponse;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.util.ClaimFlag;
import com.jy.recycle.util.Constants;
import com.jy.recycle.util.SharedData;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * 模拟理赔系统入参功能，进入精友定损工具
 *
 * @author Administrator
 *
 */
public class EvalTestActivity extends AppCompatActivity {
	private static String versionName;// 版本名
	private static int versioncode;// 版本号
	/** Called when the activity is first created. */

    protected ProgressDialog progressDialog;
    protected Intent mIntent;
    private long mExitTime;
	private SharedData share;
	private EditText userName;
	private EditText userPassword;

	private TextView mTextVersion;// 版本号

	private ProgressDialog progressBar;
	private int iCount = 0;
	private long newVerSize;
	private Context context;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		FIR.init(this);
        share=new SharedData(context);
		checkNewVersion();
		// UmengUpdateAgent.setUpdateOnlyWifi(false);
		// UmengUpdateAgent.update(this);
		setContentView(R.layout.main2);
		mTextVersion = (TextView) findViewById(R.id.appVersion);// 版本号
		mTextVersion.setText(getAppVersionName(this));
		userName = (EditText) findViewById(R.id.log_usernameEdit);
		userName.setText(SharedData.data().getUserName());
		userPassword = (EditText) findViewById(R.id.log_passwordEdit);
		userPassword.setText(SharedData.data().getUserPwd());
		Button evalBtn = (Button) findViewById(R.id.evalBtn);

		evalBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mIntent = new Intent();
				login();
			}
		});

	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			GsonBuilder builder = new GsonBuilder();
			builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
			Gson gson = builder.create();
			// 获取定损信息
			String claimEvalData = data.getStringExtra("CLAIMEVAL_DATA");
			EvalResponse evalResponse = gson.fromJson(claimEvalData,
					EvalResponse.class);

			String responseCode = evalResponse.getResponseCode();
			String errorMessage = evalResponse.getErrorMessage();
			if ("1".equals(responseCode)) {
			} else if ("2".equals(responseCode)) {
				Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
			} else if ("0".equals(responseCode)) {
				Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.forward_enter_activity_anim,
				R.anim.forward_exit_activity_anim);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.back_enter_activity_anim,
				R.anim.back_exit_activity_anim);
	}

	private void login() {
		SharedData.data().saveUserName(userName.getText().toString());
		SharedData.data().saveUserPwd(userPassword.getText().toString());
		new AsyncTask<Void, Void, JSONObject>() {
			@Override
			protected void onPreExecute() {
				progressDialog = ProgressDialog.show(EvalTestActivity.this,
						"请稍候", "正在登陆中。");
			}

			@Override
			protected JSONObject doInBackground(Void... params) {

				// JSONObject response =
				// ServerApiManager.loginUser("ygbx_sd_dsy", "123456", "");
				JSONObject response = ServerApiManager.loginUser(SharedData.data()
						.getUserName(), SharedData.data().getUserPwd(), "");
				return response;
			}

			@Override
			public void onPostExecute(JSONObject dataJson) {

				progressDialog.dismiss();

				if (dataJson != null) {
					try {
						String responseFlag = dataJson.getString("flag");
						String responseData = dataJson.getString("data");
						Log.i("TihuoJson", dataJson.toString());
						// String role = dataJson.getString("Js");
						// share.saveJs(role);
						if (Constants.QUEST_SUCCESS.equals(responseFlag)) {
							JSONObject jsonObject = new JSONObject(responseData);
							String responseCode = jsonObject.getString("code");
							String responseMessage = jsonObject
									.getString("message");
							Log.i("登录", responseData);
							if (Constants.LOGIN_SUCCESS.equals(responseCode)) {
								// mIntent.setClass(EvalTestActivity.this,EvalActivity.class);
								mIntent.setClass(EvalTestActivity.this,
										SearchActivity.class);
								mIntent.putExtra("responseData", responseData);
								EvalLossInfoAction action = new EvalLossInfoAction(
										EvalTestActivity.this);
								action.deleteAllEvalInfo();
								startActivity(mIntent);
								finish();
							} else {
								Toast.makeText(EvalTestActivity.this,
										responseMessage, Toast.LENGTH_LONG)
										.show();
							}
						} else if (Constants.PASSWORD_ERROR
								.equals(responseFlag)) {
							Toast.makeText(EvalTestActivity.this,
									"网络或XML格式异常返", Toast.LENGTH_LONG).show();

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(EvalTestActivity.this, "其它异常错误",
							Toast.LENGTH_LONG).show();
				}
			}
		}.execute();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			// this.exitLioneyeWithNoDataReturn(
			// EvalResponse.RESPONSE_CODE_EXIT_WITH_BACK, "退出定损");
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				// Intent intent = new
				// Intent(EvalActivity.this,EvalTestActivity.class);
				// startActivity(intent);
				EvalApplication.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void checkNewVersion() {
		// TODO Auto-generated method stub
		versionName = getVerName();

		FIR.checkForUpdateInFIR("4646637662a3a35206e5276ae199cc78",
				new VersionCheckCallback() {
					@Override
					public void onSuccess(String versionJson) {
						Log.i("fir", "check from fir.im success! " + "\n"
								+ versionJson);
						JSONObject jsonObject;
						String newVersionName = "";
						String versionDeString = "";
						String installUrl = "";
						try {
							jsonObject = new JSONObject(versionJson);
							newVersionName = jsonObject
									.getString("versionShort");
							installUrl = jsonObject.getString("installUrl");
							versionDeString = jsonObject.getString("changelog");
							if (!versionName.equals(newVersionName)) {
								// 版本不一致
//								Toast.makeText(getApplicationContext(),
//										"版本不一致", Toast.LENGTH_SHORT).show();
								doNewVersionUpdate(newVersionName,
										versionDeString, installUrl);
							} else {
//								Toast.makeText(getApplicationContext(), "版本一致",
//										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFail(Exception exception) {
						Log.i("fir",
								"check fir.im fail! " + "\n"
										+ exception.getMessage());
					}

					@Override
					public void onStart() {
						// Toast.makeText(getApplicationContext(), "正在获取",
						// Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFinish() {
						// Toast.makeText(getApplicationContext(), "获取完成",
						// Toast.LENGTH_SHORT).show();
					}
				});

	}

	private void doNewVersionUpdate(String newVerName,
			String versionDescription, final String installUrl) {
		String verName = getVerName();
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本:");
		sb.append(verName);
		sb.append(", \n发现新版本:");
		sb.append(newVerName);
		sb.append("\n" + versionDescription);
		sb.append("\n\n是否更新?");
		Dialog dialog = new AlertDialog.Builder(context)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				// 设置内容
				.setPositiveButton("更新",// 设置确定按钮
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								progressBar = new ProgressDialog(context);
								progressBar.setTitle("正在下载");
								progressBar.setMessage("请稍候...");
								progressBar
										.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
								progressBar.setMax(100);
								downloadAPK(installUrl);
							}
						})
				.setNegativeButton("暂不更新",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).create();
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * 下载APK
	 */
	private void downloadAPK(final String installUrl) {
		progressBar.setProgress(iCount);
		progressBar.show();
		Log.i("installUrl", installUrl);
		new AsyncTask<Void, Integer, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					URL url = new URL(installUrl);
			         // 打开连接
			         URLConnection con = url.openConnection();
			         //获得文件的长度
//					HttpEntity entity = HttpUtil.getEntity(installUrl);
					long length = con.getContentLength();
					newVerSize=length;
					int progress = 0;

					InputStream is = con.getInputStream();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						File f = new File(getSDPath() + File.separator
								+ ClaimFlag.APKNAME);
						if (!f.exists()) {
							f.createNewFile();
						}
						fileOutputStream = new FileOutputStream(f);

						byte[] buf = new byte[2 * 1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							iCount = count;

							// 发布进度
							progress = Math.round(count * 1.0f / length * 100);
							publishProgress(progress);
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			public void onProgressUpdate(Integer... valuse) {
				int progress = valuse[0];
				progressBar.setProgress(progress);
			}

			@Override
			public void onPostExecute(Void result) {
				progressBar.cancel();
				// 如果下载失败，删除临时文件
//				if (newVerSize != iCount) {
				if (newVerSize != iCount) {
					showDialog("下载新版过程中出现意外，更新失败，请重试！");
					String fileName = getSDPath() + File.separator
							+ ClaimFlag.APKNAME;
					File f = new File(fileName);
					if (f.exists()) {
						f.delete();
					}
				} else {
					installAPK();
				}
			}

		}.execute();
	}
	/*
	 * 安装新版本程序
	 */
	private void installAPK() {
		String fileName = getSDPath() + File.separator + ClaimFlag.APKNAME;

		PackageInfo packageInfo = null;

		try {
			packageInfo = this.getPackageManager().getPackageInfo(
					this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			PackageInfo packageArchiveInfo = this.getPackageManager()
					.getPackageArchiveInfo(fileName,
							PackageManager.GET_CONFIGURATIONS);
//			if (packageArchiveInfo.versionCode <= packageInfo.versionCode) {
//				File file = new File(fileName);
//				file.delete();
//			} else {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(fileName)),
						"application/vnd.android.package-archive");
				startActivity(intent);
				Toast.makeText(context, "安装成功", Toast.LENGTH_LONG);
//				invokeAPK();
//			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 启动程序
	 */
	private void invokeAPK() {
//		SharedData.data().saveUpdateVer("1");
		Intent i = new Intent();
		i.setComponent(new ComponentName(getPackageName(), EvalTestActivity.class
				.getName()));
		startActivity(i);
		Toast.makeText(context, "重新启动", Toast.LENGTH_LONG).show();
	}

	/**
	 * 弹出提示对话框
	 *
	 * @param msg
	 */
	public void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				}).show();
	}

	public String getVerName() {
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
			Log.i("version", versionName + "   " + versioncode);
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 获取SD卡路径
	 *
	 * @return
	 */
	private String getSDPath() {
		File f = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // determine whether sd
														// card is exist
		if (sdCardExist) {
			f = new File(Environment.getExternalStorageDirectory()
					+ File.separator + context.getString(R.string.dir)
					+ context.getString(R.string.app_dir));
			if (!f.exists()) {
				f.mkdir();
			}
		}
		return f.toString();
	}
}
