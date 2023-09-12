/*
    Move on to character
    or plants. Up to you
 */
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //initialize game
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Project Conquest v0.1");
        window.setUndecorated(true);

        DrawPanel DrawPanel = new DrawPanel();
        window.add(DrawPanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        PlantHandler p = new PlantHandler();
        Plant q = p.getPlantById(0, new Location(0, 0));
        System.out.println(q.reap());

        DrawPanel.startGameThread();

    }
}

/*

     ***  CREDITS  ***

    1. A MASSIVE thanks to RyiSnow for his video on 2D game development, which was instrumental for keyboard inputs and sound effects
            https://www.youtube.com/watch?v=om59cwR7psI (Parts I, II, and IX)

 */