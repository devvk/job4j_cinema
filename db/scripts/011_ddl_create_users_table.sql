--liquibase formatted sql

--changeset devvk:011_ddl_create_users_table
CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR NOT NULL,
    email     VARCHAR UNIQUE NOT NULL,
    password  VARCHAR NOT NULL
);

--rollback DROP TABLE users;

