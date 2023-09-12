import java.awt.*;

/*
    Any stationary object rendered on the map that can be destroyed, such as buildings, artillery, and plants
 */
public class Structure extends MapObject{

    public Structure(Location location, int health){
        super(location, health);
    }

    @Override
    public void draw(Graphics2D g2d, Camera cam) {

    }
}