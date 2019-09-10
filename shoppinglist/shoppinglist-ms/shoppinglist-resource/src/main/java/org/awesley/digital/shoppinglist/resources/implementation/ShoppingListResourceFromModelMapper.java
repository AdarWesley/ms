package org.awesley.digital.shoppinglist.resources.implementation;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;

public class ShoppingListResourceFromModelMapper implements IResourceFromModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> {

//	@Autowired
//	private IResourceFromModelMapper<UsersGroup, org.awesley.digital.shoppinglist.service.model.GroupRef> userGroupResourceFromModelMapper;
	
	@Override
	public ShoppingList mapFrom(org.awesley.digital.shoppinglist.service.model.ShoppingList modelEntity) {
		ShoppingList shoppingList = new ShoppingList();
		
		shoppingList.setId(modelEntity.getID());
		shoppingList.setName(modelEntity.getName());
		shoppingList.setGroups(null);
		
		return shoppingList;
	}

}
