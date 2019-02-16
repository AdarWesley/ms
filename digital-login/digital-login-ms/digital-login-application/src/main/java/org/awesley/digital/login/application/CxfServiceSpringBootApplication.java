package org.awesley.digital.login.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;

@SpringBootApplication(
	scanBasePackageClasses = {
			AppLayersConfiguration.class,
			CxfAppConfiguration.class
		}, 
	exclude = { XADataSourceAutoConfiguration.class })
public class CxfServiceSpringBootApplication {

	public static void main(String[] args) {
        SpringApplication.run(CxfServiceSpringBootApplication.class, args);
    }
	
}
