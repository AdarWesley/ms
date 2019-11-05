package org.awesley.digital.shoppinglist.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.stream.Collectors;

import org.awesley.digital.shoppinglist.config.TestConfiguration;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
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
	private IShoppingListRepository shoppingListRepository;
	
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
		JpaShoppingList readShoppingList = new JpaShoppingList();
		readShoppingList.setID(1L);
		readShoppingList.setName("My Test List");
		
		given(shoppingListRepository.getById(1L)).willReturn(readShoppingList);
	}

	@Before
	public void initTest() {
		initializeJWT();
		initDatabase();
	}
	
	@Test
	@WithMockUser(username="TestAdmin", roles= {"ADMIN"})
	public void canLoadShoppingList() {
		ResponseEntity<ShoppingList> entity = testRestTemplate.getForEntity("http://localhost:" + this.port + "/shoppinglist-service/shoppingList/1", ShoppingList.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
}
