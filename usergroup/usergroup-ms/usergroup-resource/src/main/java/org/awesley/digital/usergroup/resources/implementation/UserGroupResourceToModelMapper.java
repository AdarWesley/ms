package org.awesley.digital.usergroup.resources.implementation;

import org.awesley.digital.usergroup.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.usergroup.resources.models.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class UserGroupResourceToModelMapper implements IResourceToModelMapper<UserGroup, org.awesley.digital.usergroup.service.model.UserGroup> {

	@Autowired
	private ApplicationContext ctx;
	
	@Override
	public org.awesley.digital.usergroup.service.model.UserGroup mapTo(UserGroup resourceEntity) {
		org.awesley.digital.usergroup.service.model.UserGroup modelEntity = 
				ctx.getBean(org.awesley.digital.usergroup.service.model.UserGroup.class);
		
		modelEntity.setID(resourceEntity.getId());
		modelEntity.setName(resourceEntity.getName());
		
		return modelEntity;
	}

}
