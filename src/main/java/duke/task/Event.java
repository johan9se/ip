package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event-type Task in the duke.data.TaskList.
 * Consists of a description and a particular scheduled date/time.
 */
public class Event extends Task {
    public static final String EVENT_MARKER = "E";

    protected LocalDateTime at;

    public Event(String description, LocalDateTime at) {
        super(description);
        this.at = at;
    }

    @Override
    public LocalDateTime getDateTime() {
        return at;
    }

    @Override
    public String formatString() {
        return " " + EVENT_MARKER + " " + super.formatString() + " | " + at;
    }

    @Override
    public String toString() {
        return "[" + EVENT_MARKER + "]" + super.toString() + " (at: " + formatDate(at) + ")";
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"));
    }

    public String formatDateWithFullMonth(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMMM d yyyy, HH:mm"));
    }

    @Override
    public boolean contains(String keyword) {
        return super.contains(keyword)
                || at.toString().toLowerCase().contains(keyword)
                || formatDateWithFullMonth(at).toLowerCase().contains(keyword);
    }
}