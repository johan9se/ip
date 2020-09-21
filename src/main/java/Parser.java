import duke.DukeException;
import duke.InvalidTimeFrameException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Parser {
    private static final String COMMAND_EXIT_WORD = "bye";
    private static final String COMMAND_DEADLINE_WORD = "deadline";
    private static final String COMMAND_DELETE_WORD = "delete";
    private static final String COMMAND_DONE_WORD = "done";
    private static final String COMMAND_EVENT_WORD = "event";
    private static final String COMMAND_FIND_WORD = "find";
    private static final String COMMAND_LIST_WORD = "list";
    private static final String COMMAND_TODO_WORD = "todo";
    private static final String COMMAND_UPCOMING_WORD = "upcoming";

    private static final String DAY_TIMEFRAME = "day";
    private static final String WEEK_TIMEFRAME = "week";
    private static final String MONTH_TIMEFRAME = "month";

    private static final Ui ui = new Ui();

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
            case COMMAND_EXIT_WORD:
                ui.printGoodbye();
                System.exit(0);
                break;
            default:
                ui.printErrorMessage(Ui.DONT_UNDERSTAND_MESSAGE);
                ui.printGuideMessage();
                break;
            }
        } catch (NumberFormatException | NullPointerException | InvalidTimeFrameException e) {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        } catch (DukeException e) {
            ui.printErrorMessage(Ui.INVALID_COMMAND_MESSAGE, e.command);
        } catch (DateTimeParseException e) {
            ui.printErrorMessage(Ui.INVALID_DATETIME_FORMAT);
        }
    }

    public static String[] splitCommandWordAndArgs (String rawUserInput) throws DukeException, InvalidTimeFrameException {
        final String[] split = rawUserInput.trim().split(" ", 2);
        if (split.length == 1) {
            if (split[0].matches("todo|deadline|event")) {
                throw new DukeException(split[0]);
            } else if (split[0].matches("upcoming")) {
                throw new InvalidTimeFrameException();
            }
        }
        return split.length == 2 ? split : new String[] { split[0] , "" };
    }

    public static String[] splitDescriptionAndDateTime (String args) throws DukeException {
        String description = args.substring(0, args.indexOf("\\")).trim();
        String dateTimeString = formatDateAndTimeInput(args.substring(args.indexOf("\\")+3).trim());
        String[] details = {description, dateTimeString};
        if (description.isEmpty() || dateTimeString.isEmpty()) {
            throw new DukeException();
        }
        return details;
    }

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
        return new LocalDateTime[]{startDate, endDate};
    }
}
