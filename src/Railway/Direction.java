package Railway;

//TODO make better (with opposite and constructor)
public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    Direction() {

    }

    public static Direction getDirectionFromVector(int x, int y) {
        if (x == 0 && y > 0) {
            return NORTH;
        } else if (x == 0 && y < 0) {
            return SOUTH;
        } else if (x > 0 && y == 0){
            return EAST;
        }else if (x<0&&y==0){
            return WEST;
        }
        else {
            //TODO maybe throw exception
            return null;
        }
    }

    public Direction getOpposite() {
        // == okay because it's an enum
        if (this == NORTH) {
            return SOUTH;
        } else if (this == SOUTH) {
            return NORTH;
        } else if (this == WEST) {
            return EAST;
        } else if (this == EAST) {
            return WEST;
        } else {
            return null;
        }
    }


}
