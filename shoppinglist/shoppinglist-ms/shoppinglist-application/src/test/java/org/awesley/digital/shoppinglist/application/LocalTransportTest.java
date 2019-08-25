package org.awesley.digital.shoppinglist.application;

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
import org.awesley.digital.shoppinglist.config.TestConfiguration;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.digital.shoppinglist.resources.interfaces.ShoppingListApi;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.IShoppingListRepository;
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

	private final static String ENDPOINT_ADDRESS = "local://services";
	private static Server server;

	private static List<Object> providers;
	
	@Autowired
	private void setShoppingListApi(ShoppingListApi shoppingListApi){
		LocalTransportTest.shoppingListApi = shoppingListApi;
	}
	
	private static ShoppingListApi shoppingListApi;
	
	@MockBean
	private IShoppingListRepository shoppingListRepository;
	
	@BeforeClass
	public static void initialize() throws Exception {
		initProviders();
	    startServer();
	}

	@Before
	public void initDatabase() {
		JpaShoppingList shoppingList = new JpaShoppingList();
		shoppingList.setID(1L);
		shoppingList.setName("My Test ShoppingList");
		
		given(shoppingListRepository.getById(1L)).willReturn(shoppingList);
	}

	private static void initProviders() {
		providers = new ArrayList<Object>();
		JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
		jsonProvider.setMapper(new ObjectMapper());
		providers.add(jsonProvider);
	}

	private static void startServer() throws Exception {
	     JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
	     sf.setResourceClasses(ShoppingListApi.class);
	         
	     sf.setProviders(providers);
	         
	     sf.setResourceProvider(ShoppingListApi.class,
	                            new SingletonResourceProvider(null){
	    	@Override
	    	public Object getInstance(Message m) {
	    		return shoppingListApi;
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
	public void canGetShoppingList() {
		ShoppingListApi client = JAXRSClientFactory.create(ENDPOINT_ADDRESS, ShoppingListApi.class, providers);
		WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
		
		ResponseImpl resp = (ResponseImpl) client.getShoppingList(1L);
		ShoppingList shoppingList = resp.readEntity(ShoppingList.class);
		
		assertNotNull(shoppingList);
		assertEquals(1L, (long)shoppingList.getId());
	}
}
