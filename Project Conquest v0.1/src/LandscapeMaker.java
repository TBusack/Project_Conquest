/*
    Superclass that delegates creating Landscapes out to various subclasses utilized by each Planet
 */
public class LandscapeMaker {

    //constructor
    public LandscapeMaker(){
        //do nothing
    }


    public Landscape buildLandscape(Block[][] blocks, int impulse, byte planet, byte biome){


        return null;
    }

    /**
     * create the Block array for a Landscape in a given biome on a given Planet
     * @param blocks Block array
     * @param impulse seed for random number generation
     * @param planet integerial ID of the planet the Landscape is on
     * @param biome integerial ID of the biome the Landscape is
     * @return Block array with Blocks loaded in
     */
    public Block[][] createBlocks(Block[][] blocks, int impulse, byte planet, byte biome){
        switch(planet){
            case 0://Earth
                EarthLandscapeMaker a = new EarthLandscapeMaker(impulse, biome);
                return a.generateLandscape(blocks);
            case 1://Armadra

                break;
            case 2://Vlaki

                break;
            case 3://Tundraine

                break;
            case 4://Beaminhi

                break;
        }
        return null;
    }

    //create Plant array from the Block array and biome.
    public Plant[] createPlants(int impulse, byte planet, byte biome){
        switch(planet){
            case 0://Earth
                EarthLandscapeMaker a = new EarthLandscapeMaker(impulse, biome);
                return a.makePlants();
            case 1://Armadra

                break;
            case 2://Vlaki

                break;
            case 3://Tundraine

                break;
            case 4://Beaminhi

                break;
        }
        return null;
    }
}
