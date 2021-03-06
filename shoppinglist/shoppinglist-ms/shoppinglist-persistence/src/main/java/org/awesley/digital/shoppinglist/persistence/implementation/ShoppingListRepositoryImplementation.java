package org.awesley.digital.shoppinglist.persistence.implementation;

import java.util.List;

import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.repositories.ShoppingListJpaRepository;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.query.QueryExpression;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListRepositoryImplementation implements IShoppingListRepository {

	@Autowired
	private ShoppingListJpaRepository shoppingListJpaRepository;
	
	@Override
	public ShoppingList getById(Long id) {
		return shoppingListJpaRepository.findOne(id);
	}
	
	@Override
	public ShoppingList save(ShoppingList shoppingList) {
		return shoppingListJpaRepository.save((JpaShoppingList)shoppingList);
	}

	@Override
	public List<? extends ShoppingList> find(QueryExpression expression, Integer startIndex, Integer pageSize) {
		return shoppingListJpaRepository.findByQueryExpression(expression, startIndex, pageSize);
	}
}
