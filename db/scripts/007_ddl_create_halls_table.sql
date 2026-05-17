--liquibase formatted sql

--changeset devvk:007_ddl_create_halls_table
CREATE TABLE halls
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    row_count   INT     NOT NULL,
    place_count INT     NOT NULL,
    description VARCHAR NOT NULL
);

--rollback DROP TABLE halls;
