import java.util.Scanner;

public class Duke {
    private static Task[] list = new Task[100];
    private static int itemsInList = 0;

    public static void lineBreak(){
        System.out.println("\t_________________________________");
    }
    public static void printGreeting(){
        lineBreak();
        System.out.println("\t Hi! I'm Olaf!");
        System.out.println("\t What can I do for you?");
        lineBreak();
    }
    public static void printGoodbye(){
        lineBreak();
        System.out.println("\t Byebye! Hope to see you again soon!");
        lineBreak();
    }
    public static void catchError(){
        lineBreak();
        System.out.println("\t Something went wrong. Please try again.");
        lineBreak();
    }

    public static void addNewListItem(Task item){
        list[itemsInList] = item;
        itemsInList++;
    }

    public static void listItems(){
        lineBreak();
        System.out.println("\t Here are the tasks in your list:");
        for (int i=0;i<itemsInList;i++){
            System.out.printf("\t %d. [%c][%s] %s\n", i+1, list[i].getLabel(), list[i].getStatusIcon(), list[i].description);
        }
        lineBreak();
    }

    public static void echoItem(String item){
        lineBreak();
        System.out.println("\t added: " + item);
//        Got it. I've added this task:
//                [T][✗] borrow book
//        Now you have 5 tasks in the list.
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
        while (!line.equals("bye")){
            if (line.equals("list")){
                listItems();
            } else if (line.startsWith("done")) {
                try {
                    int taskID = Integer.parseInt((line.split(" "))[1]) - 1; // assumes command = "done {int}"
                    if (0 <= taskID && taskID < itemsInList){
                        list[taskID].markAsDone();
                    } else {catchError();} // out of range id
                } catch (Exception e){ // to catch if list id (int) is not provided
                    catchError();
                }
            } else {
                Task t;
                String description =line.substring(line.indexOf(" "));
                ;
                if (line.startsWith("todo")){
                    t = new ToDo(description);
                } else if (line.startsWith("deadline")) {
                    t = new Deadline(description.replace("\\by", "(by:") + ")");
                }
                addNewListItem(t);
                echoItem(t.description);
            }
            //Task t = new Task(line);
            line = in.nextLine();
        }
        printGoodbye();
    }
}
