/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_BASIC.MyXYGB;
import XYG_BASIC.PointDeletedAction;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.HelpA;
import sql.SQL_Q;
import sql.Sql_B;

/**
 * The main graph for displaying one of following: "ML","MH","t10","t90"
 * @author KOCMOC
 */
public class XyGraph_M extends MyXYGB implements PointDeletedAction {

    private MySerie serieLimitL;
    private MySerie serieLimitU;
    private BasicGraphListener gistoGraph;

    public XyGraph_M(String title, int displayMode) {
        super(title, new MyGraphXY_M(), displayMode);
        init();
    }

    public void setGistoGraph(BasicGraphListener gg) {
        this.gistoGraph = gg;
        // THIS triggers event which is processed in gg class
        this.addDiffMarkersSetListener(gg);
    }

    @Override
    public void pointDeleted(MyPoint mp) {
        gistoGraph.rebuildData(serie.getPoints(), gistoGraph.getRound());
        this.rebuildData();
    }

    private void init() {
        serie.addPointDeletedActionListener(this);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_STD_DEV, Main.jTextFieldStdDev);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_AVERAGE, Main.jTextFieldAverage);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_MEDIAN, Main.jTextFieldMedian);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_CP, Main.jTextFieldCP);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_CPU, Main.jTextFieldCPU);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_CPL, Main.jTextFieldCPL);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_CPK, Main.jTextFieldCPK);
//        addDiffMarkerOutPutComponent(DiffMarkerPoints_RS.CALC_SKEW, Main.jTextFieldSkew);
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
//        this.setBackgroundColorOfGraph(Color.BLACK);

        this.setDrawMarker(true);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
    }

    @Override
    public void initializeB() {
        //
        serie = new MySerie_M(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);

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
        serieLimitL = new MySerie("LSL", Color.red);
        serieLimitU = new MySerie("USL", Color.red);
        this.addSerie(serieLimitL, false, this);
        this.addSerie(serieLimitU, false, this);
        adjustLimitSeries();
    }

    private void adjustLimitSeries() {
        PointHighLighter.addSerie(serieLimitL);
        PointHighLighter.addSerie(serieLimitU);
        //
        serieLimitL.setLineThickness(1);
        serieLimitL.setPointThickness(0.5);
        serieLimitL.setPointColor(Color.red);
        //
        serieLimitU.setLineThickness(1);
        serieLimitU.setPointThickness(0.5);
        serieLimitU.setPointColor(Color.red);
        //
//        serieLimitU.setDrawPoints(false);
//        serieLimitU.setLineThickness(1);
    }

    public void addData(Sql_B sql, String q, String valueColName) {
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        try {
            //
            ResultSet rs = sql.execute(q);
            //
            rs.beforeFirst();
            //
            double filterCoeff = HelpA.calc_Filter_Coeff(rs, valueColName);
            //
            rs.beforeFirst();
            //
            int filtered = 0;
            //
            while (rs.next()) {
                //
                double val = processValue(rs.getString(valueColName));
                //
                double lsl = rs.getDouble(SQL_Q.LSL);
                MyPoint LSL = new MyPoint((int) lsl, lsl);
                //
                double usl = rs.getDouble(SQL_Q.USL);
                MyPoint USL = new MyPoint((int) usl, usl);
                //
                addPointBySerie(LSL, serieLimitL);
                addPointBySerie(USL, serieLimitU);
                //
//                    setLimits(minLim, maxLim);
                //
                MyPoint_M p;
                //
//                if (val > (average * 3)) {
//                    p = new MyPoint(((int) (average)), (average));
//                    p.setPointColor(Color.red);
//                    p.setPointDimenssion(16);
//                } else {
                p = new MyPoint_M(val, val, LSL, USL);
//                }
                //
                p.addPointInfo("Serie", rs.getString(SQL_Q.TEST_NAME));
                //
                //<#GFT-SPECIAL-DEMO>
                p.addPointInfo("Quality", rs.getString(SQL_Q.QUALITY));
                p.addPointInfo("Order", rs.getString(SQL_Q.ORDER));
                //</#GFT-SPECIAL-DEMO>
                //
                p.addPointInfo("Batch", rs.getString(SQL_Q.BATCH));
                p.addPointInfo("Status", rs.getString(SQL_Q.TEST_STATUS));
                //
                if (Math.abs(val) < (filterCoeff)) {
                    addPointWithDiffMarkerPointsDelete(p, diffMarkerPointsDeleteFlag);
                    diffMarkerPointsDeleteFlag = false;
                } else {
                    filtered++;
                }
                //
            }
            //
            System.out.println("Filtered batches: " + filtered);
            //
        } catch (SQLException ex) {
            Logger.getLogger(XyGraph_M.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void rebuildData() {
        //
        ArrayList<MyPoint_M> newList = new ArrayList<>();
        //
        for (MyPoint p : serie.getPoints()) {
            MyPoint_M pp = (MyPoint_M) p;
            MyPoint_M mph = new MyPoint_M(pp.y_Real, pp.y_Scaled, pp.getLSL(), pp.getUSL());

            newList.add(mph);
        }
        //
        deleteAllPointsFromAllSeries();
        //
        boolean diffMarkerPointsDeleteFlag = true;
        //
        for (MyPoint p : newList) {
            MyPoint_M phm = (MyPoint_M) p;
            addPointBySerie(phm.getLSL(), serieLimitL);
            addPointBySerie(phm.getUSL(), serieLimitU);
            addPointWithDiffMarkerPointsDelete(phm, diffMarkerPointsDeleteFlag);
            diffMarkerPointsDeleteFlag = false;
        }
    }

    private double processValue(String value) {
        try {
            double x = Double.parseDouble(value);
            return x;
        } catch (Exception ex) {
            return 0;
        }
    }

    private double parseDouble(String str) {
        if (str == null || str.equals("null")) {
            return 0;
        }

        try {
            double x = Double.parseDouble(str);
            return x;
        } catch (Exception ex) {
            return 0;
        }
    }
}
