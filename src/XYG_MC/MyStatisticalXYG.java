/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_MC;

import XYG_BASIC.MyCompleteXYG;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MySerie;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author KOCMOC
 */
public class MyStatisticalXYG extends MyCompleteXYG {

    private MySerie serie;

    public MyStatisticalXYG(String title) {
        super(title);
        initializeA();
        initializeB();
    }

    public MyStatisticalXYG(String title, int displayMode) {
        super(title, displayMode);
        initializeA();
        initializeB();
    }

    private void initializeB() {
        serie = new MySerie(getTitle());
        //
        serie.setPointThickness(1);
        serie.setCurveColor(Color.red);
        serie.setOverallScale(true);
        //
        this.addSerie(serie);
        //
        PointHighLighter.addSerie(serie);
    }

    private void initializeA() {
        this.setTitleSize(20, true);
        this.setTitleSize(20, true);
        this.setTitleColor(Color.black);
//        this.setBorderHeadAndFootComponents(BorderFactory.createLineBorder(Color.darkGray));
        this.setHeadHeight(0.1);
        //
        // setAxisScaling(...) & setDrawGrid(...) influence each other!
        this.setAxisScaling(true, true);
        this.setDrawGrid(true);
//        this.setDisableScalingWhenGrid();
        this.setGridColor(Color.black);
        this.setScaleXYaxisLength(1.2);
        //
//        this.setBackgroundColorOfGraph(new Color(249, 249, 249));
        this.setDrawMarker(false);
        this.setMarkerDotted(true);
        this.setMarkerInfo(4);
        this.setMarkerAutoReset(true);
    }

    public void addData(ResultSet rs) {
        
    }

    public static void main(String[] args) {
        MyStatisticalXYG msxyg = new MyStatisticalXYG("speed", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);

        JFrame jf = new JFrame(msxyg.getTitle());
        jf.setSize(new Dimension(800, 800));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(msxyg.getGraph());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        double[] dataSet = {1201.2, 1159.5, 1325, 1588, 1100, 1265, 1333,2200,2300,2159,2789,1565,1898};
        msxyg.addDataSetBySerie(dataSet, "speed");
        //
    }
}
