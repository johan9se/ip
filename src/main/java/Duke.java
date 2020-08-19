import java.util.Scanner;

public class Duke {
    private static Task[] list = new Task[100];
    private static int itemsInList = 0;

    public static void lineBreak(){
        System.out.println("\t_________________________________");
    }
    public static void printGreeting(){
        lineBreak();
        System.out.println("\t Hello! I'm Duke");
        System.out.println("\t What can I do for you?");
        lineBreak();
    }

    public static void printGoodbye(){
        lineBreak();
        System.out.println("\t Bye. Hope to see you again soon!");
        lineBreak();
    }

    public static void addNewListItem(Task item){
        list[itemsInList] = item;
        itemsInList++;
    }

    public static void listItems(){
        lineBreak();
        for (int i=0;i<itemsInList;i++){
            System.out.printf("\t %d. [%s] %s\n", i+1, list[i].getStatusIcon(), list[i].description);
        }
        lineBreak();
    }

    public static void echoItem(String item){
        lineBreak();
        System.out.println("\t added: " + item);
        lineBreak();
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        printGreeting();
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (!line.equals("bye")){
            if (line.equals("list")){
                listItems();
            } else if (line.startsWith("done")) {
                int taskID = Integer.parseInt((line.split(" "))[1]) - 1;
                list[taskID].markAsDone();
            } else {
                Task t = new Task(line);
                addNewListItem(t);
                echoItem(t.description);
            }
            line = in.nextLine();
        }
        printGoodbye();
    }
}
