package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FileRepository;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmServiceTest {

    private final FilmRepository filmRepository = mock(FilmRepository.class);
    private final GenreRepository genreRepository = mock(GenreRepository.class);
    private final FileRepository fileRepository = mock(FileRepository.class);
    private final FilmService filmService = new SimpleFilmService(filmRepository, genreRepository, fileRepository);

    @Test
    void whenFindAllFilmsThenReturnAllFilms() {
        var film1Dto = createFirstFilmDto();
        var film2Dto = createSecondFilmDto();
        var expectedFilmDtoList = List.of(film1Dto, film2Dto);

        var film1 = createFirstFilm();
        var film2 = createSecondFilm();
        var genre1 = new Genre(1, "Fight");
        var genre2 = new Genre(2, "Action");
        var file1 = new File(1, "film1.jpg", "path/to/film1.jpg");
        var file2 = new File(2, "film2.jpg", "path/to/film2.jpg");

        when(filmRepository.findAll()).thenReturn(List.of(film1, film2));
        when(genreRepository.findById(film1.getGenreId())).thenReturn(Optional.of(genre1));
        when(genreRepository.findById(film2.getGenreId())).thenReturn(Optional.of(genre2));
        when(fileRepository.findById(film1.getFileId())).thenReturn(Optional.of(file1));
        when(fileRepository.findById(film2.getFileId())).thenReturn(Optional.of(file2));

        var result = filmService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(expectedFilmDtoList);
    }

    @Test
    void whenFindByIdExistingFilmThenReturnFilm() {
        var filmDto = createFirstFilmDto();
        var film = createFirstFilm();
        var genre1 = new Genre(1, "Fight");
        var file1 = new File(1, "film1.jpg", "path/to/film1.jpg");

        when(filmRepository.findById(film.getId())).thenReturn(Optional.of(film));
        when(genreRepository.findById(film.getGenreId())).thenReturn(Optional.of(genre1));
        when(fileRepository.findById(film.getFileId())).thenReturn(Optional.of(file1));

        var result = filmService.findById(film.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(filmDto);
    }

    @Test
    void whenFindByIdNotExistingFilmThenReturnEmptyOptional() {
        int filmId = 1;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());
        var result = filmService.findById(filmId);
        assertThat(result).isEmpty();
    }

    private FilmDto createFirstFilmDto() {
        var filmDto = new FilmDto();
        filmDto.setId(1);
        filmDto.setName("Film 1");
        filmDto.setDescription("Film 1");
        filmDto.setYear(2018);
        filmDto.setMinimalAge(16);
        filmDto.setDurationInMinutes(120);
        filmDto.setGenreName("Fight");
        filmDto.setFilePath("path/to/film1.jpg");
        return filmDto;
    }

    private FilmDto createSecondFilmDto() {
        var filmDto = new FilmDto();
        filmDto.setId(2);
        filmDto.setName("Film 2");
        filmDto.setDescription("Film 2");
        filmDto.setYear(2022);
        filmDto.setMinimalAge(12);
        filmDto.setDurationInMinutes(130);
        filmDto.setGenreName("Action");
        filmDto.setFilePath("path/to/film2.jpg");
        return filmDto;
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