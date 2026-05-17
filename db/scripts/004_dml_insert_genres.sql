--liquibase formatted sql

--changeset devvk:004_dml_insert_genres
INSERT INTO genres(name)
VALUES
    ('Боевик'),
    ('Комедия'),
    ('Драма'),
    ('Фантастика'),
    ('Триллер'),
    ('Ужасы'),
    ('Мультфильм'),
    ('Приключения'),
    ('Детектив'),
    ('Фэнтези');

--rollback DELETE FROM genres;
