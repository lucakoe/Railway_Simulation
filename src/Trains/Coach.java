package Trains;

public class Coach extends RollingStock {
    private final CoachTypes COACH_TYPE;
    private final int ID;


    public Coach(int id,CoachTypes coachType, int length, boolean couplingFront, boolean couplingBack) {
        super(RollingStockTypes.COACH, coachType.toString(), length, couplingFront, couplingBack);
        ID=id;
        this.COACH_TYPE = coachType;


    }

    @Override
    public String getInfo() {
        String trainID;
        String couplingsString=this.couplings.containsKey(CouplingDirection.FRONT)+
                " " +this.couplings.containsKey(CouplingDirection.BEHIND);
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


}
