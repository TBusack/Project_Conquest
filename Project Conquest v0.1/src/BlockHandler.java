//this class runs important functions for parsing Block data
public class BlockHandler {
    int tileLength = 6;    //number of pixels in each block
    int blocksPerLine = 6; //number of tiles in a row in the texture file
    String texture = "block-textures.png"; //file name of texture file
    public BlockHandler(){
        //do nothing
    }

    /**
     * return Block object with the given ID
     * @param id number associated with given Block
     * @return Block object
     */
    public Block getBlockByID(int id){
        switch(id){
            case 0://air
                return new Block("air", (byte) id, (byte)0);
            case 1://dirt
                return new Block("dirt", (byte) id, (byte) 1);
            case 2://grass
                return new Block("grass", (byte) id, (byte) 0);
            case 3://gypsum sand
                return new Block("gypsum sand", (byte) id, (byte) 1);
            case 4://shale
                return new Block("slate", (byte) id, (byte) 2);
            case 5://conglomerate
                return new Block("conglomerate", (byte) id, (byte) 4);
            case 6://full water
                return new Block("water", (byte) id, (byte) 2);

            case 12://coal ore
                return new Block("coal ore", (byte) id, (byte) 2);
            case 13://iron ore
                return new Block("iron ore", (byte) id, (byte) 3);
            case 14://quartz
                return new Block("quartz deposit", (byte) id, (byte) 3);
            case 15://sapphire
                return new Block("sapphire deposit", (byte) id, (byte) 3);
            case 16://sand gem
                return new Block("sandgem deposit", (byte) id, (byte) 1);
            case 17://obsidian
                return new Block("obsidian deposit", (byte) id, (byte) 6);



        }
        return null;
    }

    /**
     * change entire array of numbers into an array of Blocks. Useful for map building
      * @param arr of ID's
     * @return array of Blocks of same size
     */
    public Block[][] convertArrayByID(byte[][] arr){
        //make a new Block array with same dimensions as int array
        int length = arr.length;
        int height = arr[0].length;
        Block[][] blocks = new Block[length][height];

        //fill Block array
        for(int i = 0; i < length; i++){
            for(int j = 0; j < height; j++){
                blocks[i][j] = getBlockByID(arr[i][j]);
            }
        }
        return blocks;
    }

    /**
     * Converts a pixel onscreen to its equivalent Location in the Block array. This works for any size!!
     * @param mouseLocation - point onscreen that the mouse is clicked
     * @param cam - Camera object currently applied to the Landscape
     * @return Location in array of the Block
     */
    public Location getBlockAddressFromScreenPosition(Location mouseLocation, Camera cam){
        //return location in Block array of the clicked point
        return new Location((mouseLocation.x - cam.pos.x) / cam.scaleFactor, (mouseLocation.y - cam.pos.y) / cam.scaleFactor);
    }
}
