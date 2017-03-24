/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import DND.DraggableComponent;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author KOCMOC
 */
public class CursorDiff extends DraggableComponent {

    private MyPoint point;
    private MyGraphXY myGraphXY;

    public CursorDiff(MyGraphXY myGraphXY, String name) {
        super();
        this.myGraphXY = myGraphXY;
        setName(name);
        setLayout(null);
        setBackground(Color.black);
    }

    public void setPoint(MyPoint point) {
        this.point = point;
    }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.clearRect(0, 0, getWidth(), getHeight());
    
            g2d.setColor(Color.red);
            System.out.println("Drawing");
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    

    protected void drawCursor(Graphics g) {
        System.out.println("draw");
        Graphics2D g2 = (Graphics2D) g;
//        g2.setStroke(myGraphXY.MARKER_STROKE);
//        g2.setPaint(point.getPointColor());
//
//        g2.drawLine(point.x, 0, point.x, myGraphXY.getHeight()); // X
//        g2.drawLine(0, point.y, myGraphXY.getWidth(), point.y); // Y


        this.setLocation(point.x - (point.POINT_D / 2), 0);

        this.setSize(5, myGraphXY.getHeight());
        myGraphXY.remove(this);
        myGraphXY.add(this);
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
        point.x = anchorX;
        point.y = anchorY;
//        myGraphXY.validate();

//        //Change Z-Buffer if it is "overbearing"
//        if (overbearing) {
//            getParent().setComponentZOrder(handle, 0);
//            repaint();
//        }
    }
}
