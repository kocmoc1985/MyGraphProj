/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XY_RUN;

import XYG_BASIC.MyGraphContainer;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author KOCMOC
 */
public class Run_B {

    public static void main(String[] args) {
        //
        XyGraph_BuhInvoice xghm = new XyGraph_BuhInvoice("test", MyGraphContainer.DISPLAY_MODE_FULL_SCREEN);
        //
        JFrame jf = new JFrame(xghm.getTitle()); //****
        jf.setSize(new Dimension(800, 400));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(xghm.getGraph()); //*****
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        ArrayList<HashMap<String,String>>list = new ArrayList<HashMap<String,String>>();
        //
        HashMap<String,String>map_1 = new HashMap<String, String>();
        map_1.put("fakturanr", "1");
        map_1.put("total_ink_moms", "8242.5");
        map_1.put("fakturadatum", "2021-04-13");
        map_1.put("forfallodatum", "2021-05-13");
        map_1.put("fakturatyp", "0");
        map_1.put("betald", "1");
        //
        HashMap<String,String>map_2 = new HashMap<String, String>();
        map_2.put("fakturanr", "2");
        map_2.put("total_ink_moms", "3999.5");
        map_2.put("fakturadatum", "2021-04-25");
        map_2.put("forfallodatum", "2021-05-25");
        map_2.put("fakturatyp", "1");
        map_2.put("betald", "0");
        //
        list.add(map_1);
        list.add(map_2);
        //
        xghm.addData(list, new String[]{"fakturadatum","forfallodatum"});
        //
//        HelpA.addMouseListenerToAllComponentsOfComponent(jf.getRootPane()); 
        //
    }
}