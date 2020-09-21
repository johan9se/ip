package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    public static final String DEADLINE_MARKER = "D";

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public LocalDateTime getDateTime() {
        return by;
    }

    @Override
    public String formatString() {
        return " " + DEADLINE_MARKER + " " + super.formatString() + " | " + by;
    }

    @Override
    public String toString() {
        return "[" + DEADLINE_MARKER + "]" + super.toString() + " (by: " + formatDate(by) + ")";
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"));
    }
}
