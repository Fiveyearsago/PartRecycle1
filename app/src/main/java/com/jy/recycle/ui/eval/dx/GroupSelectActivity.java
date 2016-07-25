/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jy.recycle.ui.eval.dx;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jy.recycle.R;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.ui.JyBaseActivity;
import com.jy.recycle.ui.adapter.DialogTypeListAdapter;
import com.jy.recycle.ui.view.CustomerMustSpinner;
import com.jy.recycle.ui.view.CustomerSpinner;
import com.jy.recycle.ui.view.CustomerZCSpinner;
import com.jy.recycle.util.CustomDialog;
import com.jy.recycle.util.SpinnerItem;
/**
 * 
 * @author Administrator
 * 弹根据车系选择车组
 */
public class GroupSelectActivity extends JyBaseActivity {
	private Context context;
	
	private String brandName ; 
	private String serId;
	
	private ArrayList<SpinnerItem> cxData;
	private List<Map<String,String>> data;
	
	private ListView groupList;
	private CustomerZCSpinner seriSel;
	private Spinner seriSelText;
	private ImageView imagevSeriSel;
	private CustomDialog seriSelListDialog;
	
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				new GetGroupListBySerialIdTask().execute();
				break;
			case 2:
				//没有查询到该品牌的车系信息时返回
		    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("没有查询到该品牌下的车系信息，请重试！")
					.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
				break;
			default:
				break;
			}
		}
		
	};
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState); 
    	setContentView(R.layout.dx_selectgroup);
    	TextView titleText=(TextView)findViewById(R.id.group_title);
    	titleText.setText("   选择车系");
    	context =this;
    	
    	Intent it=this.getIntent();
    	brandName=it.getStringExtra("BrandName");	//品牌名称
    	
    	findViews();
    	
    	new GetSerialListByBrandNameTask().execute();
    	
	}
    
    private void findViews(){
    	 groupList=(ListView)findViewById(R.id.dx_groupList); 
    	 groupList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView serId = (TextView) arg1.findViewById(R.id.dx_selId);
				TextView serName=(TextView) arg1.findViewById(R.id.dx_selName);
				Intent it = new Intent();   
				it.putExtra("GroupId",serId.getText()); 
				it.putExtra("GroupName",serName.getText().toString());
	            setResult(RESULT_OK, it);   
				finish();
			}        	 
         });
//    	 seriSelText = (Spinner) findViewById(R.id.text_dx_seriSel);
//    	 seriSelText.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					showSeriSelListDialog();
//				}
//			});
//    	 imagevSeriSel = (ImageView) findViewById(R.id.imagevBtn_seriSel_chex);
//    	 imagevSeriSel.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					showSeriSelListDialog();
//				}
//			});
    	 seriSel =(CustomerZCSpinner) findViewById(R.id.dx_seriSel); 
    	 seriSel.setOnItemSelectedListener(new OnItemSelectedListener(){
			//选择车系时列出车组信息
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				handler.sendEmptyMessage(1);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
    }
    /**
     * 根据车系Id，获取车组列表的任务
     * @author Administrator
     *
     */
    private class GetGroupListBySerialIdTask extends  AsyncTask<Void , Void , Void>{
    	ProgressDialog progressDialog = null;
		 @Override
		public void onPreExecute(){
			progressDialog = ProgressDialog.show(context, "请稍候", "正在加载车组数据...");
			int position = seriSel.getSelectedItemPosition();
//			if(serId == null){
//				serId = cxData.get(0).getID();
//			}
			if(position >= 0 && cxData!= null){
				serId = cxData.get(position).getID();
			}
		}
		@Override
		protected Void doInBackground(Void... params) {
			if(serId!=null && !"".equals(serId)){
				data= ServerApiManager.getGroupData(serId);//获取车组数据列表
			}
			return null;
		}
		@Override
		public void onPostExecute(Void result){
			progressDialog.dismiss();
			if(data!=null){
				SimpleAdapter czadapter = new SimpleAdapter(GroupSelectActivity.this, data,
		        		 R.layout.dx_select_list, new String[] { "czmc","id" },
		        		 new int[] { R.id.dx_selName, R.id.dx_selId});
				groupList.setAdapter(czadapter);
			}
		}
	 }
    
    /**
     * 根据品牌名称，获取车系列表
     */
    private class GetSerialListByBrandNameTask extends  AsyncTask<Void, Void, Void>{
    	ProgressDialog progressDialog = null;
    	@Override
		public void onPreExecute(){
			progressDialog = ProgressDialog.show(context, "请稍候", "正在加载车系数据...");
		}
		@Override
		protected Void doInBackground(Void... params) {	
			 
			cxData=ServerApiManager.getSeriData(brandName );//获取车系数据列表
	    	
	    	return null;
		}
		@Override
		public void onPostExecute(Void result){
			progressDialog.dismiss();
//			if(cxData!=null && cxData.size() > 0){
//				seriSelText.setText(cxData.get(0).getValue());
//				handler.sendEmptyMessage(1);
////	    		ArrayAdapter<SpinnerItem> cxadapter = new ArrayAdapter<SpinnerItem>(GroupSelectActivity.this,
////		 			    android.R.layout.simple_spinner_item, cxData);
////		    	cxadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////		    	seriSel.setAdapter(cxadapter);
//	    	}else{
//	    		handler.sendEmptyMessage(2);
//	    	}
			if(cxData!=null && cxData.size() > 0){
				
				
				
				seriSel.setList(cxData);
				ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<SpinnerItem>(GroupSelectActivity.this, android.R.layout.simple_spinner_item,cxData);
				seriSel.setAdapter(adapter);
		        
		        
//		        
//	    		ArrayAdapter<SpinnerItem> cxadapter = new ArrayAdapter<SpinnerItem>(GroupSelectActivity.this,
//		 			    android.R.layout.simple_spinner_item, cxData);
//		    	cxadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		    	seriSel.setAdapter(cxadapter);
	    	}else{
	    		handler.sendEmptyMessage(2);
	    	}
		}
	}

	@Override
	public void onClick(View v) {
		
	}
	
	

	private void showSeriSelListDialog() {
		seriSelListDialog = new CustomDialog(this, R.layout.dialog_pricefa_type,
				R.style.Dialog);

		TextView titleText = (TextView) seriSelListDialog
				.findViewById(R.id.text_title);
		titleText.setText("选择车组");
		titleText.setTextSize(16);

		final ImageView check = (ImageView)seriSelListDialog .findViewById(R.id.spinner_item_checked_image);
		ListView typeListView = (ListView) seriSelListDialog
				.findViewById(R.id.dialog_typeList);
		if(cxData!=null && cxData.size() > 0){
		DialogTypeListAdapter diaAdp = new DialogTypeListAdapter(context,
				cxData);
		typeListView.setAdapter(diaAdp);
		}
		typeListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				check.setImageResource(R.drawable.check_selected);
				
				
				if(position >= 0 && cxData!= null){
					serId = cxData.get(position).getID();
					new GetGroupListBySerialIdTask().execute();
				}
				seriSelListDialog.dismiss();
			}
		});
		seriSelListDialog.show();
	}
}
