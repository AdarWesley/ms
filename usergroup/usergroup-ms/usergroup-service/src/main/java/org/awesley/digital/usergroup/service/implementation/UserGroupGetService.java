package org.awesley.digital.usergroup.service.implementation;

import org.awesley.digital.usergroup.service.interfaces.IUserGroupGetService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGroupGetService implements IUserGroupGetService {

	@Autowired
	private IUserGroupRepository userGroupRepository;
	
	public UserGroup GetUserGroup(Long id) {
		return userGroupRepository.getById(id);
	}
}
