package com.telecom.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.telecom.jwt.authentication.JwtUtil;
import com.telecom.jwt.authentication.Role;
import com.telecom.models.authentication.AuthenticationRequest;
import com.telecom.models.authentication.AuthenticationResponse;
import com.telecom.models.response.SignUp;
import com.telecom.models.tables.Admin;
import com.telecom.models.tables.Customer;
import com.telecom.services.impl.AdminServiceImpl;
import com.telecom.services.impl.CustomerServiceImpl;
import com.telecom.utils.Constants;
import com.telecom.utils.PasswordUtility;
import com.telecom.utils.StringUtility;

import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;

@Controller()
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	private static final Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	private CustomerServiceImpl customerService;
	
	@Autowired
	private AdminServiceImpl adminService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
		try {
			if(!StringUtility.isNullOrEmpty(customer.getEmail()) && !StringUtility.isNullOrEmpty(customer.getName()) && !StringUtility.isNullOrEmpty(customer.getFirmName())  && !StringUtility.isNullOrEmpty(customer.getPassword())){
				if(!customerService.isCustomerAvailable(customer.getEmail())) {
					String hashPassword = PasswordUtility.passwordHashed(customer.getPassword());
					customer.setPassword(hashPassword);
					customer.setCreatedDate(Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime());
					customer = customerService.save(customer);
					if(customer != null) {
						return ResponseEntity.ok(new SignUp(true,"User Signup Successfully, Please Contect Admin for approval"));
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
				
				Customer customer = customerService.getCustomerByEmailPassword(authenticationRequest.getEmail(), hashPassword);
				if(customer != null) {
					if(customer.isApproved()) {
						List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER));
						final UserDetails userDetails = new User(customer.getEmail(),"",roles);
						final String token = jwtTokenUtil.generateToken(userDetails,customer.getID());
						return ResponseEntity.ok(new AuthenticationResponse(token,"User is Approved",true,customer.getName(),customer.getFirmName()));
					}else {
						return ResponseEntity.ok(new AuthenticationResponse("","User is not Approved, Please Contact Admin",false,"",""));
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
	
	@RequestMapping(value = "/updateprofile", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestHeader Map<String, String> headers){
		try {
				String bearerToken = headers.get("authorization");

				String token = "";
				if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
						token = bearerToken.substring(7, bearerToken.length());
						System.out.println(token);
				}
				String customerID = jwtTokenUtil.getIDFromToken(token);
				//name -> name, firmname
				//password -> email(old password), password(new password)
				
//				String customerID = "b372498e-5343-4f1b-a8df-ca660719cb05";
				if(!StringUtility.isNullOrEmpty(customerID)) {
					
					if (!StringUtility.isNullOrEmpty(customer.getName())
							&& !StringUtility.isNullOrEmpty(customer.getFirmName())) {
						
						String name = customer.getName();
						String firmName = customer.getFirmName();
						customer = customerService.findById(customerID);
						customer.setName(name);
						customer.setFirmName(firmName);
						customerService.updateById(customer);
						return ResponseEntity.ok(new SignUp(true, "updated successfully"));
						
					} else if (!StringUtility.isNullOrEmpty(customer.getEmail())
							&& !StringUtility.isNullOrEmpty(customer.getPassword())) {

						String oldPassword = customer.getEmail();
						String hasholdPassword = PasswordUtility.passwordHashed(oldPassword);
						String newPassword = customer.getPassword();

						customer = customerService.findById(customerID);

						if (customer != null && customer.getPassword().equals(hasholdPassword)) {
							String hashPassword = PasswordUtility.passwordHashed(newPassword);
							customer.setPassword(hashPassword);
							customerService.updateById(customer);
							return ResponseEntity.ok(new SignUp(true, "change password successfully"));
						} else {
							return ResponseEntity.ok(new SignUp(false, "old password is incorrect"));
						}
					}
						
				}else {
					return ResponseEntity.ok(new SignUp(false,"customerID is not available"));
				}
		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public ResponseEntity<?> updatePasssword(@RequestBody String oldPassword,String newPassword, @RequestHeader Map<String, String> headers){
		try {
			if(!StringUtility.isNullOrEmpty(newPassword) && !StringUtility.isNullOrEmpty(oldPassword)){
				String bearerToken = headers.get("authorization");

				String token = "";
				if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
						token = bearerToken.substring(7, bearerToken.length());
						System.out.println(token);
				}
				String customerID = jwtTokenUtil.getIDFromToken(token);
				
				if (!StringUtility.isNullOrEmpty(customerID)) {
					Customer customer = customerService.findById(customerID);

					if (customer != null && customer.getPassword().equals(oldPassword)) {
						String hashPassword = PasswordUtility.passwordHashed(newPassword);
						customer.setPassword(hashPassword);
//					customer.setPassword(newPassword);
						customerService.updateById(customer);
						return ResponseEntity.ok(new SignUp(true,"change password successfully"));
					}
				}else {
					return ResponseEntity.ok(new SignUp(false,"customerID is not available"));
				}
			}
			
		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteCustomer(@RequestBody String customerID){
		try {
			if(!StringUtility.isNullOrEmpty(customerID)){
				Customer customer = new Customer();
				customer.setID(customerID);
				customerService.delete(customer);
				
				return new ResponseEntity(HttpStatus.OK);
			}
			
		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		try {
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new AuthenticationResponse(token,"token pass",true,"test","firmname"));
		} catch(Exception e) {
			logger.error(e);
		}
		
		try {
			String token = jwtTokenUtil.extractJwtFromRequest(request);
			return ResponseEntity.ok(new AuthenticationResponse(token,"token pass",true,"test","firmname"));
		}catch(Exception e) {
			logger.error(e);
		}
		return null;
		
		
	}
	
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
}
