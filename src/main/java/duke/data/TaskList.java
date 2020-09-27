package duke.data;

import duke.data.exception.DukeException;
import duke.data.exception.InvalidTimeFrameException;
import duke.parser.Parser;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.ui.Ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the TaskList. Contains the list of Task objects.
 */
public class TaskList {

    protected static ArrayList<Task> taskList;
    protected static int itemsInList = 0;

    private static final Ui ui = new Ui();

    public TaskList() {
        taskList = new ArrayList<>();
    }

    /**
     * Give access to the tasklist to the commands read by duke.parser.Parser.
     *
     * @param inputCommand  full line entered by user.
     */
    public void accessTaskList(String inputCommand) {
        Parser.parseCommand(inputCommand);
    }


    /*
     * ===========================================
     *           ADDING TO TASKLIST
     * ===========================================
     */

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
            String description = Parser.splitDescriptionAndDateTime(args)[0];
            String byDateTimeString = Parser.formatDateAndTimeInput(Parser.splitDescriptionAndDateTime(args)[1]);
            LocalDateTime byDateTime = Parser.getDateTime(byDateTimeString);

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
            String[] details = Parser.splitDescriptionAndDateTime(args);
            String description = details[0];
            String[] dateTimeRange = Parser.splitStartAndEndDateTime(details[1]);
            String startDateTimeString = dateTimeRange[0];
            String endDateTimeString = dateTimeRange[1];

            LocalDateTime startDateTime = Parser.getDateTime(startDateTimeString);
            LocalDateTime endDateTime = Parser.getDateTime(endDateTimeString);

            Task event = new Event(description, startDateTime, endDateTime);
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
        System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n" + Ui.LINE_BREAK + "\n", (itemsInList > 1 ? "s" : ""));
    }

    /*
     * ===========================================
     *          DELETING FROM TASKLIST
     * ===========================================
     */

    /**
     * Delete Task from TaskList.
     *
     * @param listNumber numerical position of Task object on the duke.data.TaskList.
     */
    public static void deleteItem(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            System.out.println("\t Okiedokie! This duke.task has been removed:");
            System.out.println("\t   " + taskList.remove(taskID).toString());
            itemsInList--;
            System.out.printf("\t Now you have " + itemsInList + " duke.task%s in the list.\n", (itemsInList == 1 ? "s" : ""));
            ui.printLineBreak();
        } else {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        }
    }


    /*
     * ===========================================
     *        LISTING CONTENTS OF TASKLIST
     * ===========================================
     */

    /**
     * List out and number all Tasks in the Tasklist.
     */
    public static void listAllItems() {
        if (itemsInList > 0) {
            System.out.println("\t Here are the tasks in your list:");
            for (int i = 0; i < itemsInList; i++) {
                System.out.printf("\t %d. %s\n", i + 1, taskList.get(i).toString());
            }
        } else {
            System.out.println("\t Your list is empty! Start adding tasks now :)");
        }
        ui.printLineBreak();
    }

    /**
     * List out and number all Tasks in the Tasklist which contain a particular keyword.
     *
     * @param searchString user input keyword to search for
     */
    public static void findAndListTasks(String searchString) {
        if (!searchString.isEmpty()) {
            System.out.println("\t These tasks contain the keyword(s): '" + searchString + "'");
            AtomicInteger i = new AtomicInteger(1);

            taskList.stream().filter((t) -> t.contains(searchString))
                    .forEach((t) -> System.out.println("\t " + i.getAndIncrement() + ". " + t.toString()));

            if (i.get() == 1) {
                System.out.println("\t No tasks containing '" + searchString + "' are found!");
            }
        } else {
            ui.printErrorMessage(Ui.MISSING_DETAILS_MESSAGE, "search query");
        }
        ui.printLineBreak();
    }

    /**
     * List out and number upcoming Tasks within a given time frame.
     *
     * @param timeFrame user input timeframe
     */
    public static void listUpcomingTasks(String timeFrame) {
        try {
            LocalDateTime[] dateRange = Parser.getTimeFrame(timeFrame);
            ArrayList<Task> upcomingTasks = getUpcomingTasks(dateRange[0], dateRange[1]);
            upcomingTasks.sort(Comparator.comparing(Task::getDateTime));

            if (!upcomingTasks.isEmpty()) {
                System.out.println("\t Here are the upcoming tasks for the " + timeFrame + ":");
                int i = 1;
                for (Task t : upcomingTasks) {
                    System.out.println("\t" + (i++) + ". " + t.toString());
                }
            } else {
                System.out.println("\t There's nothing coming up for the " + timeFrame + "! Chill ooooout :)");
            }
            ui.printLineBreak();
        } catch (InvalidTimeFrameException e) {
            if (e.timeframe.isEmpty()) {
                e.timeframe = "An empty time frame";
            }
            ui.printErrorMessage(Ui.INVALID_TIMEFRAME_MESSAGE, e.timeframe);
        }
    }

    public static ArrayList<Task> getUpcomingTasks(LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<Task> upcomingTasks = new ArrayList<>();
        for (int i = 0; i < itemsInList; i++) {
            Task t = taskList.get(i);
            if ((t instanceof Deadline || t instanceof Event) && isWithinTimeFrame(startDate, endDate, t)) {
                upcomingTasks.add(t);
            }
        }
        return upcomingTasks;
    }

    public static boolean isWithinTimeFrame(LocalDateTime startDate, LocalDateTime endDate, Task t) {
        return t.getDateTime().isAfter(startDate) && t.getDateTime().isBefore(endDate);
    }


    /*
     * ===========================================
     *         MARKING COMPLETED TASKS
     * ===========================================
     */

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
        System.out.println("\t Nice! I've marked this duke.task as done:");
        System.out.println("\t   " + taskList.get(taskID).toString());
        ui.printLineBreak();
    }
}
