public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "bye";
    public static final String COMMAND_USE = COMMAND_WORD + ": exits the programme";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "\t Byebye! Hope to see you again soon!";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    public static boolean isExit(Command command) {
        return command instanceof ExitCommand; // instanceof returns false if it is null
    }
}
