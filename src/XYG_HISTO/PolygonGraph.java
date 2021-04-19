/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.HelpAA;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MyXYGB;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.HelpA;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public class PolygonGraph extends MyXYGB implements DiffMarkerAction, BasicGraphListener {

    public TreeMap<Double, Integer> histoMap = new TreeMap();
    public ArrayList<String> xValuesList;
    public String valueColName;
    public String round = "#.####";
    public Sql_B sql;
    public String query;

    public PolygonGraph(String title, MyGraphXY_PG mgxyh, int displayMode) {
        super(title, mgxyh, displayMode);
    }

    @Override
    public String getRound() {
        return this.round;
    }

    @Override
    public void addDiffMarkersSetListener(DiffMarkerAction dma) {
        super.addDiffMarkersSetListener(dma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void markersUnset(MyGraphXY trigerInstance) {
    }

    @Override
    public void markersSet(MyGraphXY trigerInstance, MyPoint markerA, MyPoint markerB) {
        if (trigerInstance instanceof MyGraphXY_PG == false) {
//            rebuildData(resultSet, valueColName, round, markerA.getPointIndex(), markerB.getPointIndex());
            Thread x = new Thread(new RebuildDataThread(markerA.getPointIndex(), markerB.getPointIndex()));
            x.start();
        }
    }

    class RebuildDataThread implements Runnable {

        private int markerAPointIndex;
        private int markerBPointIndex;

        public RebuildDataThread(int markerAPointIndex, int markerBPointIndex) {
            this.markerAPointIndex = markerAPointIndex;
            this.markerBPointIndex = markerBPointIndex;
        }

        @Override
        public void run() {
            rebuildData(valueColName, round, markerAPointIndex, markerBPointIndex);
        }

    }

    public void reset() {
        deleteAllPointsFromAllSeries();
        histoMap = new TreeMap<Double, Integer>();
    }

    public void refresh() {
        getGraph().repaint();
    }

    @Override
    public void addData(Sql_B sql, String q, String valueColName) {
        //
        this.valueColName = valueColName;
        //
        reset();
        refresh();
        //
        try {
            //
            this.sql = sql;
            this.query = q;
            //
            ResultSet rs = sql.execute(q);
            //
            rs.beforeFirst();
            //
            double filterCoeff = HelpA.calc_Filter_Coeff(rs, valueColName);
            //
            rs.beforeFirst();
            //
            while (rs.next()) {
                //
//                addLimits(rs);
                //
                double val = rs.getDouble(valueColName);
                //
                if (val < (filterCoeff)) {
                    buildHistogramDataSet(val, histoMap, round);
                }
            }
            //
            addPoints();
            //
        } catch (SQLException ex) {
            Logger.getLogger(PolygonGraph.class.getName()).log(Level.SEVERE, null, ex);
            addPoints();
        }
    }

    public void addData(double[] values, String round) {
        for (double val : values) {
            buildHistogramDataSet(val, histoMap, round);
        }

        addPoints();
    }

    public void addData(int[] values, String round) {
        for (int val : values) {
            buildHistogramDataSet(val, histoMap, round);
        }
        addPoints();
    }

    public synchronized void rebuildData(String valueColName, String round, int start, int end) {
        //
        deleteAllPointsFromSerie(serie);
        histoMap.clear();
        //
        int x = 0;
        //
        try {
            //
            ResultSet rs = sql.execute_2(query);
            //
            while (rs.next()) {
                if (x >= start && x <= end) {
                    double val = rs.getDouble(valueColName);
                    buildHistogramDataSet(val, histoMap, round);
                }
                x++;
            }
            //
            addPoints();
            //
        } catch (SQLException ex) {
            Logger.getLogger(PolygonGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void rebuildData(ArrayList<MyPoint> points, String round) {
        //
//        getSerie().deleteAllPoints();
        deleteAllPointsFromSerie(serie);
        histoMap.clear();
        //
        for (MyPoint myPoint : points) {
            buildHistogramDataSet(myPoint.y_Display, histoMap, round);
        }
        //
        addPoints();
    }

    public void addPoints() {
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        Set set = histoMap.keySet();
        Iterator it = set.iterator();
        xValuesList = new ArrayList<>();
        myGraphXY.X_MAX = 0;
        //
        while (it.hasNext()) {
            double key = (Double) it.next();
            int value = histoMap.get(key);
            xValuesList.add("" + key);
            MyPoint p = new MyPoint(value, value);
            p.setDisplayValueX(key);
            this.addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
//            this.addPointBySerie(p, serie);
            //
            diffMarkerPointsDeleteFlag = false;
            //
        }
        //
        MyGraphXY_PG h = (MyGraphXY_PG) myGraphXY;
        h.setXValues(xValuesList);
        //
    }

    public void buildHistogramDataSet(double key, TreeMap map, String round) {
        if (round != null) {
            key = HelpAA.roundDouble(key, round);
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

    /**
     * Good to keep as example though
     *
     * @deprecated
     * @param rs
     */
    public void addLimits(ResultSet rs) {
        try {
            double minLim = rs.getDouble("LSL");
            double maxLim = rs.getDouble("USL");
            setLimits(minLim, maxLim);
        } catch (SQLException ex) {
            Logger.getLogger(PolygonGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
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
//        this.setScaleXYaxisLength(1.2);
        //
//        this.setBackgroundColorOfGraph(Color.BLACK);
        this.setDrawMarker(false);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }

    @Override
    public void initializeB() {
        serie = new MySerie_M(getTitle());
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
        this.addSerie(serie, true, this);
        //
        PointHighLighter.addSerieSingle(serie);
    }
}
