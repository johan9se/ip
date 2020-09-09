package duke;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void markAsDone() {
        this.isDone = true;
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.printf("\t   [%s] %s\n", this.getStatusIcon(), this.description);
        System.out.println("\t_________________________________");
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] "+ this.description;
    }
}