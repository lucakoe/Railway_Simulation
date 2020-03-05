package railway_simulation.exceptions;

public class TrackIsOccupiedException extends Exception {
    public TrackIsOccupiedException() {
        super(EXCEPTION_MASSAGE);
    }
    private static final String EXCEPTION_MASSAGE = "The Track is occupied";
}
