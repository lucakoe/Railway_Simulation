package railway_simulation.railway;

import railway_simulation.exceptions.TrackIsOccupiedException;
import railway_simulation.exceptions.TrackMaterialCanNotBeRemoved;

public interface TrackMaterial {
    Coordinate getStartCoordinate();
    Coordinate getEndCoordinate();
    boolean isOccupied();
    void disconnectAndSafeDeletePoints() throws TrackIsOccupiedException, TrackMaterialCanNotBeRemoved;
    TrackMaterialTypes getTrackMaterialType();
    boolean isActive();
    long getLength();
    int getTrackMaterialID();

}
