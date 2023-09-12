import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//this class is the land in a given MapTile
public class Landscape implements Drawable{
    final int length;
    final int height;

    Plant[] plants;
    Block[][] blocks;

    BufferedImage image;

    //constructor
    public Landscape(Block[][] blocks, Plant[] plants){
        this.blocks = blocks;
        this.length = blocks.length;
        this.height = blocks[0].length;
        this.plants = plants;
        try {
            //set image to the file
            image = ImageIO.read(new File("TEXTURE FILES/block-textures.png"));
        } catch (IOException e) {
            //set to missing texture
            image = null;
            System.err.println("ERROR: block-textures.png is missing");
        }
    }

    /**
     * add a new Plant to the list of Plants
     * @param plant new Plant to add
     */
    public void addPlant(Plant plant){
        for(int i = 0; i < plants.length; i++){
            if(plants[i] == null){
                plants[i] = plant;
                break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera cam) {
        //run through the array and draw each block
        for (int i = 0; i < length; i++) {
            BufferedImage block;
            for (int j = 0; j < height; j++) {
                //draw the blocks
                if (blocks[i][j] != null) {
                    block = this.image.getSubimage((blocks[i][j].id & 0x7) << 3,(blocks[i][j].id >>> 3) << 3, 8, 8);//block.id % 8, block.id // 8
                    g2d.drawImage(block, i * cam.scaleFactor + cam.pos.x, j * cam.scaleFactor + cam.pos.y, cam.scaleFactor, cam.scaleFactor, null);
                }
            }
        }
    }
}
