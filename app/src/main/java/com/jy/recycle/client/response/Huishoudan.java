package com.jy.recycle.client.response;

public class Huishoudan {
	public String id;// ���յ�id
	public String bah;// ������
	public String state;// ���յ�״̬״̬(1000�ݴ�,1001�������,1002ȷ�����,1003����)
	public String cph;// ���ƺ�
	public String car;// ��ʧ����
	public String secondFlag;// �Խӱ�ʶ



	public String sureRecycle;// ���ձ�ʶ

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
