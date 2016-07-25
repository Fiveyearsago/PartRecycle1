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
		// 下载完成后将数据回传给调用者
		public void response(String url, byte[] bytes, String evalId);
	}

	public static final String CACHEDIR = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "/jyds/img/";

	public static String saveImg(String url, byte[] bytes, String evalId)
			throws IOException {
		// 判断存储目录是否存在
		Log.i("path:", path);
		final String path = CACHEDIR + evalId + "/";
		File dir = new File(path);

		if (!dir.exists())
			dir.mkdirs();
		// 把图片数据写入到一个图片文件
		FileOutputStream fos = new FileOutputStream(new File(dir, getName(url)));
		fos.write(bytes);
		Log.i("bytes", bytes.length + "");
		fos.close();
		// 把图片路径存到数据库
		return path + getName(url);
	}

	public static String getName(String url) {
		Log.i("截前：", url);
		String string = url.substring(url.indexOf("-") - 4, url.length());
		Log.i("截后", string);
		return string;
	}

	private static ExecutorService service = Executors.newFixedThreadPool(5);

	/**
	 * 下载网络资源的方法
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
				// TODO 实现网络下载资源的功能
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(url)
							.openConnection();
					InputStream is = conn.getInputStream();
					byte[] buffer = new byte[10 * 1024];
					int len = -1;

					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					if (conn.getResponseCode() == 200) { // 判断是否连接成功

						while ((len = is.read(buffer)) != -1) {
							baos.write(buffer, 0, len);
						}

						final byte[] bytes = baos.toByteArray(); // 将下载完的流转成字节数组
						Log.i("is ++++", is.available() + "");
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								// 通过接口回调将数据回传给主线程
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
