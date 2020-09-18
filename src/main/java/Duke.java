import java.io.IOException;

public class Duke {
    public Duke(String filePath) {
        Storage.load(filePath);
        Ui.printGreeting();
        Ui.printGuideMessage();

        String inputLine;
        do {
            inputLine = Ui.getUserCommand();
            Ui.printLineBreak();
            try {
                Storage.writeToFile(filePath, Storage.formatList());
            } catch (IOException e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        } while (!Parser.parseCommand(inputLine));
        Ui.printGoodbye();
    }

    public static void main(String[] args) {
        new Duke("taskList.txt");
    }

}
