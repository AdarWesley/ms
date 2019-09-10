package org.awesley.digital.shoppinglist.service.interfaces.repository;

import org.awesley.digital.shoppinglist.service.model.ShoppingList;

public interface IShoppingListRepository {
	ShoppingList getById(Long ID);
	ShoppingList save(ShoppingList shoppingList);
}
