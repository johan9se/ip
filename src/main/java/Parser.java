import duke.DukeException;

import java.time.LocalDate;

public class Parser {
    private static final String COMMAND_LIST_WORD = "list";
    private static final String COMMAND_TODO_WORD = "todo";
    private static final String COMMAND_DEADLINE_WORD = "deadline";
    private static final String COMMAND_EVENT_WORD = "event";
    private static final String COMMAND_DELETE_WORD = "delete";
    private static final String COMMAND_DONE_WORD = "done";
    private static final String COMMAND_EXIT_WORD = "bye";

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
                TaskList.listItems();
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
        } catch (NumberFormatException | NullPointerException e) {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        } catch (DukeException e) {
            ui.printErrorMessage(Ui.INVALID_COMMAND_MESSAGE, e.command);
        }
    }

    public static String[] splitCommandWordAndArgs (String rawUserInput) throws DukeException {
        final String[] split = rawUserInput.trim().split(" ", 2);
        if (split[0].matches("todo|deadline|event") && split.length == 1) {
            throw new DukeException(split[0]);
        }
        return split.length == 2 ? split : new String[] { split[0] , "" };
    }

    public static String[] splitDescriptionAndDateTime (String args) throws DukeException {
        String description = args.substring(0, args.indexOf("\\")).trim();
        String dateTimeString = args.substring(args.indexOf("\\")+3).trim();
        String[] details = {description, dateTimeString};
        if (description.isEmpty() || dateTimeString.isEmpty()) {
            throw new DukeException();
        }
        return details;
    }

    public static LocalDate getDateTimeDescription(String inputDate) {
        return LocalDate.parse(inputDate);
    }
}
