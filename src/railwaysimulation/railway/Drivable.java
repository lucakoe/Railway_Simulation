package railwaysimulation.railway;

import railwaysimulation.trains.Train;

public interface Drivable {
    CardinalDirection getNextDirection(CardinalDirection currentCardinalDirection);
    CardinalDirection getPreviousDirection(CardinalDirection currentCardinalDirection);
    void addTrain(Train train);
    void removeTrain(Train train);
    Train[] getTrains();
    boolean isOccupied();
    boolean isOccupiedPut();
}
