package org.awesley.digital.login.resources.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.awesley.digital.login.resources.implementation.delegate.AuthenticationApiImpl;
import org.awesley.digital.login.resources.implementation.AuthenticationRequestToUserPasswordCredentialsMapper;
import org.awesley.digital.login.resources.implementation.JwtTokenToJwtAuthenticationResponseMapper;
import org.awesley.digital.login.resources.implementation.delegate.UserApiImpl;
import org.awesley.digital.login.resources.implementation.UserModelToResourceMapper;
import org.awesley.digital.login.resources.interfaces.AuthenticationApi;
import org.awesley.digital.login.resources.interfaces.IMapper;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.resources.models.JwtAuthenticationRequest;
import org.awesley.digital.login.resources.models.JwtAuthenticationResponse;
import org.awesley.digital.login.resources.models.User;

@Configuration
public class ResourcesConfiguration {

	@Bean
	public AuthenticationApi authenticationApi() {
		return new AuthenticationApiImpl();
	}
	
	@Bean
	public UserApi userApi(){
		return new UserApiImpl();
	}
	
	@Bean
	public IMapper<User, org.awesley.digital.login.service.model.User> userModelToResourceMapper(){
		return new UserModelToResourceMapper();
	}
	
	@Bean
	public IMapper<org.awesley.digital.login.service.model.UserPasswordCredentials, JwtAuthenticationRequest>
		authenticationRequestToUserPasswordCredentials(){
		return new AuthenticationRequestToUserPasswordCredentialsMapper();
	}
	
	@Bean
	IMapper<JwtAuthenticationResponse, org.awesley.digital.login.service.model.JwtToken>
		jwtTokenToJwtAuthenticationResponseMapper(){
		return new JwtTokenToJwtAuthenticationResponseMapper();
	}
}
