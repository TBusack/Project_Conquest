import java.awt.*;
import java.awt.image.BufferedImage;

/*
    Inventory containing VisualItem objects
 */
public class VisualInventory implements Drawable{
    VisualItem[] items;
    BufferedImage image;

    //constructor
    public VisualInventory(int size){
        items = new VisualItem[size];
    }

    /**
     * Override the standard function and instead print a list of the IDs of all items in the function. If the item is null, print NU
     * @return concatenated string with the IDs of all items separated by a space
     */

    /**
     * add an item to the inventory, taking up the first available non-null slot in the VI.
     * NOTE: if the item is null, the function still runs as normal, but does not actually alter the VI
     * @param item VisualItem being added
     */
    public void addItem(VisualItem item) throws FullInventoryException{
        for(int i = 0; i < items.length; i++){
            if(items[i] == null){
                items[i] = item;
                break;
            }
        }
    }

    /**
     * combine two VisualInventories, and return a new VI containing the items in both
     * @param other VisualInventory
     * @return VisualInventory containing this.items and other.items
     */
    public VisualInventory combineTwoVisualInventories(VisualInventory other){
        if(other == null){
            return this;
        }
        this.sortItemsById();
        other.sortItemsById();
        int sizeOfBoth = this.numberOfItems() + other.numberOfItems();
        VisualInventory combined = new VisualInventory(sizeOfBoth);
        int ti = 0;//iterator for this.items
        int oi = 0;//iterator for other.items
        int ci = 0;//iterator for combined.items

        for(int i = 0; ti < this.items.length || oi < other.items.length; i++){
            if(this.items[ti].id == i){
                if(this.items[ti].id == other.items[oi].id) {//if ID is equal
                    combined.items[ci] = this.items[ti];
                    combined.items[ci].quantity = this.items[ti].quantity + other.items[oi].quantity;
                    if (combined.items[ci].quantity > 100) {
                        combined.items[ci + 1] = combined.items[ci];
                        combined.items[ci + 1].quantity -= 100;
                        combined.items[ci].quantity = 100;
                        ci++;
                    }
                    oi++;
                }
                else{//if only this.items hits the id
                    combined.items[ci] = this.items[ti];
                }
                ti++;
            }
            else if(other.items[oi].id == i){
                combined.items[ci] = other.items[oi];
                oi++;
            }
        }
        return combined;
    }

    /**
     * see if, after sorting and combining, this all the items in this and VI "other" can both fit within this.items
     * @param other other VI getting combined into
     * @return true if the merger was successful. If it failed, return false and DO NOT change this
     */
    public boolean combineIntoThisVisualInventory(VisualInventory other){
        if(other == null){
            return true;
        }
        VisualInventory another = this.combineTwoVisualInventories(other);
        if(another.numberOfItems() < this.items.length){
            for(int i = 0; another.items[i] != null; i++){
                this.items[i] = another.items[i];
            }
            return true;
        }
        return false;
    }

    /**
     * return the number of slots in the VisualInventory actually occupied by items
     * @return number of items
     */
    public int numberOfItems(){
        int tally = 0;
        for(int i = 0; i < this.items.length; i++){
            if(items[i] != null){
                tally++;
            }
        }
        return tally;
    }

    /**
     * sort this.items by ID value, coalescing up to 100 items of the same type into each stack.
     * if there are more than 100, start a second stack
     */
    public void sortItemsById(){
        VisualItem[] sorted = new VisualItem[items.length];
        int topId = 0;

        for(int i = 0; i < items.length; i++){
            if(items[i] != null){
                if(items[i].id > topId){
                    topId = items[i].id;
                }
            }
        }

        //outer loop goes up in ID
        int firstSlot = 0;
        boolean foundOne = false;
        for(int i = 0; i <= topId; i++){
            for(int j = 0; j < items.length; j++){
                //if an item in items has the given ID, add it to the first available slot and flag firstSlot for incrementation
                if(items[j].id == i){
                    foundOne = true;
                    //if firstSlot is empty, put the item there. If not, add the quantities.
                    if(sorted[firstSlot] == null){
                        sorted[firstSlot] = items[j];
                    }
                    else{
                        sorted[firstSlot].quantity += items[j].quantity;
                        //if the quantity exceeds 100, push it over into the next slot.
                        if(sorted[firstSlot].quantity > 100){
                            sorted[firstSlot+1] = sorted[firstSlot];
                            sorted[firstSlot].quantity = 100;
                            firstSlot++;
                            sorted[firstSlot].quantity -= 100;
                        }
                    }
                }
            }
            if(foundOne){
                firstSlot++;
                foundOne = false;
            }
        }
        //once all ID's have been run through, you've sorted the list!
        items = sorted;
    }

    @Override
    public String toString(){
        String list = "";

        for(int i = 0; i < items.length; i++) {
            if(items[i] == null){
                list = list + "[NULL] ";
                continue;
            }
            list = list + items[i].toString() + " ";
        }

        return list;
    }

    @Override
    public void draw(Graphics2D g2d, Camera cam) {
        for(int i = 0; i < items.length && items[i] != null; i++){
            items[i].draw(g2d, cam);
        }
    }
}
