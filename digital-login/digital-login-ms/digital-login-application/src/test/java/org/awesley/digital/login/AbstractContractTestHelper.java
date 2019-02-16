package org.awesley.digital.login;

import java.util.Map;

import org.springframework.test.context.TestContext;

public abstract class AbstractContractTestHelper implements ContractTestHelper {

	@Override
	public void beforeTestClass(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception {
		/* no-op */
	}

	@Override
	public void prepareTestInstance(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception {
		/* no-op */
	}

	@Override
	public void beforeTestMethod(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception {
		/* no-op */
	}

	@Override
	public void afterTestMethod(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception {
		/* no-op */
	}

	@Override
	public void afterTestClass(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception {
		/* no-op */
	}
	
	@Override
	public void doValidate(Map<String, Object> response) throws Exception {
		/* no-op */
	}
}
