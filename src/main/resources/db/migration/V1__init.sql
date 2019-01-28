CREATE TABLE `person` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL COMMENT 'email address',
  `first_name` varchar(30) DEFAULT NULL COMMENT 'first name',
  `last_name` varchar(50) DEFAULT NULL COMMENT 'last name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
