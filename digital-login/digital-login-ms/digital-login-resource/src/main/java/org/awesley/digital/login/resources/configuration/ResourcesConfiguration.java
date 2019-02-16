package org.awesley.digital.login.resources.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.awesley.digital.login.resources.implementation.delegate.AuthenticationApiImpl;
import org.awesley.digital.login.resources.implementation.AuthenticationRequestToUserPasswordCredentialsMapper;
import org.awesley.digital.login.resources.implementation.JwtPublicKeyResourceFromJwtPublicKeyModelMapper;
import org.awesley.digital.login.resources.implementation.JwtTokenToJwtAuthenticationResponseMapper;
import org.awesley.digital.login.resources.implementation.PasswordChangeRequestResourceToModelMapper;
import org.awesley.digital.login.resources.implementation.delegate.UserApiImpl;
import org.awesley.digital.login.resources.implementation.UserResourceFromModelMapper;
import org.awesley.digital.login.resources.implementation.UserResourceToModelMapper;
import org.awesley.digital.login.resources.interfaces.AuthenticationApi;
import org.awesley.digital.login.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.login.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.resources.models.JwtAuthenticationRequest;
import org.awesley.digital.login.resources.models.JwtAuthenticationResponse;
import org.awesley.digital.login.resources.models.JwtPublicKeyResponse;
import org.awesley.digital.login.resources.models.PasswordChangeRequest;
import org.awesley.digital.login.resources.models.User;
import org.awesley.digital.login.service.model.JwtPublicKey;
import org.awesley.infra.security.model.JwtToken;

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
	public IResourceFromModelMapper<User, org.awesley.digital.login.service.model.User> userResourceFromModelMapper(){
		return new UserResourceFromModelMapper();
	}
	
	@Bean
	public IResourceToModelMapper<User, org.awesley.digital.login.service.model.User> userResourceToModelMapper(){
		return new UserResourceToModelMapper();
	}
	
	@Bean
	public IResourceFromModelMapper<org.awesley.digital.login.service.model.UserPasswordCredentials, JwtAuthenticationRequest>
		authenticationRequestToUserPasswordCredentials(){
		return new AuthenticationRequestToUserPasswordCredentialsMapper();
	}
	
	@Bean
	IResourceFromModelMapper<JwtAuthenticationResponse, JwtToken>
		jwtTokenToJwtAuthenticationResponseMapper(){
		return new JwtTokenToJwtAuthenticationResponseMapper();
	}
	
	@Bean
	IResourceToModelMapper<PasswordChangeRequest, org.awesley.digital.login.service.model.PasswordChangeRequest>
		passwordChangeRequestResourceToModelMapper(){
		return new PasswordChangeRequestResourceToModelMapper();
	}
	
	@Bean
	IResourceFromModelMapper<JwtPublicKeyResponse, org.awesley.digital.login.service.model.JwtPublicKey>
		jwtPublicKeyResourceFromJwtPublicKeyModelMapper(){
		return new JwtPublicKeyResourceFromJwtPublicKeyModelMapper();
	}
}
