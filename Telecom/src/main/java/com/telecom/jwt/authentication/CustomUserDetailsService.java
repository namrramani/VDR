package com.telecom.jwt.authentication;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			List<SimpleGrantedAuthority> roles = null;
			if (username.equals("admin")) {
				roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_ADMIN));
				return new User("admin", "$2a$10$0Nv/Qa7m8DdjWpBS2XRZWeP8rWDB7OdScb2grQSRDS9I9fWWlBNG2", roles);
			}
			if (username.equals("user")) {
				roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_USER));
				return new User("user", "$2a$10$5VikX1NNQFL9f.N7Ta5wVuBL5HuPi7ro5Q3UZYGVOCURwiotGrVCS", roles);
			}
			if(username.equals("custom")) {
				roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER));
				return new User("custom","",roles);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);
	}

}
