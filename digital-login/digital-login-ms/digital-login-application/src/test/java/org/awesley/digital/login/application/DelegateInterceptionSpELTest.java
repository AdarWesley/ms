package org.awesley.digital.login.application;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.apache.tools.ant.filters.StringInputStream;
import org.awesley.digital.login.config.TestConfiguration;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaUser;
import org.awesley.digital.login.resources.implementation.delegate.UserApiImpl;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.User;
import org.awesley.infra.applicativecontext.ApplicativeContextEntities;
import org.awesley.infra.applicativecontext.ContextEntityInfo;
import org.awesley.infra.applicativecontext.DefaultErrorMessageFormatter;
import org.awesley.infra.applicativecontext.IErrorMessageFormatter;
import org.awesley.infra.applicativecontext.SpELErrorMessageFormatter;
import org.awesley.infra.errors.ApplicationException;
import org.awesley.infra.errors.ErrorInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { 
	TestConfiguration.class, 
	// CxfServiceSpringBootApplication.class, 
	DelegateInterceptionSpELTest.DelegateInterceptionSpELConfiguration.class 
}) //, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DelegateInterceptionSpELTest {

	@MockBean
	private IUserRepository userRepository;
	
	@Inject
	private UserApi userApiDelegate;
	
	private static User mockUser = createUser();	
	
//	@Inject
//	private ResourceBundle errorFormatResources;

	@Test
	@WithMockUser(roles = {"ADMIN"})	
	public void simpleExceptionIsConvertedToApplicationExceptionWithDefaultFormatFromSpEL() {
		initializeMockObject("TestEntity", new NullPointerException("Object was null"));
		
		boolean didThrow = false;
		ErrorInfo errorInfo = null;
		try {
			userApiDelegate.getUserByName("TestUser");
		}
		catch (Exception ex) {
			didThrow = true;
			assertTrue(ApplicationException.class.isAssignableFrom(ex.getClass()));
			errorInfo = ((ApplicationException)ex).getErrorInfo();
		}
		assertTrue(didThrow);
		assertNotNull(errorInfo);
		assertEquals("While executing UserApiImpl.getUserByName on TestEntity[1] received error: Object was null", errorInfo.getMessage());
	}

	@Test
	@WithMockUser(roles = {"ADMIN"})	
	public void simpleExceptionIsConvertedToApplicationExceptionWithSpecificFormatFromSpEL() {
		initializeMockObject("TestEntity", new NullPointerException("Object was null"));
		
		boolean didThrow = false;
		ErrorInfo errorInfo = null;
		try {
			userApiDelegate.getUser(1L);
		}
		catch (Exception ex) {
			didThrow = true;
			assertTrue(ApplicationException.class.isAssignableFrom(ex.getClass()));
			errorInfo = ((ApplicationException)ex).getErrorInfo();
		}
		assertTrue(didThrow);
		assertNotNull(errorInfo);
		assertEquals("Completely different error format on: User[1]", errorInfo.getMessage());
	}

	private void initializeMockObject(String entityType, Throwable exceptionToThrow) {
		IUserGetService serviceMock = Mockito.mock(IUserGetService.class);
		Mockito
			.when(serviceMock.GetUser(Mockito.anyLong()))
			.thenAnswer(invocation -> {
				ApplicativeContextEntities.addContextEntities("TestContext", new ContextEntityInfo(entityType, 1L));
				throw exceptionToThrow;
			});
		
		ReflectionTestUtils.setField(userApiDelegate, UserApiImpl.class, "userGetService", serviceMock, IUserGetService.class);
	}

	private static User createUser() {
		User user = new JpaUser();
		user.setFirstname("Test");
		user.setLastname("User");
		user.setId(1L);
		return user;
	}
	
	@Configuration
	static class DelegateInterceptionSpELConfiguration {
		
		@Bean
		@Primary
		public ResourceBundle errorFormatResources() {
			String testResource =
					"DefaultErrorFormat='While executing ' + @methodName.create(#ctx) + ' on ' + @contextEntities.create(#ctx) + ' received error: ' + @innerError.create(#ctx)\n" +
					"UserApiImpl.getUser='Completely different error format on: ' + @userEntMessageFragment.create(#args[0])";
			StringReader sr = new StringReader(testResource);
			ResourceBundle rb = null;
			try {
				rb = new PropertyResourceBundle(sr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				sr.close();
			}
			return rb;
		}
		
		@Bean
		@Primary
		public IErrorMessageFormatter errorMessageFormatter() {
			return new SpELErrorMessageFormatter();
		}

	}
}
