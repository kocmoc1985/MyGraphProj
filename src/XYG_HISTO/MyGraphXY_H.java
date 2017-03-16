/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.MyGraphXY;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_H extends MyGraphXY {

    private ArrayList<Double> xValuesList;

    public void setXValues(ArrayList list) {
        this.xValuesList = list;
    }

    @Override
    public void scaleX(Graphics2D g2) {
        if (SCALE_X_AXIS) {
            int j = 0; // step identifier

            int vv = (int) (X_MAX);
//            if (vv > 100000 && vv < 1000000) {
//                j = 10000;
//            } else if (vv > 10000 && vv < 100000) {
//                j = 1000;
//            } else if (vv > 1000 && vv < 10000) {
//                j = 100;
//            } else if (vv > 100 && vv < 1000) {
//                j = 50;
//            } else if (vv > 10 && vv < 100) {
//                j = 5;
//            } else {
//                j = 1;
//            }

            j = 1;

            int m = 1; // frequency regulator
            for (int i = 1; i < getWidth(); i++) {
                double X = i / ONE_UNIT_X; //!!!!!!!!! X = nr of one_unit_x per real pixel
                if (X > (j * m) && X < (j * m) + ONE_UNIT_X) {
                    if (SHOW_GRID) {
                        g2.setPaint(GRID_COLOR);
                        g2.drawRect(i, 0, 1, getHeight());
                        //
                        if (SHOW_GRID_AND_SCALE) {
                            g2.drawString("" + (j * m), i - 10, (int) (getHeight() - 3 * COEFF_SMALL_GRID) - 1);

                        }
                        //
                        m++;
                    } else {
                        g2.setPaint(GRID_COLOR);

                        if (xValuesList != null) {
                            try {
                                g2.drawString("" + xValuesList.get((j * m) - 1), i - 10, (int) (getHeight() - 3 * COEFF_SMALL_GRID) - 1);
                            } catch (IndexOutOfBoundsException ex) {
                                g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 3);
                            }
                        } else {
                            g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 3);
                        }

                        g2.drawRect(i, (int) (getHeight() - 5 * COEFF_SMALL_GRID), 1, (int) (5 * COEFF_SMALL_GRID));
                        m++;
                    }

                }
            }
        }
    }

    @Override
    public void myPointClicked() {
        popup.removeAll();

        //==========================Batch Info displaying==================
        MARKER_POINT.addPointInfo("serie", MARKER_POINT.getSerieName());
        MARKER_POINT.addPointInfo("y", "" + (MARKER_POINT.y_Display));
        MARKER_POINT.addPointInfo("x", "" + MARKER_POINT.x_Display);
        //
        HashMap<String, String> b_info_map = MARKER_POINT.getBatchInfo();
        //
        Set set = b_info_map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            try {
                String key = (String) it.next();
                String value = (String) b_info_map.get(key);
                popup.add(new MenuItem(key + ": " + value));
            } catch (NoSuchElementException ex1) {
                System.out.println("" + ex1);
            }
        }

        //=================================================================
        popup.show(this, MARKER_POINT.x + 5, MARKER_POINT.y + 5);
        //
    }
    
    
}
