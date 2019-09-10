package org.awesley.digital.usergroup.service.interfaces;

import org.awesley.digital.usergroup.service.model.UserGroup;

public interface IUserGroupRepository {
	UserGroup getById(Long ID);
	UserGroup save(UserGroup userGroup);
}
