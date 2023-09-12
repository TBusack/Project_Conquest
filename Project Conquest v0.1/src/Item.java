import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//represents a single item
public class Item {
    String name;
    int id;
    int quantity;
    int value;

    public Item(String name, int id, int quantity, int value){
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.value = value;
    }

    @Override
    public String toString(){
        return "[ID: " + this.id + ", Qt: " + this.quantity + "]";
    }

    /**
     * set this object to a different Item by value (NOT pointers)
     * @param other
     */
    public void setTo(Item other){
        this.name = other.name;
        this.id = other.id;
        this.value = other.value;
        this.quantity = other.quantity;
    }
}
