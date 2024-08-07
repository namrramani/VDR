package com.telecom.servicesofI;

import java.sql.Date;
import java.util.List;

import com.telecom.models.response.PaymentRes;
import com.telecom.models.tables.Payment;


public interface PaymentService {

	Payment save(Payment payment);
	Payment findById(String id);
	boolean delete(Payment payment);
	List<Payment> findAll();
	Payment updateById(Payment payment);
	boolean isUTRAlreadyPresent(String utrno);
	List<Payment> getAllPaymentByDate(Date date,String customerID);
	List<Payment> getAllPaymentByDate(Date date);
	List<Payment> getAllPaymentByFromToDate(Date fromdate,Date todate);
	List<PaymentRes> getTestAllPaymentByDate(Date date,String customerID);
}
