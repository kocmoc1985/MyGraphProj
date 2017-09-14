/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to be able to highlight all with same crediantials at all
 * of the involved graphs
 *
 * @author Administrator
 */
public class PointHighLighter {

    private static ArrayList<MySerie> series_list = new ArrayList<MySerie>();
    private static ArrayList<MySerie> series_list_single = new ArrayList<MySerie>();
    private static HashMap FIXED_POINTS_MAP = new HashMap<Integer, MyPoint>();

    /**
     * Use this when a point must be highlighted on several series
     *
     * @param serie
     */
    public static void addSerie(MySerie serie) {
        series_list.add(serie);
    }

    /**
     * Use this when a point must be highlighted only on a single serie
     *
     * @param serie
     */
    public static void addSerieSingle(MySerie serie) {
        series_list_single.add(serie);
    }

    public static boolean serieExists(MySerie serie) {
        if (series_list.contains(serie)) {
            return true;
        } else {
            return false;
        }
    }

    public static Object[] getFixedPoints() {
        return FIXED_POINTS_MAP.values().toArray();
    }

    public static int getAmmountFixed() {
        return FIXED_POINTS_MAP.size();
    }

    public static void setPointFixed(MyPoint point) {
        FIXED_POINTS_MAP.put(point.getPointIndex(), point);
//        System.out.println("SIZE >>>>>>" + FIXED_POINTS_MAP.size());
    }

    public static void setPointUnfixed(MyPoint point) {
        FIXED_POINTS_MAP.remove(point.getPointIndex());
//        System.out.println("SIZE >>>>>>" + FIXED_POINTS_MAP.size());
    }

    public static boolean isFixed(Object obj) {
        if (obj instanceof MyPoint) {
            MyPoint point = (MyPoint) obj;
            if (FIXED_POINTS_MAP.containsValue(point)) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof Integer) {
            int index = (Integer) obj;
            if (FIXED_POINTS_MAP.containsKey(index)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void highLightAllPointsAtIndex(MyPoint point) {
        //
        if (series_list_single.contains(point.getSerie())) {
            for (MySerie serie : series_list_single) {
                if (serie.getName().equals(point.getSerie().getName())) {
                    serie.highLightPointAtIndex(point.getPointIndex());
                    return;
                }
            }
        }
        //
        if (series_list.contains(point.getSerie()) == false) {
            return;
        }
        //
        if (isFixed(point.getPointIndex()) == true) {
            return;
        }
        for (MySerie serie : series_list) {
            serie.highLightPointAtIndex(point.getPointIndex());
        }
    }

    public static void unhighLightAllPointsAtIndex(MyPoint point) {
        if (series_list.contains(point.getSerie()) == false) {
            return;
        }

        if (isFixed(point.getPointIndex()) == true) {
            return;
        }
        for (MySerie serie : series_list) {
            serie.unhighLightPointAtIndex(point.getPointIndex());
        }
    }
}
