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
	 * 根据定损单号、任务号、案件好获取定损Id
	 * @param estimateNo
	 * @param caseNo
	 * @return
	 */
	public long getEvalId(String estimateNo , String caseNo){
		return evalLossInfoDao.getEvalIdByEstimateNoAndCaseNo( estimateNo ,  caseNo);
	}

	/**
	 * 获取请求信息
	 * @return
	 */
	public EvalLossInfo getEvalLossInfoByEvalId(long evalId){
		EvalLossInfo evalLossInfo = evalLossInfoDao.getEvalLossInfoByEvalId(evalId);
		return evalLossInfo ; 
	}

	/**
	 * 更新车型信息
	 * @param evalLossInfo
	 * @return
	 */
	public long updateVehicleInfo(EvalLossInfo evalLossInfo){
		return evalLossInfoDao.updateVehicleInfo(evalLossInfo);
	}


	/**
	 * 删除定损信息
	 * 一条条删除列表项
	 */
	public void deleteEvalLossInfo(Long evalId) {
		List<EvalPartInfo> partList = partDao.getListEvalPart(evalId); 
		for(EvalPartInfo part : partList ){
			partDao.delEvalPart(String.valueOf(part.get_id()));
		}
		
	}

	/**
	 * 删除定损单信息
	 * @param evalId
	 * 
	 * @return
	 */
	public void deleteEvalInfo(long evalId){
		evalLossInfoDao.deleteEvalInfo(evalId);
	}
	/**
	 * 删除定所有回收单单信息
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
	 * 删除定所有回收单单信息
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
				// "请稍候。。。", "正在删除中。。");
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
					Log.i("删除配件", dataJson.toString());
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
