package org.awesley.digital.usergroup.service.configuration;

import org.awesley.digital.usergroup.service.implementation.UserGroupCreateService;
import org.awesley.digital.usergroup.service.implementation.UserGroupFindService;
import org.awesley.digital.usergroup.service.implementation.UserGroupGetService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupCreateService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupFindService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupGetService;
import org.awesley.infra.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

	@Bean
	public IUserGroupGetService userGroupGetService(){
		return new UserGroupGetService();
	}
	
	@Bean
	public IUserGroupFindService userGroupFindService() {
		return new UserGroupFindService();
	}
	
	@Bean
	public IUserGroupCreateService userGroupCreateService() {
		return new UserGroupCreateService();
	}
	
	@Bean
	public JwtTokenUtil jwtTokenUtil(
			@Value("${jwt.secret:secret}") String secret, 
    		@Value("${jwt.expiration:#{60*60*24*7}}") Long expiration,
    		@Value("${jwt.algorithm:}") String algorithm,
    		@Value("${jwt.base64EncodedSigningKey:}") String base64EncodedSigningKey,
    		@Value("${jwt.base64EncodedValidationKey:}") String base64EncodedValidationKey) {
		return new JwtTokenUtil(secret, expiration, algorithm, base64EncodedSigningKey, base64EncodedValidationKey);
	}
}
