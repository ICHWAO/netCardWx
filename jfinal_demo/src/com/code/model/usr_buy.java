package com.code.model;

import java.io.Serializable;
import java.util.Date;

public class usr_buy implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String tcid;
	private String tcmc;
	private double gmjg;
	private String oId;
	private Date gmsj;
	
	public Date getGmsj() {
		return gmsj;
	}
	public void setGmsj(Date gmsj) {
		this.gmsj = gmsj;
	}
	public String getTcmc() {
		return tcmc;
	}
	public void setTcmc(String tcmc) {
		this.tcmc = tcmc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTcid() {
		return tcid;
	}
	public void setTcid(String tcid) {
		this.tcid = tcid;
	}
	public double getGmjg() {
		return gmjg;
	}
	public void setGmjg(double gmjg) {
		this.gmjg = gmjg;
	}
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}

}
