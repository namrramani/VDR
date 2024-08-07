package com.telecom.repository.sqlrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.telecom.models.tables.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{
	
	@Query(value = "SELECT * FROM customer_details Where email_customer_details=?1 and password_customer_details=?2", nativeQuery = true)
	public Customer getCustomerByEmailPassword(String email,String password);
	
	@Query(value = "SELECT * FROM customer_details Where email_customer_details=?1", nativeQuery = true)
	public Customer isCustomerAvailable(String email);

}
