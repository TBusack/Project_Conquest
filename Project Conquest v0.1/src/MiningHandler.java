import java.util.Random;

/*
    Handles tasks related to the destruction of Blocks and the creation of drops
 */
public class MiningHandler {

    //constructor
    public MiningHandler(){
        //do nothing
    }



    //ENSURE THE IMPULSE IS RANDOMIZED. If it is fixed, it will always give the same drops

    /**
     * convert a given Block into its drops
     * @param block Block being mined
     * @param impulse seed for RNG
     * @param l Location of new VisualItem(s)
     * @return VisualInventory containing all the VItems
     * @throws MinedAirException if the Block mined is not allowed to be broken
     */
    public VisualInventory turnBlockIntoDrops(Block block, int impulse, Location l) throws MinedAirException{
        Random rand = new Random(impulse);
        ItemHandler i = new ItemHandler();

        switch (block.id){
            default://unprogrammed blocks
                throw new MinedAirException(l);

            case 1://dirt
                VisualInventory v = new VisualInventory(1);//hey, I know this sucks, but it's bc different items give more than 1 drop at once
                int temp = rand.nextInt(201);
                if(temp < 195) {//97.5% chance - dirt
                    v.items[0] = i.getVItemById(1, l);
                }
                else if(temp < 198){//1.5% chance - stone
                    v.items[0] = i.getVItemById(4, l);
                }
                else if(temp < 199){//0.5% chance - coal
                    v.items[0] = i.getVItemById(12, l);
                }
                else{//0.5% chance - iron ore
                    v.items[0] = i.getVItemById(13, l);
                }
                return v;

            case 2://grass
                VisualInventory v2 = new VisualInventory(1);
                if(rand.nextFloat() < 0.005){
                    v2.items[0] = i.getVItemById(10, l);//1/200 chance to get carrot
                }
                else{
                    v2.items[0] = i.getVItemById(2, l);//otherwise get plant fibers
                }
                return v2;

            case 3://sand
                VisualInventory v3 = new VisualInventory(1);
                if(rand.nextFloat() < 0.02){//2% chance - stone
                    v3.items[0] = i.getVItemById(4, l);
                    return v3;
                }
                //98% - sand
                v3.items[0] = i.getVItemById(3, l);
                return v3;

            case 4://slate
                VisualInventory v4 = new VisualInventory(1);
                v4.items[0] = i.getVItemById(4, l);
                return v4;

            case 5://conglomerate
                VisualInventory v5 = new VisualInventory(2);
                v5.items[0] = i.getVItemById(5, l);
                int i2 = rand.nextInt(10);
                if(i2 == 1){//10% quartz
                    v5.items[1] = i.getVItemById(14, l);
                }
                else{//90% two conglomerate
                    v5.items[1] = i.getVItemById(5, l);
                }
                return v5;

            case 12://coal ore
                VisualInventory v12 = new VisualInventory(1);
                v12.items[0] = i.getVItemById( 12, l);
                return v12;

            case 13://iron ore
                VisualInventory v13 = new VisualInventory(1);
                v13.items[0] = i.getVItemById( 13, l);
                return v13;

            case 14://quartz deposit
                VisualInventory v14 = new VisualInventory(1);
                v14.items[0] = i.getVItemById( 14, l);
                return v14;
        }
    }
}
