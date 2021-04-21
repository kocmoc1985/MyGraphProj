/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_BASIC.MyXYGB;
import XYG_BASIC.PointDeletedAction;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class XyGraph_Basic extends MyXYGB implements PointDeletedAction{

    public XyGraph_Basic(String title,MyGraphXY xy,int displayMode) {
        super(title, xy, displayMode);
    }

    @Override
    public void initializeA() {
        //
        this.setTitleSize(20, true);
        this.setTitleSize(20, true);
        this.setTitleColor(Color.black);
//        this.setBorderHeadAndFootComponents(BorderFactory.createLineBorder(Color.darkGray));
        this.setHeadHeight(0.1);
        //
        // setAxisScaling(...) & setDrawGrid(...) influence each other!
        this.setAxisScaling(true, true);
//        this.setDrawGrid(true);
//        this.setDisableScalingWhenGrid();
        this.setGridColor(Color.black);
        this.setScaleXYaxisLength(1.2);
        //
//        this.setBackgroundColorOfGraph(Color.WHITE); // it is white by default
        //
        this.setDrawMarker(false);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }

    @Override
    public void initializeB() {
        //
        serie = new MySerie(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);
        //
        serie.setDrawLines(true);
        serie.setLineThickness(1);
        serie.setLineDotted();
        serie.setCurveColor(Color.BLUE);
        serie.setOverallScale(true);
        //
        this.addSerie(serie, true, this);
        //
        PointHighLighter.addSerie(serie);
        //
    }
    
    
    @Override
    public void pointDeleted(MyPoint point) {
        this.rebuildData();
    }
    
    public void rebuildData() {
        //
        ArrayList<MyPoint> newList = new ArrayList<MyPoint>();
        //
        for (MyPoint p : serie.getPoints()) {
            MyPoint pp = (MyPoint) p;
            MyPoint mph = new MyPoint(pp.y_Real, pp.y_Scaled);
            //
            newList.add(mph);
        }
        //
        deleteAllPointsFromAllSeries();
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        for (MyPoint p : newList) {
            MyPoint phm = (MyPoint) p;
            addPointWithDiffMarkerPointsDelete(phm, diffMarkerPointsDeleteFlag);
            diffMarkerPointsDeleteFlag = false;
        }
    }
    
    

}
