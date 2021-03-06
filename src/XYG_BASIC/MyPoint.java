/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    public double y_Real;
    public double y_Display;
    public double x_Display;
    //=======================
    private MySerie SERIE;
    //=======================
    public Color ORDINARY_COLOR = Color.BLUE;
    public Color HIGHLIGHT_COLOR = ORDINARY_COLOR;
    public Color POINT_COLOR = Color.BLACK;
    public Color POINT_COLOR_B = null;
    public Color INITIAL_POINT_COLOR = null;
    public boolean DRAW_RECT;
    public boolean DRAW_RECT__INITIAL = false;
    public boolean highLightSet = false;
    //point dimenssion
    public int POINT_D = 7; // default 7
    private int POINT_D_SET = 1; // default 7
    public int point_area;
    //Coeff of this point !!! 
    private double RECALC_COEFF = 1;
    public int POINT_INDEX;
    //This value is for Marker so it nows if to draw or not
    public boolean MARKER_DRAW;
    //========================
    private boolean initialized_with_constructor_1 = false;
    private boolean initialized_with_constructor_2 = false;
    //===========================
//     private HashMap<String, String> batch_info_map = new HashMap<String, String>();
    private LinkedHashMap<String, String> batch_info_map = new LinkedHashMap<String, String>(); // [2020-04-14]
    //
    private Color POINT_BORDER_COLOR__OVAL = null;
    private Color POINT_BORDER_COLOR__RECT = null;
    public Color POINT_BORDER_COLOR__RECT__PREV = null;
    public Color POINT_BORDER_COLOR__OVAL__PREV = null;

    public MyPoint(int x, double y, double y_) {
        this.x_Real = x;
        this.y_Real = y;
        this.x_Scaled = x;
        this.y_Display = y_;
        this.y_Scaled = y_;
        this.initialized_with_constructor_1 = true;
    }

    public MyPoint(double y, double y_) {
        this.y_Real = y;
        this.y_Scaled = y_;
        this.y_Display = y_;
        this.initialized_with_constructor_2 = true;
    }

    public MyPoint(double y, double y_, Color c) {
        this.y_Real = y;
        this.y_Scaled = y_;
        this.y_Display = y_;
        this.INITIAL_POINT_COLOR = c;
        this.POINT_COLOR_B = c;
        this.initialized_with_constructor_2 = true;
    }

    public void setDisplayValueX(double x_) {
        this.x_Display = x_;
    }

    public void deletePoint() {
        SERIE.deletePoint(this);
    }

    /**
     * Adds a info for the given point, for displaying it later when
     * clicking/pointing the point with mouse
     *
     * @param key
     * @param value
     * @param info
     */
    public void addPointInfo(String key, String value) {
        batch_info_map.put(key, value);
    }

    public void setPointBorder(Color borderColor,boolean setPrev) {
        this.POINT_BORDER_COLOR__OVAL = borderColor;
        if(setPrev){
            this.POINT_BORDER_COLOR__OVAL__PREV = borderColor;
        }
        
    }

    public void setPointRectBorder(Color borderColor,boolean setPrev) {
        this.POINT_BORDER_COLOR__RECT = borderColor;
        if(setPrev){
          this.POINT_BORDER_COLOR__RECT__PREV = borderColor;  
        }
    }

    /**
     * For adjusting of the point size
     *
     * @param d
     */
    public void setPointDimenssion(int d) {
        this.POINT_D_SET = d;
        this.POINT_D = POINT_D_SET;
    }

    public void setPointHighLightColor(Color c) {
        this.HIGHLIGHT_COLOR = c;
    }

    public void setPointColor(Color c) {
        this.POINT_COLOR_B = c;
    }
    
    public void setPointColorInitial(Color c) {
        this.POINT_COLOR_B = c;
        this.INITIAL_POINT_COLOR = c;
    }

    public void resetPointColor() {
        this.POINT_COLOR_B = this.INITIAL_POINT_COLOR;
    }

    public void resetPointDimenssion() {
        this.POINT_D = POINT_D_SET;
    }

    public void setPointDrawRect(boolean bln) {
        this.DRAW_RECT = bln;
    }

    public void setPointDrawRectInitial(boolean bln) {
        this.DRAW_RECT__INITIAL = bln;
    }

    /**
     * Sets the index of this point. The index is the position of this point in
     * the serie
     *
     * @param index
     */
    public void setPointIndex(int index) {
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

    public void setRecalcCoeff(double coeff) {
        this.RECALC_COEFF = coeff;
    }

    public double getRecalcCoeff() {
        return this.RECALC_COEFF;
    }

    public MySerie getSerie() {
        return this.SERIE;
    }

    public String getSerieName() {
        return this.SERIE.getName();
    }

    public MyPoint getPreviousPoint() {
        return this.SERIE.getPoint(POINT_INDEX - 1);
    }

    /**
     * Get the sequence nr of the point in the serie of the point
     *
     * @return
     */
    public int getPointIndex() {
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
    public boolean getDrawMarker() {
        return this.MARKER_DRAW;
    }

    /**
     * Gets the additional batch info which was set when creating the point
     *
     * @return
     */
    public HashMap<String, String> getPointInfo() {
        return this.batch_info_map;
    }

    /**
     *
     * @param g
     * @param pointColor
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

        if (DRAW_RECT || DRAW_RECT__INITIAL) {
            if (POINT_BORDER_COLOR__RECT == null) {
                g2d.fill3DRect((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D, true);
            } else {
                //
                g2d.setColor(POINT_COLOR);
                g2d.fill3DRect((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D, true);
                //
                g2d.setColor(POINT_BORDER_COLOR__RECT);
                int point_d_temp = POINT_D - 4;
                g2d.fill3DRect((int) (x - point_d_temp / 2), (int) (y - point_d_temp / 2), point_d_temp, point_d_temp, true);
                //
            }
        } else {
            if (POINT_BORDER_COLOR__OVAL == null) {
                g2d.fillOval((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D);
            } else {
                //
                g2d.setColor(POINT_COLOR);
                g2d.fillOval((int) (x - POINT_D / 2), (int) (y - POINT_D / 2), POINT_D, POINT_D);
                //
                g2d.setColor(POINT_BORDER_COLOR__OVAL);
                int point_d_temp = POINT_D - 4;
                g2d.fillOval((int) (x - point_d_temp / 2), (int) (y - point_d_temp / 2), point_d_temp, point_d_temp);
                //
            }

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
    public void setHighlight() {
        this.POINT_COLOR = HIGHLIGHT_COLOR;
        this.POINT_D = (POINT_D_SET * 2);
        this.highLightSet = true;
        MARKER_DRAW = true;
    }

    /**
     * Set the ordinary color of the point
     */
    public void setHighlightOff() {
        this.POINT_COLOR = ORDINARY_COLOR;
        this.POINT_D = POINT_D_SET;
        this.highLightSet = false;
        MARKER_DRAW = false;
//        System.out.println("set ordinary");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyPoint other = (MyPoint) obj;
        if (this.POINT_INDEX != other.POINT_INDEX) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.POINT_INDEX;
        return hash;
    }

    @Override
    public String toString() {
        return "index: " + POINT_INDEX + "  /  value: " + y_Scaled;
    }
}
