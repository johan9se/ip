import duke.Deadline;
import duke.Event;
import duke.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles the loading and saving of Tasks into a file.
 */
public class Storage {
    protected static File file;
    protected String filePath;

    public Storage(String filePath) {
        file = new File(filePath);
        this.filePath = filePath;
    }

    /**
     * Loads existing tasks from a file into the TaskList.
     */
    public void load() {
        try {
            file.createNewFile();
            readFromFile(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Reads contents of a file.
     */
    public void readFromFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        int taskCount = 0;
        while (s.hasNext()) {
            taskCount++;
            processListFromFile(s.nextLine(), taskCount);
        }
    }

    /**
     * Processes existing tasks from a file and adds it into the TaskList.
     */
    public void processListFromFile(String taskLine, int listNumber) {
        String[] args = taskLine.split("\\|");
        String type = args[0].trim();
        boolean isDone = Boolean.parseBoolean(args[1].trim());
        String description = args[2].trim();

        switch (type) {
        case ToDo.TODO_MARKER:
            TaskList.addNewTodo(description, false);
            break;
        case Deadline.DEADLINE_MARKER:
            TaskList.addNewDeadline(description + "\\  " + args[3], false);
            break;
        case Event.EVENT_MARKER:
            TaskList.addNewEvent(description + "\\  " + args[3], false);
            break;
        default:
            break;
        }
        if (isDone) {
            TaskList.taskList.get(listNumber - 1).markAsDone();
        }
    }

    /**
     * Handle string formatting of contents before it is written into a file.
     */
    public String formatList() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i< TaskList.itemsInList; i++) {
            text.append(TaskList.taskList.get(i).formatString()).append(System.lineSeparator());
        }
        return text.toString();
    }

    public void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }
}
