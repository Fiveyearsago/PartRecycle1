package com.jy.recycle.action;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jy.recycle.client.response.EvalLossInfo;
import com.jy.recycle.client.response.EvalPartInfo;
import com.jy.recycle.dao.EvalLossInfoDao;
import com.jy.recycle.dao.EvalPartDao;
import com.jy.recycle.server.ServerApiManager;
import com.jy.recycle.util.SharedData;

import org.json.JSONObject;

import java.util.List;

public class EvalLossInfoAction extends BaseAction {
	
	private EvalLossInfoDao evalLossInfoDao = new EvalLossInfoDao();
	private EvalPartDao partDao = EvalPartDao.getInstance();

	public EvalLossInfoAction(Context context){
		super(context);
	}
	/**
	 * ���ݶ��𵥺š�����š������û�ȡ����Id
	 * @param estimateNo
	 * @param caseNo
	 * @return
	 */
	public long getEvalId(String estimateNo , String caseNo){
		return evalLossInfoDao.getEvalIdByEstimateNoAndCaseNo( estimateNo ,  caseNo);
	}

	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	public EvalLossInfo getEvalLossInfoByEvalId(long evalId){
		EvalLossInfo evalLossInfo = evalLossInfoDao.getEvalLossInfoByEvalId(evalId);
		return evalLossInfo ; 
	}

	/**
	 * ���³�����Ϣ
	 * @param evalLossInfo
	 * @return
	 */
	public long updateVehicleInfo(EvalLossInfo evalLossInfo){
		return evalLossInfoDao.updateVehicleInfo(evalLossInfo);
	}


	/**
	 * ɾ��������Ϣ
	 * һ����ɾ���б���
	 */
	public void deleteEvalLossInfo(Long evalId) {
		List<EvalPartInfo> partList = partDao.getListEvalPart(evalId); 
		for(EvalPartInfo part : partList ){
			partDao.delEvalPart(String.valueOf(part.get_id()));
		}
		
	}

	/**
	 * ɾ��������Ϣ
	 * @param evalId
	 * 
	 * @return
	 */
	public void deleteEvalInfo(long evalId){
		evalLossInfoDao.deleteEvalInfo(evalId);
	}
	/**
	 * ɾ�������л��յ�����Ϣ
	 * 
	 * @return
	 */
	public void deleteAllEvalInfo(){
		evalLossInfoDao.deleteAllEvalInfo();
	}
	public void deleteAllCompletedEvalInfo(){
		evalLossInfoDao.deleteAllCompletedEvalInfo();
	}
	/**
	 * ɾ�������л��յ�����Ϣ
	 * 
	 * @return
	 */
	public long insertEvalInfo(EvalLossInfo evalLossInfo){
		return evalLossInfoDao.insertEvalInfo(evalLossInfo);
	}
	public long initInsertEvalInfo() {
		// TODO Auto-generated method stub
		return evalLossInfoDao.initInsertEvalInfo();
	}
	public long getEvalState() {
		// TODO Auto-generated method stub
		return evalLossInfoDao.getHsdState();
	}
	public void deleteServerPartInfo(Long evalId, String remId) {
		// TODO Auto-generated method stub
			delOnePart(remId);
		
	}
	private void delOnePart(final String remId) {
		new AsyncTask<Void, Void, JSONObject>() {
			@Override
			protected void onPreExecute() {
				// progressDialog = ProgressDialog.show(EvalActivity.this,
				// "���Ժ򡣡���", "����ɾ���С���");
			}

			@Override
			protected JSONObject doInBackground(Void... params) {
				SharedData share = SharedData.data();
				JSONObject response = ServerApiManager.delAllPart(share.data()
						.getUserName(), remId);

				return response;
			}

			@Override
			public void onPostExecute(JSONObject dataJson) {
				if (dataJson != null) {
					Log.i("ɾ�����", dataJson.toString());
					// /progressDialog.dismiss();
				}
			}
		}.execute();
	}
	public void deleteEvalLossInfo(long evalId, String partId) {
		// TODO Auto-generated method stub
		partDao.delEvalPart(evalId,partId);
	}
}
