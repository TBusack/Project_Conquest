import java.util.Random;

/*
    Non-data class that makes Landscapes for the planet Earth
 */
public class EarthLandscapeMaker {

    int numberOfUniqueBiomes = 1;//ensure this isn't larger than 128; number of different biomes that exist to make
    Random rand;
    byte biome = -1;

    //constructor if no seed is given
    public EarthLandscapeMaker(){
        rand = new Random();
    }

    //constructor if given a seed but not a biome
    public EarthLandscapeMaker(int impulse){
        rand = new Random(impulse);
    }

    // constructor if given both a seed AND biome
    public EarthLandscapeMaker(int impulse, byte biome){
        rand = new Random(impulse);
        this.biome = biome;
    }


    /**
     * generate a Landscape's Block array for an Earth biome
     * @param blocks Block array being edited
     * @return the Block array
     */
    public Block[][] generateLandscape(Block[][] blocks){
        //if no biome was set, get one randomly
        if(biome == -1){
            biome = (byte) rand.nextInt(numberOfUniqueBiomes);
        }
        //based on the biome, make a full set of Blocks
        switch(biome){
            case 0:
                return makePlains(blocks);
        }
        return blocks;
    }

    /**
     * helper function that creates the plains biome (biome 0)
     * @param blocks Block array that will be edited
     * @return edited Block array
     */
    private Block[][] makePlains(Block[][] blocks){
        //initialize variables
        BlockHandler b = new BlockHandler();
        int lengthOfHill = 0;
        int heightOfHill = 0;

        //prepare to make first column somewhere between 50% and 66.6% of the way up
        int start = (blocks[0].length / 2 - rand.nextInt((blocks[0].length / 6)));

        //build successive columns based on location of ground level
        //curCol is current column being made
        for(int curCol = 0; curCol < blocks.length; curCol++){
            //draw the column
            for(int i = 0; i < start; i++){//fill in the air
                blocks[curCol][i] = b.getBlockByID(0);
            }
            blocks[curCol][start] = b.getBlockByID(2);//fill in 1 grass
            blocks[curCol][start + 1] = b.getBlockByID(1);//fill in 2 dirt
            blocks[curCol][start + 2] = b.getBlockByID(1);
            for (int i = start + 3; i < blocks[0].length; i++) {//fill rest with shale
                blocks[curCol][i] = b.getBlockByID(4);
            }

            //manage length of the hill
            if(lengthOfHill <= 0){
                lengthOfHill = rand.nextInt(7) + 3;
                heightOfHill = rand.nextInt(6) - 3;
            }

            if(start < 18){//too high
                heightOfHill = -4;
                lengthOfHill = 6;
            }
            if(start > 30){//too low
                heightOfHill = 4;
                lengthOfHill = 6;
            }

            //if height is negative
            if(heightOfHill < 0){
                //if -height is less than 1.5 times -length, go down 2
                if(heightOfHill < -1.5*lengthOfHill){
                    heightOfHill += 2;
                    start += 2;
                }
                //if its between 1.5 and 0.6 of length, go down 1
                else if(heightOfHill < -0.6*lengthOfHill){
                    heightOfHill++;
                    start++;
                }
                //otherwise don't change it
            }
            //if height is positive
            else if(heightOfHill > 0){
                //if -height is less than 1.5 times -length, go down 2
                if(heightOfHill > 1.5*lengthOfHill){
                    heightOfHill -= 2;
                    start -= 2;
                }
                //if its between 1.5 and 0.6 of length, go down 1
                else if(heightOfHill > 0.6*lengthOfHill){
                    heightOfHill--;
                    start--;
                }
                //otherwise don't change
            }
            //with start changed, repeat algorithm from the start
            lengthOfHill--;
        }
        //create a new random number generator to make any lakes. It stays consistent if a seed is given
        Random lake = new Random(rand.nextInt(1000000000));
        float f = lake.nextFloat();
        if(f < 0.5) {//50% chance to try to spawn a lake
            int loc = lake.nextInt(blocks.length - 5) + 1;//make it somewhere between the second block and the fourth-to-last block
            drawLake(0, blocks, loc);
        }

        //now spawn additional features. Do not overwrite current blocks of the following types:
        int[] stdBlacklist = new int[]{0,1,2};//blacklist air, grass, and dirt

        //now spawn the ore into the map
        spawnOre(blocks, 12, 22, 40, (float) 0.07, (float) 0.85, stdBlacklist);//putting in coal
        spawnOre(blocks, 13, 22, 45, (float) 0.03, (float) 0.82, stdBlacklist);//putting in iron
        spawnOre(blocks, 14, 30, blocks[0].length - 3, (float) 0.02, (float) 0.75, stdBlacklist);//putting in quartz

        return blocks;
    }




    //HANDLE LAKES {

    /**
     * Function that attempts to find a viable lake to draw starting at a given point
     * @param biome int value of biome being drawn
     * @param blocks Block array being checked
     * @param startCol first column in the array to try building the lake in
     */
    private void drawLake(int biome, Block blocks[][], int startCol) {
        //set up the variables by biome
        int maxDepth = 0;
        int maxLength = 0;
        boolean ready = false;
        int lookForBlock = 2;//the block to look for during surface finding functions
        switch(biome){
            case 0:
                maxDepth = 7;
                maxLength = 25;
                lookForBlock = 1;
                break;
        }
        //while startCol is still small enough, but the lake isn't valid, keep trying
        for(int i = startCol; startCol < findOtherSideOfLake(blocks, startCol, lookForBlock) - 2 && !ready; startCol++){
            //if it's too long
            if(findOtherSideOfLake(blocks, startCol, lookForBlock) > startCol + maxLength){
                continue;
            }
            //if it has an uncooperative neighbor
            if(findHeightOfFirstBlock(blocks[startCol], lookForBlock) <= findHeightOfFirstBlock(blocks[startCol-1], lookForBlock)){
                continue;
            }
            //if it isn't a valid lake
            if(!findValidLake(blocks, startCol, maxDepth, maxLength, lookForBlock)){
                continue;
            }
            //otherwise, green light
            ready = true;
        }
        //if it's good on length still
        if(startCol < findOtherSideOfLake(blocks, startCol, lookForBlock) - 2){
            drawLake(blocks, startCol, findOtherSideOfLake(blocks, startCol, lookForBlock), findHeightOfFirstBlock(blocks[startCol], lookForBlock), biome);
        }
    }

    /**
     * helper function that finds the first Block of a given ID in a 1-D array
     * @param blocks single line of Block objects representing a column
     * @param id ID of the target Block
     * @return
     */
    private int findHeightOfFirstBlock(Block[] blocks, int id){
        for(int i = 0; i < blocks.length; i++){
            if(blocks[i].id == id){
                return i;
            }
        }
        return -1;
    }

    /**
     * helper function that recurses down the banks attempting to find a valid lake to draw
     * @param blocks Block array
     * @param startCol first column to attempt a lake in
     * @param maxDepth position in the array of the bottom Block of the lake
     * @param maxLength distance in columns between startCol and the opposite side
     * @param target Block on the opposite side of the lake we are looking for
     * @return true if a lake can be drawn starting at startCol
     */
    private boolean findValidLake(Block[][] blocks, int startCol, int maxDepth, int maxLength, int target){
        //see if the lake is too long
        if(findOtherSideOfLake(blocks, startCol, target) > startCol+maxLength){
            return false;//too long
        }
        int surface = findHeightOfFirstBlock(blocks[startCol], target);
        //find depth at all points, if any of them are too deep, return false
        for(int i = startCol; i < findOtherSideOfLake(blocks, startCol, target); i++){
            if(findHeightOfFirstBlock(blocks[i], target) > surface + maxDepth){
                return false;//too deep
            }
        }
        return true;
    }

    /**
     * find first column with a solid block on the opposite side of the lake
     * @param blocks Block array
     * @param startCol first column to check
     * @param id id of solid Block findHeightOfFirstBlock should look for
     * @return location of opposite edge, or -1 if there isn't one
     */
    private int findOtherSideOfLake(Block[][] blocks, int startCol, int id){
        int surface = findHeightOfFirstBlock(blocks[startCol], id);
        //find the first block above this one
        for(int i = startCol + 1; i < blocks.length; i++){
            if(findHeightOfFirstBlock(blocks[i], id) < surface){
                return i;
            }
        }
        return -1;
    }

    /**
     * Force a lake to be drawn in the array starting at a given point and a given height. Variant is the fill type
     * This does not check validity, it forces a lake to be drawn
     * @param blocks Block array
     * @param startCol starting point x-wise
     * @param endPoint ending point y-wise
     * @param surfaceHeight height of surface y-wise
     * @param variant type of fill (NOT Block ID)
     */
    private void drawLake(Block[][] blocks, int startCol, int endPoint, int surfaceHeight, int variant){
        int currentDepth = 0;
        BlockHandler b = new BlockHandler();
        switch(variant){
            case 0://water
                variant = 6;
                break;
        }
        //run down columns
        for(int i = startCol-1; i < endPoint; i++){
            //run down blocks in column
            for(int j = surfaceHeight-1; j < blocks[0].length; j++){
                //if you hit something besides air and grass:
                if(!blocks[i][j].equals(b.getBlockByID(0)) && !blocks[i][j].equals(b.getBlockByID(2))){
                    break;
                }
                blocks[i][j] = b.getBlockByID(variant);
            }
        }
    }

    //END HANDLE LAKES }


    //HANDLE ORE {

    /**
     * spawn a vein of ore in a Block array. Parameters specify restrictions on location, killfactor is how likely the ore is to proliferate
     * blacklist is a list of Block IDs that should not be changed to match the ore
     * @param blocks Block array
     * @param id     ID of ore
     * @param minDep lowest allowable depth of ore
     * @param maxDep highest allowable depth of ore
     * @param frequency average number of veins the program should spawn (this does not guarantee that many will spawn)
     * @param killFactor how likely an adjacent Block is to be turned into ore
     * @param blacklist  list of IDs not to change
     * @return edited Block array
     */
    private Block[][] spawnOre(Block[][] blocks, int id, int minDep, int maxDep, float frequency, float killFactor, int[] blacklist){
        for(int i = 0; i < blocks.length; i++){
            //if threshold is hit, start a vein
            if(rand.nextFloat() < (frequency/killFactor)){
                //begin recursive call
                blocks = proliferateOre(blocks, i, rand.nextInt(maxDep-minDep) + minDep, minDep, id, killFactor, blacklist);
            }
        }

        return blocks;
    }

    /**
     * recursive helper function used to attempt to proliferate the ore to all straight-line adjacent Blocks
     * @param blocks Block array
     * @param x x position in array of ore Block
     * @param y y position in array of ore Block
     * @param minDep minimum depth the ore can proliferate to
     * @param id ID of ore
     * @param killFactor chance of surrounding Blocks to proliferate
     * @param blacklist list of Blocks that should not be edited
     * @return Block array
     */
    private Block[][] proliferateOre(Block[][] blocks, int x, int y, int minDep, int id, float killFactor, int[] blacklist){
        BlockHandler b = new BlockHandler();
        //if the block is blacklisted
        for(int i = 0; i < blacklist.length; i++){
            if(blocks[x][y].equals(b.getBlockByID(blacklist[i]))){
                return blocks;
            }
        }
        //if the block is already the target
        if(blocks[x][y].equals(b.getBlockByID(id))){
            return blocks;
        }
        else if(rand.nextFloat() < killFactor){
            //if the threshold is hit, change the block
            blocks[x][y] = b.getBlockByID(id);

            //recurse, dilute the killFactor
            if(x < blocks.length - 1) {
                blocks = proliferateOre(blocks, x + 1, y, minDep, id, killFactor * killFactor, blacklist);
            }
            if(x > 0) {
                blocks = proliferateOre(blocks, x - 1, y, minDep, id, killFactor * killFactor, blacklist);
            }
            if(y < blocks[0].length - 1) {
                blocks = proliferateOre(blocks, x, y + 1, minDep, id, killFactor * killFactor, blacklist);
            }
            if(y < minDep) {
                blocks = proliferateOre(blocks, x, y - 1, minDep, id, killFactor * killFactor, blacklist);
            }
        }
        return blocks;
    }

    //END HANDLE ORE }


    //HANDLE PLANTS {

    public Plant[] makePlants(){
        PlantHandler p = new PlantHandler();
        switch(biome){
            default:
                return null;
            case 1://plains
                //try to force one plant to spawn. 60% tree, 40% bush
                Plant[] plants = new Plant[1];
                if(rand.nextFloat() > 0.4){
                    //return tree
                }
                else{
                    //return
                }
                return null;
        }
    }

    public Location findValidPlantLocationById(){
        return new Location(0,0);
    }

    public Location findValidPlantLocationByParameters(int startX, int startY, int minWidth, Block[] deadGround){
        return new Location(0,0);
    }

    private boolean validatePlantLocation(Location location){
        return false;
    }

    private Plant spawnPlant(int xCoord, int plantID){
        switch(plantID){
            case 0:
                return null;//return small tree
            case 1:
                return null;//return bush
        }
        return null;
    }

    //END HANDLE PLANTS }
}
