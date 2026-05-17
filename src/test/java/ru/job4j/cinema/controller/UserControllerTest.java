package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.user.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void whenGetRegisterThenReturnRegisterPage() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"));
    }

    @Test
    void whenPostRegisterWithExistingEmailThenReturnError() throws Exception {
        when(userService.register(any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("users/register"));
    }

    @Test
    void whenPostRegisterSuccessThenRedirectToSessionsPage() throws Exception {
        when(userService.register(any(User.class))).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/users/register"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sessions"));
    }

    @Test
    void whenGetLoginThenReturnLoginPage() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    void whenPostLoginWithWrongCredentialsThenReturnError() throws Exception {
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("users/login"));
    }

    @Test
    void whenPostLoginSuccessThenRedirectToSessionsPage() throws Exception {
        var user = new User();
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/users/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("user", user))
                .andExpect(redirectedUrl("/sessions"));

    }

    @Test
    void whenGetLogoutThenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get("/users/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }
}