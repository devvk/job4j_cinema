package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Sql2oFilmSessionRepositoryTest {

    @Autowired
    private FilmSessionRepository filmSessionRepository;

    @Test
    void whenFindByIdThenReturnFilmSession() {
        int filmSessionId = 1;
        Optional<FilmSession> filmSession = filmSessionRepository.findById(filmSessionId);
        assertThat(filmSession).isPresent().get().extracting(FilmSession::getId).isEqualTo(filmSessionId);
    }

    @Test
    void whenFindByIdThenReturnEmptyOptional() {
        Optional<FilmSession> filmSession = filmSessionRepository.findById(999);
        assertThat(filmSession).isEmpty();
    }

    @Test
    void whenFindAllThenReturnAllFilms() {
        Collection<FilmSession> filmSessions = filmSessionRepository.findAll();
        assertThat(filmSessions.size()).isGreaterThan(0);
    }
}