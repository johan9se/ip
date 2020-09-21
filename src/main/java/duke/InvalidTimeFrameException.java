package duke;

public class InvalidTimeFrameException extends Exception{
    public String timeframe;

    public InvalidTimeFrameException(String inputTimeframe){
        this.timeframe = inputTimeframe;
    }
}
