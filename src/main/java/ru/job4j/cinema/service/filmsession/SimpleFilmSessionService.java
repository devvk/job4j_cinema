package ru.job4j.cinema.service.filmsession;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.file.FileRepository;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.filmsession.FilmSessionRepository;
import ru.job4j.cinema.repository.hall.HallRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;
    private final FileRepository fileRepository;
    private final FilmSessionRepository filmSessionRepository;

    public SimpleFilmSessionService(FilmRepository filmRepository,
                                    HallRepository hallRepository,
                                    FileRepository fileRepository,
                                    FilmSessionRepository filmSessionRepository) {
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
        this.fileRepository = fileRepository;
        this.filmSessionRepository = filmSessionRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        Collection<FilmSessionDto> result = new ArrayList<>();
        var allFilmSessions = filmSessionRepository.findAll();
        for (FilmSession filmSession : allFilmSessions) {
            buildFilmSessionDto(filmSession).ifPresent(result::add);
        }
        return result;
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        return filmSessionRepository.findById(id).flatMap(this::buildFilmSessionDto);
    }

    private Optional<FilmSessionDto> buildFilmSessionDto(FilmSession filmSession) {
        var filmOptional = filmRepository.findById(filmSession.getFilmId());
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        var film = filmOptional.get();
        var hallOptional = hallRepository.findById(filmSession.getHallsId());
        var fileOptional = fileRepository.findById(film.getFileId());
        if (hallOptional.isEmpty() || fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var hall = hallOptional.get();
        var file = fileOptional.get();
        return Optional.of(new FilmSessionDto(
                filmSession.getId(),
                film.getName(),
                hall.getName(),
                file.getPath(),
                filmSession.getStartTime(),
                filmSession.getEndTime(),
                filmSession.getPrice()
        ));
    }
}
