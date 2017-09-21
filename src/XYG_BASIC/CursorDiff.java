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
    private MyPoint prevPoint;
    private MyGraphXY myGraphXY;
    private DiffMarkerPoints diffMarkerPoints;
    private Point anchorPoint;
    private MySerie serie;
    private Color paintColor = Color.BLACK;
    private CursorOverPoint cursorOverPoint;
    private boolean setLocationFlag = false;
    private boolean clickSwitcherFlag = false;

    public CursorDiff(DiffMarkerPoints dmp, MyGraphXY myGraphXY, MySerie serie, String name) {
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

                if (me.getSource() instanceof CursorDiff && me.getButton() == 1 && clickSwitcherFlag == false) {
//                    System.out.println("CLICKED: A");
                    clickSwitcherFlag = true;
                    myGraphXY.REPAINT_ON_MOUSE_MOOVE = true;
                    anchorPoint = me.getPoint();
                    //
                    if (point != null) {
                        diffMarkerPoints.remove(point);
                    }
                    //
                    prevPoint = point;
                } else if (me.getSource() instanceof CursorDiff && me.getButton() == 1) {
//                    System.out.println("CLICKED: B");
                    clickSwitcherFlag = false;
                    MyPoint point = cursorOverPoint.getPoint();
                    if (prevPoint != null) {
                        diffMarkerPoints.remove(prevPoint);
                    }
                    if (point != null) {
                        diffMarkerPoints.add(point);
                    }
                }

//                drawCursor(myGraphXY.getGraphics());
            }
        });

    }

    public void setPoint(MyPoint point) {

        this.myGraphXY.REPAINT_ON_MOUSE_MOOVE = false;
        this.setLocationFlag = false;
        this.anchorPoint = null;
        this.point = point;
        if (point == null) {
            this.setLocation(0, 0);
            return;
        }
        this.paintColor = point.getPointColor();
    }

    protected void drawCursor(Graphics g) {
        if (setLocationFlag == false) {
            if (point == null) {
                return;
            }
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
//        System.out.println("x: " + x + " / y: " + y + " / caller: " + caller);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(myGraphXY.MARKER_STROKE);
        g2.setPaint(paintColor);
        //
//        if (diffMarkerPoints.bothMarkersSet() == false) {
        cursorOverPoint.go(x, diffMarkerPoints.bothMarkersSet());
//        }
        //
        if (cursorOverPoint.isCursorOverPoint()) {
            this.setLocation(x - 5, 0);//
            myGraphXY.setComponentZOrder(cursorOverPoint.getPoint(), 0);
        } else {
            this.setLocation(0, 0);
        }
        //
        g2.drawLine(x, 0, x, myGraphXY.getHeight()); // X

        if (drawY) {
            g2.drawLine(0, y, myGraphXY.getWidth(), y); // Y
        }

//        this.setBorder(BorderFactory.createLineBorder(Color.red));
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
