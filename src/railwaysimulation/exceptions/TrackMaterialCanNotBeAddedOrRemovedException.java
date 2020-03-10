package railwaysimulation.exceptions;

public class TrackMaterialCanNotBeAddedOrRemovedException extends Exception {
    public TrackMaterialCanNotBeAddedOrRemovedException(){
        super(MESSAGE);
    }
    private static final String MESSAGE="track material can't be added or removed";
}
