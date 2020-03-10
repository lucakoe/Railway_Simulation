package railwaysimulation.railway;


import railwaysimulation.exceptions.TrackIsOccupiedException;
import railwaysimulation.exceptions.TrackMaterialCanNotBeAddedOrRemovedException;
import railwaysimulation.exceptions.WrongInputException;

//TODO maybe make super class
public abstract class TrackMaterial implements Drivable {

    private final TrackMaterialTypes TRACK_MATERIAL_TYPE;
    private final int TRACK_MATERIAL_ID;
    //TODO add trains.Train in<>


    TrackMaterial(TrackMaterialTypes trackMaterialType, int trackMaterialID) throws WrongInputException {
        this.TRACK_MATERIAL_ID = trackMaterialID;
        this.TRACK_MATERIAL_TYPE = trackMaterialType;


    }

    @Override
    public String toString() {
        return getTrackMaterialType().toString() + " " + this.getStartCoordinate().toString() + " -> " + this.getCurrentEndCoordinate().toString() + " " + this.getLength();
    }


    public abstract Point getStartPoint();

    public abstract Point getCurrentEndPoint();

    public abstract Coordinate getStartCoordinate();

    public abstract Coordinate getCurrentEndCoordinate() ;

    protected Point getStartOrEndPoint(Point startOrEndPoint) {
        if (getStartPoint() != null && startOrEndPoint == getStartPoint()) {
            return getCurrentEndPoint();
        } else if (getCurrentEndPoint() != null && startOrEndPoint == getCurrentEndPoint()) {
            return getStartPoint();
        }
        return null;
    }

    public TrackMaterialTypes getTrackMaterialType() {
        return TRACK_MATERIAL_TYPE;
    }

    abstract long getLength();

    int getTrackMaterialID() {
        return TRACK_MATERIAL_ID;
    }

    public abstract boolean isOccupied();

    abstract void disconnectAndSafeDeletePoints() throws TrackIsOccupiedException, TrackMaterialCanNotBeAddedOrRemovedException, WrongInputException;


    @Override
    public CardinalDirection getNextDirection(CardinalDirection currentCardinalDirection) {
        return currentCardinalDirection;
    }

    @Override
    public CardinalDirection getPreviousDirection(CardinalDirection currentCardinalDirection) {
        return currentCardinalDirection.getOpposite();
    }

    abstract public boolean isActive();
}
