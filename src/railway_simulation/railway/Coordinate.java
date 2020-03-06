package railway_simulation.railway;

public class Coordinate implements Comparable<Coordinate> {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
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
        if ((this.x - coordinate.getX()) == 0) {
            out = this.y - coordinate.getY();
        } else {
            out = this.x - coordinate.getX();
        }
        if (out == 0) {
            return 0;
        } else if (out > 0) {
            return 1;
        } else {
            return -1;
        }

    }

    public long distanceToOtherCoordinate(Coordinate otherCoordinate) {
        //TODO check if this works with max min int
        //calculate distance with Pythagoras' Theorem
        return (long) Math.sqrt(Math.pow(((long) this.getX() - (long) otherCoordinate.getX()), 2) + Math.pow(((long) this.getY() - (long) otherCoordinate.getY()), 2));
    }

    public Direction getDirectionToCoordinate(Coordinate coordinate) {
        if (this.getX() == coordinate.getX() && this.compareTo(coordinate) < 0) {
            return Direction.NORTH;
        } else if (this.getX() == coordinate.getX() && this.compareTo(coordinate) > 0) {
            return Direction.SOUTH;
        } else if (this.getY() == coordinate.getY() && this.compareTo(coordinate) < 0) {
            return Direction.EAST;
        } else if (this.getY() == coordinate.getY() && this.compareTo(coordinate) > 0) {
            return Direction.WEST;
        }
        return null;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}