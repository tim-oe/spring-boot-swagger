package org.tec.auth.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tec.auth.entity.LoginAttempt;

/**
 * https://www.baeldung.com/spring-data-jpa-query-by-date
 * https://stackoverflow.com/questions/10696490/does-spring-data-jpa-have-any-way-to-count-entites-using-method-name-resolving/27458590
 */
@Repository()
public interface LoginAttemptRepository extends CrudRepository<LoginAttempt, Long> {
    int MAX_ATTEMPTS = 3;
    int MAX_MINUTES = 2;

    @Query(value="select count(*) from login_attempt where is_deleted=false and user_name=? and TIMESTAMPDIFF(MINUTE,created_on,CURRENT_TIMESTAMP)<=?", nativeQuery = true)
    int countAttempts(String userName, int minutes);

    //looking at jpa impl it is using the spring transactional annotation
    @Transactional
    // https://www.baeldung.com/spring-data-jpa-modifying-annotation
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update LoginAttempt set isDeleted=true where isDeleted=false and userName=:userName")
    void clearUserName(String userName);
}
