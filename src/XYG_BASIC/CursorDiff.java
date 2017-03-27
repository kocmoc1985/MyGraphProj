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
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 *
 * @author KOCMOC
 */
public class CursorDiff extends JComponent implements MouseMotionListener {

    private MyPoint point;
    private MyGraphXY myGraphXY;
    private DiffMarkerPoints diffMarkerPoints;
    private Point anchorPoint;
    private MySerie serie;
    private CursorOverPoint cursorOverPoint;
    private boolean setLocationFlag = false;

    public CursorDiff(DiffMarkerPoints dmp,MyGraphXY myGraphXY, MySerie serie, String name) {
        super();
        this.diffMarkerPoints = dmp;
        this.myGraphXY = myGraphXY;
        this.serie = serie;
        setName(name);
        setLayout(null);
        setBackground(Color.black);
        init();
    }

    private void init() {
        myGraphXY.add(this);
        //
        cursorOverPoint = new CursorOverPoint(serie);
        //
        this.addMouseMotionListener(this);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println("CLICKED");
                MyPoint point = cursorOverPoint.getPoint();
                if (point != null) {
                    setPoint(point);
                }
                drawCursor(myGraphXY.getGraphics());
            }
        });

    }

    public void setPoint(MyPoint point) {
        this.myGraphXY.REPAINT_ON_MOUSE_MOOVE = false;
        this.setLocationFlag = false;
        this.anchorPoint = null;
        this.point = point;
    }

    protected void drawCursor(Graphics g) {

        if (setLocationFlag == false) {
            this.setLocation(point.x - 5, 0);//
            this.setSize(10, myGraphXY.getHeight());
            draw(g, point.x, point.y, true, "A");
            setLocationFlag = true;
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
        //
        cursorOverPoint.go(x);
        //
        if (cursorOverPoint.isCursorOverPoint()) {
            this.setLocation(x - 5, 0);//
        }else{
            this.setLocation(0,0);
        }
        //
//        this.setLocation(x - 5, 0);
        g2.drawLine(x, 0, x, myGraphXY.getHeight()); // X

        if (drawY) {
            g2.drawLine(0, y, myGraphXY.getWidth(), y); // Y
        }
        
//        this.setBorder(BorderFactory.createLineBorder(Color.red));
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
