package exceptions;

public class NotAllSwitchesSetException extends Exception {
    private static final String MESSAGE ="not all switches are set" ;

    public NotAllSwitchesSetException() {
        super(MESSAGE);
    }
}
