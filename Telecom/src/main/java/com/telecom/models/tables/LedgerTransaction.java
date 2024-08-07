package com.telecom.models.tables;

import java.sql.Date;
import java.sql.Timestamp;
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
@Table(	name = "ledger_transaction_details")
public class LedgerTransaction {

	@JsonProperty("ID")
	@Id
	@Column(name = "ID_ledger_transaction_details")
    private String ID = UUID.randomUUID().toString();
	
	@ManyToOne
	@JoinColumn(name="customerID_ledger_transaction_details", referencedColumnName="ID_customer_details")
	private Customer customer;
	
	@Column(name = "credit_mst_ledger_details")
	private double credit;
	
	@Column(name = "debit_mst_ledger_details")
	private double debit;
	
//	@Column(name = "isCredit_ledger_transaction_details")
//	private boolean isCredit;
	
	@Column(name = "date_ledger_transaction_details")
	private Date date;
	
	@Column(name = "createdDate_ledger_transaction_details")
	@CreatedDate
	private Timestamp createdDate;
	
	@Column(name = "modifiedDate_ledger_transaction_details")
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	@Column(name = "remarks_ledger_transaction_details")
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
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
