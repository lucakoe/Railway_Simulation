package railway_simulation.trains;

public class Engine extends MotorizedRollingStock {
    private final EngineTypes ENGINE_TYPE;

    public Engine(EngineTypes engineType, String engineClass, String name, int length, boolean couplingFront,
                  boolean couplingBack) {
        super(RollingStockTypes.ENGINE, engineType.toString(), engineClass, name, length, couplingFront, couplingBack);
        this.ENGINE_TYPE = engineType;
    }
}
