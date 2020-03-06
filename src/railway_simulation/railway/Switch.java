package railway_simulation.railway;

import railway_simulation.exceptions.TrackIsOccupiedException;
import railway_simulation.exceptions.TrackMaterialCanNotBeRemoved;
import railway_simulation.exceptions.WrongCoordinatesException;
import railway_simulation.exceptions.WrongInputException;

public class Switch implements TrackMaterial {
    private static int NUMBER_OF_SWITCHABLE_TRACKS = 2;
    private Track[] tracks;
    private TrackMaterial Type;
    private Track currentTrack;
    private final CoordinateSystemMap MAP;

    public Switch(CoordinateSystemMap map, Coordinate startPointCoordinate, Coordinate... endPointCoordinates) throws WrongCoordinatesException, WrongInputException {
        if (endPointCoordinates.length != NUMBER_OF_SWITCHABLE_TRACKS) {
            throw new WrongInputException("Too many/not enough endpoint coordinates");
        }
        tracks = new Track[endPointCoordinates.length];
        for (int i = 0; i < tracks.length; i++) {
            try {
                tracks[i] = new Track(map, startPointCoordinate, endPointCoordinates[i]);
                tracks[i].setActivation(false);
            } catch (WrongCoordinatesException e) {
                throw new WrongCoordinatesException();
            }
        }
        this.MAP=map;

    }

    public boolean isSet() {
        if (currentTrack != null) {
            return true;
        }
        return false;
    }

    public void set(Coordinate coordinate) throws WrongCoordinatesException {
        //TODO maybe implement collision check for trains on the track
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i].getEndPoint().getCoordinate().compareTo(coordinate) == 0) {
                if (this.isSet()) {
                    currentTrack.setActivation(false);
                }
                currentTrack = tracks[i];
                currentTrack.setActivation(true);

            } else {
                throw new WrongCoordinatesException();
            }
        }

    }

    private Track getActiveTrack() {
        return this.currentTrack;
    }


    //TODO implement methods


    @Override
    public Coordinate getStartCoordinate() {
        if (isSet()) {
            return currentTrack.getStartCoordinate();
        }
        return null;
    }

    @Override
    public Coordinate getEndCoordinate() {
        if (isSet()) {
            return currentTrack.getEndCoordinate();
        }
        return null;
    }

    @Override
    public boolean isOccupied() {
        if (isSet()) {
            return currentTrack.isOccupied();
        }
        return true;
    }

    @Override
    public void disconnectAndSafeDeletePoints() throws TrackIsOccupiedException, TrackMaterialCanNotBeRemoved {

        boolean allTracksAreNotOccupied = true;
        boolean allTracksAreNotConnectedFromStartToEnd=false;
        int numberOfTracksAreNotConnectedFromStartToEnd=0;
        boolean aTrackHasOtherTrackAtEndPoint=false;
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i].isOccupied()) {
                allTracksAreNotOccupied = false;
            }
            if (tracks[i].existsTrackConnectionFromStartToEnd(tracks)){
                numberOfTracksAreNotConnectedFromStartToEnd++;
            }
            if(tracks[i].getEndPoint().getNumberOfConnectionPoints()!=1){
                aTrackHasOtherTrackAtEndPoint=true;
            }
        }
        if(numberOfTracksAreNotConnectedFromStartToEnd<tracks.length){
            allTracksAreNotConnectedFromStartToEnd=true;
        }


        if (allTracksAreNotOccupied) {
            //TODO maybe check case removal of 180 switch (line) if there is an alternative route between both endpoints can be found
            //TODO check for mistakes
            if (getStartPoint().getNumberOfConnectionPoints()==NUMBER_OF_SWITCHABLE_TRACKS||
                    !aTrackHasOtherTrackAtEndPoint||
                    allTracksAreNotConnectedFromStartToEnd) {
                for (int i = 0; i < tracks.length; i++) {
                    tracks[i].disconnectAndSafeDeletePoints();
                    tracks[i].setActivation(false);
                }
            }
            else {
                String exceptionMessage="track material is connected to other track material and can't be removed";
                throw new TrackMaterialCanNotBeRemoved(exceptionMessage);
            }
        }

        else {
            throw new TrackIsOccupiedException();
        }


    }

    private Point getStartPoint(){
        return tracks[0].getStartPoint();
    }

    @Override
    public TrackMaterialTypes getTrackMaterialType() {
        return null;
    }

    @Override
    public boolean isActive() {
        if (isSet()) {
            return currentTrack.isActive();
        }
        return false;
    }

    @Override
    public long getLength() {
        if (!this.isSet()) {
            //TODO in the list representation exchange 0 with ""
            return 0;
        }
        return getActiveTrack().getLength();
    }

    @Override
    public int getTrackMaterialID() {
        return 0;
    }
}
