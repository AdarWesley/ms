package org.awesley.digital.shoppinglist.resources.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.models.ListItem;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.resources.models.UsersGroup;

public class ShoppingListResourceFromModelMapper implements IResourceFromModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> {

	@Override
	public ShoppingList mapFrom(org.awesley.digital.shoppinglist.service.model.ShoppingList modelEntity) {
		ShoppingList shoppingList = new ShoppingList();
		
		shoppingList.setId(modelEntity.getID());
		shoppingList.setName(modelEntity.getName());
		List<UsersGroup> groupsList = null;
		if (modelEntity.getUserGroups() != null) {
			groupsList = modelEntity.getUserGroups().stream().map(gr -> {
				UsersGroup ug = new UsersGroup();
				ug.setGroupId(gr.getId());
				ug.setGroupName(gr.getGroupName());
				return ug;
			}).collect(Collectors.toList());
		}
		shoppingList.setGroups(groupsList);
		
		List<ListItem> itemsList = null;
		if (modelEntity.getListItems() != null) {
			itemsList = modelEntity.getListItems().stream().map(li -> {
				ListItem rli = new ListItem();
				rli.setListItemId(li.getItemId());
				rli.setItemDescription(li.getItemDescription());
				return rli;
			}).collect(Collectors.toList());
		}
		shoppingList.setListItems(itemsList);
		return shoppingList;
	}
}
