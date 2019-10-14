package org.awesley.digital.shoppinglist.resources.implementation.delegate;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.interfaces.IResourceToModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.awesley.digital.shoppinglist.resources.interfaces.ShoppingListApi;
import org.awesley.digital.shoppinglist.resources.models.GroupName;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListAssociateGroupService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListCreateService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListGetService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListApiImpl implements ShoppingListApi {

	@Autowired
	private IShoppingListGetService shoppingListGetService;
	
	@Autowired
	private IShoppingListCreateService shoppingListCreateService;
	
	@Autowired
	private IShoppingListAssociateGroupService shoppingListAssociateGroupService;
	
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

	@Override
	public Response associateGroup(Long id, GroupName groupName) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelShoppingList = shoppingListGetService.GetShoppingList(id);
		
		org.awesley.digital.shoppinglist.service.model.ShoppingList updatedShoppingList = 
				shoppingListAssociateGroupService.associateGroup(modelShoppingList, groupName.getGroupName());
		
		ShoppingList shoppingList = shoppingListResourceFromModelMapper.mapFrom(updatedShoppingList);
		return Response.status(Status.CREATED).entity(shoppingList).build();
	}
}
