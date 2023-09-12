import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//class for plants in the world. These cannot move, but can be destroyed
public class Plant extends MapObject implements Drawable {

    BufferedImage image;
    Hitbox hitbox;
    Plant[] dependents = new Plant[5];
    int hardness;
    int dropTable = 0;
    int dropRolls = 0;

    /**
     * Constructor used when a single image file is the entire portion of the plant
     * @param location location of top left corner
     * @param health health of the plant
     * @param hardness hardness value
     * @param imageID file name of the image
     */
    public Plant(Location location, int health, int hardness, String imageID){
        super(location, health);
        this.hardness = hardness;
        try {
            //set image to the file
            image = ImageIO.read(new File(imageID));
            hitbox = new Hitbox(new Location(location.x, location.y), image.getWidth(), image.getHeight());
        } catch (IOException e) {
            //set to missing texture
            image = null;
            System.err.println("ERROR: " + imageID + " is missing");
            hitbox = new Hitbox(new Location(location.x, location.y), 0, 0);
        }
    }

    /**
     * alternate constructor that pulls from a texture file
     * @param location  of top right corner of the plant
     * @param health    health of the plant
     * @param hardness  armor of the plant
     * @param imageID   name of the texture of the plant
     * @param pointX    X point of the left edge of the image in the file
     * @param pointY    Y point of the bottom edge of the image in the file
     * @param width     width of image in texture file
     * @param height    height of image in texture file
     */
    public Plant(Location location, int health, int hardness, String imageID, int pointX, int pointY, int width, int height){
        super(location, health);
        this.hardness = hardness;
        try {
            //set image to the file
            image = ImageIO.read(new File(imageID));
            this.image.getSubimage(pointX, pointY, width, height);
            hitbox = new Hitbox(new Location(location.x, location.y), width, height);
        } catch (IOException e) {
            //set to missing texture
            image = null;
            System.err.println("ERROR: " + imageID + " is missing");
            hitbox = new Hitbox(new Location(location.x, location.y), 0, 0);
        }
    }

    /**
     * Constructor used when a single image file is the entire portion of the plant AND specifies drops
     * @param location location of top left corner
     * @param health health of the plant
     * @param hardness hardness value
     * @param imageID file name of the image
     * @param dropTable integer value specifying which table to pull drops from
     * @param dropRolls integer value specifying how many drops to pull from the table
     */
    public Plant(Location location, int health, int hardness, String imageID, int dropTable, int dropRolls){
        super(location, health);
        this.hardness = hardness;
        try {
            //set image to the file
            image = ImageIO.read(new File(imageID));
            hitbox = new Hitbox(new Location(location.x, location.y), image.getWidth(), image.getHeight());
        } catch (IOException e) {
            //set to missing texture
            image = null;
            System.err.println("ERROR: " + imageID + " is missing");
            hitbox = new Hitbox(new Location(location.x, location.y), 0, 0);
        }
        this.dropTable = dropTable;
        this.dropRolls = dropRolls;
    }


    /**
     * alternate constructor that pulls from a texture file AND specifies drops
     * @param location  of top right corner of the plant
     * @param health    health of the plant
     * @param hardness  armor of the plant
     * @param imageID   name of the texture of the plant
     * @param pointX    X point of the left edge of the image in the file
     * @param pointY    Y point of the bottom edge of the image in the file
     * @param width     width of image in texture file
     * @param height    height of image in texture file
     * @param dropTable integer value specifying which table to pull drops from
     * @param dropRolls integer value specifying how many drops to pull from the table
     */
    public Plant(Location location, int health, int hardness, String imageID, int pointX, int pointY, int width, int height, int dropTable, int dropRolls){
        super(location, health);
        this.hardness = hardness;
        try {
            //set image to the file
            image = ImageIO.read(new File(imageID));
            this.image.getSubimage(pointX, pointY, width, height);
            hitbox = new Hitbox(new Location(this.location.x, this.location.y), width, height);
        } catch (IOException e) {
            //set to missing texture
            image = null;
            System.err.println("ERROR: " + imageID + " is missing");
            hitbox = new Hitbox(new Location(this.location.x, this.location.y), width, height);
        }
        this.dropTable = dropTable;
        this.dropRolls = dropRolls;
    }

    /**
     * add a new dependent to this plant. If it has 5 already, then don't
     * @param plant new dependent
     */
    public void addDependent(Plant plant){
        for(int i = 0; i < dependents.length; i++){
            if(dependents[i] == null){
                dependents[i] = plant;
                break;
            }
        }
    }

    /**
     * create a VisualInventory of arbitrary size containing drops from the destroyed plant
     * @return VisualInventory containing all drops from this object
     */
    public int[] convertToDrops(){
        PlantHandler p = new PlantHandler();
        ItemHandler ih = new ItemHandler();
        int[] myDrops = new int[ih.getMaxItemID() + 1];
        /*
        Start with the first object to be killed
        If it has dependents, grab all the dependent's drops recursively
            If it does NOT have drops, skip that step, create an int array of the drops, then sort it.
            Return the drops up the chain
        Coalesce them and sort them, then return the int array
         */

        //run through list of dependents
        int dependentItems = 0;
        for(int i = 0; i < dependents.length; i++) {
            if(dependents[i] == null){
                //check the next one
                continue;
            }
            else{
                myDrops = combineTheseTables(myDrops, dependents[i].convertToDrops());
            }
        }
        myDrops = combineTheseTables(myDrops, p.turnPlantIntoDrops(this));
        return myDrops;
    }

    /**
     * combine two arrays by adding the values in each element
     * @param me an array of length x
     * @param other another array of the same length
     * @return array with values added together
     */
    private int[] combineTheseTables(int[] me, int[] other){
        if(other == null || me == null){
            return me;
        }
        for(int i = 0; i < me.length; i++){
            me[i] += other[i];
        }
        return me;
    }

    /**
     * move the hitbox of the plant (does not move the location of the image)
     * @param x position of the left side of the hitbox
     * @param y position of top of the hitbox
     */
    public void setHitbox(int x, int y){
        hitbox.location.x = x;
        hitbox.location.y = y;
    }

    /**
     * change the size of the hitbox
     * @param width new width of the hitbox
     * @param height new height of the hitbox
     */
    public void resizeHitbox(int width, int height){
        hitbox.width = width;
        hitbox.height = height;
    }

    /**
     * Kill the plant and its dependents. Items are not dropped
     * Actual clearing of the object will take place in the Landscape class
     */
    public void kill(){
        this.health = 0;
        killDependents();
    }

    /**
     * kill this plant and all dependents, but calculate the drops and return a VI containing them.
     * @return VI with all drops from this and all descendants
     */
    public VisualInventory reap(){
        this.health = 0;
        ItemHandler i = new ItemHandler();
        killDependents();
        return i.turnCheckIntoVInventory(this.convertToDrops(), location, calculateTolerance());
    }

    /**
     * find the tolerance value of a given plant. This is the distance between the out edges of the hitboxes of its first and last dependents
     * @return int representing span of first generation's hitboxes
     */
    public int calculateTolerance(){
        Hitbox left = null;
        Hitbox right = null;
        for(int i = 0; i < dependents.length; i++){
            if(dependents[i] != null){
                if(left == null){
                    left = dependents[i].hitbox;
                }
                right = dependents[i].hitbox;
            }
        }
        if(left == null){//if NO dependents
            left = this.hitbox;
            right = left;
        }
        else if(right == left){//if some dependents, but only 1 (or have same hitbox)
            right = this.hitbox;
        }
        //return the difference between the far right edge of the last dependent's hitbox
        //and the far left edge of the first dependent's hitbox
        return (right.location.x + right.width - left.location.x);
    }

    /**
     * Cycle through and kill the list of dependents
     */
    public void killDependents(){
        for(int i = 0; i < dependents.length; i++){
            if(dependents[i] != null){
                dependents[i].kill();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera cam) {
        //draw the plant
    }
}
