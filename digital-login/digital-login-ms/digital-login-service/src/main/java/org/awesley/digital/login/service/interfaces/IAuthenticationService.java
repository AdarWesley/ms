package org.awesley.digital.login.service.interfaces;

import org.awesley.digital.login.service.model.UserPasswordCredentials;
import org.awesley.infra.security.model.JwtToken;

public interface IAuthenticationService {

	JwtToken authenticate(UserPasswordCredentials credentials);

}
