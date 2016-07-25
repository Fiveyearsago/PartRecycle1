package com.jy.recycle.client.response;

import java.util.List;

import com.jy.recycle.client.request.ActingBrandItem;
import com.jy.recycle.client.request.InsuranceItem;

/**
 * 定损单响应信息
 * @author zhaowenbin
 *
 */
public class EvalLossInfo{
	
	private String plateNo;//车牌号
	private Long _id ;
    private String carNo;
    private String personName;
    private String personTel;
    private String personAdd;
    private String carText;
    private String reportOne;
    private String reportTwo;
    private String reportThree;
    private String reportFour;
    
    private String userName;
	private String vinNo;
    private String tiHuoShang;
    private String carProperty;
    private String hsdState;
	private String vipRoleDate;

	public String getRepairFlag() {
		return repairFlag;
	}

	public void setRepairFlag(String repairFlag) {
		this.repairFlag = repairFlag;
	}

	private String repairFlag;

	public EvalLossInfo() {
	}

	public String getReportFour() {
		return reportFour;
	}
	public void setReportFour(String reportFour) {
		this.reportFour = reportFour;
	}
	public String getVipRoleDate() {
		return vipRoleDate;
	}
	public void setVipRoleDate(String vipRoleDate) {
		this.vipRoleDate = vipRoleDate;
	}
	public String getHsdState() {
		return hsdState;
	}
	public void setHsdState(String hsdState) {
		this.hsdState = hsdState;
	}
	public String getCarProperty() {
		return carProperty;
	}
	public void setCarProperty(String carProperty) {
		this.carProperty = carProperty;
	}
	public String getTiHuoShang() {
		return tiHuoShang;
	}
	public void setTiHuoShang(String tiHuoShang) {
		this.tiHuoShang = tiHuoShang;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCarText() {
		return carText;
	}
	public void setCarText(String carText) {
		this.carText = carText;
	}
	public String getReportOne() {
		return reportOne;
	}
	public void setReportOne(String reportOne) {
		this.reportOne = reportOne;
	}
	public String getReportTwo() {
		return reportTwo;
	}
	public void setReportTwo(String reportTwo) {
		this.reportTwo = reportTwo;
	}
	public String getReportThree() {
		return reportThree;
	}
	public void setReportThree(String reportThree) {
		this.reportThree = reportThree;
	}


	public String getVinNo() {
		return vinNo;
	}
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}


	/**
	 * 任务号，唯一确定一个任务号。
	 */
	private String dmgVhclId ;

	/**
	 * 定损单号
	 */
	private String estimateNo ; 
	/**
	 * 事故号码
	 */
	private String caseNo ; 
	/**
	 * 定损车辆种类代码
	 */
	private String vehKindCode ; 
	/**
	 * 定损车辆种类名称
	 */
	private String vehKindName ; 
	/**
	 * 定损车型Id
	 */
	private String vehCertainId ; 
	
	/**
	 * 定损种类Id
	 */
	private String vehStandCertainId ; 
	/**
	 * 定损车型编码
	 */
	private String vehCertainCode ; 
	/**
	 * 定损车型名称
	 */
	private String vehCertainName ; 
	/**
	 * 定损车组名称
	 */
	private String vehGroupName ; 
	/**
	 * 定损车组代码
	 */
	private String vehGroupCode ; 
	/**
	 * 定损车系编码
	 */
	private String vehSeriCode ; 
	/**
	 * 定损车系编码
	 */
	private String vehSeriId ; 
	public String getVehSeriId() {
		return vehSeriId;
	}
	public void setVehSeriId(String vehSeriId) {
		this.vehSeriId = vehSeriId;
	}


	/**
	 * 定损车系名称
	 */
	private String vehSeriName ; 
	/**
	 * 年款
	 */
	private String vehYearType ; 
	/**
	 * 厂家编码
	 */
	private String vehFactoryCode ; 
	/**
	 * 厂家名称
	 */
	private String vehFactoryName ; 
	/**
	 * 品牌编码
	 */
	private String vehBrandCode ; 
	/**
	 * 品牌名称
	 */
	private String vehBrandName ; 
	/**
	 * 自定义车型标志  ：
	 * 1-自定义
	 * 0-系统点选
	 */
	private String selfConfigFlag ; 



	/**
	 * 备注
	 */
	private String remark ; 
	/**
	 * 换件列表
	 */
	private List<EvalPartInfo> evalPartList ;


	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	/**
	 * 所属分公司代码
	 */
	private String comCode ;
	/**
	 * 修理厂方
	 */
	//代理品牌
	private String actingBrand;


	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonTel() {
		return personTel;
	}
	public void setPersonTel(String personTel) {
		this.personTel = personTel;
	}
	public String getPersonAdd() {
		return personAdd;
	}
	public void setPersonAdd(String personAdd) {
		this.personAdd = personAdd;
	}
	public String getVehStandCertainId() {
		return vehStandCertainId;
	}
	public void setVehStandCertainId(String vehStandCertainId) {
		this.vehStandCertainId = vehStandCertainId;
	}


	public String getActingBrand() {
		return actingBrand;
	}
	public void setActingBrand(String actingBrand) {
		this.actingBrand = actingBrand;
	}

	public String getEstimateNo() {
		return estimateNo;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getVehKindCode() {
		return vehKindCode;
	}
	public void setVehKindCode(String vehKindCode) {
		this.vehKindCode = vehKindCode;
	}
	public String getVehKindName() {
		return vehKindName;
	}
	public void setVehKindName(String vehKindName) {
		this.vehKindName = vehKindName;
	}
	public String getVehCertainCode() {
		return vehCertainCode;
	}
	public void setVehCertainCode(String vehCertainCode) {
		this.vehCertainCode = vehCertainCode;
	}
	public String getVehCertainName() {
		return vehCertainName;
	}
	public void setVehCertainName(String vehCertainName) {
		this.vehCertainName = vehCertainName;
	}
	public String getVehGroupName() {
		return vehGroupName;
	}
	public void setVehGroupName(String vehGroupName) {
		this.vehGroupName = vehGroupName;
	}
	public String getVehGroupCode() {
		return vehGroupCode;
	}
	public void setVehGroupCode(String vehGroupCode) {
		this.vehGroupCode = vehGroupCode;
	}
	public String getVehSeriCode() {
		return vehSeriCode;
	}
	public void setVehSeriCode(String vehSeriCode) {
		this.vehSeriCode = vehSeriCode;
	}
	public String getVehSeriName() {
		return vehSeriName;
	}
	public void setVehSeriName(String vehSeriName) {
		this.vehSeriName = vehSeriName;
	}
	public String getVehYearType() {
		return vehYearType;
	}
	public void setVehYearType(String vehYearType) {
		this.vehYearType = vehYearType;
	}
	public String getVehFactoryCode() {
		return vehFactoryCode;
	}
	public void setVehFactoryCode(String vehFactoryCode) {
		this.vehFactoryCode = vehFactoryCode;
	}
	public String getVehFactoryName() {
		return vehFactoryName;
	}
	public void setVehFactoryName(String vehFactoryName) {
		this.vehFactoryName = vehFactoryName;
	}
	public String getVehBrandCode() {
		return vehBrandCode;
	}
	public void setVehBrandCode(String vehBrandCode) {
		this.vehBrandCode = vehBrandCode;
	}
	public String getVehBrandName() {
		return vehBrandName;
	}
	public void setVehBrandName(String vehBrandName) {
		this.vehBrandName = vehBrandName;
	}
	public String getSelfConfigFlag() {
		return selfConfigFlag;
	}
	public void setSelfConfigFlag(String selfConfigFlag) {
		this.selfConfigFlag = selfConfigFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<EvalPartInfo> getEvalPartList() {
		return evalPartList;
	}
	public void setEvalPartList(List<EvalPartInfo> evalPartList) {
		this.evalPartList = evalPartList;
	}

	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String getVehCertainId() {
		return vehCertainId;
	}
	public void setVehCertainId(String vehCertainId) {
		this.vehCertainId = vehCertainId;
	}
	public String getDmgVhclId() {
		return dmgVhclId;
	}
	public void setDmgVhclId(String dmgVhclId) {
		this.dmgVhclId = dmgVhclId;
	}

}
