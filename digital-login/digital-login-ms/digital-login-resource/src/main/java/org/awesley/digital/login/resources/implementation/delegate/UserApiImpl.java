package org.awesley.digital.login.resources.implementation.delegate;

import javax.ws.rs.core.Response;

import org.awesley.digital.login.resources.interfaces.IMapper;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.resources.models.User;
import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

public class UserApiImpl implements UserApi {

	@Autowired
	private IUserGetService userGetService;
	
	@Autowired
	private IMapper<User, org.awesley.digital.login.service.model.User> userMapper;
	
    @PreAuthorize("hasRole('ADMIN')")
	public Response getUser(Long id) {
		org.awesley.digital.login.service.model.User modelUser = userGetService.GetUser(id);
		User user = userMapper.mapFrom(modelUser);
		return Response.ok(user).build();
	}

	@Override
	public Response getUserByName(String username) {
		org.awesley.digital.login.service.model.User modelUser = userGetService.GetUser(1L);
		User user = userMapper.mapFrom(modelUser);
		return Response.ok(user).build();
	}
}
