package com.telecom.models.response;

import java.util.List;

import com.telecom.models.tables.LedgerTransaction;


public class LedgerTransactionRes {
	private boolean issuccess;

private String message;
	
	List<LedgerTransaction> data;
	
	public LedgerTransactionRes(boolean issuccess,String message,List<LedgerTransaction> data) {
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

	public List<LedgerTransaction> getData() {
		return data;
	}

	public void setData(List<LedgerTransaction> data) {
		this.data = data;
	}
}
