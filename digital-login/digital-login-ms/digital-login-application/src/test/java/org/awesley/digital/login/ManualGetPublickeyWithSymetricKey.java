package org.awesley.digital.login;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.ForbiddenException;

import org.awesley.digital.login.config.TestConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(
	classes = { 
		TestConfiguration.class
		//, CxfServiceSpringBootApplication.class 
		// , ContractTestsBase.LocalTransportConfiguration.class 
	}, 
	properties = {
		"jwt.secret=anysymetrickeysecret"
	}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManualGetPublickeyWithSymetricKey extends ContractTestsBase {

	@Value("${jwt.base64EncodedValidationKey}")
	private String expectedPublicKey;
	
	@Test(expected = ForbiddenException.class)
	public void validate_shouldThrowExceptionWhenConfiguredKeyIsSymetric() throws Exception {
		// given:
			RequestSpecification request = given()
					.header("Content-Type", "application/json");

		// when:
			@SuppressWarnings("unused")
			Response response = given().spec(request).get("/publickey");
	}

}
