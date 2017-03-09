/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.Border;

/**
 *
 * @author Administrator
 */
public class MyCompleteXYG {

    private MyGraphContainer my_graph_container;
    private MyGraphXY my_xy_graph;

    public MyCompleteXYG(String title) {
        my_graph_container = new MyGraphContainer(title);
        my_xy_graph = new MyGraphXY();
        my_graph_container.addGraph(my_xy_graph);
    }

    public MyCompleteXYG(String title, int displayMode) {
        my_graph_container = new MyGraphContainer(title, displayMode);
        my_xy_graph = new MyGraphXY();
        my_graph_container.addGraph(my_xy_graph);
    }

    /**
     * This method is used when the graph component is to be added to some other
     * component. This is not only the graph but also other gui components which
     * are attached to it
     *
     * @return
     */
    public JComponent getGraph() {
        return my_graph_container.getGraph();
    }

    /**
     * Use this method to get the instance of the graph & adjust it's visual
     * properties & others. This returns only the graph component without any
     * other gui components
     *
     * @return
     */
    public MyGraphXY getGraph2() {
        return this.my_xy_graph;
    }

    public void addSerie(MySerie serie) {
        my_xy_graph.addSerie(serie);
        my_graph_container.addLegend(new LegendElement(serie.getName(), serie.getCurveColor()).getLegendElement());
    }

    public void addBySerie(Object y_value, String serieName) {
        my_xy_graph.addPointToSerie(y_value, serieName);
    }
   

    public void setScaleXYaxisLength(double dbl) {
        this.my_xy_graph.setScaleXYaxisLength(dbl);
    }

    public void setDrawGrid(boolean b) {
        this.my_xy_graph.setDrawGrid(b);
    }

    public void setGridColor(Color c) {
        this.my_xy_graph.setGridColor(c);
    }

    public void setMarkerDotted(boolean b) {
        this.my_xy_graph.setMarkerDotted(b);
    }

    public void setDrawMarker(boolean b) {
        this.my_xy_graph.setDrawMarker(b);
    }

    public void setMarkerAutoReset(boolean b) {
        this.my_xy_graph.setMarkerAutoReset(b);
    }

    public void setMarkerInfo(int mode) {
        this.my_xy_graph.setMarkerInfo(mode);
    }

    /**
     * Sets the color of the title component
     *
     * @param color
     */
    public void setHeadColor(Color color) {
        my_graph_container.setHeadColor(color);
    }

    /**
     * Sets the color of the foot component
     *
     * @param color
     */
    public void setFootColor(Color color) {
        my_graph_container.setFootColor(color);
    }

    public void setTitleSize(int size, boolean bold) {
        this.my_graph_container.setTitleSize(size, bold);
    }

    public void setTitleColor(Color c) {
        this.my_graph_container.setTitleColor(c);
    }

    public void setBorder(Border b) {
        this.my_graph_container.setBorder(b);
    }

    public void setHeadHeight(double percent) {
        this.my_graph_container.setHeadHeight(percent);
    }

    public void setBorderHeadAndFootComponents(Border b) {
        this.my_graph_container.setBorderHeadAndFootComponents(b);
    }

    public void setBackgroundColorOfGraph(Color c) {
        this.my_xy_graph.setBackgroundColor(c);
    }

    public void setAxisScaling(boolean scale_x, boolean scale_y) {
        this.my_xy_graph.setScaleXorYAxis(scale_x, scale_y);
    }

    public void deleteAllPointsFromSerie(String serieName) {
        this.my_xy_graph.deleteAllPointsFromSerie(serieName);
    }

    public void deleteAllPointsFromAllSeries() {
        my_xy_graph.deleteAllPointsFromAllSeries();
    }

    public void setDisableScalingWhenGrid() {
        my_xy_graph.setDisableScalingWhenGrid();
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyCompleteXYG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        //
//        HelpA.err_output_to_file();
        //
        MyCompleteXYG xyg = new MyCompleteXYG("Speed", MyGraphContainer.DISPLAY_MODE_FOOT_HEAD_ENABLED);
        //
//        xyg.setHeadColor(Color.yellow);
//        xyg.setFootColor(Color.yellow);
        xyg.setTitleSize(20, true);
        xyg.setTitleColor(Color.black);
//        xyg.setBorderHeadAndFootComponents(BorderFactory.createLineBorder(Color.darkGray));
        xyg.setHeadHeight(0.1);
        //
        // setAxisScaling(...) & setDrawGrid(...) influence each other!
        xyg.setAxisScaling(true, true);
        xyg.setDrawGrid(true);
//        xyg.setDisableScalingWhenGrid();
        xyg.setGridColor(Color.black);
        xyg.setScaleXYaxisLength(1.2);
        //
//        xyg.setBackgroundColorOfGraph(new Color(249, 249, 249));
        xyg.setDrawMarker(false);
        xyg.setMarkerDotted(true);
        xyg.setMarkerInfo(4);
        xyg.setMarkerAutoReset(true);
        //
        //
        JFrame jf = new JFrame("test");
        jf.setSize(new Dimension(800, 800));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(xyg.getGraph());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        //=======================================
        //
        MySerie speed_serie = new MySerie("speed");
        //
        speed_serie.setPointThickness(1);
        speed_serie.setCurveColor(Color.red);
        speed_serie.setOverallScale(true);
        //
        xyg.addSerie(speed_serie);
        //
        //
        xyg.addBySerie(1201.2, "speed");
        xyg.sleep(100);
        xyg.addBySerie(1159, "speed");
        xyg.sleep(100);
        xyg.addBySerie(1325, "speed");
        xyg.sleep(100);
        xyg.addBySerie(1588, "speed");
        xyg.sleep(100);
        xyg.addBySerie(1100, "speed");
        xyg.sleep(100);
        xyg.addBySerie(1265, "speed");
        xyg.sleep(100);
        xyg.addBySerie(1333, "speed");
        //
//        xyg.deleteAllPointsFromSerie("speed");
        //
        MySerie torq_serie = new MySerie("torq");
        //
        torq_serie.setCurveColor(Color.BLUE);
        torq_serie.setDrawLines(false);
        torq_serie.setOverallScale(true);
        xyg.addSerie(torq_serie);
        //
        xyg.addBySerie(256, "torq");
        xyg.sleep(100);
        xyg.addBySerie(389, "torq");
        xyg.sleep(100);
        xyg.addBySerie(456, "torq");
        xyg.sleep(100);
        xyg.addBySerie(157, "torq");
        xyg.sleep(100);
        xyg.addBySerie(125, "torq");
        //
        //
        PointHighLighter.addSerie(speed_serie);
        PointHighLighter.addSerie(torq_serie);
        //
//        addMouseListenerToAllComponentsOfComponent(xyg.getGraph());
    }
    private static Border PREV_BORDER;

    public static void addMouseListenerToAllComponentsOfComponent(JComponent c) {
        Component[] c_arr = c.getComponents();
        for (Component component : c_arr) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    String str = "SOURCE ELEM: " + me.getSource();
                    System.out.println(str);
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    if (me.getSource() instanceof JComponent) {
                        JComponent jc = (JComponent) me.getSource();
                        PREV_BORDER = jc.getBorder();
                        jc.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                    }
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    if (me.getSource() instanceof JComponent) {
                        JComponent jc = (JComponent) me.getSource();
                        jc.setBorder(PREV_BORDER);
                    }
                }
            });
            if (component instanceof JComponent) {
                addMouseListenerToAllComponentsOfComponent((JComponent) component);
            }
        }
    }
}
