package org.awesley.digital.login.resources.implementation.delegate;

import javax.ws.rs.core.Response;

import org.awesley.digital.login.resources.interfaces.AuthenticationApi;
import org.awesley.digital.login.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.login.resources.models.JwtAuthenticationRequest;
import org.awesley.digital.login.resources.models.JwtAuthenticationResponse;
import org.awesley.digital.login.resources.models.JwtPublicKeyResponse;
import org.awesley.digital.login.service.interfaces.IAuthenticationService;
import org.awesley.digital.login.service.interfaces.IPublicKeyService;
import org.awesley.infra.security.model.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationApiImpl implements AuthenticationApi {

	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	private IResourceFromModelMapper<org.awesley.digital.login.service.model.UserPasswordCredentials, JwtAuthenticationRequest> requestMapper;
	
	@Autowired
	private IResourceFromModelMapper<JwtAuthenticationResponse, JwtToken> responseMapper;
	
	@Autowired
	private IResourceFromModelMapper<JwtPublicKeyResponse, org.awesley.digital.login.service.model.JwtPublicKey> publicKeyResponseMapper;

	@Autowired
	private IPublicKeyService publicKeyService;
	
	@Override
	public Response authenticatePost(JwtAuthenticationRequest jwtAuthenticationRequest) {
		org.awesley.digital.login.service.model.UserPasswordCredentials credentials = 
				requestMapper.mapFrom(jwtAuthenticationRequest);
		
		JwtToken jwtToken = authenticationService.authenticate(credentials);
		
		return Response.ok(responseMapper.mapFrom(jwtToken)).build();
	}

	@Override
	public Response getPublicKey() {
		org.awesley.digital.login.service.model.JwtPublicKey jwtPublicKey =
				publicKeyService.getPublicKey();
		return Response.ok(publicKeyResponseMapper.mapFrom(jwtPublicKey)).build();
	}
}
