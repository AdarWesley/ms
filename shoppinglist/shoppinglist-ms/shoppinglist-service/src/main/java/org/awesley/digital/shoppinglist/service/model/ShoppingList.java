package org.awesley.digital.shoppinglist.service.model;

import java.util.List;

public interface ShoppingList {
	Long getID();
	void setID(Long id);
	
	String getName();
	void setName(String name);
	
	List<? extends GroupRef> getUserGroups();
	void setUserGroups(List<? extends GroupRef> groups);
	
	List<? extends ListItem> getListItems();
	void setListItems(List<? extends ListItem> itemsList);
}
