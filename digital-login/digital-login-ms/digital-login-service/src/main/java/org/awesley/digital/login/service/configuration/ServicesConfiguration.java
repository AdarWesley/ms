package org.awesley.digital.login.service.configuration;

import org.awesley.digital.login.service.implementation.AuthenticationService;
import org.awesley.digital.login.service.implementation.JwtTokenUtil;
import org.awesley.digital.login.service.implementation.UserGetService;
import org.awesley.digital.login.service.interfaces.IAuthenticationService;
import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class ServicesConfiguration {

	@Bean
	public IUserGetService userGetService(){
		return new UserGetService();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserGetService();
	}
	
	@Bean
	public JwtTokenUtil jwtTokenUtil() {
		return new JwtTokenUtil();
	}
	
	@Bean
	public IAuthenticationService authenticationService() {
		return new AuthenticationService();
	}
}
