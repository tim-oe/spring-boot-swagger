package org.tec.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * https://www.baeldung.com/jpa-java-time
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "login_attempt")
public class LoginAttempt {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id", updatable=false, nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="user_name", updatable=false, nullable=false)
    private String userName;

    @Column(name="ip_addr", updatable=false)
    private String ipAddress;

    @Column(name="is_deleted", nullable=false)
    private boolean isDeleted;

    @Column(name="create_on", insertable =false, updatable=false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime createdOn;
}
