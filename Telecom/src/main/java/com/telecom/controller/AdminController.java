package com.telecom.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.telecom.jwt.authentication.Role;
import com.telecom.models.authentication.AuthenticationRequest;
import com.telecom.models.authentication.AuthenticationResponse;
import com.telecom.models.response.AdminRes;
import com.telecom.models.response.CustomerRes;
import com.telecom.models.response.PaymentRes;
import com.telecom.models.response.SignUp;
import com.telecom.models.tables.Admin;
import com.telecom.models.tables.Customer;
import com.telecom.models.tables.LedgerTransaction;
import com.telecom.models.tables.MSTledger;
import com.telecom.models.tables.Payment;
import com.telecom.services.impl.AdminServiceImpl;
import com.telecom.services.impl.CustomerServiceImpl;
import com.telecom.services.impl.LedgerTransactionServiceImpl;
import com.telecom.services.impl.MSTledgerServiceImpl;
import com.telecom.services.impl.PaymentServiceImpl;
import com.telecom.utils.Constants;
import com.telecom.utils.ExcelHelper;
import com.telecom.utils.PasswordUtility;
import com.telecom.utils.StringUtility;

@Controller()
@RequestMapping("/api/v1/admin")
public class AdminController {

	private static final Logger logger = LogManager.getLogger(AdminController.class);

	@Autowired
	private AdminServiceImpl adminService;
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private MSTledgerServiceImpl mstLedgerServiceImpl;
	
	@Autowired
	private LedgerTransactionServiceImpl ledgerTransactionServiceImpl;
	
	@Autowired
	ExcelHelper excelHelper;
	
	@RequestMapping(value = "/signup",method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@RequestBody Admin admin){
		try {
			if(!StringUtility.isNullOrEmpty(admin.getEmail()) && !StringUtility.isNullOrEmpty(admin.getPassword()) && !StringUtility.isNullOrEmpty(admin.getName())){
				if(!adminService.isUserAvailable(admin.getEmail())) {
					String hashPassword = PasswordUtility.passwordHashed(admin.getPassword());
					admin.setPassword(hashPassword);
					admin.setCreatedDate(Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime());
					admin = adminService.save(admin);
					if(admin != null) {
						return ResponseEntity.ok(new SignUp(true,"User Signup Successfully, Please Contact Admin for approval"));
					}
				}else {
					return ResponseEntity.ok(new SignUp(false,"Email is already Registered"));
				}
				
			}
			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> getUser(@RequestBody AuthenticationRequest authenticationRequest){
		try {
			if(!StringUtility.isNullOrEmpty(authenticationRequest.getEmail()) && !StringUtility.isNullOrEmpty(authenticationRequest.getPassword())){
				
				String hashPassword = PasswordUtility.passwordHashed(authenticationRequest.getPassword());
				authenticationRequest.setPassword(hashPassword);
				
				Admin admin = adminService.getUserByEmailPassword(authenticationRequest.getEmail(), hashPassword);
				if(admin != null) {
					if(!admin.isArchive()) {
						List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_ADMIN));
						final UserDetails userDetails = new User(admin.getEmail(),"",roles);
						final String token = jwtTokenUtil.generateToken(userDetails,admin.getID());
						return ResponseEntity.ok(new AuthenticationResponse(token,"User is Approved",true,admin.getName(),null));
					}else {
						return ResponseEntity.ok(new AuthenticationResponse("","User has been deleted",false,"",""));
					}
				}else {
					return ResponseEntity.ok(new AuthenticationResponse("","please check email and password",false,"",""));
				}
			}
			
		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
		
	}
	
	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public ResponseEntity<?> updateAdmin(@RequestBody Admin admin, @RequestHeader Map<String, String> headers){
		try {

						if ( !StringUtility.isNullOrEmpty(admin.getPassword())) {

						String newPassword = admin.getPassword();

						admin = adminService.findById(admin.getID());

						if (admin != null) {
							String hashPassword = PasswordUtility.passwordHashed(newPassword);
							admin.setPassword(hashPassword);
							adminService.updateById(admin);
							return ResponseEntity.ok(new SignUp(true, "change password successfully"));
						} else {
							return ResponseEntity.ok(new SignUp(false, "old password is incorrect"));
						}
					}else {
						return ResponseEntity.ok(new SignUp(false, "Passwprd is empty please enter new"));
					}

		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/customer/updateprofile", method = RequestMethod.POST)
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestHeader Map<String, String> headers) {
		try {

			if (!StringUtility.isNullOrEmpty(customer.getPassword())) {

				String newPassword = customer.getPassword();

				customer = customerService.findById(customer.getID());

				if (customer != null) {
					String hashPassword = PasswordUtility.passwordHashed(newPassword);
					customer.setPassword(hashPassword);
					customerService.updateById(customer);
					return ResponseEntity.ok(new SignUp(true, "change password successfully"));
				} else {
					return ResponseEntity.ok(new SignUp(false, "old password is incorrect"));
				}
			} else {
				return ResponseEntity.ok(new SignUp(false, "Passwprd is empty please enter new"));
			}

		} catch (Exception e) {
			logger.error("Exception at verifyUser " + e);
		}

		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public ResponseEntity<?> deleteCustomer(@RequestBody Admin admin, @RequestHeader Map<String, String> headers){
		try {
			
//			String bearerToken = headers.get("authorization");
//
//			String token = "";
//			if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
//					token = bearerToken.substring(7, bearerToken.length());
//					System.out.println(token);
//			}
//			String adminID = jwtTokenUtil.getIDFromToken(token);
//			
			if(!StringUtility.isNullOrEmpty(admin.getID())){
				if(adminService.deleteUser(admin.getID())) {		
					return ResponseEntity.ok(new SignUp(true,"deleted user successfully"));
				}else {
					return ResponseEntity.ok(new SignUp(false,"delete is failed"));
				}
			}
			
		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAdmin(@RequestHeader Map<String, String> headers){
		try {
			List<Admin> admins = adminService.getAllAdmin();
			 
//			String str = mapper.writeValueAsString(payments);
			return ResponseEntity.ok(new AdminRes(true,"data fatched successfully",admins));
		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public ResponseEntity<?> getPayment(@RequestParam(name = "date", defaultValue = "") String fromDateStr,@RequestParam(name = "todate", defaultValue = "") String toDateStr) {
		try {
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

			List<Payment> payments = paymentService.getAllPaymentByFromToDate(fromDate,toDate);

			return ResponseEntity.ok(new PaymentRes(true, "data fatched successfully", payments));

		} catch (Exception e) {
			logger.error("Exception at verifyUser " + e);
		}

		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/payment/download", method = RequestMethod.GET)
	  public ResponseEntity<Resource> getExcel(@RequestParam(name = "date", defaultValue = "") String fromDateStr,@RequestParam(name = "todate", defaultValue = "") String toDateStr) {
	    String filename = "payments"+fromDateStr+toDateStr+".xlsx";
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

		List<Payment> payments = paymentService.getAllPaymentByFromToDate(fromDate,toDate);
	    InputStreamResource file = new InputStreamResource(excelHelper.paymentsToExcel(payments));
	    
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
	
	@RequestMapping(value = "/payment/update", method = RequestMethod.POST)
	public ResponseEntity<?> updatePayment(@RequestBody Payment payment,@RequestHeader Map<String, String> headers){
		try {
			
			String bearerToken = headers.get("authorization");

			String token = "";
			if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
					token = bearerToken.substring(7, bearerToken.length());
					System.out.println(token);
			}
			String adminID = jwtTokenUtil.getIDFromToken(token);
			
			Admin admin = adminService.findById(adminID);
			
			if(admin != null) {
					payment = paymentService.findById(payment.getID());
	
					payment.setApproved(true);
					payment.setApprovedBy(admin.getName());

					
					payment = paymentService.updateById(payment);
					
					if(payment != null) {
						
						MSTledger mstLedger = mstLedgerServiceImpl.getledgerByCustomerId(payment.getCustomer().getID());
						
						if(mstLedger != null) {
							mstLedger.setCredit(mstLedger.getCredit() + payment.getAmount());
							
							Date date = new java.sql.Date(System.currentTimeMillis());
							mstLedger.setDate(date);
							mstLedgerServiceImpl.updateById(mstLedger);
							
							LedgerTransaction ledgerTransaction = new LedgerTransaction();
							ledgerTransaction.setDate(date);
							ledgerTransaction.setCustomer(payment.getCustomer());
							ledgerTransaction.setCredit(payment.getAmount());
							ledgerTransaction.setRemarks(payment.getRemarks());
							
							Timestamp time =  new Timestamp(System.currentTimeMillis());
							ledgerTransaction.setCreatedDate(time);
							
							ledgerTransactionServiceImpl.save(ledgerTransaction);
							
							return ResponseEntity.ok(new SignUp(true,"Payment update successfully"));
							
						} 
						
						
						
						
					}else {
						return ResponseEntity.ok(new SignUp(false,"Payment is not updated please logout and login again"));
					}
			}

			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/payment/update/reject", method = RequestMethod.POST)
	public ResponseEntity<?> rejectPayment(@RequestBody Payment payment,@RequestHeader Map<String, String> headers){
		try {
			
			String adminRemarks = payment.getAdminRemarks();

					payment = paymentService.findById(payment.getID());
					
					if(payment != null) {
						payment.setArchive(true);
						payment.setAdminRemarks(adminRemarks);
						payment = paymentService.updateById(payment);
						return ResponseEntity.ok(new SignUp(true,"Payment rejected successfully"));
					} else {
						return ResponseEntity.ok(new SignUp(false,"Please try again"));
					}
			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.DELETE)
	public ResponseEntity<?> daletePayment(@RequestBody Payment payment){
		try {
			if(!StringUtility.isNullOrEmpty(payment.getID()) ){
				if(paymentService.delete(payment)) {
				return ResponseEntity.ok(new SignUp(true,"Payment deleted successfully"));
				}
				
			}else {
				return ResponseEntity.ok(new SignUp(false,"ID is not specified"));
			}
			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/customer/update", method = RequestMethod.POST)
	public ResponseEntity<?> updatePayment(@RequestBody Customer customer,@RequestHeader Map<String, String> headers){
		try {
			
			String bearerToken = headers.get("authorization");

			String token = "";
			if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
					token = bearerToken.substring(7, bearerToken.length());
					System.out.println(token);
			}
			String adminID = jwtTokenUtil.getIDFromToken(token);
			
			Admin admin = adminService.findById(adminID);
			
			if(admin != null) {
				customer = customerService.findById(customer.getID());
	
				customer.setApproved(true);
				customer.setApprovedBy(admin.getName());

					
				customer = customerService.updateById(customer);
					
					if(customer != null) {

						MSTledger mstLedger = new MSTledger();
						mstLedger.setCredit(0);
						mstLedger.setDebit(0);
						mstLedger.setCustomer(customer);
						Date date = new java.sql.Date(System.currentTimeMillis());
						mstLedger.setDate(date);
						mstLedger = mstLedgerServiceImpl.save(mstLedger);
						
						if(mstLedger != null) {
							return ResponseEntity.ok(new SignUp(true,customer.getName()+" is Approved"));
						}else {
							return ResponseEntity.ok(new SignUp(true,customer.getName()+" is Approved not added in ledger"));
						}
						
					}else {
						return ResponseEntity.ok(new SignUp(false,"Customer is not approved logout and login again"));
					}
			}

			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomer() {
		try {
			List<Customer> customer = customerService.findAll();

			return ResponseEntity.ok(new CustomerRes(true, "data fatched successfully", customer));

		} catch (Exception e) {
			logger.error("Exception at verifyUser " + e);
		}

		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

}
