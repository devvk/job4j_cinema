--liquibase formatted sql

--changeset devvk:009_ddl_create_film_sessions_table
CREATE TABLE film_sessions
(
    id         SERIAL PRIMARY KEY,
    film_id    INT REFERENCES films (id) NOT NULL,
    halls_id   INT REFERENCES halls (id) NOT NULL,
    start_time TIMESTAMP                 NOT NULL,
    end_time   TIMESTAMP                 NOT NULL,
    price      INT                       NOT NULL
);

--rollback DROP TABLE film_sessions;
