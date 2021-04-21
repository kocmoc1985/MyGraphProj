/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BARGRAPH;

import XYG_STATS.BarGraphListener;
import XYG_STATS.MyGraphXY_HG;
import XYG_STATS.MyPoint_HG;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_BG extends MyGraphXY_HG {

    @Override
    public void popUpShow() {
         popup.show(this, MARKER_POINT.x, MARKER_POINT.y);
    }

    
    
    @Override
    public void addPointInfoBasic() {
        //
        MyPoint_BG point = (MyPoint_BG) MARKER_POINT;
        //
        if (point.getBarName() != null) {
            point.addPointInfo("Månad", point.getBarName());
        }
        //
    }

    @Override
    public void callEventWatchersHover(MouseEvent e) {
        for (BarGraphListener bgl : bg_listener_list) {
            bgl.barGraphHoverEvent(e, MARKER_POINT);
        }
    }
}
