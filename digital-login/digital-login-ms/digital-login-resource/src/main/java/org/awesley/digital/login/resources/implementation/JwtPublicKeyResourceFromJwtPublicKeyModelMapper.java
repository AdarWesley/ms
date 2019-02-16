package org.awesley.digital.login.resources.implementation;

import org.awesley.digital.login.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.login.resources.models.JwtPublicKeyResponse;
import org.awesley.digital.login.service.model.JwtPublicKey;

public class JwtPublicKeyResourceFromJwtPublicKeyModelMapper
		implements IResourceFromModelMapper<JwtPublicKeyResponse, org.awesley.digital.login.service.model.JwtPublicKey> {

	@Override
	public JwtPublicKeyResponse mapFrom(JwtPublicKey jwtPublicKey) {
		JwtPublicKeyResponse jwtPublicKeyResponse = new JwtPublicKeyResponse();
		jwtPublicKeyResponse.setPublickey(jwtPublicKey.getPublicKey());
		return jwtPublicKeyResponse;
	}

}
