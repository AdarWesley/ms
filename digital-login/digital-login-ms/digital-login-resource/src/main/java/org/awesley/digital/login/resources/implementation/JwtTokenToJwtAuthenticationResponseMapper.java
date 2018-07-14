package org.awesley.digital.login.resources.implementation;

import org.awesley.digital.login.resources.interfaces.IMapper;
import org.awesley.digital.login.resources.models.JwtAuthenticationResponse;
import org.awesley.digital.login.service.model.JwtToken;

public class JwtTokenToJwtAuthenticationResponseMapper implements IMapper<JwtAuthenticationResponse, JwtToken> {

	@Override
	public JwtAuthenticationResponse mapFrom(JwtToken modelEntity) {
		JwtAuthenticationResponse response = new JwtAuthenticationResponse();
		response.setToken(modelEntity.getToken());
		return response;
	}

}
