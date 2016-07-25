package com.jy.recycle.client.response;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author SongRan
 * 
 */
public class HSDInfo implements Serializable{
	private String remId;
	private String vin;
	private String cph;
	private String bah;
	private String carProperty;
	private String ppmc;
	private String ppbm;
	private String vehSeriName;
	private String vehCertainName;
	private String vehCertainBm;
	private String assessName;
	private String repairPhone;
	private String repairAddress;
	private String sfid;
	private String sfmc;
	private String csid;
	private String csmc;
	private String vipRoleDate;
	private List<PJInfo> pjInfos;
	private List<TihuoInfo> tihuoInfos;
	private String vehCertainId;
	private String vehSeriId;

	public String getRepairFlag() {
		return repairFlag;
	}

	public void setRepairFlag(String repairFlag) {
		this.repairFlag = repairFlag;
	}

	private String repairFlag;
	public String getVehCertainId() {
		return vehCertainId;
	}

	public void setVehCertainId(String vehCertainId) {
		this.vehCertainId = vehCertainId;
	}

	public String getVehSeriId() {
		return vehSeriId;
	}

	public void setVehSeriId(String vehSeriId) {
		this.vehSeriId = vehSeriId;
	}


	public HSDInfo(String remId, String vin, String cph, String bah,
			String carProperty, String ppmc, String ppbm, String vehSeriName,
			String vehCertainName, String vehCertainBm, String assessName,
			String repairPhone, String repairAddress, String sfid, String sfmc,
			String csid, String csmc, String vipRoleDate,String vehSeriId,String vehCertainId,String repairFlag, List<PJInfo> pjInfos,
			List<TihuoInfo> tihuoInfos) {
		super();
		this.remId = remId;
		this.vin = vin;
		this.cph = cph;
		this.bah = bah;
		this.carProperty = carProperty;
		this.ppmc = ppmc;
		this.ppbm = ppbm;
		this.vehSeriName = vehSeriName;
		this.vehCertainName = vehCertainName;
		this.vehCertainBm = vehCertainBm;
		this.assessName = assessName;
		this.repairPhone = repairPhone;
		this.repairAddress = repairAddress;
		this.sfid = sfid;
		this.sfmc = sfmc;
		this.csid = csid;
		this.csmc = csmc;
		this.vipRoleDate = vipRoleDate;
		this.vehSeriId = vehSeriId;
		this.vehCertainId = vehCertainId;
		this.repairFlag=repairFlag;
		this.pjInfos = pjInfos;
		this.tihuoInfos = tihuoInfos;
	}

	public HSDInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "HSDInfo [remId=" + remId + ", vin=" + vin + ", cph=" + cph
				+ ", bah=" + bah + ", carProperty=" + carProperty + ", ppmc="
				+ ppmc + ", ppbm=" + ppbm + ", vehSeriName=" + vehSeriName
				+ ", vehCertainName=" + vehCertainName + ", vehCertainBm="
				+ vehCertainBm + ", assessName=" + assessName
				+ ", repairPhone=" + repairPhone + ", repairAddress="
				+ repairAddress + ", sfid=" + sfid + ", sfmc=" + sfmc
				+ ", csid=" + csid + ", csmc=" + csmc + ", vipRoleDate="
				+ vipRoleDate + ", pjInfos=" + pjInfos + ", tihuoInfos="
				+ tihuoInfos + "]";
	}

	public String getRemId() {
		return remId;
	}

	public void setRemId(String remId) {
		this.remId = remId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getCph() {
		return cph;
	}

	public void setCph(String cph) {
		this.cph = cph;
	}

	public String getBah() {
		return bah;
	}

	public void setBah(String bah) {
		this.bah = bah;
	}

	public String getCarProperty() {
		return carProperty;
	}

	public void setCarProperty(String carProperty) {
		this.carProperty = carProperty;
	}

	public String getPpmc() {
		return ppmc;
	}

	public void setPpmc(String ppmc) {
		this.ppmc = ppmc;
	}

	public String getPpbm() {
		return ppbm;
	}

	public void setPpbm(String ppbm) {
		this.ppbm = ppbm;
	}

	public String getVehSeriName() {
		return vehSeriName;
	}

	public void setVehSeriName(String vehSeriName) {
		this.vehSeriName = vehSeriName;
	}

	public String getVehCertainName() {
		return vehCertainName;
	}

	public void setVehCertainName(String vehCertainName) {
		this.vehCertainName = vehCertainName;
	}

	public String getVehCertainBm() {
		return vehCertainBm;
	}

	public void setVehCertainBm(String vehCertainBm) {
		this.vehCertainBm = vehCertainBm;
	}

	public String getAssessName() {
		return assessName;
	}

	public void setAssessName(String assessName) {
		this.assessName = assessName;
	}

	public String getRepairPhone() {
		return repairPhone;
	}

	public void setRepairPhone(String repairPhone) {
		this.repairPhone = repairPhone;
	}

	public String getRepairAddress() {
		return repairAddress;
	}

	public void setRepairAddress(String repairAddress) {
		this.repairAddress = repairAddress;
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

	public String getVipRoleDate() {
		return vipRoleDate;
	}

	public void setVipRoleDate(String vipRoleDate) {
		this.vipRoleDate = vipRoleDate;
	}

	public List<PJInfo> getPjInfos() {
		return pjInfos;
	}

	public void setPjInfos(List<PJInfo> pjInfos) {
		this.pjInfos = pjInfos;
	}

	public List<TihuoInfo> getTihuoInfos() {
		return tihuoInfos;
	}

	public void setTihuoInfos(List<TihuoInfo> tihuoInfos) {
		this.tihuoInfos = tihuoInfos;
	}

}
