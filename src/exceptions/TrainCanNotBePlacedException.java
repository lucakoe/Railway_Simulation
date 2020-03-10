package exceptions;

public class TrainCanNotBePlacedException extends Exception {
    private static final String MESSAGE = "train can't be placed";

    public TrainCanNotBePlacedException() {
        super(MESSAGE);
    }
}
