package org.awesley.digital.shoppinglist.persistence.implementation.jpa.repositories;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.awesley.digital.shoppinglist.persistence.implementation.jpa.entities.JpaShoppingList;
import org.awesley.infra.query.QueryExpression;
import org.awesley.infra.query.jpa.QueryExpressionToJpaVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingListJpaRepository extends CrudRepository<JpaShoppingList, Long>, JpaSpecificationExecutor<JpaShoppingList> {

	default List<? extends JpaShoppingList> findByQueryExpression(QueryExpression expression, Integer startIndex, Integer pageSize){
		
		Specification<JpaShoppingList> querySpecification = new Specification<JpaShoppingList>() {

			@Override
			public Predicate toPredicate(Root<JpaShoppingList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return QueryExpressionToJpaVisitor.baseConvert(root, query, cb, expression);
			}
			
		};
		
		Page<JpaShoppingList> page = findAll(querySpecification, new PageRequest(startIndex / pageSize, pageSize));
		return page.getContent();
	}
}
