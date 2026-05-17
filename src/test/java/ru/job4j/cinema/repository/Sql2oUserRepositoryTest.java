package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Sql2oUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Sql2o sql2o;

    @BeforeEach
    void clearUsers() {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("TRUNCATE users").executeUpdate();
        }
    }

    @Test
    void whenSaveUserThenReturnSavedUser() {
        var newUser = new User(1, "name", "email", "password");
        var userOptional = userRepository.save(newUser);
        assertThat(userOptional).isPresent().get().extracting(User::getId).isEqualTo(newUser.getId());

    }

    @Test
    void whenSaveExistingUserThenReturnEmptyOptional() {
        var user1 = new User(1, "name", "email", "password");
        var user2 = new User(1, "name", "email", "password");
        userRepository.save(user1);
        var user2Optional = userRepository.save(user2);
        assertThat(user2Optional).isEmpty();

    }

    @Test
    void whenFindByEmailAndPasswordExistingUserThenReturnUser() {
        var newUser = new User(1, "name", "email", "password");
        userRepository.save(newUser);
        var userOptional = userRepository.findByEmailAndPassword(newUser.getEmail(), newUser.getPassword());
        assertThat(userOptional).isPresent().get().extracting(User::getId).isEqualTo(newUser.getId());
    }

    @Test
    void whenFindByEmailAndPasswordNotExistingUserThenReturnEmptyOptional() {
        var newUser = new User(1, "name", "email", "password");
        var userOptional = userRepository.findByEmailAndPassword(newUser.getEmail(), newUser.getPassword());
        assertThat(userOptional).isEmpty();
    }

    @Test
    void whenFindByIdExistingUserThenReturnUser() {
        var newUser = new User(1, "name", "email", "password");
        userRepository.save(newUser);
        var userOptional = userRepository.findById(newUser.getId());
        assertThat(userOptional).isPresent().get().extracting(User::getId).isEqualTo(newUser.getId());
    }

    @Test
    void whenFindByIdNotExistingUserThenReturnEmptyOptional() {
        var newUser = new User(1, "name", "email", "password");
        var userOptional = userRepository.findById(newUser.getId());
        assertThat(userOptional).isEmpty();
    }
}