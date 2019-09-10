package org.awesley.digital.usergroup.resources.implementation;

import org.awesley.digital.usergroup.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.usergroup.resources.models.UserGroup;

public class UserGroupResourceFromModelMapper implements IResourceFromModelMapper<UserGroup, org.awesley.digital.usergroup.service.model.UserGroup> {

	@Override
	public UserGroup mapFrom(org.awesley.digital.usergroup.service.model.UserGroup modelEntity) {
		UserGroup userGroup = new UserGroup();
		
		userGroup.setId(modelEntity.getID());
		userGroup.setName(modelEntity.getName());
		
		return userGroup;
	}

}
