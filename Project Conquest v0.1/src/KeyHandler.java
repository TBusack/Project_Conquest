import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
    Function that handles keyboard listening and events
 */
public class KeyHandler implements KeyListener {
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
    boolean esc = false;
    boolean plus = false;
    boolean minus = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 27){//esc
            esc = true;
        }
        if(code == 37){//left
            left = true;
        }
        if(code == 38){//up
            up = true;
        }
        if(code == 39){//right
            right = true;
        }
        if(code == 40){//down
            down = true;
        }
        if(code == 61){//equals key, plus when shifted
            plus = true;
        }
        if(code == 45){//minus
            minus = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 27){//esc
            esc = false;
        }
        if(code == 37){//left
            left = false;
        }
        if(code == 38){//up
            up = false;
        }
        if(code == 39){//right
            right = false;
        }
        if(code == 40){//down
            down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();
    }

}
