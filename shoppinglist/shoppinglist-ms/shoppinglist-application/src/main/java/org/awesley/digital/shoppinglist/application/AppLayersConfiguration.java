package org.awesley.digital.shoppinglist.application;

import org.awesley.digital.gateway.config.GatewayConfiguration;
import org.awesley.digital.shoppinglist.application.security.config.WebSecurityConfig;
import org.awesley.digital.shoppinglist.persistence.configuration.PersistenceConfiguration;
import org.awesley.digital.shoppinglist.resources.configuration.ResourcesConfiguration;
import org.awesley.digital.shoppinglist.service.configuration.ServicesConfiguration;
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
		GatewayConfiguration.class,
		PersistenceConfiguration.class,
		ApplicativeContextConfiguration.class
})
public class AppLayersConfiguration {

}
