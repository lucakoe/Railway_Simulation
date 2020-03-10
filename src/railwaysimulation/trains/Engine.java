package railwaysimulation.trains;

import railwaysimulation.railway.Direction;

public class Engine extends MotorizedRollingStock {
    private final EngineTypes ENGINE_TYPE;

    public Engine(EngineTypes engineType, String engineClass, String name, long length, boolean couplingFront,
                  boolean couplingBack) {
        super(RollingStockTypes.ENGINE, engineType.getSprite(), engineClass, name, length, couplingFront, couplingBack);
        this.ENGINE_TYPE = engineType;
    }

    @Override
    public String toString() {
        String trainID;
        String couplingsString=this.couplingExists(Direction.FORWARD)+
                " " +this.couplingExists(Direction.BACKWARD);
        if (train!=null){
            trainID=Integer.toString(train.getID());
        }
        else {
            trainID=RollingStock.getNoTrainAssignedString();
        }
        return trainID+" "+this.ENGINE_TYPE.getInfoString()+" "+this.getMotorizedRollingStockClass()+" "+this.getName()+" "+this.getLength()+" "+couplingsString;
    }

    public EngineTypes getEngineType() {
        return ENGINE_TYPE;
    }
}
