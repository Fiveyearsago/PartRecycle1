package com.jy.recycle.ui.view;

import java.util.ArrayList;

import com.jy.recycle.R;
import com.jy.recycle.util.SpinnerItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListviewMustAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<SpinnerItem> list;
	public ListviewMustAdapter(Context context,ArrayList<SpinnerItem> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			RelativeLayout layout = (RelativeLayout) vi.inflate(
					R.layout.spinner_item_layout, parent, false);
			
			TextView label = (TextView) layout.findViewById(R.id.spinner_item_label);
			ImageView check = (ImageView) layout.findViewById(R.id.spinner_item_checked_image);
			if(position == 0){
				label.setVisibility(View.GONE);
				check.setVisibility(View.GONE);
			}else {
				label.setText(list.get(position).getValue());
			}
			return layout;
	}

}
