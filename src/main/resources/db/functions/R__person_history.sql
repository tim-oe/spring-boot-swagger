CREATE OR REPLACE DEFINER=`root`@`localhost` TRIGGER `person_after_insert` AFTER INSERT ON person FOR EACH ROW
    INSERT INTO person_history (
    `op`,
    `person_id`,
    `email`,
    `first_name`,
    `last_name`
    )
  VALUES (
    'I',  -- I For Insert.
    NEW.`id`,
    NEW.`email`,
    NEW.`first_name`,
    NEW.`last_name`
    );
;

CREATE OR REPLACE DEFINER=`root`@`localhost` TRIGGER `person_after_update` AFTER UPDATE ON person FOR EACH ROW
    INSERT INTO person_history (
    `op`,
    `person_id`,
    `email`,
    `first_name`,
    `last_name`
    )
  VALUES (
    'U',  -- U For Update
    NEW.`id`,
    NEW.`email`,
    NEW.`first_name`,
    NEW.`last_name`
    );
;

CREATE OR REPLACE DEFINER=`root`@`localhost` TRIGGER `person_after_delete` AFTER DELETE ON person FOR EACH ROW
    INSERT INTO person_history (
    `op`,
    `person_id`,
    `email`,
    `first_name`,
    `last_name`
    )
  VALUES (
    'D',  -- D For delete
    OLD.`id`,
    OLD.`email`,
    OLD.`first_name`,
    OLD.`last_name`
    );
;