package org.awesley.digital.login.resources.implementation.delegate;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.awesley.digital.login.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.login.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.resources.models.PasswordChangeRequest;
import org.awesley.digital.login.resources.models.User;
import org.awesley.digital.login.service.interfaces.IUserChangePasswordService;
import org.awesley.digital.login.service.interfaces.IUserCreateService;
import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

public class UserApiImpl implements UserApi {

	@Autowired
	private IUserGetService userGetService;
	
	@Autowired
	private IUserCreateService userCreateService;
	
	@Autowired
	private IResourceFromModelMapper<User, org.awesley.digital.login.service.model.User> userResourceFromModelMapper;

	@Autowired
	private IResourceToModelMapper<User, org.awesley.digital.login.service.model.User> userResourceToModelMapper;
	
	@Autowired
	private IUserChangePasswordService userChangePasswordService;
	
	@Autowired
	private IResourceToModelMapper<PasswordChangeRequest, org.awesley.digital.login.service.model.PasswordChangeRequest> passwordChangeRequestMapper;
	
    @PreAuthorize("hasRole('ADMIN')")
	@Override
	public Response getUser(Long id) {
		org.awesley.digital.login.service.model.User modelUser = userGetService.GetUser(id);
		User user = userResourceFromModelMapper.mapFrom(modelUser);
		return Response.ok(user).build();
	}

    @PreAuthorize("hasRole('ADMIN')")
	@Override
	public Response getUserByName(String username) {
		org.awesley.digital.login.service.model.User modelUser = userGetService.GetUser(1L);
		User user = userResourceFromModelMapper.mapFrom(modelUser);
		return Response.ok(user).build();
	}

    @PreAuthorize("isAuthenticated()")
	@Override
	public Response changeUserPassword(Long id, PasswordChangeRequest passwordChangeRequest) {
		org.awesley.digital.login.service.model.PasswordChangeRequest 
			modelPasswordChangeRequest = passwordChangeRequestMapper.mapTo(passwordChangeRequest);
		
		userChangePasswordService.changePassword(id, modelPasswordChangeRequest);
		
		return Response.ok().build();
	}

    @PreAuthorize("hasRole('ADMIN')")
	@Override
	public Response createUser(User user) {
    	org.awesley.digital.login.service.model.User modelUser = 
    			userResourceToModelMapper.mapTo(user);
    	
    	modelUser = userCreateService.createUser(modelUser);
    	
    	user = userResourceFromModelMapper.mapFrom(modelUser);
    	
		return Response.status(Status.CREATED).entity(user).build();
	}

    @PreAuthorize("hasRole('ADMIN')")
	@Override
	public Response updateUser(Long id) {
		return Response.ok().build();
	}
}
