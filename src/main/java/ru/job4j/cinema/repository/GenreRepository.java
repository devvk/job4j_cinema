package ru.job4j.cinema.repository;

import java.util.Collection;
import java.util.Optional;

public interface GenreRepository {

    Optional<GenreRepository> findById(int id);

    Collection<GenreRepository> findAll();
}
