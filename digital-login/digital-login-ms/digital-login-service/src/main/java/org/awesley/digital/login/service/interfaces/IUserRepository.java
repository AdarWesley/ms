package org.awesley.digital.login.service.interfaces;

import org.awesley.digital.login.service.model.User;

public interface IUserRepository {
	User getById(Long id);
	User getByUsername(String username);
	
	User save(User user);
}
