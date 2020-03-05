package railway_simulation.trains;

public abstract class MotorizedRollingStock extends RollingStock {

    //TODO maybe find better name
    private String MOTORIZED_ROLLING_STOCK_CLASS;
    private String NAME;
    private String ID;


    public MotorizedRollingStock(RollingStockTypes rollingStockType, String sprite, String trainSetClass, String name,
                                 int length, boolean couplingFront, boolean couplingBack) {
        super(rollingStockType, sprite, length, couplingFront, couplingBack);
        //TODO maybe use exceptions to look for right class and name
        this.NAME = name;
        this.MOTORIZED_ROLLING_STOCK_CLASS = trainSetClass;
        this.ID = this.NAME + "-" + this.MOTORIZED_ROLLING_STOCK_CLASS;
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
