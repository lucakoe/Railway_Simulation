package railwaysimulation.trains;

public enum RollingStockTypes {
    COACH(RollingStockTypes.COACH_STRING), ENGINE(RollingStockTypes.ENGINE_STRING), TRAIN_SET(RollingStockTypes.TRAIN_SET_STRING);




    RollingStockTypes(String stringRepresentation) {
        this.STRING_REPRESENTATION = stringRepresentation;
    }

    @Override
    public String toString() {
        return this.STRING_REPRESENTATION;
    }
    private final String STRING_REPRESENTATION;
    private static final String COACH_STRING = "coach";
    private static final String ENGINE_STRING = "engine";
    private static final String TRAIN_SET_STRING = "train-set";
}
