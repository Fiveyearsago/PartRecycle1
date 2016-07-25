package com.jy.recycle.client.request;


/**
 * 险别信息
 * @author zhaowenbin
 *
 */
public class ActingBrandItem {
	/**
	 * 主键
	 */
	private Long _id ; 
	/**
	 * 定损主键
	 */
	private Long evalId ; 
	/**
	 * 品牌名称
	 */
	private String brandName ; 
	/**
	 * 品牌代码
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
