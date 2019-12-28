package org.awesley.digital.shoppinglist.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.awesley.digital.shoppinglist.persistence.PersistenceTestConfiguration;
import org.awesley.digital.shoppinglist.persistence.configuration.PersistenceConfiguration;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaGroupRef;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaListItem;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.repositories.ShoppingListJpaRepository;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.awesley.digital.shoppinglist.service.model.ListItem;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.query.BinaryQueryExpression;
import org.awesley.infra.query.Operator;
import org.awesley.infra.query.QueryExpression;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		PersistenceTestConfiguration.class
		, PersistenceConfiguration.class	
	}
)
public class DbAccessTest {

	@Autowired
	private ShoppingListJpaRepository shoppingListJpaRepository;
	
	@Autowired
	private IShoppingListRepository shoppingListRepository;
	
	@Test
	public void canQueryDatabase() {
		long count = shoppingListJpaRepository.count();
		assertEquals(3, count); // test data from resources/data.sql populates 3 empty lists
	}
	
	@Test
	public void canFindShoppingListByQueryExpression() {
		QueryExpression findExpression = new BinaryQueryExpression("name", Operator.EQUALS, "ShoppingList1");
		
		List<? extends ShoppingList> shoppingLists = shoppingListRepository.find(findExpression, 0, 1);
		
		assertNotNull(shoppingLists);
		assertEquals(1, shoppingLists.size());
		assertEquals("ShoppingList1", shoppingLists.get(0).getName());
	}
	
	@Test
	public void canUpdateShoppingList() {
		ShoppingList shoppingList = shoppingListRepository.getById(2L);
		
		updateAndPersistShoppingList(shoppingList);
		
		ShoppingList persistentShoppingList = shoppingListRepository.getById(2L);
		
		assertNotNull(persistentShoppingList);
		assertEquals("ShoppingList2", persistentShoppingList.getName());
		
		assertNotNull(persistentShoppingList.getListItems());
		assertEquals(1, persistentShoppingList.getListItems().size());
		assertEquals(1L, persistentShoppingList.getListItems().get(0).getItemId().longValue());
		assertEquals("Item 1 description", persistentShoppingList.getListItems().get(0).getItemDescription());
		
		assertNotNull(persistentShoppingList.getUserGroups());
		assertEquals(1, persistentShoppingList.getUserGroups().size());
		assertEquals(1L, persistentShoppingList.getUserGroups().get(0).getId().longValue());
		assertEquals("Group1", persistentShoppingList.getUserGroups().get(0).getGroupName());
	}

	@SuppressWarnings("unchecked")
	protected void updateAndPersistShoppingList(ShoppingList shoppingList) {
		GroupRef group = new JpaGroupRef();
		//group.setId(1L);
		group.setGroupName("Group1");
		((JpaGroupRef)group).setShoppingList((JpaShoppingList)shoppingList);
		((List<GroupRef>)shoppingList.getUserGroups()).add(group);
		
		ListItem item = new JpaListItem();
		//item.setItemId(1L);
		item.setItemDescription("Item 1 description");
		((JpaListItem)item).setShoppingList((JpaShoppingList)shoppingList);
		((List<ListItem>)shoppingList.getListItems()).add(item);
		
		shoppingListRepository.save(shoppingList);
	}
}
