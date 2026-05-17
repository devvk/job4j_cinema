--liquibase formatted sql

--changeset devvk:003_ddl_create_genres_table
CREATE TABLE genres
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

--rollback DROP TABLE genres;
