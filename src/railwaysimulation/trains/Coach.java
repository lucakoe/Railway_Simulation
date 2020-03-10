package railwaysimulation.trains;

import railwaysimulation.railway.Direction;

public class Coach extends RollingStock {


    private static final int COACH_ID_FIRST_NUMBER=1;
    private final CoachTypes COACH_TYPE;
    private final int ID;


    public Coach(int id,CoachTypes coachType, long length, boolean couplingFront, boolean couplingBack) {
        super(RollingStockTypes.COACH, coachType.getSprite(), length, couplingFront, couplingBack);
        ID=id;
        this.COACH_TYPE = coachType;


    }

    public static int getCoachIdFirstNumber() {
        return COACH_ID_FIRST_NUMBER;
    }

    @Override
    public String toString() {
        String trainID;
        String couplingsString=this.couplingExists(Direction.FORWARD)+
                " " +this.couplingExists(Direction.BACKWARD);
        if(getTrain()==null){
            trainID=RollingStock.getNoTrainAssignedString();
        }
        else {
            trainID= Integer.toString(this.getTrain().getID());
        }

        return this.ID+" "+trainID+" "+ this.COACH_TYPE.getInfoString()+" "+this.getLength()+" "+couplingsString;
    }


    public int getID() {
        return ID;
    }


    public CoachTypes getCoachType() {
        return COACH_TYPE;
    }
}
