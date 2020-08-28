public class Deadline extends Task{
    public String description;
    public String by;

    public Deadline(String description){
        super(description);
    }

    @Override
    public char getLabel() {
        return 'D';
    }
}
