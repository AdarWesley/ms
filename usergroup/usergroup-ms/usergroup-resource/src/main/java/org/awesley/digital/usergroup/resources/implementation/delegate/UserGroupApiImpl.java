package org.awesley.digital.usergroup.resources.implementation.delegate;

import org.awesley.digital.usergroup.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.usergroup.resources.interfaces.IResourceToModelMapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.awesley.digital.usergroup.resources.interfaces.UserGroupApi;
import org.awesley.digital.usergroup.resources.models.UserGroup;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupCreateService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupFindService;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupGetService;
import org.awesley.infra.query.QueryExpression;
import org.awesley.infra.query.parser.QueryExpressionParser;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGroupApiImpl implements UserGroupApi {

	@Autowired
	private IUserGroupGetService userGroupGetService;
	
	@Autowired
	private IUserGroupFindService userGroupFindService;
	
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
		try {
			filter = URLDecoder.decode(filter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		QueryExpressionParser expressionParser = new QueryExpressionParser(filter);
		expressionParser.Parse();
		QueryExpression expression = expressionParser.getQueryExpression();
		List<? extends org.awesley.digital.usergroup.service.model.UserGroup> userGroupList = 
				userGroupFindService.findUserGroup(expression, startIndex, pageSize);
		return Response.ok(userGroupList.get(0)).build();
	}

	@Override
	public Response updateUserGroup(Long id, UserGroup userGroup) {
		// TODO Auto-generated method stub
		return null;
	}
}
