package org.awesley.digital.usergroup.resources.implementation.delegate;

import org.awesley.digital.usergroup.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.usergroup.resources.interfaces.IResourceToModelMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.awesley.digital.usergroup.resources.interfaces.UserGroupApi;
import org.awesley.digital.usergroup.resources.models.UserGroup;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupCreateService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupGetService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGroupApiImpl implements UserGroupApi {

	@Autowired
	private IUserGroupGetService userGroupGetService;
	
	@Autowired
	private IUserGroupCreateService userGroupCreateService;
	
	@Autowired
	private IResourceFromModelMapper<UserGroup, org.awesley.digital.usergroup.service.model.UserGroup> userGroupResourceFromModelMapper;
	
	@Autowired
	private IResourceToModelMapper<UserGroup, org.awesley.digital.usergroup.service.model.UserGroup> userGroupResourceToModelMapper;

	public Response getUserGroup(Long id) {
		org.awesley.digital.usergroup.service.model.UserGroup modelUserGroup = userGroupGetService.GetUserGroup(id);
		UserGroup userGroup = userGroupResourceFromModelMapper.mapFrom(modelUserGroup);
		return Response.ok(userGroup).build();
	}

	@Override
	public Response createUserGroup(UserGroup userGroup) {
		org.awesley.digital.usergroup.service.model.UserGroup modelUserGroup = userGroupResourceToModelMapper.mapTo(userGroup);
		modelUserGroup = userGroupCreateService.createUserGroup(modelUserGroup);
		userGroup = userGroupResourceFromModelMapper.mapFrom(modelUserGroup);
		
		return Response.status(Status.CREATED).entity(userGroup).build();
	}

	@Override
	public Response findUserGroup(String filter, Integer startIndex, Integer pageSize) {
		return null;
	}

	@Override
	public Response updateUserGroup(Long id, UserGroup userGroup) {
		// TODO Auto-generated method stub
		return null;
	}
}
