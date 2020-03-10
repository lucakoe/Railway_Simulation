package railwaysimulation.exceptions;

public class IDAlreadyExistsException extends Exception {

    public IDAlreadyExistsException(String kindOfID) {
        super("The "+kindOfID+" already exists");
    }
}
