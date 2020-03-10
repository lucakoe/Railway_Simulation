package railwaysimulation.trains;

import railwaysimulation.railway.Direction;

public abstract class MotorizedRollingStock extends RollingStock {

    //TODO maybe find better name
    private String MOTORIZED_ROLLING_STOCK_CLASS;
    private String NAME;
    private String ID;


    public MotorizedRollingStock(RollingStockTypes rollingStockType, String sprite, String motorizedRollingStockClass, String name,
                                 long length, boolean couplingFront, boolean couplingBack) {
        super(rollingStockType, sprite, length, couplingFront, couplingBack);
        //TODO maybe use exceptions to look for right class and name
        this.NAME = name;
        this.MOTORIZED_ROLLING_STOCK_CLASS = motorizedRollingStockClass;
        this.ID = this.MOTORIZED_ROLLING_STOCK_CLASS + "-" + this.NAME;
    }

    @Override
    public String toString() {
        String couplingsString=this.couplingExists(Direction.FORWARD)+
                " " +this.couplingExists(Direction.BACKWARD);
        String trainID;
        if (this.getTrain()!=null){
            trainID=Integer.toString(this.getTrain().getID());
        }
        else {
            trainID=RollingStock.getNoTrainAssignedString();
        }
        return trainID+" "+this.getMotorizedRollingStockClass()+" "+this.getName()+" "+this.getLength()+" "+couplingsString;
    }

    public String getName() {
        return NAME;
    }

    public String getID() {
        return this.ID;
    }

    public String getMotorizedRollingStockClass() {
        return MOTORIZED_ROLLING_STOCK_CLASS;
    }


}
