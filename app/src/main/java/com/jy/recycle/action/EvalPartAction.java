package com.jy.recycle.action;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.jy.recycle.client.response.EvalPartInfo;
import com.jy.recycle.dao.EvalPartDao;
import com.jy.recycle.pojo.EvalPart;

public class EvalPartAction extends BaseAction{
	EvalPartDao epDao = null ; 
	
	public EvalPartAction(Context context) {
		super(context);
		this.epDao = EvalPartDao.getInstance();
	}
	
	/**
	 * �������
	 * @param value
	 */
	public long insertEvalFits(EvalPartInfo evalPartInfo){
		return this.epDao.insertEvalFits(evalPartInfo);
	}
	
	/**
	 * ������ѯ�������б���ҳչʾ
	 * @param evalId
	 * @return
	 */
	public List<EvalPartInfo> getListEvalPart(Long evalId){ 
		return epDao.getListEvalPart(evalId);
	}
	/**
	 * ����������ж��Ƿ��Ѿ��д������
	 * @param evalId
	 * @param partName
	 * @return
	 */
	public boolean getExistsEvalFits(Long evalId,String partName){
		return epDao.getExistsEvalFits(evalId,partName);
	}
//	/**
//	 * �������
//	 * @param value
//	 */
//	public void insetEvalFits(ContentValues value){
//		epDao.insetEvalFits(value);
//	}
	/**
	 * ��ȡ������ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public EvalPartInfo getEvalPartById(String id){
		return epDao.getEvalPartById(id);
	}
	/**
	 * �޸Ļ���
	 * @param id
	 * @param value
	 */
	public void updateEvalPart(EvalPartInfo evalPartInfo){
		epDao.updateEvalPart(evalPartInfo);
	}
	/**
	 * ɾ������
	 * @param id
	 */
	public void delEvalPart(String id){
		epDao.delEvalPart(id);
	}
}
