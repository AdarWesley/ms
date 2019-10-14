package org.awesley.digital.usergroup.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.awesley.digital.usergroup.persistence.configuration.PersistenceConfiguration;
import org.awesley.digital.usergroup.persistence.implementation.jpa.repositories.UserGroupJpaRepository;
import org.awesley.digital.usergroup.service.interfaces.IUserGroupRepository;
import org.awesley.digital.usergroup.service.model.UserGroup;
import org.awesley.infra.query.BinaryQueryExpression;
import org.awesley.infra.query.Operator;
import org.awesley.infra.query.QueryExpression;
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
	
	@Autowired
	private IUserGroupRepository userGroupRepository;
	
	@Test
	public void canQueryDatabase() {
		long count = userGroupJpaRepository.count();
	}
	
	@Test
	public void canFindUserGroupByQueryExpression() {
		QueryExpression findExpression = new BinaryQueryExpression("name", Operator.EQUALS, "Group1");
		
		List<? extends UserGroup> groups = userGroupRepository.find(findExpression, 0, 1);
		
		assertNotNull(groups);
		assertEquals(1, groups.size());
	}
}
