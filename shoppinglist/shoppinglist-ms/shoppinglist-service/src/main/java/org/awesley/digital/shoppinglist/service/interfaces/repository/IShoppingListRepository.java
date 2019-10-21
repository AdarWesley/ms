package org.awesley.digital.shoppinglist.service.interfaces.repository;

import java.util.List;

import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.query.QueryExpression;

public interface IShoppingListRepository {
	ShoppingList getById(Long ID);
	ShoppingList save(ShoppingList shoppingList);
	List<? extends ShoppingList> find(QueryExpression expression, Integer startIndex, Integer pageSize);
}
