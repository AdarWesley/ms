package org.awesley.digital.shoppinglist.service.implementation;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListUpdateService;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.ListItem;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListUpdateService implements IShoppingListUpdateService {

	@Autowired
	private IShoppingListRepository shoppingListRepository;

	@Override
	public ShoppingList updateShoppingList(Long id, ShoppingList modelShoppingList) {
		ShoppingList currentShoppingList = shoppingListRepository.getById(id);
		
		// update shopping list
		currentShoppingList.setName(modelShoppingList.getName());
		currentShoppingList.setUserGroups(modelShoppingList.getUserGroups());
		currentShoppingList.setListItems(modelShoppingList.getListItems());
		
		shoppingListRepository.save(currentShoppingList);
		return currentShoppingList;
	}

}
