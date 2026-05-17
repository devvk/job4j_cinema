package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleUserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new SimpleUserService(userRepository);
    private final User user = new User(1, "user", "user@mail.com", "password");

    @Test
    void whenRegisterUserThenReturnOptionalUser() {
        when(userRepository.save(user)).thenReturn(Optional.of(user));

        var result = userService.register(user);

        assertThat(result).isPresent();
        assertThat(result).contains(user);
    }

    @Test
    void whenRegisterExistingUserThenReturnEmptyOptional() {
        when(userRepository.save(user))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty());

        var user1Result = userService.register(user);
        var user2Result = userService.register(user);

        assertThat(user1Result).isPresent();
        assertThat(user1Result).contains(user);
        assertThat(user2Result).isEmpty();
    }

    @Test
    void whenFindByEmailAndPasswordExistingUserThenReturnOptionalUser() {
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));
        var result = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(result).isPresent();
        assertThat(result).contains(user);
    }

    @Test
    void whenFindByEmailAndPasswordNotExistingUserThenReturnEmptyOptional() {
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.empty());
        var result = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(result).isEmpty();

    }
}