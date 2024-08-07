package com.telecom.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.telecom.jwt.authentication.JwtUtil;
import com.telecom.models.generaic.DateInput;
import com.telecom.models.response.PaymentRes;
import com.telecom.models.response.SignUp;
import com.telecom.models.tables.Customer;
import com.telecom.models.tables.Payment;
import com.telecom.services.impl.CustomerServiceImpl;
import com.telecom.services.impl.PaymentServiceImpl;
import com.telecom.utils.Constants;
import com.telecom.utils.PasswordUtility;
import com.telecom.utils.StringUtility;

@Controller()
@RequestMapping("/api/v1/customer/payment")
public class PaymentController {

	private static final Logger logger = LogManager.getLogger(PaymentController.class);
	
	@Autowired
	PaymentServiceImpl paymentServiceImpl;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	private String uploadPath;
	
	@Value("${upload.image.path}")
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
//	private final Path root = Paths.get("src/main/resources/static/image/");
	
	private final Path root = Paths.get("C:\\xampp\\htdocs\\namra\\");
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> addCustomer(@RequestParam(name="date",defaultValue="") String dateStr, @RequestHeader Map<String, String> headers){
		try {
			 headers.forEach((key, value) -> {
				 logger.info(String.format("Header '%s' = %s", key, value));
			    });
			 String bearerToken = headers.get("authorization");

			 String token = "";
			 if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
					token = bearerToken.substring(7, bearerToken.length());
					
			}
			 String customerID = jwtTokenUtil.getIDFromToken(token);
//			 String customerID = "b372498e-5343-4f1b-a8df-ca660719cb05";
			Date date = new java.sql.Date(System.currentTimeMillis());
			ObjectMapper mapper = new ObjectMapper();
	        mapper.enable(SerializationFeature.INDENT_OUTPUT);
			if(!StringUtility.isNullOrEmpty(customerID)) {
				if(!StringUtility.isNullOrEmpty(dateStr)) {
					date=Date.valueOf(dateStr);

				}
				
				 List<Payment> payments = paymentServiceImpl.getAllPaymentByDate(date,customerID);
				 
//				String str = mapper.writeValueAsString(payments);
				return ResponseEntity.ok(new PaymentRes(true,"data fatched successfully",payments));
			}
			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addPayment(@RequestBody Payment payment, @RequestHeader Map<String, String> headers){
		try {
			logger.info(payment.getAmount() + ""+ payment.getDate());
			if(payment.getAmount() > 0 && payment.getDate() != null ){
//				if(!paymentServiceImpl.isUTRAlreadyPresent(payment.getUtrno())) {
				
				if(StringUtility.isNullOrEmpty(payment.getID())) {
					payment.setID(UUID.randomUUID().toString());
				}
					
					String bearerToken = headers.get("authorization");

					String token = "";
					if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
							token = bearerToken.substring(7, bearerToken.length());
					}
					String customerID = jwtTokenUtil.getIDFromToken(token);

					if (!StringUtility.isNullOrEmpty(customerID)) {
						Customer customer = new Customer();
						customer.setID(customerID);
						payment.setCustomer(customer);
						payment = paymentServiceImpl.save(payment);
						if (payment != null) {
							return ResponseEntity.ok(new SignUp(true, "Payment added successfully"));
						}
					}else {
						return ResponseEntity.ok(new SignUp(false,"customerID is not available"));
					}
//				}else {
//					return ResponseEntity.ok(new SignUp(false,"UTR No. not present"));
//				}

			}else {
				ResponseEntity.ok(new SignUp(false,"UTR No. , ammount or date is not present"));
			}
			
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, 
		      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		try {
//			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
			String filename = "";
			long millis = System.currentTimeMillis();
		    String datetime = new Date(millis).toGMTString();
		    datetime = datetime.replace(" ", "");
		    datetime = datetime.replace(":", "");
		    String rndchars = RandomStringUtils.randomAlphanumeric(16);
		    filename = FilenameUtils.removeExtension(file.getOriginalFilename()) +"_"+rndchars + "_" + datetime + "_" + millis+".";
		    
//	      File convertFile = new File("C:\\Users\\Administrator\\Downloads\\Telecom\\"+filename+FilenameUtils.getExtension(file.getOriginalFilename()));
	      File convertFile = new File(uploadPath+"/"+filename+FilenameUtils.getExtension(file.getOriginalFilename()));
	      convertFile.createNewFile();
	      FileOutputStream fout = new FileOutputStream(convertFile);
	      fout.write(file.getBytes());
	      fout.close();
	      
	      System.out.println();
	      return ResponseEntity.ok(new SignUp(true,filename+FilenameUtils.getExtension(file.getOriginalFilename())));
	      
		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	   }
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> updatePayment(@RequestBody Payment payment){
		try {
			if(!payment.isArchive()) {
			if( payment.getAmount() > 0 && payment.getDate() != null ){
//				if(paymentServiceImpl.isUTRAlreadyPresent(payment.getUtrno())) {
//				if (!StringUtility.isNullOrEmpty(customerID)) {
					
					
					String utr = payment.getUtrno();
					Date date = payment.getDate();
					String city = payment.getCity();
					double amount = payment.getAmount();
					String remarks = payment.getRemarks();
					String bank = payment.getBank();
					String image = payment.getImageurl();
					
					payment = paymentServiceImpl.findById(payment.getID());
					
					payment.setUtrno(utr);
					payment.setDate(date);
					payment.setAmount(amount);
					payment.setCity(city);
					payment.setRemarks(remarks);
					payment.setBank(bank);
					payment.setImageurl(image);
					
					payment = paymentServiceImpl.updateById(payment);
					
					if(payment != null) {
						return ResponseEntity.ok(new SignUp(true,"Payment update successfully"));
					}
				}
			} else{
				return ResponseEntity.ok(new SignUp(true,"Payment is rejected"));
			}

		}catch (Exception e) {
			logger.error("Exception at addCustomer "+e);
		}
		
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> daletePayment(@RequestBody Payment payment){
		try {
			if(!StringUtility.isNullOrEmpty(payment.getID()) ){
				if(paymentServiceImpl.delete(payment)) {
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
}
