package com.telecom.models.tables;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(	name = "payment_details")
public class Payment {
	
	public Payment(Date date, String utr,String city,double amount) {
		this.date = date;
		this.utrno = utr;
		this.city =city;
		this.amount = amount;
	}
	public Payment() {
		
	}

	@JsonProperty("ID")
	@Id
	@Column(name = "ID_payment_details")
    private String ID = UUID.randomUUID().toString();
	
	@Column(name = "city_payment_details")
	private String city;
	
	@ManyToOne
	@JoinColumn(name="customerID_payment_details", referencedColumnName="ID_customer_details")
	private Customer customer;
	
	@Column(name = "amount_payment_details")
	private double amount;
	
	@Column(name = "imageurl_payment_details")
	private String imageurl;
	
	@Column(name = "utrno_payment_details")
	private String utrno;
	
	@Column(name = "date_payment_details")
	private Date date;
	
	@Column(name = "bankName_payment_details")
	private String bank;

	@JsonProperty("isApproved")
	@Column(name = "isApproved_payment_details")
	private boolean isApproved;
	
	@Column(name = "approvedBy_payment_details")
	private String approvedBy;
	
	@Column(name = "createdDate_payment_details")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate_payment_details")
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	@Column(name = "remarks_payment_details")
	private String remarks;
	
	@Column(name = "admin_remarks_payment_details")
	private String adminRemarks;

	@Column(name = "isArchive_payment_details")
	private boolean isArchive;
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getUtrno() {
		return utrno;
	}

	public void setUtrno(String utrno) {
		this.utrno = utrno;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAdminRemarks() {
		return adminRemarks;
	}
	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}
	public boolean isArchive() {
		return isArchive;
	}
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}
}
