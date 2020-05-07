-- maiadb 10.4
-- CREATE USER toad IDENTIFIED BY 'toad';
-- GRANT ALL PRIVILEGES ON *.* TO 'toad'@'%' WITH GRANT OPTION;
-- FLUSH PRIVILEGES;
-- select user,host from user;

-- insert Roles
INSERT INTO role( id, short_desc, long_desc)
VALUES ( 0, 'ANONYMOUS', 'has not been authenticated or is publicly available');;

INSERT INTO role( id, short_desc, long_desc)
VALUES ( 10, 'PERSON', 'a user that has person access');;

INSERT INTO role( id, short_desc, long_desc)
VALUES ( 20, 'SUPPORT ', 'a support person');;

INSERT INTO role( id, short_desc, long_desc)
VALUES ( 30, 'ADMIN ', 'application administrator');;

INSERT INTO role( id, short_desc, long_desc)
VALUES ( 255, 'SUPER_USER ', 'application god');;

-- insert people
INSERT INTO person
    (email, first_name, last_name, pwd_hash)
VALUES ('user1@example.net', 'john', 'doe',
        '$2a$10$fOq4pHK.7.AWG2biKuTLB.FsKvvOJsNRBVrnuIUocUFlx.Oirc2pu');;

INSERT INTO person_role( person_id,role_id) VALUES (
(select id from person where email = 'user1@example.net'),
(select id from role where short_desc='PERSON'));;

INSERT INTO person
    (email, first_name, last_name, pwd_hash)
VALUES ('user2@example.net', 'jane', 'smith',
        '$2a$10$lqpOeL1tnCoKsvXxSqrkw.mgk3Aph2WYvkDRF9usgI4/G.isM6.Ly');;

INSERT INTO person_role( person_id,role_id) VALUES (
(select id from person where email = 'user2@example.net'),
(select id from role where short_desc='SUPPORT'));;

INSERT INTO person
    (email, first_name, last_name, pwd_hash)
VALUES ('user3@example.net', 'harry', 'jones',
        '$2a$10$QxZueqotzW2T.GjxtfpmZeozKnwXuHwOtcAqdEKsngLLEwD5/qq3W');;

INSERT INTO person_role( person_id,role_id) VALUES (
(select id from person where email = 'user3@example.net'),
(select id from role where short_desc='ADMIN'));;

INSERT INTO person
(email, first_name, last_name, pwd_hash)
VALUES ('owner@example.net', 'harry', 'jones',
        '$2a$10$x7gdDR5Eh9BpJkNAgIa3DubBVEzdxMCCjIVp2YX7PIp1PV15.ZL1K');;

INSERT INTO person_role( person_id,role_id) VALUES (
(select id from person where email = 'owner@example.net'),
(select id from role where short_desc='SUPER_USER'));;