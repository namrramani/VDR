package com.telecom.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.models.tables.MSTledger;
import com.telecom.repository.sqlrepo.MSTledgerRepository;
import com.telecom.servicesofI.MSTledgerService;

@Service
public class MSTledgerServiceImpl implements MSTledgerService{
	
	private static final Logger logger = LogManager.getLogger(MSTledgerServiceImpl.class);

	@Autowired
	MSTledgerRepository mstLedgerRepository;
	@Override
	public MSTledger save(MSTledger mstledger) {
		try {
			return mstLedgerRepository.save(mstledger);
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : save "+ e);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MSTledger findById(String id) {
		try {
			return mstLedgerRepository.findById(id).get();
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : findById "+ e);
		}
		return null;
	}

	@Override
	public boolean delete(MSTledger mstledger) {
		try {
			mstLedgerRepository.delete(mstledger);
			return true;
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : delete "+ e);
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MSTledger> findAll() {
		try {
			return mstLedgerRepository.findAll();
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : findAll "+ e);
		}
		return null;
	}

	@Override
	public MSTledger updateById(MSTledger mstledger) {
		try {
			return mstLedgerRepository.save(mstledger);
		}catch(Exception e) {
			logger.error("Exception at MSTledgerServiceImpl : updateById " +e);
		}
		return null;
	}

	public MSTledger getledgerByCustomerId(String customerId) {
		try {
			return mstLedgerRepository.getledgerByCustomerId(customerId);
		}catch(Exception e) {
			logger.info("Exception at MSTledgerServiceImpl : findById "+ e);
		}
		return null;
	}

}
