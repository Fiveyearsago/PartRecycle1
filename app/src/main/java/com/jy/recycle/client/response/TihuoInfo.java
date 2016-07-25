package com.jy.recycle.client.response;

import java.io.Serializable;

public class TihuoInfo implements Serializable{
	private String hssid;
	private String gsmc;
	private String lxr;
	private String lxdh;
	private String sjhm;
	private String sfmc;
	private String csmc;
	private String mrbz;
	private boolean isSelect;
	
	public TihuoInfo() {
		// TODO Auto-generated constructor stub
	}
	public TihuoInfo(String hssid, String gsmc, String lxr, String lxdh,
			String sjhm, String sfmc, String csmc, String mrbz,boolean isSelect) {
		this.hssid = hssid;
		this.gsmc = gsmc;
		this.lxr = lxr;
		this.lxdh = lxdh;
		this.sjhm = sjhm;
		this.sfmc = sfmc;
		this.csmc = csmc;
		this.mrbz = mrbz;
		this.isSelect = isSelect;
	}
	
	public boolean getIsSelect() {
		return isSelect;
	}
	public void setIsSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public String getHssid() {
		return hssid;
	}
	public void setHssid(String hssid) {
		this.hssid = hssid;
	}
	public String getGsmc() {
		return gsmc;
	}
	public void setGsmc(String gsmc) {
		this.gsmc = gsmc;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getSfmc() {
		return sfmc;
	}
	public void setSfmc(String sfmc) {
		this.sfmc = sfmc;
	}
	public String getCsmc() {
		return csmc;
	}
	public void setCsmc(String csmc) {
		this.csmc = csmc;
	}
	public String getMrbz() {
		return mrbz;
	}
	public void setMrbz(String mrbz) {
		this.mrbz = mrbz;
	}
	@Override
	public String toString() {
		return "TihuoInfo [hssid=" + hssid + ", gsmc=" + gsmc + ", lxr=" + lxr
				+ ", lxdh=" + lxdh + ", sjhm=" + sjhm + ", sfmc=" + sfmc
				+ ", csmc=" + csmc + ", mrbz=" + mrbz + "]";
	}
	
	

}
