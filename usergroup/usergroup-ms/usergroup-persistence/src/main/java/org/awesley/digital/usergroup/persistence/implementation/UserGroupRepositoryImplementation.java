package org.awesley.digital.usergroup.persistence.implementation;

import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.awesley.digital.usergroup.persistence.implementation.jpa.repositories.UserGroupJpaRepository;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
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
}
