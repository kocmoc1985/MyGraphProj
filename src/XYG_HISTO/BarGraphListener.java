package XYG_HISTO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.MouseEvent;

/**
 *
 * @author mcab
 */
public interface BarGraphListener {

    public void barGraphHoverEvent(MouseEvent e, MyPoint_HG point);

    public void barGraphHoverOutEvent(MouseEvent e);

}
