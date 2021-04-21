/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BARGRAPH;

import XYG_STATS.BarGraphListener;
import XYG_STATS.MyGraphXY_HG;
import XYG_STATS.MyPoint_HG;
import java.awt.event.MouseEvent;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_BG extends MyGraphXY_HG {


    @Override
    public void addPointInfoBasic() {
        // MARKER_POINT.addPointInfo("y", "" + (MARKER_POINT.y_Display));
    }
    
    
    @Override
    public void callEventWatchersHover(MouseEvent e) {
        for (BarGraphListener bgl : bg_listener_list) {
            bgl.barGraphHoverEvent(e, MARKER_POINT);
        }
    }
}
