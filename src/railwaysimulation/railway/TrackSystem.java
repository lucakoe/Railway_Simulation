package railwaysimulation.railway;

import exceptions.TrainCanNotBePlacedException;
import railwaysimulation.exceptions.*;
import railwaysimulation.trains.Train;

import java.util.HashSet;
import java.util.TreeMap;

public class TrackSystem {

    private static final String NOT_AVAILABLE_MASSAGE_TRACKS = "tracks";
    final int trackMaterialIdFirstNumber = 1;
    TreeMap<Coordinate, Point> points;
    TreeMap<Integer, TrackMaterial> trackMaterials;

    public TrackSystem() {
        this.points = new TreeMap<Coordinate, Point>();
        this.trackMaterials = new TreeMap<Integer, TrackMaterial>();
    }

    //TODO remove or use methods
    /*
    private Track addAndReturnTrack(int trackMaterialID,Coordinate startCoordinate, Coordinate endCoordinate) throws IDDoesNotExistExcepiton, WrongInputException, TrackMaterialCanNotBeAddedOrRemovedException, NotAvailableException {

        CardinalDirection cardinalDirectionOfTrack = startCoordinate.getDirectionToCoordinate(endCoordinate);
        if (cardinalDirectionOfTrack != null) {
            //TODO add other conditions for adding
            boolean firstTrackCondition = getNumberOfTracks() == 0;
            //checks if there already is a point at the start/endpoint and if so, it has a free creatable connection
            //point (the point is not created yet and there is a creatable connection point available)
            boolean availableConnectionToOtherTrack = (points.containsKey(startCoordinate) &&
                    points.get(startCoordinate).creatableConnectionPointInDirection(cardinalDirectionOfTrack)) ||
                    (points.containsKey(endCoordinate) &&
                            points.get(endCoordinate).creatableConnectionPointInDirection(cardinalDirectionOfTrack.getOpposite()));

            Track track;
            if (firstTrackCondition || availableConnectionToOtherTrack) {
                addPoint(startCoordinate);
                addPoint(endCoordinate);
                //TODO check if its working properly without this condition
                //if (points.get(startCoordinate).getNumberOfCreatableConnectionPoints() != 0 &&
                //        points.get(endCoordinate).getNumberOfCreatableConnectionPoints() != 0) {


                track=new Track(trackMaterialID,points.get(startCoordinate), points.get(endCoordinate));

                try {
                    points.get(startCoordinate).setTrack(cardinalDirectionOfTrack, track);

                } catch (NotAvailableException e) {
                    throw e;
                }
                try{
                    points.get(endCoordinate).setTrack(cardinalDirectionOfTrack.getOpposite(), track);
                } catch (NotAvailableException e) {
                    points.get(startCoordinate).removeTrack(cardinalDirectionOfTrack);
                    throw e;
                }

                return track;
            }
            else {
                throw new TrackMaterialCanNotBeAddedOrRemovedException();
            }

        }
        else {
            throw new TrackMaterialCanNotBeAddedOrRemovedException();
        }

        return null;

    }
    private void removeTrack(Track track) throws TrackIsOccupiedException {
        if (!track.isOccupied() && (!track.startAndEndPointsAreConnectedToOtherTrack() ||
                this.existsTrackConnectionFromStartToEnd(track))) {

            track.getStartPoint().removeTrack(track.getStartPoint().getCoordinate().getDirectionToCoordinate(track.getCurrentEndPoint().getCoordinate()));
            track.getCurrentEndPoint().removeTrack(track.getCurrentEndPoint().getCoordinate().getDirectionToCoordinate(track.getStartPoint().getCoordinate()));
            safeRemovePoint(track.getStartPoint().getCoordinate());
            safeRemovePoint(track.getCurrentEndPoint().getCoordinate());
        } else {
            throw new TrackIsOccupiedException();
        }
    }
    public int addSwitch(int trackMaterialID,Coordinate startCoordinate, Coordinate... endCoordinates)throws WrongInputException{
        if (endCoordinates.length!=TrackMaterialTypes.SWITCH.getNumberOfEndPoints()){
            int rackMaterialID=getNextFreeTrackMaterialID();
            Track[] tracks=new Track[endCoordinates.length];
            for(int i=0;i<endCoordinates.length;i++){
                try {
                    addAndReturnTrack(trackMaterialID,startCoordinate,endCoordinates[i]);
                } catch (IDDoesNotExistExcepiton | NotAvailableException | TrackMaterialCanNotBeAddedOrRemovedException idDoesNotExistException) {
                    for (int j=0;j<i;j++){
                        try {
                            removeTrack(tracks[j]);
                        } catch (TrackIsOccupiedException) {
                            //can't happen
                        }

                    }
                    throw ;
                }
            }
        }
        else {
            throw new WrongInputException("Too many/not enough endpoint coordinates");
        }
    }

    public void removeTrackMaterial(int trackMaterialID) throws TrackIsOccupiedException, IDDoesNotExistExcepiton {
        if (trackMaterials.containsKey(trackMaterialID)){
            if (trackMaterials.get(trackMaterialID).getTrackMaterialType()==TrackMaterialTypes.TRACK){
                removeTrack((Track) trackMaterials.get(trackMaterialID));
            }
            else if(trackMaterials.get(trackMaterialID).getTrackMaterialType()==TrackMaterialTypes.SWITCH){

            }
        }
        else {
            throw new IDDoesNotExistExcepiton();
        }
    }
    */


    public int addTrack(Coordinate startPointCoordinate, Coordinate endPointCoordinate) throws WrongInputException, TrackMaterialCanNotBeAddedOrRemovedException {
        int trackID = getNextFreeTrackMaterialID();
        trackMaterials.put(trackID, new Track(this, trackID, startPointCoordinate, endPointCoordinate));
        ((Track) trackMaterials.get(trackID)).addThisTrackToItsStartAndEndPoint();
        return trackID;
    }

    public int addSwitch(Coordinate startPointCoordinate, Coordinate firstEndPointCoordinate, Coordinate secondEndPointCoordinate) throws WrongInputException, TrackIsOccupiedException, TrackMaterialCanNotBeAddedOrRemovedException {
        int trackID = getNextFreeTrackMaterialID();
        trackMaterials.put(trackID, new Switch(this, trackID, startPointCoordinate, firstEndPointCoordinate, secondEndPointCoordinate));
        return trackID;
    }

    public void deleteTrackMaterial(int trackMaterialID) throws WrongInputException, TrackIsOccupiedException, TrackMaterialCanNotBeAddedOrRemovedException {
        if (trackMaterials.containsKey(trackMaterialID)) {
            trackMaterials.get(trackMaterialID).disconnectAndSafeDeletePoints();
        }
    }

    protected void safeRemovePoint(Coordinate coordinate) {
        //if the point exists, it has no tracks and no ConnectionPoints it will be removed
        if (points.containsKey(coordinate) &&
                points.get(coordinate).getNumberOfConnectionPoints() == 0) {
            points.remove(coordinate);
        }
    }

    //TODO maybe remove
    private boolean existsTrackConnectionFromStartToEnd(Track track, Track... additionalTracksToBeIgnored) {
        Track tracksToBeIgnored[] = new Track[additionalTracksToBeIgnored.length + 1];
        for (int i = 0; i < additionalTracksToBeIgnored.length; i++) {
            tracksToBeIgnored[i + 1] = additionalTracksToBeIgnored[i];
        }
        tracksToBeIgnored[0] = track;
        HashSet<Point> listOfVisitedPoints = new HashSet<Point>();
        return track.getStartPoint().existsTrackConnectionToPoint(tracksToBeIgnored, listOfVisitedPoints, track.getCurrentEndPoint());

    }

    public boolean allSwitchesSet() {
        for (TrackMaterial trackMaterial : trackMaterials.values()) {
            if (trackMaterial.getTrackMaterialType() == TrackMaterialTypes.SWITCH) {
                if (!(((Switch) trackMaterial).isSet())) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void addPoint(Coordinate coordinate) {
        if (!points.containsKey(coordinate)) {
            points.put(coordinate, new Point(coordinate));
        }
    }

    protected boolean pointExists(Coordinate coordinate) {
        if (points.containsKey(coordinate)) {
            return true;
        }
        return false;
    }


    private int getNextFreeTrackMaterialID() {
        int countingVariable = trackMaterialIdFirstNumber;
        while (trackMaterials.containsKey(countingVariable)) {
            countingVariable++;
        }
        return countingVariable;
    }

    private TrackMaterial getTrackMaterial(int trackMaterialID) throws IDDoesNotExistExcepiton {
        if (0 <= trackMaterialID - trackMaterialIdFirstNumber &&
                trackMaterialID - trackMaterialIdFirstNumber < trackMaterials.size() &&
                trackMaterials.get(trackMaterialID) != null) {
            return trackMaterials.get(trackMaterialID - trackMaterialIdFirstNumber);
        } else {
            throw new IDDoesNotExistExcepiton("TrackMaterialID");
        }
    }

    public int getNumberOfTracks() {
        int out = 0;
        if (trackMaterials == null) {
            return out;
        } else {
            for (TrackMaterial trackMaterial : trackMaterials.values()) {
                if (trackMaterial != null) {
                    out++;
                }
            }
        }
        return out;
    }
    //TODO complete;

    public boolean containsDrivable(Coordinate coordinateOfPoint) {
        return getDrivable(coordinateOfPoint) != null;
    }

    public Drivable getDrivable(Coordinate coordinateOfPoint) {

        for (Point node : points.values()) {
            if (coordinateOfPoint.compareTo(node.getCoordinate()) == 0) {
                return node;
            }
        }
        for (TrackMaterial trackMaterial : trackMaterials.values()) {
            if (trackMaterial.getCurrentEndCoordinate().getDirectionToCoordinate(coordinateOfPoint)!=null&&trackMaterial.getStartCoordinate().getDirectionToCoordinate(coordinateOfPoint) == trackMaterial.getCurrentEndCoordinate().getDirectionToCoordinate(coordinateOfPoint).getOpposite()) {
                return trackMaterial;
            }
        }
        return null;
    }

    public TreeMap<Integer,Train[]> setSwitch(int trackID, Coordinate coordinate) throws IDDoesNotExistExcepiton, WrongCoordinatesException {
        TreeMap<Integer,Train[]> crashes=new TreeMap<Integer, Train[]>();
        if (trackMaterials.containsKey(trackID)&&trackMaterials.get(trackID).getTrackMaterialType()==TrackMaterialTypes.SWITCH){
            return ((Switch)trackMaterials.get(trackID)).set(coordinate);
        }
        return crashes;

    }

    protected boolean containsPoint(Coordinate coordinate) {
        if (points.containsKey(coordinate)) {
            return true;
        }
        return false;
    }

    protected Point getPoint(Coordinate coordinate) {
        if (containsPoint(coordinate)) {
            return points.get(coordinate);
        }
        return null;
    }

    public Coordinate getNextCoordinate(Coordinate currentCoordinate, CardinalDirection cardinalDirection) {
        switch (cardinalDirection) {
            case NORTH:
                return new Coordinate(currentCoordinate.getX(), currentCoordinate.getY() + 1);

            case EAST:
                return new Coordinate(currentCoordinate.getX() + 1, currentCoordinate.getY());

            case SOUTH:
                return new Coordinate(currentCoordinate.getX(), currentCoordinate.getY() - 1);

            case WEST:
                return new Coordinate(currentCoordinate.getX() - 1, currentCoordinate.getY());

            default:
                return currentCoordinate;

        }

    }

    public CardinalDirection getNextDirection(Coordinate currentCoordinate, CardinalDirection currentCardinalDirection) throws WrongCoordinatesException {
        if (containsDrivable(currentCoordinate)) {
            return getDrivable(currentCoordinate).getNextDirection(currentCardinalDirection);
        } else {
            throw new WrongCoordinatesException();
        }
    }

    //TODO maybe make toString
    public String listAllTracks() throws NotAvailableException {
        String out = new String();
        if (trackMaterials.isEmpty()) {
            throw new NotAvailableException(NOT_AVAILABLE_MASSAGE_TRACKS);
        }
        for (TrackMaterial trackMaterial : trackMaterials.values()) {
            if (trackMaterial != null) {
                out += trackMaterial.toString() + "\n";

            }
        }
        // removes last \n
        return out.substring(0, out.length() - 1);
    }

    //TODO remove
    public void putTrain(Train train, Coordinate coordinate, CardinalDirection cardinalDirection) throws TrainCanNotBePlacedException {
        if (getDrivable(train.getHeadCoordinate(Direction.FORWARD)) != null) {
            Coordinate checkHead = train.getHeadCoordinate(Direction.FORWARD);
            Drivable drivable = getDrivable(checkHead);
            CardinalDirection checkHeadCardinalDirection = drivable.getPreviousDirection(cardinalDirection);

            for (long i = 0; i < train.getLength(); i++) {

                drivable = getDrivable(checkHead);
                //TODO add conditon that the
                if (drivable != null || !drivable.isOccupiedPut()) {
                    checkHeadCardinalDirection = drivable.getNextDirection(checkHeadCardinalDirection);
                    checkHead = getNextCoordinate(checkHead, checkHeadCardinalDirection);
                } else {
                    throw new TrainCanNotBePlacedException();
                }
            }

        } else {
            throw new TrainCanNotBePlacedException();
        }


    }
}
