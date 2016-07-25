package com.jy.recycle.util;

/**
 * spinnerʵ��ȡVALUEֵ��TEXTֵ����дtoString��ʵ��
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
	public String toString() { //ΪʲôҪ��дtoString()�أ���Ϊ����������ʾ���ݵ�ʱ����������������Ķ������ַ���������£�ֱ�Ӿ�ʹ�ö���.toString()
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
