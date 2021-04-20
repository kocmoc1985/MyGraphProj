/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JComboBox;
import other.HelpA;

/**
 *
 * @author KOCMOC
 */
public class SQL_Q {

    public static  String PRIM_TABLE = loadFromProps();
    public static  String QUALITY = "Quality";
    public static  String ORDER = "order";
    public static  String BATCH = "BatchNo";
    public static  String TEST_CODE = "TestCode";
    public static  String TEST_NAME = "Name";
    public static  String LSL = "LSL";
    public static  String USL = "USL";
    public static  String TEST_DATE = "testdate";
    public static  String TEST_VALUE = "value";
    public static  String TEST_STATUS = "Status";
    //
//    public static final String PRIM_TABLE = "dbagb3.fnMC04V3()";
//    public static final String QUALITY = "Recipe";
//    public static final String ORDER = "Order";
//    public static final String BATCH = "Batch";
//    public static final String TEST_CODE = "TestProcedure";
//    public static final String TEST_NAME = "TestTag";
//    public static final String LSL = "LSL";
//    public static final String USL = "USL";
//    public static final String TEST_DATE = "TestDate";
//    public static final String TEST_VALUE = "TestResult";
//    public static final String TEST_STATUS = "TestStatus";

    private static String loadFromProps() {
        Properties p = HelpA.properties_load_properties("main.properties", false);
        return p.getProperty("resultsn_name", "REsultsN");
    }


    public static String buildAdditionalWhereGistoGram(String lowerValue, String higherValue) {
        return " AND [" + TEST_VALUE + "]>=" + quotes(lowerValue, true) + ""
                + " AND [" + TEST_VALUE + "] <=" + quotes(higherValue, true);
    }

    public static String buildAdditionalWhereXyGraph(int firstIndex, int lastIndex) {
        return " AND [" + TEST_VALUE + "]>=" + quotes("" + firstIndex, true) + ""
                + " AND [" + TEST_VALUE + "] <=" + quotes("" + lastIndex, true);
    }
    
//    public static String tableHeaders(boolean mysql){
//        String q = "SELECT * from " + PRIM_TABLE + " WHERE [Quality]='xxxxxx-xxxx'";
//        //
//        if(){
//            
//        }
//    }

    public static String forTest() {
        return "SELECT * from " + PRIM_TABLE + " WHERE [TestCode]='10171' AND [Name]='ML' AND [testdate]='09/12/14'";
    }

    public static String forTestB() {
        return "SELECT * from " + PRIM_TABLE + " WHERE [" + SQL_Q.QUALITY + "] = '1802860-ST220' AND [" + SQL_Q.TEST_CODE + "]='10194' AND [" + SQL_Q.TEST_NAME + "]='ML'";
    }

    public static String forTestC() {
        return "SELECT * from " + PRIM_TABLE + " WHERE [" + SQL_Q.QUALITY + "] = '9401696-ST59' AND [" + SQL_Q.TEST_CODE + "]='30001' AND [" + SQL_Q.TEST_NAME + "]='HHMedian'";
    }


    public static String quotes(String str, boolean number) {
        //
        if (str == null || str.equals("NULL")) {
            return "NULL";
        }
        //
        if (number) {
            return str.replaceAll("'", "");
        } else {
            if (str.contains("'")) {
                return str;
            } else {
                return "'" + str + "'";
            }
        }
    }

    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * @deprecated @return
     */
//    public static String showResultB() {
//        //
//        String query = "SELECT * from " + PRIM_TABLE;
//        //
//        String quality = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxQuality);
//        String order = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxOrder);
//        String batch = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxBatch);
//        String testCode = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestCode);
//        String testName = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestName);
//        String lsl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxLSL);
//        String usl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxUSL);
//        String date_ = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateA);
//        String dateB = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateB);
//        //
//        if (quality != null && quality.isEmpty() == false) {
//            query += " AND [" + QUALITY + "]=" + quotes(quality, false);
//        }
//        if (order != null && order.isEmpty() == false) {
//            query += " AND [" + ORDER + "]=" + quotes(order, false);
//        }
//        if (batch != null && batch.isEmpty() == false) {
//            query += " AND [" + BATCH + "]=" + quotes(batch, isNumber(batch));
//        }
//        if (testCode != null && testCode.isEmpty() == false) {
//            query += " AND [" + TEST_CODE + "]=" + quotes(testCode, false);
//        }
//        if (testName != null && testName.isEmpty() == false) {
//            query += " AND [" + TEST_NAME + "]=" + quotes(testName, false);
//        }
//        if (lsl != null && lsl.isEmpty() == false) {
//            query += " AND [" + LSL + "]=" + quotes(lsl, true);
//        }
//        if (usl != null && usl.isEmpty() == false) {
//            query += " AND [" + USL + "]=" + quotes(usl, true);
//        }
//        //
//        if ((dateB != null && dateB.isEmpty() == false) && (date_ != null && date_.isEmpty() == false)) {
//            query += " AND [" + TEST_DATE + "] >=" + quotes(date_, false);
//            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
//        } else {
//            if (date_ != null && date_.isEmpty() == false) {
//                query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
//            }
//        }
//        //
//        if (query.contains("WHERE") == false) {
//            query = query.replaceFirst("AND", "WHERE");
//        }
//        //
//        System.out.println("query: " + query);
//        return query;
//    }
    /**
     * @deprecated @param actualComboParam
     * @return
     */
//    private static String fillAutoB(String actualComboParam) {
//        //
//        String query = "SELECT DISTINCT [" + actualComboParam + "], COUNT(" + actualComboParam + ") as 'ammount'"
//                + " from " + PRIM_TABLE;
//        //
//        String quality = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxQuality);
//        String order = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxOrder);
//        String batch = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxBatch);
//        String testCode = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestCode);
//        String testName = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxTestName);
//        String lsl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxLSL);
//        String usl = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxUSL);
//        String date_ = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateA);
//        String dateB = HelpA.getComboBoxSelectedValue_b(Main.jComboBoxDateB);
//        //
//        if (quality != null && quality.isEmpty() == false && actualComboParam.equals(QUALITY) == false) {
//            query += " AND [" + QUALITY + "]=" + quotes(quality, false);
//        }
//        if (order != null && order.isEmpty() == false && actualComboParam.equals(ORDER) == false) {
//            query += " AND [" + ORDER + "]=" + quotes(order, false);
//        }
//        if (batch != null && batch.isEmpty() == false && actualComboParam.equals(BATCH) == false) {
//            query += " AND [" + BATCH + "]=" + quotes(batch, isNumber(batch)); // OBS! Is Integer
//        }
//        if (testCode != null && testCode.isEmpty() == false && actualComboParam.equals(TEST_CODE) == false) {
//            query += " AND [" + TEST_CODE + "]=" + quotes(testCode, false);
//        }
//        if (testName != null && testName.isEmpty() == false && actualComboParam.equals(TEST_NAME) == false) {
//            query += " AND [" + TEST_NAME + "]=" + quotes(testName, false);
//        }
//        if (lsl != null && lsl.isEmpty() == false && actualComboParam.equals(LSL) == false) {
//            query += " AND [" + LSL + "]=" + quotes(lsl, true);
//        }
//        if (usl != null && usl.isEmpty() == false && actualComboParam.equals(USL) == false) {
//            query += " AND [" + USL + "]=" + quotes(usl, true);
//        }
//
//        if ((dateB != null && dateB.isEmpty() == false) && (date_ != null && date_.isEmpty() == false)) {
//            query += " AND [" + TEST_DATE + "] >=" + quotes(date_, false);
//            query += " AND [" + TEST_DATE + "] <=" + quotes(dateB, false);
//        } else {
//            if (date_ != null && date_.isEmpty() == false) {
//                query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
//            }
//        }
//
////        if (date_ != null && date_.isEmpty() == false && actualComboParam.equals(TEST_DATE) == false) {
////            query += " AND [" + TEST_DATE + "]=" + quotes(date_, false);
////        }
//        //
//
//        if (query.contains("WHERE") == false) {
//            query = query.replaceFirst("AND", "WHERE");
//        }
//        //
////        query += " GROUP BY [" + actualComboParam + "]";
//        query += " GROUP BY [" + actualComboParam + "]";
////        query += " ORDER BY [" + actualComboParam + "] DESC";
//        //
//        System.out.println("query: " + query);
//        return query;
//    }
    private static String fill_quality_combo_box() {
        return "SELECT distinct " + QUALITY + "  from " + PRIM_TABLE;
    }

    private static String fill_order_combo_box(String quality) {
        return "SELECT distinct " + ORDER + " from " + PRIM_TABLE
                + " WHERE " + QUALITY + "=" + quotes(quality, false);
    }
}
