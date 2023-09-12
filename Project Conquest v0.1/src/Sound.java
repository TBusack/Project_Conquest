import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {

    Clip sound;
    URL audioURL[] = new URL[30];

    public Sound () {
        audioURL[0] = getClass().getResource("SOUNDS/The Epoch of Armadra.wav");
        audioURL[1] = getClass().getResource("SOUNDS/Yet Another Sunrise on a Distant Planet.wav");
    }

    /**
     * set loaded sound to a given track in the game's files
     * @param i ID of the sound
     */
    public void setFile(int i){

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioURL[i]);
            sound = AudioSystem.getClip();
            sound.open(ais);
        }
        catch(Exception e){
            System.err.println("Sound " + audioURL[i] + " (" + i + ") not found.");
            //do nothing
        }
    }

    /**
     * Begin playing loaded sound
     */
    public void play(){
        sound.start();
    }

    /**
     * loop loaded sound continuously
     */
    public void loop(){
        sound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * cease loaded sound
     */
    public void end(){
        sound.stop();
    }



}