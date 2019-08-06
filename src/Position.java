/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.Objects;

// line 96 "model.ump"
// line 189 "model.ump"
public class Position {

    private final int x;
    private final int y;

    public Position(int aX, int aY) {
        x = aX;
        y = aY;
    }

    /**
     * Gets the x position of the position
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of the position
     */
    public int getY() {
        return y;
    }

    /**
     * Generates a position from a string
     * @param s The string to generate a position from
     * @return  The position generated
     */
    public static Position positionFromString(String s){
        String[] split = s.toLowerCase().split(",");

        int x = Integer.valueOf(split[0]) - 1;
        int y = split[1].charAt(0) - 'a';

        return new Position(x, y);
    }

    /**
     * Adds values to this position
     * @param x     The x position to add
     * @param y     The y position to add
     * @return      The new position
     */
    public Position add(int x, int y){
        return new Position(this.x + x, this.y + y);
    }

    /**
     * Calculates the distance between two points
     * @param p The second point to calculate the distance between
     * @return  The distance between the points
     */
    public int distTo(Position p) {
        return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return (x * 1987) + y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}