package com.telecom.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.util.Base64Utils;


public class PasswordUtility {

	private static final Logger logger = LogManager.getLogger(PasswordUtility.class);
	
	private static final String SALT = "gitatelecom";
	
//	public static String GeneratePassword(String plainText)
//    {
//		try {
//			
//		} catch (Exception e) {
//			logger.error("Exception at PasswordUtility : GeneratePassword "+e);
//		}
//    }
	
	public static String passwordHashed(String password) {
        return new Sha256Hash(password, SALT, 1024).toBase64();
    }

    public static boolean isPasswordValid(String encryptPassword, String rawPassword) {
        String hashedPassword = passwordHashed(rawPassword);
        return hashedPassword.equals(encryptPassword);
//        return StringUtils.equals(hashedPassword, encryptPassword);
    }

    public String decode(String password) {
        byte[] bytes = Base64Utils.decodeFromString(password);
        return new String(bytes);
    }

//    @Bean
//    public ShaPasswordEncoder passwordSpringEncoder() {
//        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
//        return encoder;
//    }
}
