package railwaysimulation.exceptions;
//TODO maybe exchange for io/input exception
public class WrongCoordinatesException extends Exception {
    public WrongCoordinatesException(){
        super(EXCEPTION_MASSAGE);
    }
    private static final String EXCEPTION_MASSAGE = "The Coordinates you entered are wrong";
}
