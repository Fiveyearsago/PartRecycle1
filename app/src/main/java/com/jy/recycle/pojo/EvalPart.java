package com.jy.recycle.pojo;

import java.io.Serializable;
import java.util.List;

import com.jy.mobile.dto.FitsDTO;

public class EvalPart implements Serializable {
	private static final long serialVersionUID = 1L;

	public long _id;
	public String evalId;
	public String partId;
	public String partName;
	public String originNo;
	public String originalName;
	public double lossPrice;
	public int lossCount;
	public double totalSum;
	public double localPrice;
	public double systemPrice;
	public String handFlag;
	public double refPrice1;
	public double refPrice2;
	public double localPrice1;
	public double localPrice2;
	public double localPrice3;
	public String reUseFlag;
	public double remain;
	public String remark;
	public String partGroupCode;
	public String partGroupName;


	public EvalPart() {
		// TODO Auto-generated constructor stub
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getEvalId() {
		return evalId;
	}

	public void setEvalId(String evalId) {
		this.evalId = evalId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getOriginNo() {
		return originNo;
	}

	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public double getLossPrice() {
		return lossPrice;
	}

	public void setLossPrice(double lossPrice) {
		this.lossPrice = lossPrice;
	}

	public int getLossCount() {
		return lossCount;
	}

	public void setLossCount(int lossCount) {
		this.lossCount = lossCount;
	}

	public double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}

	public double getLocalPrice() {
		return localPrice;
	}

	public void setLocalPrice(double localPrice) {
		this.localPrice = localPrice;
	}

	public double getSystemPrice() {
		return systemPrice;
	}

	public void setSystemPrice(double systemPrice) {
		this.systemPrice = systemPrice;
	}

	public String getHandFlag() {
		return handFlag;
	}

	public void setHandFlag(String handFlag) {
		this.handFlag = handFlag;
	}

	public double getRefPrice1() {
		return refPrice1;
	}

	public void setRefPrice1(double refPrice1) {
		this.refPrice1 = refPrice1;
	}

	public double getRefPrice2() {
		return refPrice2;
	}

	public void setRefPrice2(double refPrice2) {
		this.refPrice2 = refPrice2;
	}

	public double getLocalPrice1() {
		return localPrice1;
	}

	public void setLocalPrice1(double localPrice1) {
		this.localPrice1 = localPrice1;
	}

	public double getLocalPrice2() {
		return localPrice2;
	}

	public void setLocalPrice2(double localPrice2) {
		this.localPrice2 = localPrice2;
	}

	public double getLocalPrice3() {
		return localPrice3;
	}

	public void setLocalPrice3(double localPrice3) {
		this.localPrice3 = localPrice3;
	}

	public String getReUseFlag() {
		return reUseFlag;
	}

	public void setReUseFlag(String reUseFlag) {
		this.reUseFlag = reUseFlag;
	}

	public double getRemain() {
		return remain;
	}

	public void setRemain(double remain) {
		this.remain = remain;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
