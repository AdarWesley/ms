package org.awesley.digital.login.service.implementation;

import org.awesley.digital.login.service.interfaces.IUserCreateService;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCreateService implements IUserCreateService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public User createUser(User user) {
		userRepository.save(user);
		return user;
	}

}
