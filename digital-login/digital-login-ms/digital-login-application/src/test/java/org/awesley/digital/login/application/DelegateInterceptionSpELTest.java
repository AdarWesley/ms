package org.awesley.digital.login.application;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaUser;
import org.awesley.digital.login.resources.implementation.delegate.UserApiImpl;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.awesley.digital.login.service.model.User;
import org.awesley.digital.msf.applicativecontext.ApplicativeContextEntities;
import org.awesley.digital.msf.applicativecontext.ContextEntityInfo;
import org.awesley.digital.msf.applicativecontext.DefaultErrorMessageFormatter;
import org.awesley.digital.msf.applicativecontext.IErrorMessageFormatter;
import org.awesley.digital.msf.applicativecontext.SpELErrorMessageFormatter;
import org.awesley.digital.msf.errors.ApplicationException;
import org.awesley.digital.msf.errors.ErrorInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CxfServiceSpringBootApplication.class, DelegateInterceptionSpELTest.DelegateInterceptionSpELConfiguration.class 
		}) //, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DelegateInterceptionSpELTest {

	@Inject
	private UserApi userApiDelegate;
	
	private static User mockUser = CreateUser();	
	
	@Inject
	private ResourceBundle errorFormatResources;

	@Test
	public void simpleExceptionIsConvertedToApplicationExceptionWithDefaultFormatFromSpEL() {
		initializeMockObject("TestEntity", new NullPointerException("Object was null"));
		Mockito.when(errorFormatResources.getString(Mockito.anyString())).thenCallRealMethod();
		
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
		assertEquals("While executing UserApiImpl.getUser on TestEntity[1] received error: Object was null", errorInfo.getMessage());
	}

	@Test
	public void simpleExceptionIsConvertedToApplicationExceptionWithSpecificFormatFromSpEL() {
		initializeMockObject("TestEntity", new NullPointerException("Object was null"));
		
		Mockito.when(errorFormatResources.getString("UserApiImpl.getUser"))
			.thenReturn("Completely different error format");
		
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
		assertEquals("Completely different error format", errorInfo.getMessage());
	}

	private void initializeMockObject(String entityType, Throwable exceptionToThrow) {
		IUserGetService serviceMock = Mockito.mock(IUserGetService.class);
		Mockito
			.when(serviceMock.GetUser(Mockito.anyLong()))
			.thenAnswer(invocation -> {
				ApplicativeContextEntities.AddContextEntities("TestContext", new ContextEntityInfo(entityType, 1L));
				throw exceptionToThrow;
			});
		
		ReflectionTestUtils.setField(userApiDelegate, UserApiImpl.class, "userGetService", serviceMock, IUserGetService.class);
	}

	private static User CreateUser() {
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
			ResourceBundle rb = Mockito.mock(ResourceBundle.class);
			return rb;
		}
		
		@Bean
		@Primary
		public IErrorMessageFormatter errorMessageFormatter() {
			return new SpELErrorMessageFormatter();
		}

	}
}
