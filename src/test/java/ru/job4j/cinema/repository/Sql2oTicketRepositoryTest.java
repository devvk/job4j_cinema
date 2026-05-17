package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class Sql2oTicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private Sql2o sql2o;

    @BeforeEach
    void clearTickets() {
        try (var connection = sql2o.open()) {
            connection.createQuery("TRUNCATE tickets;").executeUpdate();
        }
    }

    @Test
    void whenSaveTicketThenReturnSavedTicket() {
        var newTicket = new Ticket(1, 1, 1, 1, 1);
        Optional<Ticket> ticketOptional = ticketRepository.save(newTicket);
        assertThat(ticketOptional).isPresent().get().extracting(Ticket::getId).isEqualTo(newTicket.getId());
    }

    @Test
    void whenSaveExistingTicketThenReturnEmptyOptional() {
        var ticket1 = new Ticket(1, 1, 1, 1, 1);
        var ticket2 = new Ticket(1, 1, 1, 1, 1);
        ticketRepository.save(ticket1);
        Optional<Ticket> existingTicketOptional = ticketRepository.save(ticket2);
        assertThat(existingTicketOptional).isEmpty();

    }
}