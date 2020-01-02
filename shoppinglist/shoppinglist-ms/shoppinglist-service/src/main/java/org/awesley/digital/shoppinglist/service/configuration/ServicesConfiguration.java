package org.awesley.digital.shoppinglist.service.configuration;

import org.awesley.digital.shoppinglist.service.implementation.ShoppingListAddItemService;
import org.awesley.digital.shoppinglist.service.implementation.ShoppingListAssociateGroupService;
import org.awesley.digital.shoppinglist.service.implementation.ShoppingListCreateService;
import org.awesley.digital.shoppinglist.service.implementation.ShoppingListFindService;
import org.awesley.digital.shoppinglist.service.implementation.ShoppingListGetService;
import org.awesley.digital.shoppinglist.service.implementation.ShoppingListUpdateService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListAddItemService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListAssociateGroupService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListCreateService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListFindService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListGetService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListUpdateService;
import org.awesley.infra.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

	@Bean
	public IShoppingListGetService shoppingListGetService(){
		return new ShoppingListGetService();
	}
	
	@Bean
	public IShoppingListFindService shoppingListFindService() {
		return new ShoppingListFindService();
	}
	
	@Bean
	public IShoppingListCreateService shoppingListCreateService() {
		return new ShoppingListCreateService();
	}
	
	@Bean
	public IShoppingListAddItemService shoppingListAddItemService() {
		return new ShoppingListAddItemService();
	}
	
	@Bean
	public IShoppingListAssociateGroupService shoppingListAssociateGroupService() {
		return new ShoppingListAssociateGroupService();
	}
	
	@Bean
	public IShoppingListUpdateService shoppingListUpdateService() {
		return new ShoppingListUpdateService();
	}
	@Bean
	public JwtTokenUtil jwtTokenUtil(
			@Value("${jwt.secret:secret}") String secret, 
    		@Value("${jwt.expiration:#{60*60*24*7}}") Long expiration,
    		@Value("${jwt.algorithm:}") String algorithm,
    		@Value("${jwt.base64EncodedSigningKey:}") String base64EncodedSigningKey,
    		@Value("${jwt.base64EncodedValidationKey:}") String base64EncodedValidationKey) {
		return new JwtTokenUtil(secret, expiration, algorithm, base64EncodedSigningKey, base64EncodedValidationKey);
	}
}
