package org.awesley.digital.usergroup.persistence;

import static org.junit.Assert.*;

import org.awesley.digital.usergroup.persistence.configuration.PersistenceConfiguration;
import org.awesley.digital.usergroup.persistence.implementation.jpa.repositories.UserGroupJpaRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
		"spring.datasource.url=jdbc:mysql://localhost:3306/usergroup?useSSL=false&allowPublicKeyRetrieval=true"
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
	private UserGroupJpaRepository userGroupJpaRepository;
	
	@Test
	public void canQueryDatabase() {
		long count = userGroupJpaRepository.count();
	}
}
