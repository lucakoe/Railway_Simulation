package Exceptions;

public class WrongCoordinatesException extends Exception {
    WrongCoordinatesException(){
        super("The Coordinates you entered are wrong");
    }

}
