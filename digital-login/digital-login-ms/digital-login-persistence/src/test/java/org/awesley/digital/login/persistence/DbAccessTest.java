package org.awesley.digital.login.persistence;

import static org.junit.Assert.*;

import org.awesley.digital.login.persistence.configuration.PersistenceConfiguration;
import org.awesley.digital.login.persistence.implementation.jpa.repositories.UserJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		PersistenceTestConfiguration.class
		,PersistenceConfiguration.class	
	},
	properties = {
		"spring.datasource.url=jdbc:mysql://localhost:3306/digitallogin?useSSL=false&allowPublicKeyRetrieval=true"
		,"spring.datasource.username=root"
		,"spring.datasource.password=adarRada"			
		,"spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver"
		,"spring.jpa.generate-ddl=true"
		,"spring.jpa.hibernate.ddl-auto=create"
		,"spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect"
		,"spring.jpa.properties.javax.persistence.validation.mode=none"
	}
)
public class DbAccessTest {

	@Autowired
	private UserJpaRepository userJpaRepository;
	
	@Test
	public void canQueryDatabase() {
		long count = userJpaRepository.count();
	}
}
