import java.util.Scanner;

public class Duke {
    private static Task[] list = new Task[100];
    private static int itemsInList = 0;

    public static void printGreeting(){
        System.out.println("\t_________________________________");
        System.out.println("\t  Hello! I'm Duke");
        System.out.println("\t  What can I do for you?");
        System.out.println("\t_________________________________");
    }
    public static void printGoodbye(){
        System.out.println("\t_________________________________");
        System.out.println("\t  Bye. Hope to see you again soon!");
        System.out.println("\t_________________________________");
    }

    public static void addNewListItem(Task item){
        list[itemsInList] = item;
        itemsInList++;
    }

    public static void listItems(){
        System.out.println("\t_________________________________");
        for (int i=0;i<itemsInList;i++){
            System.out.printf("%d. [%s] %s\n", i+1, list[i].getStatusIcon(), list[i].description);
        }
        System.out.println("\t_________________________________");
    }

    public static void echoItem(String item){
        System.out.println("\t_________________________________");
        System.out.println("\t  added: " + item);
        System.out.println("\t_________________________________");
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
