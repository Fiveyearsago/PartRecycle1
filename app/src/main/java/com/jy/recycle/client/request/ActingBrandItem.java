package com.jy.recycle.client.request;


/**
 * �ձ���Ϣ
 * @author zhaowenbin
 *
 */
public class ActingBrandItem {
	/**
	 * ����
	 */
	private Long _id ; 
	/**
	 * ��������
	 */
	private Long evalId ; 
	/**
	 * Ʒ������
	 */
	private String brandName ; 
	/**
	 * Ʒ�ƴ���
	 */
	private String brandCode ;
	
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public Long getEvalId() {
		return evalId;
	}
	public void setEvalId(Long evalId) {
		this.evalId = evalId;
	}
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	} 
	
	
}
