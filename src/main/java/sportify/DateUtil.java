package sportify;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private DateUtil(){}

    /** The date pattern that is used for conversion.*/
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /** Returns the given date as a well formatted String. The above defined*/
    public static String format(LocalDate date) {
        String s;
        if (date == null) {
            return null;
        }
        s = DATE_FORMATTER.format(date);
        return s;
    }

    /** Converts a String in the format of the defined DATE_PATTERN to a LocalDate object.*/
    public static LocalDate parse(String dateString) {
        LocalDate value;
        try {
            value = DATE_FORMATTER.parse(dateString, LocalDate::from);
        }
        catch (DateTimeParseException e) {
            value = null;
        }
        return value;
    }
}
