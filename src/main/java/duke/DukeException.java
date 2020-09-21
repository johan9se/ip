package duke;

public class DukeException extends Exception {
    public String command;

    public DukeException() {}

    public DukeException(String command){
        this.command = command;
    }
}