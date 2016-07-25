package com.jy.recycle.client.response;

public class Huishoudan {
	public String id;// 回收单id
	public String bah;// 报案号
	public String state;// 回收单状态状态(1000暂存,1001申请入库,1002确认入库,1003供货)
	public String cph;// 车牌号
	public String car;// 损失类型
	public String secondFlag;// 对接标识



	public String sureRecycle;// 回收标识

	public Huishoudan(String id, String bah, String cph, String car,
			String state, String secondFlag,String sureRecycle) {
		super();
		this.id = id;
		this.bah = bah;
		this.cph = cph;
		this.car = car;
		this.state = state;
		this.secondFlag = secondFlag;
		this.sureRecycle=sureRecycle;
	}
	public String getSureRecycle() {
		return sureRecycle;
	}

	public void setSureRecycle(String sureRecycle) {
		this.sureRecycle = sureRecycle;
	}
	public String getSecondFlag() {
		return secondFlag;
	}

	public void setSecondFlag(String secondFlag) {
		this.secondFlag = secondFlag;
	}

	public String getId() {
		return id;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBah() {
		return bah;
	}

	public void setBah(String bah) {
		this.bah = bah;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCph() {
		return cph;
	}

	public void setCph(String cph) {
		this.cph = cph;
	}

	@Override
	public String toString() {
		return "Huishoudan [id=" + id + ", bah=" + bah + ", state=" + state
				+ ", cph=" + cph + "]";
	}

}
