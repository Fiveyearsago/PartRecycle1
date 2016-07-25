package com.jy.recycle.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.jy.recycle.R;

public class NUtils {
	private static Handler mHandler = new Handler();
	public static String path = "";

	public interface Callback {
		// ������ɺ����ݻش���������
		public void response(String url, byte[] bytes, String evalId);
	}

	public static final String CACHEDIR = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "/jyds/img/";

	public static String saveImg(String url, byte[] bytes, String evalId)
			throws IOException {
		// �жϴ洢Ŀ¼�Ƿ����
		Log.i("path:", path);
		final String path = CACHEDIR + evalId + "/";
		File dir = new File(path);

		if (!dir.exists())
			dir.mkdirs();
		// ��ͼƬ����д�뵽һ��ͼƬ�ļ�
		FileOutputStream fos = new FileOutputStream(new File(dir, getName(url)));
		fos.write(bytes);
		Log.i("bytes", bytes.length + "");
		fos.close();
		// ��ͼƬ·���浽���ݿ�
		return path + getName(url);
	}

	public static String getName(String url) {
		Log.i("��ǰ��", url);
		String string = url.substring(url.indexOf("-") - 4, url.length());
		Log.i("�غ�", string);
		return string;
	}

	private static ExecutorService service = Executors.newFixedThreadPool(5);

	/**
	 * ����������Դ�ķ���
	 * 
	 * @param url
	 * @param callback
	 * @throws Exception
	 */
	public static void get(final String url, final Callback callback,
			final String evalId) {

		service.execute(new Runnable() {
			@Override
			public void run() {
				// TODO ʵ������������Դ�Ĺ���
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(url)
							.openConnection();
					InputStream is = conn.getInputStream();
					byte[] buffer = new byte[10 * 1024];
					int len = -1;

					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					if (conn.getResponseCode() == 200) { // �ж��Ƿ����ӳɹ�

						while ((len = is.read(buffer)) != -1) {
							baos.write(buffer, 0, len);
						}

						final byte[] bytes = baos.toByteArray(); // �����������ת���ֽ�����
						Log.i("is ++++", is.available() + "");
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								// ͨ���ӿڻص������ݻش������߳�
								callback.response(url, bytes, evalId);
							}
						});

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
