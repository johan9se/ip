package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    public static final String EVENT_MARKER = "E";

    protected LocalDate at;

    public Event(String description, LocalDate at) {
        super(description);
        this.at = at;
    }

    @Override
    public String formatString() {
        return " " + EVENT_MARKER + " " + super.formatString() + " | " + at;
    }

    @Override
    public String toString() {
        return "[" + EVENT_MARKER + "]" + super.toString() + " (at: " + formatDate(at) + ")";
    }

    public String formatDate(LocalDate dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
