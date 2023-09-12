import java.util.Random;

/**
 * Class tasked with creating new plants and reverse engineering a plant into pieces
 */
public class PlantHandler {

    /**
     * return a standard plant with a given ID
     * @param ID of Plant
     * @return standard Plant
     */
    public Plant getPlantById(int ID, Location loc){
        switch(ID){
            default:
                return null;
            case 0://small tree
                //bark, keep hitbox same
                Plant t0a = new Plant(loc, 35, 1, "TEXTURE FILES/SmallTree.png", 8, 12, 4, 12, 0, 15);//trunk

                //left leaves
                Plant l0a = new Plant(new Location(loc.x - 8, loc.y - 11), 12, 0, "TEXTURE FILES/SmallTree.png", 0, 1, 10, 11, 1, 7);//left leaves
                l0a.resizeHitbox(9,9);
                l0a.setHitbox(loc.x - 7, loc.y - 9);

                //right leaves
                Plant l0b = new Plant (new Location(loc.x + 2, loc.y - 12), 10, 0, "TEXTURE FILES/SmallTree.png", 10, 0, 5, 12, 1, 7);//right leaves
                l0b.resizeHitbox(3,11);
                l0b.setHitbox(loc.x + 3, loc.y - 11);

                t0a.addDependent(l0a);
                t0a.addDependent(l0b);
                return t0a;

            case 1://small bush
                Plant t1a = new Plant(loc, 10, 0, "TEXTURE FILES/SmallTree.png", 10, 0, 5, 9, 1, 7);
                t1a.resizeHitbox(3,8);
                t1a.setHitbox(loc.x + 1, loc.y + 1);
                return t1a;
        }
    }
    //create separate functions to build irregular plants


    /**
     * Convert a given plant into an array representing the drops of a plant
     * @param plant to be turned into drops
     * @return array representing drops from the plant. indexes are IDs, numbers in each are quantities
     */
    public int[] turnPlantIntoDrops(Plant plant) {
        ItemHandler it = new ItemHandler();
        int[] drops = new int[it.getMaxItemID() + 1];
        if(plant == null){
            return drops;
        }
        if(plant.dropTable == -1){
            return null;
        }
        for(int i = 0; i < plant.dropRolls; i++){
            drops[makeOnePlantDrop(plant.dropTable)]++;
        }
        plant.dropTable = -1;
        return drops;
    }

    /**
     * take in a drop table, and return the ID of a single systematically chosen item from the drop table
     * @param table number ID of a given drop table
     * @return ID of chosen item
     */
    private int makeOnePlantDrop(int table) {
        Random r = new Random();
        ItemHandler i = new ItemHandler();
        float val = r.nextFloat();
        switch (table) {
            default:
                return 0;
            case 0:
                //  Table 0:
                //      0.90 - Wood
                //      0.10 - Nothing
                if (val > 0.1) {
                    return 7;
                }
                break;
            case 1:
                //  Table 1:
                //      0.65 - Wood
                //      0.30 - Leaves
                //      0.01 - Apple
                //      0.04 - Nothing
                if (val > 0.35) {
                    return 7;
                } else if (val > 0.05) {
                    return 8;
                } else if (val < 0.01) {
                    return 9;
                }
                break;
        }
        return 0;
    }
}
