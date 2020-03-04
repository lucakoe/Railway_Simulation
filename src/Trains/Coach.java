package Trains;

public class Coach extends RollingStock {
    private final CoachTypes COACH_TYPE;

    public Coach(CoachTypes coachType, int length, boolean couplingFront, boolean couplingBack) {
        super(RollingStockTypes.COACH, coachType.toString(), length, couplingFront, couplingBack);
        this.COACH_TYPE = coachType;

    }

}
