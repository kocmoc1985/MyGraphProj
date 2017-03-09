/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Needs the class MyRamPosPlot
 *
 * @author Administrator
 */
public class MyGraphContainer implements ComponentListener {

    private JComponent GRAPH;
    private JPanel head = new JPanel(new GridBagLayout()); //GridBagLayout works like "CenterLayout()"
    private JPanel foot = new JPanel(new GridBagLayout());
    private JPanel body = new JPanel(new GridBagLayout());
    private JLabel head_label = new JLabel("Ram position");
    private JPanel PARENT = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));//new GridLayout(2,0);
    private static double HEAD_COEFF_H;
    private static double HEAD_COEFF_W;
    private static double GRAPH_COEFF_H;
    private static double GRAPH_COEFF_W;
    
    private Font TITLE_FONT = new Font("Serif", Font.BOLD, 20);
    private Font FOOT_FONT = Font.getFont(Font.SANS_SERIF);
    private Border PARENT_BORDER;
    private Color HEAD_COLOR = new Color(240, 240, 240);
    private Color FOOT_COLOR = HEAD_COLOR;
    private Color CHART_PLOT_COLOR = HEAD_COLOR;
    //===========================================
    private boolean DISPLAY_HEAD = true;
    private boolean DISPLAY_FOOT = true;
    //===========================================
    public static final int DISPLAY_MODE_FULL_SCREEN = 1;
    public static final int DISPLAY_MODE_FOOT_DISABLED = 2;
    public static final int DISPLAY_MODE_FOOT_HEAD_ENABLED = 3;

    /**
     * Creates a graph with title component by default
     *
     * @param head_label
     */
    public MyGraphContainer(String head_label) {
        this.head_label = new JLabel(head_label);
        // Display mode 2 by default
        this.DISPLAY_FOOT = false;
        HEAD_COEFF_H = 0.1;
        HEAD_COEFF_W = 0.99;
        GRAPH_COEFF_H = (0.99 - HEAD_COEFF_H);
        GRAPH_COEFF_W = 0.99;
        //NOTE 0.99 is to have space to draw a border
    }

    /**
     * Creates 3 different views of the graph
     *
     * @param head_label
     * @param displayMode - <p>displayMode 1 -> show graph full screen;
     * <p>displayMode 2 -> show graph & title full screen
     * <p>displayMode 3 -> show graph, title & foot
     */
    public MyGraphContainer(String head_label, int displayMode) {
        if (displayMode == DISPLAY_MODE_FULL_SCREEN) {// graph fullScreen mode
            this.DISPLAY_HEAD = false;
            this.DISPLAY_FOOT = false;
            GRAPH_COEFF_H = 0.99;
            GRAPH_COEFF_W = 0.99;
        } else if (displayMode == DISPLAY_MODE_FOOT_DISABLED) {
            this.DISPLAY_FOOT = false;
            HEAD_COEFF_H = 0.1;
            HEAD_COEFF_W = 0.99;
            GRAPH_COEFF_H = (0.99 - HEAD_COEFF_H);
            GRAPH_COEFF_W = 0.99;
        } else if (displayMode == DISPLAY_MODE_FOOT_HEAD_ENABLED) {
            HEAD_COEFF_H = 0.1;
            HEAD_COEFF_W = 0.99;
            GRAPH_COEFF_W = 0.99;
            GRAPH_COEFF_H = 0.99 - (HEAD_COEFF_H * 2);
        }
        this.head_label = new JLabel(head_label);
    }

    private void defineComponentNames() {
        PARENT.setName("PARENT");
        body.setName("body");
        GRAPH.setName("Graph");
    }
    
    public void addLegend(JComponent le){
        le.setBackground(FOOT_COLOR);
        foot.add(le);
    }

    /**
     * Add the graph component to the graph container
     *
     * @param graph
     */
    public void addGraph(JPanel graph) {
        GRAPH = graph;
        body.add(GRAPH);
        //
        PARENT.addComponentListener(this);
        //
        if (DISPLAY_HEAD) {
            head.setBackground(HEAD_COLOR);
            head_label.setFont(TITLE_FONT);
            head.add(head_label);
        }
        //
        if (DISPLAY_FOOT) {
            foot.setBackground(FOOT_COLOR);
            foot.setLayout(new GridLayout(1, 20));
        }
        //
        PARENT.setBackground(CHART_PLOT_COLOR);
        //========================
        if (DISPLAY_HEAD) {
            PARENT.add(head);
        }
        //
        PARENT.add(body); // the body contains graph
        //
        if (DISPLAY_FOOT) {
            PARENT.add(foot);
        }
        //============
        PARENT.revalidate();
        //
        defineComponentNames();
    }

    /**
     * Set a border for the whole MyGraphContainer component
     *
     * @param border
     */
    public void setBorder(Border border) {
        PARENT_BORDER = border;
        if (PARENT_BORDER != null) {
            PARENT.setBorder(PARENT_BORDER);
        }
    }

    /**
     * Sets the head width. if you need 15% the parameter should be = 0.15.
     *
     * @param percent
     */
    public void setHeadHeight(double percent) {
        HEAD_COEFF_H = percent;
        if (DISPLAY_FOOT) {
            GRAPH_COEFF_H = 0.99 - (HEAD_COEFF_H * 2);
        } else {
            GRAPH_COEFF_H = 0.99 - HEAD_COEFF_H;
        }
    }
    
    public void setBorderHeadAndFootComponents(Border b){
        head.setBorder(b);
        foot.setBorder(b);
    }

    /**
     *
     * @param font
     */
    public void setTitleFont(Font font) {
        TITLE_FONT = font;
    }

    /**
     * Set the title size
     *
     * @param size
     * @param bold
     */
    public void setTitleSize(int size, boolean bold) {
        if (bold) {
            TITLE_FONT = new Font("Serif", Font.BOLD, size);
        } else {
            TITLE_FONT = new Font("Serif", Font.PLAIN, size);
        }
    }

    /**
     * Set the color of the title
     *
     * @param c
     */
    public void setTitleColor(Color c) {
        head_label.setForeground(c);
    }

    /**
     * Sets the bgcolor of the 'head' component which holds the title
     *
     * @param color
     */
    public void setHeadColor(Color color) {
        HEAD_COLOR = color;
        refresh();
    }

    /**
     * Sets the bgcolor of the 'foot' component which holds various information
     *
     * @param color
     */
    public void setFootColor(Color color) {
        FOOT_COLOR = color;
//        refresh();
    }

    /**
     * Gets this component with the Graph inside
     *
     * @return
     */
    public JComponent getGraph() {
        return this.PARENT;
    }

    /**
     * Note use revalidate(), repaint() doesn't fit!!!
     */
    private void refresh() {
        PARENT.revalidate();
        head.revalidate();
        body.revalidate();
    }

    private void recalc() {
        if (PARENT != null && head != null && GRAPH != null) {
            head.setPreferredSize(new Dimension((int) (PARENT.getWidth() * HEAD_COEFF_W), (int) (PARENT.getHeight() * HEAD_COEFF_H)));
            foot.setPreferredSize(new Dimension((int) (PARENT.getWidth() * HEAD_COEFF_W), (int) (PARENT.getHeight() * HEAD_COEFF_H)));
            GRAPH.setPreferredSize(new Dimension((int) (PARENT.getWidth() * GRAPH_COEFF_W), (int) (PARENT.getHeight() * GRAPH_COEFF_H)));
            refresh();
        }
    }

//    public static void main(String[] args) {
//        new MyRamPosGraph(null,null);
//    }
    @Override
    public void componentResized(ComponentEvent e) {
        recalc();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
//        System.out.println("");
    }

    @Override
    public void componentShown(ComponentEvent e) {
//        System.out.println("");
    }

    @Override
    public void componentHidden(ComponentEvent e) {
//        System.out.println("");
    }

    /**
     *
     */
   
}
