import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
    An Item that is to be rendered on the map visually
 */
public class VisualItem extends Item implements Drawable{

    Location location;
    BufferedImage image;

    //constructors
    //using texture file and integerial coordinates
    public VisualItem(String name, int id, int quantity, int value, String imageID, int tileLength, int itemsPerLine, int x, int y){
        super(name, id, quantity, value);
        try {
            //set image to the portion of the file defined by
            image = ImageIO.read(new File(imageID)).getSubimage((id & 0x7) << 3,
                    (id >>> 3) << 3,
                    8,
                    8);

        } catch (IOException e) {
            //set to missing texture
            image = null;
        }
        moveTo(x, y);
    }

    //using texture file and Location object
    public VisualItem(String name, int id, int quantity, int value, String imageID, Location location){
        super(name, id, quantity, value);
        try {
            //set image to the portion of the file defined by
            image = ImageIO.read(new File(imageID)).getSubimage((id & 0x7) << 3,
                    (id >>> 3) << 3,
                    8,
                    8);

        } catch (IOException e) {
            //set to missing texture
            image = null;
            System.err.println("ERROR: " + imageID + " is missing");
        }
        this.location.x = location.x;
        this.location.y = location.y;
    }

    //setting equal to a different VisualItem
    public VisualItem(VisualItem other){
        super(other.name, other.id, other.quantity, other.value);
        this.location = other.location;
        this.image = other.image;
    }

    /**
     * set an existing VisualItem to a different one by value (NOT pointers)
     * @param other other VisualItem
     */
    public void setTo(VisualItem other){
        this.name = other.name;
        this.id = other.id;
        this.value = other.value;
        this.quantity = other.quantity;
        this.location = other.location;
        this.image = other.image;
    }

    /**
     * move this to a different Location
     * @param x x position
     * @param y y position
     */
    public void moveTo(int x, int y){
        location = new Location(x, y);
    }

    @Override
    public void draw(Graphics2D g2d, Camera cam) {
        //draw the image at the given spot rounded to the nearest pixel
        g2d.drawImage(this.image, (location.x * cam.scaleFactor + cam.pos.x), (location.y * cam.scaleFactor + cam.pos.y), cam.scaleFactor, cam.scaleFactor, null);
    }
}
