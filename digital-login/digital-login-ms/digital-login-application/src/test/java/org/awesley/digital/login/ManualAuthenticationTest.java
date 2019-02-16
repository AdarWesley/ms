package org.awesley.digital.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ManualAuthenticationTest extends ContractTestsBase {

	@Test
	public void validate_shouldAuthenticateProperCredentialsAndReturnJWT() throws Exception {
		// given:
			RequestSpecification request = given()
					.header("Content-Type", "application/json")
					.body("{\"username\":\"TestUser\",\"password\":\"password\"}");

		// when:
			Response response = given().spec(request)
					.post("/authenticate");

			Response res = response.then().log().all().and().extract().response();
			System.out.println("response="+res.toString());
			
		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).isEqualTo("application/json");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
		// and:
			assertThat(parsedJson.read("$.['token']", String.class)).matches("[^.]+.[^.]+.[^.]+");
	}

}
