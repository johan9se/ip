import java.io.IOException;

/**
 * Entry point of the Duke application.
 * Initializes the application and starts the interaction with the user.
 */
public class Duke {
    private static final String COMMAND_EXIT_WORD = "bye";

    private final Ui ui;
    private final TaskList tasks;
    private final Storage storage;

    public Duke(String filePath) {
        assert false;
        ui = new Ui();
        tasks = new TaskList();
        storage = new Storage(filePath);
    }

    public void run() {
        ui.printGreeting();
        ui.printGuideMessage();
        storage.load();

        String inputLine;
        do {
            inputLine = ui.getUserCommand();
            ui.printLineBreak();
            tasks.accessTaskList(inputLine);
            try {
                storage.writeToFile(storage.filePath, storage.formatList());
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        } while (!inputLine.equals(COMMAND_EXIT_WORD));
        ui.printGoodbyeAndExit();
    }

    public static void main(String[] args) {
        new Duke("taskList.txt").run();
    }

}
