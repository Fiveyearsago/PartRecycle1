package com.jy.recycle.pojo;

import java.util.ArrayList;
import java.util.List;

public class LossPart {
	
	private static List<LossPartItem> LOSS_PART_ITEM_LIST = new ArrayList<LossPartItem>();
	private static class LossPartItem{
		String name ; 
		String value ; 
		
		public LossPartItem(String value , String name ){
			this.name = name ;
			this.value = value ; 
		}
	}
	
	static{
		LossPartItem item = null ; 
		
		item = new LossPartItem("01" , "ȫ��");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("02" , "ǰ��");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("03" , "��ǰ��");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("04" , "��ǰ��");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("05" , "���");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("06" , "�Ҳ�");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("07" , "���");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("08" , "�Һ�");
		LOSS_PART_ITEM_LIST.add(item) ;
		
		item = new LossPartItem("09" , "�ײ�");
		LOSS_PART_ITEM_LIST.add(item) ;
		
		item = new LossPartItem("10" , "����");
		LOSS_PART_ITEM_LIST.add(item) ;
		
		item = new LossPartItem("11" , "β��");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
		item = new LossPartItem("12" , "�ڲ�");
		LOSS_PART_ITEM_LIST.add(item) ; 
		
	}
	
	public static String getNameByValue(String value){
		for(LossPartItem item : LOSS_PART_ITEM_LIST){
			if(item.value.equals(value)){
				return item.name ; 
			}
		}
		return null ; 
	}
	
	public static String getValueByName(String name){
		for(LossPartItem item : LOSS_PART_ITEM_LIST){
			if(item.name.equals(name)){
				return item.value ; 
			}
		}
		return null ; 
	}
	
	public static String getValuesByNames(String[] names){
		StringBuffer values = new StringBuffer();
		for(String name : names){
			String value = getValueByName(name);
			values.append(value).append(",");
		}
		return values.toString().substring(0,values.lastIndexOf(",")) ;
	}
	
	public static String getNamesByValues(String[] values){
		StringBuffer names = new StringBuffer();
		for(String value : values){
			String name = getNameByValue(value);
			names.append(name).append(",");
		}
		return names.toString().substring(0,names.lastIndexOf(",")) ;
	}
}
