package org.awesley.digital.usergroup.service.implementation;

import org.awesley.digital.usergroup.service.interfaces.IUserGroupCreateService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGroupCreateService implements IUserGroupCreateService {

	@Autowired
	private IUserGroupRepository userGroupRepository;
	
	@Override
	public UserGroup createUserGroup(UserGroup modelUserGroup) {
		return userGroupRepository.save(modelUserGroup);
	}
}
