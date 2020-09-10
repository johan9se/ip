import duke.*;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Duke {
    private static final String COMMAND_LIST_WORD = "list";
    private static final String COMMAND_TODO_WORD = "todo";
    private static final String COMMAND_DEADLINE_WORD = "deadline";
    private static final String COMMAND_EVENT_WORD = "event";
    private static final String COMMAND_DONE_WORD = "done";
    private static final String COMMAND_EXIT_WORD = "bye";

    private static final String GENERAL_ERROR_MESSAGE = "\t Oops! Something went wrong. Please try again.";
    private static final String INVALID_COMMAND_MESSAGE = "\t Ohno! The description of a '%s' cannot be empty :(";
    private static final String DONT_UNDERSTAND_MESSAGE = "\t Uhm........I'm sorry I do not understand what that means.";
    private static final String MISSING_DATETIME_MESSAGE = "\t Please provide a date/time for this %s description!";
    private static final String MISSING_DETAILS_MESSAGE = "\t Please provide all the details for this %s!";

    private static final String LOGO = "\t   .            *        .\n" +
                                        "\t *    .     * .     *\n" +
                                        "\t  .         ___  .        *\n" +
                                        "\t   *      _[___]_   .\n" +
                                        "\t *  .      ('v') v *    *\n" +
                                        "\t        >--( . )/     .     .\n" +
                                        "\t .    *   (  :  )   *\n" +
                                        "\t .. . ...  '--`-` ... *  .";

    private static final int MAX_TASK_LENGTH = 100;
    private static final Task[] list = new Task[MAX_TASK_LENGTH];
    private static int itemsInList = 0;

    public static void printLineBreak() {
        System.out.println("\t_________________________________");
    }

    public static void printGreeting() {
        System.out.println(LOGO);
        printLineBreak();
        System.out.println("\t Hi! I'm Olaf!\n\t What can I do for you?");
        printLineBreak();
    }

    public static void printGoodbye() {
        printLineBreak();
        System.out.println("\t Byebye! Hope to see you again soon!");
        printLineBreak();
    }

    public static void printGuideMessage() {
        System.out.println("\t Here are the commands you can use:");
        System.out.println("\t todo {description}........................ to add a new task");
        System.out.println("\t deadline {description} \\by {date time}.... to add a new task that needs to be done by a specific date/time");
        System.out.println("\t event {description} \\at {date time}....... to add a new task that starts and ends at a specific date/time");
        System.out.println("\t list...................................... to see your entire to-do list");
        System.out.println("\t done [item num]........................... to check off something you have completed");
        System.out.println("\t bye....................................... see you later alligator!");
        printLineBreak();
    }

    public static void printErrorMessage(String message, String... args) {
        System.out.printf((message) + "\n", args);
        printLineBreak();
    }

    public static void executeCommand(String userInput) {
        try {
            final String[] commandAndParams = splitCommandWordAndArgs(userInput);
            final String command = commandAndParams[0];
            final String commandArgs = commandAndParams[1];
            switch (command) {
            case COMMAND_TODO_WORD:
                Task todo = addNewTodo(commandArgs);
                echoNewlyAddedItem(todo);
                break;
            case COMMAND_DEADLINE_WORD:
                Task deadline = addNewDeadline(commandArgs);
                echoNewlyAddedItem(deadline);
                break;
            case COMMAND_EVENT_WORD:
                Task event = addNewEvent(commandArgs);
                echoNewlyAddedItem(event);
                break;
            case COMMAND_LIST_WORD:
                listItems();
                break;
            case COMMAND_DONE_WORD:
                markTaskAsDone(commandArgs);
                printDoneMessage(commandArgs);
                break;
            default:
                printErrorMessage(DONT_UNDERSTAND_MESSAGE);
                printGuideMessage();
                break;
            }
        } catch (NumberFormatException e) {
            printErrorMessage(GENERAL_ERROR_MESSAGE);
        } catch (DukeException e) {
            printErrorMessage(INVALID_COMMAND_MESSAGE, e.command);
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
        String dateTime = args.substring(args.indexOf("\\")+3).trim();
        String[] details = {description, dateTime};
        if (description.isEmpty() || dateTime.isEmpty()) {
            throw new DukeException();
        }
        return details;
    }

    public static void addNewListItem(Task item) {
        list[itemsInList] = item;
        itemsInList++;
    }

    public static Task addNewTodo(String args) {
        Task todo = new ToDo(args);
        addNewListItem(todo);
        return todo;
    }

    public static Task addNewDeadline(String args) {
        try {
            String description = splitDescriptionAndDateTime(args)[0];
            String byDateTime = splitDescriptionAndDateTime(args)[1];
            Task deadline = new Deadline(description, byDateTime);
            addNewListItem(deadline);
            return deadline;
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage(MISSING_DATETIME_MESSAGE, "deadline");
        } catch (DukeException e) {
            printErrorMessage(MISSING_DETAILS_MESSAGE, "deadline");
        }
        return null;
    }

    public static Task addNewEvent(String args) {
        try {
            String description = splitDescriptionAndDateTime(args)[0];
            String atDateTime = splitDescriptionAndDateTime(args)[1];
            Task event = new Event(description, atDateTime);
            addNewListItem(event);
            return event;
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage(MISSING_DATETIME_MESSAGE, "event");
        } catch (DukeException e) {
            printErrorMessage(MISSING_DETAILS_MESSAGE, "event");
        }
        return null;
    }

    public static void listItems() {
        if (itemsInList > 0) {
            System.out.println("\t Here are the tasks in your list:");
            for (int i=0;i<itemsInList;i++) {
                System.out.printf("\t %d. %s\n", i+1, list[i].toString());
            }
        } else {
            System.out.println("\t Your list is still empty! Start adding tasks now :)");
        }
        printLineBreak();
    }

    public static void echoNewlyAddedItem(Task item) {
        System.out.println("\t Got it! I've added this task:");
        System.out.println("\t   " + item.toString());
        System.out.printf("\t Now you have " + itemsInList + " task%s in the list.\n", (itemsInList>1 ? "s":""));
        printLineBreak();
    }

    public static void markTaskAsDone(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        if (0 <= taskID && taskID < itemsInList) {
            list[taskID].markAsDone();
        } else {
            printErrorMessage(GENERAL_ERROR_MESSAGE);
        }
    }

    public static void printDoneMessage(String listNumber) {
        int taskID = Integer.parseInt(listNumber) - 1;
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t   " + list[taskID].toString());
        System.out.println("\t_________________________________");
    }

    public static String formatList() {
        StringBuilder text = new StringBuilder();
        for (int i=0;i<itemsInList;i++) {
            text.append(list[i].formatString()).append(System.lineSeparator());
        }
        return text.toString();
    }

    private static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    private static void readFromFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        int taskCount = 0;
        while (s.hasNext()) {
            taskCount++;
            processListFromFile(s.nextLine(), taskCount);
        }
    }

    private static void processListFromFile(String taskLine, int listNumber) {
        String[] args = taskLine.split("\\|");
        String type = args[0].trim();
        boolean isDone = Boolean.parseBoolean(args[1].trim());
        String description = args[2].trim();

        switch (type) {
        case "T":
            addNewTodo(description);
            break;
        case "D":
            addNewDeadline(description + "\\  " + args[3]);
            break;
        case "E":
            addNewEvent(description + "\\  " + args[3]);
            break;
        default:
            break;
        }
        if (isDone) {
            list[listNumber-1].markAsDone();
        }
    }

    public static void main(String[] args) {
        printGreeting();
        printGuideMessage();

        String file = "data/list.txt";
        try {
            readFromFile(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();

        while (!inputLine.equals(COMMAND_EXIT_WORD)) {
            printLineBreak();
            executeCommand(inputLine);
            try {
                writeToFile(file, formatList());
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
            inputLine = in.nextLine();
        }
        printGoodbye();
    }
}
