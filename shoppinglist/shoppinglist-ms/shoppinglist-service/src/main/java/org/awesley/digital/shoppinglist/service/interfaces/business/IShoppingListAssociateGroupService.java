package org.awesley.digital.shoppinglist.service.interfaces.business;

import java.util.List;

import org.awesley.digital.shoppinglist.service.model.ShoppingList;

public interface IShoppingListAssociateGroupService {

	ShoppingList associateGroup(ShoppingList shoppingList, String groupNames);

}
