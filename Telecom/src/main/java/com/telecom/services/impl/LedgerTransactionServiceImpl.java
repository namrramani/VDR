package com.telecom.services.impl;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.models.tables.LedgerTransaction;
import com.telecom.models.tables.MSTledger;
import com.telecom.repository.sqlrepo.LedgerTransactionRepository;
import com.telecom.servicesofI.LedgerTransactionService;

@Service
public class LedgerTransactionServiceImpl implements LedgerTransactionService{

	private static final Logger logger = LogManager.getLogger(LedgerTransactionServiceImpl.class);

	@Autowired
	LedgerTransactionRepository ledgerTransactionRepository;
	@Override
	public LedgerTransaction save(LedgerTransaction ledgerTransaction) {
		try {
			return ledgerTransactionRepository.save(ledgerTransaction);
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : save "+ e);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerTransaction findById(String id) {
		try {
			return ledgerTransactionRepository.findById(id).get();
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : findById "+ e);
		}
		return null;
	}

	@Override
	public boolean delete(LedgerTransaction ledgerTransaction) {
		try {
			ledgerTransactionRepository.delete(ledgerTransaction);
			return true;
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : delete "+ e);
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LedgerTransaction> findAll() {
		try {
			return ledgerTransactionRepository.findAll();
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : findAll "+ e);
		}
		return null;
	}
	
	@Override
	public List<LedgerTransaction> getLedgerByDateAndCustomer(Date fromDate,Date toDate,String customerID) {
		try {
			return ledgerTransactionRepository.getLedgerByDateAndCustomer(fromDate ,toDate,customerID);
		}catch(Exception e) {
			logger.error("Exception at MSTledgerServiceImpl : updateById " +e);
		}
		return null;
	}

	@Override
	public LedgerTransaction updateById(LedgerTransaction ledgerTransaction) {
		try {
			return ledgerTransactionRepository.save(ledgerTransaction);
		}catch(Exception e) {
			logger.error("Exception at MSTledgerServiceImpl : updateById " +e);
		}
		return null;
	}

}
