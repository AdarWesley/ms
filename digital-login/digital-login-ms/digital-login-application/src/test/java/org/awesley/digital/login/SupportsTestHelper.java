package org.awesley.digital.login;

public interface SupportsTestHelper<BASETESTType> {
	ContractTestHelper<BASETESTType> getContractTestHelper();
	void setContractTestsHelper(ContractTestHelper<BASETESTType> contractTestHelper);
}
