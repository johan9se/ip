package task;

/**
 * Represents a Todo-type Task in the TaskList
 */
public class ToDo extends Task {
    public static final String TODO_MARKER = "T";

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String formatString() {
        return " " + TODO_MARKER + " " + super.formatString();
    }

    @Override
    public String toString() {
        return "[" + TODO_MARKER + "]" + super.toString();
    }
}
