package org.awesley.digital.login.persistence;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@OverrideAutoConfiguration(enabled = false)
@ComponentScan()
@ImportAutoConfiguration(value = {
	DataSourceAutoConfiguration.class
	,JpaRepositoriesAutoConfiguration.class
	,HibernateJpaAutoConfiguration.class
	,JdbcTemplateAutoConfiguration.class
 })
public class PersistenceTestConfiguration {
	/*@Bean
	public DataSource dataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		return ds;
	}*/
}
