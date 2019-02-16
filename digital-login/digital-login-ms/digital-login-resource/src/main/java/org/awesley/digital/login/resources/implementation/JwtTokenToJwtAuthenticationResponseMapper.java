package org.awesley.digital.login.resources.implementation;

import org.awesley.digital.login.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.login.resources.models.JwtAuthenticationResponse;
import org.awesley.infra.security.model.JwtToken;

public class JwtTokenToJwtAuthenticationResponseMapper implements IResourceFromModelMapper<JwtAuthenticationResponse, JwtToken> {

	@Override
	public JwtAuthenticationResponse mapFrom(JwtToken modelEntity) {
		JwtAuthenticationResponse response = new JwtAuthenticationResponse();
		response.setToken(modelEntity.getToken());
		return response;
	}

}
