package org.awesley.digital.shoppinglist.persistence.configuration;

import org.awesley.digital.shoppinglist.persistence.implementation.ShoppingListRepositoryImplementation;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaGroupRef;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaListItem;
import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.digital.shoppinglist.service.interfaces.repository.IShoppingListRepository;
import org.awesley.digital.shoppinglist.service.model.GroupRef;
import org.awesley.digital.shoppinglist.service.model.ListItem;
import org.awesley.digital.shoppinglist.service.model.ShoppingList;
import org.awesley.infra.query.jpa.QueryExpressionToJpaVisitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableJpaRepositories(basePackages = { "org.awesley.digital.shoppinglist.persistence.implementation.jpa.repositories" })
@EntityScan(basePackages = { "org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities" })
@ComponentScan(basePackageClasses = { QueryExpressionToJpaVisitor.class })
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
	
	@Bean
	@Scope("prototype")
	public ListItem listItem() {
		return new JpaListItem();
	}
	
	@Bean
	@Scope("prototype")
	public GroupRef groupRef() {
		return new JpaGroupRef();
	}
}
