package org.awesley.digital.login.application;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.ProcessingException;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.local.LocalConduit;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaAuthority;
import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaUser;
import org.awesley.digital.login.resources.interfaces.AuthenticationApi;
import org.awesley.digital.login.resources.interfaces.UserApi;
import org.awesley.digital.login.resources.models.JwtAuthenticationRequest;
import org.awesley.digital.login.resources.models.JwtAuthenticationResponse;
import org.awesley.digital.login.resources.models.User;
import org.awesley.digital.login.service.interfaces.IUserRepository;
import org.awesley.digital.login.service.model.AuthorityName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

//import com.arjuna.ats.internal.jdbc.drivers.modifiers.extensions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CxfServiceSpringBootApplication.class
							, LocalTransportTest.LocalTransportConfiguration.class 
		}) //, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalTransportTest {

	private final static String ENDPOINT_ADDRESS = "local://services";
	private static Server server;
	private static List<Object> providers;
	
	@MockBean
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private void setUserApi(UserApi userApi){
		LocalTransportTest.userApi = userApi;
	}
	
	private static UserApi userApi;
	
	@Autowired
	private void setAuthenticationApi(AuthenticationApi authenticationApi) {
		LocalTransportTest.authenticationApi = authenticationApi;
	}
	
	private static AuthenticationApi authenticationApi;

	private static UserApi userApiClient;
	private static AuthenticationApi authApiClient;
	
	@BeforeClass
	public static void initialize() throws Exception {
		initProviders();
	    startServer();
	    
		userApiClient = JAXRSClientFactory.create(ENDPOINT_ADDRESS, UserApi.class, providers);
		WebClient.getConfig(userApiClient).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);

		authApiClient = JAXRSClientFactory.create(ENDPOINT_ADDRESS, AuthenticationApi.class, providers);
		WebClient.getConfig(authApiClient).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
	}

	private static void initProviders() {
		providers = new ArrayList<Object>();
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
		jsonProvider.setMapper(new ObjectMapper());
		providers.add(jsonProvider);
	}

	private static void startServer() throws Exception {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
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

		sf.setAddress(ENDPOINT_ADDRESS);

		server = sf.create();
	}
	
	@Before
	public void initDatabase() {
		JpaUser testUser = createUserAndSave(1L, "TestUser", "Test", "User", "test.user@test.com", 
						  Arrays.asList(new JpaAuthority(AuthorityName.ROLE_USER)));
		given(userRepository.getByUsername("TestUser")).willReturn(testUser);
		given(userRepository.getById(1L)).willReturn(testUser);
		
		given(userRepository.getByUsername("AdminUser")).willReturn(
				createUserAndSave(2L, "AdminUser", "Admin", "User", "admin.user@test.com",
						Arrays.asList(new JpaAuthority(AuthorityName.ROLE_ADMIN))));
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
	
	@AfterClass
	public static void destroy() throws Exception {
	   server.stop();
	   server.destroy();
	   providers = null;
	}

	@Test(expected = RuntimeException.class)
	public void canGetUser() {
		userApiClient.getUser(1L);
	}
	
	@Test
	public void canAuthenticate() {
		JwtAuthenticationResponse response = doAuthentication("TestUser", "password");
		assertNotNull(response);
		System.out.println(response.getToken());
	}

	@Test(expected = ProcessingException.class)
	public void canAuthenticateAndGetUserAccessDenied() {
		JwtAuthenticationResponse response = doAuthentication("TestUser", "password");
		assertNotNull(response);
		
		WebClient.client(userApiClient).header("Authorization", "Bearer " + response.getToken());
		userApiClient.getUser(1L);
		
		fail();
	}
	
	@Test
	public void canAuthenticateAndGetUserSuccess() {
		JwtAuthenticationResponse response = doAuthentication("AdminUser", "password");
		assertNotNull(response);
		
		WebClient.client(userApiClient).header("Authorization", "Bearer " + response.getToken());
		User user = userApiClient.getUser(1L);
		
		assertNotNull(user);
	}
	
	private JwtAuthenticationResponse doAuthentication(String username, String password) {
		JwtAuthenticationRequest request = new JwtAuthenticationRequest();
		request.setUsername(username);
		request.setPassword(password);

		JwtAuthenticationResponse response = authApiClient.authenticatePost(request);
		return response;
	}
	
	@Configuration
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
	}
}
