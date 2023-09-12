import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Random;


/*
    Main function that handles the window the game is drawn in
 */
public class DrawPanel extends JPanel implements Runnable {
    final int screenWidth;
    final int screenHeight;

    Sound sound = new Sound();
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    int FPS = 60;
    int initX = 0;
    int initY = 0;
    int initScale = 16;

    Camera cam = new Camera(initX,initY,initScale);
    Camera staticCam = new Camera(initX,initY,initScale);

    MapTile TEST_ONLY;
    public MapTile runTest(){
        LandscapeMaker l = new LandscapeMaker();
        VisualInventory v = new VisualInventory(50);

        Random rr = new Random();
        int impulse = rr.nextInt(100000000);

        System.out.println(impulse);
        MapTile test = new MapTile(new Landscape(l.createBlocks(new Block[100][50],impulse,(byte)0,(byte)0), new Plant[1]), v);//change to 10

        return test;
    }



    public DrawPanel() {
        //size up the window
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screen.getWidth();
        screenHeight = (int) screen.getHeight();
        System.out.println("Your size is " + screenWidth + "x" + screenHeight);
        this.setPreferredSize(screen);
        this.setBackground(new Color(22,22,29));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);

        TEST_ONLY = runTest();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        this.playMusic(0);
    }

    /**
     * Main game function, runs at 60 hertz (ideally)
     */
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; //60 FPS, or 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();//update game state

            repaint();//draw new game state on screen

            try{//run one frame
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0){//if experiencing lag
                    if(remainingTime < -166){//if we're behind by more than 10 frames, alert me
                        System.out.println("Experiencing significant latency  (-" + (remainingTime/-16.66) + " frames)");
                    }
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            }
            catch(InterruptedException e){//if game crashes
                e.printStackTrace();
            }
        }
    }

    /**
     * Run game updates. Details within
     */
    public void update() {
        //check keys
        //camera controls

        //scale the distance you move to the zoom factor
        if (keyH.left) {
            cam.reposition(cam.pos.x + (cam.scaleFactor >>> 2), cam.pos.y);
        }
        if (keyH.up) {
            cam.reposition(cam.pos.x, cam.pos.y + (cam.scaleFactor >>> 2));
        }
        if (keyH.right) {
            cam.reposition(cam.pos.x - (cam.scaleFactor >>> 2), cam.pos.y);
        }
        if (keyH.down) {
            cam.reposition(cam.pos.x, cam.pos.y - (cam.scaleFactor >>> 2));
        }
        if(keyH.minus){
            if(cam.scaleFactor > 4){
                cam.rescale(cam.scaleFactor - 4);
            }
            keyH.minus = false;
        }
        if(keyH.plus){
            if(cam.scaleFactor < 36){
                cam.rescale(cam.scaleFactor + 4);
            }
            keyH.plus = false;
        }

        if(mouseH.clicked){
            BlockHandler b = new BlockHandler();
            Location l = b.getBlockAddressFromScreenPosition(new Location(mouseH.x,mouseH.y), staticCam);

            Random r = new Random();
            TEST_ONLY.mineBlock(TEST_ONLY.landscape.blocks[l.x][l.y], r.nextInt(), l);
        }
    }

    public void playMusic(int i){
        sound.setFile(1);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.end();
    }

    public void playSound(int i){
        sound.setFile(i);
        sound.play();
    }

    /**
     * draw the game on screen inside the JFrame
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //draw shit
        //set the staticCam to current values of Cam
        staticCam.moveToMe(cam);

        TEST_ONLY.draw(g2, staticCam);
    }




}
