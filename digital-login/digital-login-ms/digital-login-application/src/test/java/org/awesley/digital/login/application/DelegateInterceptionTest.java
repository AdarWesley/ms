package org.awesley.digital.login.application;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaUser;
import org.awesley.digital.login.resources.implementation.delegate.UserApiImpl;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.service.interfaces.IUserGetService;
import org.awesley.digital.login.service.model.User;
import org.awesley.digital.msf.applicativecontext.ApplicativeContextEntities;
import org.awesley.digital.msf.applicativecontext.ContextEntityInfo;
import org.awesley.digital.msf.errors.ApplicationException;
import org.awesley.digital.msf.errors.ErrorInfo;
import org.hamcrest.Matchers;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CxfServiceSpringBootApplication.class 
		}) //, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DelegateInterceptionTest {
	
	@Inject
	private UserApi userApiDelegate;
	
	private static User mockUser = CreateUser();	
	
	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void applicativeContextIsEmptyAfterExecutionTest() {
		IUserGetService serviceMock = Mockito.mock(IUserGetService.class);
		Mockito.when(serviceMock.GetUser(Mockito.any())).thenReturn(mockUser);
		
		ReflectionTestUtils.setField(userApiDelegate, UserApiImpl.class, "userGetService", serviceMock, IUserGetService.class);
		
		userApiDelegate.getUser(1L);
		
		assertEquals(0L, ApplicativeContextEntities.Count());
	}

	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void applicativeContextIsEmptyAfterExecutionAfterStartingDirtyTest() {
		IUserGetService serviceMock = Mockito.mock(IUserGetService.class);
		Mockito.when(serviceMock.GetUser(Mockito.any())).thenReturn(mockUser);
		
		ReflectionTestUtils.setField(userApiDelegate, UserApiImpl.class, "userGetService", serviceMock, IUserGetService.class);
		ApplicativeContextEntities.AddContextEntities("TestContext", new ContextEntityInfo("TestEntity", 1L));
		
		userApiDelegate.getUser(1L);
		
		assertEquals(0L, ApplicativeContextEntities.Count());
	}

	@Test
	@WithMockUser(roles = {"ADMIN"})
	public void simpleExceptionIsConvertedToApplicationExceptionWithContextTest() {
		IUserGetService serviceMock = Mockito.mock(IUserGetService.class);
		Mockito
			.when(serviceMock.GetUser(Mockito.anyLong()))
			.thenAnswer(invocation -> {
				ApplicativeContextEntities.AddContextEntities("TestContext", new ContextEntityInfo("TestEntity", 1L));
				throw new NullPointerException("Object was null");
			});
		
		ReflectionTestUtils.setField(userApiDelegate, UserApiImpl.class, "userGetService", serviceMock, IUserGetService.class);
		
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
	@WithMockUser(roles = {"ADMIN"})
	public void applicativeContextIsEmptyAfterInitializingInDelegateAndThrowingTest() {
		IUserGetService serviceMock = Mockito.mock(IUserGetService.class);
		Mockito
			.doAnswer(invocation -> {
				ApplicativeContextEntities.AddContextEntities("TestContext", new ContextEntityInfo("TestEntity", 1L));
				throw new NullPointerException("Object was null");
			})
			.when(serviceMock).GetUser(Mockito.any());
		
		ReflectionTestUtils.setField(userApiDelegate, UserApiImpl.class, "userGetService", serviceMock, IUserGetService.class);
		
		boolean didThrow = false;
		ErrorInfo errorInfo = null;
		try {
			userApiDelegate.getUser(1L);
		}
		catch (Exception ex) {
			didThrow = true;
		}
		
		assertTrue(didThrow);
		assertEquals(0L, ApplicativeContextEntities.Count());
	}

	private static User CreateUser() {
		User user = new JpaUser();
		user.setFirstname("Test");
		user.setLastname("User");
		user.setId(1L);
		return user;
	}
}
