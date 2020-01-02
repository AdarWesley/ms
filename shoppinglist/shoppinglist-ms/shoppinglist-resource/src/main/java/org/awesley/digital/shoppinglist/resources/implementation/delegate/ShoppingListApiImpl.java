package org.awesley.digital.shoppinglist.resources.implementation.delegate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.awesley.digital.shoppinglist.resources.interfaces.IResourceFromModelMapper;
import org.awesley.digital.shoppinglist.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.shoppinglist.resources.interfaces.ShoppingListApi;
import org.awesley.digital.shoppinglist.resources.models.GroupName;
import org.awesley.digital.shoppinglist.resources.models.ListItem;
import org.awesley.digital.shoppinglist.resources.models.ShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListAddItemService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListAssociateGroupService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListCreateService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListFindService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListGetService;
import org.awesley.digital.shoppinglist.service.interfaces.business.IShoppingListUpdateService;
import org.awesley.infra.query.QueryExpression;
import org.awesley.infra.query.parser.QueryExpressionParser;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingListApiImpl implements ShoppingListApi {

	@Autowired
	private IShoppingListGetService shoppingListGetService;
	
	@Autowired
	private IShoppingListFindService shoppingListFindService;
	
	@Autowired
	private IShoppingListCreateService shoppingListCreateService;
	
	@Autowired
	private IShoppingListAddItemService shoppingListAddItemService;
	
	@Autowired
	private IShoppingListAssociateGroupService shoppingListAssociateGroupService;
	
	@Autowired
	private IShoppingListUpdateService shoppingListUpdateService;

	@Autowired
	private IResourceFromModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> shoppingListResourceFromModelMapper;
	
	@Autowired
	private IResourceToModelMapper<ShoppingList, org.awesley.digital.shoppinglist.service.model.ShoppingList> shoppingListResourceToModelMapper;

	@Autowired
	private IResourceToModelMapper<ListItem, org.awesley.digital.shoppinglist.service.model.ListItem> listItemResourceToModelMapper;

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
		try {
			filter = URLDecoder.decode(filter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		QueryExpressionParser expressionParser = new QueryExpressionParser(filter);
		expressionParser.Parse();
		QueryExpression expression = expressionParser.getQueryExpression();
		List<? extends org.awesley.digital.shoppinglist.service.model.ShoppingList> shoppingListList = 
				shoppingListFindService.find(expression, startIndex, pageSize);
		return Response.ok(shoppingListList.get(0)).build();
	}

	@Override
	public Response updateShoppingList(Long id, ShoppingList shoppingList) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelShoppingList = shoppingListResourceToModelMapper.mapTo(shoppingList);
		modelShoppingList = shoppingListUpdateService.updateShoppingList(id, modelShoppingList);
		shoppingList = shoppingListResourceFromModelMapper.mapFrom(modelShoppingList);
		
		return Response.status(Status.OK).entity(shoppingList).build();
	}

	@Override
	public Response associateGroup(Long id, GroupName groupName) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelShoppingList = shoppingListGetService.GetShoppingList(id);
		
		org.awesley.digital.shoppinglist.service.model.ShoppingList updatedShoppingList = 
				shoppingListAssociateGroupService.associateGroup(modelShoppingList, groupName.getGroupName());
		
		ShoppingList shoppingList = shoppingListResourceFromModelMapper.mapFrom(updatedShoppingList);
		return Response.status(Status.CREATED).entity(shoppingList).build();
	}

	@Override
	public Response addItem(Long id, ListItem body) {
		org.awesley.digital.shoppinglist.service.model.ShoppingList modelShoppingList = shoppingListGetService.GetShoppingList(id);
		org.awesley.digital.shoppinglist.service.model.ListItem listItem = listItemResourceToModelMapper.mapTo(body);
		
		org.awesley.digital.shoppinglist.service.model.ShoppingList updatedShoppingList = 
				shoppingListAddItemService.addItem(modelShoppingList, listItem);
		
		ShoppingList shoppingList = shoppingListResourceFromModelMapper.mapFrom(updatedShoppingList);
		return Response.status(Status.OK).entity(shoppingList).build();
	}
}
