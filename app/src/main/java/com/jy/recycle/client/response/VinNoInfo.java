package com.jy.recycle.client.response;

import com.jy.mobile.dto.FitsDTO;

/**
 * 
 * 
 */
public class VinNoInfo {
	private Long _id;
	private String vinQustion ;//vin问题
	private String vinAnswre;//vin答案
	private String vinQustionID ;//vin 问题id
	private Long evalId;
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String getVinQustion() {
		return vinQustion;
	}
	public void setVinQustion(String vinQustion) {
		this.vinQustion = vinQustion;
	}
	public String getVinAnswre() {
		return vinAnswre;
	}
	public void setVinAnswre(String vinAnswre) {
		this.vinAnswre = vinAnswre;
	}
	public String getVinQustionID() {
		return vinQustionID;
	}
	public void setVinQustionID(String vinQustionID) {
		this.vinQustionID = vinQustionID;
	}
	public Long getEvalId() {
		return evalId;
	}
	public void setEvalId(Long evalId) {
		this.evalId = evalId;
	}
	
}
