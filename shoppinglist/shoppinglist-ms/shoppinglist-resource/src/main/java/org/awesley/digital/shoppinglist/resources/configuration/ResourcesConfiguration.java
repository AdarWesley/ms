package org.awesley.digital.shoppinglist.resources.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.awesley.digital.shoppinglist.resources.implementation.ListItemResourceToModelMapper;
import org.awesley.digital.shoppinglist.resources.implementation.ShoppingListResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.implementation.ShoppingListResourceToModelMapper;
import org.awesley.digital.shoppinglist.resources.implementation.delegate.ShoppingListApiImpl;
import org.awesley.digital.shoppinglist.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.shoppinglist.resources.interfaces.ShoppingListApi;
import org.awesley.digital.shoppinglist.resources.models.ListItem;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;

@Configuration
public class ResourcesConfiguration {

	@Bean
	public ShoppingListApi shoppingListApi(){
		return new ShoppingListApiImpl();
	}
	
	@Bean
	public IResourceFromModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> 
	shoppingListResourceFromModelMapper(){
		return new ShoppingListResourceFromModelMapper();
	}
	
	@Bean
	public IResourceToModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList>
	shoppingListResourceToModelMapper(){
		return new ShoppingListResourceToModelMapper();
	}
	
	@Bean
	public IResourceToModelMapper<ListItem, org.awesley.digital.shoppinglist.service.model.ListItem> 
	listItemResourceToModelMapper() {
		return new ListItemResourceToModelMapper();
	}
}
