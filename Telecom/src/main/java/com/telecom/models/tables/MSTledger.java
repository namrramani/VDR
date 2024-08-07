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
@Table(	name = "mst_ledger_details")
public class MSTledger {

	@JsonProperty("ID")
	@Id
	@Column(name = "ID_mst_ledger_details")
    private String ID = UUID.randomUUID().toString();
	
	@ManyToOne
	@JoinColumn(name="customerID_mst_ledger_details", referencedColumnName="ID_customer_details")
	private Customer customer;
	
	@Column(name = "credit_mst_ledger_details")
	private double credit;
	
	@Column(name = "debit_mst_ledger_details")
	private double debit;
	
	@Column(name = "isCredit_mst_ledger_details")
	private boolean isCredit;
	
	@Column(name = "date_mst_ledger_details")
	private Date date;
	
//	@Column(name = "type_mst_ledger_details")
//	private String type;
	
	@Column(name = "createdDate_mst_ledger_details")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate_mst_ledger_details")
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	@Column(name = "remarks_mst_ledger_details")
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

//	public String getType() {
//		return type;
//	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
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

	public boolean isCredit() {
		return isCredit;
	}

	public void setIsCredit(boolean isCredit) {
		this.isCredit = isCredit;
	}

//	public void setType(String type) {
//		this.type = type;
//	}
}
