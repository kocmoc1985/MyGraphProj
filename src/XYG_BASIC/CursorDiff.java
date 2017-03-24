/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 *
 * @author KOCMOC
 */
public class CursorDiff extends JComponent {

    private MyGraphXY myGraphXY;
    private Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
    private Cursor DRAG_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    public CursorDiff(MyGraphXY myGraphXY) {
        this.myGraphXY = myGraphXY;

        setListeners();
    }

    protected void drawCursor(Graphics g, MyPoint point, BasicStroke stroke) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(stroke);
        g2.setPaint(point.getPointColor());

        g2.drawLine(point.x, 0, point.x, myGraphXY.getHeight()); // X
        g2.drawLine(0, point.y, myGraphXY.getWidth(), point.y); // Y

        this.setLocation(point.x - (point.POINT_D / 2), 0);
        System.out.println("y: " + point.y + " / graphH: " + myGraphXY.getHeight());
        this.setSize(5, myGraphXY.getHeight());
        this.setBorder(BorderFactory.createLineBorder(Color.red));
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
    
    private void setCursor_(Cursor cursor){
        Frame.getFrames()[0].setCursor(cursor);
    }
}
