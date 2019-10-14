package org.awesley.digital.usergroup.service.interfaces;

import java.util.List;

import org.awesley.digital.usergroup.service.model.UserGroup;
import org.awesley.infra.query.QueryExpression;

public interface IUserGroupRepository {
	UserGroup getById(Long ID);
	UserGroup save(UserGroup userGroup);
	List<? extends UserGroup> find(QueryExpression expression, Integer startIndex, Integer pageSize);
}
