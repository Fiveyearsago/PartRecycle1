package com.jy.recycle.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.recycle.R;
import com.jy.recycle.action.EvalLossInfoAction;
import com.jy.recycle.client.response.Huishoudan;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.ui.eval.EvalActivity;
import com.jy.recycle.ui.eval.EvalActivity2;
import com.jy.recycle.ui.eval.EvalActivity3;
import com.jy.recycle.ui.eval.EvalActivity4;
import com.jy.recycle.ui.eval.EvalActivity5;
import com.jy.recycle.ui.eval.EvalActivity6;
import com.jy.recycle.ui.eval.EvalActivity7;
import com.jy.recycle.ui.eval.EvalOtherActivity;
import com.jy.recycle.util.SharedData;

public class HSDAdapter extends BaseAdapter {
	private List<Huishoudan> list;
	private Context context;
	private static Map<String, String> map;// 回收单状态Map集合
	private SharedData mShare;// 共享变量
	private String responseData = "";// 登陆后服务器返回的数据（登录界面传值）
	private String requestData = "";
	private String mHsdString = "";
	private Intent mIntent;
	private String mState;
	private String isDuijie="";

	public HSDAdapter(List<Huishoudan> list, Context context,
			String responseData) {
		super();
		this.list = list;
		this.context = context;
		this.responseData = responseData;
		mShare = SharedData.data();
		intiMap();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_hsd, null);
			viewHolder = new ViewHolder();
			viewHolder.textBah = (TextView) convertView
					.findViewById(R.id.item_bah);
			viewHolder.textCph = (TextView) convertView
					.findViewById(R.id.item_cph);
			viewHolder.textCar = (TextView) convertView
					.findViewById(R.id.item_car);
			viewHolder.textState = (TextView) convertView
					.findViewById(R.id.item_state);
			viewHolder.btnGo = (Button) convertView.findViewById(R.id.item_go);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textBah.setText(list.get(position).getBah());
		String sureRecycle=list.get(position).getSureRecycle();
		if (sureRecycle.equals("")||sureRecycle.equals("1000")){
			viewHolder.textBah.setTextColor(Color.BLACK);
		}else if (Integer.parseInt(sureRecycle)>1000){
			viewHolder.textBah.setTextColor(Color.RED);
		}
		viewHolder.textCph.setText(list.get(position).getCph());
		if(list.get(position).getCar().equals("0")){
		viewHolder.textCar.setText("标的车");
		viewHolder.textCar.setVisibility(View.VISIBLE);
		}
		else if(list.get(position).getCar().equals("1")){
			viewHolder.textCar.setText("三者车");
			viewHolder.textCar.setVisibility(View.VISIBLE);
		}else{
			viewHolder.textCar.setVisibility(View.GONE);
		}
		viewHolder.textState.setText(map.get(list.get(position).getState()
				.trim()));
		final String id = list.get(position).getId().trim();
		viewHolder.btnGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 进入到修改信息界面
				mState = list.get(position).getState();
				downloadhOneHsd(id,list.get(position).getSecondFlag());
//				downloadhOneHsd(id,"1");
				// context.startActivity(intent);
			}
		});
		return convertView;
	}

	public static class ViewHolder {
		private TextView textBah, textCph,textCar,textState;
		private Button btnGo;

	}

	public static void intiMap() {
		map = new HashMap<String, String>();
		map.put("1000", "暂存");
		map.put("1001", "申请入库");
		map.put("1002", "确认入库");
		map.put("1003", "供货");
	}

	private void downloadhOneHsd(final String id,final String secondFlag) {
		// TODO 查询回收单
		new AsyncTask<Void, Void, JSONObject>() {
			@Override
			protected void onPreExecute() {
			}

			@Override
			protected JSONObject doInBackground(Void... params) {
				JSONObject response = ServerApiManager.downloadOneHSD(
						mShare.getUserName(), id);
				return response;
			}

			@Override
			public void onPostExecute(JSONObject dataJson) {

				if (dataJson != null) {
					try {
						String responseFlag = dataJson.getString("flag");
						mHsdString = dataJson.getString("data");
//						Log.i("mHsdString", dataJson.toString());
						if ("0510".equals(responseFlag)) {
							JSONObject jsonObject = new JSONObject(mHsdString);
							String responseCode = jsonObject.getString("code");
							String responseMessage = jsonObject
									.getString("message");

							if ("0511".equals(responseCode)) {
								// 处理下载数据
								//Log.i("OneHuishoudan", dataJson.toString());
								 //analysisHSDJson(dataJson.toString());
								String bxgsgz=SharedData.data().getBxgsgz();
//								bxgsgz="6";
								if(bxgsgz.equals("1")){
									mIntent = new Intent(context, EvalActivity.class);
								}else if (bxgsgz.equals("2")) {
									mIntent = new Intent(context, EvalActivity2.class);
								}else if (bxgsgz.equals("3")) {
									if(secondFlag.equals("1")){
										mIntent = new Intent(context, EvalActivity5.class);
									}else {
										mIntent = new Intent(context, EvalActivity3.class);

									}
								}else if (bxgsgz.equals("5")) {
									mIntent = new Intent(context, EvalActivity4.class);
								}else if (bxgsgz.equals("6")) {
									mIntent = new Intent(context, EvalActivity6.class);
								}else if (bxgsgz.equals("7")) {
									mIntent = new Intent(context, EvalActivity7.class);
								}else {
									mIntent = new Intent(context, EvalOtherActivity.class);
								}
//								mIntent = new Intent(context, EvalActivity.class);
								mIntent.putExtra("state", mState);
								mIntent.putExtra("responseData", responseData);
								mIntent.putExtra("REQUEST_DATA", requestData);
								mIntent.putExtra("remId", id);
								mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								mIntent.putExtra("hsdInfo", mHsdString);
								Log.i("mHsdString", mHsdString);
								//删除数据库全部回收单数据
								EvalLossInfoAction action=new EvalLossInfoAction(context);
								action.deleteAllEvalInfo();
								context.startActivity(mIntent);
							} else {
								Toast.makeText(context, responseMessage,
										Toast.LENGTH_SHORT).show();
							}
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(context, "其它异常错误", Toast.LENGTH_LONG).show();
				}
			}

		}.execute();
	}

	
}
