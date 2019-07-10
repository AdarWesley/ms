package org.awesley.digital.login;

//import static org.mockito.BDDMockito.given;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.message.Message;
import org.awesley.digital.login.config.TestConfiguration;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaAuthority;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaUser;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.resources.interfaces.AuthenticationApi;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.infra.contracttesting.ContractTestHelper;
import org.awesley.infra.contracttesting.ContractTestsExecutionListener;
import org.awesley.infra.contracttesting.SupportsTestHelper;
import org.awesley.infra.security.JwtTokenUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Strings;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(
	// classes = { 
			//TestConfiguration.class
			//, CxfServiceSpringBootApplication.class 
			// , ContractTestsBase.LocalTransportConfiguration.class 
	//}, 
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { TestConfiguration.class })
@TestExecutionListeners(value = { ContractTestsExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class ContractTestsBase implements SupportsTestHelper<ContractTestsBase> {

	// private final static String ENDPOINT_ADDRESS = "local://services";
    @LocalServerPort
    private int port;
    private Server server;
	private List<Object> providers;
	
	@Autowired
    private Bus bus;
	
	@MockBean
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Provider<JwtTokenUtil> jwtTokenUtil;
	
	@Autowired
	private void setUserApi(UserApi userApi){
		ContractTestsBase.userApi = userApi;
	}
	
	private static UserApi userApi;
	
	@Autowired
	private void setAuthenticationApi(AuthenticationApi authenticationApi) {
		ContractTestsBase.authenticationApi = authenticationApi;
	}
	
	private static AuthenticationApi authenticationApi;
	
	private void initProviders() {
		providers = new ArrayList<Object>();
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
		jsonProvider.setMapper(new ObjectMapper());
		providers.add(jsonProvider);
	}

	private void startServer() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setBus(bus);
		sf.setResourceClasses(UserApi.class, AuthenticationApi.class);

		sf.setProviders(providers);

		sf.setResourceProvider(UserApi.class, new SingletonResourceProvider(null) {
			@Override
			public Object getInstance(Message m) {
				return userApi;
			}
		});
		sf.setResourceProvider(AuthenticationApi.class, new SingletonResourceProvider(null) {
			@Override
			public Object getInstance(Message m) {
				return authenticationApi;
			}
		});

		sf.setAddress(
				"http://localhost:" + 
				port + 
				"/services");

		server = sf.create();
	}

	private RequestSpecification testRequestSpecification;
	private String authenticatedIdentityToken;
	private ContractTestHelper<ContractTestsBase> contractTestHelper;
	
	public String getAuthenticatedIdentityToken() {
		return Strings.isNullOrEmpty(authenticatedIdentityToken) ? 
				jwtTokenUtil.get().generateToken("TestUser", Arrays.asList(new JpaAuthority("ROLE_USER"))) :
				authenticatedIdentityToken;
	}

	public void setAuthenticatedIdentityToken(String authenticatedIdentityToken) {
		this.authenticatedIdentityToken = authenticatedIdentityToken;
	}

	public RequestSpecification given() {
		return testRequestSpecification;
	}
	
	@Before
	public void BeforeTest() {
		initProviders();
	    startServer();
	    
	    RestAssured.baseURI = 
	    		"http://localhost:" + 
	    		port + 
	    		"/services";
	    
	    testRequestSpecification = RestAssured.given();
	    
	    testRequestSpecification.header("Authorization", "Bearer " + getAuthenticatedIdentityToken());

		JpaUser testUser = createUserAndSave(1L, "TestUser", "Test", "User", "test.user@test.com",
				Arrays.asList(new JpaAuthority("ROLE_USER")));
		org.mockito.BDDMockito.given(userRepository.getByUsername("TestUser")).willReturn(testUser);
		org.mockito.BDDMockito.given(userRepository.getById(1L)).willReturn(testUser);

		org.mockito.BDDMockito.given(userRepository.getByUsername("AdminUser")).willReturn(createUserAndSave(2L, "AdminUser", "Admin", "User",
				"admin.user@test.com", Arrays.asList(new JpaAuthority("ROLE_ADMIN"))));
	}

	private JpaUser createUserAndSave(long id, String username, String firstname, String lastname, String email,
			List<JpaAuthority> authorities) {
		JpaUser user = new JpaUser();
		user.setId(id);
		user.setUsername(username);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode("password"));
		user.setEmail(email);
		user.setLastPasswordResetDate(Date.from(Instant.now().minus(Duration.ofDays(7))));
		
		user.setAuthorities(authorities);
		
//		authorityJpaRepository.save(authorities);	
//		userJpaRepository.save(user);
		
		return user;
	}
	
	public void doValidate(Map<String, Object> response) throws Exception {
		System.out.println(response);
		if (contractTestHelper != null) {
			contractTestHelper.doValidate(response);
		}
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

	/*@Configuration
	static class LocalTransportConfiguration {
		
		@Bean
		@Primary
		public DataSource dataSource(){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl("jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
			dataSource.setUsername("sa");
			dataSource.setPassword("");
			dataSource.setDriverClassName("org.h2.Driver");
			return dataSource;
		}
	}*/	
}
