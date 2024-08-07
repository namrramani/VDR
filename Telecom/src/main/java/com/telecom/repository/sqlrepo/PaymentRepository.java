package com.telecom.repository.sqlrepo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.telecom.models.response.PaymentRes;
import com.telecom.models.tables.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>{
	
	@Query(value = "SELECT * FROM payment_details Where utrno_payment_details=?1", nativeQuery = true)
	public Payment isUTRAlreadyPresent(String utrno);
	
	@Query(value = "SELECT * FROM payment_details  Where date_payment_details=?1 and customerID_payment_details=?2", nativeQuery = true)
	public List<Payment> getAllPaymentByDate(Date date,String customerID);
	
	@Query(value = "SELECT * FROM payment_details  Where date_payment_details=?1", nativeQuery = true)
	public List<Payment> getAllPaymentByDate(Date date);
	
	@Query(value = "SELECT * FROM payment_details  Where isArchive_payment_details=0 And date_payment_details between ?1 And ?2", nativeQuery = true)
	public List<Payment> getAllPaymentByFromToDate(Date fromdate,Date todate);
	
//	@Query(name = "PaymentRes",value = "SELECT ID_payment_details,amount_payment_details,city_payment_details,name_customer_details,utrno_payment_details,date_payment_details,isApproved_payment_details,remarks_payment_details FROM payment_details INNER JOIN customer_details ON ID_customer_details = customerID_payment_details Where date_payment_details=?1 and customerID_payment_details=?2", nativeQuery = true)
	@Query(value = "SELECT ID_payment_details as iD,amount_payment_details as aount,city_payment_details as city ,name_customer_details as name,utrno_payment_details as utrno,date_payment_details as date,isApproved_payment_details as isApproved,remarks_payment_details as remarks FROM payment_details INNER JOIN customer_details ON ID_customer_details = customerID_payment_details Where date_payment_details=?1 and customerID_payment_details=?2", nativeQuery = true)
	public  List<PaymentRes> getTestAllPaymentByDate(Date date,String customerID);

}
