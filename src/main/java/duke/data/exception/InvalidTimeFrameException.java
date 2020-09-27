package duke.data.exception;

/**
 * Signals an error caused by invalid input time frame keyword
 * when adding a new Deadline or Event into the TaskList.
 */
public class InvalidTimeFrameException extends Exception{
    public String timeframe;

    public InvalidTimeFrameException(String inputTimeframe){
        this.timeframe = inputTimeframe;
    }
}
