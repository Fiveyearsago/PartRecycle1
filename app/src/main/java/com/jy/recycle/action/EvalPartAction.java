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
	 * 新增零件
	 * @param value
	 */
	public long insertEvalFits(EvalPartInfo evalPartInfo){
		return this.epDao.insertEvalFits(evalPartInfo);
	}
	
	/**
	 * 主键查询出换件列表主页展示
	 * @param evalId
	 * @return
	 */
	public List<EvalPartInfo> getListEvalPart(Long evalId){ 
		return epDao.getListEvalPart(evalId);
	}
	/**
	 * 增加零件先判断是否已经有此零件了
	 * @param evalId
	 * @param partName
	 * @return
	 */
	public boolean getExistsEvalFits(Long evalId,String partName){
		return epDao.getExistsEvalFits(evalId,partName);
	}
//	/**
//	 * 新增零件
//	 * @param value
//	 */
//	public void insetEvalFits(ContentValues value){
//		epDao.insetEvalFits(value);
//	}
	/**
	 * 获取换件详细信息
	 * @param id
	 * @return
	 */
	public EvalPartInfo getEvalPartById(String id){
		return epDao.getEvalPartById(id);
	}
	/**
	 * 修改换件
	 * @param id
	 * @param value
	 */
	public void updateEvalPart(EvalPartInfo evalPartInfo){
		epDao.updateEvalPart(evalPartInfo);
	}
	/**
	 * 删除换件
	 * @param id
	 */
	public void delEvalPart(String id){
		epDao.delEvalPart(id);
	}
}
