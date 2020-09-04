import java.util.Scanner;

public class Duke {
    private static final String COMMAND_LIST_WORD = "list";
    private static final String COMMAND_TODO_WORD = "todo";
    private static final String COMMAND_DEADLINE_WORD = "deadline";
    private static final String COMMAND_EVENT_WORD = "event";
    private static final String COMMAND_DONE_WORD = "done";
    private static final String COMMAND_EXIT_WORD = "bye";

    private static final String LOGO = "\t   .            *        .\n" +
                                        "\t *    .     * .     *\n" +
                                        "\t  .         ___  .        *\n" +
                                        "\t   *      _[___]_   .\n" +
                                        "\t *  .      ('▻') v *    *\n" +
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

    public static void printErrorMessage() {
        System.out.println("\t Oops! Something went wrong. Please try again.");
        printLineBreak();
    }

    public static void printInvalidCommandMessage(String command) {
        System.out.println("\t Ohno! The description of a '" + command +"' cannot be empty :(");
        printLineBreak();
    }

    public static void printDontUnderstandMessage() {
        System.out.println("\t Uhm........I'm sorry I do not understand what that means.");
        printLineBreak();
    }

    public static void executeCommand(String userInput) {
        try {
            final String[] commandAndParams = splitCommandWordAndArgs(userInput);
            final String command = commandAndParams[0];
            final String commandArgs = commandAndParams[1];
            switch (command) {
            case COMMAND_TODO_WORD:
                addNewTodo(commandArgs);
                break;
            case COMMAND_DEADLINE_WORD:
                addNewDeadline(commandArgs);
                break;
            case COMMAND_EVENT_WORD:
                addNewEvent(commandArgs);
                break;
            case COMMAND_LIST_WORD:
                listItems();
                break;
            case COMMAND_DONE_WORD:
                markTaskAsDone(commandArgs);
                break;
            default:
                printDontUnderstandMessage();
                printGuideMessage();
                break;
            }
        } catch (NumberFormatException e) {
            printErrorMessage();
        } catch (DukeException e) {
            printInvalidCommandMessage(e.command);
        }
    }

    public static String[] splitCommandWordAndArgs(String rawUserInput) throws DukeException {
        final String[] split = rawUserInput.trim().split(" ", 2);
        if (split[0].matches("todo|deadline|event") && split.length == 1) {
            throw new DukeException(split[0]);
        }
        return split.length == 2 ? split : new String[] { split[0] , "" };
    }

    public static void addNewListItem(Task item) {
        list[itemsInList] = item;
        itemsInList++;
    }

    public static void addNewTodo(String args) {
        Task todo = new ToDo(args);
        addNewListItem(todo);
        echoNewlyAddedItem(todo);
    }

    public static void addNewDeadline(String args) {
        try {
            String description = args.substring(0, args.indexOf("\\by"));
            String byDateTime = args.substring(args.indexOf("\\by")+3);
            Task deadline = new Deadline(description, byDateTime);
            addNewListItem(deadline);
            echoNewlyAddedItem(deadline);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("\t Please provide a date/time for this deadline description!");
        }

    }

    public static void addNewEvent(String args) {
        try {
            String description = args.substring(0, args.indexOf("\\at"));
            String atDateTime = args.substring(args.indexOf("\\at")+3);
            Task event = new Event(description, atDateTime);
            addNewListItem(event);
            echoNewlyAddedItem(event);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("\t Please provide a date/time for this event description!");
        }
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
            printErrorMessage();
        }
    }

    public static void main(String[] args) {
        printGreeting();

        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();

        while (!inputLine.equals(COMMAND_EXIT_WORD)) {
            printLineBreak();
            executeCommand(inputLine);
            inputLine = in.nextLine();
        }
        printGoodbye();
    }
}
