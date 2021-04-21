/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_STATS;

import XYG_BASIC.MyPoint;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * MyGraphXY_HG = MyGraphXY for the HISTOGRAM GRAPH
 * @author KOCMOC
 */
public class MyGraphXY_HG extends MyGraphXY_PG {

    public final ArrayList<BarGraphListener> bg_listener_list = new ArrayList<>();

    @Override
    public void addPointInfo() {
        MyPoint_HG m = (MyPoint_HG) MARKER_POINT;
        MARKER_POINT.addPointInfo("serie", m.getSerieName());
        MARKER_POINT.addPointInfo("y", "" + (m.y_Display));
        MARKER_POINT.addPointInfo("x", "" + m.getRangeStart() + " -> " + m.getRangeEnd());
    }

    @Override
    public void addAdditionalControlsPopups() {
         if (MARKER_POINT.isDiffMarkerPoint() == false) {
            popup.add(menu_item_diff_marker_add);
        } else if (MARKER_POINT.isDiffMarkerPoint()) {
            popup.add(menu_item_diff_marker_remove);
        }
        //
//        popup.add(menu_item_delete_point);
    }
    
    

    public void addBarGraphListener(BarGraphListener bgl) {
        bg_listener_list.add(bgl);
    }

    @Override
    public void scaleX(Graphics2D g2) {
       // Yes it shall overwrite
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e); //To change body of generated methods, choose Tools | Templates.
        //
        if (e.getSource() instanceof MyPoint) {
            callEventWatchersHover(e);
        } else {
            callEventWatchersHoverOut(e);
        }

    }

    private void callEventWatchersHoverOut(MouseEvent e) {
        for (BarGraphListener bgl : bg_listener_list) {
            bgl.barGraphHoverOutEvent(e);
        }
    }

    public void callEventWatchersHover(MouseEvent e) {
        for (BarGraphListener bgl : bg_listener_list) {
            bgl.barGraphHoverEvent(e, (MyPoint_HG) MARKER_POINT);
        }
    }
}
