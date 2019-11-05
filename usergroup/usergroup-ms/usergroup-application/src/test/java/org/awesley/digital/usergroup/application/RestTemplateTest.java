package org.awesley.digital.usergroup.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.stream.Collectors;

import org.awesley.digital.usergroup.config.TestConfiguration;
import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.awesley.digital.usergroup.resources.models.UserGroup;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { 
	TestConfiguration.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private JWTAuthorizationRequestInterceptor jwtAuthorizationRequestInterceptor;
	
	@MockBean
	private IUserGroupRepository userGroupRepository;
	
	public void initializeJWT() {
		List<ClientHttpRequestInterceptor> interceptors = testRestTemplate.getRestTemplate().getInterceptors();
		interceptors = 
				interceptors.stream().filter(i -> 
					(!(i instanceof BasicAuthorizationInterceptor) && 
					 !(i instanceof JWTAuthorizationRequestInterceptor)))
				.collect(Collectors.toList());
		interceptors.add(jwtAuthorizationRequestInterceptor);
		testRestTemplate.getRestTemplate().setInterceptors(interceptors);
	}
	
	private void initDatabase() {
		JpaUserGroup readUserGroup = new JpaUserGroup();
		readUserGroup.setID(1L);
		readUserGroup.setName("My Test List");
		
		given(userGroupRepository.getById(1L)).willReturn(readUserGroup);
	}

	@Before
	public void initTest() {
		initializeJWT();
		initDatabase();
	}
	
	@Test
	@WithMockUser(username="TestAdmin", roles= {"ADMIN"})
	public void canLoadUserGroup() {
		ResponseEntity<UserGroup> entity = testRestTemplate.getForEntity("http://localhost:" + this.port + "/usergroup-service/userGroup/1", UserGroup.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
}
