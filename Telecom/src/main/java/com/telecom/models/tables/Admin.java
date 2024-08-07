package com.telecom.models.tables;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(	name = "admin_details" )
public class Admin {
	
	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	@Id
	@JsonProperty("ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID_admin_details")
    private String ID= UUID.randomUUID().toString();

	@Column(name = "name_admin_details")
	private String name;

	@Column(name = "email_admin_details")
	private String email;

	@Column(name = "password_admin_details")
	private String password;
	
	@Column(name = "isArchive_admin_details")
	private boolean isArchive;
	
	@Column(name = "createdDate_admin_details")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name = "modifiedDate_admin_details")
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	@Column(name = "remarks_admin_details")
	private String remarks;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
