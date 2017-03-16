/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author KOCMOC
 */
public class MyXYGB extends MyXYGA {
    
    public MySerie serie;
    
    public MyXYGB(String title) {
        super(title);
        initialize();
    }
    
    public MyXYGB(String title, int displayMode) {
        super(title, displayMode);
        initialize();
    }
    
    public MySerie getSerie(){
        return this.serie;
    }
    
    public void addPoint(Object valueOrPoint){
        addPointBySerie(valueOrPoint, getTitle());
    }
    
    private void initialize(){
        initializeA();
        initializeB();
    }
    
    public void initializeB() {
        serie = new MySerie(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);
        
        serie.setDrawLines(true);
        serie.setLineThickness(1);
        serie.setLineDotted();
        serie.setCurveColor(Color.red);
        serie.setOverallScale(true);
        //
        this.addSerie(serie);
        //
        PointHighLighter.addSerie(serie);
    }
    
    public void initializeA() {
        this.setTitleSize(20, true);
        this.setTitleSize(20, true);
        this.setTitleColor(Color.black);
//        this.setBorderHeadAndFootComponents(BorderFactory.createLineBorder(Color.darkGray));
        this.setHeadHeight(0.1);
        //
        // setAxisScaling(...) & setDrawGrid(...) influence each other!
        this.setAxisScaling(true, true);
//        this.setDrawGrid(true);
        this.setDisableScalingWhenGrid();
        this.setGridColor(Color.black);
//        this.setScaleXYaxisLength(1.2);
        //
//        this.setBackgroundColorOfGraph(Color.BLACK);
        this.setDrawMarker(true);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }
    
    
    public static void main(String[] args) {
        //
        MyXYGB msxyg = new MyXYGB("speed", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        JFrame jf = new JFrame(msxyg.getTitle());
        jf.setSize(new Dimension(800, 800));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(msxyg.getGraph());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        double[] dataSet = {1201.2, 1159.5, 1325, 1588, 1100, 1265, 1333, 2200, 2300, 2159, 2789, 1565, 1898,358,965,879,253,96,1547,1625,1200};
        
//        double[] dataSet = {3.5,1.7,2.3,1.3,4.5,7.8,6.7,8.7};
//        
//        double[] dataSet = {0.7,1.3,3.2,0.7,4.8};
        
//        double[] dataSet = {0.35,0.24,0.78,0.47,0.52};
        
        msxyg.addDataSetBySerie(dataSet, "speed");
        //
//        HelpA.addMouseListenerToAllComponentsOfComponent(jf.getRootPane());
    }
}
