package duke;

public class Deadline extends Task {
    public static final String DEADLINE_MARKER = "D";

    protected String description;
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String formatString() {
        return " " + DEADLINE_MARKER + " " + super.formatString() + " | " + by;
    }

    @Override
    public String toString() {
        return "[" + DEADLINE_MARKER + "]" + super.toString() + " (by: " + by + ")";
    }
}
