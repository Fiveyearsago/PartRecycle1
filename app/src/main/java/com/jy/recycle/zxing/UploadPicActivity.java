package com.jy.recycle.zxing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.httpclient.mime.ContinueUploadInputStreamEntity;
import com.jy.httpclient.mime.ContinueUploadInputStreamEntity.ProgressListener;
import com.jy.recycle.R;
import com.jy.recycle.action.EvalLossInfoAction;
import com.jy.recycle.dao.QuestionDetailsDao;
import com.jy.recycle.ui.EvalApplication;
import com.jy.recycle.util.Constants;
import com.jy.recycle.util.FileOperate;
import com.jy.recycle.util.HttpUtil;
import com.jy.recycle.util.LogUtil;
import com.jy.recycle.util.SharedData;
import com.umeng.analytics.MobclickAgent;


/**
 * �ϴ�ҳ��
 *
 * @author iStar
 *
 */
public class UploadPicActivity extends AppCompatActivity {
	private Context context = this;
	private SharedData share = new SharedData(context);

	private Button uploadBtn;
	private Button cancelBtn;

	private ProgressBar compassProgress;
	private ProgressBar imgProgress;

	private TextView textInfo;
	private TextView imgInfo;
	private TextView imgNotice;

	private String taskNo;
	private String claimId;
	private String platNo;
	private String reResult;
	private String claimFlag;
	private String content = null;

	private boolean background = false;// ����Ƿ��̨�ϴ�
	private int totalSize = 0;
	private int sentSize = 0;
	private int index = 0;
	private int count = 0;
	private int compressTotal = 0;
	private int errCount = 0;

	private long sendedLength = 0;
	private long pretime = 0, currenttime = 0;
	private long startTime = 0, endTime = 0;

	ProgressDialog pd = null;
	private UploadTask uploadTask = null;
	private String finishFlag;
	private String rebackFlag ;
	private String PATH ="";
	private ProgressDialog progressDialog;
	private EvalLossInfoAction evalLossInfoAction;
	private String mState;

	private long evalId;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.upload_window);
		EvalApplication.getInstance().addActivity(UploadPicActivity.this);

		findViews();
		bindViews();
		Intent intentget = this.getIntent();
		evalId = intentget.getLongExtra("evalId", 0);
		mState=intentget.getStringExtra("state");
		evalLossInfoAction = new EvalLossInfoAction(this);
		content = intentget.getStringExtra("content");
		Log.i("content", content);
	}

	/**
	 * ����Views
	 */
	private void findViews() {
		uploadBtn = (Button) findViewById(R.id.upload);
		cancelBtn = (Button) findViewById(R.id.btn_upload_cancel);
		compassProgress = (ProgressBar) findViewById(R.id.text_uploadbar);
		imgProgress = (ProgressBar) findViewById(R.id.img_uploadbar);
		textInfo = (TextView) findViewById(R.id.text_upload_info);
		imgInfo = (TextView) findViewById(R.id.img_upload_info);
		imgNotice = (TextView) findViewById(R.id.img_notice);
		// ��ʼ�����ͼƬ����������
		imgProgress.setVisibility(View.INVISIBLE);
		imgNotice.setVisibility(View.INVISIBLE);
	}

	/**
	 * ��Views
	 */
	private void bindViews() {
		// ǰ̨�ϴ�
		uploadBtn.setOnClickListener(onClickListener);
		// ȡ���ϴ�
		cancelBtn.setOnClickListener(onClickListener);
	}

	/**
	 * Handler
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
					case 5: // �ϴ���ʱ
						onUploadTimeout();
						break;
					case 7: // �ϴ��ɹ�
						onUploadSuccess();
						break;
					case 8: // ��������ʧ�����
						onUploadOtherError();
						break;
					case 9: // ѹ��ͼƬ����
						onCompassImg(msg);
						break;
					case 10://    ѹ��
						onStartCompass();
						break;
					case 11:// ȡ���ɹ�
						onCancalSuccess();
						break;
					case 99:// �ϴ�ʧ�ܣ���
						onRetry();
						break;
					default:
						break;
				}
			}
		}
	};

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.upload:
					onForgroundUpload();
					uploadBtn.setClickable(false);
					break;
				case R.id.btn_upload_cancel:
					onCancelUpload();
					cancelBtn.setClickable(false);
					break;
				default:
			}
		}

	};

	/**
	 * ǰ̨�ϴ�
	 */
	private void onForgroundUpload() {
		doUpload();
	}

	/**
	 * ��̨�ϴ�
	 */
	private void onBackgroundUpload() {

		this.background = true;
		finish();
	}

	/**
	 * �ϴ�
	 */
	private void doUpload() {
		uploadTask = new UploadTask();
		uploadTask.execute();
	}

	/**
	 * ȡ���ϴ��� ��̨����
	 */
	private void onCancelUpload() {
		pd = ProgressDialog.show(this, "ȡ������", "����ȡ��...", true);
		if (uploadTask != null) {
			uploadTask.cancelTask(true);
		} else {
			sendMsg(11);
		}
	}

	/**
	 *    ѹ��
	 */
	private void onStartCompass() {
		compassProgress.setMax(compressTotal);
	}

	/**
	 * ȡ������
	 */
	private void onCancalSuccess() {
		pd.dismiss();
		Toast.makeText(this, "ȡ���ɹ�", Toast.LENGTH_LONG).show();
		finish();
	}


	/**
	 * ���ӳ�ʱ����
	 */
	private void onUploadTimeout() {
//		Toast.makeText(getApplicationContext(), "���������ӳ�ʱ���������ԡ� ����",
//				Toast.LENGTH_LONG).show();
	}

	/**
	 * �ϴ��ɹ�
	 */
	private void onUploadSuccess() {

		if (reResult != null && !reResult.equals("")) {
			Toast.makeText(this, reResult, Toast.LENGTH_LONG).show();
		}

		finish();
	}

	/**
	 * �������ʧ��
	 */
	private void onUploadOtherError() {
		if (reResult != null && !reResult.equals("")) {
//			Toast.makeText(this, reResult, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ѹ����������
	 */
	private void onCompassImg(Message msg) {
		compassProgress.setProgress(msg.getData().getInt("length"));
		float num = (float) compassProgress.getProgress()
				/ (float) compassProgress.getMax();
		int result = (int) (num * 100);
		textInfo.setText("(" + index + "/" + count + ")" + result + "%");
		if (compassProgress.getProgress() == compassProgress.getMax()) {
			textInfo.setText("ѹ�����,��ѹ����" + count + " ");
		}
	}

	/**
	 * �ϴ�ʧ�ܣ���
	 */
	private void onRetry() {
		errCount++;
		if (errCount > 3) {
			finish();
//			Toast.makeText(this, "�����ϴ�ʧ�ܣ��������Ա��ϵ", Toast.LENGTH_SHORT).show();
		} else {
//			Toast.makeText(this, "�����ϴ�ʧ��  + errCount + "�Σ���������", Toast.LENGTH_SHORT).show();
			uploadTask = new UploadTask();
			uploadTask.execute();
		}
	}

	/**
	 * ��imgHandler�� ��Ϣ
	 *
	 * @param what
	 */
	private void sendMsg(int what) {
		Message msg = handler.obtainMessage(what);
		handler.sendMessage(msg);
	}

	/**
	 * �ϴ�����
	 *
	 */
	private class UploadTask extends AsyncTask<Void, Integer, Void> {
		boolean uploadSuccess = false;
		HttpPost httpPost = null;

		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ context.getString(R.string.dir)
				+ context.getString(R.string.img_dir) + File.separator
				+ evalId;
		File fileDir = new File(path);
		File[] imageList = fileDir.listFiles();
		//		private List<File> imageLis = fileDir.listFiles();

		/**
		 * �ϴ�ǰ��
		 */
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "���Ժ�", "�����ϴ�");
		}

		/**
		 * �ϴ�����
		 */
		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (isCancelled()) {
					sendMsg(11);
					return null;
				}
				if(imageList!=null){
					count = imageList.length;
				}

				int length = 0;
				String zipFilePath = Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ getString(R.string.dir)
						+ getString(R.string.img_dir)
						+ File.separator
						+ "fitAppTempFile2015" + ".zip";// ѹ����·
				startTime = System.currentTimeMillis();
				// 3.ѹ������
				compressFiles(zipFilePath, imageList);
				if (isCancelled()) {
					sendMsg(11);
					return null;
				}

				// 4.�ϴ�����
				File zipFile = new File(zipFilePath);
				if (zipFile.length() >= length) {
					uploadSuccess = uploadFile(length, new OnProgressListener(
							length));
				} else if (zipFile.length() < length) {
					length = getUploadLength("1");
					uploadSuccess = uploadFile(length, new OnProgressListener(
							length));
				}
				//				if (isCancelled()) {
				//					sendMsg(11);
				//					return null;
				//				}
				// 5.�ϴ�����
				if (uploadSuccess) {
					boolean uploadFinished = uploadFinish();
					if (zipFile.exists() && uploadFinished) {
						zipFile.delete();
					}
					publishProgress(3);
				}

			} catch (Exception e) {
				if (isCancelled()) {
					sendMsg(11);
					return null;
				}
				sendMsg(99);
			} finally {
			}

			return null;
		}

		/**
		 * �ϴ���
		 */
		@Override
		public void onPostExecute(Void result) {
			progressDialog.dismiss();
			if(uploadSuccess){
				Toast.makeText(context, "�ϴ��ɹ�", Toast.LENGTH_LONG).show();
				//				PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				//						+ context.getString(R.string.dir)
				//						+ context.getString(R.string.img_dir)
				//						+"/"+evalId;
				PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
						+ context.getString(R.string.dir)
						+ context.getString(R.string.img_dir);
				FileOperate.delAllFile(PATH);
				//				deleteEvalInfo(evalId);
				//				QuestionDetailsDao detailsDao = QuestionDetailsDao
				//						.getInstance();
				//
				//				detailsDao.deleteId(evalId);
				EvalLossInfoAction action=new EvalLossInfoAction(context);
				action.deleteAllEvalInfo();
				//				finish();
				//				Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
				//				startActivity(intent);
				EvalApplication.getInstance().exit();
			}else{
				//				sendMsg(99);
				Toast.makeText(context, "�ϴ�ʧ�ܣ�������", Toast.LENGTH_LONG).show();
				finish();
			}
		}


		/**
		 * ȡ����ǰ����
		 *
		 * @param mayInterruptIfRunning
		 * @return
		 */
		public boolean cancelTask(boolean mayInterruptIfRunning) {
			boolean cancelled = super.cancel(mayInterruptIfRunning);
			if (httpPost != null) {
				httpPost.abort();
			}
			return cancelled;
		}

		/**
		 * ��ȡ�ϵ������ļ���С
		 *
		 * @param rePackFlag
		 * @return
		 * @throws IOException
		 * @throws JSONException
		 */
		private int getUploadLength(String rePackFlag) throws IOException,
				JSONException {
			InputStream is = null;
			try {
				String url=Constants.URL_UPLOAD+"pjhsAppUploadZipDataInteractionServlet";
				//				if(mState.equals("")){
				//					 url=Constants.URL_UPLOAD+"pjhsAppUploadZipDataInteractionServlet";
				//				}else{
				//					url=Constants.URL_UPLOAD+"pjhsAppUploadZipDataSecondInteractionServlet";
				//				}
				//String url=Constants.URL_UPLOAD+"pjhsAppUploadZipDataInteractionServlet";
				String path = url + "/QtTaskUpload?type=0"
						+ "&taskNo=" + taskNo + "&rePackFlag=" + rePackFlag;


				httpPost = new HttpPost(url);
				//				httpPost = new HttpPost(path);
				HttpContext localContext = new BasicHttpContext();
				HttpResponse response = HttpUtil.getHttpClient().execute(
						httpPost, localContext);
				// ������Ӧ���
				BufferedReader reader = null;
				String data = "";
				try {
					reader = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent(), Constants.ENCODING));
					String line = null;
					while ((line = reader.readLine()) != null) {
						data += line;
					}
					if (data != null && !"".equals(data)) {
						JSONObject json = new JSONObject(data);
						int position = json.getInt("position");
						return position;
					}
				} finally {
					if (reader != null) {
						reader.close();
					}
				}

			} finally {
				if (is != null) {
					is.close();
					is = null;
				}
			}
			return 0;
		}

		/**
		 * ѹ���ļ�
		 *
		 * @param zipFilePath
		 * @param imageList
		 * @throws IOException
		 */
		private void compressFiles(String zipFilePath,File[] imageList) throws IOException {
			if(imageList!=null){
				count = imageList.length;
			}else {
				count=0;
			}

			File zipFile = new File(zipFilePath);

			if (zipFile.exists()) {
				zipFile.delete();
			}
			// 1.�����ļ�
			zipFile.createNewFile();
			ZipOutputStream outZip = null;
			index = 0;
			try {
				outZip = new ZipOutputStream(new FileOutputStream(zipFile));
				// 2.ѹ��������Ϣ
				//�ϴ�����
				ZipEntry zipEntry = null;
				zipEntry = new ZipEntry("fitAppTempFile2015" + ".json");

				outZip.putNextEntry(zipEntry);
				outZip.write(content.getBytes());
				LogUtil.saveUpload("","", content.getBytes().toString());
				outZip.closeEntry();
				// 3.���ͼƬ��Ϣ
				for (int i = 0; i < count && !isCancelled(); i++) {
					int compassed = 0;// �Ѿ�ѹ��
					File imageFile = imageList[i];
					index++;
					compressTotal = (int) imageFile.length(); // ͼƬ�ܴ�

					if (!background) {
						sendMsg(10);
					}
					// �ж��ǲ�����
					if (imageFile.isFile() && imageFile.exists()) {
						zipEntry = new ZipEntry(imageFile.getName());
						FileInputStream inputStream = new FileInputStream(imageFile.getAbsolutePath());
						outZip.putNextEntry(zipEntry);

						int len = -1;
						byte[] buffer = new byte[4096];

						while ((len = inputStream.read(buffer)) != -1
								&& !isCancelled()) {
							int end = len;
							if ((compressTotal - compassed) < len) {
								end = compressTotal - compassed;
							}
							outZip.write(buffer, 0, len);
							compassed += end;// �ۼ��Ѿ��ϴ������ݳ�
							Message msg = new Message();
							msg.getData().putInt("length", compassed);
							msg.what = 9;
							handler.sendMessage(msg);
						}
						inputStream.close();
						outZip.closeEntry();
					}

				}
			} finally {
				if (outZip != null) {
					try {
						outZip.close();
					} catch (IOException ex) {

					}
				}
			}

		}

		/**
		 * �ϴ��ļ�
		 *
		 * @param position
		 * @param lisenter
		 * @return
		 * @throws ClientProtocolException
		 * @throws IOException
		 */
		private boolean uploadFile(int position, ProgressListener lisenter)
				throws ClientProtocolException, IOException {
			String zipFilePath = Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ getString(R.string.dir)
					+ File.separator
					+ getString(R.string.img_dir)
					+ File.separator + "fitAppTempFile2015" + ".zip";
			File zipFile = new File(zipFilePath);
			totalSize = (int) zipFile.length();


			//			//ͼƬ�ϴ��õ�zip��ַ
			String actionUrl=Constants.URL_UPLOAD+"pjhsAppUploadZipDataInteractionServlet";
			//			if(mState.equals("")){

			//			}else{
			//				actionUrl=Constants.URL_UPLOAD+"pjhsAppUploadZipDataSecondInteractionServlet";
			//			}
			//String actionUrl=Constants.URL_UPLOAD+"pjhsAppUploadZipDataInteractionServlet";
			if (!background) {
				publishProgress(1, totalSize);
			}

			InputStream is = null;
			// HttpClient httpClient = null ;
			try {
				is = new FileInputStream(zipFile);
				is.skip(position);
				// �� ��������
				// HttpParams httpParameters = new BasicHttpParams();
				// HttpConnectionParams.setConnectionTimeout(httpParameters,20000);
				// HttpConnectionParams.setSoTimeout(httpParameters, 20000);

				// httpClient = new DefaultHttpClient(httpParameters);
				HttpContext localContext = new BasicHttpContext();
				httpPost = new HttpPost(actionUrl);
				httpPost.addHeader("fileLength", String.valueOf(totalSize));
				ContinueUploadInputStreamEntity entity = new ContinueUploadInputStreamEntity(is, totalSize - position, lisenter);
				httpPost.setEntity(entity);
				HttpResponse response = HttpUtil.getHttpClient().execute(
						httpPost, localContext);
				// ������Ӧ���
				BufferedReader reader = null;
				String data = "";
				try {
					reader = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent(), Constants.ENCODING));
					String line = null;
					while ((line = reader.readLine()) != null) {
						data += line;
					}
				} finally {
					if (reader != null) {
						reader.close();
					}
				}
				// �������
				try {
					JSONObject json = new JSONObject(data);


					String responseFlag = json.getString("flag");
					String responseData = json.getString("data");
					//					String role = dataJson.getString("Js");
					//						share.saveJs(role);
					if(Constants.REQUEST_SUBMIT.equals(responseFlag)){
						JSONObject jsonObject = new JSONObject(responseData);
						String responseCode = jsonObject.getString("code");
						String responseMessage =jsonObject.getString("message");

						if(Constants.SUBMIT_SUCCESS.equals(responseCode)){
							uploadSuccess = true;
						}else{
							uploadSuccess = false;
						}
					}else{
						uploadSuccess = false;
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if(zipFile.exists()){
						zipFile.delete();
					}
					reResult = "�������˷��������쳣" + ",�����룺601";
					sendMsg(8);
					return false;
				}

			} finally {
				// �ͷ�ʹ�õ�����Դ��������
				if (httpPost != null) {
					httpPost.abort();
					httpPost = null;
				}
				if (is != null) {
					is.close();
				}
			}

			return uploadSuccess;

		}

		/**
		 * ����״ Ϊ��
		 */
		private boolean uploadFinish() {
			return true;
		}

		/**
		 *
		 * �ϴ����ȼ���
		 */
		private class OnProgressListener implements ProgressListener {
			int originalLength = 0;

			public OnProgressListener(int originalLength) {
				this.originalLength = originalLength;
			}

			@Override
			public void transferred(long num) {
				sentSize = this.originalLength + (int) num;
				sendedLength = num;

				if (!background) {
					publishProgress(2, sentSize);
				}
			}

			@Override
			public void onUpload(InputStream in, OutputStream out) {
				if (isCancelled()) {
					try {
						in.close();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

		};
	}


	/**
	 * ɾ��������Ϣ
	 *
	 *
	 */
	private void deleteEvalInfo(Long evalId) {
		evalLossInfoAction.deleteEvalInfo(evalId);//ɾ������
		evalLossInfoAction.deleteEvalLossInfo(evalId);//ɾ�������
	}
}
