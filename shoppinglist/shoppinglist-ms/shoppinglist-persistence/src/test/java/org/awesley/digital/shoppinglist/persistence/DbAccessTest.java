package org.awesley.digital.shoppinglist.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.awesley.digital.shoppinglist.persistence.configuration.PersistenceConfiguration;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.repositories.ShoppingListJpaRepository;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
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
		,PersistenceConfiguration.class	
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
	}
	
	@Test
	public void canFindShoppingListByQueryExpression() {
		QueryExpression findExpression = new BinaryQueryExpression("name", Operator.EQUALS, "ShoppingList1");
		
		List<? extends ShoppingList> groups = shoppingListRepository.find(findExpression, 0, 1);
		
		assertNotNull(groups);
		assertEquals(1, groups.size());
	}
}
