/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MyXYGB;
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

    private TreeMap<Double, Integer> histoMap = new TreeMap();
    private ArrayList<Double> xValuesList;
    private MyGraphXY_H mgxyh;

    public HistograM(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
        mgxyh = (MyGraphXY_H)getGraph2();
        
    }
    
    public void addData(ResultSet rs, String valueColName) {
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                buildHistogramDataSet(val, histoMap);
            }

            addPoints();

        } catch (SQLException ex) {
            Logger.getLogger(HistograM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addData(double[] values) {
        for (double val : values) {
            buildHistogramDataSet(val, histoMap);
        }
        
        addPoints();
    }
    
     public void addData(int[] values) {
         for (int val : values) {
              buildHistogramDataSet(val, histoMap);
         }
         addPoints();
     }

    private void addPoints() {
        Set set = histoMap.keySet();
        Iterator it = set.iterator();
        xValuesList = new ArrayList<Double>();
        while (it.hasNext()) {
            double key = (Double) it.next();
            int value = histoMap.get(key);
            xValuesList.add(key);
            System.out.println("key: " + key + " /  value: " + value);
            MyPoint p = new MyPoint(value, value);
            p.setDisplayValueX(key);
            this.addPoint(p);
        }
        mgxyh.setXValues(xValuesList);
    }

    public void buildHistogramDataSet(double key, TreeMap map) {
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


        double[] dataSet = {1.5,1.5,1.5,1.5,3.2,3.2,4.3,4.3,4.5};

//        msxyg.addDataSetBySerie(dataSet, "speed");

        hm.addData(dataSet);

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
