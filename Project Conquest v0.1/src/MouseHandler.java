import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
    Handles mouse events
 */
public class MouseHandler implements MouseListener {
    int x;
    int y;
    boolean clicked;
    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        clicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
