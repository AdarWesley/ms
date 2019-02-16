package org.awesley.digital.login.service.configuration;

import org.awesley.digital.login.service.implementation.AuthenticationService;
import org.awesley.digital.login.service.implementation.PublicKeyService;
import org.awesley.digital.login.service.implementation.UserChangePasswordService;
import org.awesley.digital.login.service.implementation.UserCreateService;
import org.awesley.digital.login.service.implementation.UserGetService;
import org.awesley.digital.login.service.interfaces.IAuthenticationService;
import org.awesley.digital.login.service.interfaces.IPublicKeyService;
import org.awesley.digital.login.service.interfaces.IUserChangePasswordService;
import org.awesley.digital.login.service.interfaces.IUserCreateService;
import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.awesley.infra.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServicesConfiguration {

	@Bean
	public IUserGetService userGetService(){
		return new UserGetService();
	}
	
	@Bean
	public IUserCreateService userCreateService() {
		return new UserCreateService();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserGetService();
	}
	
	@Bean
	public IUserChangePasswordService userPasswordChangeService() {
		return new UserChangePasswordService();
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public JwtTokenUtil jwtTokenUtil(
			@Value("${jwt.secret:secret}") String secret, 
    		@Value("${jwt.expiration:#{60*60*24*7}}") Long expiration,
    		@Value("${jwt.algorithm:}") String algorithm,
    		@Value("${jwt.base64EncodedSigningKey:}") String base64EncodedSigningKey,
    		@Value("${jwt.base64EncodedValidationKey:}") String base64EncodedValidationKey) {
		return new JwtTokenUtil(secret, expiration, algorithm, base64EncodedSigningKey, base64EncodedValidationKey);
	}
	
	@Bean
	public IAuthenticationService authenticationService() {
		return new AuthenticationService();
	}
	
	@Bean
	public IPublicKeyService publicKeyService() {
		return new PublicKeyService();
	}
}
