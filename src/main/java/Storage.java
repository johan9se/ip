import duke.Deadline;
import duke.Event;
import duke.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    public static String load(String filepath) {
        File file = new File(filepath);

        try {
            file.createNewFile();
            readFromFile(filepath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return filepath;
    }
    
    static void readFromFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        int taskCount = 0;
        while (s.hasNext()) {
            taskCount++;
            processListFromFile(s.nextLine(), taskCount);
        }
    }

    static void processListFromFile(String taskLine, int listNumber) {
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

    public static String formatList() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i< TaskList.itemsInList; i++) {
            text.append(TaskList.taskList.get(i).formatString()).append(System.lineSeparator());
        }
        return text.toString();
    }

    static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }
}
