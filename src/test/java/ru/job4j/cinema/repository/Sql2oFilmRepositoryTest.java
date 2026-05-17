package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Sql2oFilmRepositoryTest {

    @Autowired
    private Sql2oFilmRepository filmRepository;

    @Test
    void whenFindByIdThenReturnExistingFilm() {
        int filmId = 1;
        Optional<Film> film = filmRepository.findById(1);
        assertThat(film).isPresent().get().extracting(Film::getId).isEqualTo(filmId);
    }

    @Test
    void whenFindByIdThenReturnEmptyOptional() {
        Optional<Film> film = filmRepository.findById(999);
        assertThat(film).isEmpty();
    }

    @Test
    void whenFindAllThenReturnAllFilms() {
        Collection<Film> films = filmRepository.findAll();
        assertThat(films.size()).isGreaterThan(0);
    }
}