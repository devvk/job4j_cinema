package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    @MockitoBean
    private FilmSessionService filmSessionService;

    @Test
    void whenGetBuyFormWithExistingSessionThenReturnBuyForm() throws Exception {
        when(filmSessionService.findById(1)).thenReturn(Optional.of(new FilmSessionDto()));

        mockMvc.perform(get("/tickets/buy/1")
                        .sessionAttr("user", new User()))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/buyForm"))
                .andExpect(model().attributeExists("filmSession"))
                .andExpect(model().attributeExists("rows"))
                .andExpect(model().attributeExists("places"));
    }

    @Test
    void whenGetBuyFormWithNotExistingSessionThenReturn404Page() throws Exception {
        when(filmSessionService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tickets/buy/999")
                        .sessionAttr("user", new User()))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void whenBuyTicketWithoutUserThenRedirectToLoginPage() throws Exception {
        mockMvc.perform(post("/tickets/buy")
                        .param("sessionId", "1")
                        .param("rowNumber", "1")
                        .param("placeNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    void whenBuyTicketSuccessfullyThenRedirectToSuccessPage() throws Exception {
        when(ticketService.buy(any(Ticket.class))).thenReturn(Optional.of(new Ticket()));

        mockMvc.perform(post("/tickets/buy")
                        .param("sessionId", "1")
                        .param("rowNumber", "1")
                        .param("placeNumber", "1")
                        .sessionAttr("user", new User()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tickets/success"));
    }

    @Test
    void whenBuyExistingTicketThenRedirectToFailPage() throws Exception {
        when(ticketService.buy(any(Ticket.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/tickets/buy")
                        .param("sessionId", "1")
                        .param("rowNumber", "1")
                        .param("placeNumber", "1")
                        .sessionAttr("user", new User()))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void whenGetSuccessPageThenReturnSuccessView() throws Exception {
        mockMvc.perform(get("/tickets/success")
                        .sessionAttr("user", new User()))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/success"));
    }

}