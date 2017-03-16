/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import javax.swing.JComponent;

/**
 *
 * @author Administrator
 */
public class MyPoint extends JComponent {

    //the coordinates after which the graph is drawn
    public int x;
    public int y;
    //====================== 
    public int x_Scaled;
    public double y_Scaled;
    //=======================
    //This values corresponds to real values which are to be drawn!!!
    public int x_Real;
    public int y_Real;
    //=======================
    private MySerie SERIE;
    //=======================
    private Color ORDINARY_COLOR = Color.BLUE;
    private Color HIGHLIGHT_COLOR = ORDINARY_COLOR;
    private Color POINT_COLOR = Color.BLACK;
    private Color POINT_COLOR_B = null;
    private Color INITIAL_POINT_COLOR = null;
    private boolean DRAW_RECT;
    private boolean highLightSet = false;
    //point dimenssion
    private int POINT_D = 7; // default 7
    private int POINT_D_SET = 1; // default 7
    private int point_area;
    //Coeff of this point !!! 
    private double RECALC_COEFF = 1;
    private int POINT_INDEX;
    //This value is for Marker so it nows if to draw or not
    private boolean MARKER_DRAW;
    //========================
    private boolean initialized_with_constructor_1 = false;
    private boolean initialized_with_constructor_2 = false;
    //===========================
    private HashMap<String, String> batch_info_map = new HashMap<String, String>();

    public MyPoint(int x, int y, double y_) {
        this.x_Real = x;
        this.y_Real = y;
        this.x_Scaled = x;
        this.y_Scaled = y_;
        this.initialized_with_constructor_1 = true;
    }

    public MyPoint(int y, double y_) {
        this.y_Real = y;
        this.y_Scaled = y_;
        this.initialized_with_constructor_2 = true;
    }

    public MyPoint(int y, double y_, Color c) {
        this.y_Real = y;
        this.y_Scaled = y_;
        this.INITIAL_POINT_COLOR = c;
        this.POINT_COLOR_B = c;
        this.initialized_with_constructor_2 = true;
    }

    /**
     * Adds a info for the given point, for displaying it later when
     * clicking/pointing the point with mouse
     *
     * @param info
     */
    public void addPointInfo(String key, String value) {
        batch_info_map.put(key, value);
    }

    /**
     * For adjusting of the point size
     *
     * @param d
     */
    protected void setPointDimenssion(int d) {
        this.POINT_D_SET = d;
        this.POINT_D = POINT_D_SET;
    }

    protected void setPointHighLightColor(Color c) {
        this.HIGHLIGHT_COLOR = c;
    }

    public void setPointColor(Color c) {
        this.POINT_COLOR_B = c;
    }
    
    public void resetPointColor(){
        this.POINT_COLOR_B =  this.INITIAL_POINT_COLOR;
    }
    
    public void setPointDrawRect(boolean bln) {
        this.DRAW_RECT = bln;
    }

    /**
     * Sets the index of this point. The index is the position of this point in
     * the serie
     *
     * @param index
     */
    protected void setPointIndex(int index) {
        if (initialized_with_constructor_1) {
            this.POINT_INDEX = index;
        } else if (initialized_with_constructor_2) { // this one is needed when adding a point with auto x -coordinate
            this.POINT_INDEX = index;
            x_Real = index + 1;
            this.x_Scaled = index + 1;
        }

    }

    public void setSerie(MySerie serie) {
        this.SERIE = serie;
    }

    protected void setRecalcCoeff(double coeff) {
        this.RECALC_COEFF = coeff;
    }

    protected double getRecalcCoeff() {
        return this.RECALC_COEFF;
    }

    public MySerie getSerie() {
        return this.SERIE;
    }

    protected String getSerieName() {
        return this.SERIE.getName();
    }

    /**
     * Get the sequence nr of the point in the serie of the point
     *
     * @return
     */
    protected int getPointIndex() {
        return this.POINT_INDEX;
    }

    public Color getPointColor() {
        if (POINT_COLOR_B != null) {
            return POINT_COLOR_B;
        } else {
            return POINT_COLOR;
        }
    }

    public boolean isDiffMarkerPoint() {
        return SERIE.isDiffMarkerPoint(this);
    }

    /**
     * Get if marker should be drawn
     *
     * @return
     */
    protected boolean getDrawMarker() {
        return this.MARKER_DRAW;
    }

    /**
     * Gets the additional batch info which was set when creating the point
     *
     * @return
     */
    protected HashMap<String, String> getBatchInfo() {
        return this.batch_info_map;
    }

    /**
     *
     * @param g
     * @param point_rescale_coeff
     */
    protected void drawPoint(Graphics g, Color pointColor) {
        Graphics2D g2d = (Graphics2D) g;
        if (highLightSet == false) {
            POINT_COLOR = pointColor;
        }

        if (POINT_COLOR_B != null) {
            POINT_COLOR = POINT_COLOR_B;
        }

        g2d.setColor(POINT_COLOR);

        if (DRAW_RECT) {
            g2d.fill3DRect((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D, true);
        } else {
            g2d.fillOval((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D);
        }

        point_area = (int) 3.14 * (int) Math.pow(POINT_D / 2, 2);
        //==================================IMPORTNAT=============================
        //Sets the size of the component which reffers to this point
        this.setLocation((x - POINT_D / 2), (int) (y - POINT_D / 2));
        this.setSize(POINT_D, POINT_D);
    }

    /**
     * Set the highlight color of the point
     */
    protected void setHighlight() {
        this.POINT_COLOR = HIGHLIGHT_COLOR;
        this.POINT_D = (POINT_D_SET * 2);
        this.highLightSet = true;
        MARKER_DRAW = true;
    }

    /**
     * Set the ordinary color of the point
     */
    protected void setHighlightOff() {
        this.POINT_COLOR = ORDINARY_COLOR;
        this.POINT_D = POINT_D_SET;
        this.highLightSet = false;
        MARKER_DRAW = false;
//        System.out.println("set ordinary");
    }

    @Override
    public String toString() {
        return "index: " + POINT_INDEX + "  /  value: " + y_Scaled;
    }

}
