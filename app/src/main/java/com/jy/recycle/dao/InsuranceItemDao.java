package com.jy.recycle.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.jy.recycle.client.request.InsuranceItem;

public class InsuranceItemDao extends BaseDao {

	private final static String TABLE = "M_INSURANCE_ITEM";
	
	private static final InsuranceItemDao instance = new InsuranceItemDao();
	public static final InsuranceItemDao getInstance(){
		return instance ; 
	}
	
	public long insertInsuranceItem(InsuranceItem item){
		ContentValues values = new ContentValues();
		values.put(Columns.EVAL_ID, item.getEvalId());
		values.put(Columns.INSURE_TERM, item.getInsureTerm());
		values.put(Columns.INSURE_TERM_CODE, item.getInsureTermCode());
		
		long id = db.insert(TABLE, null, values);
		
		return id ; 
	}
	public void deleteInsuranceItem(Long evalId){
		String[] arg = { String.valueOf(evalId) };
		db.beginTran();
		try {
			db.delete(TABLE, "EVAL_ID=?", arg);
			db.setTranSuc();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			db.endTran();
		}
	}
	
	/**
	 * 获取险种列表
	 * @param evalId
	 * @return
	 */
	public List<InsuranceItem> getListInsuranceItem(Long evalId){
		List<InsuranceItem> itemList = new ArrayList<InsuranceItem>();
		
		String[] arg = { String.valueOf(evalId)};
		StringBuilder sb=new StringBuilder();
		sb.append(" select * ")
		  .append(" from "+TABLE+" where "+Columns.EVAL_ID+" =? ");
		
		Cursor cursor = db.querySql(sb.toString(), arg);
		if(cursor!=null){
			while(cursor.moveToNext()){
				InsuranceItem insuranceItem = getInsuranceItemByCursor(cursor);
				itemList.add(insuranceItem);
			}
			cursor.close();
		}
	    
	    return itemList;
	}
	/**
	 * 获取险别
	 * @param cursor
	 * @return
	 */
	public InsuranceItem getInsuranceItemByCursor(Cursor cursor){
		InsuranceItem item = new InsuranceItem();
		item.set_id(cursor.getLong(cursor.getColumnIndex(Columns._id)));
		item.setEvalId(cursor.getLong(cursor.getColumnIndex(Columns.EVAL_ID)));
		item.setInsureTerm(cursor.getString(cursor.getColumnIndex(Columns.INSURE_TERM)));
		item.setInsureTermCode(cursor.getString(cursor.getColumnIndex(Columns.INSURE_TERM_CODE)));
		return item ; 
	}
	
	public static final class Columns{
		public static final String _id = "_id";
		public static final String EVAL_ID = "EVAL_ID" ; 
		public static final String INSURE_TERM = "INSURE_TERM";
		public static final String INSURE_TERM_CODE = "INSURE_TERM_CODE" ; 
	}
}
