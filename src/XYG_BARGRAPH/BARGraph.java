/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BARGRAPH;

import XYG_BASIC.PointHighLighter;
import XYG_STATS.MyGraphXY_PG;
import XYG_STATS.HistogramGraph;
import XYG_STATS.MySerie_M;
import java.awt.Color;
import java.util.ArrayList;
import other.StringDouble;

/**
 *
 * @author KOCMOC
 */
public class BARGraph extends HistogramGraph {

    private final ArrayList<Double> values_list = new ArrayList<>();
    private ArrayList<StringDouble> values_list_b = new ArrayList<>();

    public BARGraph(String title, MyGraphXY_PG mgxyh, int displayMode) {
        super(title, mgxyh, displayMode);
    }

    
    public void addData(ArrayList<StringDouble> values) {
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        this.values_list_b = values;
        //
        for (int i = 0; i < values_list_b.size(); i++) {
            //
            StringDouble value = values_list_b.get(i);
            //
            MyPoint_BG p = new MyPoint_BG(value.getParam_2(), value.getParam_2(), value.getParam_1());
            //
             this.addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
            //
            diffMarkerPointsDeleteFlag = false;
            //
        }
        //
        //
    }

    public void addData(double[] values) {
        //
        for (Double val : values) {
            values_list.add(val);
        }
        //
        addPoints();
        //
    }

    @Override
    public void addPoints() {
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        for (int i = 0; i < values_list.size(); i++) {
            //
            double value = values_list.get(i);
            //
            MyPoint_BG p = new MyPoint_BG(value, value);
//            p.setDisplayValueX(i);
            //
            this.addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
            //
            diffMarkerPointsDeleteFlag = false;
            //
        }
        //
    }


    @Override
    public void initializeB() {
        serie = new MySerie_M(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);

        serie.setDrawLines(false);
        serie.setLineThickness(1);
        serie.setLineDotted();
        serie.setCurveColor(Color.red);
        serie.setOverallScale(true);
        //
        this.addSerie(serie, false, this);
        //
        PointHighLighter.addSerieSingle(serie);
    }

}
