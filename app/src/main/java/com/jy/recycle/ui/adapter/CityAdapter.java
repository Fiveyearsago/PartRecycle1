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
import com.jy.recycle.client.response.CityInfo;
import com.jy.recycle.util.SharedData;

public class CityAdapter extends BaseAdapter {
	private List<CityInfo> list;
	private Context context;

	public CityAdapter(List<CityInfo> list, Context context) {
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
				R.layout.item_city, parent, false);
		convertView=layout;
		}
		TextView textCity = (TextView) convertView.findViewById(R.id.item_city);
		textCity.setText(list.get(position).getCsmc());
		if (SharedData.data().getState().equals("1003")) {
			textCity.setTextColor(Color.BLACK);
		}
		return convertView;
	}

}
