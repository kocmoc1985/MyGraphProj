package XYG_STATS;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import XYG_BASIC.MyPoint;
import XYG_STATS.MyPoint_HG;
import java.awt.event.MouseEvent;

/**
 *
 * @author mcab
 */
public interface BarGraphListener {

    public void barGraphHoverEvent(MouseEvent e, MyPoint point);

    public void barGraphHoverOutEvent(MouseEvent e);

}
