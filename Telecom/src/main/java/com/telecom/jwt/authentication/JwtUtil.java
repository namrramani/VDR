package com.telecom.jwt.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.telecom.utils.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtil {
	private static final Logger logger = LogManager.getLogger(JwtUtil.class);
	private String secret;
	private int jwtExpirationInMs;

	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Value("${jwt.expirationDateInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	// generate token for user
	public String generateToken(UserDetails userDetails,String Id) {
		try {
			Map<String, Object> claims = new HashMap<>();
			Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
			if (roles.contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN))) {
				claims.put("isAdmin", true);
			}
			if (roles.contains(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER))) {
				claims.put("isCustomer", true);
			}
			claims.put("ID", Id);

			return doGenerateToken(claims, userDetails.getUsername());
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		try {
			return Jwts.builder().setClaims(claims).setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}
	
	public String extractJwtFromRequest(HttpServletRequest request) {
		try {
			String bearerToken = request.getHeader(Constants.AUTHORIZATION);
			if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
				return bearerToken.substring(7, bearerToken.length());
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return null;
	}

	public boolean validateToken(String authToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			throw ex;
		}
	}

	public String getUsernameFromToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

			return claims.getSubject();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
		List<SimpleGrantedAuthority> roles = null;
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
			Boolean isAdmin = claims.get("isAdmin", Boolean.class);
			Boolean isUser = claims.get("isUser", Boolean.class);
			Boolean isCustomer = claims.get("isCustomer", Boolean.class);
			
			if (isAdmin != null && isAdmin == true) {
				roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_ADMIN));
			}
			if (isUser != null && isUser == true) {
				roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_USER));
			}
			if (isCustomer != null && isCustomer == true) {
				roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER));
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return roles;
	}

	public String getIDFromToken(String authToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
			return claims.get("ID", String.class);
			
		} catch (Exception e) {
			logger.error(e);
		}
	
		return null;
	}

}

