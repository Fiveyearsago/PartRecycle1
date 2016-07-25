package com.jy.recycle.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.client.response.ProvinceInfo;
import com.jy.recycle.util.SharedData;

public class ProvinceAdapter extends BaseAdapter {
	private List<ProvinceInfo> list;
	private Context context;

	public ProvinceAdapter(List<ProvinceInfo> list, Context context) {
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
		if(convertView==null){
		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) vi.inflate(
				R.layout.item_province, parent, false);
		convertView=layout;
		}
		TextView textProvince = (TextView) convertView.findViewById(R.id.item_province);
		textProvince.setText(list.get(position).getSfmc());
		if (SharedData.data().getState().equals("1003")) {
			textProvince.setTextColor(Color.BLACK);
		}
		return convertView;
	}

}
