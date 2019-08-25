package org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.awesley.digital.shoppinglist.service.model.ShoppingList;

@Entity
@Table(name = "TBL_SHOPPING_LIST")
public class JpaShoppingList implements ShoppingList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long entityID;
	
	@Column(name = "NAME")
	private String name;
	
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
}
