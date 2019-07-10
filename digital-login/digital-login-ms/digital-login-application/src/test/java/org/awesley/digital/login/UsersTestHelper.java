package org.awesley.digital.login;

import java.util.Arrays;

import javax.inject.Provider;

import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaAuthority;
import org.awesley.infra.contracttesting.AbstractContractTestHelper;
import org.awesley.infra.contracttesting.ContractTestsExecutionListener;
import org.awesley.infra.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContext;

public class UsersTestHelper extends AbstractContractTestHelper<ContractTestsBase> {

	@Autowired
	private Provider<JwtTokenUtil> jwtTokenUtil;

	@Override
	public void beforeTestMethod(ContractTestsExecutionListener executionListener, TestContext testContext) throws Exception {
		switch (testContext.getTestMethod().getName()) {
		case "validate_shouldChangePassword":
			((ContractTestsBase)testContext.getTestInstance())
				.setAuthenticatedIdentityToken(jwtTokenUtil.get().generateToken("AdminUser", Arrays.asList(new JpaAuthority("ROLE_ADMIN"))));
			break;

		case "validate_shouldCreateUser":
			((ContractTestsBase)testContext.getTestInstance())
				.setAuthenticatedIdentityToken(jwtTokenUtil.get().generateToken("AdminUser", Arrays.asList(new JpaAuthority("ROLE_ADMIN"))));
			break;
			
		default:
			break;
		}
	}
}
