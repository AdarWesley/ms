package org.awesley.digital.shoppinglist.service.implementation;

import java.util.List;

import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListFindService;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.query.QueryExpression;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListFindService implements IShoppingListFindService {

	@Autowired
	private IShoppingListRepository shoppingListRepository;
	
	@Override
	public List<? extends ShoppingList> find(QueryExpression expression, Integer startIndex, Integer pageSize) {
		List<? extends ShoppingList> shoppingListList = shoppingListRepository.find(expression, startIndex, pageSize);
		return shoppingListList;
	}

}
