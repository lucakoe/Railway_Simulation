package railwaysimulation.exceptions;

public class TrackIsOccupiedException extends Exception {
    public TrackIsOccupiedException() {
        super(EXCEPTION_MASSAGE);
    }
    private static final String EXCEPTION_MASSAGE = "the track is occupied";
}
