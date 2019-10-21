package org.awesley.digital.usergroup.persistence.configuration;

import org.awesley.digital.usergroup.persistence.implementation.UserGroupRepositoryImplementation;
import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
import org.awesley.infra.query.jpa.QueryExpressionToJpaVisitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration()
@ComponentScan(basePackageClasses = { QueryExpressionToJpaVisitor.class })
@EnableJpaRepositories(basePackages = { "org.awesley.digital.usergroup.persistence.implementation.jpa.repositories" })
@EntityScan(basePackages = { "org.awesley.digital.usergroup.persistence.implementation.jpa.entities" })
public class PersistenceConfiguration {

	@Bean
	public IUserGroupRepository userGroupRepository() {
		return new UserGroupRepositoryImplementation();
	}
	
	@Bean
	@Scope("prototype")
	public UserGroup userGroup() {
		return new JpaUserGroup();
	}
}
