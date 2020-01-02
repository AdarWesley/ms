package org.awesley.digital.gateway.implementation;

import org.awesley.digital.shoppinglist.service.interfaces.gateway.IGroupGatewayService;
import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.awesley.digital.usergroup.resources.interfaces.UserGroupApi;
import org.awesley.digital.usergroup.resources.models.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class GroupGatewayService implements IGroupGatewayService {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private UserGroupApi userGroupApi;
	
	@Override
	public GroupRef getGroup(String groupName) {
		UserGroup group = userGroupApi.findUserGroup("name=" + groupName, 0, 1);
		GroupRef groupRef = ctx.getBean(GroupRef.class);
		groupRef.setId(group.getId());
		groupRef.setGroupName(group.getName());
		return groupRef;
	}

}
