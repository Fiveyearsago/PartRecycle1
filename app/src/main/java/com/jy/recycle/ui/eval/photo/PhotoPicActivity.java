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
 * �Զ��屣����Ƭ
 * 
 * @author Administrator ���ݰ�������������� �����ı�ҳ��,����-->ȷ��,����,����,ȡ��,���� ȷ��������Ƭ������
 *         ���ĸ���ԭ��������Ƭ ���ı�����Ƭ���ٴ����� ,ȷ������ ȡ����������Ƭ���� ������Ƭ��С������
 */
public class PhotoPicActivity extends JyBaseActivity implements
		SensorEventListener {
	private Context context;
	private SharedData share;

	private QuestionDetailAction action;
	private String eval_id;// ���𵥺�
	private String question_id;
	private String falw1 = "����";

	private ImageView phReGo;
	private ImageView phGoOn;

	private TextView phLocal;
	// ����������������
	private SurfaceView surfaceView;
	// ��������������
	private SurfaceHolder surfaceHolder;
	// ���������
	private Camera camera;
	private EvalApplication app;
	private ProgressDialog dialog;

	private SensorManager mSensorManager;
	private static String PATH;
	String turnFlag = "0";// ����
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
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��

		setContentView(R.layout.ds_photolay);
		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������
		initLocation();

		init();
		checkStage();// ����豸
		findView();
		bindView();
	}
	// ��ʼ����λ����
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 1000;
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setAddrType("all");
		option.setScanSpan(1000);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(false);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
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
		app = (EvalApplication) getApplication(); // ������ǵ�Ӧ�ó���ȫ�ֱ���
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
		phLocal.setText(address == null ? "���ڻ�ȡ��ǰλ��..." : address);

		phReGo = (ImageView) findViewById(R.id.ds_phReGo);// ��������
		phReGo.setOnClickListener(this.onClickListener);

		phGoOn = (ImageView) findViewById(R.id.ds_phGoOn);// ��������
		phGoOn.setOnClickListener(this.onClickListener);

		phReGoS = (ImageView) findViewById(R.id.ds_phReGo_s);// ��������
		phReGoS.setOnClickListener(this.onClickListener);

		phGoOnS = (ImageView) findViewById(R.id.ds_phGoOn_s);// ��������
		phGoOnS.setOnClickListener(this.onClickListener);
		
		imageViewFlash=(ImageView) findViewById(R.id.flash_image);
		imageViewFlash1=(ImageView) findViewById(R.id.flash_image1);
		imageViewFlash.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		// ʵ�������ս������
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
		
		if ("����".equals(falw1)) {
			takePic();
			falw1 = "ȷ��";
			// phOK.setImageResource(R.drawable.ds_phsure_before);
		} else if ("ȷ��".equals(falw1)) {
			// showDialog(0);
			// finish();
		} else if ("����".equals(falw1)) {
			// showDialog(0);
			// finish();
		}
	}

	private void photoReGO() {
		mLocationClient.start();
		// ���ջ�Ԥ��
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
		falw1 = "����";
		// ���ջ�Ԥ��
		takePic();
		// phOK.setImageResource(R.drawable.ds_phback_before);
		// phOKS.setImageResource(R.drawable.ds_phback_before);
	}

	private void photoCancel() {
		// ����
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
					// ������Ƭ��ʽ
					parameters.setPictureFormat(ImageFormat.JPEG);
					// �����������
					camera.setParameters(parameters);
				}
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
			} catch (IOException e) {
				camera.stopPreview();
				camera.release();
				camera = null;
				toast("������ͷʧ�ܣ�", Color.RED);
				finish();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			try {
				// ����������
				if (camera != null) {
					Camera.Parameters parameters = camera.getParameters();
					parameters.setJpegQuality(99);
					parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
					// ������Ƭ��ʽ
					parameters.setPictureFormat(PixelFormat.JPEG);
					// �����������
					camera.setParameters(parameters);
					// ��ʼ���
					camera.startPreview();
				}
			} catch (Exception e) {
				eshowDialog("������ϣ��޷����ӵ����");
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
			// ����
			findViewById(R.id.contral_h).setVisibility(View.VISIBLE);
			findViewById(R.id.contral_s).setVisibility(View.GONE);
			Log.e("-----------------h", "-----------------h");
			turnFlag = "0";// ����
			// camera.setDisplayOrientation(0);

		} else {
			turnFlag = "1";// ����
			// ����
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

	// ��Ƭ�ص� ֻ��ʵ������ص����������н��롢���漴�ɣ�ǰ2����������ֱ����Ϊnull������ϵͳһ����Զ��������Щ��д������
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

			// �������ݿ�
			QuestionAnswerDetial details = new QuestionAnswerDetial();
			details.setEval_id(eval_id);
			details.setQuestion_id(question_id);
			details.setPic_name(picName);
			details.setPic_path(picPath);
			action.insertQuestionAnswerDetial(details);

			camera.startPreview();
			// ������ɣ��ظ����չ��ܿ���
			phGoOn.setEnabled(true);
			// phOK.setEnabled(true);
			phReGo.setEnabled(true);

			phGoOnS.setEnabled(true);
			// phOKS.setEnabled(true);
			phReGoS.setEnabled(true);
			EvalApplication.isTakingPicture = false;
		}
	};

	// ���շ���
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
				dialog = ProgressDialog.show(context, null, "�����У����Ժ�...", true);
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
				dialog.dismiss(); // �رս�����
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
			// ���ջ�Ԥ��
			phGoOn.setEnabled(false);
			phReGo.setEnabled(false);
			// phOK.setEnabled(false);

			phGoOnS.setEnabled(false);
			phReGoS.setEnabled(false);
			// phOKS.setEnabled(false);
			// ���ջ�Ԥ��
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
	 * ���һ���ֻ��豸����Ӳ���Ŀ���״̬ �ж��ֻ�SD���Ƿ����
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
					.setMessage("��鵽û�д洢��,������ֻ��洢���ٿ�����Ӧ��")
					.setPositiveButton("ȷ��",
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
			dialog.setMessage("�����У����Ժ�...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
		}
		return null;
	}

	private void eshowDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("�������").setMessage(msg).setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
	// ��λ�ص��ӿ�
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
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// ��λ������ÿСʱ
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// ��λ����
					sb.append("\ndirection : ");
					sb.append(location.getDirection());// ��λ��
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
					sb.append("\ndescribe : ");
					sb.append("gps��λ�ɹ�");
					address = location.getAddrStr();


				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());
					// ��Ӫ����Ϣ
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("���綨λ�ɹ�");
					address = location.getAddrStr();

				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
					sb.append("\ndescribe : ");
					sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
					address = location.getAddrStr();

				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
				}
				sb.append("\nlocationdescribe : ");
				sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
				List<Poi> list = location.getPoiList();// POI����
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
