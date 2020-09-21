import duke.Deadline;
import duke.Event;
import duke.ToDo;
import duke.Task;
import duke.DukeException;

import java.util.ArrayList;

/**
 * Represents the TaskList. Contains the list of Task objects.
 */
public class TaskList {

    protected static ArrayList<Task> taskList;
    protected static int itemsInList = 0;

    private static final Ui ui = new Ui();

    public TaskList() {
        taskList =  new ArrayList<>();
    }

    /**
     * Give access to the tasklist to the commands read by Parser.
     *
     * @param inputCommand  full line entered by user.
     */
    public void accessTaskList(String inputCommand) {
        Parser.parseCommand(inputCommand);
    }

    /**
     * Add newly input Task into the TaskList and
     * increment the number of items in the TaskList.
     *
     * @param item represents the Task object.
     */
    public static void addNewListItem(Task item) {
        taskList.add(item);
        itemsInList++;
    }

    /**
     * Add newly input Todo into the TaskList.
     *
     * @param args the description of the Todo object.
     * @param isNew indicates that the Task is a new input, not loaded from an existing file
     */
    public static void addNewTodo(String args, boolean isNew) {
        Task todo = new ToDo(args);
        addNewListItem(todo);
        if (isNew) {
            echoNewlyAddedItem(todo);
        }
    }

    /**
     * Add newly input Deadline into the TaskList.
     *
     * @param args the description of the Deadline object.
     * @param isNew indicates that the Task is a new input, not loaded from an existing file
     */
    public static void addNewDeadline(String args, boolean isNew) {
        try {
            String description = splitDescriptionAndDateTime(args)[0];
            String byDateTime = splitDescriptionAndDateTime(args)[1];
            Task deadline = new Deadline(description, byDateTime);
            addNewListItem(deadline);
            if (isNew) {
                echoNewlyAddedItem(deadline);
            }
        } catch (StringIndexOutOfBoundsException e) {
            ui.printErrorMessage(Ui.MISSING_DATETIME_MESSAGE, "deadline");
        } catch (DukeException e) {
            ui.printErrorMessage(Ui.MISSING_DETAILS_MESSAGE, "deadline");
        }
    }

    /**
     * Add newly input Event into the TaskList.
     *
     * @param args the description of the Event object.
     * @param isNew indicates that the Task is a new input, not loaded from an existing file
     */
    public static void addNewEvent(String args, boolean isNew) {
        try {
            String description = splitDescriptionAndDateTime(args)[0];
            String atDateTime = splitDescriptionAndDateTime(args)[1];
            Task event = new Event(description, atDateTime);
            addNewListItem(event);
            if (isNew) {
                echoNewlyAddedItem(event);
            }
        } catch (StringIndexOutOfBoundsException e) {
            ui.printErrorMessage(Ui.MISSING_DATETIME_MESSAGE, "event");
        } catch (DukeException e) {
            ui.printErrorMessage(Ui.MISSING_DETAILS_MESSAGE, "event");
        }
    }

    /**
     * Echo back the newly added item to the user.
     *
     * @param item represents the Task object.
     */
    public static void echoNewlyAddedItem(Task item) {
        System.out.println("\t Got it! I've added this task:");
        System.out.println("\t   " + item.toString());
        System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n" + Ui.LINE_BREAK +"\n", (itemsInList >1 ? "s":""));
    }

    /**
     * Delete Task from TaskList.
     *
     * @param listNumber numerical position of Task object on the TaskList.
     */
    public static void deleteItem(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            System.out.println("\t Okiedokie! This task has been removed:");
            System.out.println("\t   " + taskList.remove(taskID).toString());
            itemsInList--;
            System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n", (itemsInList ==1 ? "s":""));
            ui.printLineBreak();
        } else {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        }

    }

    /**
     * Separate the description and corresponding date/time attributed to a Task,
     * returned as an array.
     *
     * @param args the full user input, excluding the command
     */
    public static String[] splitDescriptionAndDateTime (String args) throws DukeException {
        String description = args.substring(0, args.indexOf("\\")).trim();
        String dateTime = args.substring(args.indexOf("\\")+3).trim();
        String[] details = {description, dateTime};
        if (description.isEmpty() || dateTime.isEmpty()) {
            throw new DukeException();
        }
        return details;
    }

    /**
     * List out and number all Tasks in the Tasklist.
     */
    public static void listItems() {
        if (itemsInList > 0) {
            System.out.println("\t Here are the tasks in your list:");
            for (int i = 0; i< itemsInList; i++) {
                System.out.printf("\t %d. %s\n", i+1, taskList.get(i).toString());
            }
        } else {
            System.out.println("\t Your list is empty! Start adding tasks now :)");
        }
        ui.printLineBreak();
    }

    /**
     * Mark a particular Task item as done.
     *
     * @param listNumber numerical position of Task object on the TaskList.
     */
    public static void markTaskAsDone(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            taskList.get(taskID).markAsDone();
        } else {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        }
    }

    /**
     * Acknowledge and display which Task item has been marked as done.
     *
     * @param listNumber numerical position of Task object on the TaskList.
     */
    public static void printDoneMessage(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t   " + taskList.get(taskID).toString());
        ui.printLineBreak();
    }
}
