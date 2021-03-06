/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 *
 * @author Administrator
 */
public class MyXYGA {

    private final MyGraphContainer my_graph_container;
    public final MyGraphXY myGraphXY;
    private final String TITLE;

    public MyXYGA(String title, MyGraphXY xY) {
        this.TITLE = title;
        my_graph_container = new MyGraphContainer(title);
        myGraphXY = xY;
        my_graph_container.addGraph(myGraphXY);
    }

    public MyXYGA(String title, MyGraphXY xY, int displayMode) {
        this.TITLE = title;
        my_graph_container = new MyGraphContainer(title, displayMode);
        myGraphXY = xY;
        my_graph_container.addGraph(myGraphXY);
    }

    public String getTitle() {
        return this.TITLE;
    }

    /**
     * This method is used when the graph component is to be added to some other
     * component. This is not only the graph but also other gui components which
     * are attached to it
     *
     * @return
     */
    public JComponent getGraph() {
        //
        JComponent comp = my_graph_container.getGraph();
        comp.setSize(200, 200);//[#WAIT-FOR-HEIGHT#]
        //
        return comp;
    }

    /**
     * Use this method to get the instance of the graph & adjust it's visual
     * properties & others. This returns only the graph component without any
     * other gui components
     *
     * @return
     */
    public MyGraphXY getGraph2() {
        return this.myGraphXY;
    }

    public void addSerie(MySerie serie,boolean createDiffMarkers,Object caller) {
//        System.out.println("graphXY: " + myGraphXY + " / caller: " + caller + " / " + serie.getName());
        myGraphXY.addSerie(serie,createDiffMarkers,caller);
        my_graph_container.addLegend(new LegendElement(serie.getName(), serie.getCurveColor()).getLegendElement());
    }

    /**
     * @deprecated 
     * @param y_value
     * @param serieName 
     */
    public void addPointBySerie(Object y_value, String serieName) {
        myGraphXY.addPointToSerie(y_value, serieName);
    }
    
    public void addPointBySerie(Object y_value, MySerie serie){
        myGraphXY.addPointToSerie(y_value, serie);
    }

    public void addDataSetBySerie(double[] y_values, String serieName) {
        myGraphXY.addDataSetToSerie(y_values, serieName);
    }

    public void setScaleXYaxisLength(double dbl) {
        this.myGraphXY.setScaleXYaxisLength(dbl);
    }

    /**
     * Good to leave as an example
     * @deprecated 
     * @param min
     * @param max 
     */
    public void setLimits(double min, double max) {
        this.myGraphXY.setLimits(min, max);
    }
    
    public void setDrawGrid(boolean b) {
        this.myGraphXY.setDrawGrid(b);
    }

    public void setGridColor(Color c) {
        this.myGraphXY.setGridColor(c);
    }    
  
    public void setShowPopUpLeftClick(boolean b){
        this.myGraphXY.setShowPopUpLeftClick(b);
    }
    
    public void setPointHighLighterEnabled(boolean b){
        this.myGraphXY.setPointHighLighterEnabled(b);
    }

    public void setMarkerDotted(boolean b) {
        this.myGraphXY.setMarkerDotted(b);
    }

    public void setDrawMarker(boolean b) {
        this.myGraphXY.setDrawMarker(b);
    }

    public void setMarkerAutoReset(boolean b) {
        this.myGraphXY.setMarkerAutoReset(b);
    }

    public void setMarkerInfo(int mode) {
        this.myGraphXY.setMarkerInfo(mode);
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
        this.myGraphXY.setBackgroundColor(c);
    }

    public void setAxisScaling(boolean scale_x, boolean scale_y) {
        this.myGraphXY.setScaleXorYAxis(scale_x, scale_y);
    }

    public void deleteAllPointsFromSerie(MySerie serie) {
        this.myGraphXY.deleteAllPointsFromSerie(serie);
    }

    public void deleteAllPointsFromAllSeries() {
        myGraphXY.deleteAllPointsFromAllSeries();
    }

    public void setDisableScalingWhenGrid() {
        myGraphXY.setDisableScalingWhenGrid();
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyXYGA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void main(String[] args) {
//        //
////        HelpA.err_output_to_file();
//        //
//        MyXYGA xyg = new MyXYGA("Speed",new MyGraphXY(), MyGraphContainer.DISPLAY_MODE_FOOT_HEAD_ENABLED);
//        //
////        xyg.setHeadColor(Color.yellow);
////        xyg.setFootColor(Color.yellow);
//        xyg.setTitleSize(20, true);
//        xyg.setTitleColor(Color.black);
////        xyg.setBorderHeadAndFootComponents(BorderFactory.createLineBorder(Color.darkGray));
//        xyg.setHeadHeight(0.1);
//        //
//        // setAxisScaling(...) & setDrawGrid(...) influence each other!
//        xyg.setAxisScaling(true, true);
//        xyg.setDrawGrid(true);
////        xyg.setDisableScalingWhenGrid();
//        xyg.setGridColor(Color.black);
//        xyg.setScaleXYaxisLength(1.2);
//        //
////        xyg.setBackgroundColorOfGraph(new Color(249, 249, 249));
////        xyg.setDrawMarker(true);
//        xyg.setMarkerDotted(true);
//        xyg.setMarkerInfo(4);
//        xyg.setMarkerAutoReset(true);
//        //
//        //
//        JFrame jf = new JFrame("test");
//        jf.setSize(new Dimension(800, 800));
//        jf.setLayout(new GridLayout(1, 0));
//        jf.add(xyg.getGraph());
//        jf.setVisible(true);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        //
//        //=======================================
//        //
//        MySerie speed_serie = new MySerie("speed");
//        //
//        speed_serie.setPointThickness(1);
//        speed_serie.setCurveColor(Color.red);
//        speed_serie.setOverallScale(true);
//        //
//        xyg.addSerie(speed_serie);
//        //
//        //
//        double[] dataSet = {1201.2, 1159.5, 1325, 1588, 1100, 1265, 1333};
//        xyg.addDataSetBySerie(dataSet, "speed");
//        //
////        xyg.deleteAllPointsFromSerie("speed");
//        //
//        MySerie torq_serie = new MySerie("torq");
//        //
//        torq_serie.setCurveColor(Color.BLUE);
//        torq_serie.setDrawLines(false);
//        torq_serie.setOverallScale(true);
//        xyg.addSerie(torq_serie);
//        //
//        xyg.addPointBySerie(new MyPoint(256, 256.0, Color.green), "torq");
//        xyg.sleep(100);
//        xyg.addPointBySerie(new MyPoint(389, 389.0, Color.MAGENTA), "torq");
//        xyg.sleep(100);
//        xyg.addPointBySerie(456, "torq");
//        xyg.sleep(100);
//        xyg.addPointBySerie(157, "torq");
//        xyg.sleep(100);
//        xyg.addPointBySerie(125, "torq");
//        //
//        //
//        PointHighLighter.addSerie(speed_serie);
//        PointHighLighter.addSerie(torq_serie);
//        //
////        addMouseListenerToAllComponentsOfComponent(xyg.getGraph());
//    }
}
