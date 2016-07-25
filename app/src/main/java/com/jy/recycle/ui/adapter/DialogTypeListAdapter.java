package com.jy.recycle.ui.adapter;
/**
 * 定损修理列表适配器
 * @author wl
 *
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.util.SpinnerItem;

import java.util.List;


public class DialogTypeListAdapter extends BaseAdapter {
	private List<SpinnerItem> data ;
	private Context context ;
	public DialogTypeListAdapter(Context context,List<SpinnerItem> data){
		this.context=context;
		this.data=data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public SpinnerItem getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SpinnerItem item = data.get(position);

		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) vi.inflate(
				R.layout.dialog_type_list_item, parent, false);

		TextView dialogTypeId = (TextView) layout.findViewById(R.id.dialog_type_id);
		dialogTypeId.setText(item.getValue()==null || "".equals(item.getValue().trim())? "无价格方案" : item.getID());//item.getInsureTerm()

		TextView dialogTypeName = (TextView) layout.findViewById(R.id.dialog_type_name);
		dialogTypeName.setText(item.getValue()==null || "".equals(item.getValue().trim())? "无价格方案" : item.getValue());//item.getInsureTerm()
		
		ImageView check = (ImageView) layout.findViewById(R.id.spinner_item_checked_image);
		if(position == 0){
			dialogTypeName.setVisibility(View.GONE);
			check.setVisibility(View.GONE);
		}else {
			dialogTypeName.setText(data.get(position).getValue());
		}
		layout.setId(position);
		return layout;
	}
}
