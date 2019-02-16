package org.awesley.digital.login.config;

import java.util.Date;
import java.util.List;

import org.apache.cxf.spring.boot.autoconfigure.CxfAutoConfiguration;
import org.awesley.digital.login.AuthenticationTestHelper;
import org.awesley.digital.login.UsersTestHelper;
import org.awesley.digital.login.application.CxfAppConfiguration;
import org.awesley.digital.login.application.security.config.WebSecurityConfig;
import org.awesley.digital.login.resources.configuration.ResourcesConfiguration;
import org.awesley.digital.login.service.configuration.ServicesConfiguration;
import org.awesley.digital.login.service.model.User;
import org.awesley.infra.applicativecontext.ApplicativeContextConfiguration;
import org.awesley.infra.security.model.Authority;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.support.AbstractTestExecutionListener;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class, 
		DataSourceTransactionManagerAutoConfiguration.class, 
		HibernateJpaAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class,
		JtaAutoConfiguration.class,
		TransactionAutoConfiguration.class,
		WebMvcAutoConfiguration.class,
		SpringDataWebAutoConfiguration.class
})
//@ComponentScan(
//		excludeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { PersistenceConfiguration.class }) },
//		basePackages = {"org.awesley.digital.login"}
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
		// PersistenceConfiguration.class,
		ApplicativeContextConfiguration.class
})
@OverrideAutoConfiguration(enabled = false)
@ImportAutoConfiguration(value = {
		CxfAutoConfiguration.class
})
public class TestConfiguration {
	
	@Bean("org.awesley.digital.login.AuthenticationTestHelper")
	@Scope("prototype")
	public AuthenticationTestHelper authenticationTestHelper() {
		return new AuthenticationTestHelper();
	}
	
	@Bean("org.awesley.digital.login.UsersTestHelper")
	@Scope("prototype")
	public UsersTestHelper usersTestHelper() {
		return new UsersTestHelper();
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public User user() {
		return new User() {

			private Long id;
			private String username;
			private String password;
			private String firstName;
			private String lastName;
			private String email;
			private Boolean isEnabled;
			private Date lastPasswordResetDate;
			private List<? extends Authority> authorities;

			@Override
			public Long getId() {
				return this.id;
			}

			@Override
			public void setId(Long id) {
				this.id = id;
			}

			@Override
			public String getUsername() {
				return this.username;
			}

			@Override
			public void setUsername(String username) {
				this.username = username;
			}

			@Override
			public String getPassword() {
				return this.password;
			}

			@Override
			public void setPassword(String password) {
				this.password = password;
			}

			@Override
			public String getFirstname() {
				return this.firstName;
			}

			@Override
			public void setFirstname(String firstname) {
				this.firstName = firstname;
			}

			@Override
			public String getLastname() {
				return this.lastName;
			}

			@Override
			public void setLastname(String lastname) {
				this.lastName = lastname;
			}

			@Override
			public String getEmail() {
				return this.email;
			}

			@Override
			public void setEmail(String email) {
				this.email = email;
			}

			@Override
			public Boolean getEnabled() {
				return this.isEnabled;
			}

			@Override
			public void setEnabled(Boolean enabled) {
				this.isEnabled = enabled;
			}

			@Override
			public List<? extends Authority> getAuthorities() {
				return this.authorities;
			}

			@Override
			public void setAuthorities(List<? extends Authority> authorities) {
				this.authorities = authorities;
			}

			@Override
			public Date getLastPasswordResetDate() {
				return this.lastPasswordResetDate;
			}

			@Override
			public void setLastPasswordResetDate(Date lastPasswordResetDate) {
				this.lastPasswordResetDate = lastPasswordResetDate;
			}
			
		};
	}
}
