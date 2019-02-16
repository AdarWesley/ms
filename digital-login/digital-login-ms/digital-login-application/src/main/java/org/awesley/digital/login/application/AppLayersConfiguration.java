package org.awesley.digital.login.application;

import org.awesley.digital.login.application.security.config.WebSecurityConfig;
import org.awesley.digital.login.persistence.configuration.PersistenceConfiguration;
import org.awesley.digital.login.resources.configuration.ResourcesConfiguration;
import org.awesley.digital.login.service.configuration.ServicesConfiguration;
import org.awesley.infra.applicativecontext.ApplicativeContextConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan()
@Import(value = {
		WebSecurityConfig.class,
		ResourcesConfiguration.class, 
		ServicesConfiguration.class,
		PersistenceConfiguration.class,
		ApplicativeContextConfiguration.class
})
public class AppLayersConfiguration {

}
