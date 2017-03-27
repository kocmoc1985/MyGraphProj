package DND;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

public class DraggableComponent extends JComponent implements MouseMotionListener {

    private boolean draggable = true;
    protected Point anchorPoint;
    protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    protected boolean overbearing = false;
    public final DraggableComponent handle = this;

    public DraggableComponent() {
        setOpaque(true);
        setBackground(new Color(240, 240, 240));
        addDragListeners();
    }

    /**
     * We have to define this method because a JComponent is a void box. So we
     * have to define how it will be painted. We create a simple filled
     * rectangle.
     *
     * @param g Graphics object as canvas
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int anchorX = anchorPoint.x;
        int anchorY = anchorPoint.y;
        System.out.println("DRAG....");

        Point parentOnScreen = getParent().getLocationOnScreen();
        Point mouseOnScreen = e.getLocationOnScreen();
        Point position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);
        setLocation(position);
        System.out.println("SetLocation B");

        //Change Z-Buffer if it is "overbearing"
        if (overbearing) {
            getParent().setComponentZOrder(handle, 0);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        anchorPoint = e.getPoint();
//        System.out.println("AnchorPoint set");
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Remove all Mouse Motion Listener. Freeze component.
     */
    private void removeDragListeners() {
        for (MouseMotionListener listener : this.getMouseMotionListeners()) {
            removeMouseMotionListener(listener);
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Get the value of draggable
     *
     * @return the value of draggable
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Set the value of draggable
     *
     * @param draggable new value of draggable
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
        if (draggable) {
            addDragListeners();
        } else {
            removeDragListeners();
        }

    }
    
    private void addDragListeners(){
        this.addMouseMotionListener(this);
    }

    /**
     * Get the value of draggingCursor
     *
     * @return the value of draggingCursor
     */
    public Cursor getDraggingCursor() {
        return draggingCursor;
    }

    /**
     * Set the value of draggingCursor
     *
     * @param draggingCursor new value of draggingCursor
     */
    public void setDraggingCursor(Cursor draggingCursor) {
        this.draggingCursor = draggingCursor;
    }

    /**
     * Get the value of overbearing
     *
     * @return the value of overbearing
     */
    public boolean isOverbearing() {
        return overbearing;
    }

    /**
     * Set the value of overbearing
     *
     * @param overbearing new value of overbearing
     */
    public void setOverbearing(boolean overbearing) {
        this.overbearing = overbearing;
    }
}