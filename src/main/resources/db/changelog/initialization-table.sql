SET search_path TO ${database.defaultSchemaName};

CREATE TABLE users
(
    id  SERIAL primary key,
    username VARCHAR(75) unique not null,
    email    VARCHAR(255) unique not null,
    password VARCHAR(255)       not null,
    role     VARCHAR(50)        not null,
    refresh_token text
);

CREATE SEQUENCE user_id_seq
    START WITH 2
    INCREMENT BY 1
    MINVALUE 2
    NO CYCLE