package Exceptions;

public class WrongCoordinatesException extends Exception {
    public WrongCoordinatesException(){
        super("The Coordinates you entered are wrong");
    }

}
