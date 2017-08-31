/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.MyPoint;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class HistograMM extends HistograM implements DiffMarkerAction {

    private ResultSet resultSet;
    private String valueColName;
    private String round;

    public HistograMM(String title, MyGraphXY_H xY_H, int displayMode) {
        super(title, xY_H, displayMode);
    }

    @Override
    public void markersSet(MyPoint markerA, MyPoint markerB) {
        rebuildData(resultSet, valueColName, round, markerA.getPointIndex(), markerB.getPointIndex());
    }

    @Override
    public void addData(ResultSet rs, String valueColName, String round) {
        this.resultSet = rs;
        this.valueColName = valueColName;
        this.round = round;
        //
        reset();
        refresh();
        //
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                buildHistogramDataSet(val, histoMap, round);
            }

            addPoints();

        } catch (SQLException ex) {
            Logger.getLogger(HistograMM.class.getName()).log(Level.SEVERE, null, ex);
            addPoints();
        }
    }

    private void rebuildData(ResultSet rs, String valueColName, String round, int start, int end) {
        //
        getSerie().deleteAllPoints();
        histoMap.clear();
        //
        int x = 0;
        try {
            //
            rs.first();
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
            Logger.getLogger(HistograMM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}