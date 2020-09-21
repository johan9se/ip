package duke.parser;

import duke.data.TaskList;
import duke.data.exception.DukeException;
import duke.data.exception.InvalidTimeFrameException;
import duke.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Parses user input.
 */
public class Parser {
    private static final String COMMAND_EXIT_WORD = "bye";
    private static final String COMMAND_DEADLINE_WORD = "deadline";
    private static final String COMMAND_DELETE_WORD = "delete";
    private static final String COMMAND_DONE_WORD = "done";
    private static final String COMMAND_EVENT_WORD = "event";
    private static final String COMMAND_FIND_WORD = "find";
    private static final String COMMAND_HELP_WORD = "help";
    private static final String COMMAND_LIST_WORD = "list";
    private static final String COMMAND_TODO_WORD = "todo";
    private static final String COMMAND_UPCOMING_WORD = "upcoming";

    private static final String DAY_TIMEFRAME = "day";
    private static final String WEEK_TIMEFRAME = "week";
    private static final String MONTH_TIMEFRAME = "month";

    private static final Ui ui = new Ui();

    /**
     * Parses user input into command and executes it accordingly.
     *
     * @param userInput full user input string
     */
    public static void parseCommand(String userInput) {
        try {
            final String[] commandAndParams = splitCommandWordAndArgs(userInput);
            final String command = commandAndParams[0];
            final String commandArgs = commandAndParams[1];

            switch (command) {
            case COMMAND_TODO_WORD:
                TaskList.addNewTodo(commandArgs, true);
                break;
            case COMMAND_DEADLINE_WORD:
                TaskList.addNewDeadline(commandArgs, true);
                break;
            case COMMAND_EVENT_WORD:
                TaskList.addNewEvent(commandArgs, true);
                break;
            case COMMAND_LIST_WORD:
                TaskList.listAllItems();
                break;
            case COMMAND_UPCOMING_WORD:
                TaskList.listUpcomingTasks(commandArgs);
                break;
            case COMMAND_FIND_WORD:
                TaskList.findAndListTasks(commandArgs);
                break;
            case COMMAND_DONE_WORD:
                TaskList.markTaskAsDone(commandArgs);
                TaskList.printDoneMessage(commandArgs);
                break;
            case COMMAND_DELETE_WORD:
                TaskList.deleteItem(commandArgs);
                break;
            case COMMAND_HELP_WORD:
                ui.printGuideMessage();
                break;
            case COMMAND_EXIT_WORD:
                ui.printGoodbyeAndExit();
                break;
            default:
                ui.printErrorMessage(Ui.DONT_UNDERSTAND_MESSAGE);
                break;
            }
        } catch (NumberFormatException | NullPointerException e) {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        } catch (DukeException e) {
            ui.printErrorMessage(Ui.INVALID_COMMAND_MESSAGE, e.command);
        } catch (DateTimeParseException e) {
            ui.printErrorMessage(Ui.INVALID_DATETIME_FORMAT);
        }
    }

    /**
     * Separate the command word from the full input line.
     *
     * @param rawUserInput full line entered by the user.
     * @return array containing the command word, and the rest of the input line.
     */
    public static String[] splitCommandWordAndArgs (String rawUserInput) throws DukeException {
        final String[] split = rawUserInput.trim().split(" ", 2);
        if (split.length == 1 && (split[0].matches("todo|deadline|event"))) {
            throw new DukeException(split[0]);
        }
        return split.length == 2 ? split : new String[] { split[0] , "" };
    }

    /**
     * Separate the description and corresponding date/time attributed to a Task,
     * returned as an array.
     *
     * @param args the full user input, excluding the command
     */
    public static String[] splitDescriptionAndDateTime (String args) throws DukeException {
        String description = args.substring(0, args.indexOf("\\")).trim();
        String dateTimeString = formatDateAndTimeInput(args.substring(args.indexOf("\\")+3).trim());
        String[] details = {description, dateTimeString};
        if (description.isEmpty() || dateTimeString.isEmpty()) {
            throw new DukeException();
        }
        return details;
    }

    /**
     * Convert input date/time string into a format that can
     * be parsed into a LocalDateTime object.
     */
    public static String formatDateAndTimeInput(String dateTimeString) {
        String[] args = dateTimeString.split(",", 2);
        if (args.length > 1) {
            String date = args[0].trim();
            String time = args[1].trim();
            return date + "T" + time;
        }
        return dateTimeString;
    }

    public static LocalDateTime getDateTimeDescription(String inputDateTime) {
        return LocalDateTime.parse(inputDateTime);
    }

    /**
     * Get the relevant LocalDateTime values for a particular input time frame.
     *
     * @param timeFrame String input indicating date/time range
     */
    public static LocalDateTime[] getStartAndEndDate(String timeFrame) throws InvalidTimeFrameException {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = null;
        switch (timeFrame) {
        case DAY_TIMEFRAME:
            endDate = startDate.plus(1, ChronoUnit.DAYS);
            break;
        case WEEK_TIMEFRAME:
            endDate = startDate.plus(1, ChronoUnit.WEEKS);
            break;
        case MONTH_TIMEFRAME:
            endDate = startDate.plus(1, ChronoUnit.MONTHS);
            break;
        default:
            throw new InvalidTimeFrameException(timeFrame);
        }
        return new LocalDateTime[] {startDate, endDate};
    }
}
