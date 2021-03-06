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

import org.awesley.digital.shoppinglist.service.model.GroupRef;

@Entity
@Table(name = "TBL_GROUP_REF")
public class JpaGroupRef implements GroupRef {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_SHOPPINGLIST", nullable = false)
	private JpaShoppingList shoppingList;
		
	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public JpaShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(JpaShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

	@Override
	public String getGroupName() {
		return groupName;
	}

	@Override
	public void setGroupName(String name) {
		this.groupName = name;
	}

}
