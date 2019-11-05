package org.awesley.digital.usergroup.persistence.implementation;

import java.util.List;

import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.awesley.digital.usergroup.persistence.implementation.jpa.repositories.UserGroupJpaRepository;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
import org.awesley.infra.query.QueryExpression;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGroupRepositoryImplementation implements IUserGroupRepository {

	@Autowired
	private UserGroupJpaRepository userGroupJpaRepository;
	
	@Override
	public UserGroup getById(Long id) {
		return userGroupJpaRepository.findOne(id);
	}
	
	@Override
	public UserGroup save(UserGroup userGroup) {
		return userGroupJpaRepository.save((JpaUserGroup)userGroup);
	}

	@Override
	public List<? extends UserGroup> find(QueryExpression expression, Integer startIndex, Integer pageSize) {
		return userGroupJpaRepository.findByQueryExpression(expression, startIndex, pageSize);
	}
}
