package com.jy.recycle.client.response;

public class PJInfo {
	private String goodListId;
	private String ljmc;
	private String ycljh;
	private String appNo;
	private String ljsm;
	private String partId;
	private String Carestate;
	private String oldDetail;
	private String editFlag;
	private String huishouFlag;
	public PJInfo(String goodListId, String ljmc, String ycljh, String appNo,
			String ljsm, String partId, String carestate,String oldDetail,String huishouFlag) {
		super();
		this.goodListId = goodListId;
		this.ljmc = ljmc;
		this.ycljh = ycljh;
		this.appNo = appNo;
		this.ljsm = ljsm;
		this.partId = partId;
		this.Carestate = carestate;
		this.oldDetail=oldDetail;
		this.huishouFlag=huishouFlag;
	}
	public PJInfo(String goodListId, String ljmc, String ycljh, String appNo,
			String ljsm, String partId, String carestate,String oldDetail,String editFlag,String huishouFlag) {
		super();
		this.goodListId = goodListId;
		this.ljmc = ljmc;
		this.ycljh = ycljh;
		this.appNo = appNo;
		this.ljsm = ljsm;
		this.partId = partId;
		this.Carestate = carestate;
		this.oldDetail=oldDetail;
		this.editFlag=editFlag;
		this.huishouFlag=huishouFlag;
	}

	@Override
	public String toString() {
		return "PJInfo [goodListId=" + goodListId + ", ljmc=" + ljmc
				+ ", ycljh=" + ycljh + ", appNo=" + appNo + ", ljsm=" + ljsm
				+ ", partId=" + partId + ", Carestate=" + Carestate + "]";
	}

	public String getHuishouFlag() {
		return huishouFlag;
	}
	public void setHuishouFlag(String huishouFlag) {
		this.huishouFlag = huishouFlag;
	}
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	public String getGoodListId() {
		return goodListId;
	}

	public void setGoodListId(String goodListId) {
		this.goodListId = goodListId;
	}

	public String getLjmc() {
		return ljmc;
	}

	public void setLjmc(String ljmc) {
		this.ljmc = ljmc;
	}

	public String getYcljh() {
		return ycljh;
	}

	public void setYcljh(String ycljh) {
		this.ycljh = ycljh;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getLjsm() {
		return ljsm;
	}

	public void setLjsm(String ljsm) {
		this.ljsm = ljsm;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getCarestate() {
		return Carestate;
	}

	public void setCarestate(String carestate) {
		Carestate = carestate;
	}
	public String getOldDetail() {
		return oldDetail;
	}

	public void setOldDetail(String oldDetail) {
		this.oldDetail = oldDetail;
	}
}
