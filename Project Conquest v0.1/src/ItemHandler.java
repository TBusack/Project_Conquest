import java.util.Optional;
import java.util.Random;

/*
    Functional class used to handle interactions with Items/VisualItems and Inventories/VisualInventories
 */
public class ItemHandler {

    String TEXTUREID = "TEXTURE FILES/item-textures.png";
    int tileLength = 6;
    int itemsPerLine = 6;

    public ItemHandler(){
        //do nothing
    }

    /**
     * return the item object associated with the given ID
     * @param id ID of item
     * @return Item with given ID
     */
    public Item getItemById(int id){
        switch(id){
            default://missing texture
                return new Item("MISSINGITM.", 0, 0, 0);
            case 1://dirt blob
                return new Item("dirt", id, 1, 1);
            case 2://grass fibers
                return new Item("fibers", id, 1, 2);
            case 3://sand
                return new Item("silicate sand", id, 1, 5);
            case 4://slate chip
                return new Item("slate chip", id, 1, 3);
            case 5://conglomerate chunk
                return new Item("conglomerate", id, 1, 3);
            case 6://water cup
                return new Item("cup of water", id, 1, 10);
            case 7://wood
                return new Item("uncut lumber", id, 1, 7);
            case 8://leaves
                return new Item("deciduous leaves", id, 1, 4);
            case 9://apple
                return new Item("apple", id, 1, 12);
            case 10://carrot <3
                return new Item("carrot", id, 1, 15);

            case 12://coal ore
                return new Item("coal", id, 1, 12);
            case 13://iron ore
                return new Item("iron ore", id, 1, 22);
            case 14://quartz ore
                return new Item("quartz shard", id, 1, 35);
        }
    }

    public Item getQuantitiedItemByID(int id, int quantity){
        Item i = getItemById(id);
        i.quantity = quantity;
        return i;
    }

    /**
     * return the ID of the highest numbered item. This will FAIL if the ID is over 101
     * @return ID of highest numbered item
     */
    public int getMaxItemID(){
        for(int i = 100; i > 0; i--){
            if(getItemById(i).id != 0) {
                return i + 1;
            }
        }
        return 101;
    }

    /**
     * transform an Item into a VisualItem at the given location
     * @param item Item object being transformed
     * @param l location of new VisualItem
     * @return new VisualItem
     */
    public VisualItem turnItemIntoVItem(Item item, Location l){
        return new VisualItem(item.name, item.id, item.quantity, item.value, TEXTUREID, tileLength, itemsPerLine,l.x, l.y);
    }

    /**
     * create a new VisualItem at the origin based on the given ID
     * @param id ID of item
     * @return new VisualItem at (0,0)
     */
    public VisualItem getVItemById(int id){
        return turnItemIntoVItem(getItemById(id), new Location(0,0));
    }

    /**
     * create a new VisualItem at the given Location based on the given ID
     * @param id ID of item
     * @param l Location of new VisualItem
     * @return new VisualItem at l
     */
    public VisualItem getVItemById(int id, Location l){
        return turnItemIntoVItem(getItemById(id), l);
    }

    /**
     * combine the contents of two inventories into the first one
     * @param init the first inventory
     * @param other the new inventory
     * @return init with all the new items added
     * @throws FullInventoryException if init becomes full before the process is done
     */
    public Inventory combineInventories(Inventory init, Inventory other) throws FullInventoryException {
        //run through list of items in other
        if(other.items[0] == null){
            return init;
        }
        for(int i = 0; i < other.items.length && other.items[i] != null; i++){
            //for each one, see if it's in init already.
            for(int j = 0; j < init.items.length; j++){
                //if it is, add the quantities
                if(init.items[j].id == other.items[i].id){
                    init.items[j].quantity += other.items[i].quantity;
                    other.items[i] = null;
                    break;
                }
                //if not, but there is open space, put it there
                else if(init.items[j] == null){
                    init.items[j].setTo(other.items[i]);
                    other.items[i] = null;
                }
            }
            //if other.items[i] couldn't find a spot
            if(other.items[i] != null){
                throw new FullInventoryException();
            }
        }
        return init;
    }

    /**
     * combine the contents of two VisualInventories into the first one
     * @param init VInventory taking in the new items
     * @param other new VItems to add
     * @return init with VItems added
     * @throws FullInventoryException if init fills up before the function finishes
     */
    public VisualInventory combineVInventories(VisualInventory init, VisualInventory other) throws FullInventoryException {
        //run through list of items in other
        if(other.items[0] == null){
            return init;
        }
        for(int i = 0; i < other.items.length && other.items[i] != null; i++){
            //for each one, see if it's in init already.
            for(int j = 0; j < init.items.length; j++){
                //if space is available, put it in
                if(init.items[j] == null){
                    init.items[j] = new VisualItem(other.items[i]);
                    other.items[i] = null;
                    break;
                }
                //if there wasn't open space, combine the quantities if IDs are the same
                //it is AND the locations are within 2 blocks, add the quantities
                else if(init.items[j].id == other.items[i].id &&
                        (Math.abs(init.items[j].location.x - other.items[i].location.x) <= 2) &&
                        (Math.abs(init.items[j].location.y - other.items[i].location.y) <= 2)){
                    init.items[j].quantity += other.items[i].quantity;
                    other.items[i] = null;
                    break;
                }
            }
            //if other.items[i] couldn't find a spot
            if(other.items[i] != null){
                throw new FullInventoryException();
            }
        }
        return init;
    }

    /**
     * return the minimum number of slots necessary to fit a VisualInventory created from the given int array
     * @param vi the int array representing a VisualInventory
     * @return int number of slots
     */
    public int minimumSizeOfVI(int[] vi){
        int size = 0;
        for(int i = 1; i < vi.length; i++){
            if(vi[i] <= 0){
                continue;
            }
            size += Math.ceilDiv(vi[i], 100);
        }
        return size;
    }

    /**
     * transform a special int array into a corresponding Inventory
     * @param check the "check" being cashed: an int array of length [max legal item ID]
     * @return Inventory with corresponding items
     */
    public Inventory turnCheckIntoInventory(int[] check){

        ItemHandler ih = new ItemHandler();
        int end = getMaxItemID();
        if(end > check.length){
            end = check.length;
        }
        Inventory inv = new Inventory(minimumSizeOfVI(check));

        for(int i = 1; i < end; i++){
            try{
                while(check[i] > 0) {
                    if(check[i] > 100) {
                        inv.addItem(ih.getQuantitiedItemByID(i, 100));
                        check[i] -= 100;
                    }
                    else{
                        inv.addItem(ih.getQuantitiedItemByID(i, check[i]));
                        check[i] = 0;
                    }
                }
            }
            catch(FullInventoryException e){
                System.err.println(i + " ERROR in minimumSizeOfVI or turnCheckIntoInventory");
            }
        }
        return inv;
    }
    public VisualInventory turnCheckIntoVInventory(int[] check, Location loc, int tolerance){
        if(tolerance < 1){
            tolerance = 1;
        }
        int length = check.length;
        Inventory inv = turnCheckIntoInventory(check);
        VisualInventory vinv = new VisualInventory(length);
        Location l;
        Random r = new Random();
        int x;
        int y;
        length = inv.items.length;
        for(int i = 0; i < length; i++){
            x = loc.x + (r.nextInt(tolerance) - (tolerance / 2));
            y = loc.y + (r.nextInt(tolerance) - (tolerance / 2));
            l = new Location(x,y);
            try {
                vinv.addItem(turnItemIntoVItem(inv.items[i], l));
            }
            catch (FullInventoryException e){
                System.err.println("ERROR turnCheckIntVInventory in class ItemHandler");
            }
        }
        return vinv;
    }

    /**
     * return a new Inventory with no nulls, only items. This crunches down the number of slots to whatever is already occupied, empty slots are destroyed
     * @param inv Inventory to be reduces
     * @return new Inventory with all open slots destroyed
     */
    public Inventory removeNullsFromInventory(Inventory inv){
        if(inv == null){
            return null;
        }
        int tally = 0;
        for(int i = 0; i < inv.items.length; i++){
            if(inv.items[i] != null){
                tally++;
            }
        }
        Inventory newInv = new Inventory(tally);
        for(int i = 0; i < inv.items.length; i++){
            if(inv.items[i] != null){
                try{
                    newInv.addItem(inv.items[i]);
                }
                catch (FullInventoryException e){
                    System.err.println("ERROR: removeNullsFromInventory in class ItemHandler");
                }
            }
        }
        return newInv;
    }

    /**
     * return a new VisualInventory with no nulls, only items. This crunches down the number of slots to whatever is already occupied, empty slots are destroyed
     * @param inv VisualInventory to be reduces
     * @return new VisualInventory with all open slots destroyed
     */
    public VisualInventory removeNullsFromVI(VisualInventory inv){
        if(inv == null){
            return null;
        }
        int tally = 0;
        for(int i = 0; i < inv.items.length; i++){
            if(inv.items[i] != null){
                tally++;
            }
        }
        VisualInventory newInv = new VisualInventory(tally);
        for(int i = 0; i < inv.items.length; i++){
            if(inv.items[i] != null){
                try{
                    newInv.addItem(inv.items[i]);
                }
                catch (FullInventoryException e){
                    System.err.println("ERROR: removeNullsFromVI in class ItemHandler");
                }
            }
        }
        return newInv;
    }
}
