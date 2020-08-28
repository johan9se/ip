import java.util.Scanner;

public class Duke {
    private static Task[] list = new Task[100];
    private static int itemsInList = 0;

    public static void lineBreak() {
        System.out.println("\t_________________________________");
    }
    public static void printGreeting() {
        lineBreak();
        System.out.println("\t Hi! I'm Olaf!");
        System.out.println("\t What can I do for you?");
        lineBreak();
    }
    public static void printGoodbye() {
        lineBreak();
        System.out.println("\t Byebye! Hope to see you again soon!");
        lineBreak();
    }
    public static void catchError() {
        lineBreak();
        System.out.println("\t Oops! Something went wrong. Please try again.");
        lineBreak();
    }

    public static void addNewListItem(Task item) {
        list[itemsInList] = item;
        itemsInList++;
    }

    public static void listItems() {
        lineBreak();
        System.out.println("\t Here are the tasks in your list:");
        for (int i=0;i<itemsInList;i++) {
            System.out.printf("\t %d. %s\n", i+1, list[i].toString());
        }
        lineBreak();
    }

    public static void echoItem(Task item) {
        lineBreak();
        System.out.println("\t Got it! I've added this task:");
        System.out.println("\t   " + item.toString());
        System.out.println("\t Now you have " + itemsInList + " tasks in the list.");
        lineBreak();
    }

    public static void main(String[] args) {
        String logo =
                "\t   .            *        .\n" +
                "\t *    .     * .     *\n" +
                "\t  .     *       .       *\n" +
                "\t   *        \\|     .\n" +
                "\t *  .      ('^')  v *    *\n" +
                "\t        >--(  . )/     .   .\n" +
                "\t .    *    (  .  )   *\n" +
                "\t .. .  ...  '--``-` ... *  .";
        System.out.println(logo);

        printGreeting();
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (!line.equals("bye")) {
            if (line.equals("list")) {
                listItems();
            } else if (line.startsWith("done")) {
                try {
                    int taskID = Integer.parseInt((line.split(" "))[1]) - 1; // assumes command = "done {int}"
                    if (0 <= taskID && taskID < itemsInList) {
                        list[taskID].markAsDone();
                    } else {catchError();} // out of range id
                } catch (Exception e) { // if list id (int) is not provided
                    catchError();
                }
            } else {
                Task t;
                String taskType = line.split(" ")[0];
                String description;

                switch (taskType) {
                case "todo":
                    description = line.substring(line.indexOf(" ")+1);
                    t = new ToDo(description);
                    break;
                case "deadline":
                    description = line.substring(line.indexOf(" ")+1, line.indexOf("\\by"));
                    String by = line.substring(line.indexOf("\\by")+3);
                    t = new Deadline(description, by);
                    break;
                case "event":
                    description = line.substring(line.indexOf(" ")+1, line.indexOf("\\at"));
                    String at = line.substring(line.indexOf("\\at")+3);
                    t = new Event(description, at);
                    break;
                default:
                    t = new Task(line);
                    break;
                }
                addNewListItem(t);
                echoItem(t);
            }
            line = in.nextLine();
        }
        printGoodbye();
    }
}
