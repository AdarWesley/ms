package org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.awesley.digital.shoppinglist.service.model.ListItem;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "TBL_SHOPPING_LIST")
public class JpaShoppingList implements ShoppingList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long entityID;
	
	@Column(name = "NAME")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="shoppingList") //fetch = FetchType.EAGER, 
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("groupName ASC")
	private List<JpaGroupRef> groups = new ArrayList<JpaGroupRef>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="shoppingList") // fetch = FetchType.EAGER, 
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy
	private List<JpaListItem> listItems = new ArrayList<JpaListItem>();
	
	@Override
	public Long getID() {
		return entityID;
	}

	@Override
	public void setID(Long id) {
		this.entityID = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<? extends GroupRef> getUserGroups() {
		return groups;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setUserGroups(List<? extends GroupRef> groups) {
		this.groups.clear();
		if (groups != null) {
			this.groups.addAll((List<JpaGroupRef>) groups);
		}
	}

	@Override
	public List<? extends ListItem> getListItems() {
		return listItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setListItems(List<? extends ListItem> itemsList) {
		this.listItems.clear();
		if (itemsList != null) {
			this.listItems.addAll((List<JpaListItem>)itemsList);
		}
	}
}
