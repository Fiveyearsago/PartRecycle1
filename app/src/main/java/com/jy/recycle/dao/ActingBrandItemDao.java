package com.jy.recycle.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.jy.recycle.client.request.ActingBrandItem;
import com.jy.recycle.client.request.InsuranceItem;

public class ActingBrandItemDao extends BaseDao {

	private final static String TABLE = "M_ACTINGBRAND_ITEM";
	
	private static final ActingBrandItemDao instance = new ActingBrandItemDao();
	public static final ActingBrandItemDao getInstance(){
		return instance ; 
	}

	/**
	 * 获取险种列表
	 * @param evalId
	 * @return
	 */
	public List<ActingBrandItem> getListActingBrandItem(Long evalId){
		List<ActingBrandItem> itemList = new ArrayList<ActingBrandItem>();
		
		String[] arg = { String.valueOf(evalId)};
		StringBuilder sb=new StringBuilder();
		sb.append(" select * ")
		  .append(" from "+TABLE+" where "+Columns.EVAL_ID+" =? ");
		
		Cursor cursor = db.querySql(sb.toString(), arg);
		if(cursor!=null){
			while(cursor.moveToNext()){
				ActingBrandItem actingBrandItem = getActingBrandItemByCursor(cursor);
				itemList.add(actingBrandItem);
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
	public ActingBrandItem getActingBrandItemByCursor(Cursor cursor){
		ActingBrandItem item = new ActingBrandItem();
		item.set_id(cursor.getLong(cursor.getColumnIndex(Columns._id)));
		item.setEvalId(cursor.getLong(cursor.getColumnIndex(Columns.EVAL_ID)));
		item.setBrandCode(cursor.getString(cursor.getColumnIndex(Columns.ACTING_BRANDCODE)));
		item.setBrandName(cursor.getString(cursor.getColumnIndex(Columns.ACTING_BRANDNAME)));
		return item ; 
	}
	
	public static final class Columns{
		public static final String _id = "_id";
		public static final String EVAL_ID = "EVAL_ID" ; 
		public static final String ACTING_BRANDNAME = "ACTING_BRANDNAME";
		public static final String ACTING_BRANDCODE = "ACTING_BRANDCODE" ; 
	}
}
