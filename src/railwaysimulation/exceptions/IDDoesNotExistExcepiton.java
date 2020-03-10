package railwaysimulation.exceptions;

public class IDDoesNotExistExcepiton extends Exception {
    public IDDoesNotExistExcepiton(String typeOfID) {
        super("this "+typeOfID+" does't exist");
    }
}
