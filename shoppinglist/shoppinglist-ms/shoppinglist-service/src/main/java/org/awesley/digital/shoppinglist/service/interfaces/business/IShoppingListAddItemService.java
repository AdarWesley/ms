package org.awesley.digital.shoppinglist.service.interfaces.business;

import org.awesley.digital.shoppinglist.service.model.ListItem;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;

public interface IShoppingListAddItemService {

	ShoppingList addItem(ShoppingList modelShoppingList, ListItem listItem);

}
