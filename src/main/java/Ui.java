import java.util.Scanner;

public class Ui {
    private static final String LOGO = "\t   .            *        .\n" +
                                        "\t *    .     * .     *\n" +
                                        "\t  .         ___  .        *\n" +
                                        "\t   *      _[___]_   .\n" +
                                        "\t *  .      ('v') v *    *\n" +
                                        "\t        >--( . )/     .     .\n" +
                                        "\t .    *   (  :  )   *\n" +
                                        "\t .. . ...  '--`-` ... *  .";

    static final String GENERAL_ERROR_MESSAGE = "\t Oops! Something went wrong. Please try again.";
    static final String INVALID_COMMAND_MESSAGE = "\t Ohno! The description of a '%s' cannot be empty :(";
    static final String DONT_UNDERSTAND_MESSAGE = "\t Uhm........I'm sorry I do not understand what that means.";
    static final String MISSING_DATETIME_MESSAGE = "\t Please provide a date/time for this %s description!";
    static final String MISSING_DETAILS_MESSAGE = "\t Please provide all the details for this %s!";
    static final String INVALID_DATETIME_FORMAT = "\t The date+time format should be {yyyy-mm-dd, HH:mm}";
    static final String LINE_BREAK = "\t_________________________________";

    public Ui() {
    }

    public void printLineBreak() {
        System.out.println(LINE_BREAK);
    }

    public void printGreeting() {
        System.out.println(LOGO);
        printLineBreak();
        System.out.println("\t Hi! I'm Olaf!\n\t What can I do for you?");
        printLineBreak();
    }

    public void printGoodbye() {
        System.out.println("\t Byebye! Hope to see you again soon!");
        printLineBreak();
    }

    public void printGuideMessage() {
        System.out.println("\t Here are the commands you can use:");
        System.out.println("\t todo {description}........................ to add a new task");
        System.out.println("\t deadline {description} \\by {yyyy-mm-dd,HH:mm}.... to add a new task that needs to be done by a specific date/time");
        System.out.println("\t event {description} \\at {yyyy-mm-dd,HH:mm}....... to add a new task that starts and ends at a specific date/time");
        System.out.println("\t list...................................... to see your entire to-do list");
        System.out.println("\t delete [item num]........................... to remove item off the list");
        System.out.println("\t done [item num]........................... to check off something you have completed");
        System.out.println("\t bye....................................... see you later alligator!");
        printLineBreak();
    }

    public String getUserCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public void printErrorMessage(String message, String... args) {
        System.out.printf(message + "\n" + LINE_BREAK + "\n", args);
    }
}
