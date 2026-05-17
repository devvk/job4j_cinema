package ru.job4j.cinema.service.film;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.file.FileRepository;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.genre.GenreRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final FileRepository fileRepository;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository, FileRepository fileRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        Collection<FilmDto> result = new ArrayList<>();
        var allFilms = filmRepository.findAll();
        for (Film film : allFilms) {
            var genreOptional = genreRepository.findById(film.getGenreId());
            var fileOptional = fileRepository.findById(film.getFileId());
            result.add(buildFilmDto(film, genreOptional, fileOptional));
        }
        return result;
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        var genreOptional = genreRepository.findById(filmOptional.get().getGenreId());
        var fileOptional = fileRepository.findById(filmOptional.get().getFileId());
        return Optional.of(buildFilmDto(filmOptional.get(), genreOptional, fileOptional));
    }

    private FilmDto buildFilmDto(Film film, Optional<Genre> genreOptional, Optional<File> fileOptional) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setYear(film.getYear());
        filmDto.setMinimalAge(film.getMinimalAge());
        filmDto.setDurationInMinutes(film.getDurationInMinutes());
        filmDto.setGenreName(genreOptional.map(Genre::getName).orElse(null));
        filmDto.setFilePath(fileOptional.map(File::getPath).orElse(null));
        return filmDto;
    }
}
