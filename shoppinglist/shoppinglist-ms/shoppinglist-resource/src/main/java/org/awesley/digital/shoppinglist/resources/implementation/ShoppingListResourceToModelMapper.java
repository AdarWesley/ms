package org.awesley.digital.shoppinglist.resources.implementation;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ShoppingListResourceToModelMapper implements IResourceToModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> {

	@Autowired
	private ApplicationContext ctx;
	
	@Override
	public org.awesley.digital.shoppinglist.service.model.ShoppingList mapTo(ShoppingList resourceEntity) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelEntity = 
				ctx.getBean(org.awesley.digital.shoppinglist.service.model.ShoppingList.class);
		
		modelEntity.setID(resourceEntity.getId());
		modelEntity.setName(resourceEntity.getName());
		
		return modelEntity;
	}

}
