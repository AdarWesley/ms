package org.awesley.digital.shoppinglist.service.implementation;

import java.util.List;

import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListAddItemService;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.ListItem;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListAddItemService implements IShoppingListAddItemService {

	@Autowired
	private IShoppingListRepository shoppingListRepository;

	@SuppressWarnings("unchecked")
	@Override
	public ShoppingList addItem(ShoppingList modelShoppingList, ListItem listItem) {
		((List<ListItem>)modelShoppingList.getListItems()).add(listItem);
		
		shoppingListRepository.save(modelShoppingList);
		
		return modelShoppingList;
	}

}
