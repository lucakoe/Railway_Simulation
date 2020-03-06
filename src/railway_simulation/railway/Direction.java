package railway_simulation.railway;

public enum Direction {
    NORTH(Direction.SOUTH), SOUTH(Direction.NORTH), EAST(Direction.WEST), WEST(Direction.EAST);

    Direction(Direction oppositeDirection) {
        this.OPPOSITE_DIRECTION = oppositeDirection;
    }

    private final Direction OPPOSITE_DIRECTION;

    public static Direction getDirectionFromVector(int x, int y) {
        if (x == 0 && y > 0) {
            return NORTH;
        } else if (x == 0 && y < 0) {
            return SOUTH;
        } else if (x > 0 && y == 0) {
            return EAST;
        } else if (x < 0 && y == 0) {
            return WEST;
        } else {
            //TODO maybe throw exception
            return null;
        }
    }

    public Direction getOpposite() {
        return this.OPPOSITE_DIRECTION;
    }


}
