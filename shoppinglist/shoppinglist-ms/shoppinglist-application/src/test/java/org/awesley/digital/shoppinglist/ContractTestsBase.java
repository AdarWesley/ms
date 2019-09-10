package org.awesley.digital.shoppinglist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.message.Message;
import org.awesley.digital.shoppinglist.config.TestConfiguration;
import org.awesley.digital.shoppinglist.resources.interfaces.ShoppingListApi;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.security.JwtTokenUtil;
import org.awesley.infra.security.model.JwtAuthority;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { 
	TestConfiguration.class
	//, CxfServiceSpringBootApplication.class 
	// , ContractTestsBase.LocalTransportConfiguration.class 
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = { "org.awesley.digital:usergroup-application:+:stubs" })
public class ContractTestsBase {

	// private final static String ENDPOINT_ADDRESS = "local://services";
    @LocalServerPort
    private int port;
    private Server server;
	private List<Object> providers;
	
	@Autowired
    private Bus bus;
	
	@MockBean
	private IShoppingListRepository shoppingListRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private void setShoppingListApi(ShoppingListApi shoppingListApi){
		ContractTestsBase.shoppingListApi = shoppingListApi;
	}
	
	protected static ShoppingListApi shoppingListApi;
	
	private void initProviders() {
		providers = new ArrayList<Object>();
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
		jsonProvider.setMapper(new ObjectMapper());
		providers.add(jsonProvider);
	}

	private void startServer() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setBus(bus);
		sf.setResourceClasses(ShoppingListApi.class);

		sf.setProviders(providers);

		sf.setResourceProvider(ShoppingListApi.class, new SingletonResourceProvider(null) {
			@Override
			public Object getInstance(Message m) {
				return shoppingListApi;
			}
		});

		sf.setAddress(
				"http://localhost:" + 
				port + 
				"/services");

		server = sf.create();
	}

	private RequestSpecification testRequestSpecification;
	
	public RequestSpecification given() {
		return testRequestSpecification;
	}
	
	private void initRestAssuredRequestSpecification() {
		RestAssured.baseURI = 
	    		"http://localhost:" + 
	    		port + 
	    		"/services";
	    
	    testRequestSpecification = RestAssured.given();
	    
	    String mockToken = jwtTokenUtil.generateToken("TestUser", Arrays.asList(new JwtAuthority("ROLE_USER")));
	    testRequestSpecification.header("Authorization", "Bearer " + mockToken);
	}

	private void initMockRepository() {
		ShoppingList shoppingList = new ShoppingList() {
			@Override public Long getID() { return 1L; }
			@Override public void setID(Long id) {}
			@Override public String getName() {	return "TestUser"; }
			@Override public void setName(String name) {}
			@Override public List<? extends GroupRef> getUserGroups() { return null; }
			@Override public void setUserGroups(List<? extends GroupRef> groups) {}
		};
			
		org.mockito.BDDMockito.given(shoppingListRepository.getById(1L)).willReturn(shoppingList);
		org.mockito.BDDMockito.given(shoppingListRepository.save(org.mockito.BDDMockito.any(ShoppingList.class)))
			.willAnswer(new Answer<ShoppingList>() {
				@Override
				public ShoppingList answer(InvocationOnMock invocation) throws Throwable {
					ShoppingList shoppingList = (ShoppingList)invocation.getArgument(0);
					shoppingList.setID(1L);
					return shoppingList;
				}
			});
	}
	
	@Before
	public void BeforeTest() {
		initProviders();
	    startServer();
	    initMockRepository();
	    
	    initRestAssuredRequestSpecification();
	}

	@After
	public void destroy() throws Exception {
	   server.stop();
	   server.destroy();
	   providers = null;
	}
}