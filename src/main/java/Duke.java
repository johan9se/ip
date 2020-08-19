import java.util.Scanner;

public class Duke {
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
            System.out.println("\t_________________________________");
            System.out.println("\t  " + line);
            System.out.println("\t_________________________________\n");
            line = in.nextLine();
        }
        printGoodbye();
    }
}
