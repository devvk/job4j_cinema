--liquibase formatted sql

--changeset devvk:002_dml_insert_files
INSERT INTO files(name, path)
VALUES
    (
        'interstellar.jpg',
        'files/posters/interstellar.jpg'
    ),
    (
        'john_wick.jpg',
        'files/posters/john_wick.jpg'
    ),
    (
        'shrek.jpg',
        'files/posters/shrek.jpg'
    ),
    (
        'inception.jpg',
        'files/posters/inception.jpg'
    ),
    (
        'harry_potter.jpg',
        'files/posters/harry_potter.jpg'
    );

--rollback DELETE FROM files;
