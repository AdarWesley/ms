package org.awesley.digital.usergroup.persistence.implementation.jpa.repositories;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.awesley.digital.usergroup.service.model.UserGroup;
import org.awesley.infra.query.QueryExpression;
import org.awesley.infra.query.jpa.QueryExpressionToJpaVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupJpaRepository extends CrudRepository<JpaUserGroup, Long>, JpaSpecificationExecutor<JpaUserGroup> {

	default List<? extends UserGroup> findByQueryExpression(QueryExpression expression, Integer startIndex, Integer pageSize){
		
		Specification<JpaUserGroup> querySpecification = new Specification<JpaUserGroup>() {

			@Override
			public Predicate toPredicate(Root<JpaUserGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return QueryExpressionToJpaVisitor.baseConvert(root, query, cb, expression);
			}
			
		};
		
		Page<JpaUserGroup> page = findAll(querySpecification, new PageRequest(startIndex / pageSize, pageSize));
		return page.getContent();
	}
}
