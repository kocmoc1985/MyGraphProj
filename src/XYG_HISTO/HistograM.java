/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.HelpA;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_BASIC.MyXYGB;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author KOCMOC
 */
public class HistograM extends MyXYGB {

    public TreeMap<Double, Integer> histoMap = new TreeMap();
    public ArrayList<Double> xValuesList;

    public HistograM(String title, MyGraphXY_H h, int displayMode) {
        super(title, h, displayMode);
    }
    
    public void reset() {
        deleteAllPointsFromAllSeries();
        histoMap = new TreeMap<Double,Integer>();
    }
    
    public void refresh(){
        getGraph().repaint();
    }
    
   public void setStepIdentifierX(int x){
       MyGraphXY_H h = (MyGraphXY_H)my_xy_graph;
       h.setStepIdentifierX(x);
   }
    
    public void addData(ResultSet rs, String valueColName,String round) {
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                buildHistogramDataSet(val, histoMap,round);
            }

            addPoints();

        } catch (SQLException ex) {
            Logger.getLogger(HistograM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addData(double[] values,String round) {
        for (double val : values) {
            buildHistogramDataSet(val, histoMap,round);
        }
        
        addPoints();
    }
    
     public void addData(int[] values,String round) {
         for (int val : values) {
              buildHistogramDataSet(val, histoMap,round);
         }
         addPoints();
     }

    public void addPoints() {
        Set set = histoMap.keySet();
        Iterator it = set.iterator();
        xValuesList = new ArrayList<Double>();
        my_xy_graph.X_MAX = 0;
        while (it.hasNext()) {
            double key = (Double) it.next();
            int value = histoMap.get(key);
            xValuesList.add(key);
            MyPoint p = new MyPoint(value, value);
            p.setDisplayValueX(key);
            this.addPoint(p);
        }
        //
        MyGraphXY_H h = (MyGraphXY_H)my_xy_graph;
        h.setXValues(xValuesList);
        //
    }

    public void buildHistogramDataSet(double key, TreeMap map,String round) {
        if(round != null){
            key = HelpA.roundDouble(key, round);
        }
        if (map.containsKey(key)) {
            int val = (Integer) map.get(key);
            val++;
            updateMap(key, val, map);
        } else {
            map.put(key, 1);
        }
    }

    private void updateMap(Object key, Object value, TreeMap map) {
        map.remove(key);
        map.put(key, value);
    }

    @Override
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
        this.setShowPopUpLeftClick(true);
        this.setPointHighLighterEnabled(true);
        this.setDisableScalingWhenGrid();
        this.setGridColor(Color.black);
//        this.setScaleXYaxisLength(1.9);
        //
//        this.setBackgroundColorOfGraph(Color.BLACK);
        this.setDrawMarker(true);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }

    @Override
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
    

    public static void main(String[] args) {
        //
        HistograM hm = new HistograM("speed", new MyGraphXY_H(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        JFrame jf = new JFrame(hm.getTitle());
        jf.setSize(new Dimension(800, 800));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(hm.getGraph());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        double[] dataSet = {1.5,1.5,1.5,1.5,3.2,3.2,4.3,4.3,4.5,5.5,5.5,5.5,5.2,5.2,5.2,7.8,7.8,8.3,8.3,8.6,8.9,9.2,9.2,9.2};

//        msxyg.addDataSetBySerie(dataSet, "speed");

        hm.addData(dataSet,"#.#");

//        hm.addData(arr(200, 10, 15));

        //
//        HelpA.addMouseListenerToAllComponentsOfComponent(jf.getRootPane());
    }
    
     public static int[] arr(int n, double min, double max) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = (int) (Math.random() * max + min);
        }
        return array;
    }

   
}
