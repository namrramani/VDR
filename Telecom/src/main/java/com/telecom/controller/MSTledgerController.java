package com.telecom.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.telecom.jwt.authentication.JwtUtil;
import com.telecom.models.response.MSTledgerRes;
import com.telecom.models.response.SignUp;
import com.telecom.models.tables.Customer;
import com.telecom.models.tables.LedgerTransaction;
import com.telecom.models.tables.MSTledger;
import com.telecom.models.tables.Payment;
import com.telecom.services.impl.CustomerServiceImpl;
import com.telecom.services.impl.LedgerTransactionServiceImpl;
import com.telecom.services.impl.MSTledgerServiceImpl;
//import com.telecom.models.response.PaymentRes;
//import com.telecom.models.tables.Payment;
import com.telecom.utils.Constants;
import com.telecom.utils.StringUtility;

@Controller()
@RequestMapping("/api/v1/admin/ledger")
public class MSTledgerController {

	private static final Logger logger = LogManager.getLogger(MSTledgerController.class);
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private MSTledgerServiceImpl mstLedgerServiceImpl;
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	private LedgerTransactionServiceImpl ledgerTransactionServiceImpl;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> addAllLedger(){
		try {
				List<MSTledger> mstLedgers = mstLedgerServiceImpl.findAll();
				 
				return ResponseEntity.ok(new MSTledgerRes(true,"data fatched successfully",mstLedgers));
			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addLedger(@RequestBody MSTledger mstLedger){
		try {
			
//			boolean isCrdit = mstLedger.isCredit();
//			double credit = mstLedger.getCredit();
//			double debit = mstLedger.getDebit();
			
			Customer customer = mstLedger.getCustomer();
			
			mstLedger = new MSTledger();
			mstLedger.setCredit(0);
			mstLedger.setDebit(0);
			mstLedger.setCustomer(customer);
			Date date = new java.sql.Date(System.currentTimeMillis());
			mstLedger.setDate(date);
			mstLedgerServiceImpl.save(mstLedger);
			
			return ResponseEntity.ok(new SignUp(true,"Ledger added successfully"));
			
//			mstLedger = mstLedgerServiceImpl.getledgerByCustomerId(customerId);
			
//			if(mstLedger != null) {
//				mstLedger.setCredit(mstLedger.getCredit()+credit);
//				mstLedger.setCredit(debit+mstLedger.getDebit());
//				Date date = new java.sql.Date(System.currentTimeMillis());
//				mstLedger.setDate(date);
//				mstLedgerServiceImpl.updateById(mstLedger);
//			} else {
//				
//			}
	
//			return ResponseEntity.ok(new SignUp(true,"Ledger added successfully"));
										
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		return ResponseEntity.ok(new SignUp(false,"Ledger does not added"));
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResponseEntity<?> updateLedger(@RequestBody MSTledger mstLedger){
		try {
			
			boolean isCrdit = mstLedger.isCredit();
			double credit = mstLedger.getCredit();
			double debit = mstLedger.getDebit();
			String remarks = mstLedger.getRemarks();
			
			Customer customer = mstLedger.getCustomer();
			String customerId = customer.getID();
			
			mstLedger = mstLedgerServiceImpl.getledgerByCustomerId(customerId);
			
			if(mstLedger != null) {
				mstLedger.setCredit(mstLedger.getCredit() + credit);
				mstLedger.setDebit(mstLedger.getDebit() + debit);
				Date date = new java.sql.Date(System.currentTimeMillis());
				mstLedger.setDate(date);
				mstLedgerServiceImpl.updateById(mstLedger);
				
				LedgerTransaction ledgerTransaction = new LedgerTransaction();
				ledgerTransaction.setDate(date);
				Timestamp time =  new Timestamp(System.currentTimeMillis());
				ledgerTransaction.setCreatedDate(time);
				ledgerTransaction.setCustomer(mstLedger.getCustomer());
				ledgerTransaction.setCredit(credit);
				ledgerTransaction.setDebit(debit);
				ledgerTransaction.setRemarks(remarks);
				
				ledgerTransactionServiceImpl.save(ledgerTransaction);
				
				return ResponseEntity.ok(new SignUp(true,"Ledger updated successfully"));
			} else {
				return ResponseEntity.ok(new SignUp(true,"Ledger is not updated successfully"));
			}
	
			
										
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		return ResponseEntity.ok(new SignUp(false,"Ledger does not added"));
	}
	
	
}
