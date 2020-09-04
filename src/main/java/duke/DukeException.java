package duke;

public class DukeException extends Exception {
    //no other code needed
    public String command;

    public DukeException() {

    }

    public DukeException(String command){
        this.command = command;
    }
}