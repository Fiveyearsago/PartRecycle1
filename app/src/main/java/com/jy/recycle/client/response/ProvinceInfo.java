package com.jy.recycle.client.response;

import java.util.List;

public class ProvinceInfo {
	private String sfid;
	private String sfmc;

	private List<CityInfo>list;
	public ProvinceInfo(String sfid, String sfmc, List<CityInfo> list) {
		this.sfid = sfid;
		this.sfmc = sfmc;
		this.list = list;
	}
	


	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getSfmc() {
		return sfmc;
	}
	public void setSfmc(String sfmc) {
		this.sfmc = sfmc;
	}
	public List<CityInfo> getList() {
		return list;
	}
	public void setList(List<CityInfo> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "ProvinceInfo [sfid=" + sfid + ", sfmc=" + sfmc + ", list="
				+ list + "]";
	}
	

}
