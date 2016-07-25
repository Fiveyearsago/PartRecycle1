package com.jy.recycle.pojo;

public class VersionInfoRsp {
	RspMsgVo rspMsgVo;
	public String isDevice;
	public String isApp;
	public String isUser;
	public String isUpdate;
	public String isForceUp;
	public String upgradePath;
	public String versionMessage;
	public RspMsgVo getRspMsgVo() {
		return rspMsgVo;
	}
	public void setRspMsgVo(RspMsgVo rspMsgVo) {
		this.rspMsgVo = rspMsgVo;
	}
	public String getIsDevice() {
		return isDevice;
	}
	public void setIsDevice(String isDevice) {
		this.isDevice = isDevice;
	}
	public String getIsApp() {
		return isApp;
	}
	public void setIsApp(String isApp) {
		this.isApp = isApp;
	}
	public String getIsUser() {
		return isUser;
	}
	public void setIsUser(String isUser) {
		this.isUser = isUser;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getIsForceUp() {
		return isForceUp;
	}
	public void setIsForceUp(String isForceUp) {
		this.isForceUp = isForceUp;
	}
	public String getUpgradePath() {
		return upgradePath;
	}
	public void setUpgradePath(String upgradePath) {
		this.upgradePath = upgradePath;
	}
	public String getVersionMessage() {
		return versionMessage;
	}
	public void setVersionMessage(String versionMessage) {
		this.versionMessage = versionMessage;
	}
	
}
