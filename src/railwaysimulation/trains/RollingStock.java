package railwaysimulation.trains;

import railwaysimulation.railway.Direction;


import java.util.TreeMap;

//TODO Maybe make interface instead
public abstract class RollingStock {
    //number of lines the sprite is high
    private static final int SPRITE_SIZE_VERTICAL = 8;
    //number of columns (number of chars)
    private static final int SPRITE_SIZE_HORIZONTAL = 20;
    //shows up in the list/info text if the rolling stock isn't added to a train
    private static final String NO_TRAIN_ASSIGNED_STRING = "none";
    private final long LENGTH;


    private TreeMap<Direction, RollingStock> couplings;
    RollingStockTypes rollingStockType;
    Train train;
    private final String SPRITE;

    RollingStock(RollingStockTypes rollingStockType, String sprite, long length, boolean couplingFront, boolean couplingBack) {
        this.rollingStockType = rollingStockType;
        this.SPRITE = sprite;
        this.LENGTH = length;
        this.couplings = new TreeMap<Direction, RollingStock>();
        if (couplingFront) {
            couplings.put(Direction.FORWARD, null);
        }
        if (couplingBack) {
            couplings.put(Direction.BACKWARD, null);
        }

    }

    public boolean isAssignedToTrain(){
        if (this.train ==null){
            return false;
        }
        return true;
    }
    public long getLength() {
        return LENGTH;
    }

    public Train getTrain() {
        return train;
    }
    @Override
    public String toString() {
        return new String();
    }

    public static String getNoTrainAssignedString() {
        return NO_TRAIN_ASSIGNED_STRING;
    }

    public static int getSpriteSizeVertical() {
        return SPRITE_SIZE_VERTICAL;
    }

    public static int getSpriteSizeHorizontal() {
        return SPRITE_SIZE_HORIZONTAL;
    }

    public RollingStock getCoupledRollingStock(Direction direction) {
        if (couplings.containsKey(direction)) {
            return couplings.get(direction);
        }
        return null;
    }
    public boolean couplingExists(Direction direction){
        if (couplings.containsKey(direction)){
            return true;
        }
        return false;
    }

    public boolean freeCouplingInDirection(Direction direction) {
        //if there is a coupling in the direction, and if its free it will return true, else false
        if (couplings.containsKey(direction) && couplings.get(direction) == null) {
            return true;
        }
        return false;
    }

    public RollingStockTypes getRollingStockType() {
        return rollingStockType;
    }

    public String getSprite() {
        return SPRITE;
    }


    public void assignToTrain(Train train){
        this.train=train;
    }
    public void deAssignTrain(){
        this.train=null;
    }
}


