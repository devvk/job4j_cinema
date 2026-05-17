package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.service.FilmSessionService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FilmSessionController.class)
class FilmSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FilmSessionService filmSessionService;

    @Test
    void whenGetSessionsThenReturnListPage() throws Exception {
        when(filmSessionService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/sessions"))
                .andExpect(status().isOk())
                .andExpect(view().name("sessions/list"))
                .andExpect(model().attributeExists("sessions"));
    }
}