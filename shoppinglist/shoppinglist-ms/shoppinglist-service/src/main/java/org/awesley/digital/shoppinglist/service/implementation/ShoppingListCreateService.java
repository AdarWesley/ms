package org.awesley.digital.shoppinglist.service.implementation;

import org.awesley.digital.shoppinglist.service.interfaces.IShoppingListCreateService;
import org.awesley.digital.shoppinglist.service.interfaces.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListCreateService implements IShoppingListCreateService {

	@Autowired
	private IShoppingListRepository shoppingListRepository;
	
	@Override
	public ShoppingList createShoppingList(ShoppingList modelShoppingList) {
		return shoppingListRepository.save(modelShoppingList);
	}
}
