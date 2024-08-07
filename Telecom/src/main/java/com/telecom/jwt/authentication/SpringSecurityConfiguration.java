package com.telecom.jwt.authentication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
	private static final Logger logger = LogManager.getLogger(SpringSecurityConfiguration.class);
	@Autowired
	CustomUserDetailsService userDetailsService;

	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		try {
			authProvider.setUserDetailsService(userDetailsService);
			authProvider.setPasswordEncoder(passwordEncoder());
		} catch (Exception e) {
			logger.error(e);
		}
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/test","/api/v1/customer/signup","/api/v1/customer/signin","/api/v1/admin/signin","/api/v1/admin/payment/download","/api/v1/admin/ledger/history/download");
	}
//	,"/api/v1/customer/changepassword","/api/v1/customer/delete","/api/v1/payment/getbydate","/api/v1/customer/payment","/api/v1/payment/update"

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		try {
			http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authenticationProvider(authenticationProvider()).authorizeRequests()
					.requestMatchers("/api/v1/customer/signin").permitAll()
					.requestMatchers("/api/v1/customer/payment").hasRole(Role.ROLE_CUSTOMER)
					.requestMatchers("/api/v1/admin/*").hasRole(Role.ROLE_ADMIN)
					.requestMatchers("/api/v1/customer/signup").permitAll()
					.anyRequest().authenticated().and().exceptionHandling()
					.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			http.addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
			

			return http.build();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

}