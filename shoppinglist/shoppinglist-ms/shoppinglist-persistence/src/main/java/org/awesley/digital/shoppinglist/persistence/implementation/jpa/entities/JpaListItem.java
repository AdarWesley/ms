package org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.awesley.digital.shoppinglist.service.model.ListItem;

@Entity
@Table(name = "TBL_LIST_ITEM")
public class JpaListItem implements ListItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITEM_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_SHOPPINGLIST", nullable = false)
	private JpaShoppingList shoppingList;
	
	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;
	
	@Override
	public Long getItemId() {
		return id;
	}

	@Override
	public void setItemId(Long listItemId) {
		this.id = listItemId;
	}

	public JpaShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(JpaShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

	@Override
	public String getItemDescription() {
		return itemDescription;
	}

	@Override
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
}
