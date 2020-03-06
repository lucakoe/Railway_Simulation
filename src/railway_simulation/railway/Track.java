package railway_simulation.railway;

import railway_simulation.exceptions.TrackIsOccupiedException;
import railway_simulation.exceptions.WrongCoordinatesException;
import railway_simulation.trains.Train;

import java.util.ArrayList;
import java.util.HashSet;

public class Track implements TrackMaterial {

    private ArrayList<Point> points;
    ArrayList<Train> occupingTrains;
    boolean active;
    private final CoordinateSystemMap MAP;
    private final long LENGTH;


    private final TrackMaterialTypes TYPE = TrackMaterialTypes.TRACK;

    public Track(CoordinateSystemMap map, Coordinate startPointCoordinate, Coordinate endPointCoordinate) throws WrongCoordinatesException {
        this.MAP = map;
        this.points = new ArrayList<Point>();
        this.occupingTrains = new ArrayList<Train>();

        if (startPointCoordinate == null || endPointCoordinate == null) {
            throw new WrongCoordinatesException();
        }

        Direction directionOfTrack = startPointCoordinate.getDirectionToCoordinate(endPointCoordinate);

        //TODO complete
        if (directionOfTrack != null) {
            //checks if this is the first map added (if so it can be placed anywhere)
            boolean firstTrackCondition = map.numberOfTracks() == 0;
            //checks if there already is a point at the start/endpoint and if so, it has a free creatable connection
            //point (the point is not created yet and there is a creatable connection point available)
            boolean availableConnectionToOtherTrack = (map.containsPoint(startPointCoordinate) &&
                    map.getPoint(startPointCoordinate).creatableConnectionPointInDirection(directionOfTrack)) ||
                    (map.containsPoint(endPointCoordinate) &&
                            map.getPoint(endPointCoordinate).creatableConnectionPointInDirection(directionOfTrack.getOpposite()));

            if (firstTrackCondition || availableConnectionToOtherTrack) {
                //TODO maybe switch case
                if (directionOfTrack == Direction.EAST) {
                    //TODO maybe remove code repetition
                    for (int i = 0; i < startPointCoordinate.distanceToOtherCoordinate(endPointCoordinate) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPointCoordinate.getX() + i, startPointCoordinate.getY());
                        map.addPoint(coordinate);
                        points.add(map.getPoint(coordinate));
                        points.get(i).assignTrack(this);
                    }
                } else if (directionOfTrack == Direction.WEST) {
                    for (int i = 0; i < startPointCoordinate.distanceToOtherCoordinate(endPointCoordinate) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPointCoordinate.getX() - i, startPointCoordinate.getY());
                        map.addPoint(coordinate);
                        points.add(map.getPoint(coordinate));
                        points.get(i).assignTrack(this);
                    }
                }
                if (directionOfTrack == Direction.NORTH) {
                    for (int i = 0; i < startPointCoordinate.distanceToOtherCoordinate(endPointCoordinate) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPointCoordinate.getX(), startPointCoordinate.getY() + i);
                        map.addPoint(coordinate);
                        points.add(map.getPoint(coordinate));
                        points.get(i).assignTrack(this);
                    }
                } else if (directionOfTrack == Direction.SOUTH) {
                    for (int i = 0; i < startPointCoordinate.distanceToOtherCoordinate(endPointCoordinate) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPointCoordinate.getX(), startPointCoordinate.getY() - i);
                        map.addPoint(coordinate);
                        points.add(map.getPoint(coordinate));
                        points.get(i).assignTrack(this);
                    }
                }
                for (int i = 0; i < points.size() - 1; i++) {
                    //TODO if false disconnect and remove all the points, throw e
                    points.get(i).connectToPoint(points.get(i + 1));

                }
            } else {
                throw new WrongCoordinatesException();
            }
        } else {
            throw new WrongCoordinatesException();
        }

        this.LENGTH = this.getStartCoordinate().distanceToOtherCoordinate(this.getEndCoordinate());

    }

    public void setActivation(boolean activated) {
        for (int i = 0; i < points.size(); i++) {
            //TODO maybe only check start and endpoint
            //shows if any of the other of the Tracks the point is part of is active
            boolean anotherTrackActivated = false;
            //gets all the tracks of a point as array
            //TODO maybe put this method in Point, so no Track array has to be returned
            Track[] otherTracksOnPoint = points.get(i).getTracks();
            //looks for every track thats not this one if its active and if so sets anotherTrackActivated to true,
            //if not it stays at it's initial value false
            for (int j = 0; j < otherTracksOnPoint.length; j++) {
                if (otherTracksOnPoint[j].isActive() == true && otherTracksOnPoint[j] != this) {
                    anotherTrackActivated = true;
                }
            }

            /*
            sets point to true if ether this track is set to true of if any of the other tracks are true.
            if this track is set inactive (false), the points activation is set to false if no other track the point
            is part of is active.
             */
            points.get(i).setActive(activated || anotherTrackActivated);
            this.active = activated;
        }
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public long getLength() {
        return LENGTH;
    }

    @Override
    public int getTrackMaterialID() {
        return 0;
    }

    @Override
    public Coordinate getStartCoordinate() {
        return getStartPoint().getCoordinate();
    }

    @Override
    public Coordinate getEndCoordinate() {
        return getEndPoint().getCoordinate();
    }

    @Override
    public boolean isOccupied() {
        return !occupingTrains.isEmpty();
    }

    @Override
    public void disconnectAndSafeDeletePoints() throws TrackIsOccupiedException {
        if (!this.isOccupied()&&(!this.startAndEndPointIsConnectedToOtherTrack()||
                this.existsTrackConnectionFromStartToEnd())) {
            disconnectAndSafeDeletePointsIgnoreConditions();
        } else {
            throw new TrackIsOccupiedException();
        }

    }

    // for Switches
    public void disconnectAndSafeDeletePointsIgnoreConditions() {
        //TODO if this method's conditons change switches disconnectAndSafeDeletePoints
        //TODO add deep search algorithm to look if points can be disconnected without creating a seperated Track



            for (int i = 0; i < points.size() - 1; i++) {
                points.get(i).disconnectFromPoint(points.get(i + 1));
                points.get(i).removeTrack(this);
                MAP.safeRemovePoint(points.get(i).getCoordinate());
            }
            getEndPoint().removeTrack(this);
            MAP.safeRemovePoint(getEndPoint().getCoordinate());


    }

    public boolean startAndEndPointIsConnectedToOtherTrack() {
        return !(this.getStartPoint().getNumberOfConnectionPoints() == 1 ||
                this.getEndPoint().getNumberOfConnectionPoints() == 1);
    }

    //TODO complete Depth-First Search

    private boolean existsTrackConnectionFromStartToEnd() {
        return this.existsTrackConnectionFromStartToEnd(this);

    }
    //for switch removal
    protected boolean existsTrackConnectionFromStartToEnd(Track... additionalTracksToBeIgnored) {
        Track tracksToBeIgnored[] =new Track[additionalTracksToBeIgnored.length+1];
        for(int i =0;i<additionalTracksToBeIgnored.length;i++){
            tracksToBeIgnored[i+1]=additionalTracksToBeIgnored[i];
        }
        tracksToBeIgnored[0]=this;
        HashSet<Point> listOfVisitedPoints = new HashSet<Point>();
        return this.getStartPoint().existsTrackConnectionToPoint(tracksToBeIgnored, listOfVisitedPoints, this.getEndPoint());

    }


    //as parameter you enter ether the start or the endpoint and recived the other point.
    //if the point entered is nether the start nor the end point, it returns null
    protected Point getStartOrEndPoint(Point startOrEndPoint) {
        if (getStartPoint() != null && startOrEndPoint == getStartPoint()) {
            return getEndPoint();
        } else if (getEndPoint() != null && startOrEndPoint == getEndPoint()) {
            return getStartPoint();
        }
        return null;
    }

    protected Point getStartPoint() {
        if (points.isEmpty()) {
            return null;
        } else {
            return points.get(0);
        }

    }

    public Point getEndPoint() {
        if (points.isEmpty()) {
            return null;
        } else {
            return points.get(points.size() - 1);
        }

    }

    @Override
    public TrackMaterialTypes getTrackMaterialType() {
        return TYPE;
    }
}

