import duke.*;

import java.util.ArrayList;

public class TaskList {

    protected static ArrayList<Task> taskList = new ArrayList<>();
    protected static int itemsInList = 0;

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
            String description = splitDescriptionAndDateTime(args)[0];
            String byDateTime = splitDescriptionAndDateTime(args)[1];
            Task deadline = new Deadline(description, byDateTime);
            addNewListItem(deadline);
            if (isNew) {
                echoNewlyAddedItem(deadline);
            }
        } catch (StringIndexOutOfBoundsException e) {
            Ui.printErrorMessage(Ui.MISSING_DATETIME_MESSAGE, "deadline");
        } catch (DukeException e) {
            Ui.printErrorMessage(Ui.MISSING_DETAILS_MESSAGE, "deadline");
        }
    }

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
            Ui.printErrorMessage(Ui.MISSING_DATETIME_MESSAGE, "event");
        } catch (DukeException e) {
            Ui.printErrorMessage(Ui.MISSING_DETAILS_MESSAGE, "event");
        }
    }

    public static void echoNewlyAddedItem(Task item) {
        System.out.println("\t Got it! I've added this task:");
        System.out.println("\t   " + item.toString());
        System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n", (itemsInList >1 ? "s":""));
        Ui.printLineBreak();
    }

    public static void deleteItem(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            System.out.println("\t Okiedokie! This task has been removed:");
            System.out.println("\t   " + taskList.remove(taskID).toString());
            itemsInList--;
            System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n", (itemsInList ==1 ? "s":""));
            Ui.printLineBreak();
        } else {
            Ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        }

    }

    public static String[] splitDescriptionAndDateTime (String args) throws DukeException {
        String description = args.substring(0, args.indexOf("\\")).trim();
        String dateTime = args.substring(args.indexOf("\\")+3).trim();
        String[] details = {description, dateTime};
        if (description.isEmpty() || dateTime.isEmpty()) {
            throw new DukeException();
        }
        return details;
    }

    public static void listItems() {
        if (itemsInList > 0) {
            System.out.println("\t Here are the tasks in your list:");
            for (int i = 0; i< itemsInList; i++) {
                System.out.printf("\t %d. %s\n", i+1, taskList.get(i).toString());
            }
        } else {
            System.out.println("\t Your list is empty! Start adding tasks now :)");
        }
        Ui.printLineBreak();
    }

    public static void markTaskAsDone(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            taskList.get(taskID).markAsDone();
        } else {
            Ui.printErrorMessage(Ui.GENERAL_ERROR_MESSAGE);
        }
    }

    public static void printDoneMessage(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t   " + taskList.get(taskID).toString());
        System.out.println("\t_________________________________");
    }
}
