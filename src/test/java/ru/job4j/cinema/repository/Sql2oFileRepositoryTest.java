package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.file.FileRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Sql2oFileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Test
    void whenFindExistingFileThenReturnFile() {
        int fileId = 1;
        Optional<File> file = fileRepository.findById(fileId);
        assertThat(file).isPresent().get().extracting(File::getId).isEqualTo(fileId);
    }

    @Test
    void whenFindMissingFileThenReturnEmptyOptional() {
        Optional<File> file = fileRepository.findById(999);
        assertThat(file).isEmpty();
    }
}