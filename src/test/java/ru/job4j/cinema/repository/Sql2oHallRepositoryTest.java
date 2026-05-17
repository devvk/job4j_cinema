package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.cinema.model.Hall;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Sql2oHallRepositoryTest {

    @Autowired
    private Sql2oHallRepository sql2oHallRepository;

    @Test
    void whenFindByIdThenReturnHall() {
        int hallId = 1;
        Optional<Hall> hall = sql2oHallRepository.findById(hallId);
        assertThat(hall).isPresent().get().extracting(Hall::getId).isEqualTo(hallId);
    }
}