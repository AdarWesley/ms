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
import org.awesley.digital.shoppinglist.service.model.ListItem;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.contracttesting.ContractTestHelper;
import org.awesley.infra.contracttesting.ContractTestsExecutionListener;
import org.awesley.infra.contracttesting.SupportsTestHelper;
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
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = { 
				TestConfiguration.class
				//, CxfServiceSpringBootApplication.class 
				// , ContractTestsBase.LocalTransportConfiguration.class 
		}
		, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
		, properties = { "extconnection.UserGroup.usergroupapi.url=http://localhost:8090" }
		)
@AutoConfigureStubRunner(ids = { "org.awesley.digital:usergroup-application:+:stubs:8090" }, 
	workOffline = true, mappingsOutputFolder = "target/outputMappings")
@TestExecutionListeners(value = { ContractTestsExecutionListener.class }, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ContractTestsBase implements SupportsTestHelper<ContractTestsBase> {

	// private final static String ENDPOINT_ADDRESS = "local://services";
    @LocalServerPort
    private int port;
    private Server server;
	private List<Object> providers;
	
	@Autowired
	private ApplicationContext ctx;
	
	private ContractTestHelper<ContractTestsBase> contractTestHelper;
	
	@Autowired
    private Bus bus;
	
	@MockBean
	private IShoppingListRepository shoppingListRepository;
	
	public IShoppingListRepository getShoppingListRepository() {
		return shoppingListRepository;
	}
	
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
				"/shoppinglist-service");

		server = sf.create();
	}

	private RequestSpecification testRequestSpecification;
	
	public RequestSpecification given() {
		return testRequestSpecification;
	}
	
	private void initRestAssuredRequestSpecification() {
//		RestAssured.baseURI = "http://localhost";
//		RestAssured.port = port;
	    
	    testRequestSpecification = RestAssured.given()
	    		.baseUri("http://localhost")
	    		.port(port);
	    
	    String mockToken = jwtTokenUtil.generateToken("TestUser", Arrays.asList(new JwtAuthority("ROLE_USER")));
	    testRequestSpecification.header("Authorization", "Bearer " + mockToken);
	}

	private void initMockRepository() {
		ShoppingList shoppingList = ctx.getBean(ShoppingList.class);
		shoppingList.setID(1L);
		shoppingList.setName("ShoppingList1");
			
		org.mockito.BDDMockito.given(shoppingListRepository.getById(1L)).willReturn(shoppingList);
		org.mockito.BDDMockito.given(shoppingListRepository.save(org.mockito.BDDMockito.any(ShoppingList.class)))
			.willAnswer(new Answer<ShoppingList>() {
				@Override
				public ShoppingList answer(InvocationOnMock invocation) throws Throwable {
					ShoppingList shoppingList = (ShoppingList)invocation.getArgument(0);
					shoppingList.setID(1L);
					List<? extends ListItem> itemsList = shoppingList.getListItems();
					for (int i = 0; i < itemsList.size(); i++) {
						if (itemsList.get(i).getItemId() == null) {
							itemsList.get(i).setItemId((long)(i + 1));
						}
					}
					
					List<? extends GroupRef> groups = shoppingList.getUserGroups();
					for (int i = 0; i < groups.size(); i++) {
						if (groups.get(i).getId() == null) {
							groups.get(i).setId((long)(i + 1));
						}
					}
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

	@Override
	public ContractTestHelper<ContractTestsBase> getContractTestHelper() {
		return this.contractTestHelper;
	}

	@Override
	public void setContractTestsHelper(ContractTestHelper<ContractTestsBase> contractTestHelper) {
		this.contractTestHelper = contractTestHelper;
	}
}