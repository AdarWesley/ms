package org.awesley.digital.usergroup.service.implementation;

import java.util.List;

import org.awesley.digital.usergroup.service.interfaces.IUserGroupFindService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
import org.awesley.infra.query.QueryExpression;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGroupFindService implements IUserGroupFindService {

	@Autowired
	private IUserGroupRepository userGroupRepository;

	@Override
	public List<? extends UserGroup> findUserGroup(QueryExpression expression, Integer startIndex, Integer pageSize) {
		List<? extends UserGroup> userGroupList = userGroupRepository.find(expression, startIndex, pageSize);
		return userGroupList;
	}
	
}
