import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//this class is a single block used to make a Landscape
public class Block {
    byte id;
    byte toughness; //what tier of pickaxe/drill (or force of explosion) is required to damage it
    String name;

    //constructor
    public Block(String name, byte id, byte toughness){
        this.name = name;
        this.id = id;
        this.toughness = toughness;
    }

    /**
     * return true if another Block is the same as this one by comparing their ID's
     * @param other Block to be tested against
     * @return true if same Block
     */
    public boolean equals(Block other){
        return this.id == other.id;
    }

}
