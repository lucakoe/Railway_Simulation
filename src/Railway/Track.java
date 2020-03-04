package Railway;

import Trains.Train;

import java.util.ArrayList;

public class Track {

    private ArrayList<Point> points;
    Train occupied;

    CoordinateSystemMap map;

    public Track(CoordinateSystemMap map, Coordinate startPoint, Coordinate endPoint) {
        this.map = map;
        this.points = new ArrayList<Point>();
        Direction directionOfTrack;



        if (startPoint.getDirectionToCoordinate(endPoint) != null ) {

            directionOfTrack = startPoint.getDirectionToCoordinate(endPoint);
            boolean firstTrackCondition = map.numberOfTracks() == 0;
            boolean availableConnectionToOtherTrack = (map.containsPoint(startPoint) &&
                    map.getPoint(startPoint).connectionPointInDirectionIsFree(directionOfTrack)) ||
                    (map.containsPoint(endPoint) &&
                            map.getPoint(startPoint).connectionPointInDirectionIsFree(directionOfTrack.getOpposite()));

            if (firstTrackCondition || availableConnectionToOtherTrack) {
                //TODO maybe switch case
                if (directionOfTrack == Direction.EAST) {
                    //TODO maybe remove code repition
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
            }


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

