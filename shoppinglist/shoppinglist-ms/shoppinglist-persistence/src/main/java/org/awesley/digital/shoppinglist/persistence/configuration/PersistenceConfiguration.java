package org.awesley.digital.shoppinglist.persistence.configuration;

import org.awesley.digital.shoppinglist.persistence.implementation.ShoppingListRepositoryImplementation;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableJpaRepositories(basePackages = { "org.awesley.digital.shoppinglist.persistence.implementation.jpa.repositories" })
@EntityScan(basePackages = { "org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities" })
public class PersistenceConfiguration {

	@Bean
	public IShoppingListRepository shoppingListRepository() {
		return new ShoppingListRepositoryImplementation();
	}
	
	@Bean
	@Scope("prototype")
	public ShoppingList shoppingList() {
		return new JpaShoppingList();
	}
}
