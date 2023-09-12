import java.awt.*;

/*
    Shared by all in-game objects that can be rendered visually onscreen. They must implement this method
 */
public interface Drawable {

    /**
     * interface method used to draw the object on screen
     * @param g2d This is the Graphics2D object we've set up in DrawPanel to handle paint events
     * @param cam This is the current camera being applied to the frame
     */
    public void draw(Graphics2D g2d, Camera cam);
}
