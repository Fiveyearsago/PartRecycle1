package com.jy.recycle.ui.adapter;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.client.response.TihuoInfo;
import com.jy.recycle.ui.eval.pick.PickActivity;

public class TihuoAdapter extends BaseAdapter {
	private List<TihuoInfo> list;
	private Context context;

	public TihuoAdapter(List<TihuoInfo> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_tihuo, null);
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.tihuo_list_checkbox);
			viewHolder.textComName = (TextView) convertView
					.findViewById(R.id.tihuo_list_com_name);
			viewHolder.textContact = (TextView) convertView
					.findViewById(R.id.tihuo_list_contact_name);
			viewHolder.textCity = (TextView) convertView
					.findViewById(R.id.tihuo_list_contact_city);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final TihuoInfo info = list.get(position);
		viewHolder.textComName.setText(info.getGsmc());
		viewHolder.textContact.setText(info.getLxr());
		viewHolder.textCity.setText(info.getCsmc());

		viewHolder.checkBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO 复选框选择变换事件
						if (isChecked) {
							info.setIsSelect(true);
						} else {
							info.setIsSelect(false);
						}
					}
				});
		//Log.i("PickActivity.emptyFlag", PickActivity.emptyFlag+"");
		if (PickActivity.emptyFlag) {
			
			if (info.getMrbz().equals("1")) {
				viewHolder.checkBox.setChecked(true);
			}
		} else {
			if (info.getIsSelect()) {
				viewHolder.checkBox.setChecked(true);
			} else {
				viewHolder.checkBox.setChecked(false);
			}
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(context);
				dialog.setTitle("提货商信息");
				View layout = LayoutInflater.from(context).inflate(
						R.layout.dialog_tihuo, null);
				((TextView) layout.findViewById(R.id.tihuo_company))
						.setText(info.getGsmc());
				((TextView) layout.findViewById(R.id.tihuo_contact))
						.setText(info.getLxr());
				((TextView) layout.findViewById(R.id.tihuo_phone)).setText(info
						.getSjhm());
				((TextView) layout.findViewById(R.id.tihuo_province))
						.setText(info.getSfmc());
				((TextView) layout.findViewById(R.id.tihuo_city)).setText(info
						.getCsmc());
				dialog.setContentView(layout);
				dialog.show();
				dialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog消失
				layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
			}
		});
		return convertView;
	}

	public static class ViewHolder {
		CheckBox checkBox;
		TextView textComName, textContact, textCity;
	}

}
