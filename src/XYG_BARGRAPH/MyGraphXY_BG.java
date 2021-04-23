/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BARGRAPH;

import XYG_BASIC.MyGraphXY;
import XYG_STATS.BarGraphListener;
import XYG_STATS.MyGraphXY_HG;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_BG extends MyGraphXY_HG {

    public MyGraphXY_BG() {
        init();
    }

    private void init(){
        MyGraphXY.ADD_POINT_INFO_BASIC = true;
    }
    
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
    
     @Override
    public double defineJJ__y_axis(double vvv) {
        //
        double jj;
        //
        if (vvv > 100000 && vvv < 1000000) {
            jj = 50000; // adjusted [2021-04-14]
        } else if (vvv > 10000 && vvv < 100000) {
            jj = 10000; // adjusted [2021-04-14]
        } else if (vvv > 1000 && vvv < 10000) {
            jj = 2000; // adjusted [2021-04-14]
        } else if (vvv > 100 && vvv < 1000) {
            jj = 200;
        } else if (vvv > 10 && vvv < 100) {
            jj = 5;
        } else if (vvv > 5 && vvv < 10) {
            jj = 1;
        } else if (vvv > 1 && vvv < 3) {
            jj = 0.5;
        } else if (vvv > 0 && vvv < 3) {
            jj = 0.1;
        } else {
            jj = 1;
        }
        //
        if (vvv < 1) {
            jj = 0.02;
        }
        //
        return jj;
        //
    }
    
}
