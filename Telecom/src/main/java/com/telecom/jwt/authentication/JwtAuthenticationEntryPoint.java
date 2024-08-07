package com.telecom.jwt.authentication;

import java.io.IOException;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.utils.Constants;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger logger = LogManager.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

		try {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			String message;

			final Exception exception = (Exception) request.getAttribute(Constants.EXCEPTION);
			
			final ExpiredJwtException expiredJwtException = (ExpiredJwtException) request.getAttribute("ExpiredJwtTocken");

			if (exception != null) {

				byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap(Constants.CAUSE, exception.toString()));
				response.getOutputStream().write(body);
				
			} else if(expiredJwtException != null){
				response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
				byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap(Constants.CAUSE, "token expire"));
				response.getOutputStream().write(body);
			} else {
				
				if (authException.getCause() != null) {
					message = authException.getCause().toString() + " " + authException.getMessage();
				} else {
					message = authException.getMessage();
				}

				byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
				response.getOutputStream().write(body);
				
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

}