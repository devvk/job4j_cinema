package ru.job4j.cinema.repository.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.SqlExceptionUtils;

import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oTicketRepository.class);

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (Connection connection = sql2o.open()) {
            var sql = """
                    INSERT INTO tickets (session_id, row_number, place_number, user_id)
                    VALUES(:sessionId, :rowNumber, :placeNumber, :userId);
                    """;
            var query = connection.createQuery(sql, true);
            query.addParameter("sessionId", ticket.getSessionId());
            query.addParameter("rowNumber", ticket.getRowNumber());
            query.addParameter("placeNumber", ticket.getPlaceNumber());
            query.addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Sql2oException e) {
            if (SqlExceptionUtils.isUniqueConstraintViolation(e)) {
                LOG.warn(
                        "Ticket for session {}, row {}, place {} already exists",
                        ticket.getSessionId(),
                        ticket.getRowNumber(),
                        ticket.getPlaceNumber()
                );
                return Optional.empty();
            }
            LOG.error("Error while saving ticket", e);
            throw e;
        }
    }
}
