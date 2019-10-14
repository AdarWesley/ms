package org.awesley.digital.usergroup;

import java.util.Arrays;

import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.infra.contracttesting.AbstractContractTestHelper;
import org.awesley.infra.contracttesting.ContractTestsExecutionListener;
import org.awesley.infra.query.QueryExpression;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.TestContext;

public class UserGroupTestHelper extends AbstractContractTestHelper<ContractTestsBase> {

	@Override
	public void beforeTestMethod(ContractTestsExecutionListener executionListener, TestContext testContext)
			throws Exception {
		
		IUserGroupRepository groupRepositoryMock = getTestInstance().getUserGroupRepository();
		//BinaryQueryExpression findByNameExpresion = new BinaryQueryExpression("name", Operator.EQUALS, "Group1");
		JpaUserGroup mockGroup = new JpaUserGroup();
		mockGroup.setID(1L);
		mockGroup.setName("Group1");
		org.mockito.BDDMockito.given(groupRepositoryMock.find(ArgumentMatchers.any(QueryExpression.class), ArgumentMatchers.eq(0), ArgumentMatchers.eq(1)))
			.willAnswer(new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) throws Throwable {
					return Arrays.asList(mockGroup);
				}
			});
	}
}
