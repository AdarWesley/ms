package org.awesley.digital.login.resources.implementation.delegate;

import org.awesley.digital.login.resources.interfaces.AuthenticationApi;
import org.awesley.digital.login.resources.interfaces.IMapper;
import org.awesley.digital.login.resources.models.JwtAuthenticationRequest;
import org.awesley.digital.login.resources.models.JwtAuthenticationResponse;
import org.awesley.digital.login.service.interfaces.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationApiImpl implements AuthenticationApi {

	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	private IMapper<org.awesley.digital.login.service.model.UserPasswordCredentials, JwtAuthenticationRequest> requestMapper;
	
	@Autowired
	private IMapper<JwtAuthenticationResponse, org.awesley.digital.login.service.model.JwtToken> responseMapper;
	
	@Override
	public JwtAuthenticationResponse authenticatePost(JwtAuthenticationRequest jwtAuthenticationRequest) {
		org.awesley.digital.login.service.model.UserPasswordCredentials credentials = 
				requestMapper.mapFrom(jwtAuthenticationRequest);
		
		org.awesley.digital.login.service.model.JwtToken jwtToken = authenticationService.authenticate(credentials);
		
		return responseMapper.mapFrom(jwtToken);
	}
}
