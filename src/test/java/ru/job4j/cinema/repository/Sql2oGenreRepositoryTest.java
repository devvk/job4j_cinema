package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.cinema.model.Genre;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Sql2oGenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void whenFindByIdThenReturnGenre() {
        int genreId = 1;
        Optional<Genre> genre = genreRepository.findById(genreId);
        assertThat(genre).isPresent().get().extracting(Genre::getId).isEqualTo(genreId);
    }

    @Test
    void whenFindByIdThenReturnEmptyOptional() {
        Optional<Genre> genre = genreRepository.findById(999);
        assertThat(genre).isEmpty();
    }

    @Test
    void whenFindAllThenReturnAllGenres() {
        Collection<Genre> genres = genreRepository.findAll();
        assertThat(genres.size()).isGreaterThan(0);
    }
}