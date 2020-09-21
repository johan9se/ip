package duke;

/**
 * Signals an error caused by invalid commands when adding new Tasks into the TaskList.
 */
public class DukeException extends Exception {
    //no other code needed
    public String command;

    public DukeException() {

    }

    public DukeException(String command){
        this.command = command;
    }
}