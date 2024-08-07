package com.telecom.jwt.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.telecom.models.tables.Admin;
import com.telecom.models.tables.Customer;
import com.telecom.services.impl.AdminServiceImpl;
import com.telecom.services.impl.CustomerServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter{
	private static final Logger logger = LogManager.getLogger(CustomAuthenticationManager.class);

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@Autowired
	private AdminServiceImpl adminService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			String jwtToken = jwtTokenUtil.extractJwtFromRequest(request);

			if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {
				
				List<SimpleGrantedAuthority> roles = jwtTokenUtil.getRolesFromToken(jwtToken);
				UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "", roles);
				
				if(roles.contains(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER))) {
					Customer customer = customerService.findById(jwtTokenUtil.getIDFromToken(jwtToken));
					if(customer != null) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
				if(roles.contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN))){
					Admin admin = adminService.findById(jwtTokenUtil.getIDFromToken(jwtToken));
					
					if(admin != null) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
				
			} else {
				logger.info("Cannot set the Security Context");
			}

		 } catch (ExpiredJwtException ex) {

//			String isRefreshToken = request.getHeader("isRefreshToken");
			String requestURL = request.getRequestURL().toString();
			// allow for Refresh Token creation if following conditions are true.
//			allowForRefreshToken(ex, request);
			if (requestURL.contains("refreshtoken")) {
//				String jwtToken = jwtTokenUtil.extractJwtFromRequest(request);
//				
//				List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER));
//				
//				if(roles.contains(new SimpleGrantedAuthority(Role.ROLE_CUSTOMER))) {
//					final UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken),"",roles);
//					final String token = jwtTokenUtil.generateToken(userDetails,jwtTokenUtil.getIDFromToken(jwtToken));
//					request.setAttribute("claims", token);
//				}
				
				
				allowForRefreshToken(ex, request);
			} else
				request.setAttribute("ExpiredJwtTocken", ex);

		} catch (BadCredentialsException ex) {
			request.setAttribute("exception", ex);
		} catch (Exception ex) {
			request.setAttribute("exception", ex);
			System.out.println(ex);
		}
		filterChain.doFilter(request, response);

	}
	
	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
//		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}

}
