package org.awesley.digital.login.service.implementation;

import javax.inject.Provider;

import org.awesley.digital.login.service.interfaces.IPublicKeyService;
import org.awesley.digital.login.service.model.JwtPublicKey;
import org.awesley.infra.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class PublicKeyService implements IPublicKeyService {

    @Autowired
    private Provider<JwtTokenUtil> jwtTokenUtil;

    @Override
	public JwtPublicKey getPublicKey() {
    	String publicKey = jwtTokenUtil.get().getPublicKeyEncodedString();
    	JwtPublicKey jwtPublicKey = new JwtPublicKey();
    	jwtPublicKey.setPublicKey(publicKey);
    	
		return jwtPublicKey;
	}

}
