--liquibase formatted sql

--changeset devvk:001_ddl_create_files_table
CREATE TABLE files
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    path VARCHAR NOT NULL UNIQUE
);

--rollback DROP TABLE files;
