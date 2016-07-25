package com.jy.recycle.dao;



/** @author Hibernate CodeGenerator */
public class PjJgxxbBd{

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String jgfaid;

    /** nullable persistent field */
    private String txjglx;

    /** nullable persistent field */
    private String jglylx;

    /** nullable persistent field */
    private String dyjglybh;

    /** nullable persistent field */
    private String jglx;

    /** nullable persistent field */
    private String mssm;

    /** nullable persistent field */
    private String bz;

    /** full constructor */
    public PjJgxxbBd(String jgfaid, String txjglx, String jglylx, String dyjglybh, String jglx, String mssm, String bz) {
        this.jgfaid = jgfaid;
        this.txjglx = txjglx;
        this.jglylx = jglylx;
        this.dyjglybh = dyjglybh;
        this.jglx = jglx;
        this.mssm = mssm;
        this.bz = bz;
    }

    /** default constructor */
    public PjJgxxbBd() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getJgfaid() {
		return jgfaid;
	}

	public void setJgfaid(String jgfaid) {
		this.jgfaid = jgfaid;
	}

	public String getTxjglx() {
        return this.txjglx;
    }

    public void setTxjglx(String txjglx) {
        this.txjglx = txjglx;
    }

    public String getJglylx() {
        return this.jglylx;
    }

    public void setJglylx(String jglylx) {
        this.jglylx = jglylx;
    }

    public String getDyjglybh() {
        return this.dyjglybh;
    }

    public void setDyjglybh(String dyjglybh) {
        this.dyjglybh = dyjglybh;
    }

    public String getJglx() {
        return this.jglx;
    }

    public void setJglx(String jglx) {
        this.jglx = jglx;
    }

    public String getMssm() {
        return this.mssm;
    }

    public void setMssm(String mssm) {
        this.mssm = mssm;
    }

    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

}
