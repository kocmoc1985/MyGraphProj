/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_TEST;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class MyPoint_T extends JPanel implements MouseListener {

    private JPanel graph_panel;
    private double x;
    private double y;
    private double x_initial;
    private double y_initial;
    private int x_draw;
    private int y_draw;
    public static final int default_diameter = 10;

    public MyPoint_T(double x, double y, JPanel graph_panel) {
        this.x_initial = x;
        this.y_initial = y;
        this.x = x;
        this.y = y;
        this.graph_panel = graph_panel;
        go();
    }

    private void go() {
        this.addMouseListener(this);
        calcMax();
        setXY();
    }

    private void calcMax() {
        if (MyGraph_T.x__max == 0 && MyGraph_T.y__max == 0) {
            MyGraph_T.x__max = graph_panel.getWidth();
            MyGraph_T.y__max = graph_panel.getHeight();
        }

        while (x > MyGraph_T.x__max || y > MyGraph_T.y__max) {
            MyGraph_T.x__max *= 1.2;
            MyGraph_T.y__max *= 1.2;
            MyGraph_T.one_unit_x = (graph_panel.getWidth() / MyGraph_T.x__max);
            MyGraph_T.one_unit_y = (graph_panel.getHeight() / MyGraph_T.y__max);
        }
    }

    private void setXY() {
        this.x = Math.round(MyGraph_T.one_unit_x * x_initial);
        this.y = graph_panel.getHeight() - Math.round(MyGraph_T.one_unit_y * y_initial);

        this.x_draw = (int) (x - (default_diameter / 2));
        this.y_draw = (int) (y - (default_diameter / 2));

        this.setLocation(this.x_draw, this.y_draw);
        this.setSize(100, 100);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setBackground(Color.yellow);
    }
    
    

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);

        g2.drawOval(x_draw, y_draw, default_diameter, default_diameter);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("source = " + me.getSource());
    }

    @Override
    public void mousePressed(MouseEvent me) {
        System.out.println("a");
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        System.out.println("b");
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        System.out.println("c");
    }

    @Override
    public void mouseExited(MouseEvent me) {
        System.out.println("d");
    }
}
