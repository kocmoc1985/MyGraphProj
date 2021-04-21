/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_STATS;

import XYG_BASIC.DiffMarkerAction;
import XYG_BASIC.MyPoint;
import java.util.List;
import javax.swing.JComponent;
import sql.Sql_B;

/**
 *
 * @author KOCMOC
 */
public interface BasicGraphListener extends DiffMarkerAction{
    
    public void addDiffMarkersSetListener(DiffMarkerAction dma);
    
    public void addData(Sql_B sql,String q, String valueColName);
    
    public void addData_B(double[]values);
    
    public String getRound();
    
    public void rebuildData(List<MyPoint> points,String round);
    
    public JComponent getGraph();
    
}
