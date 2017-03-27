/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class CursorOverPoint {

    private MySerie serie;
    private ArrayList<MyPoint> points;
    private MyPoint prevPoint;
    private boolean cursorOverPoint = false;

    public CursorOverPoint(MySerie serie) {
        this.serie = serie;
        this.points = serie.getPoints();
    }
    
    public boolean isCursorOverPoint(){
        return cursorOverPoint;
    }
    
    public MyPoint getPoint(){
        return prevPoint;
    }

    public MyPoint go(int x) {
        for (MyPoint point : points) {
//            System.out.println("point.x: " + point.x_Real + " / " + point.x_Scaled + " / " + point.x); 

            for (int i = 0; i < point.POINT_D / 2; i++) {
                if (point.x == (x - i)) {
//                    System.out.println("CURSOR OVER POINT -!!: " + i);
                    highLightOnOff(point, true);
                    prevPoint = point;
                    cursorOverPoint = true;
                    return point;
                } else {
                    cursorOverPoint = false;
                    if (prevPoint != null) {
                        prevPoint.setHighlightOff();
                    }
                }
            }

            for (int i = 0; i < point.POINT_D / 2; i++) {
                if (point.x == (x + i)) {
//                    System.out.println("CURSOR OVER POINT +!!: " + i);
                    highLightOnOff(point, true);
                    prevPoint = point;
                    cursorOverPoint = true;
                    return point;
                } else {
                    cursorOverPoint = false;
                    if (prevPoint != null) {
                        prevPoint.setHighlightOff();
                    }
                }
            }
        }
        return null;
    }

    private void highLightOnOff(MyPoint point, boolean on) {
        if (prevPoint != null) {
            prevPoint.setHighlightOff();
        }

        if (on) {
            point.setHighlight();
        } else {
            point.setHighlightOff();
        }

    }
}
