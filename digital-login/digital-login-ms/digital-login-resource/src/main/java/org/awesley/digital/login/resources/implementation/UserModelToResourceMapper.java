package org.awesley.digital.login.resources.implementation;

import org.awesley.digital.login.resources.interfaces.IMapper;
import org.awesley.digital.login.resources.models.User;

public class UserModelToResourceMapper implements IMapper<User, org.awesley.digital.login.service.model.User> {

	@Override
	public User mapFrom(org.awesley.digital.login.service.model.User modelEntity) {
		User user = new User();
		
		user.setId(modelEntity.getId());
		user.setUsername(modelEntity.getUsername());
		user.setFirstName(modelEntity.getFirstname());
		user.setLastName(modelEntity.getLastname());
		user.setEmail(modelEntity.getEmail());
		user.setIsEnabled(modelEntity.getEnabled());
		
		return user;
	}
}
