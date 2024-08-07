package com.telecom.models.tables;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(	name = "customer_details", uniqueConstraints = { @UniqueConstraint(columnNames = "email_customer_details") })
public class Customer {

	@JsonProperty("ID")
	@Id
	@Column(name = "ID_customer_details", nullable = false)
    private String ID = UUID.randomUUID().toString();
	
	@Column(name = "name_customer_details")
	private String name;
	
	@Column(name = "firmName_customer_details")
	private String firmName;
	
//	@Column(name = "city_customer_details")
//	private String city;
	
	@Column(name = "password_customer_details")
	private String password;

	@Column(name = "email_customer_details")
	private String email;
	
	@Column(name = "mobile_customer_details")
	private String mobile;
	
	@Column(name = "isApproved_customer_details")
	private boolean isApproved;
	
	@Column(name = "approvedBy_customer_details")
	private String approvedBy;
	
	@Column(name = "createdDate_customer_details")
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate_customer_details")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifiedDate;
	
	@Column(name = "remarks_customer_details")
	private String remarks;
	
//	public Customer(String ID) {
//		this.ID = ID;
//	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
