package org.awesley.digital.usergroup.persistence.implementation.jpa.repositories;

import org.awesley.digital.usergroup.persistence.implementation.jpa.entities.JpaUserGroup;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupJpaRepository extends CrudRepository<JpaUserGroup, Long> {

}
