package com.jy.recycle.ui.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jy.recycle.R;
import com.jy.recycle.util.ImageUtils;

public class ImageAdapter extends BaseAdapter {

	private List<String> picNumber = new ArrayList<String>();
	private Context context;
	private LayoutInflater inflater;
	private PopupWindow popupWindow;
	private Bitmap bitmap = null;

	public ImageAdapter() {
		// TODO Auto-generated constructor stub
	}

	public ImageAdapter(Context mContext, List<String> list) {
		context = mContext;
		picNumber = list;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return picNumber.size();
	}

	@Override
	public Object getItem(int position) {
		return picNumber.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	ViewHolder holder = null;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.picturescan_item, null);
		holder.image1 = (ImageView) convertView.findViewById(R.id.scan_img);
		holder.image2 = (ImageView) convertView
				.findViewById(R.id.scan_select_del);
		holder.image3 = (ImageView) convertView
				.findViewById(R.id.scan_select_ok);
		holder.image4 = (ImageView) convertView.findViewById(R.id.fangda);

		convertView.setTag(holder);

		String picPath = picNumber.get(position);
		if (picPath != null && !"".equals(picPath)) {
			File file = new File(picPath);
			if (file.exists()) {

				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(picPath, opts);
				opts.inSampleSize = ImageUtils.computeSampleSize(opts, -1,
						(int) (ImageUtils.getScreenH(context) * 0.5)
								* (int) (ImageUtils.getScreenW(context) * 0.4));
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;

				try {
					bitmap = BitmapFactory.decodeStream(new FileInputStream(
							picPath), null, opts);
					holder.image1.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					// TODO: handle exception
				}
			}
		}

		if (picNumber.contains("" + position)) {
			holder.image2.setVisibility(View.VISIBLE);
		} else {
			holder.image2.setVisibility(View.GONE);
		}
		if (picNumber.contains("" + position)) {
			holder.image3.setVisibility(View.VISIBLE);
		} else {
			holder.image3.setVisibility(View.GONE);
		}
		if (picNumber.contains("" + position)) {
			holder.image4.setVisibility(View.VISIBLE);
		} else {
			holder.image4.setVisibility(View.GONE);
		}

		return convertView;
	}

	public void initPopuptWindow(Bitmap bitmap2) {

		// ��ȡ�Զ��岼���ļ�pop.xml����ͼ
		final View popupWindow_view = inflater.inflate(R.layout.image_pop,
				null, false);
		ImageView imageView = (ImageView) popupWindow_view
				.findViewById(R.id.pop_img);
		imageView.setImageBitmap(bitmap2);
		popupWindow = new PopupWindow(popupWindow_view,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(popupWindow_view, Gravity.CENTER, 0, 0);

		popupWindow_view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});
	}

	public static class ViewHolder {
		public ImageView image1;
		public ImageView image2;
		public ImageView image3;
		public ImageView image4;
	}

}