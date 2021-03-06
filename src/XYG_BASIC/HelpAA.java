/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 *
 * @author KOCMOC
 */
public class HelpAA {

    public static void err_output_to_file() {
        //Write error stream to a file
        create_dir_if_missing("err_output");
        try {
            String err_file = "err_" + get_date_time() + ".txt";
            String output_path = "err_output/" + err_file;
            PrintStream out = new PrintStream(new FileOutputStream(output_path));
            System.setErr(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelpAA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void create_dir_if_missing(String path_and_folder_name) {
        File f = new File(path_and_folder_name);
        if (f.exists() == false) {
            f.mkdir();
        }
    }

    public static String get_date_time() {
        DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }
    
    public static String get_date_yyyy_MM_dd() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }
    
    public static boolean compareDates(String date1, String date_format1, String date2, String date_format2) {
        //
        long ms_date1 = dateToMillisConverter3(date1, date_format1);
        long ms_date2 = dateToMillisConverter3(date2, date_format2);
        //
        if (ms_date1 > ms_date2) {
            return true;
        } else {
            return false;
        }
    }
    
    public static synchronized long dateToMillisConverter3(String date, String date_format) {
        DateFormat formatter = new SimpleDateFormat(date_format);
        try {
            return formatter.parse(date).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(HelpAA.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

      //#.#
     public static double roundDouble(double number,String format) {
        DecimalFormat twoDForm = new DecimalFormat(format);
        DecimalFormatSymbols s = DecimalFormatSymbols.getInstance();
        s.setDecimalSeparator('.');
        twoDForm.setDecimalFormatSymbols(s);
        return Double.valueOf(twoDForm.format(number));
    }
     
    public static synchronized double roundDouble_(double number, String format) {
        return Double.parseDouble(String.format(format, number).replace(",", "."));
    } 
   

    public static int findCoeff(double value) {
        if (value <= 0) {
            return 1;
        }
        int temp = 1;
        for (int i = 0; i < 10; i++) {
            if ((1000000 - value) % 1 != 0) {
                value *= 10;
                temp *= 10;
            } else {
                break;
            }
        }
        return temp;
    }

    public static synchronized MyPoint definePoint(Object value) {
        MyPoint point;

        if (value instanceof MyPoint == false) {
            if (value instanceof Integer) {
                int val = (Integer) value;
                point = new MyPoint(val, val);
            } else {
                double val = (Double) value;
                point = new MyPoint(val, val);
            }
        } else {
            point = (MyPoint) value;
        }
        //
        return point;
    }

    private static Border PREV_BORDER;

    public static void addMouseListenerToAllComponentsOfComponent(JComponent c) {
        Component[] c_arr = c.getComponents();
        for (Component component : c_arr) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    String str = "SOURCE ELEM: " + me.getSource();
                    System.out.println(str);
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    if (me.getSource() instanceof JComponent) {
                        JComponent jc = (JComponent) me.getSource();
                        PREV_BORDER = jc.getBorder();
                        jc.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                    }
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    if (me.getSource() instanceof JComponent) {
                        JComponent jc = (JComponent) me.getSource();
                        jc.setBorder(PREV_BORDER);
                    }
                }
            });
            if (component instanceof JComponent) {
                addMouseListenerToAllComponentsOfComponent((JComponent) component);
            }
        }
    }

}
