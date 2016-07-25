package com.jy.recycle.ui.eval.photo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.jy.recycle.R;
import com.jy.recycle.action.QuestionDetailAction;
import com.jy.recycle.pojo.QuestionAnswerDetial;
import com.jy.recycle.ui.EvalApplication;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.ui.eval.EvalActivity;
import com.jy.recycle.util.ImageUtils;
import com.jy.recycle.util.Loger;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.TimestampTool;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 自定义保存照片
 * 
 * @author Administrator 根据案件任务进行拍照 进入拍报页面,拍摄-->确定,重拍,连拍,取消,设置 确定保存照片并返回
 *         重拍覆盖原先所拍照片 连拍保存照片并再次拍摄 ,确定返回 取消不保存照片返回 设置照片大小及质量
 */
public class PhotoPicActivity extends JyBaseActivity implements
		SensorEventListener {
	private Context context;
	private SharedData share;

	private QuestionDetailAction action;
	private String eval_id;// 定损单号
	private String question_id;
	private String falw1 = "拍摄";

	private ImageView phReGo;
	private ImageView phGoOn;

	private TextView phLocal;
	// 声明照相机界面组件
	private SurfaceView surfaceView;
	// 声明界面控制组件
	private SurfaceHolder surfaceHolder;
	// 声明照相机
	private Camera camera;
	private EvalApplication app;
	private ProgressDialog dialog;

	private SensorManager mSensorManager;
	private static String PATH;
	String turnFlag = "0";// 横屏
	private ImageView phReGoS;
	private ImageView phGoOnS;
	private String address;
	private String carNo;
	private String partStandName;
	
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	private ImageView imageViewFlash,imageViewFlash1;
	private String flashString="auto";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏

		setContentView(R.layout.ds_photolay);
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		initLocation();

		init();
		checkStage();// 检查设备
		findView();
		bindView();
	}
	// 初始化定位数据
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setAddrType("all");
		option.setScanSpan(1000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(false);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(false);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}
	@SuppressLint("InlinedApi")
	private void init() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mOrientationSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);

		context = this;
		PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator + context.getString(R.string.dir)
				+ context.getString(R.string.img_dir) + "/";

		Intent intent = getIntent();
		eval_id = intent.getStringExtra("evalId");
		carNo =intent.getStringExtra("carNo");
		partStandName =intent.getStringExtra("partStandName");
		question_id = intent.getStringExtra("questionId");

		share = SharedData.data();
		app = (EvalApplication) getApplication(); // 获得我们的应用程序全局变量
		action = new QuestionDetailAction(context);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(surfaceCallback);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void findView() {
		phLocal = (TextView) findViewById(R.id.ds_phLocal);
		phLocal.setText(address == null ? "正在获取当前位置..." : address);

		phReGo = (ImageView) findViewById(R.id.ds_phReGo);// 重新拍摄
		phReGo.setOnClickListener(this.onClickListener);

		phGoOn = (ImageView) findViewById(R.id.ds_phGoOn);// 连续拍摄
		phGoOn.setOnClickListener(this.onClickListener);

		phReGoS = (ImageView) findViewById(R.id.ds_phReGo_s);// 重新拍摄
		phReGoS.setOnClickListener(this.onClickListener);

		phGoOnS = (ImageView) findViewById(R.id.ds_phGoOn_s);// 连续拍摄
		phGoOnS.setOnClickListener(this.onClickListener);
		
		imageViewFlash=(ImageView) findViewById(R.id.flash_image);
		imageViewFlash1=(ImageView) findViewById(R.id.flash_image1);
		imageViewFlash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		// 实例化拍照界面组件
		surfaceView = (SurfaceView) findViewById(R.id.ds_mSurView);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			findViewById(R.id.contral_h).setVisibility(View.VISIBLE);
			findViewById(R.id.contral_s).setVisibility(View.GONE);
//			imageViewFlash.setVisibility(View.VISIBLE);
			turnFlag = "0";
		} else {
			findViewById(R.id.contral_h).setVisibility(View.GONE);
			findViewById(R.id.contral_s).setVisibility(View.VISIBLE);
//			imageViewFlash.setVisibility(View.VISIBLE);
			turnFlag = "1";
		}

	}

	private void bindView() {

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			// case R.id.ds_phOK:
			// case R.id.ds_phOK_s:
			// photoOK();
			// break;
			case R.id.ds_phReGo:
			case R.id.ds_phReGo_s:
				photoReGO();
				break;
			case R.id.ds_phGoOn:
			case R.id.ds_phGoOn_s:
				photoGoOn();
				break;
			// case R.id.ds_phCancle:
			// case R.id.ds_phCancle_s:
			// photoCancel();
			// break;
			default:
			}
		}

	};

	private void photoOK() {
		
		if ("拍摄".equals(falw1)) {
			takePic();
			falw1 = "确定";
			// phOK.setImageResource(R.drawable.ds_phsure_before);
		} else if ("确定".equals(falw1)) {
			// showDialog(0);
			// finish();
		} else if ("返回".equals(falw1)) {
			// showDialog(0);
			// finish();
		}
	}

	private void photoReGO() {
		mLocationClient.start();
		// 让照机预览
		camera.startPreview();
		// phOK.setImageResource(R.drawable.ds_phok_before);
	}

	
	private void photoGoOn() {
		mLocationClient.start();
		// phGoOn.setEnabled(false);
		// phReGo.setEnabled(false);
		// // phOK.setEnabled(false);
		// phGoOnS.setEnabled(false);
		// phReGoS.setEnabled(false);
		// phOKS.setEnabled(false);
		falw1 = "返回";
		// 让照机预览
		takePic();
		// phOK.setImageResource(R.drawable.ds_phback_before);
		// phOKS.setImageResource(R.drawable.ds_phback_before);
	}

	private void photoCancel() {
		// 返回
		finish();
	}

	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera = Camera.open();
				if (camera != null) {
					setCameraDisplayOrientation(PhotoPicActivity.this, 0,
							camera);
					Camera.Parameters parameters = camera.getParameters();
					parameters.setJpegQuality(99);
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
					// 设置照片格式
					parameters.setPictureFormat(ImageFormat.JPEG);
					// 设置相机参数
					camera.setParameters(parameters);
				}
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
			} catch (IOException e) {
				camera.stopPreview();
				camera.release();
				camera = null;
				toast("打开摄像头失败！", Color.RED);
				finish();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			try {
				// 获得相机参数
				if (camera != null) {
					Camera.Parameters parameters = camera.getParameters();
					parameters.setJpegQuality(99);
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
					// 设置照片格式
					parameters.setPictureFormat(PixelFormat.JPEG);
					// 设置相机参数
					camera.setParameters(parameters);
					// 开始浏览
					camera.startPreview();
				}
			} catch (Exception e) {
				eshowDialog("相机故障，无法连接到相机");
				Loger.e("PhotoPicActivity", "findView:", e);
				camera.release();
				camera = null;
				finish();
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (camera != null) {
				camera.setPreviewCallback(null);
				camera.stopPreview();
				camera.release();
				camera = null;
			}
		}
	};

	public static int getCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
		return degrees;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 横屏
			findViewById(R.id.contral_h).setVisibility(View.VISIBLE);
			findViewById(R.id.contral_s).setVisibility(View.GONE);
			Log.e("-----------------h", "-----------------h");
			turnFlag = "0";// 横屏
			// camera.setDisplayOrientation(0);

		} else {
			turnFlag = "1";// 竖屏
			// 竖屏
			findViewById(R.id.contral_h).setVisibility(View.GONE);
			findViewById(R.id.contral_s).setVisibility(View.VISIBLE);
			Log.e("-----------------s", "-----------------s");
			// camera.setDisplayOrientation(90);
		}
		setCameraDisplayOrientation(PhotoPicActivity.this, 0, camera);
	}

	public static void setCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
		Log.e("---------------1", degrees + "");
		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { //
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
		// camera.stopPreview();
		// camera.setDisplayOrientation(result);
		// camera.startPreview();
		// return degrees;
	}

	protected static void setDisplayOrientation(Camera camera, int angle) {
		Method downPolymorphic;
		try {
			downPolymorphic = camera.getClass().getMethod(
					"setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null)
				downPolymorphic.invoke(camera, new Object[] { angle });
		} catch (Exception e1) {
		}
	}

	// 照片回调 只需实现这个回调函数来就行解码、保存即可，前2个参数可以直接设为null，不过系统一般会自动帮你把这些都写进来的
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

		@Override
		public synchronized void onPictureTaken(byte[] data, Camera camera) {

			String picName = TimestampTool.getStrDateTime().concat("_")
					.concat(question_id).concat(".jpg");
			String picPath = PATH.concat(eval_id).concat("/").concat(picName);
			int imgType = ImageUtils.MAX_PIXELS;// takePhotoTypeCb.isChecked() ?
												// ImageUtils.MAX_PIXELS:
												// ImageUtils.MIN_PIXELS;
			ImageUtils.compressBitmap(data, partStandName,address , picPath,
					imgType, turnFlag,carNo);

			// 插入数据库
			QuestionAnswerDetial details = new QuestionAnswerDetial();
			details.setEval_id(eval_id);
			details.setQuestion_id(question_id);
			details.setPic_name(picName);
			details.setPic_path(picPath);
			action.insertQuestionAnswerDetial(details);

			camera.startPreview();
			// 拍照完成，回复拍照功能可用
			phGoOn.setEnabled(true);
			// phOK.setEnabled(true);
			phReGo.setEnabled(true);

			phGoOnS.setEnabled(true);
			// phOKS.setEnabled(true);
			phReGoS.setEnabled(true);
			EvalApplication.isTakingPicture = false;
		}
	};

	// 拍照方法
	private void takePic() {
		if (!EvalApplication.isTakingPicture) {
			EvalApplication.isTakingPicture = true;
			camera.autoFocus(new AutoFocus());
			

		}
	}

	private final class AutoFocus implements AutoFocusCallback {
		@Override
		public void onAutoFocus(boolean success, Camera arg1) {
			if (camera != null) {
				dialog = ProgressDialog.show(context, null, "处理中，请稍后...", true);
				camera.takePicture(new ShutterCallback() {

					@Override
					public void onShutter() {

					}

				}, null, pictureCallback);
				handler.sendEmptyMessage(1);
			} else {
				EvalApplication.isTakingPicture = false;
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 1:
				dialog.dismiss(); // 关闭进度条
				break;
			}
		}
	};
	private Sensor mOrientationSensor;

	// private int configurationDegrees;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_CAMERA
				|| keyCode == KeyEvent.KEYCODE_SEARCH) {
			// 让照机预览
			phGoOn.setEnabled(false);
			phReGo.setEnabled(false);
			// phOK.setEnabled(false);

			phGoOnS.setEnabled(false);
			phReGoS.setEnabled(false);
			// phOKS.setEnabled(false);
			// 让照机预览
			takePic();
			// phOK.setBackgroundResource(R.drawable.selector_button_bg);
			// phOKS.setBackgroundResource(R.drawable.selector_button_bg);
			phGoOn.setEnabled(true);
			phGoOnS.setEnabled(true);
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) { //
			Intent intent=new Intent(PhotoPicActivity.this,EvalActivity.class);
			intent.putExtra("question_id", question_id);
			this.setResult(RESULT_OK,intent);
			finish();
			
		}
		return true;
	}

	/**
	 * 检查一下手机设备各项硬件的开启状态 判断手机SD卡是否存在
	 */
	public void checkStage() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ File.separator
					+ context.getString(R.string.dir)
					+ context.getString(R.string.img_dir);
			File dir = new File(path + "/" + eval_id);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
		} else {
			new AlertDialog.Builder(this)
					.setMessage("检查到没有存储卡,请插入手机存储卡再开启本应用")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									finish();
								}
							}).show();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage("处理中，请稍后...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}
		return null;
	}

	private void eshowDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("相机故障").setMessage(msg).setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
	// 定位回调接口
		public class MyLocationListener implements BDLocationListener {

			@Override
			public void onReceiveLocation(BDLocation location) {
				// Receive Location
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 单位：公里每小时
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 单位：米
					sb.append("\ndirection : ");
					sb.append(location.getDirection());// 单位度
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
					address = location.getAddrStr();


				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
					// 运营商信息
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
					address = location.getAddrStr();

				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");
					address = location.getAddrStr();

				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}
				sb.append("\nlocationdescribe : ");
				sb.append(location.getLocationDescribe());// 位置语义化信息
				List<Poi> list = location.getPoiList();// POI数据
				if (list != null) {
					sb.append("\npoilist size = : ");
					sb.append(list.size());
					for (Poi p : list) {
						sb.append("\npoi= : ");
						sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
					}
				}
				Log.i("BaiduLocationApiDem", sb.toString());
				if (address != "" && address != null) {
					mLocationClient.stop();
				}
				;
			}
		}

}
