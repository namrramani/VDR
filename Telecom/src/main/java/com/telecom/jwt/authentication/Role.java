package com.telecom.jwt.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

	private String authority;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}

}