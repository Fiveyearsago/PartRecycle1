package com.jy.recycle.task;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.ah.bus.data.Response;
import com.jy.recycle.R;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.util.SharedData;
import com.jy.recycle.util.SpinnerItem;

public class PriceSourceTask extends AsyncTask<String, Void, Response> {

	private Context context ; 
	public PriceSourceTask(Context context){
		this.context = context ; 
	}
	
	private ProgressDialog progressDialog ; 
	@Override
	protected void onPreExecute(){
//		progressDialog = ProgressDialog.show(context, "���Ժ�", "���ڻ�ȡ�۸񷽰�...");
	}
	
	@Override
	protected Response doInBackground(String... params) {
		Response response = null ; 
		try {
			response = ServerApiManager.getUserSettings(params[0] , params[1]);
		} catch (Exception e) {
			response = new Response();
			response.setResponseCode("0");
			response.setErrorMessage("��ȡ�۸񷽰�ʧ�ܣ�ԭ��" + e.getMessage());
		}
		return response;
	}
	
	@Override
	protected void onPostExecute(Response response) {
		String message = "" ; 
		if(progressDialog!=null){
			progressDialog.dismiss() ;
		}
		if (response != null && "1".equals(response.getResponseCode())) {
			String data = response.getData();
			// �����û����õ�SharedData��
			SharedData.data().saveJgfa(data);
			List<SpinnerItem> jgfalst = new ArrayList<SpinnerItem>();
			jgfalst = SharedData.data().getJgfa();
			if(jgfalst!=null&&jgfalst.size()>0){
				
				Log.i("PriceSource", data);
				message = "��ȡ�۸񷽰��ɹ�";
			}else if(SharedData.data().getPowerPriceSource1().equals("0")&&SharedData.data().getPowerPriceSource2().equals("0")&&SharedData.data().getPowerPriceSource3().equals("0")){
				message = "���û�û�м۸񷽰�Ȩ�ޣ��޷���ȡ�۸񷽰�";
			}else{
				message = "��ȡ�۸񷽰�ʧ��";
			}
		}else{
			message = "��ȡ�۸񷽰�ʧ��";
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.toast_layout, null);
		   TextView title = (TextView) layout.findViewById(R.id.textView_toast);
		   title.setText(message);
		   Toast toast = new Toast(context);
		   toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
		   toast.setDuration(Toast.LENGTH_LONG);
		   toast.setView(layout);
		   toast.show();
//		Toast.makeText(context, message , Toast.LENGTH_LONG).show() ; 
	}
}