package ru.job4j.cinema.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.ticket.TicketRepository;
import ru.job4j.cinema.service.ticket.SimpleTicketService;
import ru.job4j.cinema.service.ticket.TicketService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleTicketServiceTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final TicketService ticketService = new SimpleTicketService(ticketRepository);
    private final Ticket ticket = new Ticket(1, 1, 1, 1, 1);

    @Test
    void whenBuyTicketThenReturnOptionalTicket() {
        when(ticketRepository.save(ticket)).thenReturn(Optional.of(ticket));

        var result = ticketService.buy(ticket);

        assertThat(result).isPresent();
        assertThat(result).contains(ticket);
    }

    @Test
    void whenBuyExistingTicketThenReturnEmptyOptional() {
        when(ticketRepository.save(ticket)).thenReturn(Optional.of(ticket))
                .thenReturn(Optional.empty());

        var result1 = ticketService.buy(ticket);
        var result2 = ticketService.buy(ticket);

        assertThat(result1).isPresent();
        assertThat(result1).contains(ticket);
        assertThat(result2).isEmpty();
    }
}