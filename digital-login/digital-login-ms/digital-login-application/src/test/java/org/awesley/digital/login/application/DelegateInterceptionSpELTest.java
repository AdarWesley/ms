package org.awesley.digital.login.application;

import static org.junit.Assert.*;

import org.awesley.digital.msf.applicativecontext.DefaultErrorMessageFormatter;
import org.awesley.digital.msf.applicativecontext.IErrorMessageFormatter;
import org.awesley.digital.msf.applicativecontext.SpELErrorMessageFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CxfServiceSpringBootApplication.class, DelegateInterceptionSpELTest.DelegateInterceptionSpELConfiguration.class 
		}) //, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DelegateInterceptionSpELTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	@Configuration
	static class DelegateInterceptionSpELConfiguration {
		
		@Bean
		@Primary
		public IErrorMessageFormatter errorMessageFormatter() {
			return new SpELErrorMessageFormatter();
		}

	}
}
