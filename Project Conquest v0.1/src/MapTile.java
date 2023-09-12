import java.awt.*;
import java.util.Random;

/*
    The entire map that gets rendered onscreen at a time. There will never be more than one of these loaded and rendered at once
 */
public class MapTile implements Drawable{

    Landscape landscape;    //the Blocks
    VisualInventory drops;  //item drops on the ground

    BlockHandler blockHandler = new BlockHandler();
    ItemHandler itemHandler = new ItemHandler();
    MiningHandler miningHandler = new MiningHandler();

    Plant[] plants;

    //include structures, characters, plants, workers

    //constructor
    public MapTile(Landscape landscape, VisualInventory drops){
        this.landscape = landscape;
        this.drops = drops;
        plants = new Plant[5];//standard fertility
    }

    /**
     * Constructor for MapTile with Plants, inventory, and landscape
     * @param landscape landscape
     * @param drops drops starting on the map
     * @param plants plants native to the map
     */
    public MapTile(Landscape landscape, VisualInventory drops, Plant[] plants){
        this.landscape = landscape;
        this.drops = drops;
        this.plants = plants;
    }

    /**
     * Constructor for MapTile with Plants, inventory, and landscape
     * @param landscape landscape
     * @param drops drops starting on the map
     * @param plants plants native to the map
     * @param fertility maximum number of plants allowed in the map
     */
    public MapTile(Landscape landscape, VisualInventory drops, Plant[] plants, int fertility){
        this.landscape = landscape;
        this.drops = drops;
        this.plants = new Plant[fertility];

    }


    /**
     * destroy a given Block and put a dropped VisualItem in its place
     * @param block Block being destroyed
     * @param impulse seed for drop
     * @param location Location to render the VItem
     */
    public void mineBlock(Block block, int impulse, Location location) {
        //put the drops into the drops array
        try{
            itemHandler.combineVInventories(drops, miningHandler.turnBlockIntoDrops(block, impulse, location));
        }
        catch (MinedAirException e){
            //do nothing
        }
        catch (FullInventoryException e){//if full, delete a random dropped item to make room
            Random rand = new Random(impulse);
            try{
                drops.items[rand.nextInt(drops.items.length)] = miningHandler.turnBlockIntoDrops(block, impulse, location).items[0];
            }
            catch (MinedAirException f){
                //do nothing
            }
        }
        //now turn the block into air
        landscape.blocks[location.x][location.y] = blockHandler.getBlockByID(0);
    }

    public MapObject getFirstObjectHere(){
        //prioritize humans, then animals, then plants, then structures
        //do this next!!

        return null;
    }

    @Override
    public void draw(Graphics2D g2d, Camera cam) {
        landscape.draw(g2d, cam);
        drops.draw(g2d, cam);
        //draw the other elements as you add them
    }
}
