package org.awesley.digital.usergroup.service.interfaces;

import java.util.List;

import org.awesley.digital.usergroup.service.model.UserGroup;
import org.awesley.infra.query.QueryExpression;

public interface IUserGroupFindService {

	List<? extends UserGroup> findUserGroup(QueryExpression expression, Integer startIndex, Integer pageSize);

}
