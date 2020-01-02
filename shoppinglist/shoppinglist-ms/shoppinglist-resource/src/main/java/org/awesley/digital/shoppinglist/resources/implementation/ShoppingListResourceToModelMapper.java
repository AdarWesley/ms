package org.awesley.digital.shoppinglist.resources.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ShoppingListResourceToModelMapper implements IResourceToModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> {

	@Autowired
	private ApplicationContext ctx;
	
	@Override
	public org.awesley.digital.shoppinglist.service.model.ShoppingList mapTo(ShoppingList resourceEntity) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelEntity = 
				ctx.getBean(org.awesley.digital.shoppinglist.service.model.ShoppingList.class);
		
		modelEntity.setID(resourceEntity.getId());
		modelEntity.setName(resourceEntity.getName());
		List<GroupRef> modelGroups = null;
		if (resourceEntity.getGroups() != null && resourceEntity.getGroups().size() > 0) {
				modelGroups = resourceEntity.getGroups().stream().map(g -> { 
				GroupRef gr = ctx.getBean(GroupRef.class); 
				gr.setId(g.getGroupId());
				gr.setGroupName(g.getGroupName());
				return gr;
			}).collect(Collectors.toList());
		}
		modelEntity.setUserGroups(modelGroups);
		
		List<org.awesley.digital.shoppinglist.service.model.ListItem> itemsList = null;
		if (resourceEntity.getListItems() != null && resourceEntity.getListItems().size() > 0) {
			itemsList = resourceEntity.getListItems().stream().map(li -> {
				org.awesley.digital.shoppinglist.service.model.ListItem mli = 
						ctx.getBean(org.awesley.digital.shoppinglist.service.model.ListItem.class);
				mli.setItemId(li.getListItemId());
				mli.setItemDescription(li.getItemDescription());
				return mli;
			}).collect(Collectors.toList());
		}
		modelEntity.setListItems(itemsList);
		
		return modelEntity;
	}
}
