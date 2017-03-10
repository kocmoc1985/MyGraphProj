/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class MyGraphXY extends JPanel implements ComponentListener, MouseListener, Runnable, MouseMotionListener, ActionListener {

    private double X_MAX = 1;
    private double Y_MAX = 1;
    private final ArrayList<MySerie> SERIES = new ArrayList<MySerie>();
    private double ONE_UNIT_X = 1;
    private double ONE_UNIT_Y = 1;
    private int PANEL_AREA_PREV;
    private PopupMenu popup = new PopupMenu("Popup");
    //==========================================
    private int MARKER_X;
    private int MARKER_Y;
    private MyPoint MARKER_POINT;
    private Color MARKER_COLOR = Color.BLACK;
    private float[] MARKER_DOTTED = new float[]{10.0f, 6.0f};
    private int DRAW_MARKER_INFO;
    private boolean AUTO_RESET_MARKER = false; // this means that marker is leaved drawn at the last point you pointed on
    //===========================================
    private BasicStroke ORDINARY_STROKE;
    private BasicStroke MARKER_STROKE;
    private BasicStroke GRID_STROKE;
    //==========================================
    //==========================================
    private double ALL_SERIES_COEFF = 1;
    //==========================================
    private boolean SHOW_GRID = false;
    private boolean SHOW_GRID_AND_SCALE = true;
    private Color GRID_COLOR = Color.LIGHT_GRAY;
    private Color BACKGROUND_COLOR = new Color(249, 249, 249);
    private boolean SHOW_POP_UP = true;
    private boolean SEARCH_POINT_POS = true;
    private boolean SCALE_XY_AXIS = true;
    private boolean SCALE_X_AXIS = true;
    private boolean SCALE_Y_AXIS = true;
    private double COEFF_SMALL_GRID = 1;
    private boolean DRAW_MARKER = true;
    //
    private MenuItem menu_item_fix_point = new MenuItem("Fix point");
    private MenuItem menu_item_unfix_point = new MenuItem("Unfix point");

    public MyGraphXY() {
        PANEL_AREA_PREV = getWidth() * getHeight();
        this.add(popup);
        setBackground(Color.WHITE);
        addMouseMotionListener(this);
        addComponentListener(this);
        addMouseListener(this);
        new Thread(this).start();
        init();
        initializeStrokes();
    }

    private void init() {
        menu_item_fix_point.addActionListener(this);
        menu_item_unfix_point.addActionListener(this);
    }

    /**
     * Gets the COEFF to be able to scale all the series with the same coeff
     *
     * @return
     */
    protected double getALL_SERIES_COEFF() {
        return this.ALL_SERIES_COEFF;
    }

    protected void setALL_SERIES_COEFF(double coeff) {
        this.ALL_SERIES_COEFF = coeff;
    }

    /**
     *
     */
    private void initializeStrokes() {

        MARKER_STROKE = new BasicStroke(
                2.0f, //thickness of the line
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                1.0f,
                MARKER_DOTTED, //// to make dotted {10.0f, 6.0f} // undoted {1.0f, 1.0f}
                0.0f);

        GRID_STROKE = new BasicStroke(
                1.0f, //thickness of the line
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                1.0f,
                new float[]{1.0f, 1.0f}, //// to make dotted {10.0f, 6.0f} // undoted {1.0f, 1.0f}
                0.0f);
    }

    /**
     * If true the grid is drawn
     *
     * @param draw
     */
    public void setDrawGrid(boolean draw) {
        SHOW_GRID = draw;
    }

    /**
     * The scaling is not shown when the Grid is shown
     */
    public void setDisableScalingWhenGrid() {
        SHOW_GRID_AND_SCALE = false;
    }

    /**
     * The color of the grid or of those xy scaling lines
     *
     * @param color
     */
    public void setGridColor(Color color) {
        this.GRID_COLOR = color;
    }

    /**
     * Set the background color of the graph
     *
     * @param color
     */
    public void setBackgroundColor(Color color) {
        this.BACKGROUND_COLOR = color;
    }

    /**
     * If true it draws not the whole grid but only the short lines on x & y
     * axises
     *
     * @param set
     */
    public void setScaleXYaxis(boolean set) {
        this.SCALE_XY_AXIS = set;
        this.SCALE_X_AXIS = set;
        this.SCALE_Y_AXIS = set;
    }

    public void setScaleXorYAxis(boolean scale_x, boolean scale_y) {
        this.SCALE_X_AXIS = scale_x;
        this.SCALE_Y_AXIS = scale_y;
        //
        if (scale_x == false && scale_y == false) {
            this.SCALE_XY_AXIS = false;
        } else {
            SCALE_XY_AXIS = true;
        }
        //

    }

    /**
     * To make the scale lines longer set percent = 1.4 for example. To clarify
     * its the same lines as grid lines but not in full length.
     *
     * @param percent
     */
    public void setScaleXYaxisLength(double percent) {
        this.COEFF_SMALL_GRID = percent;
    }

    /**
     * Sets if the popUp window shall be displayed when clicking on a point
     *
     * @param show
     */
    public void setShowPopUp(boolean show) {
        this.SHOW_POP_UP = show;
    }

    /**
     * Adjust color and width of the border aroubd the graph
     *
     * @param color
     * @param width
     */
    public void setBorder(Color color, int width) {
        this.setBorder(BorderFactory.createLineBorder(color, width));
    }

    /**
     * Sets if the program shall search for the point when moving the mouse over
     * the graph area. Saves computer capacity when turned off!! This function
     * is used to highlight the point when pointed whith a cursor. The info
     * popUp could be used without this function
     *
     * @param search
     */
    public void setHighLightPoints(boolean search) {
        SEARCH_POINT_POS = search;
    }

    /**
     * Sets if the marker should be drawn
     *
     * @param draw
     */
    public void setDrawMarker(boolean draw) {
        DRAW_MARKER = draw;
    }

    /**
     * Sets the color of the Marker lines
     *
     * @param color
     */
    public void setMarkerColor(Color color) {
        MARKER_COLOR = color;
    }

    /**
     * Sets if the marker lines shall be dotted. The marker is dotted by default
     *
     * @param dotted
     */
    public void setMarkerDotted(boolean dotted) {
        if (dotted) {
            MARKER_DOTTED = new float[]{10.0f, 6.0f};
        } else {
            MARKER_DOTTED = new float[]{1.0f, 1.0f};
        }
    }

    /**
     * Displays different kind of information depending on the mode.
     * <p>Mode 1 - Show serie name
     * <p>Mode 2 - Show serie name & y value
     * <p>Mode 3 - Show serie name & x y vales
     * <p>Mode 4 - Show serie name & both real & unreal x y values
     *
     * @param mode
     */
    public void setMarkerInfo(int mode) {
        DRAW_MARKER_INFO = mode;
    }

    /**
     * If set to true the marker is invisible when not pointing on a point. If
     * false it stays drawn on the last pointed point
     *
     * @param set
     */
    public void setMarkerAutoReset(boolean set) {
        this.AUTO_RESET_MARKER = set;
    }

    //=======================================================================
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint background
        super.setBackground(BACKGROUND_COLOR);


        if (DRAW_MARKER) {
            drawMarker(g);
        }

        if (SCALE_XY_AXIS) {
            scaleOfXYAxis(g);
        }

        drawLines(g);

        drawPointsFixedSize(g);
    }

    /**
     * Handles the marker drawing
     *
     * @param g
     */
    private void drawMarker(Graphics g) {
        if (MARKER_POINT == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;

        ORDINARY_STROKE = (BasicStroke) g2.getStroke();

        g2.setStroke(MARKER_STROKE);
        g2.setPaint(MARKER_COLOR);
        //=====================
        MARKER_X = MARKER_POINT.x;
        MARKER_Y = MARKER_POINT.y;
        //===================== DRAW MARKER======================================
        if (MARKER_POINT.getDrawMarker() || AUTO_RESET_MARKER == false) {
            g2.drawLine(MARKER_X, 0, MARKER_X, getHeight()); // X
            g2.drawLine(0, MARKER_Y, getWidth(), MARKER_Y); // Y

            //=====================DRAW MARKER INFO===================================

            if (DRAW_MARKER_INFO == 1) {
                g2.drawString(MARKER_POINT.getSerieName(), MARKER_X + 10, MARKER_Y + 20);
            } else if (DRAW_MARKER_INFO == 2) {
                g2.drawString(MARKER_POINT.getSerieName() + " | " + MARKER_POINT.y_Real_display, MARKER_X + 10, MARKER_Y + 20);
            } else if (DRAW_MARKER_INFO == 3) {
                g2.drawString(MARKER_POINT.getSerieName() + " | y: " + MARKER_POINT.y_Real_display
                        + " | x: " + MARKER_POINT.x_Real, MARKER_X + 10, MARKER_Y + 20);
            } else if (DRAW_MARKER_INFO == 4) {
                g2.drawString(MARKER_POINT.getSerieName() + " | y: " + MARKER_POINT.y_Real_display
                        + " | x: " + MARKER_POINT.x_Real + " | y2: " + MARKER_POINT.y + " | x2: " + MARKER_POINT.x,
                        MARKER_X + 10, MARKER_Y + 20);
            }
        }

        //Reset to ordinary stroke 
        g2.setStroke(ORDINARY_STROKE);
    }

    /**
     * A very important method for scaling of x & y axises
     *
     * @param g
     */
    private void scaleOfXYAxis(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        ORDINARY_STROKE = (BasicStroke) g2.getStroke();
        g2.setStroke(GRID_STROKE);

        if (ONE_UNIT_Y < 1.1 || getHeight() < 50) {
            return;
        }

        //=================================X-axis scaling=========================

        if (SCALE_X_AXIS) {
            int j = 0; // step identifier

            int vv = (int) (X_MAX);
            if (vv > 100000 && vv < 1000000) {
                j = 10000;
            } else if (vv > 10000 && vv < 100000) {
                j = 1000;
            } else if (vv > 1000 && vv < 10000) {
                j = 100;
            } else if (vv > 100 && vv < 1000) {
                j = 50;
            } else if (vv > 10 && vv < 100) {
                j = 5;
            } else {
                j = 1;
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
                        g2.drawString("" + (j * m), i - 3, (int) (getHeight() - 5 * COEFF_SMALL_GRID) - 3);
                        g2.drawRect(i, (int) (getHeight() - 5 * COEFF_SMALL_GRID), 1, (int) (5 * COEFF_SMALL_GRID));
                        m++;
                    }

                }
            }
        }



        //==================================Y - axis scaling========================

        if (SCALE_Y_AXIS) {
            //Nr of ONE_UNIT_Y per getHeight. Note that Y_MAX is not the same
            //but is the highest point in graph expressed in ONE_UNIT_Y
            double max_y_units = getHeight() / ONE_UNIT_Y;

            //Max nr of unreal points in the graph area,
            //unreal points is the values that should be displayed
            //but not the real pixels on graph
            double max_unreal_points = max_y_units / ALL_SERIES_COEFF;

            // how many unreal points there is in one real point/pixel
            double unreal_points_per_real = max_unreal_points / getHeight();

            int jj = 0; // step identifier

            int vvv = (int) (Y_MAX / ALL_SERIES_COEFF);
            if (vvv > 100000 && vvv < 1000000) {
                jj = 10000;
            } else if (vvv > 10000 && vvv < 100000) {
                jj = 1000;
            } else if (vvv > 1000 && vvv < 10000) {
                jj = 500;
            } else if (vvv > 100 && vvv < 1000) {
                jj = 50;
            } else if (vvv > 10 && vvv < 100) {
                jj = 5;
            } else {
                jj = 1;
            }

            int mm = 1; // frequency regulator
            int fix_coef_2 = 5; // this coef is for fixing the scaling of y-axis
            for (int i = 1; i < getHeight(); i++) {
                double x = (double) Math.round(i * unreal_points_per_real);
                if (x > (jj * mm) && x < (jj * mm) + unreal_points_per_real + 1) {
                    if (SHOW_GRID) {
                        g2.setPaint(GRID_COLOR);
                        g2.drawRect(0, (getHeight() - (i - fix_coef_2)), getWidth(), 1);
                        //
                        if (SHOW_GRID_AND_SCALE) {
                            g2.drawString("" + jj * mm, (int) (1 * COEFF_SMALL_GRID), getHeight() - (i - fix_coef_2 + 5));
                        }
                        //
                        mm++;
                    } else {
                        g2.setPaint(GRID_COLOR);
                        g2.drawString("" + jj * mm, (int) (7 * COEFF_SMALL_GRID), getHeight() - (i - fix_coef_2 - 3));
                        g2.drawRect(0, (getHeight() - (i - fix_coef_2)), (int) (5 * COEFF_SMALL_GRID), 1);
                        mm++;
                    }

                }
            }
        }
        g2.setStroke(ORDINARY_STROKE);
    }

    /**
     * This one is better to use when the lines are also displayed.
     *
     * @param g
     */
    private void drawPointsFixedSize(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //===================================
        for (MySerie serie : SERIES) {
            for (MyPoint point : serie.getSerie()) {
                if (serie.drawPoints()) {
                    point.drawPoint(g, serie.getPointColor());
                }
            }
        }
    }

    /**
     * Draws the lines between the points
     *
     * @param g
     */
    private void drawLines(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setStroke(new BasicStroke(3));

        try {
            for (MySerie serie : SERIES) {
                ArrayList<MyPoint> act_serie = serie.getSerie();
                g2.setPaint(serie.getCurveColor());
                g2.setStroke(serie.getLineRenderer()); // adjust appereance
                for (int i = 0; i < act_serie.size() - 1 && serie.drawLines(); i++) {
                    if (act_serie.get(i + 1) != null) {
                        g2.drawLine(act_serie.get(i).x, act_serie.get(i).y, act_serie.get(i + 1).x, act_serie.get(i + 1).y);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("" + ex);
        }
    }

    /**
     * Adds a serie to the set of series with duplicity check. The new series
     * name must differ from the existing ones
     *
     * @param serieToAdd
     * @return
     */
    public boolean addSerie(MySerie serieToAdd) {
        if (SERIES.isEmpty()) {
            serieToAdd.setMyGraphXY(this);
            SERIES.add(serieToAdd);
            return true;
        }
        //=====================================================================
        for (MySerie mySerie : SERIES) {
            if (mySerie.nameEquals(serieToAdd.getName()) == false) {
                serieToAdd.setMyGraphXY(this);
                SERIES.add(serieToAdd);
                return true;
            } else {
                System.out.println("The Dataset contains allready a serie named: " + serieToAdd.getName());
                return false;
            }
        }
        return false;
    }

    /**
     * Add a point to a serie with index x
     *
     * @param point
     * @param serie_index
     * @deprecated
     */
    public synchronized void addPointToSerie(MyPoint point, int serie_index) {
        PANEL_AREA_PREV = getWidth() * getHeight();
        defineMaxForXYAxis(point);
        SERIES.get(serie_index).getSerie().add(point);
        recalc();
        notify();
    }

    /**
     * Deletes all the points from the graph for the given serie with serie name
     * X.
     *
     * @param serie_name
     */
    public void deleteAllPointsFromSerie(String serie_name) {
        for (MySerie serie : SERIES) {
            if (serie.nameEquals(serie_name)) {
                serie.deleteAllPoints();
            }
        }
    }

    /**
     *
     */
    public void deleteAllPointsFromAllSeries() {
        for (MySerie serie : SERIES) {
            serie.deleteAllPoints();
        }
        this.X_MAX = 1; //!!!! Very important, this makes the scaling right!!!
        this.Y_MAX = 1;//!!!! Very important, this makes the scaling right!!!
    }

    public synchronized void addDataSetToSerie(double[] values, String serie_name) {
        for (double value : values) {
            addPointToSerie(value, serie_name);
        }
    }

    public synchronized void addPointToSerie(Object value, String serie_name) {
        PANEL_AREA_PREV = getWidth() * getHeight();
        MyPoint point = HelpA.definePoint(value);

        for (MySerie serie : SERIES) {
            if (serie.nameEquals(serie_name)) {
                serie.addPoint(point);
                waitForPanelHeightIsInitialized(); //Must be!!!!
                serie.checkRecalc(point, getHeight());
                defineMaxForXYAxis(point);//!!!
                //============================
                point.addMouseMotionListener(this); //ading the listener to the instance of MyPoint
                point.addMouseListener(this);
                add(point); // Adds the point component to the graph panel component
            }
        }
        notify();
    }

    /**
     * Gets the serie by name
     *
     * @param serie_name - the name of the required serie
     * @return
     */
    public MySerie getSerieNamedX(String serie_name) {
        for (MySerie serie : SERIES) {
            if (serie.nameEquals(serie_name)) {
                return serie;
            }
        }
        return null;
    }

    /**
     * You must wait untill the panel hight is initialize otherwise it come
     * failures in rescaling & others..
     */
    private synchronized void waitForPanelHeightIsInitialized() {
        while (getHeight() < 50) {
            try {
                wait(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyGraphXY.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Defines the max value for the x & y axis
     *
     * @param point
     */
    private void defineMaxForXYAxis(MyPoint point) {
        if (point.x_Scaled > X_MAX / 1.05) {
            X_MAX = (int) ((point.x_Scaled));//1.2 Note this is important value!
            X_MAX *= 1.05;
        }
        if (point.y_Scaled > Y_MAX / 1.2) {
            Y_MAX = (point.y_Scaled);
            Y_MAX *= 1.2;
        }
    }

    /**
     * Extremly important method which recalculates how much one unit of x or y
     * is after the rescaling
     */
    private void countUnit() {
        if (getHeight() < 100) {
            return;
        }
        ONE_UNIT_X = (double) (getWidth() / X_MAX);
        ONE_UNIT_Y = Math.round(getHeight() / Y_MAX);
    }

    /**
     * Recalculating of coordinates which are used for the drawing of the graph
     */
    private void recalc() {

        for (int x = 0; x < SERIES.size(); x++) {
            //
            ArrayList<MyPoint> act_serie = SERIES.get(x).getSerie();
            //
            for (int i = 0; i < act_serie.size(); i++) {

                int x_static = act_serie.get(i).x_Scaled;
                int y_static = act_serie.get(i).y_Scaled;

//                if (x_static == 1) {
//                    System.out.println("");
//                }

                countUnit();

                act_serie.get(i).x = (int) (Math.round(ONE_UNIT_X * x_static));
                act_serie.get(i).y = (int) Math.round((getHeight() - (ONE_UNIT_Y * y_static)));
            }
        }
        //
        //
//        System.out.println("one_un_x = " + ONE_UNIT_X);
//        System.out.println("one_un_y = " + ONE_UNIT_Y);
        //
        //
        repaint();
        revalidate();
    }

    public void validateFromOutside() {
        validate();
    }

    /**
     * Highlight a point when the mouse cursor points on it this method is now
     * event based, so no calculations are done due to the an instance of
     * MyPoint is now a instance of JComponent (inherits JComponent)
     *
     * @param e
     */
    private void highLightPointOnMouseMovement(MouseEvent e) {
        if (SEARCH_POINT_POS) { // if visual option is on 
            if (e.getSource() instanceof MyPoint) {
                //
                if (MARKER_POINT != null) {
                    PointHighLighter.unhighLightAllPointsAtIndex(MARKER_POINT.getPointIndex());
                }
                //
                MARKER_POINT = (MyPoint) e.getSource();
                //
                PointHighLighter.highLightAllPointsAtIndex(MARKER_POINT.getPointIndex());
                //
                repaint();
            } else {
                if (MARKER_POINT != null) {
                    //
                    PointHighLighter.unhighLightAllPointsAtIndex(MARKER_POINT.getPointIndex());
                    //
                    repaint();
                }
            }
        }
    }
    //========================================================

    @Override
    public void mouseDragged(MouseEvent e) {
//        System.out.print("");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        highLightPointOnMouseMovement(e);
    }
    //===========================================================

    @Override
    public void componentResized(ComponentEvent e) {
        recalc();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
//        System.out.print("");
    }

    @Override
    public void componentShown(ComponentEvent e) {
//        System.out.print("");
    }

    @Override
    public void componentHidden(ComponentEvent e) {
//        System.out.print("");
    }

    //===============================================================
    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() instanceof MyPoint && SHOW_POP_UP && e.getButton() == 1) {
            popup.removeAll();

            //==========================Batch Info displaying==================
            MARKER_POINT.addPointInfo("serie", MARKER_POINT.getSerieName());
            MARKER_POINT.addPointInfo("y", "" + MARKER_POINT.y_Real_display);
            MARKER_POINT.addPointInfo("x", "" + MARKER_POINT.x_Real);
            //
            HashMap<String, String> b_info_map = MARKER_POINT.getBatchInfo();
            //
            Set set = b_info_map.keySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                try {
                    String key = (String) it.next();
                    String value = (String) b_info_map.get(key);
                    popup.add(new MenuItem(key + ": " + value));
                } catch (NoSuchElementException ex1) {
                    System.out.println("" + ex1);
                }
            }

            //=================================================================
            popup.show(this, MARKER_POINT.x + 5, MARKER_POINT.y + 5);
            //
        } else if (e.getSource() instanceof MyPoint && e.getButton() == 3 && PointHighLighter.isFixed(MARKER_POINT) == false) {
            popup.removeAll();
            popup.add(menu_item_fix_point);
            popup.show(this, MARKER_POINT.x + 5, MARKER_POINT.y + 5);
        } else if (e.getSource() instanceof MyPoint && e.getButton() == 3 && PointHighLighter.isFixed(MARKER_POINT)) {
            popup.removeAll();
            popup.add(menu_item_unfix_point);
            popup.show(this, MARKER_POINT.x + 5, MARKER_POINT.y + 5);
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == menu_item_fix_point) { // Fix point
            fixPoint();
        } else if (ae.getSource() == menu_item_unfix_point) {//Unfix point
            unfixPoint();
        }
    }

    private void fixPoint() {
        PointHighLighter.highLightAllPointsAtIndex(MARKER_POINT.getPointIndex());
        PointHighLighter.setPointFixed(MARKER_POINT);
        repaint();
    }

    private void unfixPoint() {
        PointHighLighter.setPointUnfixed(MARKER_POINT);
        PointHighLighter.unhighLightAllPointsAtIndex(MARKER_POINT.getPointIndex());
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    //=======================================================
    /**
     * Works like a kind of repaint() method, as the repaint not allways works
     */
    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    wait();
//                    wait(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyGraphXY.class.getName()).log(Level.SEVERE, null, ex);
                }
                setSize(getWidth() - 1, getHeight() - 1);
                setSize(getWidth() + 1, getHeight() + 1);
//                System.out.println("sizeSet() done");
            }
        }
    }
    //=================================================================

    /**
     * Dont work so well with lines visible the points are rescaled depending on
     * the window size
     *
     * @param g
     * @deprecated
     */
    private void drawPointVariousPointSize(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //====================================================
        for (MySerie serie : SERIES) {
            for (MyPoint point : serie.getSerie()) {
                point.drawPoint(g, (getWidth() * getHeight()) / PANEL_AREA_PREV, serie.getPointColor());
            }
        }
    }

    /**
     * Returns the object on which the mouse points
     *
     * @param x
     * @param y
     * @return
     * @deprecated
     */
    private Object getObjectByMouseCursor(int x, int y) {
        for (MySerie serie : SERIES) {
            ArrayList<MyPoint> act_serie = serie.getSerie();
            for (int i = 0; i < act_serie.size(); i++) {
                if (act_serie.get(i).checkIfMousePointsOnPoint(x, y)) {
                    return act_serie.get(i);
                }
            }
        }
        return this;
    }

    /**
     * Highlights the point at which the mouse points an interesting but not
     * optimal method for finding a oint in the graph area
     *
     * @param x
     * @param y
     * @deprecated
     */
    private void highLightPointOnMouse(int x, int y) {
        for (MySerie serie : SERIES) {
            ArrayList<MyPoint> act_serie = serie.getSerie();
            for (int i = 0; i < act_serie.size(); i++) {
                if (act_serie.get(i).checkIfMousePointsOnPoint(x, y)) {
                    act_serie.get(i).setHighlight();
                    //============================
                    MARKER_POINT = act_serie.get(i);
                } else {
                    act_serie.get(i).setHighlightOff();
                }
            }
        }
        repaint();
    }

    public void getParentContainer() {
        JPanel parent = (JPanel) this.getParent().getParent();
//        System.out.println("" + parent.toString());
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("test");
        jf.setSize(new Dimension(300, 300));

        MyGraphContainer gr_container = new MyGraphContainer("Graph");
        //
        MyGraphXY gp = new MyGraphXY();
        gp.setDrawGrid(false);
        gp.setGridColor(Color.BLACK);
        gp.setShowPopUp(true);
        gp.setHighLightPoints(true);
        gp.setBackgroundColor(Color.WHITE);
        gp.setScaleXYaxis(true);
        gp.setDrawMarker(false);
//        gp.setMarkerInfo(4);
        //
        gr_container.addGraph(gp);
//        gp.getParentContainer();
        //
        jf.setLayout(new GridLayout(1, 0));
        jf.add(gr_container.getGraph());
        jf.setVisible(true);


//        MySerie speed_curve = new MySerie("speed", true, Color.green, true, Color.BLUE);
//        speed_curve.setLineDotted();
//        speed_curve.setLineThickness(2.0f);
//        speed_curve.setPointThickness(1);
//        speed_curve.setCurveColor(Color.MAGENTA);
//        speed_curve.setPointColor(Color.red);
////        speed_curve.setDrawPoints(false);
//        gp.addSerie(speed_curve);
//        for (int i = 1; i < 70; i++) {
//            if (i == 9) {
//                System.out.println("");
//            }
//            gp.addPointToSerie(new MyPoint((int) ((Math.random() * 50) + 1)), "speed");
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(MyGraphXY.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        gp.deleteAllPointsFromSerie("speed");
//        System.out.println("");
//'''''''''''
//        System.out.println("AAA coef_speed = " + speed_curve.COEFF);

        MySerie torq_curve = new MySerie("torque", true, Color.RED, true, Color.YELLOW);
        torq_curve.setOverallScale(false);
        torq_curve.setPointThickness(1);
        torq_curve.setPointHighLightColor(Color.BLACK);
        gp.addSerie(torq_curve);
        for (int i = 1; i < 1000; i++) {

            int random = (int) ((Math.random() * 5000) + 1);
            gp.addPointToSerie(new MyPoint(random, "" + random), "torque");

//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(MyGraphXY.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        System.out.println("AAA coef_torq = " + torq_curve.COEFF);

    }
}
