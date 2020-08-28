public class ToDo extends Task{
    public String description;

    public ToDo(String description){
        super(description);
    }

    @Override
    public char getLabel() {
        return 'T';
    }
}
