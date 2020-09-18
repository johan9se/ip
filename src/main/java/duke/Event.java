package duke;

public class Event extends Task {
    public static final String EVENT_MARKER = "E";

    protected String description;
    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String formatString() {
        return " " + EVENT_MARKER + " " + super.formatString() + " | " + at;
    }

    @Override
    public String toString() {
        return "[" + EVENT_MARKER + "]" + super.toString() + " (at: " + at + ")";
    }
}
