/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XY_BUH_INVOICE;

import XYG_BASIC.HelpAA;
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
public class Run_A {

    public static void main(String[] args) {
        //
        String dateNow = HelpAA.get_date_yyyy_MM_dd();
        String dateFormat = "yyyy-DD-mm";
        //
        Buh_Invoice_Main__IF bim = null;
        //
        XyGraph_BuhInvoice xghm = new XyGraph_BuhInvoice("test","total_ink_moms", new MyGraphXY_BuhInvoice(bim),
                MyGraphContainer.DISPLAY_MODE_FULL_SCREEN, dateNow, dateFormat);
        //
        System.out.println("Width: " + xghm.getGraph().getHeight() + " / " + xghm.getGraph().getWidth());
        //
        JFrame jf = new JFrame(xghm.getTitle()); //****
        jf.setSize(new Dimension(800, 400));
        jf.setLayout(new GridLayout(1, 0));
        jf.add(xghm.getGraph()); //*****
        System.out.println("Width: " + xghm.getGraph().getHeight() + " / " + xghm.getGraph().getWidth());
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        new Thread(() -> {
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            //
            HashMap<String, String> map_1 = new HashMap<String, String>();
            map_1.put("fakturanr", "1");
            map_1.put("fakturanr_alt", "0");
            map_1.put("namn", "Telenor");
            map_1.put("is_person", "0");
            map_1.put("rutavdrag", "0");
            map_1.put("total_ink_moms", "8242.5");
            map_1.put("fakturadatum", "2021-03-13");
            map_1.put("forfallodatum", "2021-04-12");
            map_1.put("fakturatyp", "0");
            map_1.put("betald", "0");
            map_1.put("makulerad", "0");
            map_1.put("omvant_skatt", "1");
            map_1.put("sent_with_email", "0");
            map_1.put("is_printed", "1");
            map_1.put("kund_kategori", "EU EUR");
            //
            HashMap<String, String> map_2 = new HashMap<String, String>();
            map_2.put("fakturanr", "2");
            map_2.put("fakturanr_alt", "0");
            map_2.put("namn", "Vodafone");
            map_2.put("is_person", "0");
            map_2.put("rutavdrag", "0");
            map_2.put("total_ink_moms", "3999.5");
            map_2.put("fakturadatum", "2021-04-25");
            map_2.put("forfallodatum", "2021-05-25");
            map_2.put("fakturatyp", "1");
            map_2.put("betald", "0");
            map_2.put("makulerad", "1");
            map_2.put("omvant_skatt", "0");
            map_2.put("sent_with_email", "0");
            map_2.put("is_printed", "0");
            map_2.put("kund_kategori", "A");
            //
            HashMap<String, String> map_3 = new HashMap<String, String>();
            map_3.put("fakturanr", "3");
            map_3.put("fakturanr_alt", "0");
            map_3.put("is_person", "0");
            map_3.put("rutavdrag", "0");
            map_3.put("total_ink_moms", "1489");
            map_3.put("fakturadatum", "2021-05-25");
            map_3.put("forfallodatum", "2021-06-25");
            map_3.put("fakturatyp", "0");
            map_3.put("betald", "0");
            map_3.put("makulerad", "0");
            map_3.put("omvant_skatt", "1");
            map_3.put("sent_with_email", "0");
            map_3.put("is_printed", "0");
            map_3.put("kund_kategori", "A");
            //
            HashMap<String, String> map_4 = new HashMap<String, String>();
            map_4.put("fakturanr", "4");
            map_4.put("fakturanr_alt", "10015");
            map_4.put("namn", "Henrik Johansson");
            map_4.put("is_person", "1");
            map_4.put("rutavdrag", "0");
            map_4.put("total_ink_moms", "5899");
            map_4.put("fakturadatum", "2021-06-25");
            map_4.put("forfallodatum", "2021-07-25");
            map_4.put("fakturatyp", "0");
            map_4.put("betald", "1");
            map_4.put("makulerad", "0");
            map_4.put("omvant_skatt", "0");
            map_4.put("sent_with_email", "0");
            map_4.put("is_printed", "0");
            map_4.put("important_komment", "SPCS 465");
            map_4.put("kund_kategori", "A");
            //
            list.add(map_4);
            list.add(map_3);
            list.add(map_2);
            list.add(map_1);
            //
            java.awt.EventQueue.invokeLater(() -> {
                xghm.addData(list, new String[]{"fakturadatum", "forfallodatum"});
            });
            //
        }).start();
        //
        //
//        HelpA.addMouseListenerToAllComponentsOfComponent(jf.getRootPane()); 
        //
    }
}
