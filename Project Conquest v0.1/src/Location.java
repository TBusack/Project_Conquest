/*
    Represents a point in space, onscreen, in an array, or anywhere a 2 dimensional point is required
 */
public class Location {
    int x;
    int y;

    //constructor
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Location other) {
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
