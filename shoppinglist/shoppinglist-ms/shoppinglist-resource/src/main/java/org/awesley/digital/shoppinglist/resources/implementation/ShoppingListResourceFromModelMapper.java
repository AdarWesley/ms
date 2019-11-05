package org.awesley.digital.shoppinglist.resources.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.resources.models.UsersGroup;

public class ShoppingListResourceFromModelMapper implements IResourceFromModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> {

//	@Autowired
//	private IResourceFromModelMapper<UsersGroup, org.awesley.digital.shoppinglist.service.model.GroupRef> userGroupResourceFromModelMapper;
	
	@Override
	public ShoppingList mapFrom(org.awesley.digital.shoppinglist.service.model.ShoppingList modelEntity) {
		ShoppingList shoppingList = new ShoppingList();
		
		shoppingList.setId(modelEntity.getID());
		shoppingList.setName(modelEntity.getName());
		List<UsersGroup> groupsList = modelEntity.getUserGroups().stream().map(gr -> {
			UsersGroup ug = new UsersGroup();
			ug.setGroupId(gr.getId());
			ug.setGroupName(gr.getName());
			return ug;
		}).collect(Collectors.toList());
		
		shoppingList.setGroups(groupsList);
		
		return shoppingList;
	}

}
