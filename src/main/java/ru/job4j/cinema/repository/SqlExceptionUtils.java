package ru.job4j.cinema.repository;

import java.sql.SQLException;

public final class SqlExceptionUtils {

    private SqlExceptionUtils() {
    }

    public static boolean isUniqueConstraintViolation(Throwable exception) {
        Throwable current = exception;
        while (current != null) {
            if (current instanceof SQLException sqlException
                    && "23505".equals(sqlException.getSQLState())) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
