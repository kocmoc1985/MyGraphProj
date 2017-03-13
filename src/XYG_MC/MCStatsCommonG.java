/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_MC;

import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyXYGB;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author mcab
 */
public class MCStatsCommonG extends MyXYGB {
    
    public MCStatsCommonG(String title) {
        super(title);
    }
    
    public MCStatsCommonG(String title, int displayMode) {
        super(title, displayMode);
    }
    
    public void addData(ResultSet rs, String valueColName, String modeColName) {
        try {
            while (rs.next()) {
                double val = rs.getDouble(valueColName);
                String mode = rs.getString(modeColName);
                Point p = new Point((int) val, "" + val);
                
                if (mode.equals("1")) {
                    p.setPointColor(Color.red);
                } else {
                    p.setPointColor(Color.blue);
                }
                
                p.addPointInfo("mode", mode);
                
                addPoint(p);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyXYGB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        //
        MyXYGB msxyg = new MyXYGB("torq", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        JFrame jf = new JFrame(msxyg.getTitle());
        jf.setSize(new Dimension(800, 800));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(msxyg.getGraph());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        double[] dataSet = {1201.2, 1159.5, 1325, 1588, 1100, 1265, 1333, 2200, 2300, 2159, 2789, 1565, 1898, 358, 965, 879, 253, 96, 1547, 1625, 1200};
        msxyg.addDataSetBySerie(dataSet, "torq");
        //
//        HelpA.addMouseListenerToAllComponentsOfComponent(msxyg.getGraph());
    }
    
}
