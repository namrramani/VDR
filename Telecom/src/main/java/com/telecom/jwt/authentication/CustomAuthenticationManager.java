package com.telecom.jwt.authentication;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthenticationManager implements AuthenticationManager {
	private static final Logger logger = LogManager.getLogger(CustomAuthenticationManager.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {

			String username = authentication.getName();
			String password = authentication.getCredentials().toString();

			ArrayList<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority(Role.ROLE_USER));
			return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
			
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
}
