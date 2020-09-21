import duke.*;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {

    protected static ArrayList<Task> taskList;
    protected static int itemsInList = 0;

    private static final Ui ui = new Ui();

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public void accessTaskList(String inputCommand) {
        Parser.parseCommand(inputCommand);
    }

    public static void addNewListItem(Task item) {
        taskList.add(item);
        itemsInList++;
    }

    public static void addNewTodo(String args, boolean isNew) {
        Task todo = new ToDo(args);
        addNewListItem(todo);
        if (isNew) {
            echoNewlyAddedItem(todo);
        }
    }

    public static void addNewDeadline(String args, boolean isNew) {
        try {
            String description = Parser.splitDescriptionAndDateTime(args)[0];
            String byDateTimeString = Parser.formatDateAndTimeInput(Parser.splitDescriptionAndDateTime(args)[1]);
            LocalDateTime byDateTime = Parser.getDateTimeDescription(byDateTimeString);

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

    public static void addNewEvent(String args, boolean isNew) {
        try {
            String description = Parser.splitDescriptionAndDateTime(args)[0];
            String atDateTimeString = Parser.formatDateAndTimeInput(Parser.splitDescriptionAndDateTime(args)[1]);
            LocalDateTime atDateTime = Parser.getDateTimeDescription(atDateTimeString);

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

    public static void echoNewlyAddedItem(Task item) {
        System.out.println("\t Got it! I've added this task:");
        System.out.println("\t   " + item.toString());
        System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n" + Ui.LINE_BREAK + "\n", (itemsInList > 1 ? "s" : ""));
    }

    public static void deleteItem(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            System.out.println("\t Okiedokie! This task has been removed:");
            System.out.println("\t   " + taskList.remove(taskID).toString());
            itemsInList--;
            System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n", (itemsInList == 1 ? "s" : ""));
            ui.printLineBreak();
        } else {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        }
    }

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

    public static void listUpcomingTasks(String timeFrame) {
        try {
            LocalDateTime[] dateRange = Parser.getStartAndEndDate(timeFrame);
            ArrayList<Task> upcomingTasks = getUpcomingTasks(dateRange[0], dateRange[1]);

            if (!upcomingTasks.isEmpty()) {
                System.out.println("\t Here are the upcoming tasks for the " + timeFrame);
                int i = 1;
                for (Task t : upcomingTasks) {
                    System.out.println("\t" + (i++) + ". " + t.toString());
                }
            } else {
                System.out.println("\t There's nothing coming up for the " + timeFrame + "! Chill ooooout :)");
            }

            ui.printLineBreak();
        } catch (InvalidTimeFrameException e) {
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

    public static void markTaskAsDone(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            taskList.get(taskID).markAsDone();
        } else {
            ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        }
    }

    public static void printDoneMessage(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t   " + taskList.get(taskID).toString());
        ui.printLineBreak();
    }
}
