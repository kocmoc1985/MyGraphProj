/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *
 * @author KOCMOC
 */
public class CursorDiff extends JComponent {
    
    private MyPoint point;
    private MyGraphXY myGraphXY;
    private Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
    private Cursor DRAG_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    public CursorDiff(MyGraphXY myGraphXY,String name) {
        this.myGraphXY = myGraphXY;
        setName(name);
        setListeners();
    }

    public void setPoint(MyPoint point) {
        this.point = point;
    }

    protected void drawCursor(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(myGraphXY.MARKER_STROKE);
        g2.setPaint(point.getPointColor());

        g2.drawLine(point.x, 0, point.x, myGraphXY.getHeight()); // X
        g2.drawLine(0, point.y, myGraphXY.getWidth(), point.y); // Y

        this.setLocation(point.x - (point.POINT_D / 2), 0);
        System.out.println("DrawCursor y: " + point.y + " / graphH: " + myGraphXY.getHeight());
        this.setSize(5, myGraphXY.getHeight());
        //OBS! Draw border causes an infinity loop of redraws
//        this.setBorder(BorderFactory.createLineBorder(Color.red)); 
        myGraphXY.remove(this);
        myGraphXY.add(this);
    }

    private void setListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println("Clicked: " + me.getSource());
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                setCursor_(DRAG_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setCursor_(DEFAULT_CURSOR);
            }
        });
    }

    private void setCursor_(Cursor cursor) {
        Frame.getFrames()[0].setCursor(cursor);
    }
}
