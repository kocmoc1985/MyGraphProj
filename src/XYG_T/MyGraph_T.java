/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_T;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author KOCMOC
 */
public class MyGraph_T extends JPanel {

    public static double x__max;
    public static double y__max;
    public static double one_unit_x;
    public static double one_unit_y;
    private ArrayList<MySerie_T> series = new ArrayList<MySerie_T>();

    public MyGraph_T() {
        super();
    }

    public JComponent getGraph() {
        return this;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.

        if (getWidth() == 0) {
            return;
        }

        drawGrid(grphcs);

//        drawPoint_2_scaling(grphcs, 8000, 6000, 10);

        drawPoints(grphcs);

    }

    private void drawGrid(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.LIGHT_GRAY);
//        g2.scale(2,2);

        //Draw vertical grid
        for (int i = 10; i < getWidth(); i += 10) {
            g2.drawRect(i, 0, 1, getHeight());
        }

        //Draw horizontal grid
        for (int i = 10; i < getHeight(); i += 10) {
            g2.drawRect(0, i, getWidth(), 1);
        }

    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param diameter
     */
    private void drawPoint_2_scaling(Graphics g, double x, double y, int diameter) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);

        //Defining x & y max
        double x_max = getWidth();
        double y_max = getHeight();

        //Proportionally increase x & y max
        while (x > x_max || y > y_max) {
            x_max *= 1.2;
            y_max *= 1.2;
        }

        //number of "real" pixels per "unreal" pixel
        double one_unit_x_ = getWidth() / x_max;
        double one_unit_y_ = getHeight() / y_max;

        //Calculating "real" values to be able to draw on the cardinate table
        int x_real = (int) (one_unit_x_ * x);
        int y_real = (int) (one_unit_y_ * y);

        //Calculating the cordinates of oval
        int x_draw = (int) (x_real - (diameter / 2));
        int y_draw = (int) (y_real - (diameter / 2));

        g2.drawOval(x_draw, y_draw, diameter, diameter);
    }

    public void drawPoints(Graphics g) {
        for (MySerie_T mySerie_T : series) {
            mySerie_T.draw(g);
        }
    }

    public void addPointToSerie(MyPoint_T point_T, String serieName) {
        for (MySerie_T mySerie_T : series) {
            if (mySerie_T.getName().equals(serieName)) {
                mySerie_T.addPoint(point_T);
            }
        }
    }

    public void addSerie(MySerie_T mySerie_T) {
        series.add(mySerie_T);
    }

    public void testDraw() {
        JFrame frame = new JFrame("MyGraph_T");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(308, 240);
        frame.setLayout(new GridLayout(0, 1));
        frame.add(getGraph());
        frame.setVisible(true);

        series.add(new MySerie_T("speed"));
        addPointToSerie(new MyPoint_T(653, 362, this), "speed");
        addPointToSerie(new MyPoint_T(320, 156, this), "speed");
    }

    private void wait_(int millis) {
        synchronized (this) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyGraph_T.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        MyGraph_T graph_T = new MyGraph_T();
        graph_T.testDraw();
    }
}
