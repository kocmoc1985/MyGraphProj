/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.MyGraphXY;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_H extends MyGraphXY {

    public ArrayList<String> xValuesList;
    public int STEP_IDENTIFIER_X_AXIS = -1;

    public void setXValues(ArrayList<String> list) {
        xValuesList = list;
    }

    public void setStepIdentifierX(int x) {
        this.STEP_IDENTIFIER_X_AXIS = x;
    }

    @Override
    public void drawLimits(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (getHeight() < 50) {//ONE_UNIT_Y < 1.1 || getHeight() < 50 || ONE_UNIT_Y > 20
            return;
        }
        //
        if (LIMIT_MAX == 0 || LIMIT_MIN == 0) {
            return;
        }
        //
        //EXTREAMLY IMPORTANT CALCULATION!!!
        double scaled_max = (LIMIT_MAX * ALL_SERIES_COEFF);
        int pixels_max = (int) Math.round(getHeight() - (ONE_UNIT_X * scaled_max));
        int max = pixels_max;

        double scaled_min = (LIMIT_MIN * ALL_SERIES_COEFF);
        int pixels_min = (int) Math.round(getHeight() - (ONE_UNIT_X * scaled_min));
        int min = pixels_min;

        ORDINARY_STROKE = (BasicStroke) g2.getStroke();

        g2.setStroke(GRID_STROKE);

        //draw lim max
        g2.setPaint(Color.RED);
//      g2.drawLine(x, max, getWidth(), max);
        g2.drawLine(max, 0, max, getHeight());

        //draw lim min
        g2.setPaint(Color.RED);
        g2.drawLine(min, 0, min, getHeight());

        g2.setStroke(ORDINARY_STROKE);

    }

    @Override
    public void scaleX(Graphics2D g2) {
        if (SCALE_X_AXIS) {
            int j = 0; // step identifier

            int vv = (int) (X_MAX);

            if (vv > 500) {
                j = 100;
            } else if (vv > 200) {
                j = 40;
            } else if (vv > 100) {
                j = 20;
            } else if (vv > 30) {
                j = 10;
            } else {
                j = 2;
            }


            if (STEP_IDENTIFIER_X_AXIS != -1) {
                j = STEP_IDENTIFIER_X_AXIS;
            }


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
                                g2.drawString("" + xValuesList.get((j * m) - 1), i - 10, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                            } catch (IndexOutOfBoundsException ex) {
                                g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                            }
                        } else {
                            g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 5);
                        }

                        g2.drawRect(i, (int) (getHeight() - 5 * COEFF_SMALL_GRID), 1, (int) (5 * COEFF_SMALL_GRID));
                        m++;
                    }

                }
            }
        }
    }


    @Override
    public void addPointInfo() {
        MARKER_POINT.addPointInfo("serie", MARKER_POINT.getSerieName());
        MARKER_POINT.addPointInfo("y", "" + (MARKER_POINT.y_Display));
        MARKER_POINT.addPointInfo("x", "" + MARKER_POINT.x_Display);
    }

    @Override
    public void addAdditionalControlsPopups() {
        if (MARKER_POINT.isDiffMarkerPoint() == false) {
            popup.add(menu_item_diff_marker_add);
        } else if (MARKER_POINT.isDiffMarkerPoint()) {
            popup.add(menu_item_diff_marker_remove);
        }
    }
}
