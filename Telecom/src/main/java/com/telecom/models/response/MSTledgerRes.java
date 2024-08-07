package com.telecom.models.response;

import java.util.List;

import com.telecom.models.tables.MSTledger;


public class MSTledgerRes {
private boolean issuccess;
	
	private String message;
	
	List<MSTledger> data;
	
	public MSTledgerRes(boolean issuccess,String message,List<MSTledger> data) {
		this.issuccess=issuccess;
		this.message=message;
		this.data=data;
	}
	
	public boolean isIssuccess() {
		return issuccess;
	}

	public void setIssuccess(boolean issuccess) {
		this.issuccess = issuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<MSTledger> getData() {
		return data;
	}

	public void setData(List<MSTledger> data) {
		this.data = data;
	}
}
