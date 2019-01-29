CREATE TABLE `person` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL COMMENT 'email address',
  `first_name` varchar(30) DEFAULT NULL COMMENT 'first name',
  `last_name` varchar(50) DEFAULT NULL COMMENT 'last name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_idx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- history table to help track changes
CREATE TABLE `person_history` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `op` ENUM('I','U','D') NOT NULL COMMENT 'the operation type Isert Update or delete',
  `person_id` int(11) UNSIGNED NOT NULL COMMENT 'linkage to person table',
  `email` varchar(100) NOT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
   KEY `person_id_idx` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
