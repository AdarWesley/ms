package org.awesley.digital.usergroup.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.impl.ResponseImpl;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.local.LocalConduit;
import org.awesley.digital.usergroup.config.TestConfiguration;
import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.awesley.digital.usergroup.resources.interfaces.UserGroupApi;
import org.awesley.digital.usergroup.resources.models.UserGroup;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { 
	TestConfiguration.class
}) //, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalTransportTest {

	private final static String ENDPOINT_ADDRESS = "local://usergroup-service";
	private static Server server;

	private static List<Object> providers;
	
	@Autowired
	private void setUserGroupApi(UserGroupApi userGroupApi){
		LocalTransportTest.userGroupApi = userGroupApi;
	}
	
	private static UserGroupApi userGroupApi;
	
	@MockBean
	private IUserGroupRepository userGroupRepository;
	
	@BeforeClass
	public static void initialize() throws Exception {
		initProviders();
	    startServer();
	}

	@Before
	public void initDatabase() {
		JpaUserGroup userGroup = new JpaUserGroup();
		userGroup.setID(1L);
		userGroup.setName("My Test UserGroup");
		
		given(userGroupRepository.getById(1L)).willReturn(userGroup);
	}

	private static void initProviders() {
		providers = new ArrayList<Object>();
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
		jsonProvider.setMapper(new ObjectMapper());
		providers.add(jsonProvider);
	}

	private static void startServer() throws Exception {
	     JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	     sf.setResourceClasses(UserGroupApi.class);
	         
	     sf.setProviders(providers);
	         
	     sf.setResourceProvider(UserGroupApi.class,
	                            new SingletonResourceProvider(null){
	    	@Override
	    	public Object getInstance(Message m) {
	    		return userGroupApi;
	    	}
	     });
	     
	     sf.setAddress(ENDPOINT_ADDRESS);
	 
	     server = sf.create();
	}
	 
	@AfterClass
	public static void destroy() throws Exception {
	   server.stop();
	   server.destroy();
	   providers = null;
	}

	@Test
	public void canGetUserGroup() {
		UserGroupApi client = JAXRSClientFactory.create(ENDPOINT_ADDRESS, UserGroupApi.class, providers);
		WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
		
		ResponseImpl resp = (ResponseImpl) client.getUserGroup(1L);
		UserGroup userGroup = resp.readEntity(UserGroup.class);
		
		assertNotNull(userGroup);
		assertEquals(1L, (long)userGroup.getId());
	}
}
