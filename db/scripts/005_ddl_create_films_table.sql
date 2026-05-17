--liquibase formatted sql

--changeset devvk:005_ddl_create_films_table
CREATE TABLE films
(
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR                    NOT NULL,
    description         VARCHAR                    NOT NULL,
    "year"              INT                        NOT NULL,
    genre_id            INT REFERENCES genres (id) NOT NULL,
    minimal_age         INT                        NOT NULL,
    duration_in_minutes INT                        NOT NULL,
    file_id             INT REFERENCES files (id)  NOT NULL
);

--rollback DROP TABLE films;
