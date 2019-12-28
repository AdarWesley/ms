package org.awesley.digital.shoppinglist.resources.implementation;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.shoppinglist.resources.models.ListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class ListItemResourceToModelMapper
		implements IResourceToModelMapper<ListItem, org.awesley.digital.shoppinglist.service.model.ListItem> {

	@Autowired
	private ApplicationContext ctx;
	
	@Override
	public org.awesley.digital.shoppinglist.service.model.ListItem mapTo(ListItem resourceEntity) {
		org.awesley.digital.shoppinglist.service.model.ListItem modelEntity = 
				ctx.getBean(org.awesley.digital.shoppinglist.service.model.ListItem.class);
		modelEntity.setItemDescription(resourceEntity.getItemDescription());
		return modelEntity;
	}

}
