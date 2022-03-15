package fp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/y h:m");

    private LocalDateTimeParser() {
        // Non instantiable static class
    }

    /**
     * Parsea la {@link LocalDateTime} pasada como argumento con fecha con formato {@code d/M/y} y hora con formato
     * {@code h:m}.
     *
     * @param date fecha a parsear, con formato {@code d/M/y}
     * @param time hora a parsear, con formato {@code h:m}
     * @return {@link LocalDateTime} parseada
     */
    public static LocalDateTime parse(String date, String time) {
        return parse(date + " " + time);
    }

    /**
     * Parsea la {@link LocalDateTime} pasada como argumento con formato {@code d/M/y h:m}.
     *
     * @param line {@link String} a parsear, con formato {@code d/M/y h:m}
     * @return {@link LocalDateTime} parseada
     */
    public static LocalDateTime parse(String line) {
        return LocalDateTime.parse(line, FORMATTER);
    }
}
