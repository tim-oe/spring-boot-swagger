package org.tec.springbootswagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.tec.springbootswagger.entity.PersonEntity;

/**
 * jpa repo for person
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    PersonEntity findByEmail(String email);

    //https://stackoverflow.com/questions/32258857/spring-boot-data-jpa-modifying-update-query-refresh-persistence-context
    @Modifying(flushAutomatically=true,clearAutomatically=true)
    @Query(value = "update person set first_name = ? where id=?", nativeQuery = true)
    int updateFirstName(String firstName, Long personId);

    @Modifying(flushAutomatically=true,clearAutomatically=true)
    @Query(value = "update person set last_name = ? where id=?", nativeQuery = true)
    int updateLastName(String lastName, Long personId);

    @Modifying(flushAutomatically=true,clearAutomatically=true)
    @Query(value = "delete from person where email=?", nativeQuery = true)
    int deleteByEmail(String email);
}
