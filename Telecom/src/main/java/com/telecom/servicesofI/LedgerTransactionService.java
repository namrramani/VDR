package com.telecom.servicesofI;

import java.sql.Date;
import java.util.List;

import com.telecom.models.tables.LedgerTransaction;


public interface LedgerTransactionService {

	LedgerTransaction save(LedgerTransaction ledgerTransaction);
	LedgerTransaction findById(String id);
	boolean delete(LedgerTransaction ledgerTransaction);
	List<LedgerTransaction> findAll();
	LedgerTransaction updateById(LedgerTransaction ledgerTransaction);
	List<LedgerTransaction> getLedgerByDateAndCustomer(Date fromDate,Date toDate,String customerID);
}
