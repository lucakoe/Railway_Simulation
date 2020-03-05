package railway_simulation.railway;

public class Coordinate implements Comparable<Coordinate>  {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public int compareTo(Coordinate coordinate) {
        /*
        if the x value is equivalent the point with the higher y value is bigger,
        else the one with the higher x value is bigger, and if x and y values are the same, the points are the same.
        since in my use case I only want to check lines parallel to the x-/y-axis,
        it will compare the x value if horizontal and for the y value if vertical
         */

        if ((this.x - coordinate.getX()) == 0) {
            return (this.y - coordinate.getY());
        } else {
            return this.x - coordinate.getX();
        }

        //TODO Remove alternative solution
        /*
        if (this.getX() == point.getX() && this.getY() == point.getY()) {
            return 0;
        } else if ((this.getX() == point.getX() && this.getY() < point.getY()) ||
                (this.getY() == point.getY() && this.getX() < point.getX())) {
            return -1;
        } else if ((this.getX() == point.getX() && this.getY() > point.getY()) ||
                (this.getY() == point.getY() && this.getX() > point.getX())) {
            return 1;
        }
        //put into another else if so it checks the first two cases first (these cases shouldn't be happening since I
        //don't want to check for points that would create a line that are not parallel to the x-/y-axis) but if I would,
        //it would select the point with the greater x-value as bigger
        else if (this.getX() < point.getX()) {
            return -1;
        } else if (this.getX() > point.getX()) {
            return 1;
        */
    }
    //TODO maybe make long because -MAXINT to +MAXINT has no lenght in int -->overflow
    public int distanceToOtherCoordinate(Coordinate otherCoordinate) {
        //calculate distance with Pythagoras' Theorem
        return (int) Math.sqrt(Math.pow((this.getX() - otherCoordinate.getX()), 2) + Math.pow((this.getY() - otherCoordinate.getY()), 2));
    }

    public Direction getDirectionToCoordinate(Coordinate coordinate){
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