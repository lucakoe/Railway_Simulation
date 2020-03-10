package railwaysimulation.exceptions;

public class RollingStockCouldntBeenAddedOrRemovedException extends Throwable {

    public RollingStockCouldntBeenAddedOrRemovedException() {
        super(MESSAGE);
    }

    private static final String MESSAGE="Rolling stock couldn't be added/removed";
}
