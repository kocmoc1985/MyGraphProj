/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unused;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Administrator
 */
public class Line {

    public Point p1;
    public Point p2;
    //===============================
    public int p1_y;
    public int p2_y;
    public int p1_x;
    public int p2_x;
    //===============================

    public Line() {
        p1 = new Point();
        p2 = new Point();
    }

    public Line(Point a, Point b) {
        p1 = new Point(a);
        p2 = new Point(b);
    }

    public Line(int x1, int y1, int x2, int y2, int graphPanelHeight) {
        p1_x = x1;
        p2_x = x2;
        p1_y = y1;
        p2_y = y2;
        p1 = new Point(x1, graphPanelHeight - y1);
        p2 = new Point(x2, graphPanelHeight - y2);
    }

    public void drawLine(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    public void drawPoints(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLUE);
        g.drawOval(p1.x, p1.y, 5, 5);
        g.drawOval(p2.x, p2.y, 5, 5);
    }

    public void drawFilledPoints(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(p1.x, p1.y, 10, 10);
        g.fillOval(p2.x, p2.y, 10, 10);
    }
}
