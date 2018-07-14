package org.awesley.digital.login.service.interfaces;

import org.awesley.digital.login.service.model.JwtToken;
import org.awesley.digital.login.service.model.UserPasswordCredentials;

public interface IAuthenticationService {

	JwtToken authenticate(UserPasswordCredentials credentials);

}
