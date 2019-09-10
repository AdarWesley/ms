package org.awesley.digital.usergroup.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;

@SpringBootApplication(
	scanBasePackageClasses = {
			AppLayersConfiguration.class,
			CxfAppConfiguration.class
		}, 
	exclude = { XADataSourceAutoConfiguration.class, ErrorMvcAutoConfiguration.class })
public class CxfServiceSpringBootApplication {

	public static void main(String[] args) {
        SpringApplication.run(CxfServiceSpringBootApplication.class, args);
    }
	
}
