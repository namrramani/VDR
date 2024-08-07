package com.telecom.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.telecom.models.response.LedgerTransactionRes;
import com.telecom.models.response.MSTledgerRes;
import com.telecom.models.tables.LedgerTransaction;
import com.telecom.models.tables.MSTledger;
import com.telecom.models.tables.Payment;
import com.telecom.services.impl.LedgerTransactionServiceImpl;
import com.telecom.utils.ExcelHelper;
import com.telecom.utils.StringUtility;

@Controller()
@RequestMapping("/api/v1/admin/ledger/history")
public class LedgerTransactionController {
	private static final Logger logger = LogManager.getLogger(LedgerTransactionController.class);
	
	@Autowired
	private LedgerTransactionServiceImpl ledgerTransactionServiceImpl;
	
	@Autowired
	ExcelHelper excelHelper;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> addAllLedger(@RequestParam(name = "date", defaultValue = "") String fromDateStr,@RequestParam(name = "todate", defaultValue = "") String toDateStr,@RequestParam(name = "customerid", defaultValue = "") String customerID){
		try {
//				ledgerTransactionServiceImpl.save(null)
			Date fromDate = new java.sql.Date(System.currentTimeMillis());
			Date toDate = new java.sql.Date(System.currentTimeMillis());
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			if (!StringUtility.isNullOrEmpty(fromDateStr)) {
				fromDate = Date.valueOf(fromDateStr);
			}
			if(!StringUtility.isNullOrEmpty(toDateStr)){
				toDate = Date.valueOf(toDateStr);
			}
//				Date date=Date.valueOf(dateStr);
				List<LedgerTransaction> ledgerTransactions = ledgerTransactionServiceImpl.getLedgerByDateAndCustomer(fromDate,toDate,customerID);
				 
				return ResponseEntity.ok(new LedgerTransactionRes(true,"data fatched successfully",ledgerTransactions));
			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/download", method = RequestMethod.GET)
	  public ResponseEntity<Resource> getExcel(@RequestParam(name = "date", defaultValue = "") String fromDateStr,@RequestParam(name = "todate", defaultValue = "") String toDateStr,@RequestParam(name = "customerid", defaultValue = "") String customerID) {
		
		Date fromDate = new java.sql.Date(System.currentTimeMillis());
		Date toDate = new java.sql.Date(System.currentTimeMillis());
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		if (!StringUtility.isNullOrEmpty(fromDateStr)) {
			fromDate = Date.valueOf(fromDateStr);
		}
		if(!StringUtility.isNullOrEmpty(toDateStr)){
			toDate = Date.valueOf(toDateStr);
		}
		
		List<LedgerTransaction> ledgerTransactions = ledgerTransactionServiceImpl.getLedgerByDateAndCustomer(fromDate,toDate,customerID);
		String filename = "ledger"+fromDate+toDate+customerID+".xlsx";
//		List<Payment> payments = paymentService.getAllPaymentByFromToDate(fromDate,toDate);
	    InputStreamResource file = new InputStreamResource(excelHelper.ledgerToExcel(ledgerTransactions));
	    
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
}
