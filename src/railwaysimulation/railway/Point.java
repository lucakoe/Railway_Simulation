package railwaysimulation.railway;

import railwaysimulation.exceptions.NotAvailableException;
import railwaysimulation.trains.Train;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;

public class Point implements Drivable {
    private int numberOfPossibleConnectionPoints;
    private static final int NUMBER_OF_MAX_CONNECTION_POINTS = 4;
    private Coordinate coordinate;
    private TreeMap<CardinalDirection, Track> connectionPoints;
    private HashSet<Train> trains;

    public Point(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.connectionPoints = new TreeMap<CardinalDirection, Track>();
        this.trains = new HashSet<Train>();
        /*
        the number of possible Connetions is by default 2* the number of end points of a track, since by default a
        point should just connect to point, if a switch is added this number will be raised by 1, so 2 new tracks
        can be added of which still only one is active
        */
        this.numberOfPossibleConnectionPoints = 2 * TrackMaterialTypes.TRACK.getNumberOfEndPoints();
    }

    protected void addToPossibleConnectionPoint() {
        //TODO connectionpoints have to be added before switch can connect
        if (numberOfPossibleConnectionPoints + 1 <= NUMBER_OF_MAX_CONNECTION_POINTS) {
            numberOfPossibleConnectionPoints++;
        }

    }

    protected void removePossibleConnectionPoint() {
        //TODO connectionpoints have to be removed after an switch disconnects
        if (numberOfPossibleConnectionPoints - 1 >= getNumberOfConnectionPoints()) {
            numberOfPossibleConnectionPoints--;
        }
    }

    public void setTrack(CardinalDirection cardinalDirectionOfTrack, Track track) throws NotAvailableException {
        if (connectionPoints.containsKey(cardinalDirectionOfTrack)) {
            throw new NotAvailableException("connection point");
        }
        connectionPoints.put(cardinalDirectionOfTrack, track);
    }

    public void removeTrack(CardinalDirection cardinalDirectionOfTrack) {
        connectionPoints.remove(connectionPoints.get(cardinalDirectionOfTrack));
    }


    protected int getNumberOfCreatableConnectionPoints() {
        return (numberOfPossibleConnectionPoints - connectionPoints.size());
    }

    public boolean creatableConnectionPointInDirection(CardinalDirection cardinalDirection) {
        if (!connectionPoints.containsKey(cardinalDirection) && getNumberOfCreatableConnectionPoints() > 0) {
            return true;
        }
        return false;
    }

    public boolean existsTrackConnectionToPoint(Track[] trackToBeIgnored, Collection<Point> list, Point endPoint) {
        list.add(this);
        if (this == endPoint) {
            return true;
        }
        for (Track track : this.connectionPoints.values()) {
            for (int i = 0; i < trackToBeIgnored.length; i++) {
                if (track != trackToBeIgnored[i] && !list.contains(track.getStartOrEndPoint(this))) {
                    if (track.getStartOrEndPoint(this).existsTrackConnectionToPoint(trackToBeIgnored, list, endPoint)) {
                        return true;
                    }
                }
            }


        }
        return false;
    }

    public int getNumberOfConnectionPoints() {
        return connectionPoints.size();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }


    @Override
    public CardinalDirection getNextDirection(CardinalDirection currentCardinalDirection) {
        //if a track in the driving direction is available than it returns the current direction
        if (connectionPoints.containsKey(currentCardinalDirection)){
            return currentCardinalDirection;
        }
        //if not it looks trough the available connections and returns the one, that's not the direction the train is
        //coming from and that's active (so no inactive switch track)
        for (CardinalDirection cardinalDirection : CardinalDirection.values()) {
            if (connectionPoints.containsKey(cardinalDirection) &&
                    currentCardinalDirection != cardinalDirection.getOpposite() &&
                    connectionPoints.get(cardinalDirection).isActive()) {
                return cardinalDirection;
            }
        }
        return currentCardinalDirection;

    }


    @Override
    public CardinalDirection getPreviousDirection(CardinalDirection currentCardinalDirection) {
        return null;
    }

    @Override
    public void addTrain(Train train) {
        trains.add(train);
    }

    @Override
    public void removeTrain(Train train) {
        trains.remove(train);
    }

    @Override
    public Train[] getTrains() {
        //TODO check if sorted right
        return trains.toArray(new Train[trains.size()]);
    }

    @Override
    public boolean isOccupied() {
        return !trains.isEmpty();
    }

    @Override
    public boolean isOccupiedPut() {
        for (Track track : connectionPoints.values()) {
            if (track.isOccupied()) {
                return true;
            }
        }
        if (this.isOccupied()) {
            return true;
        }
        return false;
    }
}
