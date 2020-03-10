package railwaysimulation.exceptions;

import railwaysimulation.trains.Train;

import java.util.ArrayList;
import java.util.TreeMap;

public class TrainCrashException extends Exception {

    public TrainCrashException(Train... trains) {
        TreeMap<Integer,Train> trainTreeMap=new TreeMap<Integer,Train>();
        this.trains=new ArrayList<Train>();
        for(Train train:trains){
            trainTreeMap.put(train.getID(),train);
        }

        for (int trainID:trainTreeMap.descendingKeySet()){
            this.trains.add(trainTreeMap.get(trainID));
        }


    }

    public Train[] getTrains() {
        return trains.toArray(new Train[trains.size()]);
    }

    private ArrayList<Train> trains;


}
