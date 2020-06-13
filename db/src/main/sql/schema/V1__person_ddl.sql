CREATE TABLE person
(
    id         int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    email      varchar(100)     NOT NULL COMMENT 'email address',
    pwd_hash   varchar(128)     NOT NULL COMMENT 'password hash',
    first_name varchar(30) DEFAULT NULL COMMENT 'first name',
    last_name  varchar(50) DEFAULT NULL COMMENT 'last name',
    zipcode    varchar(5)  DEFAULT NULL COMMENT 'zipcode location',
    enabled    BOOLEAN     DEFAULT TRUE COMMENT 'whether person is enabled',
    locked     BOOLEAN     DEFAULT FALSE COMMENT 'whether account is locked',
    PRIMARY KEY (id),
    UNIQUE KEY email_idx (email)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- history table to help track changes
CREATE TABLE person_history
(
    id         int(11) UNSIGNED   NOT NULL AUTO_INCREMENT,
    op         ENUM ('I','U','D') NOT NULL COMMENT 'the operation type insert Update or delete',
    person_id  int(11) UNSIGNED   NOT NULL COMMENT 'linkage to person table',
    email      varchar(100)       NOT NULL,
    pwd_hash   varchar(512)       NOT NULL,
    first_name varchar(30)                 DEFAULT NULL,
    last_name  varchar(50)                 DEFAULT NULL,
    enabled    BOOLEAN     DEFAULT TRUE COMMENT 'whether person is enabled',
    locked     BOOLEAN     DEFAULT FALSE COMMENT 'whether account is locked',
    created    timestamp          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY person_id_idx (person_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE role
(
    id         tinyint(3) unsigned NOT NULL COMMENT 'unique identifier',
    short_desc varchar(32)         NOT NULL COMMENT 'the short description tied to the enum name',
    long_desc  varchar(256) DEFAULT NULL COMMENT 'detailed long description used for querying',
    PRIMARY KEY (id),
    UNIQUE KEY name_UNIQUE (short_desc)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE person_role
(
    id        int(11) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT 'unique identifier',
    person_id int(11) UNSIGNED    NOT NULL COMMENT 'linkage to the user',
    role_id   tinyint(3) unsigned NOT NULL COMMENT 'linkage to the role',
    PRIMARY KEY (id),
    KEY person_idx (person_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;