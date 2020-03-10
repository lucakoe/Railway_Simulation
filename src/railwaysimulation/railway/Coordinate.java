package railwaysimulation.railway;

public class Coordinate implements Comparable<Coordinate> {
    private final int X_VALUE;
    private final int Y_VALUE;

    public Coordinate(int x, int y) {
        this.X_VALUE = x;
        this.Y_VALUE = y;
    }

    @Override
    public int compareTo(Coordinate coordinate) {
        /*
        if the x value is equivalent the point with the higher y value is bigger,
        else the one with the higher x value is bigger, and if x and y values are the same, the points are the same.
        since in my use case I only want to check lines parallel to the x-/y-axis,
        it will compare the x value if horizontal and for the y value if vertical
         */
        int out = 0;
        if ((this.X_VALUE == coordinate.getX())) {
            if (this.Y_VALUE < coordinate.getY()) {
                return -1;
            } else if (this.Y_VALUE > coordinate.getY()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (this.X_VALUE < coordinate.getX()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public long distanceToOtherCoordinate(Coordinate otherCoordinate) {
        //TODO check if this works with max min int
        //calculate distance with Pythagoras' Theorem
        return (long) Math.sqrt(Math.pow(((long) this.getX() - (long) otherCoordinate.getX()), 2) + Math.pow(((long) this.getY() - (long) otherCoordinate.getY()), 2));
    }

    public CardinalDirection getDirectionToCoordinate(Coordinate coordinate) {
        if (this.getX() == coordinate.getX() && this.compareTo(coordinate) < 0) {
            return CardinalDirection.NORTH;
        } else if (this.getX() == coordinate.getX() && this.compareTo(coordinate) > 0) {
            return CardinalDirection.SOUTH;
        } else if (this.getY() == coordinate.getY() && this.compareTo(coordinate) < 0) {
            return CardinalDirection.EAST;
        } else if (this.getY() == coordinate.getY() && this.compareTo(coordinate) > 0) {
            return CardinalDirection.WEST;
        }
        return null;
    }


    public int getX() {
        return X_VALUE;
    }

    public int getY() {
        return Y_VALUE;
    }

    @Override
    public String toString() {
        return "(" + X_VALUE + "," + Y_VALUE + ")";
    }
}