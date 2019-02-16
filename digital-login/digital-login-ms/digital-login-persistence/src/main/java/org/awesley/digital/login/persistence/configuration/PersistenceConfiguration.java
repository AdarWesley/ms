package org.awesley.digital.login.persistence.configuration;

import org.awesley.digital.login.persistence.implementation.UserRepositoryImplementation;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaAuthority;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaUser;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.User;
import org.awesley.infra.security.model.Authority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableJpaRepositories(basePackages = { "org.awesley.digital.login.persistence.implementation.jpa.repositories" })
@EntityScan(basePackages = { "org.awesley.digital.login.persistence.implementation.jpa.entities" })
public class PersistenceConfiguration {

	@Bean
	public IUserRepository userRepository() {
		return new UserRepositoryImplementation();
	}
	
	@Bean
	@Scope("prototype")
	public User user() {
		return new JpaUser();
	}
	
	@Bean
	@Scope("prototype")
	public Authority authority() {
		return new JpaAuthority();
	}
}
