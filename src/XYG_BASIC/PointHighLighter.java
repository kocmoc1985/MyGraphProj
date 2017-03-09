/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.util.ArrayList;

/**
 * This class is used to be able to highlight all with same crediantials at all
 * of the involved graphs
 *
 * @author Administrator
 */
public class PointHighLighter {

    private static ArrayList<MySerie> series_list = new ArrayList<MySerie>();
    private static boolean POINT_FIXED;

    public static void setPointFixed() {
        POINT_FIXED = true;
    }

    public static void setPointUnfixed() {
        POINT_FIXED = false;
    }

    public static boolean getIfPointFixed() {
        return POINT_FIXED;
    }

    public static void addSerie(MySerie serie) {
        series_list.add(serie);
    }

    /**
     * Higligts the given point at all of available series
     *
     * @param order_batch = should be like this: 336148$77 (336148 = order, $ =
     * delimiter, 77 = batch)
     */
    public static void highLightAllPointsAtIndex(int pointIndex) {
        if (POINT_FIXED == true) {
            return;
        }
        for (MySerie serie : series_list) {
            serie.highLightPointAtIndex(pointIndex);
        }
    }

    /**
     *
     * @param order_batch = should be like this: 336148$77 (336148 = order, $ =
     * delimiter, 77 = batch)
     */
    public static void unhighLightAllPointsAtIndex(int pointIndex) {
        if (POINT_FIXED == true) {
            return;
        }
        for (MySerie serie : series_list) {
            serie.unhighLightPointAtIndex(pointIndex);
        }
    }
}
