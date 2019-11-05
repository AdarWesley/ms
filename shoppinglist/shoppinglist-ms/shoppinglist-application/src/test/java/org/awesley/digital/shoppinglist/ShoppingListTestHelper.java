package org.awesley.digital.shoppinglist;

import java.util.Arrays;

import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.infra.contracttesting.AbstractContractTestHelper;
import org.awesley.infra.contracttesting.ContractTestsExecutionListener;
import org.awesley.infra.query.QueryExpression;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.TestContext;

public class ShoppingListTestHelper extends AbstractContractTestHelper<ContractTestsBase> {

	@Override
	public void beforeTestMethod(ContractTestsExecutionListener executionListener, TestContext testContext)
			throws Exception {
		
		IShoppingListRepository shoppingListRepositoryMock = getTestInstance().getShoppingListRepository();
		//BinaryQueryExpression findByNameExpresion = new BinaryQueryExpression("name", Operator.EQUALS, "Group1");
		JpaShoppingList mockShoppingList = new JpaShoppingList();
		mockShoppingList.setID(1L);
		mockShoppingList.setName("ShoppingList1");
		org.mockito.BDDMockito.given(shoppingListRepositoryMock.find(ArgumentMatchers.any(QueryExpression.class), ArgumentMatchers.eq(0), ArgumentMatchers.eq(1)))
			.willAnswer(new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList(mockShoppingList);
				}
			});
	}
}
