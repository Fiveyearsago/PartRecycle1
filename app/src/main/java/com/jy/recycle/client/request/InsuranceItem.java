package com.jy.recycle.client.request;


/**
 * �ձ���Ϣ
 * @author zhaowenbin
 *
 */
public class InsuranceItem {
	/**
	 * ����
	 */
	private Long _id ; 
	/**
	 * ��������
	 */
	private Long evalId ; 
	/**
	 * �ձ�����
	 */
	private String insureTerm ; 
	/**
	 * �ձ����
	 */
	private String insureTermCode ;
	
	public String getInsureTerm() {
		return insureTerm;
	}
	public void setInsureTerm(String insureTerm) {
		this.insureTerm = insureTerm;
	}
	public String getInsureTermCode() {
		return insureTermCode;
	}
	public void setInsureTermCode(String insureTermCode) {
		this.insureTermCode = insureTermCode;
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
