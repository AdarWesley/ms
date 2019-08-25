package org.awesley.digital.shoppinglist.resources.implementation.delegate;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.interfaces.IResourceToModelMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.awesley.digital.shoppinglist.resources.interfaces.ShoppingListApi;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.IShoppingListCreateService;
import org.awesley.digital.shoppinglist.service.interfaces.IShoppingListGetService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListApiImpl implements ShoppingListApi {

	@Autowired
	private IShoppingListGetService shoppingListGetService;
	
	@Autowired
	private IShoppingListCreateService shoppingListCreateService;
	
	@Autowired
	private IResourceFromModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> shoppingListResourceFromModelMapper;
	
	@Autowired
	private IResourceToModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> shoppingListResourceToModelMapper;

	public Response getShoppingList(Long id) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelShoppingList = shoppingListGetService.GetShoppingList(id);
		ShoppingList shoppingList = shoppingListResourceFromModelMapper.mapFrom(modelShoppingList);
		return Response.ok(shoppingList).build();
	}

	@Override
	public Response createShoppingList(ShoppingList shoppingList) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelShoppingList = shoppingListResourceToModelMapper.mapTo(shoppingList);
		modelShoppingList = shoppingListCreateService.createShoppingList(modelShoppingList);
		shoppingList = shoppingListResourceFromModelMapper.mapFrom(modelShoppingList);
		
		return Response.status(Status.CREATED).entity(shoppingList).build();
	}

	@Override
	public Response findShoppingList(String filter, Integer startIndex, Integer pageSize) {
		return null;
	}

	@Override
	public Response updateShoppingList(Long id, ShoppingList shoppingList) {
		// TODO Auto-generated method stub
		return null;
	}
}
