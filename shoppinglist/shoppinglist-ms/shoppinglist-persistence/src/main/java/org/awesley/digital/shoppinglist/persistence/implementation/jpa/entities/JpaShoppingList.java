package org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.awesley.digital.shoppinglist.service.model.GroupRef;
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "FK_SHOPPINGLIST")
	private List<JpaGroupRef> groups = new ArrayList<JpaGroupRef>();
	
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
		this.groups = (List<JpaGroupRef>) groups;
	}
}
