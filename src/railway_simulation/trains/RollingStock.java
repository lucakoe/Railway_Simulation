package railway_simulation.trains;

import railway_simulation.railway.Direction;
import railway_simulation.railway.Point;

import java.util.TreeMap;

//TODO Maybe make interface instead
public abstract class RollingStock {
    //number of lines the sprite is high
    private static final int SPRITE_SIZE_VERTICAL = 8;
    //number of columns (number of chars)
    private static final int SPRITE_SIZE_HORIZONTAL = 20;
    //shows up in the list/info text if the rolling stock isn't added to a train
    private static final String NO_TRAIN_ASSIGNED_STRING = "none";
    private final int LENGTH;
    private Point[] positions;
    private Direction drivingDirection;
    TreeMap<CouplingDirection, RollingStock> couplings;
    RollingStockTypes rollingStockType;
    Train train;
    private final String SPRITE;

    RollingStock(RollingStockTypes rollingStockType, String sprite, int length, boolean couplingFront, boolean couplingBack) {
        this.rollingStockType = rollingStockType;
        this.SPRITE = sprite;
        this.LENGTH = length;
        this.positions = new Point[this.LENGTH];
        this.couplings = new TreeMap<CouplingDirection, RollingStock>();
        if (couplingFront) {
            couplings.put(CouplingDirection.FRONT, null);
        }
        if (couplingBack) {
            couplings.put(CouplingDirection.BEHIND, null);
        }

    }

    public int getLength() {
        return LENGTH;
    }

    public Train getTrain() {
        return train;
    }

    public String getInfo() {
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

    public RollingStock getCoupledRollingStock(CouplingDirection couplingDirection) {
        if (couplings.containsKey(couplingDirection)) {
            return couplings.get(couplingDirection);
        }
        return null;
    }

    public boolean freeCouplingInDirection(CouplingDirection couplingDirection) {
        //if there is a coupling in the direction, and if its free it will return true, else false
        if (couplings.containsKey(couplingDirection) && couplings.get(couplingDirection) == null) {
            return true;
        }
        return false;
    }


    public void move() {

    }

    public RollingStockTypes getRollingStockType() {
        return rollingStockType;
    }

    @Override
    public String toString() {
        return SPRITE;
    }
}