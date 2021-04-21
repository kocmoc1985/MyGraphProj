/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XY_BUH_INVOICE;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.PointHighLighter;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_BuhInvoice extends MyGraphXY {

    public MenuItem menu_item_goto_faktura = new MenuItem("Se faktura");
    private final Buh_Invoice_Main__IF bim;

    public MyGraphXY_BuhInvoice(Buh_Invoice_Main__IF bim) {
        this.bim = bim;
        init();
    }

    private void init() {
        menu_item_goto_faktura.addActionListener(this);
    }

    @Override
    public void addAdditionalControlsPopups() {
        popup.add(menu_item_goto_faktura);
    }

    @Override
    public void drawMarkerWhenPointing_b(Graphics2D g2) {
         drawMarkerInfo(g2);
    }

    @Override
    public void drawMarkerInfo(Graphics2D g2) {
         if (DRAW_MARKER_INFO == 1) {
            String toDraw = getPointInfo(MARKER_POINT, XyGraph_BuhInvoice.NICK__FAKTURA_KUND);
            g2.drawString(toDraw, MARKER_X - 20, MARKER_Y - 25);
        }
    }
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
        //
        if (e.getSource() instanceof MyPoint && SHOW_POP_UP_LEFT_CLICK && e.getButton() == 1) {
            myPointClicked();
        } else if (e.getSource() instanceof MyPoint && e.getButton() == 3 && PointHighLighter.isFixed(MARKER_POINT) == false) {
            CLICK_RIGHT_POINT = (MyPoint) e.getSource();
            popup.removeAll();
//            popup.add(menu_item_fix_point);
            addAdditionalControlsPopups();
            popup.show(this, MARKER_POINT.x + 5, MARKER_POINT.y + 5);
        } else if (e.getSource() instanceof MyPoint && e.getButton() == 3 && PointHighLighter.isFixed(MARKER_POINT)) {
            CLICK_RIGHT_POINT = (MyPoint) e.getSource();
            popup.removeAll();
//            popup.add(menu_item_unfix_point);
            addAdditionalControlsPopups();
            popup.show(this, MARKER_POINT.x + 5, MARKER_POINT.y + 5);
        }
        //
    }
    
    private String getPointInfo(MyPoint point,String key){
       return  point.getPointInfo().get(key);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //
        if (ae.getSource() == menu_item_goto_faktura) {
            //
            if (bim != null) {
//                String fakturaNr = CLICK_RIGHT_POINT.getBatchInfo().get(XyGraph_BuhInvoice.KEY__FAKTURA_NR.toUpperCase());
                String fakturaNr = getPointInfo(CLICK_RIGHT_POINT, XyGraph_BuhInvoice.KEY__FAKTURA_NR.toUpperCase());
                bim.goToFaktura(fakturaNr);
            }
            //
        }
        //
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
