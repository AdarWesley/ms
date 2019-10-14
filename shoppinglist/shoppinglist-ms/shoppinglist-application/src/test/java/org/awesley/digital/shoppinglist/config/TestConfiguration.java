package org.awesley.digital.shoppinglist.config;

import java.util.ArrayList;
import java.util.List;

import org.awesley.digital.gateway.config.GatewayConfiguration;
import org.awesley.digital.shoppinglist.application.CxfAppConfiguration;
import org.awesley.digital.shoppinglist.application.JWTAuthorizationRequestInterceptor;
import org.awesley.digital.shoppinglist.application.security.config.WebSecurityConfig;
import org.awesley.digital.shoppinglist.config.TestConfiguration.ShoppingListTest;
import org.awesley.digital.shoppinglist.resources.configuration.ResourcesConfiguration;
import org.awesley.digital.shoppinglist.service.configuration.ServicesConfiguration;
import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.applicativecontext.ApplicativeContextConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class, 
		DataSourceTransactionManagerAutoConfiguration.class, 
		HibernateJpaAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class,
		JtaAutoConfiguration.class,
		TransactionAutoConfiguration.class,
		WebMvcAutoConfiguration.class,
		ErrorMvcAutoConfiguration.class,
		SpringDataWebAutoConfiguration.class
})
@ComponentScan()
//		basePackages = {"org.awesley.digital.login"}, 
//		basePackageClasses = {
//			CxfAppConfiguration.class,
//			AppLayersConfiguration.class
//		}
//	)
@Import(value = {
		CxfAppConfiguration.class,
		WebSecurityConfig.class,
		ResourcesConfiguration.class, 
		ServicesConfiguration.class,
		GatewayConfiguration.class,
		// PersistenceConfiguration.class,
		ApplicativeContextConfiguration.class
})
public class TestConfiguration {

	@Bean
	public JWTAuthorizationRequestInterceptor jwtAuthorizationRequestInterceptor() {
		return new JWTAuthorizationRequestInterceptor();
	}
	
	@Bean
	@Scope("prototype")
	public ShoppingList shoppingList() {
		return new ShoppingListTest(); 
	}
	
	@Bean
	@Scope("prototype")
	public GroupRef groupRef() {
		return new GroupRefTest();
	}
	
	public class ShoppingListTest implements ShoppingList {

		long id;
		String name;
		List<? extends GroupRef> groups = new ArrayList<GroupRef>();
		
		@Override
		public Long getID() {
			return id;
		}

		@Override
		public void setID(Long id) {
			this.id = (id != null)? id : 0L;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public List<? extends GroupRef> getUserGroups() {
			return groups;
		}

		@Override
		public void setUserGroups(List<? extends GroupRef> groups) {
			this.groups = groups;
		}

	}

	public class GroupRefTest implements GroupRef {
		Long id;
		String name;
		
		@Override
		public Long getId() {
			return id;
		}
		
		@Override
		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public void setName(String name) {
			this.name = name;
		}
	}
}
