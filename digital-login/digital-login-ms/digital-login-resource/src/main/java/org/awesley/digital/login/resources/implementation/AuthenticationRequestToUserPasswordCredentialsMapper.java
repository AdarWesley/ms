package org.awesley.digital.login.resources.implementation;

import org.awesley.digital.login.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.login.resources.models.JwtAuthenticationRequest;
import org.awesley.digital.login.service.model.UserPasswordCredentials;

public class AuthenticationRequestToUserPasswordCredentialsMapper implements 
	IResourceFromModelMapper<org.awesley.digital.login.service.model.UserPasswordCredentials, JwtAuthenticationRequest> {

	@Override
	public UserPasswordCredentials mapFrom(JwtAuthenticationRequest request) {
		UserPasswordCredentials credentials = new UserPasswordCredentials();
		
		credentials.setUsername(request.getUsername());
		credentials.setPassword(request.getPassword());
		
		return credentials;
	}

}
