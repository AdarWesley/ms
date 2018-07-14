package org.awesley.digital.login.persistence.implementation.jpa.repositories;

import org.awesley.digital.login.persistence.implementation.jpa.entities.JpaUser;
import org.awesley.digital.login.service.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<JpaUser, Long> {

	User findByUsername(String username);

}
