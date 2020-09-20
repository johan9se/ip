package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    public static final String DEADLINE_MARKER = "D";

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String formatString() {
        return " " + DEADLINE_MARKER + " " + super.formatString() + " | " + by;
    }

    @Override
    public String toString() {
        return "[" + DEADLINE_MARKER + "]" + super.toString() + " (by: " + formatDate(by) + ")";
    }

    public String formatDate(LocalDate dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
