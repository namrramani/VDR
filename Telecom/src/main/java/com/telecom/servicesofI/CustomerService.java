package com.telecom.servicesofI;

import java.util.List;

import com.telecom.models.tables.Customer;


public interface CustomerService {

	Customer save(Customer customer);
	Customer findById(String id);
	boolean delete(Customer customer);
	List<Customer> findAll();
	Customer updateById(Customer customer);
	Customer getCustomerByEmailPassword(String email,String password);
	boolean isCustomerAvailable(String email);
}
