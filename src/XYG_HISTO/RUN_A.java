/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_HISTO;

import XYG_BASIC.MyGraphContainer;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import sql.SQL_Q;

/**
 *
 * @author MCREMOTE
 */
public class RUN_A implements BarGraphListener {

    private BasicGraphListener gg;
    private MyGraphXY_HG mgxyhm;
    private final XyGraph_M xygraph = new XyGraph_M("mooney", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);

    public RUN_A() {
        init();
    }

    public static void main(String[] args) {
        RUN_A run_a = new RUN_A();
    }

    private void init() {
        //
        this.mgxyhm = new MyGraphXY_HG();
        this.mgxyhm.addBarGraphListener(this);
        this.gg = new HistogramGraph("Test HG", mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        xygraph.setGistoGraph(gg);
        //
        JFrame jf = new JFrame(xygraph.getTitle()); //****
        jf.setSize(new Dimension(800, 400));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(gg.getGraph()); //*****
        System.out.println("Width: " + gg.getGraph().getHeight() + " / " + gg.getGraph().getWidth());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        double[] values = new double[]{10, 15, 11, 14, 17, 9};
        gg.addData_B(values);
        //
    }

    @Override
    public void barGraphHoverEvent(MouseEvent e, MyPoint_HG point) {
    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent e) {
    }

}
