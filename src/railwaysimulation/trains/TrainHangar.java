package railwaysimulation.trains;

import exceptions.CouplingConfigurationInvalidException;
import railwaysimulation.exceptions.*;

import java.util.TreeMap;

public class TrainHangar {
    TreeMap<Integer, Train> trainTreeMap;
    TreeMap<Integer, Coach> coachTreeMap;
    TreeMap<String, MotorizedRollingStock> motorizedRollingStockTreeMap;


    public TrainHangar() {
        trainTreeMap = new TreeMap<Integer, Train>();
        coachTreeMap = new TreeMap<Integer, Coach>();
        motorizedRollingStockTreeMap = new TreeMap<String, MotorizedRollingStock>();
    }

    public String listTrainInfo() {
        String out = "";
        //TODO maybe remove code repetition
        Integer[] sortedKeys = trainTreeMap.navigableKeySet().toArray(new Integer[trainTreeMap.size()]);
        for (int i = 0; i < sortedKeys.length; i++) {
            Train currentTrain = trainTreeMap.get(sortedKeys[i]);
            if (currentTrain != null) {
                out += currentTrain.toString() + "\n";
            }
        }
        return out;
    }

    //TODO test both and delete one of them
    public String listRollingStockInfo(RollingStockTypes rollingStockType) {
        String out = "";
        if (rollingStockType == RollingStockTypes.ENGINE || rollingStockType == RollingStockTypes.TRAIN_SET) {
            String[] sortedKeys = motorizedRollingStockTreeMap.navigableKeySet().toArray(new String[motorizedRollingStockTreeMap.size()]);
            for (int i = 0; i < sortedKeys.length; i++) {
                out += motorizedRollingStockTreeMap.get(sortedKeys[i]).toString();
            }
        } else if (rollingStockType == RollingStockTypes.COACH) {
            Integer[] sortedKeys = coachTreeMap.navigableKeySet().toArray(new Integer[coachTreeMap.size()]);
            for (int i = 0; i < sortedKeys.length; i++) {
                Coach currentCoach = coachTreeMap.get(sortedKeys[i]);
                if (currentCoach != null) {
                    out += currentCoach.toString() + "\n";
                }
            }
        }
        return out;
    }

    public Train getTrain(int trainID) throws IDDoesNotExistExcepiton, TrainIsInvalidException {
        if (trainTreeMap.containsKey(trainID)) {
            if (trainTreeMap.get(trainID).isValid()) {
                return trainTreeMap.get(trainID);
            } else {
                throw new TrainIsInvalidException();
            }

        } else {
            throw new IDDoesNotExistExcepiton(TYPE_OF_ID_TRAIN);
        }
    }

    public String listRollingStock(RollingStockTypes rollingStockType) throws NotAvailableException {
        String out = new String();

        if (rollingStockType == RollingStockTypes.COACH) {
            if (coachTreeMap.isEmpty()) {
                throw new NotAvailableException(rollingStockType.toString());
            }
            for (Coach coach : coachTreeMap.values()) {
                if (coach.getRollingStockType() == rollingStockType) {
                    out += coach.toString() + "\n";
                }

            }
            //removes last \n
            return out.substring(0, out.length() - 1);
        } else if (rollingStockType == RollingStockTypes.TRAIN_SET || rollingStockType == RollingStockTypes.ENGINE) {
            if (motorizedRollingStockTreeMap.isEmpty()) {
                throw new NotAvailableException(rollingStockType.toString());
            }
            for (MotorizedRollingStock rollingStock : motorizedRollingStockTreeMap.values()) {
                if (rollingStock.getRollingStockType() == rollingStockType) {
                    out += rollingStock.toString() + "\n";
                }
            }
            //removes last \n
            return out.substring(0, out.length() - 1);
        }
        return out;


    }


    public String createEngine(EngineTypes engineType, String engineClass, String name, long length, boolean couplingAvailabilityFront, boolean couplingAvailabilityBack) throws IDAlreadyExistsException, CouplingConfigurationInvalidException {
        //TODO check for right name convention (class and name)
        if (!(couplingAvailabilityBack|| couplingAvailabilityFront)){
            throw new CouplingConfigurationInvalidException();
        }
        Engine engine = new Engine(engineType, engineClass, name, length, couplingAvailabilityFront, couplingAvailabilityBack);
        if (motorizedRollingStockTreeMap.containsKey(engine.getID())) {
            throw new IDAlreadyExistsException(RollingStockTypes.ENGINE.toString());
        } else {
            motorizedRollingStockTreeMap.put(engine.getID(), engine);
        }
        return engine.getID();
    }


    public String createCoach(CoachTypes coachType, long length, boolean couplingAvailabilityFront, boolean couplingAvailabilityBack) throws IDAlreadyExistsException, CouplingConfigurationInvalidException {

        if (!(couplingAvailabilityBack|| couplingAvailabilityFront)){
            throw new CouplingConfigurationInvalidException();
        }
        int coachID = getNextAvailableCoachID();
        Coach coach = new Coach(coachID, coachType, length, couplingAvailabilityFront, couplingAvailabilityBack);

        if (!(couplingAvailabilityBack|| couplingAvailabilityFront)){
            throw new CouplingConfigurationInvalidException();
        }

        if (coachTreeMap.containsKey(coach.getID())) {
            throw new IDAlreadyExistsException(RollingStockTypes.COACH.toString());
        } else {
            coachTreeMap.put(coach.getID(), coach);
        }
        return Integer.toString(coach.getID());
    }

    private int getNextAvailableCoachID() {
        int countingVariable = Coach.getCoachIdFirstNumber();
        while (coachTreeMap.containsKey(countingVariable)) {
            countingVariable++;
        }
        return countingVariable;
    }


    public String createTrainSet(String trainSetClass, String name, long length, boolean couplingAvailabilityFront, boolean couplingAvailabilityBack) throws IDAlreadyExistsException, CouplingConfigurationInvalidException {
        //TODO check for right name convention (class and name)
        if (!(couplingAvailabilityBack|| couplingAvailabilityFront)){
            throw new CouplingConfigurationInvalidException();
        }
        TrainSet trainSet = new TrainSet(trainSetClass, name, length, couplingAvailabilityFront, couplingAvailabilityBack);
        if (motorizedRollingStockTreeMap.containsKey(trainSet.getID())) {
            throw new IDAlreadyExistsException(RollingStockTypes.TRAIN_SET.toString());
        } else {
            motorizedRollingStockTreeMap.put(trainSet.getID(), trainSet);
        }
        return trainSet.getID();
    }

    //TODO complete
    public void deleteRollingStock(String rollingStockID) throws RollingStockCouldntBeenAddedOrRemovedException, IDDoesNotExistExcepiton {
        if (!getRollingStock(rollingStockID).isAssignedToTrain()) {
            switch (getRollingStock(rollingStockID).getRollingStockType()) {
                case COACH:
                    coachTreeMap.remove(((Coach) getRollingStock(rollingStockID)).getID());
                    break;
                case TRAIN_SET:
                case ENGINE:
                    motorizedRollingStockTreeMap.remove(rollingStockID);
            }

        } else {
            throw new RollingStockCouldntBeenAddedOrRemovedException();
        }
    }


    public String addTrain(int trainID, String rollingStockID) throws RollingStockCouldntBeenAddedOrRemovedException, IDDoesNotExistExcepiton {
        if (trainTreeMap.containsKey(trainID)) {

        } else if (trainID == getNextAvailableTrainID()) {
            trainTreeMap.put(getNextAvailableTrainID(), new Train(getNextAvailableTrainID()));

        } else {
            throw new IDDoesNotExistExcepiton(TYPE_OF_ID_TRAIN);
        }
        trainTreeMap.get(trainID).add(getRollingStock(rollingStockID));

        switch (getRollingStock(rollingStockID).getRollingStockType()) {
            case ENGINE:
                return ((Engine) getRollingStock(rollingStockID)).getEngineType().toString()+" "+getRollingStock(rollingStockID).getRollingStockType().toString() + " " +   rollingStockID + " added to train " + trainID;

            case COACH:
                return ((Coach) getRollingStock(rollingStockID)).getCoachType().toString()+" "+getRollingStock(rollingStockID).getRollingStockType().toString() + " " +  rollingStockID + " added to train " + trainID;
            case TRAIN_SET:
                return getRollingStock(rollingStockID).getRollingStockType().toString()+ " " +  rollingStockID + " added to train " + trainID;
            default:
                return new String();

        }

    }


    private int getNextAvailableTrainID() {
        int countingVariable = Coach.getCoachIdFirstNumber();
        while (trainTreeMap.containsKey(countingVariable)) {
            countingVariable++;
        }
        return countingVariable;
    }

    private RollingStock getRollingStock(String rollingStockID) throws IDDoesNotExistExcepiton {
        //TODO maybe remove magic string
        if (rollingStockID.matches("(W)[\\d+]")) {
            int coachID;
            try {
                coachID = Integer.parseInt(rollingStockID.substring(1));
            } catch (NumberFormatException e) {
                throw new IDDoesNotExistExcepiton("rollingStockID");
            }
            if (coachTreeMap.containsKey(coachID)) {
                return coachTreeMap.get(coachID);
            }
        }
        //TODO maybe add matches for train and
        else if (motorizedRollingStockTreeMap.containsKey(rollingStockID)) {
            return motorizedRollingStockTreeMap.get(rollingStockID);
        } else {
            throw new IDDoesNotExistExcepiton("rollingStockID");
        }
        return null;
    }

    public void deleteTrainFromHangar(int trainID) throws IDDoesNotExistExcepiton {
        //TODO derail the train in Railway Simulation before removing from hangar

        if (trainTreeMap.containsKey(trainID)) {
            trainTreeMap.get(trainID).deAssignAllRollingStock();
            trainTreeMap.remove(trainID);
        } else {
            throw new IDDoesNotExistExcepiton(TYPE_OF_ID_TRAIN);
        }
    }

    public String listTrains() throws NotAvailableException {
        String out = new String();
        if (trainTreeMap.isEmpty()) {
            throw new NotAvailableException(NOT_AVAILABLE_TRAIN);
        }
        for (Train train : trainTreeMap.values()) {
            out += train.toString() + "\n";
        }
        //removes last \n
        return out.substring(0, out.length() - 1);
    }

    public String showTrain(int trainID) throws IDDoesNotExistExcepiton {
        if (trainTreeMap.containsKey(trainID)) {
            return trainTreeMap.get(trainID).getSprite();
        } else {
            throw new IDDoesNotExistExcepiton(TYPE_OF_ID_TRAIN);
        }

    }


    private static final String NOT_AVAILABLE_TRAIN = "Train";
    private static final String TYPE_OF_ID_TRAIN = "Train";

}
