package com.jy.recycle.pojo;

import java.util.Date;

public class VersionInfoReq {
	ReqHeadVo reqHeadVo;
	public String equipmentType;
	public String applicationType;
	public String messageType;
	public ReqHeadVo getReqHeadVo() {
		return reqHeadVo;
	}
	public void setReqHeadVo(ReqHeadVo reqHeadVo) {
		this.reqHeadVo = reqHeadVo;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

}
