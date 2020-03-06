package railway_simulation.railway;

import railway_simulation.exceptions.TrackIsOccupiedException;
import railway_simulation.exceptions.WrongCoordinatesException;

import java.util.ArrayList;
import java.util.TreeMap;

public class CoordinateSystemMap {
    private TreeMap<Integer, TreeMap<Integer, Point>> map;
    //TODO change!!!
    public ArrayList<Track> tracks;


    public CoordinateSystemMap() {
        this.map = new TreeMap<Integer, TreeMap<Integer, Point>>();
        this.tracks = new ArrayList<Track>();
    }

    //TODO maybe implement exception instead of boolean
    //TODO Maybe seperate add and replace and don't let other classes add points but instead only led CoodinateSystemMap add them
    /*
    public boolean addOrReplacePoint(Coordinate coordinate, Point point) {
        if (!map.containsKey(coordinate.getX())) {
            map.put(coordinate.getX(), new TreeMap<Integer, Point>());
            map.get(coordinate.getX()).put(coordinate.getY(), point);
        } else if (map.containsKey(coordinate.getX()) && !map.get(coordinate.getX()).containsKey(coordinate.getY())) {
            map.get(coordinate.getX()).put(coordinate.getY(), point);
        } else if (map.containsKey(coordinate.getX()) && map.get(coordinate.getX()).containsKey(coordinate.getY())) {
            //TODO check if it works or if you have to insert the old value
            map.get(coordinate.getX()).replace(coordinate.getY(), point);

        } else {
            return false;
        }
        return true;

    }
     */


    public void addTrack(Coordinate startPointCoordinate, Coordinate endPointCoordinate) throws WrongCoordinatesException {
        Track track;
        try {
            track = new Track(this, startPointCoordinate, endPointCoordinate);
        } catch (WrongCoordinatesException e) {
            throw new WrongCoordinatesException();
        }
        int countingVariable=0;
        while (countingVariable<tracks.size()&&tracks.get(countingVariable)!=null){
            countingVariable++;
        }
        if (countingVariable==tracks.size()){
            tracks.add(null);
        }
        tracks.set(countingVariable,track);



    }

    public boolean removeTrack(Track track) {

        try {
            track.disconnectAndSafeDeletePoints();
        } catch (TrackIsOccupiedException e) {
            return false;
        }
        tracks.set (track.getTrackMaterialID()-1,null);
        return true;

    }

    //adds Points if they dont exist, does nothing if they exist
    public void addPoint(Coordinate coordinate) {
        if (!map.containsKey(coordinate.getX())) {
            map.put(coordinate.getX(), new TreeMap<Integer, Point>());
            map.get(coordinate.getX()).put(coordinate.getY(), new Point(coordinate));
        } else if (map.containsKey(coordinate.getX()) && !map.get(coordinate.getX()).containsKey(coordinate.getY())) {
            map.get(coordinate.getX()).put(coordinate.getY(), new Point(coordinate));
        }
    }

    public void safeRemovePoint(Coordinate coordinate) {
        //if the point exists, it has no tracks and no ConnectionPoints it will be removed
        if (this.containsPoint(coordinate) &&
                this.getPoint(coordinate).getTracks().length == 0 &&
                this.getPoint(coordinate).getNumberOfConnectionPoints() == 0) {
            map.get(coordinate.getX()).remove(coordinate.getY());
        }
    }

    public Point getPoint(Coordinate coordinate) {
        if (containsPoint(coordinate)) {
            return map.get(coordinate.getX()).get(coordinate.getY());
        } else {
            return null;
        }
    }


    public int numberOfTracks() {
        if (tracks == null) {
            return 0;
        }
        return tracks.size();
    }



    //TODO maybe implement exception instead of boolean
    public boolean removePoint(Coordinate coordinate) {
        if (containsPoint(coordinate)) {
            //TODO check if it works or if you have to insert the old value
            map.get(coordinate.getX()).remove(coordinate.getY());
            return true;
        }
        return false;
    }

    public boolean containsPoint(Coordinate coordinate) {
        if (!map.containsKey(coordinate.getX())) {
            return false;
        } else if (map.containsKey(coordinate.getX()) && !map.get(coordinate.getX()).containsKey(coordinate.getY())) {
            return false;
        } else if (map.containsKey(coordinate.getX()) && map.get(coordinate.getX()).containsKey(coordinate.getY())
                && (map.get(coordinate.getX()).get(coordinate.getY()) != null)) {
            return true;
        } else {
            return false;
        }
    }

}
