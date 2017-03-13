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
public class DiffMarkerPoints {

    private MySerie serie;
    private MyPoint MARKER_POINT_1;
    private MyPoint MARKER_POINT_2;

    public DiffMarkerPoints(MySerie serie) {
        this.serie = serie;
    }

    public ArrayList<MyPoint> getPoints() {
        ArrayList list = new ArrayList();

        if (MARKER_POINT_1 != null) {
            list.add(MARKER_POINT_1);
        }

        if (MARKER_POINT_2 != null) {
            list.add(MARKER_POINT_2);
        }

        return list;
    }

    public void add(MyPoint point) {
        if (MARKER_POINT_1 == null && MARKER_POINT_2 == null) {
            MARKER_POINT_1 = point;
            reset();
        } else if (MARKER_POINT_1 != null && MARKER_POINT_2 == null) {
            MARKER_POINT_2 = point;
            //
            addProperties();
            System.out.println("SUMM: " + calcSum());
            System.out.println("AV: " + calcAv());
            //
        } else if (MARKER_POINT_1 != null && MARKER_POINT_2 != null) {
            MARKER_POINT_1 = point;
            MARKER_POINT_2 = null;
            reset();
        }
        System.out.println("" + toString());
    }

    public void remove(MyPoint point) {
        reset();
        if (MARKER_POINT_1 == point) {
            MARKER_POINT_1 = null;
        } else if (MARKER_POINT_2 == point) {
            MARKER_POINT_2 = null;
        }
        System.out.println("" + toString());
    }

    public boolean contains(MyPoint point) {
        if (MARKER_POINT_1 == point || MARKER_POINT_2 == point) {
            return true;
        } else {
            return false;
        }
    }

    public boolean exist() {
        if (MARKER_POINT_1 != null || MARKER_POINT_2 != null) {
            return true;
        } else {
            return false;
        }
    }

    public MySerie getSerie() {
        return serie;
    }

    private boolean bothExist() {
        if (MARKER_POINT_1 != null && MARKER_POINT_2 != null) {
            return true;
        } else {
            return false;
        }
    }

    private void reset() {
        for (MyPoint myPoint : draw_rect_points_list) {
            myPoint.setPointDrawRect(false);
        }
        draw_rect_points_list = new ArrayList<MyPoint>();
    }

    private ArrayList<MyPoint> draw_rect_points_list = new ArrayList<MyPoint>();

    private void addProperties() {
        if (bothExist()) {
            for (int i = MARKER_POINT_1.getPointIndex(); i <= MARKER_POINT_2.getPointIndex(); i++) {
                MyPoint mp = serie.getSerie().get(i);
                mp.setPointDrawRect(true);
                draw_rect_points_list.add(mp);
            }
        }
    }

    public int calcSum() {
        int sum = 0;
        if (bothExist()) {
            for (int i = MARKER_POINT_1.getPointIndex(); i <= MARKER_POINT_2.getPointIndex(); i++) {
                sum += serie.getSerie().get(i).y_Real;
            }
        }
        return sum;
    }

    public int calcAv() {
        int sum = 0;
        int c = 0;
        if (bothExist()) {
            for (int i = MARKER_POINT_1.getPointIndex(); i <= MARKER_POINT_2.getPointIndex(); i++) {
                c++;
                sum += serie.getSerie().get(i).y_Real;
            }
        }
        return (sum / c);
    }

    @Override
    public String toString() {
        return "MARKER_1: " + MARKER_POINT_1 + "   /  MARKER_2: " + MARKER_POINT_2;
    }
}
