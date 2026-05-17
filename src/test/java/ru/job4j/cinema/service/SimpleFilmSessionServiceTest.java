package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.FileRepository;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmSessionServiceTest {

    private final FilmSessionRepository filmSessionRepository = mock(FilmSessionRepository.class);
    private final FilmRepository filmRepository = mock(FilmRepository.class);
    private final HallRepository hallRepository = mock(HallRepository.class);
    private final FileRepository fileRepository = mock(FileRepository.class);
    private final FilmSessionService filmSessionService = new SimpleFilmSessionService(filmRepository,
            hallRepository,
            fileRepository,
            filmSessionRepository);

    @Test
    void whenFindAllFilmSessionsThenReturnAllFilmSessions() {
        var filmSession1Dto = createFirstFilmSessionDto();
        var filmSession2Dto = createSecondFilmSessionDto();
        var expectedFilmSessionDtoList = List.of(filmSession1Dto, filmSession2Dto);

        var filmSession1 = createFirstFilmSession();
        var filmSession2 = createSecondFilmSession();
        var film1 = createFirstFilm();
        var film2 = createSecondFilm();
        var hall1 = new Hall(1, "hall1", 10, 10, "hall1");
        var hall2 = new Hall(2, "hall2", 10, 10, "hall2");
        var file1 = new File(1, "film1.jpg", "path/to/film1.jpg");
        var file2 = new File(2, "film2.jpg", "path/to/film2.jpg");

        when(filmSessionRepository.findAll()).thenReturn(List.of(filmSession1, filmSession2));
        when(filmRepository.findById(film1.getId())).thenReturn(Optional.of(film1));
        when(filmRepository.findById(film2.getId())).thenReturn(Optional.of(film2));
        when(hallRepository.findById(hall1.getId())).thenReturn(Optional.of(hall1));
        when(hallRepository.findById(hall2.getId())).thenReturn(Optional.of(hall2));
        when(fileRepository.findById(file1.getId())).thenReturn(Optional.of(file1));
        when(fileRepository.findById(file2.getId())).thenReturn(Optional.of(file2));

        var result = filmSessionService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(expectedFilmSessionDtoList);
    }

    @Test
    void whenFindByIdExistingFilmSessionThenReturnFilmSession() {
        var filmSessionDto = createFirstFilmSessionDto();
        var filmSession = createFirstFilmSession();
        var film = createFirstFilm();
        var hall = new Hall(1, "hall1", 10, 10, "hall1");
        var file = new File(1, "film1.jpg", "path/to/film1.jpg");
        when(filmSessionRepository.findById(filmSession.getId())).thenReturn(Optional.of(filmSession));
        when(filmRepository.findById(film.getId())).thenReturn(Optional.of(film));
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));
        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));

        var result = filmSessionService.findById(filmSession.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(filmSessionDto);

    }

    @Test
    void whenFindByIdNotExistingFilmSessionThenReturnEmptyFilmSession() {
        int filmSessionId = 1;
        when(filmSessionRepository.findById(filmSessionId)).thenReturn(Optional.empty());
        var result = filmSessionService.findById(filmSessionId);
        assertThat(result).isEmpty();
    }

    private FilmSessionDto createFirstFilmSessionDto() {
        var filmSessionDto = new FilmSessionDto();
        filmSessionDto.setId(1);
        filmSessionDto.setFilmName("Film 1");
        filmSessionDto.setFilePath("path/to/film1.jpg");
        filmSessionDto.setHallName("hall1");
        filmSessionDto.setStartTime(LocalDateTime.of(2026, 5, 20, 18, 30));
        filmSessionDto.setEndTime(LocalDateTime.of(2026, 5, 20, 20, 45));
        filmSessionDto.setPrice(10);
        return filmSessionDto;

    }

    private FilmSessionDto createSecondFilmSessionDto() {
        var filmSessionDto = new FilmSessionDto();
        filmSessionDto.setId(2);
        filmSessionDto.setFilmName("Film 2");
        filmSessionDto.setFilePath("path/to/film2.jpg");
        filmSessionDto.setHallName("hall2");
        filmSessionDto.setStartTime(LocalDateTime.of(2026, 5, 21, 18, 30));
        filmSessionDto.setEndTime(LocalDateTime.of(2026, 5, 21, 20, 45));
        filmSessionDto.setPrice(15);
        return filmSessionDto;
    }

    private FilmSession createFirstFilmSession() {
        var filmSession = new FilmSession();
        filmSession.setId(1);
        filmSession.setFilmId(1);
        filmSession.setHallsId(1);
        filmSession.setStartTime(LocalDateTime.of(2026, 5, 20, 18, 30));
        filmSession.setEndTime(LocalDateTime.of(2026, 5, 20, 20, 45));
        filmSession.setPrice(10);
        return filmSession;
    }

    private FilmSession createSecondFilmSession() {
        var filmSession = new FilmSession();
        filmSession.setId(2);
        filmSession.setFilmId(2);
        filmSession.setHallsId(2);
        filmSession.setStartTime(LocalDateTime.of(2026, 5, 21, 18, 30));
        filmSession.setEndTime(LocalDateTime.of(2026, 5, 21, 20, 45));
        filmSession.setPrice(15);
        return filmSession;
    }

    private Film createFirstFilm() {
        var film = new Film();
        film.setId(1);
        film.setName("Film 1");
        film.setDescription("Film 1");
        film.setYear(2018);
        film.setMinimalAge(16);
        film.setDurationInMinutes(120);
        film.setGenreId(1);
        film.setFileId(1);
        return film;
    }

    private Film createSecondFilm() {
        var film = new Film();
        film.setId(2);
        film.setName("Film 2");
        film.setDescription("Film 2");
        film.setYear(2022);
        film.setMinimalAge(12);
        film.setDurationInMinutes(130);
        film.setGenreId(2);
        film.setFileId(2);
        return film;
    }
}
