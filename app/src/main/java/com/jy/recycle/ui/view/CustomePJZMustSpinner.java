package com.jy.recycle.ui.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.util.SpinnerItem;

public class CustomePJZMustSpinner extends Spinner implements OnItemClickListener {

	public static SelectDialog dialog = null;
	private ArrayList<SpinnerItem> list;
	public static String text;

	public CustomePJZMustSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean performClick() {
		Context context = getContext();
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		final View view = inflater.inflate(R.layout.formcustomspinner, null);
		final TextView textTitle = (TextView) view.findViewById(R.id.formcustomspinner_title);
		final ListView listview = (ListView) view
				.findViewById(R.id.formcustomspinner_list);
		textTitle.setText("Åä¼þ²¿Î»");
		ListviewAdapter adapters = new ListviewAdapter(context, getList());
		listview.setAdapter(adapters);
		listview.setOnItemClickListener(this);
		dialog = new SelectDialog(context, R.style.dialog);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		dialog.addContentView(view, params);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> view, View itemView, int position,
			long id) {
		
		ImageView check = (ImageView) itemView.findViewById(R.id.spinner_item_checked_image);
		check.setImageResource(R.drawable.spinner_after);
		setSelection(position);
		setText(list.get(position).getValue());
		
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	public ArrayList<SpinnerItem> getList() {
		return list;
	}

	public void setList(ArrayList<SpinnerItem> list) {
		this.list = list;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
