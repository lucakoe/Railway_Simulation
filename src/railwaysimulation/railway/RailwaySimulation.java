package railwaysimulation.railway;


import exceptions.CouplingConfigurationInvalidException;
import exceptions.NotAllSwitchesSetException;
import exceptions.TrainCanNotBePlacedException;
import railwaysimulation.exceptions.*;

import railwaysimulation.trains.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RailwaySimulation {
    private static final String CRASH_MESSAGE_OF_TRAIN_WITHOUT_IDS = "Crash of train";
    private static final String NO_ENGINE_EXIST_MESSAGE = "No engine exists";
    private static final String NO_COACH_EXIST_MESSAGE = "No coach exists";
    private static final String NO_TRAIN_SET_EXIST_MESSAGE = "No train-set exists";
    private static final String NO_TRAIN_EXIST_MESSAGE = "No train exists";

    TrackSystem trackSystem;
    TrainHangar trainHangar;
    ArrayList<Train> placedTrains;

    public RailwaySimulation() {
        this.trackSystem = new TrackSystem();
        this.trainHangar = new TrainHangar();
        this.placedTrains = new ArrayList<Train>();
    }


    public int addTrack(Coordinate startPointCoordinate, Coordinate endPointCoordinate) throws WrongInputException, TrackMaterialCanNotBeAddedOrRemovedException {
        return trackSystem.addTrack(startPointCoordinate, endPointCoordinate);

    }

    public int addSwitch(Coordinate startPointCoordinate, Coordinate firstEndPointCoordinate, Coordinate secondEndPointCoordinate) throws WrongInputException, TrackIsOccupiedException, TrackMaterialCanNotBeAddedOrRemovedException {
        return trackSystem.addSwitch(startPointCoordinate, firstEndPointCoordinate, secondEndPointCoordinate);

    }

    public void deleteTrack(int trackID) throws WrongInputException, TrackIsOccupiedException, TrackMaterialCanNotBeAddedOrRemovedException {
        trackSystem.deleteTrackMaterial(trackID);

    }

    public String listTracks() {
        String out = NO_TRACKS_EXIST_MESSAGE;
        try {
            out = trackSystem.listAllTracks();
        } catch (Exception e) {
            out = NO_TRACKS_EXIST_MESSAGE;
        }
        return out;
    }




    public String createEngine(EngineTypes engineType, String engineClass, String name, long length, boolean couplingAvailabilityFront, boolean couplingAvailabilityBack) throws IDAlreadyExistsException, CouplingConfigurationInvalidException {
        return trainHangar.createEngine(engineType, engineClass, name, length, couplingAvailabilityFront, couplingAvailabilityBack);
    }

    public String listEngines() {
        try {
            return trainHangar.listRollingStock(RollingStockTypes.ENGINE);
        } catch (NotAvailableException e) {
            return NO_ENGINE_EXIST_MESSAGE;
        }
    }

    public String createCoach(CoachTypes coachType, long length, boolean couplingAvailabilityFront, boolean couplingAvailabilityBack) throws IDAlreadyExistsException, CouplingConfigurationInvalidException {
        return trainHangar.createCoach(coachType, length, couplingAvailabilityFront, couplingAvailabilityBack);
    }

    public String listCoaches() {
        try {
            return trainHangar.listRollingStock(RollingStockTypes.COACH);
        } catch (NotAvailableException e) {
            return NO_COACH_EXIST_MESSAGE;
        }
    }

    public String createTrainSet(String trainSetClass, String name, long length, boolean couplingAvailabilityFront, boolean couplingAvailabilityBack) throws IDAlreadyExistsException, CouplingConfigurationInvalidException {
        return trainHangar.createTrainSet(trainSetClass, name, length, couplingAvailabilityFront, couplingAvailabilityBack);
    }

    public String listTrainSets() {
        try {
            return trainHangar.listRollingStock(RollingStockTypes.TRAIN_SET);
        } catch (NotAvailableException e) {
            return NO_TRAIN_SET_EXIST_MESSAGE;
        }
    }

    public void deleteRollingStock(String rollingStockID) throws RollingStockCouldntBeenAddedOrRemovedException, IDDoesNotExistExcepiton {
        trainHangar.deleteRollingStock(rollingStockID);
    }

    public String addTrain(int trainID, String rollingStockID) throws RollingStockCouldntBeenAddedOrRemovedException, IDDoesNotExistExcepiton {
        return trainHangar.addTrain(trainID, rollingStockID);
    }

    public void deleteTrain(int trainID) throws IDDoesNotExistExcepiton {
        trainHangar.deleteTrainFromHangar(trainID);
    }

    public String listTrains() {
        try {
            return trainHangar.listTrains();
        } catch (NotAvailableException e) {
            return NO_TRAIN_EXIST_MESSAGE;
        }
    }

    public String showTrain(int trainID) throws IDDoesNotExistExcepiton {
        return trainHangar.showTrain(trainID);
    }

    public String setSwitch(int trainID, Coordinate coordinate) throws IDDoesNotExistExcepiton, WrongCoordinatesException {
        TreeMap<Integer,Train[]> crashes = trackSystem.setSwitch(trainID,coordinate);
        String crashMassage=new String();
        for (Train[] crash:crashes.descendingMap().values()){
            for (Train train:crash){
                derail(train);
            }
        }

        for (int i : crashes.keySet()) {
            //the crash messages are fabricated and get added to the out message with the lowest id (the trains
            //of the crashes get sorted in the Train crash exception and saved in the array it return) as key
            crashMassage = CRASH_MESSAGE_OF_TRAIN_WITHOUT_IDS;
            for (int j = 0; j < crashes.get(i).length - 1; j++) {
                crashMassage += " " + crashes.get(i)[j].getID() + ",";
            }
            crashMassage += " " + crashes.get(crashes.size() - 1);

        }

        return crashMassage;

    }

    public void putTrain(int trainID, Coordinate coordinate, long directionX, long directionY) throws TrainIsInvalidException, IDDoesNotExistExcepiton, TrainCanNotBePlacedException {
        CardinalDirection cardinalDirection = CardinalDirection.getDirectionFromVector(directionX, directionY);

        if (cardinalDirection==null||!trackSystem.allSwitchesSet()){
            throw new TrainCanNotBePlacedException();
        }

        Train train=trainHangar.getTrain(trainID);
        train.setHeadCoordinate(Direction.FORWARD,coordinate);

        if (trackSystem.getDrivable(train.getHeadCoordinate(Direction.FORWARD)) != null) {
            Coordinate checkHead = train.getHeadCoordinate(Direction.FORWARD);
            Drivable drivable = trackSystem.getDrivable(checkHead);
            CardinalDirection checkHeadCardinalDirection = drivable.getPreviousDirection(cardinalDirection);

            for (long i = 0; i < train.getLength(); i++) {

                drivable = trackSystem.getDrivable(checkHead);
                if (drivable != null || !drivable.isOccupiedPut()) {
                    checkHeadCardinalDirection = drivable.getNextDirection(checkHeadCardinalDirection);
                    checkHead = trackSystem.getNextCoordinate(checkHead, checkHeadCardinalDirection);
                } else {
                    train.setHeadCoordinate(Direction.FORWARD,null);
                    throw new TrainCanNotBePlacedException();
                }
            }
            checkHead = train.getHeadCoordinate(Direction.FORWARD);
            drivable = trackSystem.getDrivable(checkHead);
            //if both directions are null, this means the initial direction is wrong
            if (trackSystem.getNextCoordinate(checkHead,cardinalDirection)==null
                    &&trackSystem.getNextCoordinate(checkHead,drivable.getNextDirection(cardinalDirection))==null){
                train.setHeadCoordinate(Direction.FORWARD,null);
                throw new TrainCanNotBePlacedException();
            }
            trackSystem.getNextCoordinate(checkHead,drivable.getNextDirection(cardinalDirection));
            checkHeadCardinalDirection = drivable.getPreviousDirection(cardinalDirection);
            for (long i = 0; i < train.getLength(); i++) {

                drivable = trackSystem.getDrivable(checkHead);
                drivable.addTrain(train);
                checkHeadCardinalDirection = drivable.getNextDirection(checkHeadCardinalDirection);
                checkHead = trackSystem.getNextCoordinate(checkHead, checkHeadCardinalDirection);
                if (i == train.getLength() - 1) {
                    train.setHeadCoordinate(Direction.BACKWARD, checkHead);
                }
            }
            this.placedTrains.add(train);
            train.setPlaced(true);

        } else {
            train.setHeadCoordinate(Direction.FORWARD,null);
            throw new TrainCanNotBePlacedException();
        }


        //TODO test
    }


    public String step(short step) throws NotAllSwitchesSetException {
        Direction direction;
        String out = new String();
        TreeMap<Integer, Train[]> crashes = new TreeMap<Integer, Train[]>();
        TreeMap<Integer, String> outMassages = new TreeMap<Integer, String>();

        if (step < 0) {
            direction = Direction.BACKWARD;
        } else if (step > 0) {
            direction = Direction.FORWARD;
        } else {
            return listAllPlacedTrainsLocations();
        }

        for (short i = 0; i < Math.abs(step); i++) {
            //single Steps get executed and the Crashes are saved in the crashes map
            crashes.putAll(singleStep(direction));
        }
        for (Train placedTrain : placedTrains) {
            //all the info of trains still on the map are added to the outMassages with their id as key
            outMassages.put(placedTrain.getID(), listPlacedTrainLocation(placedTrain));
        }

        for (int i : crashes.keySet()) {
            //the crash messages are fabricated and get added to the out message with the lowest id (the trains
            //of the crashes get sorted in the Train crash exception and saved in the array it return) as key
            String crashMassage = CRASH_MESSAGE_OF_TRAIN_WITHOUT_IDS;
            for (int j = 0; j < crashes.get(i).length - 1; j++) {
                crashMassage += " " + crashes.get(i)[j].getID() + ",";
            }
            crashMassage += " " + crashes.get(crashes.size() - 1);
            outMassages.put(i, crashMassage);
        }

        for (int i : outMassages.descendingKeySet()) {
            out += outMassages.get(i) + "\n";
        }
        return out;
    }

    private Map<Integer, Train[]> singleStep(Direction direction) throws NotAllSwitchesSetException {
        TreeMap<Integer, Train[]> crashes = new TreeMap<Integer, Train[]>();
        if (trackSystem.allSwitchesSet()){
            for (Train train : placedTrains) {
                try {
                    move(train, direction);
                } catch (TrainCrashException e) {
                    for (Train train1 : e.getTrains()) {
                        derail(train1);
                    }
                    //because the train array thrown by the exception is sorted by the lowest trainID, the first id will be
                    //the key for the map
                    crashes.put(e.getTrains()[0].getID(), e.getTrains());
                }
            }
            crashes.putAll(checkForCollision());
        }
        else {
            throw new NotAllSwitchesSetException();
        }


        return crashes;
    }




    private TreeMap<Integer,Train[]> checkForCollision() {
        //TODO test
        TreeMap<Integer,Train[]> crashes=new TreeMap<Integer, Train[]>();
        for (Train train:placedTrains){
            Drivable drivable = trackSystem.getDrivable(train.getHeadCoordinate(Direction.FORWARD));
            drivable.addTrain(train);
            CardinalDirection cardinalDirection = drivable.getPreviousDirection(train.getCurrentCardinalDirection());
            Coordinate checkHead = train.getHeadCoordinate(Direction.FORWARD);
            for (long i = 0; i < train.getLength() ; i++) {
                cardinalDirection = drivable.getNextDirection(cardinalDirection);
                Train[] crash=drivable.getTrains();
                if (crash.length>1){
                    try {
                        throw new TrainCrashException(crash);
                    } catch (TrainCrashException e) {
                        for (Train train1 : e.getTrains()) {
                            derail(train1);
                        }
                        //because the train array thrown by the exception is sorted by the lowest trainID, the first id will be
                        //the key for the map
                        crashes.put(e.getTrains()[0].getID(), e.getTrains());
                    }
                }
                checkHead = trackSystem.getNextCoordinate(checkHead, cardinalDirection);
                drivable = trackSystem.getDrivable(checkHead);

            }

        }
        return crashes;
    }


    private void move(Train train, Direction direction) throws TrainCrashException {
        Drivable currentDrivable = trackSystem.getDrivable(train.getHeadCoordinate(direction));
        train.setCurrentCardinalDirection(currentDrivable.getNextDirection(train.getCurrentCardinalDirection()));
        train.setHeadCoordinate(direction, trackSystem.getNextCoordinate(train.getHeadCoordinate(direction), train.getCurrentCardinalDirection()));

        if (!trackSystem.containsDrivable(train.getHeadCoordinate(direction))) {
            throw new TrainCrashException(train);
        } else {
            Drivable drivable = trackSystem.getDrivable(train.getHeadCoordinate(direction));
            drivable.addTrain(train);
            CardinalDirection cardinalDirection = drivable.getPreviousDirection(train.getCurrentCardinalDirection());
            Coordinate removalHead = train.getHeadCoordinate(direction);
            for (long i = 0; i < train.getLength() + 1; i++) {
                cardinalDirection = drivable.getNextDirection(cardinalDirection);
                removalHead = trackSystem.getNextCoordinate(removalHead, cardinalDirection);
                drivable = trackSystem.getDrivable(removalHead);
                //the last part of the train;
                if (i == train.getLength() - 1) {
                    train.setHeadCoordinate(direction.getOpposite(), removalHead);
                }
            }
            drivable.removeTrain(train);
        }

    }

    private void derail(Train train) {
        Drivable drivable = trackSystem.getDrivable(train.getHeadCoordinate(Direction.FORWARD));
        CardinalDirection cardinalDirection = drivable.getPreviousDirection(train.getCurrentCardinalDirection());
        Coordinate removalHead = train.getHeadCoordinate(Direction.FORWARD);
        for (long i = 0; i < train.getLength(); i++) {
            drivable.removeTrain(train);
            cardinalDirection = drivable.getNextDirection(cardinalDirection);
            removalHead = trackSystem.getNextCoordinate(removalHead, cardinalDirection);
            drivable = trackSystem.getDrivable(removalHead);
        }
        train.setPlaced(false);
        placedTrains.remove(train);

    }

    private String listPlacedTrainLocation(Train train) {
        return "Train " + train.getID() + " at " + train.getHeadCoordinate(Direction.FORWARD);
    }

    private String listAllPlacedTrainsLocations() {

        if (placedTrains.isEmpty()) {
            return NO_TRAIN_EXIST_MESSAGE;
        }
        String out = new String();
        for (Train train : placedTrains) {
            out += listPlacedTrainLocation(train) + "\n";
        }
        return out;
    }


    private static final String POSITIVE_OUTCOME_MESSAGE = "OK";
    private static final String NO_TRACKS_EXIST_MESSAGE = "No track exists";


}
