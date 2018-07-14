package org.awesley.digital.login.persistence.implementation;

import org.awesley.digital.login.persistence.implementation.jpa.repositories.UserJpaRepository;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryImplementation implements IUserRepository {

	@Autowired
	private UserJpaRepository userJpaRepository;
	
	@Override
	public User getById(Long id) {
		return userJpaRepository.findOne(id);
	}

	@Override
	public User getByUsername(String username) {
		return userJpaRepository.findByUsername(username);
	}
}
