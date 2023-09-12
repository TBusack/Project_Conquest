/*
    This class handles the positional and size offsets that describe where things are drawn in relation to their true position in game
 */
public class Camera {

    //18 is the standard until otherwise noted
    int scaleFactor;//number of pixels each block should be. This will eventually be changed in settings at the start of the game
    Location pos = new Location(0,0);

    //constructor
    public Camera(int xPos, int yPos, int scaleFactor){
        pos.x = xPos;
        pos.y = yPos;
        this.scaleFactor = scaleFactor;
    }
    //alternate constructor if you input a location
    public Camera(Location pos, int scaleFactor){
        this.pos = pos;
        this.scaleFactor = scaleFactor;
    }

    /**
     * Set this equal to a different Camera (WITHOUT using pointers)
     * @param other Camera object
     */
    public void moveToMe(Camera other){
        pos.x = other.pos.x;
        pos.y = other.pos.y;
        this.scaleFactor = other.scaleFactor;
    }

    /**
     * change the scale factor to a new value
     * @param sf new scale factor
     */
    public void rescale(int sf){
        this.scaleFactor = sf;
    }

    /**
     * change the position of the Camera
     * @param x new X position
     * @param y new Y position
     */
    public void reposition(int x, int y){
        pos.x = x;
        pos.y = y;
    }

    /**
     * change position of Camera by setting it to a new Location
     * @param other
     */
    public void reposition(Location other){
        pos.x = other.x;
        pos.y = other.y;
    }
}
