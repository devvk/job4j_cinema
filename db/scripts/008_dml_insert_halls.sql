--liquibase formatted sql

--changeset devvk:008_dml_insert_halls
INSERT INTO halls(name, row_count, place_count, description)
VALUES
    (
        'Красный зал',
        10,
        20,
        'Большой зал с экраном IMAX'
    ),
    (
        'Синий зал',
        8,
        15,
        'Уютный зал для вечерних сеансов'
    ),
    (
        'VIP зал',
        5,
        10,
        'Зал повышенного комфорта'
    );

--rollback DELETE FROM halls;
