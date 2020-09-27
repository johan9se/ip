package duke.ui;

import java.util.Scanner;

/**
 * Text UI of the duke.data.Duke application.
 */
public class Ui {
    private static final String LOGO = "\t   .            *        .\n" +
                                        "\t *    .     * .     *\n" +
                                        "\t  .         ___  .        *\n" +
                                        "\t   *      _[___]_   .\n" +
                                        "\t *  .      ('v') v *    *\n" +
                                        "\t        >--( . )/     .     .\n" +
                                        "\t .    *   (  :  )   *\n" +
                                        "\t .. . ...  '--`-` ... *  .";

    public static final String GENERAL_ERROR_MESSAGE = "\t Oops! Something went wrong. Please try again.";
    public static final String INVALID_COMMAND_MESSAGE = "\t Ohno! The description of a '%s' cannot be empty :(";
    public static final String DONT_UNDERSTAND_MESSAGE = "\t Uhm........I'm sorry I do not understand what that means.\n\t Type 'help' to see all commands!";
    public static final String MISSING_DATETIME_MESSAGE = "\t Please provide a date/time for this %s description!";
    public static final String MISSING_DETAILS_MESSAGE = "\t Please provide all the details for this %s!";
    public static final String INVALID_DATETIME_FORMAT = "\t The date+time format should be {yyyy-mm-dd HH:mm}.";
    public static final String INVALID_TIMEFRAME_MESSAGE = "\t Uh oh! %s is not a valid time frame. Try {day/week/month} instead.";
    public static final String LINE_BREAK = "\t_________________________________";

    public Ui() {
    }

    public void printLineBreak() {
        System.out.println(LINE_BREAK);
    }

    public void printGreeting() {
        System.out.println(LOGO);
        printLineBreak();
        System.out.println("\t Hi! I'm Olaf!\n\t What can I do for you today?");
        System.out.println("\t Type 'help' to see all the commands you can use!");
        printLineBreak();
    }

    /**
     * Displays the goodbye message and exits the runtime.
     */
    public void printGoodbyeAndExit() {
        System.out.println("\t Byebye! Hope to see you again soon!");
        printLineBreak();
        System.exit(0);
    }

    public void printGuideMessage() {
        System.out.println("\t Here are the commands you can use:");
        System.out.println("\t todo {description}...................................................... to add a new task");
        System.out.println("\t deadline {description} \\by {yyyy-mm-dd HH:mm}........................... to add a new deadline");
        System.out.println("\t event {description} \\at {yyyy-mm-dd HH:mm} \\to {yyyy-mm-dd HH:mm}....... to add a new event that starts and ends at a specific date/time");
        System.out.println("\t list.................................................................... to see your entire to-do list");
        System.out.println("\t upcoming <day/week/month>............................................... to see what is coming up soon");
        System.out.println("\t delete [item num]....................................................... to remove an item off the list");
        System.out.println("\t done [item num]......................................................... to check off something you have completed");
        System.out.println("\t find [keyword].......................................................... to see which tasks contain a particular keyword");
        System.out.println("\t bye..................................................................... see you later alligator!");
        printLineBreak();
    }

    /**
     * Prompts for the command from the user.
     *
     * @return full line entered by the user
     */
    public String getUserCommand() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /**
     * Displays the corresponding error message.
     */
    public void printErrorMessage(String message, String... args) {
        System.out.printf(message + "\n" + LINE_BREAK + "\n", args);
    }
}
