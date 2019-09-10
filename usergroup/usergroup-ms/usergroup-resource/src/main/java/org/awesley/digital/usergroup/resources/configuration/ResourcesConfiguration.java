package org.awesley.digital.usergroup.resources.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.awesley.digital.usergroup.resources.implementation.UserGroupResourceFromModelMapper;
import org.awesley.digital.usergroup.resources.implementation.UserGroupResourceToModelMapper;
import org.awesley.digital.usergroup.resources.implementation.delegate.UserGroupApiImpl;
import org.awesley.digital.usergroup.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.usergroup.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.usergroup.resources.interfaces.UserGroupApi;
import org.awesley.digital.usergroup.resources.models.UserGroup;

@Configuration
public class ResourcesConfiguration {

	@Bean
	public UserGroupApi userGroupApi(){
		return new UserGroupApiImpl();
	}
	
	@Bean
	public IResourceFromModelMapper<UserGroup, org.awesley.digital.usergroup.service.model.UserGroup> 
	userGroupResourceFromModelMapper(){
		return new UserGroupResourceFromModelMapper();
	}
	
	@Bean
	public IResourceToModelMapper<UserGroup, org.awesley.digital.usergroup.service.model.UserGroup>
	userGroupResourceToModelMapper(){
		return new UserGroupResourceToModelMapper();
	}
}
