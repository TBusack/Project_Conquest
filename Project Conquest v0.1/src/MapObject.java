import java.awt.*;
/*
    These are things on the map that get drawn and are part of a wide range of interactive entities
    Every single one has health (and can theoretically be destroyed)
    It also has a position
 */
public abstract class MapObject implements Drawable{
    Location location;
    int health;

    //constructor
    public MapObject(int x, int y, int health){
        location = new Location(x, y);
        this.health = health;
    }
    public MapObject(Location l, int health){
        location = new Location(l.x, l.y);
        this.health = health;
    }

    //kicks the draw() function down to the subclasses
    public abstract void draw(Graphics2D g2d, Camera cam);
}
