package railwaysimulation.railway;

public enum  Direction {FORWARD,BACKWARD;

public Direction getOpposite(){
    if (this==FORWARD){
        return BACKWARD;
    }
    else if (this==BACKWARD){
        return FORWARD;
    }
    return null;
}
}
