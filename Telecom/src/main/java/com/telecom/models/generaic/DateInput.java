package com.telecom.models.generaic;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DateInput {

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private  Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
