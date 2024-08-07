package com.telecom.models.response;

import java.util.List;

import com.telecom.models.tables.Customer;
import com.telecom.models.tables.Payment;

public class CustomerRes {
private boolean issuccess;
	
	private String message;
	
	List<Customer> data;
	
	public CustomerRes(boolean issuccess,String message,List<Customer> data) {
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

	public List<Customer> getData() {
		return data;
	}

	public void setData(List<Customer> data) {
		this.data = data;
	}
}
