package com.jy.recycle.client.response;

public class CityInfo {
	private String csid;
	private String csmc;
	private String sfbm;
	private String csbm;
	public CityInfo(String csid, String csmc, String sfbm, String csbm) {
		this.csid = csid;
		this.csmc = csmc;
		this.sfbm = sfbm;
		this.csbm = csbm;
	}
	public String getCsid() {
		return csid;
	}
	public void setCsid(String csid) {
		this.csid = csid;
	}
	public String getCsmc() {
		return csmc;
	}
	public void setCsmc(String csmc) {
		this.csmc = csmc;
	}
	public String getSfbm() {
		return sfbm;
	}
	public void setSfbm(String sfbm) {
		this.sfbm = sfbm;
	}
	public String getCsbm() {
		return csbm;
	}
	public void setCsbm(String csbm) {
		this.csbm = csbm;
	}
	@Override
	public String toString() {
		return "CityInfo [csid=" + csid + ", csmc=" + csmc + ", sfbm=" + sfbm
				+ ", csbm=" + csbm + "]";
	}
	
	

}
