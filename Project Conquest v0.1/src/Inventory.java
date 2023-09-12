import java.awt.*;

//the inventory of a player, chest, or other entity
public class Inventory implements Drawable {
    Item[] items;

    //used to make a new empty inventory with finite size
    public Inventory(int size){
        items = new Item[size];
        for(int i = 0; i < size; i++){
            items[i] = null;
        }
    }

    /**
     * add a new item to the inventory
     * @param item  - item to be added
     * @throws FullInventoryException - if the inventory is full
     */
    public void addItem(Item item) throws FullInventoryException{
        int i = 0;
        for(i = 0; i < items.length; i++){
            if(items[i] == null){
                items[i] = item;
                item = null;
                break;
            }
            else{
                continue;
            }
        }
        if(i == items.length){
            throw new FullInventoryException();
        }
    }

    /**
     * add an item to a specific slot in the inventory. useful for dealing with menus
     * @param item  - item to be added
     * @param inventorySlot  - location in array of item
     * @throws FullInventoryException - if the inventory is full
     */
    public void addItem(Item item, int inventorySlot) throws FullInventoryException{
        if(items[inventorySlot] == null){
            items[inventorySlot] = item;
        }
        else{
            throw new FullInventoryException();
        }
    }

    @Override
    public String toString(){
        String list = "";

        for(int i = 0; i < items.length; i++) {
            if(items[i] == null){
                list = list +"[NULL] ";
                continue;
            }
            list = list + items[i].toString() + " ";
        }

        return list;
    }

    //draw inventory
    @Override
    public void draw(Graphics2D g2d, Camera cam) {

    }
}
