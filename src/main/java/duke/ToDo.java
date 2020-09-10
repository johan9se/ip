package duke;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String formatString() {
        return " T " + super.formatString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
