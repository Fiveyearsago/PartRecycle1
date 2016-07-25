package com.jy.recycle.client.request;


/**
 * 险别信息
 * @author zhaowenbin
 *
 */
public class InsuranceItem {
	/**
	 * 主键
	 */
	private Long _id ; 
	/**
	 * 定损主键
	 */
	private Long evalId ; 
	/**
	 * 险别名称
	 */
	private String insureTerm ; 
	/**
	 * 险别代码
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
