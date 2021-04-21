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
    public void RESET_MARKER_POINT__IMPORTANT() {
        MARKER_POINT = null; // If not activated does not remove the "name" after "mouse-out" from a point/component
    }

    
    @Override
    public void popUpShow() {
        popup.show(this, MARKER_POINT.x, MARKER_POINT.y);
    }

    @Override
    public void drawMarkerWhenPointing_b(Graphics2D g2) {
        //
        if (DRAW_MARKER_INFO == 1) {
            //
            MyPoint_BG point = (MyPoint_BG) MARKER_POINT;
            //
            if (point.getBarName() != null) {
                g2.drawString(point.getBarName(), MARKER_X - 40, MARKER_Y - 25);
//                g2.drawString("" + point.y_Real + ":-", MARKER_X - 50, MARKER_Y - 10);
            }
            //
        }
        //
    }

    @Override
    public void addPointInfoBasic() {
        //
        MyPoint_BG point = (MyPoint_BG) MARKER_POINT;
        //
        if (point.getBarName() != null) {
            point.addPointInfo("Total", "" + (int) point.y_Display + ":-");
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
