package org.awesley.digital.shoppinglist.service.interfaces.business;

import org.awesley.digital.shoppinglist.service.model.ShoppingList;

public interface IShoppingListUpdateService {

	ShoppingList updateShoppingList(Long id, ShoppingList modelShoppingList);

}
