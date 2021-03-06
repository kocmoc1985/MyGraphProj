/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class MySerie {

    public MyGraphXY myGraphXY;
//    public ArrayList<MyPoint> points = new ArrayList<MyPoint>();
    private final List<MyPoint> points = Collections.synchronizedList(new ArrayList<MyPoint>());
    private HashMap<Integer, MyPoint> pointsMap = new HashMap<Integer, MyPoint>();
    private String name;
    private Color curveColor = Color.BLACK;
    private Color pointsColor = Color.BLUE;
    private boolean drawPoints = true;
    private boolean drawLines = true;
    //=============================
    private BasicStroke lineRenderer;
    private float[] renderer_line_dotted = new float[]{1.0f, 1.0f}; //undoted by default
    private float renderer_line_thickness = 1.0f;
    //==============================
    public int point_thickness = 7;//should be 7 by default
    public Color pointsHighlightColor = pointsColor;
    //==============================
    /**
     * if this algor is used then all the series are scaled with the
     */
    private boolean SCALE_SAME_ALL_SERIES = true;
    //========================
    public double COEFF = 1;
    //
    private double MAX;
    private int POINT_INDEX;
    //========================
    public DiffMarkerPoints DIFF_MARKER_POINTS;
    //
    private ArrayList<PointDeletedAction> point_deleted_action_list = new ArrayList<PointDeletedAction>();

    public MySerie(String name) {
        this.name = name;
        adjustRenderer();
    }

    public MySerie(String name, Color curve_color) {
        this.name = name;
        this.curveColor = curve_color;
        adjustRenderer();
    }

    /**
     *
     * @param name - the name of the Serie
     * @param drawLines - if the line shall be drawn
     * @param curve_color - The color of the Curve
     * @param drawPoints - if points shall be drawn
     * @param pointsColor - the color of the points
     */
    public MySerie(String name, boolean drawLines, Color curve_color, boolean drawPoints, Color pointsColor) {
        this.name = name;
        this.curveColor = curve_color;
        this.drawPoints = drawPoints;
        this.pointsColor = pointsColor;
        this.pointsHighlightColor = pointsColor;
        adjustRenderer();
    }

    /**
     *
     * @param name - the name of the Serie
     * @param drawLines - if the line shall be drawn
     * @param curve_color - The color of the Curve
     * @param drawPoints - if points shall be drawn
     * @param pointsColor - the color of the points
     * @param overalScaling - true if all the series are to be scaled by the
     * lowest coeff
     */
    public MySerie(String name, boolean drawLines, Color curve_color, boolean drawPoints, Color pointsColor, boolean overalScaling) {
        this.name = name;
        this.curveColor = curve_color;
        this.drawPoints = drawPoints;
        this.pointsColor = pointsColor;
        this.pointsHighlightColor = pointsColor; //
        this.SCALE_SAME_ALL_SERIES = overalScaling;
        adjustRenderer();
    }

    public void addDiffMarkerOutPutComponent(String calculatioName, JTextField jtf) {
        this.DIFF_MARKER_POINTS.addDiffMarkerOutPutComponent(calculatioName, jtf);
    }

    public List<MyPoint> getPoints() {
        return points;
    }

    public void setMyGraphXY(MyGraphXY myGraph, boolean createDiffMarkers) {
        this.myGraphXY = myGraph;
        if (createDiffMarkers == false) {
            return;
        }
        if (this.DIFF_MARKER_POINTS == null) {
            this.DIFF_MARKER_POINTS = new DiffMarkerPoints(this, myGraphXY);
        }
    }

    /**
     * Adjusts the visual appereance of the curve
     */
    private void adjustRenderer() {
        //default rendering options

        lineRenderer = new BasicStroke(
                renderer_line_thickness, //thickness of the line
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                1.0f,
                renderer_line_dotted, //// to make dotted {10.0f, 6.0f} // undoted {1.0f, 1.0f}
                0.0f);
    }

    /**
     * Checks if scaling coeef shall be changed
     *
     * @param point
     */
    protected void checkRecalc(MyPoint point, int pHeight) {
        if (point.y_Real > MAX) {
            MAX = point.y_Scaled;

            double y = point.y_Scaled;
            double coeff_temp = 1;
            double y_temp = y;

            // Note this is a very important coeff 
            // if this coef is to small (0.001) it will not
            // fit to acomplisch values over 10000.
            // But the less this coeff is the more it takes on CPU
            double q = 0.00001;
            //
            if (MAX < 1) {
                q = 1;
            } else if (MAX < 10) {
                q = 0.1;
            } else if (MAX < 100) {
                q = 0.01;
            } else if (MAX < 1000) {
                q = 0.001;
            } else if (MAX < 10000) {
                q = 0.0001;
            } else if (MAX < 100000) {
                q = 0.00001;
            } else if (MAX < 1000000) {
                q = 0.000001;
            }
            //
            int x = 4; // Very Important (4 is best!), if this coef is 4 it means then 1_real_pixel = 4_points on graph
            //
            if (pHeight / y < x) {
                //
                while (pHeight / (y_temp * coeff_temp) < x) {
                    coeff_temp -= q; // 0.00001 
                }
                //
                if (SCALE_SAME_ALL_SERIES) {
                    useOverallCoeffAlgor(coeff_temp);
                } else {
                    COEFF = coeff_temp;
                }
                //====================================
            }
            recalcSerie();
        }

        //Must be!!
        if (point.getRecalcCoeff() != COEFF) {
            point.y_Scaled = (point.y_Real * COEFF);
            point.setRecalcCoeff(COEFF);
        }
    }

    /**
     * Recalculates all the points in the serie but only if the
     * points.RECALC_COEFF != COEFF!!!!!!
     */
    private void recalcSerie() {
        for (MyPoint myPoint : points) {
            if (myPoint.getRecalcCoeff() != COEFF) {
                myPoint.y_Scaled = (myPoint.y_Real * COEFF);
                myPoint.setRecalcCoeff(COEFF);
            }
        }
    }

    /**
     * This to be able to scale all series with the same coeff
     *
     * @param coeff_calculated
     */
    private void useOverallCoeffAlgor(double coeff_calculated) {
        if (coeff_calculated < myGraphXY.getALL_SERIES_COEFF()) {
            COEFF = coeff_calculated;
            myGraphXY.setALL_SERIES_COEFF(COEFF);
        } else {
            COEFF = myGraphXY.getALL_SERIES_COEFF();
        }
    }

    public MyPoint getPoint(int index) {
        try {
            return this.points.get(index);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Return the ArrayList containing all the points in the serie
     *
     * @return
     */
    public List<MyPoint> getSerie() {
        return this.points;
    }

    /**
     * adds a point to this serie
     *
     * @param point
     */
    protected void addPoint(MyPoint point) {
        point.setSerie(this);
        point.setPointIndex(POINT_INDEX);
        //
        point.setPointDimenssion(point_thickness);
        //
        point.setPointHighLightColor(pointsHighlightColor);
        pointsMap.put(POINT_INDEX, point);
        points.add(point);
        POINT_INDEX++;
    }

    /**
     * @deprecated Use the delete method in MyXyGraph
     */
    public void deleteAllPoints() {
        points.clear();
        POINT_INDEX = 0;
        MAX = 0;
    }

    public void deletePoint(MyPoint point) {
        //
        myGraphXY.remove(point);
        points.remove(point);
        //
        for (PointDeletedAction pda : point_deleted_action_list) {
            pda.pointDeleted(point);
        }
        //
    }

    public void addPointDeletedActionListener(PointDeletedAction pda) {
        point_deleted_action_list.add(pda);
    }

    //===================================SET=====================================
    /**
     * If this is set, than all the series in the graph are scaled with the same
     * coef. If you choose to have it off for a serie than it will be
     * independently scaled
     */
    public void setOverallScale(boolean scale_same) {
        this.SCALE_SAME_ALL_SERIES = scale_same;
    }

    /**
     * true if the line should be dotted
     *
     * @param dotted
     */
    public void setLineDotted() {
        this.renderer_line_dotted = new float[]{10.0f, 6.0f};
        adjustRenderer();
    }

    /**
     * Adjusts the thickness of the line
     *
     * @param thickness - the thickness of the line should be written as (3.0f)
     */
    public void setLineThickness(float thickness) {
        this.renderer_line_thickness = thickness;
        adjustRenderer();
    }

    /**
     * Sets the size of the points. if you want to make the point 50% bigger the
     * procent param shoul be 1.5
     *
     * @param procent
     */
    public void setPointThickness(double percent) {
        point_thickness *= percent;
    }

    public void setPointHighLightColor(Color c) {
        this.pointsHighlightColor = c;
    }

    /**
     *
     * @param draw true if the lines shall be drawn
     */
    public void setDrawLines(boolean draw) {
        this.drawLines = draw;
    }

    /**
     * Sets the color of the curve
     *
     * @param c
     */
    public void setCurveColor(Color c) {
        this.curveColor = c;
    }

    /**
     *
     * @param draw - true if the points shall be drawn
     */
    public void setDrawPoints(boolean draw) {
        this.drawPoints = draw;
    }

    /**
     * Sets the color of the points
     *
     * @param c
     */
    public void setPointColor(Color c) {
        this.pointsColor = c;
        this.pointsHighlightColor = c;
    }

    public void resetPointsColorAndForm() {
        //
        for (MyPoint point : points) {
            //
            if (point.POINT_BORDER_COLOR__RECT__PREV != null) {
                point.setPointRectBorder(new Color(point.POINT_BORDER_COLOR__RECT__PREV.getRGB()), true);
            } else if (point.POINT_BORDER_COLOR__OVAL__PREV != null) {
                point.setPointBorder(new Color(point.POINT_BORDER_COLOR__OVAL__PREV.getRGB()), true);
            }
            //
            //
            if (point.DRAW_RECT__INITIAL == false) {
                point.setPointDrawRect(false);
            }
            //
            point.resetPointColor();
            point.resetPointDimenssion();
        }
        //
        myGraphXY.repaint();
        myGraphXY.updateUI();
    }
    //===================================GET=====================================

    /**
     * Gets the renderer of this line. The renderer affects the graphical
     * properties of the line
     *
     * @return
     */
    public BasicStroke getLineRenderer() {
        return this.lineRenderer;
    }

    public Color getCurveColor() {
        return this.curveColor;
    }

    public Color getPointColor() {
        return this.pointsColor;
    }

    /**
     * true if the lines shall be written
     *
     * @return
     */
    public boolean drawLines() {
        return this.drawLines;
    }

    /**
     * true if the points shall be written
     *
     * @return
     */
    public boolean drawPoints() {
        return this.drawPoints;
    }

    /**
     * Get the name of the serie (speed, torq or whatever......)
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public boolean nameEquals(String inName) {
        if (this.name.equals(inName)) {
            return true;
        } else {
            return false;
        }
    }

    public void highLightPointAtIndex(int pointIndex) {
        MyPoint curr_point = pointsMap.get(pointIndex);
        if (curr_point != null) {
            curr_point.setHighlight();
            myGraphXY.validateFromOutside();
        }
    }

    public void unhighLightPointAtIndex(int pointIndex) {
        MyPoint curr_point = pointsMap.get(pointIndex);
        if (curr_point != null) {
            pointsMap.get(pointIndex).setHighlightOff();
        }
    }

    public void addDiffMarkerPoint(MyPoint point) {
        this.DIFF_MARKER_POINTS.add(point);
    }

    public void addDiffMarkerPoints() {
        MyPoint pointA = getPoint(0);
        MyPoint pointB = getPoint(points.size() - 1);
        this.DIFF_MARKER_POINTS.add(pointA);
        this.DIFF_MARKER_POINTS.add(pointB);
    }

    public void removeDiffMarkerPoint(MyPoint point) {
        this.DIFF_MARKER_POINTS.oneCursorRemovedUsingMenu(point);
    }

    public void removeDiffMarkerPoints() {
        this.DIFF_MARKER_POINTS.removeDiffMarkerPoints();
    }

    public boolean isDiffMarkerPoint(MyPoint point) {
        return this.DIFF_MARKER_POINTS.contains(point);
    }

    public ArrayList getDiffMarkerPoints() {
        return this.DIFF_MARKER_POINTS.getPoints();
    }

    public ArrayList getDiffCursors() {
        return this.DIFF_MARKER_POINTS.getCursors();
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final MySerie other = (MySerie) obj;
//        if (Double.doubleToLongBits(this.COEFF) != Double.doubleToLongBits(other.COEFF)) {
//            return false;
//        }
//        if (Double.doubleToLongBits(this.MAX) != Double.doubleToLongBits(other.MAX)) {
//            return false;
//        }
//        if (!Objects.equals(this.name, other.name)) {
//            return false;
//        }
//        return true;
//    }
//    
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 67 * hash + Objects.hashCode(this.name);
//        hash = 67 * hash + (int) (Double.doubleToLongBits(this.COEFF) ^ (Double.doubleToLongBits(this.COEFF) >>> 32));
//        hash = 67 * hash + (int) (Double.doubleToLongBits(this.MAX) ^ (Double.doubleToLongBits(this.MAX) >>> 32));
//        return hash;
//    }
}
