package org.tec.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tec.auth.entity.OAuthUser;

import java.util.Optional;

@Repository()
public interface OAuthUserRepository extends CrudRepository<OAuthUser, Long> {

    Optional<OAuthUser> findByUsername(String username);
}