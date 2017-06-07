package com.code.model;

import java.io.Serializable;

public class usr_tc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;		//主键
	private String tcmc;		//套餐名称
	private double lsjg;		//零售价格
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTcmc() {
		return tcmc;
	}
	public void setTcmc(String tcmc) {
		this.tcmc = tcmc;
	}
	public double getLsjg() {
		return lsjg;
	}
	public void setLsjg(double lsjg) {
		this.lsjg = lsjg;
	}

}
