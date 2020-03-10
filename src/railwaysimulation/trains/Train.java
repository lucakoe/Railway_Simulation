package railwaysimulation.trains;

import railwaysimulation.exceptions.RollingStockCouldntBeenAddedOrRemovedException;
import railwaysimulation.railway.CardinalDirection;
import railwaysimulation.railway.Coordinate;
import railwaysimulation.railway.Direction;

import java.util.ArrayList;

public class Train {


    private ArrayList<RollingStock> rollingStocks;

    public boolean isPlaced() {
        return placed;
    }

    private boolean placed;
    private final int ID;
    private long length;

    public long getLength() {
        return length;
    }

    public Train(int id) {
        this.rollingStocks = new ArrayList<RollingStock>();
        this.ID = id;
    }

    protected boolean isValid() {
        //TODO check if criteria are right
        //other Criteria are checked while adding!
        if (!rollingStocks.isEmpty() &&
                (rollingStocks.get(0).getRollingStockType() == RollingStockTypes.ENGINE ||
                        rollingStocks.get(0).getRollingStockType() == RollingStockTypes.TRAIN_SET ||
                        getLastRollingStock().getRollingStockType() == RollingStockTypes.ENGINE ||
                        getLastRollingStock().getRollingStockType() == RollingStockTypes.TRAIN_SET)) {
            return true;
        }
        return false;


    }

    private RollingStock getLastRollingStock() {
        if (!rollingStocks.isEmpty()) {
            return rollingStocks.get(rollingStocks.size() - 1);
        }
        return null;
    }

    public void add(RollingStock rollingStock) throws RollingStockCouldntBeenAddedOrRemovedException {
        //TODO complete
        //checks if the rolling stock already is added to a train and if the train is placed
        if (placed == false && rollingStock != null && rollingStock.getTrain() == null) {

            if (rollingStocks.isEmpty()) {
                rollingStocks.add(rollingStock);
                rollingStock.assignToTrain(this);
                /*
                if the coupling of the last rolling stock added and the coupling of the rolling stock to be added are
                free in the right direction, both rolling stocks are a train set and the Class is the same, the rolling
                stock can be added
                */
            } else if ((getLastRollingStock().freeCouplingInDirection(Direction.BACKWARD) &&
                    rollingStock.freeCouplingInDirection(Direction.FORWARD)) &&
                    getLastRollingStock().getRollingStockType() == RollingStockTypes.TRAIN_SET &&
                    rollingStock.getRollingStockType() == RollingStockTypes.TRAIN_SET &&
                    ((TrainSet) getLastRollingStock()).getMotorizedRollingStockClass().equals(((TrainSet) rollingStock).getMotorizedRollingStockClass())) {

                rollingStocks.add(rollingStock);
                rollingStock.assignToTrain(this);
                /*
                if the coupling of the last rolling stock added and the coupling of the rolling stock to be added are
                free in the right direction, both rolling stocks are a engine or an coach , the rolling stock
                can be added
                */
            } else if ((getLastRollingStock().freeCouplingInDirection(Direction.BACKWARD) &&
                    rollingStock.freeCouplingInDirection(Direction.FORWARD)) &&
                    (getLastRollingStock().getRollingStockType() == RollingStockTypes.COACH ||
                            getLastRollingStock().getRollingStockType() == RollingStockTypes.ENGINE)
                    && rollingStock.getRollingStockType() != RollingStockTypes.TRAIN_SET) {
                rollingStocks.add(rollingStock);
                rollingStock.assignToTrain(this);

            }
            else {
                throw new RollingStockCouldntBeenAddedOrRemovedException();
            }

        }
        else throw new RollingStockCouldntBeenAddedOrRemovedException();

    }

    public CardinalDirection getCurrentCardinalDirection() {
        return currentCardinalDirection;
    }

    public void setCurrentCardinalDirection(CardinalDirection currentCardinalDirection) {
        this.currentCardinalDirection = currentCardinalDirection;
    }

    private CardinalDirection currentCardinalDirection;

    public Coordinate getHeadCoordinate(Direction direction) {
        return headCoordinate;
    }

    public void setHeadCoordinate(Direction direction, Coordinate headCoordinate) {

        this.headCoordinate = headCoordinate;


    }

    private Coordinate headCoordinate;

    public void setPlaced(boolean newPlaced) {

    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        String iDString = Integer.toString(this.getID());
        String rollingStockString = "";
        for (int i = 0; i < rollingStocks.size(); i++) {
            if (rollingStocks.get(i).getRollingStockType() == RollingStockTypes.COACH) {
                rollingStockString += " " +
                        CoachTypes.getIDPrefix() +
                        (((Coach) rollingStocks.get(i)).getID());
            } else {
                rollingStockString += " " + (((MotorizedRollingStock) rollingStocks.get(i)).getID());
            }
        }
        return iDString + rollingStockString;
    }


    public String getSprite() {
        String out = "";
        if (rollingStocks != null) {
            //all the Sprites of the different rolling stocks split in their lines (height) ([rollingStock][line])
            String[][] splitSprites = new String[rollingStocks.size()][];
            //the combined (length) single rolling Stock lines to one big out line ([line])
            String[] outSplitSprites;

            int heightHighestSprite = 0;
            for (int i = rollingStocks.size() - 1; i >= 0; i--) {
                //splits the Sprites of the rolling stock into single lines
                splitSprites[i] = rollingStocks.get(i).getSprite().split("\n");
                //if one Sprite has more lines than the currently highestSprite it will be the new highestSprite
                if (splitSprites[i].length > heightHighestSprite) {
                    heightHighestSprite = splitSprites[i].length;
                }
            }
            //initialise the outSplitSprites, since you need to know how high the highest Sprite is
            outSplitSprites = new String[heightHighestSprite];
            for (int i = 0; i < outSplitSprites.length; i++) {
                outSplitSprites[i] = "";
            }
            for (int i = 0; i < rollingStocks.size(); i++) {
                //if the current i-th Sprite is smaller than the highest Sprite, it will get saved if oldSplitSprites
                //and than a new splitSprites with the height of the Highest Sprite will be initialised
                if (splitSprites[i].length < heightHighestSprite) {
                    String[] oldSplitSprites = splitSprites[i];
                    splitSprites[i] = new String[heightHighestSprite];
                    //the lengthDifference between the current and the new Sprite is determinted and the overlapping
                    //lines are filled with spaces (in the length of a sprite)
                    int lengthDifference = splitSprites[i].length - oldSplitSprites.length;
                    for (int j = 0; j < lengthDifference; j++) {
                        splitSprites[i][j] = "";
                        for (int l = 0; l < RollingStock.getSpriteSizeHorizontal(); l++) {
                            splitSprites[i][j] += " ";
                        }
                    }
                    //the other lines are just added to the new Sprite
                    for (int j = lengthDifference; j < splitSprites[i].length; j++) {
                        splitSprites[i][j] = oldSplitSprites[j - lengthDifference];
                    }

                }
                //all the rolling stock sprites are added behind each other
                for (int j = 0; j < heightHighestSprite; j++) {
                    outSplitSprites[j] += splitSprites[i][j] + " ";
                    //TODO maybe remove all spaces after the last symbol
                }

            }

            //the single split lines are added together to a single String
            for (int i = 0; i < heightHighestSprite; i++) {
                //removes the last char (the space symbol i added beforehand between the rolling stocks and one behind)
                out += outSplitSprites[i].substring(0, outSplitSprites[i].length() - 1) + "\n";
            }
            //removes last \n
            return out.substring(0, out.length() - 1);
        }
        return out;
    }


    public void deAssignAllRollingStock() {
        for (RollingStock rollingStock:rollingStocks){
            rollingStock.deAssignTrain();
        }
    }
}
