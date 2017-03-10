/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class is used to be able to highlight all with same crediantials at all
 * of the involved graphs
 *
 * @author Administrator
 */
public class PointHighLighter {

    private static ArrayList<MySerie> series_list = new ArrayList<MySerie>();
    private static HashMap FIXED_POINTS_MAP = new HashMap<Integer, MyPoint>();

    public static void addSerie(MySerie serie) {
        series_list.add(serie);
    }
    
    public static Object[] getFixedPoints(){
        return FIXED_POINTS_MAP.values().toArray();
    }
    
    public static int getAmmountFixed(){
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

    public static void highLightAllPointsAtIndex(int pointIndex) {
        if (isFixed(pointIndex) == true) {
            return;
        }
        for (MySerie serie : series_list) {
            serie.highLightPointAtIndex(pointIndex);
        }
    }

    public static void unhighLightAllPointsAtIndex(int pointIndex) {
        if (isFixed(pointIndex) == true) {
            return;
        }
        for (MySerie serie : series_list) {
            serie.unhighLightPointAtIndex(pointIndex);
        }
    }
}
