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
@Table(	name = "ledger_details")
public class Laser {

	@JsonProperty("ID")
	@Id
	@Column(name = "ID_laser_details")
    private String ID = UUID.randomUUID().toString();
	
	@ManyToOne
	@JoinColumn(name="customerID_laser_details", referencedColumnName="ID_customer_details")
	private Customer customer;
	
	@Column(name = "amount_laser_details")
	private double amount;
	
	@Column(name = "isCredit_laser_details")
	private boolean isCredit;
	
	@Column(name = "date_laser_details")
	private Date date;
	
	@Column(name = "type_laser_details")
	private String type;
	
	@Column(name = "createdDate_laser_details")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate_laser_details")
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	@Column(name = "remarks_laser_details")
	private String remarks;
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	public boolean isCredit() {
		return isCredit;
	}

	public void setCredit(boolean isCredit) {
		this.isCredit = isCredit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
