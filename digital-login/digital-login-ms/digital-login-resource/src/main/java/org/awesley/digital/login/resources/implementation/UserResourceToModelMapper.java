package org.awesley.digital.login.resources.implementation;

import org.awesley.digital.login.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.login.resources.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class UserResourceToModelMapper
		implements IResourceToModelMapper<User, org.awesley.digital.login.service.model.User> {

	@Autowired
	private ApplicationContext ctx;
	
	@Override
	public org.awesley.digital.login.service.model.User mapTo(User resourceEntity) {
		org.awesley.digital.login.service.model.User modelUser = 
				ctx.getBean(org.awesley.digital.login.service.model.User.class);
		
		modelUser.setId(resourceEntity.getId());
		modelUser.setUsername(resourceEntity.getUsername());
		modelUser.setFirstname(resourceEntity.getFirstName());
		modelUser.setLastname(resourceEntity.getLastName());
		modelUser.setEmail(resourceEntity.getEmail());
		modelUser.setEnabled(resourceEntity.isIsEnabled());
		
		return modelUser;
	}

}
