package org.awesley.digital.login.persistence.implementation.jpa.repositories;

import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaAuthority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityJpaRepository extends CrudRepository<JpaAuthority, Long> {

}
