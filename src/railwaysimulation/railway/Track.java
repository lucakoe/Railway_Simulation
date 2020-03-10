package railwaysimulation.railway;

import railwaysimulation.exceptions.NotAvailableException;
import railwaysimulation.exceptions.TrackIsOccupiedException;
import railwaysimulation.exceptions.TrackMaterialCanNotBeAddedOrRemovedException;
import railwaysimulation.exceptions.WrongInputException;
import railwaysimulation.trains.Train;

import java.util.HashSet;

public class Track extends TrackMaterial {
    private final Point START_POINT;
    private final Point END_POINT;
    private boolean active;
    private final TrackSystem TRACK_SYSTEM;
    HashSet<Train> trains;

    public Track(TrackSystem trackSystem, int trackMaterialID, Coordinate startPointCoordinate, Coordinate endPointCoordinate) throws WrongInputException, TrackMaterialCanNotBeAddedOrRemovedException {
        super(TrackMaterialTypes.TRACK, trackMaterialID);
        this.TRACK_SYSTEM = trackSystem;
        this.active = true;
        trains = new HashSet<Train>();

        CardinalDirection cardinalDirectionOfTrack = startPointCoordinate.getDirectionToCoordinate(endPointCoordinate);
        if (cardinalDirectionOfTrack != null) {
            //TODO add other conditions for adding
            boolean firstTrackCondition = trackSystem.getNumberOfTracks() == 0;
            //checks if there already is a point at the start/endpoint and if so, it has a free creatable connection
            //point (the point is not created yet and there is a creatable connection point available)
            boolean availableConnectionToOtherTrack = (trackSystem.containsPoint(startPointCoordinate) &&
                    trackSystem.getPoint(startPointCoordinate).creatableConnectionPointInDirection(cardinalDirectionOfTrack)) ||
                    (trackSystem.containsPoint(endPointCoordinate) &&
                            trackSystem.getPoint(endPointCoordinate).creatableConnectionPointInDirection(cardinalDirectionOfTrack.getOpposite()));

            Track track;
            if (firstTrackCondition || availableConnectionToOtherTrack) {
                trackSystem.addPoint(startPointCoordinate);
                trackSystem.addPoint(endPointCoordinate);
                //TODO check if its working properly without this condition
                /*if (points.get(startCoordinate).getNumberOfCreatableConnectionPoints() != 0 &&
                        points.get(endCoordinate).getNumberOfCreatableConnectionPoints() != 0) {
                        */


            } else {
                throw new TrackMaterialCanNotBeAddedOrRemovedException();
            }

        } else {
            throw new TrackMaterialCanNotBeAddedOrRemovedException();
        }

        this.START_POINT = trackSystem.getPoint(startPointCoordinate);
        this.END_POINT = trackSystem.getPoint(endPointCoordinate);
    }

    //has to be called right after initialisation!
    protected void addThisTrackToItsStartAndEndPoint() {
        CardinalDirection cardinalDirectionOfTrack = getStartCoordinate().getDirectionToCoordinate(getCurrentEndCoordinate());
        try {
            TRACK_SYSTEM.getPoint(getStartCoordinate()).setTrack(cardinalDirectionOfTrack, this);
            TRACK_SYSTEM.getPoint(getCurrentEndCoordinate()).setTrack(cardinalDirectionOfTrack.getOpposite(), this);

        } catch (
                NotAvailableException e) {
            //can't happen since the conditions cover these
        }

    }

    @Override
    public String toString() {
        return getTrackMaterialType().toString() +" "+this.getTrackMaterialID()+ " " + this.getStartCoordinate().toString() + " -> " + this.getCurrentEndCoordinate().toString() + " " + this.getLength();
    }

    @Override
    public void addTrain(Train train) {
        trains.add(train);
    }

    @Override
    public void removeTrain(Train train) {
        trains.remove(train);
    }

    public void setActivation(boolean newActivation) {
        active = newActivation;
    }

    @Override
    public void disconnectAndSafeDeletePoints() throws TrackIsOccupiedException {
        if (!this.isOccupied() && (!this.startAndEndPointIsConnectedToOtherTrack() ||
                this.existsTrackConnectionFromStartToEnd())) {
            disconnectAndSafeDeletePointsIgnoreConditions();
        } else {
            throw new TrackIsOccupiedException();
        }
    }

    private void disconnectAndSafeDeletePointsIgnoreConditions() {
        START_POINT.removeTrack(START_POINT.getCoordinate().getDirectionToCoordinate(END_POINT.getCoordinate()));
        END_POINT.removeTrack(END_POINT.getCoordinate().getDirectionToCoordinate(START_POINT.getCoordinate()));
        TRACK_SYSTEM.safeRemovePoint(START_POINT.getCoordinate());
        TRACK_SYSTEM.safeRemovePoint(END_POINT.getCoordinate());
    }


    public boolean startAndEndPointIsConnectedToOtherTrack() {
        return !(this.getStartPoint().getNumberOfConnectionPoints() == 1 ||
                this.getCurrentEndPoint().getNumberOfConnectionPoints() == 1);
    }

    public Point getCurrentEndPoint() {
        return END_POINT;
    }

    @Override
    public Coordinate getStartCoordinate() {
        return START_POINT.getCoordinate();
    }

    @Override
    public Coordinate getCurrentEndCoordinate() {
        return END_POINT.getCoordinate();
    }

    @Override
    long getLength() {
        return getStartCoordinate().distanceToOtherCoordinate(getCurrentEndCoordinate());
    }

    @Override
    public boolean isOccupied() {
        return !trains.isEmpty();
    }

    @Override
    public boolean isOccupiedPut() {
        return isOccupied();
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Point getStartPoint() {
        return START_POINT;
    }


    private boolean existsTrackConnectionFromStartToEnd() {
        Track tracksToBeIgnored[] = new Track[1];
        tracksToBeIgnored[0] = this;
        HashSet<Point> listOfVisitedPoints = new HashSet<Point>();
        return this.getStartPoint().existsTrackConnectionToPoint(tracksToBeIgnored, listOfVisitedPoints, this.getCurrentEndPoint());

    }

    protected boolean existsTrackConnectionFromStartToEnd(Track... additionalTracksToBeIgnored) {
        Track tracksToBeIgnored[] = new Track[additionalTracksToBeIgnored.length + 1];
        for (int i = 0; i < additionalTracksToBeIgnored.length; i++) {
            tracksToBeIgnored[i + 1] = additionalTracksToBeIgnored[i];
        }
        tracksToBeIgnored[0] = this;
        HashSet<Point> listOfVisitedPoints = new HashSet<Point>();
        return this.getStartPoint().existsTrackConnectionToPoint(tracksToBeIgnored, listOfVisitedPoints, this.getCurrentEndPoint());

    }
    @Override
    public Train[] getTrains() {
        //TODO check if sorted right
        return trains.toArray(new Train[trains.size()]);
    }


}
