package org.awesley.digital.shoppinglist.persistence;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@OverrideAutoConfiguration(enabled = false)
@ComponentScan()
@ImportAutoConfiguration(value = {
	DataSourceAutoConfiguration.class
	,JpaRepositoriesAutoConfiguration.class
	,HibernateJpaAutoConfiguration.class
	,JdbcTemplateAutoConfiguration.class
 })
public class PersistenceTestConfiguration {
}
