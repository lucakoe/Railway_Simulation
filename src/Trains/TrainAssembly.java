package Trains;

import java.util.ArrayList;
import java.util.TreeMap;

public class TrainAssembly {
    TreeMap<Integer, Train> trainTreeMap;
    TreeMap<Integer, Coach> coachTreeMap;
    TreeMap<String, MotorizedRollingStock> motorizedRollingStockTreeMap;


    public TrainAssembly() {

    }

    public String listTrainInfo() {
        String out = "";
        //TODO maybe remove code repetition
        Integer[] sortedKeys = trainTreeMap.navigableKeySet().toArray(new Integer[trainTreeMap.size()]);
        for (int i = 0; i < sortedKeys.length; i++) {
            Train currentTrain = trainTreeMap.get(sortedKeys[i]);
            if (currentTrain != null) {
                out += currentTrain.getInfo() + "\n";
            }
        }
        return out;
    }

    public String listRollingStockInfo(RollingStockTypes rollingStockType) {
        String out = "";
        if (rollingStockType == RollingStockTypes.ENGINE || rollingStockType == RollingStockTypes.TRAIN_SET) {
            String[] sortedKeys = motorizedRollingStockTreeMap.navigableKeySet().toArray(new String[motorizedRollingStockTreeMap.size()]);
            for (int i = 0; i < sortedKeys.length; i++) {
                out += motorizedRollingStockTreeMap.get(sortedKeys[i]).getInfo();
            }
        } else if (rollingStockType == RollingStockTypes.COACH) {
            Integer[] sortedKeys = coachTreeMap.navigableKeySet().toArray(new Integer[coachTreeMap.size()]);
            for (int i = 0; i < sortedKeys.length; i++) {
                Coach currentCoach = coachTreeMap.get(sortedKeys[i]);
                if (currentCoach != null) {
                    out += currentCoach.getInfo() + "\n";
                }
            }
        }

        return out;


    }


}
