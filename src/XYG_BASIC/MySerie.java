/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class MySerie {

    private MyGraphXY myGraphXY;
    private ArrayList<MyPoint> points = new ArrayList<MyPoint>();
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
    private int point_thickness = 7;//should be 7 by default
    private Color pointsHighlightColor = pointsColor;
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
    private DiffMarkerPoints DIFF_MARKER_POINTS;

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
        DIFF_MARKER_POINTS.addDiffMarkerOutPutComponent(calculatioName, jtf);
    }

    public ArrayList<MyPoint> getPoints() {
        return points;
    }

    public void setMyGraphXY(MyGraphXY myGraph) {
        this.myGraphXY = myGraph;
        DIFF_MARKER_POINTS = new DiffMarkerPoints(this, myGraphXY);
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
            if (MAX < 100) {
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


            int x = 4; // Very Important (4 is best!), if this coef is 4 it means then 1_real_pixel = 4_points on graph
            if (pHeight / y < x) {

                while (pHeight / (y_temp * coeff_temp) < x) {
                    coeff_temp -= q; // 0.00001 
                }
                //=========!!!!!======================
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
            point.y_Scaled = (int) (point.y_Real * COEFF);
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
                myPoint.y_Scaled = (int) (myPoint.y_Real * COEFF);
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
        return this.points.get(index);
    }

    /**
     * Return the ArrayList containing all the points in the serie
     *
     * @return
     */
    protected ArrayList<MyPoint> getSerie() {
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
        point.setPointDimenssion(point_thickness);
        point.setPointHighLightColor(pointsHighlightColor);
        pointsMap.put(POINT_INDEX, point);
        points.add(point);
        POINT_INDEX++;
    }

    /**
     * Deletes all the points from the serie
     */
    protected void deleteAllPoints() {
        points.clear();
        POINT_INDEX = 0;
        MAX = 0;
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

    public void removeDiffMarkerPoint(MyPoint point) {
        this.DIFF_MARKER_POINTS.remove(point);
    }

    public boolean isDiffMarkerPoint(MyPoint point) {
        return this.DIFF_MARKER_POINTS.contains(point);
    }

    public boolean diffMarkersExist() {
        return this.DIFF_MARKER_POINTS.exist();
    }

    public ArrayList getDiffMarkerPoints() {
        return this.DIFF_MARKER_POINTS.getPoints();
    }

    public ArrayList getDiffCursors() {
        return this.DIFF_MARKER_POINTS.getCursors();
    }
}
