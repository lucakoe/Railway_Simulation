package railway_simulation.trains;


import java.util.ArrayList;

public class Train {

    private ArrayList<RollingStock> rollingStocks;
    private boolean placed;
    private final int ID;


    //TODO Complete


    public Train(int id) {
        this.rollingStocks = new ArrayList<RollingStock>();
        this.ID = id;
    }

    public boolean move() {
        if (placed) {
            //TODO change depending on the direction the train is driving (depending on the direction of the last/first train) first train moves west --> last train has to move first, first train moves east --> first train moves first.
            for (int i = 0; i < this.getLength(); i++) {
                rollingStocks.get(i).move();
            }
        }
        return false;
    }

    private boolean getValid() {
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

    public boolean add(RollingStock rollingStock) {
        //TODO complete
        //checks if the rolling stock already is added to a train and if the train is placed
        if (placed == false && rollingStock != null && rollingStock.getTrain() == null) {

            if (rollingStocks.isEmpty()) {
                rollingStocks.add(rollingStock);
                return true;
                /*
                if the coupling of the last rolling stock added and the coupling of the rolling stock to be added are
                free in the right direction, both rolling stocks are a train set and the Class is the same, the rolling
                stock can be added
                */
            } else if ((getLastRollingStock().freeCouplingInDirection(CouplingDirection.BEHIND) &&
                    rollingStock.freeCouplingInDirection(CouplingDirection.FRONT)) &&
                    getLastRollingStock().getRollingStockType() == RollingStockTypes.TRAIN_SET &&
                    rollingStock.getRollingStockType() == RollingStockTypes.TRAIN_SET &&
                    ((TrainSet) getLastRollingStock()).getMotorizedRollingStockClass().equals(((TrainSet) rollingStock).getMotorizedRollingStockClass())) {

                rollingStocks.add(rollingStock);
                return true;
                /*
                if the coupling of the last rolling stock added and the coupling of the rolling stock to be added are
                free in the right direction, both rolling stocks are a engine or an coach , the rolling stock
                can be added
                */
            } else if ((getLastRollingStock().freeCouplingInDirection(CouplingDirection.BEHIND) &&
                    rollingStock.freeCouplingInDirection(CouplingDirection.FRONT)) &&
                    (getLastRollingStock().getRollingStockType() == RollingStockTypes.COACH ||
                            getLastRollingStock().getRollingStockType() == RollingStockTypes.ENGINE)
                    && rollingStock.getRollingStockType() != RollingStockTypes.TRAIN_SET) {
                rollingStocks.add(rollingStock);
                return true;
            }

        }
        return false;

    }

    @Override
    //TODO maybe use to String for the list-representaion and a new method to/getSprite() for returning the sprite
    public String toString() {
        String out = "";
        if (rollingStocks != null) {
            //all the Sprites of the different rolling stocks split in their lines (height) ([rollingStock][line])
            String[][] splitSprites = new String[rollingStocks.size()][];
            //the combined (length) single rolling Stock lines to one big out line ([line])
            String[] outSplitSprites;

            int heightHighestSprite = 0;
            for (int i = rollingStocks.size() - 1; i >= 0; i--) {
                //splits the Sprites of the rolling stock into single lines
                splitSprites[i] = rollingStocks.get(i).toString().split("\n");
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
                out += outSplitSprites[i] + "\n";
            }
        }
        return out;

    }

    public int getLength() {
        int out = 0;
        for (int i = 0; i < rollingStocks.size(); i++) {
            out += rollingStocks.get(i).getLength();
        }
        return out;
    }

    public int getID() {
        return ID;
    }

    public String getInfo() {
        String iDString = Integer.toString(this.getID());
        String rollingStockString = "";
        for (int i = 0; i < rollingStocks.size(); i++) {
            if (rollingStocks.get(i).getRollingStockType() == RollingStockTypes.COACH) {
                rollingStockString += " " +
                        CoachTypes.getIDPrefix() +
                        (Integer.toString(((Coach) rollingStocks.get(i)).getID()));
            } else {
                rollingStockString += " "+(((MotorizedRollingStock) rollingStocks.get(i)).getID());
            }
        }
        return iDString + rollingStockString;
    }


}
