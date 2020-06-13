CREATE TABLE oauth_access_token
(
    token_id          varchar(32) NOT NULL,
    token             blob,
    authentication_id varchar(32) NOT NULL,
    user_name         varchar(100)         DEFAULT NULL,
    client_id         varchar(32)          DEFAULT NULL,
    authentication    blob,
    refresh_token     varchar(32)          DEFAULT NULL,
    last_updated      timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (token_id),
    UNIQUE KEY authentication_id_unq (authentication_id),
    KEY user_name_idx (user_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE oauth_refresh_token
(
    token_id       varchar(32) NOT NULL,
    token          blob,
    authentication blob,
    last_updated   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (token_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS oauth_client_details
(
    CLIENT_ID               VARCHAR(32) PRIMARY KEY,
    RESOURCE_IDS            VARCHAR(255),
    CLIENT_SECRET           VARCHAR(255),
    SCOPE                   VARCHAR(255),
    AUTHORIZED_GRANT_TYPES  VARCHAR(255),
    WEB_SERVER_REDIRECT_URI VARCHAR(255),
    AUTHORITIES             VARCHAR(255),
    ACCESS_TOKEN_VALIDITY   INTEGER,
    REFRESH_TOKEN_VALIDITY  INTEGER,
    ADDITIONAL_INFORMATION  VARCHAR(4096),
    AUTOAPPROVE             VARCHAR(255)
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8;

CREATE TABLE login_attempt
(
    id         bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    user_name  varchar(128)        NOT NULL comment 'the user name trying to log in',
    ip_addr    varchar(64)         NOT NULL comment 'the client ip address',
    is_deleted BOOLEAN             NOT NULL DEFAULT FALSE,
    created_on timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `user_name_KEY` (`user_name`),
    KEY `created_on_KEY` (`created_on`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='for tracking login attempt data';;

