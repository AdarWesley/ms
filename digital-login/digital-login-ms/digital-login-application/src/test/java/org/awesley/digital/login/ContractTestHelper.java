package org.awesley.digital.login;

import java.util.Map;

import org.springframework.test.context.TestContext;

public interface ContractTestHelper {
	void beforeTestClass(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception;
	void prepareTestInstance(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception;
	void beforeTestMethod(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception;
	void afterTestMethod(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception;
	void afterTestClass(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception;
	void doValidate(Map<String, Object> response) throws Exception;
}
