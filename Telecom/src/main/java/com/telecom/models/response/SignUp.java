package com.telecom.models.response;

public class SignUp {

	private boolean issuccess;
	
	private String message;
	
	public SignUp(boolean issuccess,String message) {
		this.issuccess = issuccess;
		this.message = message;
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
}
