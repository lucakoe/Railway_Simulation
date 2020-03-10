package railwaysimulation.exceptions;

public class ConversionException extends Exception {

    public ConversionException() {
        super(MESSAGE);
    }
    private static final String MESSAGE="Could't convert";
}
