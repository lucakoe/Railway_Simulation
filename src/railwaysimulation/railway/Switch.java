package railwaysimulation.railway;


import railwaysimulation.exceptions.*;
import railwaysimulation.trains.Train;

import java.util.HashSet;
import java.util.TreeMap;

public class Switch extends TrackMaterial {
    private final TrackSystem TRACK_SYSTEM;
    Point startPoint;
    TreeMap<Coordinate, Track> tracks;
    Track currentTrack;
    private HashSet<Train> trains;

    Switch(TrackSystem trackSystem, int trackMaterialID, Coordinate startPointCoordinate, Coordinate... endPointCoordinates) throws WrongInputException, TrackMaterialCanNotBeAddedOrRemovedException, TrackIsOccupiedException {
        super(TrackMaterialTypes.SWITCH, trackMaterialID);
        this.tracks = new TreeMap<Coordinate, Track>();
        this.TRACK_SYSTEM = trackSystem;
        this.trains=new HashSet<Train>();
        if (TRACK_SYSTEM.getNumberOfTracks() == 0) {
            TRACK_SYSTEM.addPoint(startPointCoordinate);
            TRACK_SYSTEM.getPoint(startPointCoordinate).addToPossibleConnectionPoint();
        } else if ((TRACK_SYSTEM.pointExists(startPointCoordinate)
                && trackSystem.getPoint(startPointCoordinate).getNumberOfCreatableConnectionPoints() != 0)
                ||(TRACK_SYSTEM.pointExists(endPointCoordinates[0])
                &&trackSystem.getPoint(endPointCoordinates[0]).getNumberOfCreatableConnectionPoints() != 0)
                ||(TRACK_SYSTEM.pointExists(endPointCoordinates[1])
                &&trackSystem.getPoint(endPointCoordinates[1]).getNumberOfCreatableConnectionPoints() != 0)) {
            TRACK_SYSTEM.addPoint(startPointCoordinate);
            TRACK_SYSTEM.getPoint(startPointCoordinate).addToPossibleConnectionPoint();
        }
        else{
            throw new TrackMaterialCanNotBeAddedOrRemovedException();
        }
        TRACK_SYSTEM.getPoint(startPointCoordinate).addToPossibleConnectionPoint();


        for (Coordinate endPointCoordinate : endPointCoordinates) {
            try {
                tracks.put(endPointCoordinate, new Track(trackSystem, trackMaterialID, startPointCoordinate, endPointCoordinate));
                tracks.get(endPointCoordinate).setActivation(false);
                tracks.get(endPointCoordinate).addThisTrackToItsStartAndEndPoint();
            } catch (WrongInputException e) {
                for (Track track : tracks.values()) {
                    try {
                        track.disconnectAndSafeDeletePoints();
                    } catch (TrackIsOccupiedException ex) {
                        //can't happen since track was just created

                    }
                }
                trackSystem.getPoint(startPointCoordinate).removePossibleConnectionPoint();
                throw e;
            }
        }
        //doesn't matter which one
        startPoint = TRACK_SYSTEM.getPoint(startPointCoordinate);
    }

    @Override
    public String toString() {
        String endCoordinateString = new String();
        String lengthString = new String();
        if (!(this.getLength() == 0)) {
            lengthString = " " + this.getLength();
        }
        for (Track track : tracks.values()) {
            endCoordinateString += track.getCurrentEndCoordinate().toString() + ",";
        }
        //removes the last ","
        endCoordinateString = endCoordinateString.substring(0, endCoordinateString.length() - 1);
        return getTrackMaterialType().toString() + " " + this.getTrackMaterialID() + " " + this.getStartCoordinate().toString() + " -> " + endCoordinateString + lengthString;
    }

    @Override
    public void disconnectAndSafeDeletePoints() throws TrackIsOccupiedException, TrackMaterialCanNotBeAddedOrRemovedException, WrongInputException {
        boolean allTracksAreNotOccupied = true;
        boolean allTracksAreNotConnectedFromStartToEnd = false;
        int numberOfTracksAreNotConnectedFromStartToEnd = 0;
        boolean aTrackHasOtherTrackAtEndPoint = false;
        for (Track track : tracks.values()) {
            if (track.isOccupied()) {
                allTracksAreNotOccupied = false;
            }
            if (track.existsTrackConnectionFromStartToEnd(tracks.values().toArray(new Track[tracks.size()]))) {
                numberOfTracksAreNotConnectedFromStartToEnd++;
            }
            if (track.getCurrentEndPoint().getNumberOfConnectionPoints() != 1) {
                aTrackHasOtherTrackAtEndPoint = true;
            }
        }
        if (numberOfTracksAreNotConnectedFromStartToEnd < tracks.size()) {
            allTracksAreNotConnectedFromStartToEnd = true;
        }


        if (allTracksAreNotOccupied) {
            //TODO maybe check case removal of 180 switch (line) if there is an alternative route between both endpoints can be found
            //TODO check for mistakes


            if (!aTrackHasOtherTrackAtEndPoint ||
                    allTracksAreNotConnectedFromStartToEnd || existsTrackConnectionFromStartToEnd() || existsTrackConnectionFromEndToEnd()) {
                for (Track track : tracks.values()) {
                    track.disconnectAndSafeDeletePoints();
                    track.setActivation(false);
                }
                TRACK_SYSTEM.getPoint(getStartCoordinate()).removePossibleConnectionPoint();
            } else {
                String exceptionMessage = "track material is connected to other track material and can't be removed";
                throw new TrackMaterialCanNotBeAddedOrRemovedException();
            }

        } else {
            throw new TrackIsOccupiedException();
        }
    }


    protected boolean existsTrackConnectionFromStartToEnd() {
        Track[] tracksToBeIgnored = tracks.values().toArray(new Track[tracks.size()]);
        HashSet<Point> listOfVisitedPoints = new HashSet<Point>();
        return this.getStartPoint().existsTrackConnectionToPoint(tracksToBeIgnored, listOfVisitedPoints, this.getCurrentEndPoint());

    }

    protected boolean existsTrackConnectionFromEndToEnd() throws WrongInputException {
        //only works if switch has 2 endPoints (which is the default in this Exercise)
        if (getTrackMaterialType().getNumberOfEndPoints() == 2) {
            Track[] tracksToBeIgnored = tracks.values().toArray(new Track[tracks.size()]);
            HashSet<Point> listOfVisitedPoints = new HashSet<Point>();
            return tracksToBeIgnored[0].getCurrentEndPoint().existsTrackConnectionToPoint(tracksToBeIgnored, listOfVisitedPoints, tracksToBeIgnored[1].getCurrentEndPoint());
        } else {
            throw new WrongInputException("can't be called");
        }

    }


    public TreeMap<Integer, Train[]> set(Coordinate coordinateEndPoint) throws WrongCoordinatesException {
        TreeMap<Integer,Train[]> crashes=new TreeMap<Integer, Train[]>();
        if (tracks.containsKey(coordinateEndPoint)) {
            if (this.isSet()) {
                try {
                    if(currentTrack.getTrains().length!=0) {
                        throw new TrainCrashException(currentTrack.getTrains());
                    }
                } catch (TrainCrashException e) {
                    crashes.put(e.getTrains()[0].getID(),e.getTrains());

                }

                currentTrack.setActivation(false);
            }
            currentTrack = tracks.get(coordinateEndPoint);
            currentTrack.setActivation(true);
        }
        else {
            throw new WrongCoordinatesException();
        }
        return crashes;
    }

    public boolean isSet() {
        if (currentTrack != null) {
            return true;
        }
        return false;
    }

    @Override
    public Point getStartPoint() {
        return startPoint;
    }

    public Point getCurrentEndPoint() {
        if (isSet()) {
            return currentTrack.getCurrentEndPoint();
        }
        return null;

    }

    public Coordinate getStartCoordinate() {
        return tracks.get(tracks.firstKey()).getStartCoordinate();
    }

    @Override
    public Coordinate getCurrentEndCoordinate() {
        if (isSet()) {
            return currentTrack.getCurrentEndPoint().getCoordinate();
        }
        return null;
    }

    @Override
    long getLength() {
        if (isSet()) {
            return currentTrack.getLength();
        }
        return 0;
    }


    @Override
    public void addTrain(Train train) {
        if (isSet()) {
            currentTrack.addTrain(train);
        }

    }

    @Override
    public void removeTrain(Train train) {
        if (isSet()) {
            currentTrack.removeTrain(train);
        }
    }

    @Override
    public boolean isOccupied() {
        if (isSet()) {
            return currentTrack.isOccupied();
        }
        //TODO check if okay
        return false;
    }

    @Override
    public boolean isOccupiedPut() {
        return isOccupied();
    }

    @Override
    public boolean isActive() {
        if (isSet()) {
            return currentTrack.isActive();
        }
        //TODO check if works
        return false;
    }

    @Override
    public Train[] getTrains() {
        //TODO check if sorted right
        return trains.toArray(new Train[trains.size()]);
    }

}
