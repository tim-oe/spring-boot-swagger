CREATE OR REPLACE DEFINER=root@localhost TRIGGER person_after_insert AFTER INSERT ON person FOR EACH ROW
    INSERT INTO person_history (
    op,
    person_id,
    email,
    first_name,
    last_name,
    pwd_hash,
    enabled,
    locked
    )
  VALUES (
    'I',  -- I For Insert.
    NEW.id,
    NEW.email,
    NEW.first_name,
    NEW.last_name,
    NEW.pwd_hash,
    NEW.enabled,
    NEW.locked
    );
;

CREATE OR REPLACE DEFINER=root@localhost TRIGGER person_after_update AFTER UPDATE ON person FOR EACH ROW
    INSERT INTO person_history (
    op,
    person_id,
    email,
    first_name,
    last_name,
    pwd_hash,
    enabled,
    locked
    )
  VALUES (
    'U',  -- U For Update
    NEW.id,
    NEW.email,
    NEW.first_name,
    NEW.last_name,
    NEW.pwd_hash,
    NEW.enabled,
    NEW.locked
    );
;

CREATE OR REPLACE DEFINER=root@localhost TRIGGER person_after_delete AFTER DELETE ON person FOR EACH ROW
    INSERT INTO person_history (
    op,
    person_id,
    email,
    first_name,
    last_name,
    pwd_hash,
    enabled,
    locked
    )
  VALUES (
    'D',  -- D For delete
    OLD.id,
    OLD.email,
    OLD.first_name,
    OLD.last_name,
    OLD.pwd_hash,
    OLD.enabled,
    OLD.locked
    );
;