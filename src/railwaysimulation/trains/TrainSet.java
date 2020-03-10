package railwaysimulation.trains;

public class TrainSet extends MotorizedRollingStock {

    private static final String SPRITE = "         ++         \n" +
            "         ||         \n" +
            "_________||_________\n" +
            "|  ___ ___ ___ ___ |\n" +
            "|  |_| |_| |_| |_| |\n" +
            "|__________________|\n" +
            "|__________________|\n" +
            "   (O)        (O)   \n";



    //TODO find better method name (and make interface for engine and train set with common methods)
    public TrainSet(String trainSetClass, String name, long length, boolean couplingFront, boolean couplingBack) {
        super(RollingStockTypes.TRAIN_SET, SPRITE, trainSetClass, name, length, couplingFront, couplingBack);

    }

}
