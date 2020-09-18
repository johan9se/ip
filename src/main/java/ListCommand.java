public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_USE = COMMAND_WORD + ": see your entire to-do list";
    public static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "\t Here are the tasks in your list:";
    public static final String MESSAGE_EMPTY_LIST = "\t Your list is empty! Start adding tasks now :)";

    @Override
    public CommandResult execute() {
        return new CommandResult("wait");
    }
}

//    public static void listItems() {
//        if (itemsInList > 0) {
//            System.out.println("\t Here are the tasks in your list:");
//            for (int i=0;i<itemsInList;i++) {
//                System.out.printf("\t %d. %s\n", i+1, list.get(i).toString());
//            }
//        } else {
//            System.out.println("\t Your list is empty! Start adding tasks now :)");
//        }
//        Ui.printLineBreak();
//    }
