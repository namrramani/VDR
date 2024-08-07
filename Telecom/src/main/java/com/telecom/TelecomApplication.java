package com.telecom;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telecom.models.response.AdminRes;
import com.telecom.models.tables.Admin;


@Configuration
@ComponentScan(basePackages={"com.telecom.*"})
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@Controller
public class TelecomApplication {

	private static final Logger logger = LogManager.getLogger(TelecomApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TelecomApplication.class, args);
		logger.info("info log");
	}
	
	@RequestMapping(value="/test",method = RequestMethod.GET)
	public ResponseEntity<?> getAdmin(@RequestHeader Map<String, String> headers){
		try {
			
			return ResponseEntity.ok(new String("test is working"));
		}catch (Exception e) {
			logger.error("Exception at verifyUser "+e);
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
}
