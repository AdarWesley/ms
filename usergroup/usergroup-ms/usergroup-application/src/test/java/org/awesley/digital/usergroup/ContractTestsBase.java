package org.awesley.digital.usergroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.message.Message;
import org.awesley.digital.usergroup.config.TestConfiguration;
import org.awesley.digital.usergroup.resources.interfaces.UserGroupApi;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Any;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { 
	TestConfiguration.class
	//, CxfServiceSpringBootApplication.class 
	// , ContractTestsBase.LocalTransportConfiguration.class 
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractTestsBase {

	// private final static String ENDPOINT_ADDRESS = "local://services";
    @LocalServerPort
    private int port;
    private Server server;
	private List<Object> providers;
	
	@Autowired
    private Bus bus;
	
	@MockBean
	private IUserGroupRepository userGroupRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private void setUserGroupApi(UserGroupApi userGroupApi){
		ContractTestsBase.userGroupApi = userGroupApi;
	}
	
	protected static UserGroupApi userGroupApi;
	
	private void initProviders() {
		providers = new ArrayList<Object>();
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
		jsonProvider.setMapper(new ObjectMapper());
		providers.add(jsonProvider);
	}

	private void startServer() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setBus(bus);
		sf.setResourceClasses(UserGroupApi.class);

		sf.setProviders(providers);

		sf.setResourceProvider(UserGroupApi.class, new SingletonResourceProvider(null) {
			@Override
			public Object getInstance(Message m) {
				return userGroupApi;
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
		UserGroup userGroup = new UserGroup() {
			@Override public Long getID() { return 1L; }
			@Override public void setID(Long id) {}
			@Override public String getName() {	return "TestUser"; }
			@Override public void setName(String name) {}
		};
			
		org.mockito.BDDMockito.given(userGroupRepository.getById(1L)).willReturn(userGroup);
		org.mockito.BDDMockito.given(userGroupRepository.save(org.mockito.BDDMockito.any(UserGroup.class)))
			.willAnswer(new Answer<UserGroup>() {
				@Override
				public UserGroup answer(InvocationOnMock invocation) throws Throwable {
					UserGroup userGroup = (UserGroup)invocation.getArgument(0);
					userGroup.setID(1L);
					return userGroup;
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