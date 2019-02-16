package org.awesley.digital.login.service.interfaces;

import org.awesley.digital.login.service.model.PasswordChangeRequest;

public interface IUserChangePasswordService {

	void changePassword(Long id, PasswordChangeRequest modelPasswordChangeRequest);

}
