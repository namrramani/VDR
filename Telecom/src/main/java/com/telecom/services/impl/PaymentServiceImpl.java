package com.telecom.services.impl;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.models.response.PaymentRes;
import com.telecom.models.tables.Payment;
import com.telecom.repository.sqlrepo.PaymentRepository;
import com.telecom.servicesofI.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);
	
	@Autowired
	PaymentRepository paymentRepository;
	@Override
	public Payment save(Payment payment) {
		try {
			return paymentRepository.save(payment);
		}catch(Exception e) {
			logger.info("Exception at PaymentServiceImpl : save "+ e);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Payment findById(String id) {
		try {
			return paymentRepository.findById(id).get();
		}catch(Exception e) {
			logger.info("Exception at PaymentServiceImpl : findById "+ e);
		}
		return null;
	}

	@Override
	public boolean delete(Payment payment) {
		try {
			paymentRepository.deleteById(payment.getID());
			return true;
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : delete "+ e);
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Payment> findAll() {
		try {
			return paymentRepository.findAll();
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}

	@Override
	public Payment updateById(Payment payment) {
		try {
			return paymentRepository.save(payment);
		}catch(Exception e) {
			logger.error("Exception at CustomerServiceImpl : updateById " +e);
		}
		return null;
	}

	public boolean isUTRAlreadyPresent(String utrno) {
		try {
			Payment payment = paymentRepository.isUTRAlreadyPresent(utrno);
			if(payment != null) {
				return true;
			}
		}catch (Exception e)
        {
       	 logger.error("Exception at  CustomerServiceImpl : getCustomerByEmailPassword "+e);
        }
        return false;
	}
	
	@Override
	public List<Payment> getAllPaymentByDate(Date date,String customerID) {
		try {
			return paymentRepository.getAllPaymentByDate(date,customerID);
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}
	
	@Override
	public List<Payment> getAllPaymentByDate(Date date) {
		try {
			return paymentRepository.getAllPaymentByDate(date);
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}
	
	@Override
	public List<Payment> getAllPaymentByFromToDate(Date fromdate,Date todate) {
		try {
			return paymentRepository.getAllPaymentByFromToDate(fromdate,todate);
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}
	
	@Override
	public List<PaymentRes> getTestAllPaymentByDate(Date date,String customerID) {
		try {
			return paymentRepository.getTestAllPaymentByDate(date,customerID);
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}

}
