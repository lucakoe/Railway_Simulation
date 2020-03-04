package Railway;

import Trains.Train;

import java.util.ArrayList;
import java.util.TreeMap;


//TODO maybe change to rail
public class Point implements Comparable<Point> {
    private final int NUMBER_OF_POSSIBLE_RAILWAY_CONNECTIONS = 2;
    private Coordinate coordinate;
    private Train occupant;
    private TreeMap<Direction, Point> connectionPoints;
    private ArrayList<Track> tracks;
    private boolean active;

    //TODO Maybe add Number of possible railway connections in constructor for switch
    public Point(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.connectionPoints = new TreeMap<Direction, Point>();
        this.tracks=new ArrayList<Track>();
    }

    @Override
    public int compareTo(Point point) {
        /*
        if the x value is equivalent the point with the higher y value is bigger,
        else the one with the higher x value is bigger, and if x and y values are the same, the points are the same.
        since in my use case I only want to check lines parallel to the x-/y-axis,
        it will compare the x value if horizontal and for the y value if vertical
         */
        return this.getCoordinate().compareTo(point.getCoordinate());
    }

    public Direction getNextMovingDirection(Direction currentMovingDirection) {
        if (connectionPoints.containsKey(Direction.NORTH) && currentMovingDirection != Direction.NORTH) {
            return Direction.NORTH;
        } else if (connectionPoints.containsKey(Direction.SOUTH) && currentMovingDirection != Direction.SOUTH) {
            return Direction.SOUTH;
        } else if (connectionPoints.containsKey(Direction.EAST) && currentMovingDirection != Direction.EAST) {
            return Direction.EAST;
        } else if (connectionPoints.containsKey(Direction.WEST) && currentMovingDirection != Direction.WEST) {
            return Direction.WEST;
        } else {
            return currentMovingDirection;
        }
    }

    public int getX() {
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean connectionPointInDirectionIsFree(Direction direction){
        if (connectionPoints.containsKey(direction)&&connectionPoints.get(direction)==null){
            return true;
        }
        return false;
    }

    public Point getConnectionPoint(Direction direction) {
        if (connectionPoints.containsKey(direction)) {
            return connectionPoints.get(direction);
        }
        return null;
        //TODO rename or integrate isActive check in Train/RollingStock (and set get Point to protected)
    /*
    public Point getConnectionPoint(Direction direction) {
        if (connectionPoints.containsKey(direction) && connectionPoints.get(direction).isActive()) {
            return connectionPoints.get(direction);
        }
        return null;
    }

    */
    }

    protected int getNumberOfFreeConnectionPoints() {
        return (NUMBER_OF_POSSIBLE_RAILWAY_CONNECTIONS - connectionPoints.size());
    }



    public boolean isActive() {
        return active;
    }
    public boolean assignTrack(Track track){
        tracks.add(track);
        return true;
    }

    //TODO Carefull with this method could cause problems
    public boolean connectToPoint(Point point) {
        Direction direction = this.getCoordinate().getDirectionToCoordinate(point.getCoordinate());
        /*
        checks if both points have more than zero connections. the second point is allowed to have connections >=0
        if the point in the connection is the same as the one that called the method, because otherwise the connection
        point already set in the first point would prevent the method from executing even though, it's the same
        connection as the one trying to be created by the second one
        */
        boolean freeConnectionPointsAvailable = this.getNumberOfFreeConnectionPoints() > 0 &&
                (point.getNumberOfFreeConnectionPoints() > 0 ||
                        (point.getNumberOfFreeConnectionPoints() >= 0 &&
                                point.getConnectionPoint(direction.getOpposite()) == this));
        /*
        checks if the ConnectionPoint in the direction of the other Point is free and if the ConnectionPoint of the
        other Point is free or has a reference to the first Point
         */
        boolean adjacentConnectionPointsAvailable = this.getConnectionPoint(direction) == null &&
                ((point.getConnectionPoint(direction.getOpposite()) == null) ||
                        point.getConnectionPoint(direction.getOpposite()) == this);

        /*
        If the requirements above are meet, the reference to the second point is written into the ConnectionPoint and
        the same method is called in the other point. Since the own direction is null and the other point referenced in
        the first point is the one that called the method it will set the connection point in the second point to a
        reference to the first point, which than calls the connectToPoint method in the first point again, which will
        not execute, because the connection point asked to connect to is already full.
        */
        if (freeConnectionPointsAvailable && adjacentConnectionPointsAvailable) {
            setConnectionPoint(direction, point);
            point.connectToPoint(this);
            return true;
        }
        return false;
    }

    public boolean disconnectFromPoint(Point point) {
        /*
        same procedure as connectToPoint just in the opposite direction; checks if point in this direction is the same
        (as in same reference) as the point you want to remove. it removes the point and calls the method in the other
        point which will do the same since the other reference is null or the own reference. It than calls the method
        again in the first point, which will not call the method again, since it has no connection point with the
        reference of the first point.
         */
        Direction direction = this.getCoordinate().getDirectionToCoordinate(point.getCoordinate());
        if (this.getConnectionPoint(direction) == point && (point.getConnectionPoint(direction.getOpposite()) == this
                || point.getConnectionPoint(direction.getOpposite()) == null)) {
            setConnectionPoint(direction, null);
            point.disconnectFromPoint(this);
            return true;
        }
        return false;
    }



    //should not be used outside of connectPoint
    private void setConnectionPoint(Direction direction, Point point) {
        if (point == null) {
            connectionPoints.remove(direction);
        } else if (connectionPoints.containsKey(direction)) {
            connectionPoints.replace(direction, point);
        } else {
            connectionPoints.put(direction, point);
        }

    }

    public boolean isOccupied() {
        if (occupant != null) {
            return true;
        } else {
            return false;
        }
    }
}

