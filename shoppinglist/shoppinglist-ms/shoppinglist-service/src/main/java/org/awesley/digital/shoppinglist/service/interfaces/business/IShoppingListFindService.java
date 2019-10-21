package org.awesley.digital.shoppinglist.service.interfaces.business;

import java.util.List;

import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.query.QueryExpression;

public interface IShoppingListFindService {

	List<? extends ShoppingList> find(QueryExpression expression, Integer startIndex, Integer pageSize);
}
