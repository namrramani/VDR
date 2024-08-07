package com.telecom.models.response;

import java.util.List;

import com.telecom.models.tables.Payment;

public class PaymentRes {
	private boolean issuccess;
	
	private String message;
	
	List<Payment> data;
	
	public PaymentRes(boolean issuccess,String message,List<Payment> data) {
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

	public List<Payment> getData() {
		return data;
	}

	public void setData(List<Payment> data) {
		this.data = data;
	}
}
