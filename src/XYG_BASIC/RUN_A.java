/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import XYG_BASIC.XyGraph_Basic;
import XYG_BASIC.MyGraphContainer;
import XYG_BASIC.MyGraphXY;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author KOCMOC
 */
public class RUN_A {

    public static void main(String[] args) {
        //
        XyGraph_Basic xyGraph_A = new XyGraph_Basic("test", new MyGraphXY(), MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        JFrame jf = new JFrame(xyGraph_A.getTitle()); //****
        jf.setSize(new Dimension(800, 400));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(xyGraph_A.getGraph()); //*****
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
//        double[] dataSet = {3.5,1.7,2.3,1.3,4.5,7.8,6.7,8.7};
//        double[] dataSet = {0.7,1.3,3.2,0.7,4.8};
//        double[] dataSet = {0.35,0.24,0.78,0.47,0.52};
        //
        new Thread(() -> {
            //
            double[] dataSet = {1201.2, 1159.5, 1325, 1588, 1100, 1265, 1333, 2200, 2300, 2159, 2789, 1565, 1898, 358, 965, 879, 253, 96, 1547, 1625, 1200};
            //
            java.awt.EventQueue.invokeLater(() -> {
                xyGraph_A.addDataSetBySerie(dataSet, "test");
            });
            //
        }).start();
        //
//        HelpA.addMouseListenerToAllComponentsOfComponent(jf.getRootPane()); 
        //
    }
}
