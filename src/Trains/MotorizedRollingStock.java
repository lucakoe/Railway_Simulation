package Trains;

public abstract class MotorizedRollingStock extends RollingStock {

    //TODO maybe find better name
    private String MOTORIZED_ROLLING_STOCK_CLASS;
    private String NAME;

    public MotorizedRollingStock(RollingStockTypes rollingStockType, String sprite, String trainSetClass, String name,
                                 int length, boolean couplingFront, boolean couplingBack) {
        super(rollingStockType, sprite, length, couplingFront, couplingBack);
        this.NAME = name;
        this.MOTORIZED_ROLLING_STOCK_CLASS = trainSetClass;
    }

    public String getName() {
        return NAME;
    }

    public String getMotorizedRollingStockClass() {
        return MOTORIZED_ROLLING_STOCK_CLASS;
    }


}
