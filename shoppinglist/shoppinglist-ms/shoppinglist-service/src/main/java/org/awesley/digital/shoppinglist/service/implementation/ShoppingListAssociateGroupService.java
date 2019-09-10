package org.awesley.digital.shoppinglist.service.implementation;

import java.util.List;

import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListAssociateGroupService;
import org.awesley.digital.shoppinglist.service.interfaces.gateway.IGroupGatewayService;
import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListAssociateGroupService implements IShoppingListAssociateGroupService {

	@Autowired
	private IGroupGatewayService groupGatewayService;

	@SuppressWarnings("unchecked")
	@Override
	public ShoppingList associateGroup(ShoppingList shoppingList, String groupName) {
		GroupRef group = groupGatewayService.getGroup(groupName);
		((List<GroupRef>)(shoppingList.getUserGroups())).add(group);
		return shoppingList;
	}

}
