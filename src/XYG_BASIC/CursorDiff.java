/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

/**
 *
 * @author KOCMOC
 */
public class CursorDiff extends JComponent implements MouseMotionListener {

    private MyPoint point;
    private MyGraphXY myGraphXY;
    protected Point anchorPoint;

    public CursorDiff(MyGraphXY myGraphXY, String name) {
        super();
        this.myGraphXY = myGraphXY;
        setName(name);
        setLayout(null);
        setBackground(Color.black);
        init();
    }

    private void init() {
        this.addMouseMotionListener(this);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println("CLICKED");
            }
        });

    }

    public void setPoint(MyPoint point) {
        myGraphXY.REPAINT_ON_MOUSE_MOOVE = false;
        this.point = point;
    }
    private boolean flag = false;

    protected void drawCursor(Graphics g) {

        if (flag == false) {
//            this.setLocation(point.x - (point.POINT_D / 2), 0);
            this.setSize(10, myGraphXY.getHeight());
            myGraphXY.remove(this);
            myGraphXY.add(this);
            draw(g, point.x, point.y, true, "A");
            flag = true;
        }

        if (anchorPoint != null) {
            draw(g, anchorPoint.y, false, "B");
        } else {
            draw(g, point.x, point.y, true, "C");
        }
    }

    private void draw(Graphics g, int y, boolean drawY, String caller) {
        int x_ = myGraphXY.POINT_ON_SCREEN_MOOVE.x_Real;
        draw(g, x_, y, drawY, caller);
    }

    private void draw(Graphics g, int x, int y, boolean drawY, String caller) {
        System.out.println("x: " + x + " / y: " + y + " / caller: " + caller);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(myGraphXY.MARKER_STROKE);
        g2.setPaint(point.getPointColor());


        this.setLocation(x - 5, 0);
        g2.drawLine(x, 0, x, myGraphXY.getHeight()); // X

        if (drawY) {
            g2.drawLine(0, y, myGraphXY.getWidth(), y); // Y
        }
//        myGraphXY.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        anchorPoint = e.getPoint();
        myGraphXY.REPAINT_ON_MOUSE_MOOVE = true;
        System.out.println("DRAG....");
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
