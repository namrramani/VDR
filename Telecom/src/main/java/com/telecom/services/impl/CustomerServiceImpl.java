package com.telecom.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.TelecomApplication;
import com.telecom.models.tables.Customer;
import com.telecom.repository.sqlrepo.CustomerRepository;
import com.telecom.servicesofI.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	CustomerRepository customerRepository;
	

	@Override
	public Customer save(Customer customer) {
		try {
			return customerRepository.save(customer);
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : save "+ e);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findById(String id) {
		try {
			return customerRepository.findById(id).get();
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : save "+ e);
		}
		return null;
	}

	@Override
	public boolean delete(Customer customer) {
		try {
			customerRepository.delete(customer);
			return true;
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : delete "+ e);
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Customer> findAll() {
		try {
			return customerRepository.findAll();
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}

	@Override
	public Customer updateById(Customer customer) {
		try {
			return customerRepository.save(customer);
		}catch(Exception e) {
			logger.error("Exception at CustomerServiceImpl : updateById " +e);
		}
		return null;
	}

	@Override
	public Customer getCustomerByEmailPassword(String email, String password) {
		try {
			return customerRepository.getCustomerByEmailPassword(email, password);
		}catch (Exception e)
        {
       	 logger.error("Exception at  CustomerServiceImpl : getCustomerByEmailPassword "+e);
        }
        return null;
	}

	@Override
	public boolean isCustomerAvailable(String email) {
		try {
			Customer customer = customerRepository.isCustomerAvailable(email);
			if(customer != null) {
				return true;
			}
		}catch (Exception e)
        {
       	 logger.error("Exception at  CustomerServiceImpl : getCustomerByEmailPassword "+e);
        }
        return false;
	}
}
