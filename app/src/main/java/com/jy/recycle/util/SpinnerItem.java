package com.jy.recycle.util;

/**
 * spinner实现取VALUE值和TEXT值，重写toString简单实现
 *
 */
public class SpinnerItem {

	private String ID = "";
	private String Value = "";
	private String remark = "";
	
	
	public int id;

	public SpinnerItem() {
		id = -1;
		ID = "";
		Value = "";
	}

	public SpinnerItem(String _ID, String _Value) {
		ID = _ID;
		Value = _Value;
	}
	public SpinnerItem(int _id, String _Value) {
		id = _id;
		ID = String.valueOf(id);
		Value = _Value;
	}
	public SpinnerItem(String _ID, String _Value , String remark ) {
		this(_ID , _Value);
		this.remark = remark ;
	}
 
	@Override
	public String toString() { //为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
		return Value;
	}

	public String getID() {
		return ID;
	}

	public String getValue() {
		return Value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setValue(String value) {
		Value = value;
	}
	
	
}
