package org.awesley.digital.shoppinglist.service.implementation;

import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListGetService;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListGetService implements IShoppingListGetService {

	@Autowired
	private IShoppingListRepository shoppingListRepository;
	
	public ShoppingList GetShoppingList(Long id) {
		return shoppingListRepository.getById(id);
	}
}
