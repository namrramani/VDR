package com.telecom.repository.sqlrepo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telecom.models.tables.LedgerTransaction;
import com.telecom.models.tables.Payment;


public interface LedgerTransactionRepository extends JpaRepository<LedgerTransaction, String>{
	
	@Query(value = "SELECT * FROM ledger_transaction_details  Where date_ledger_transaction_details between ?1 And ?2 And customerID_ledger_transaction_details=?3 ORDER BY createdDate_ledger_transaction_details ASC", nativeQuery = true)
	public List<LedgerTransaction> getLedgerByDateAndCustomer(Date fromdate,Date todate,String customerID);

}
