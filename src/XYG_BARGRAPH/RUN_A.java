/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BARGRAPH;

import XYG_STATS.BarGraphListener;
import XYG_STATS.BasicGraphListener;
import XYG_STATS.XyGraph_M;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyPoint;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import other.StringDouble;

/**
 *
 * @author MCREMOTE
 */
public class RUN_A implements BarGraphListener {

    private BasicGraphListener gg;
    private MyGraphXY_BG mgxyhm;
    private final XyGraph_M xygraph = new XyGraph_M("Test BG-A", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);

    public RUN_A() {
        init();
    }

    public static void main(String[] args) {
        RUN_A run_a = new RUN_A();
    }

    private void init() {
        //
        this.mgxyhm = new MyGraphXY_BG("Total",":-");
        this.mgxyhm.addBarGraphListener(this);
        this.gg = new BARGraph("Test BG-B", mgxyhm, MyGraphContainer.DISPLAY_MODE_FULL_SCREEN); // MyGraphContainer.DISPLAY_MODE_FOOT_DISABLED
        //
        xygraph.setGraph(gg);
        //
        JFrame jf = new JFrame(xygraph.getTitle()); //****
        jf.setSize(new Dimension(800, 400));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(gg.getGraph()); //***** [#WAIT-FOR-HEIGHT#]
        System.out.println("Width: " + gg.getGraph().getHeight() + " / " + gg.getGraph().getWidth());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
//        double[] values = new double[]{10, 15, 11, 14, 17, 9, 79, 140, 55, 98, 14,0};
//        BARGraph barg = (BARGraph)gg;
//        barg.addData(values);
        //
        new Thread(() -> {
            //
            ArrayList<StringDouble> vl = new ArrayList<>();
            vl.add(new StringDouble("Jan", 47900));
            vl.add(new StringDouble("Feb", 69800));
            vl.add(new StringDouble("Mar", 36010));
            vl.add(new StringDouble("Apr", 52310));
            vl.add(new StringDouble("Maj", 89900));
            vl.add(new StringDouble("Jun", 58900));
            vl.add(new StringDouble("Juli", 78600));
            vl.add(new StringDouble("Aug", 71298));
            vl.add(new StringDouble("Sept", 36789));
            vl.add(new StringDouble("Okt", 98767));
            vl.add(new StringDouble("Nov", 43987));
            vl.add(new StringDouble("Dec", 55436));
            //
            java.awt.EventQueue.invokeLater(() -> {
                BARGraph barg = (BARGraph) gg;
                barg.addData(vl);
            });
            //
        }).start();
        //
        //
    }

    @Override
    public void barGraphHoverEvent(MouseEvent e, MyPoint point) {
        // See MCLabStats 
    }

    @Override
    public void barGraphHoverOutEvent(MouseEvent e) {
        // See MCLabStats
    }

}
