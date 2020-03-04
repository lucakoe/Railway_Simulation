package Railway;

import Exceptions.TrackIsOccupiedException;
import Exceptions.WrongCoordinatesException;
import Trains.Train;

import java.util.ArrayList;

public class Track {

    private ArrayList<Point> points;
    ArrayList<Train> occupied;
    boolean active;


    CoordinateSystemMap map;

    public Track(CoordinateSystemMap map, Coordinate startPoint, Coordinate endPoint) throws WrongCoordinatesException {
        this.map = map;
        this.points = new ArrayList<Point>();
        this.occupied=new ArrayList<Train>();
        if (startPoint == null || endPoint == null) {
            throw new WrongCoordinatesException();
        }

        Direction directionOfTrack = startPoint.getDirectionToCoordinate(endPoint);

        //TODO complete
        if (directionOfTrack != null) {
            //checks if this is the first map added (if so it can be placed anywhere)
            boolean firstTrackCondition = map.numberOfTracks() == 0;
            //checks if there already is a point at the start/endpoint and if so, it has a free creatable connection
            //point (the point is not created yet and there is a creatable connection point available)
            boolean availableConnectionToOtherTrack = (map.containsPoint(startPoint) &&
                    map.getPoint(startPoint).creatableConnectionPointInDirection(directionOfTrack)) ||
                    (map.containsPoint(endPoint) &&
                            map.getPoint(startPoint).creatableConnectionPointInDirection(directionOfTrack.getOpposite()));

            if (firstTrackCondition || availableConnectionToOtherTrack) {
                //TODO maybe switch case
                if (directionOfTrack == Direction.EAST) {
                    //TODO maybe remove code repetition
                    for (int i = 0; i < startPoint.distanceToOtherCoordinate(endPoint) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPoint.getX() + i, startPoint.getY());
                        map.addPoint(coordinate);
                        points.add(map.getPoint(coordinate));
                        points.get(i).assignTrack(this);
                    }
                } else if (directionOfTrack == Direction.WEST) {
                    for (int i = 0; i < startPoint.distanceToOtherCoordinate(endPoint) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPoint.getX() - i, startPoint.getY());
                        map.addPoint(coordinate);
                        points.add(map.getPoint(coordinate));
                        points.get(i).assignTrack(this);
                    }
                }
                if (directionOfTrack == Direction.NORTH) {
                    for (int i = 0; i < startPoint.distanceToOtherCoordinate(endPoint) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPoint.getX(), startPoint.getY() + i);
                        map.addPoint(coordinate);
                        points.add(map.getPoint(coordinate));
                        points.get(i).assignTrack(this);
                    }
                } else if (directionOfTrack == Direction.SOUTH) {
                    for (int i = 0; i < startPoint.distanceToOtherCoordinate(endPoint) + 1; i++) {
                        Coordinate coordinate = new Coordinate(startPoint.getX(), startPoint.getY() - i);
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

    public boolean isOccupied(){
        return !occupied.isEmpty();
    }

    public void disconnectAndSafeDeletePoints() throws TrackIsOccupiedException {

        if(!this.isOccupied()){
            for(int i=0;i<points.size()-1;i++){
                points.get(i).disconnectFromPoint(points.get(i+1));
                points.get(i).removeTrack(this);
                map.safeRemovePoint(points.get(i).getCoordinate());
            }
            getEndPoint().removeTrack(this);
            map.safeRemovePoint(getEndPoint().getCoordinate());
        }
        else {
            throw new TrackIsOccupiedException();
        }

    }

    public Point getStartPoint() {
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
}

