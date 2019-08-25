package org.awesley.digital.shoppinglist.persistence.implementation.jpa.repositories;

import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingListJpaRepository extends CrudRepository<JpaShoppingList, Long> {

}
