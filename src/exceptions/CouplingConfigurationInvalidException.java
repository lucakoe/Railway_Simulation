package exceptions;

public class CouplingConfigurationInvalidException extends Exception {
    public CouplingConfigurationInvalidException(){
        super(MESSAGE);
    }
    private static final String MESSAGE="the chosen coupling configuration is invalid";
}
