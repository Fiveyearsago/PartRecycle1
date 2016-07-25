package com.jy.recycle.pojo;

import java.util.Date;

public class ReqHeadVo {
	public String tokenID;
	public String userCode;
	public String applicationId;
	public String IMEI;
	public Date flowinTime;
	public String applicationVersionNo;
	public String getTokenID() {
		return tokenID;
	}
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public Date getFlowinTime() {
		return flowinTime;
	}
	public void setFlowinTime(Date flowinTime) {
		this.flowinTime = flowinTime;
	}
	public String getApplicationVersionNo() {
		return applicationVersionNo;
	}
	public void setApplicationVersionNo(String applicationVersionNo) {
		this.applicationVersionNo = applicationVersionNo;
	}
	
}
