package com.telecom.models.response;

import java.util.List;

import com.telecom.models.tables.Admin;


public class AdminRes {

private boolean issuccess;
	
	private String message;
	
	List<Admin> data;
	
	public AdminRes(boolean issuccess,String message,List<Admin> data) {
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

	public List<Admin> getData() {
		return data;
	}

	public void setData(List<Admin> data) {
		this.data = data;
	}
	
	
}
