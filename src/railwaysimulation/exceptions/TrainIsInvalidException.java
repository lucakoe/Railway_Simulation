package railwaysimulation.exceptions;

public class TrainIsInvalidException extends Exception {
    public TrainIsInvalidException(){
        super(MESSAGE);
    }
    private static final String MESSAGE="Train is invalid";
}
