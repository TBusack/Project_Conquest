import java.awt.*;
import java.awt.image.BufferedImage;

/*
    A human(oid) character; any mobile creature MapObject that isn't designated as such is an animal
 */
public class Human extends MapObject{
    String allegiance;//political affiliation
    boolean hostile;//if true, then attack

    int speedX; //horizontal speed in pixels per second. positive is right, negative is left
    int speedY; //vertical speed in pixels per second. positive is down, negative is up
    int width;  //size of sprite and hitbox
    int height;
    BufferedImage image;//sprite

    //constructor
    public Human(Location location, int health, String allegiance, boolean hostile){
        super(location, health);
        this.allegiance = allegiance;
        this.hostile = hostile;
    }

    /**
     * change this.allegiance to a something new
     * @param allegiance new allegiance
     */
    public void changeAllegiance(String allegiance){
        this.allegiance = allegiance;
    }

    /**
     * Force this.hostile to become true
     */
    public void makeHostile(){
        hostile = true;
    }

    /**
     * force this.hostile to become false
     */
    public void pacify(){
        hostile = false;
    }

    /**
     * check if this human should attack another based on allegiance
     * @param other other Human's allegiance
     * @return true if this should be hostile
     */
    public boolean allegianceInteraction(String other){
        AllegianceHandler a = new AllegianceHandler();
        return a.allegianceInteraction(this.allegiance, other);
    }

    //draw the Human
    @Override
    public void draw(Graphics2D g2d, Camera cam) {
        //draw the stationary version if speedX == 0
        //draw walking animation otherwise
        //should be animations for mining or attacking. Character sprites will probably be 24x12, but drawn as 2x1 blocks
    }
}
