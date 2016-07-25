package com.jy.recycle.client.response;

import com.jy.mobile.dto.FitsDTO;

/**
 * 换件项目信息
 * 
 * @author zhaowenbin
 * 
 */
public class EvalPartInfo {
	private Long _id;

	private Long evalId;
	private String careState;
	

	public String getCareState() {
		return careState;
	}
	public void setCareState(String careState) {
		this.careState = careState;
	}
	/**
	 * 配件Id
	 */
	private String partId;
	/**
	 * 序列号：按估损单从1开始排序，若指定项被删除则跳过该号。
	 */
	private Integer serialNo;
	/**
	 * 原厂零件号
	 */
	private String originalId;
	/**
	 * 原厂零件名称
	 */
	private String originalName;
	/**
	 * 配件标准编码
	 */
	private String partStandardCode;
	/**
	 * 配件标准名称
	 */
	private String partStandard;
	/**
	 * 配件部位代码
	 */
	private String partGroupCode;
	/**
	 * 配件部位名称
	 */
	private String partGroupName;
	/**
	 * 定损价格
	 */
	private Double lossPrice;
	/**
	 * 修理厂报价
	 */
	private Double repairSitePrice;
	/**
	 * 换件数量
	 */
	private Integer lossCount;
	/**
	 * 换件金额小计：定损价格ⅹ换件数量 - 残值
	 */
	private Double sumPrice;
	/**
	 * 自定义标记： 1：自定义 0：一次点选 2：二次点选
	 */
	private String selfConfigFlag;
	/**
	 * 价格方案代码：编码
	 */
	private String chgCompSetCode;
	/**
	 * 价格方案代码：编码
	 */
	private String chgCompSetId;
	private String deleteFlag;
	
	private String localFlag;
	private String LocPriceJmFlag;
	private String editFlag;
	private String huishouFlag;

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
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public void setLocPriceJmFlag(String locPriceJmFlag) {
		LocPriceJmFlag = locPriceJmFlag;
	}
	public String getLocPriceJmFlag() {
		return LocPriceJmFlag;
	}
	public String getLocalFlag() {
		return localFlag;
	}

	public void setLocalFlag(String localFlag) {
		this.localFlag = localFlag;
	}

	public String getChgCompSetId() {
		return chgCompSetId;
	}

	public void setChgCompSetId(String chgCompSetId) {
		this.chgCompSetId = chgCompSetId;
	}
	/**
	 * 价格方案名称
	 */
	private String chgCompSetName;
	/**
	 * 价格方案系统价格
	 */
	private Double chgRefPrice;
	/**
	 * 价格方案本地价格
	 */
	private Double chgLocPrice;
	/**
	 * 系统价格1
	 */
	private Double refPrice1;
	/**
	 * 本地价格1
	 */
	private Double locPrice1;
	/**
	 * 系统价格2
	 */
	private Double refPrice2;
	/**
	 * 本地价格2
	 */
	private Double locPrice2;
	/**
	 * 系统价格3
	 */
	private Double refPrice3;
	/**
	 * 本地价格3
	 */
	private Double locPrice3;
	
	
	/**
	 *核价价格
	 */
	private Double auditPrice;
	/**
	 *核价状态
	 */
	private String auditState;
	/**
	 *核价意见
	 */
	private String auditMemo;
	/**
	 *定损意见
	 */
	private String lossOption;
	private String oldDetail;//新旧配件标识
	private String goodListId;//服务器生成的配件ID
	

	public String getGoodListId() {
		return goodListId;
	}
	public void setGoodListId(String goodListId) {
		this.goodListId = goodListId;
	}
	public String getOldDetail() {
		return oldDetail;
	}
	public void setOldDetail(String oldDetail) {
		this.oldDetail = oldDetail;
	}
	public Double getAuditPrice() {
		return auditPrice;
	}

	public void setAuditPrice(Double auditPrice) {
		this.auditPrice = auditPrice;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getLossOption() {
		return lossOption;
	}

	public void setLossOption(String lossOption) {
		this.lossOption = lossOption;
	}

	public Double getRefPrice1() {
		return refPrice1;
	}

	public void setRefPrice1(Double refPrice1) {
		this.refPrice1 = refPrice1;
	}

	public Double getLocPrice1() {
		return locPrice1;
	}

	public void setLocPrice1(Double locPrice1) {
		this.locPrice1 = locPrice1;
	}

	public Double getRefPrice2() {
		return refPrice2;
	}

	public void setRefPrice2(Double refPrice2) {
		this.refPrice2 = refPrice2;
	}

	public Double getLocPrice2() {
		return locPrice2;
	}

	public void setLocPrice2(Double locPrice2) {
		this.locPrice2 = locPrice2;
	}

	public Double getRefPrice3() {
		return refPrice3;
	}

	public void setRefPrice3(Double refPrice3) {
		this.refPrice3 = refPrice3;
	}

	public Double getLocPrice3() {
		return locPrice3;
	}

	public void setLocPrice3(Double locPrice3) {
		this.locPrice3 = locPrice3;
	}
	/**
	 * 是否损余：1-是，0-否
	 */
	private String ifRemain;
	/**
	 * 残值
	 */
	private Double remnant;
	/**
	 * 险别名称
	 */
	private String insureTerm;
	/**
	 * 险别代码
	 */
	private String insureTermCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 精友数据库中的id
	 */
	private String jySystemId;

	public EvalPartInfo() {
	}

	public EvalPartInfo(FitsDTO fits) {
		this.partId = fits.getLjid();
		this.partStandard = fits.getLjbzmc();
		this.partStandardCode = fits.getLjbzbh();
		this.lossPrice = fits.getLossPrice();
		this.chgRefPrice = fits.getXtjg()==null ? 0d : fits.getXtjg();
		this.chgLocPrice = fits.getBdjg()==null ? 0d : fits.getBdjg();

		this.refPrice1 = fits.getRefPrice1()==null ? 0d : fits.getRefPrice1();
		this.refPrice2 = fits.getRefPrice2()==null ? 0d : fits.getRefPrice2();
		this.refPrice3 = fits.getRefPrice3()==null ? 0d : fits.getRefPrice3();
		this.locPrice1 = fits.getLocPrice1()==null ? 0d : fits.getLocPrice1();
		this.locPrice2 = fits.getLocPrice2()==null ? 0d : fits.getLocPrice2();
		this.locPrice3 = fits.getLocPrice3()==null ? 0d : fits.getLocPrice3();
		
		this.partGroupCode = fits.getLjfzbh();
		this.partGroupName = fits.getLjfzmc();
		this.originalId = fits.getYcbh();
		this.originalName = fits.getYcljmc();
		this.remark = fits.getBz();
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public Long getEvalId() {
		return evalId;
	}

	public void setEvalId(Long evalId) {
		this.evalId = evalId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getPartStandardCode() {
		return partStandardCode;
	}

	public void setPartStandardCode(String partStandardCode) {
		this.partStandardCode = partStandardCode;
	}

	public String getPartStandard() {
		return partStandard;
	}

	public void setPartStandard(String partStandard) {
		this.partStandard = partStandard;
	}

	public String getPartGroupCode() {
		return partGroupCode;
	}

	public void setPartGroupCode(String partGroupCode) {
		this.partGroupCode = partGroupCode;
	}

	public String getPartGroupName() {
		return partGroupName;
	}

	public void setPartGroupName(String partGroupName) {
		this.partGroupName = partGroupName;
	}

	public Double getLossPrice() {
		return lossPrice;
	}

	public void setLossPrice(Double lossPrice) {
		this.lossPrice = lossPrice;
	}

	public Double getRepairSitePrice() {
		return repairSitePrice;
	}

	public void setRepairSitePrice(Double repairSitePrice) {
		this.repairSitePrice = repairSitePrice;
	}

	public Integer getLossCount() {
		return lossCount;
	}

	public void setLossCount(Integer lossCount) {
		this.lossCount = lossCount;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getSelfConfigFlag() {
		return selfConfigFlag;
	}

	public void setSelfConfigFlag(String selfConfigFlag) {
		this.selfConfigFlag = selfConfigFlag;
	}

	public String getChgCompSetCode() {
		return chgCompSetCode;
	}

	public void setChgCompSetCode(String chgCompSetCode) {
		this.chgCompSetCode = chgCompSetCode;
	}


	public String getChgCompSetName() {
		return chgCompSetName;
	}

	public void setChgCompSetName(String chgCompSetName) {
		this.chgCompSetName = chgCompSetName;
	}

	public Double getChgRefPrice() {
		return chgRefPrice;
	}

	public void setChgRefPrice(Double chgRefPrice) {
		this.chgRefPrice = chgRefPrice;
	}

	public Double getChgLocPrice() {
		return chgLocPrice;
	}

	public void setChgLocPrice(Double chgLocPrice) {
		this.chgLocPrice = chgLocPrice;
	}

	public String getIfRemain() {
		return ifRemain;
	}

	public void setIfRemain(String ifRemain) {
		this.ifRemain = ifRemain;
	}

	public Double getRemnant() {
		return remnant;
	}

	public void setRemnant(Double remnant) {
		this.remnant = remnant;
	}

	public String getInsureTerm() {
		return insureTerm;
	}

	public void setInsureTerm(String insureTerm) {
		this.insureTerm = insureTerm;
	}

	public String getInsureTermCode() {
		return insureTermCode;
	}

	public void setInsureTermCode(String insureTermCode) {
		this.insureTermCode = insureTermCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public String getJySystemId() {
		return jySystemId;
	}
	public void setJySystemId(String jySystemId) {
		this.jySystemId = jySystemId;
	}
}
