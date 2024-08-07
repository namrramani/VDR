package com.telecom.models.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {

	private String token;
	
//	@JsonProperty("issuccess")
	private boolean issuccess;
	
	private String message;
	
	private String name;
	
	private String firmname;

	public boolean isIssuccess() {
		return issuccess;
	}

	public void setIssuccess(boolean issuccess) {
		this.issuccess = issuccess;
	}

	public AuthenticationResponse(String token,String message,boolean issuccess,String name,String firmname) {
		this.token = token;
		this.message = message;
		this.issuccess = issuccess;
		this.name = name;
		this.firmname = firmname;
	}

	public AuthenticationResponse() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
//	public boolean isSuccess() {
//		return issuccess;
//	}
//
//	public void setIsSuccess(boolean issuccess) {
//		this.issuccess = issuccess;
//	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirmname() {
		return firmname;
	}

	public void setFirmname(String firmname) {
		this.firmname = firmname;
	}
}
