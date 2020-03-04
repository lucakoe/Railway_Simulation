package Trains;

public class TrainSet extends MotorizedRollingStock {

    private static final String SPRITE = "         ++         \n" +
            "         ||         \n" +
            "_________||_________\n" +
            "|  ___ ___ ___ ___ |\n" +
            "|  |_| |_| |_| |_| |\n" +
            "|__________________|\n" +
            "|__________________|\n" +
            "   (O)        (O)   \n";
    public static String getSprite(){
        return SPRITE;
    }

    //TODO find better method name (and make interface for engine and train set with common methods)
    public TrainSet(String trainSetClass, String name, int length, boolean couplingFront, boolean couplingBack) {
        super(RollingStockTypes.TRAIN_SET, TrainSet.getSprite(), trainSetClass, name, length, couplingFront, couplingBack);

    }

}
