/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XY_RUN;

import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class XyGraph_BuhInvoice extends XyGraph_Basic {

    // Corresponds to DBEraser -> BuhInvoice -> DB.public static final String STATIC__FAKTURA_TYPE_NORMAL__NUM = "0";
    public static final int FAKTURA_TYPE__NORMAL = 0;
    public static final int FAKTURA_TYPE__KREDIT = 1;
    public static final int FAKTURA_TYPE__KONTANT = 2;
    public static final int FAKTURA_TYPE__OFFERT = 3;

    public static final String KEY_MAIN__VALUE = "total_ink_moms";
    public static final String KEY__FAKTURA_NR = "fakturanr";
    public static final String KEY__FAKTURA_TYPE = "fakturatyp";
    public static final String KEY__FAKTURA_BETALD = "betald";

    public HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>();
    public HashMap<String, String> fakturaTypeMap = new HashMap<String, String>();

    public XyGraph_BuhInvoice(String title, int displayMode) {
        super(title, displayMode);
        init();
    }

    private void init() {
        defineColorMap();
        defineFakturaTypeMap();
        MyGraphXY.ADD_POINT_INFO_BASIC = false;
    }

    public void addData(ArrayList<HashMap<String, String>> list, String[] hashMapKeysInfo) {
        //
        for (HashMap<String, String> map : list) {
            double val = Double.parseDouble(map.get(KEY_MAIN__VALUE));
            int fakturaNr = Integer.parseInt(map.get(KEY__FAKTURA_NR));
            int fakturaTyp = Integer.parseInt(map.get(KEY__FAKTURA_TYPE));
            int betald = Integer.parseInt(map.get(KEY__FAKTURA_BETALD));
            Color color = defineColor(fakturaTyp, betald);
            //
            MyPoint p = new MyPoint((int) val, val, color);
            //
            if (betald == 1) {
                p.setPointDrawRect(true);
                p.addPointInfo("BETALD", "Ja");
            }
            //
            p.addPointInfo("FAKTURANR", "" + fakturaNr);
            p.addPointInfo("FAKTURATYP", fakturaTypeMap.get("" + fakturaTyp));
            p.addPointInfo(KEY_MAIN__VALUE, "" + val);
            //
            for (String infoKey : hashMapKeysInfo) {
                p.addPointInfo(infoKey, map.get(infoKey));
            }
            //
            addPointWithDiffMarkerPointsDelete(p, true);
            //
        }
        //
    }

    public void defineFakturaTypeMap() {
        fakturaTypeMap.put("" + FAKTURA_TYPE__NORMAL, "NORMAL");
        fakturaTypeMap.put("" + FAKTURA_TYPE__KREDIT, "KREDIT");
        fakturaTypeMap.put("" + FAKTURA_TYPE__KONTANT, "KONTANT");
        fakturaTypeMap.put("" + FAKTURA_TYPE__OFFERT, "OFFERT");
    }

    public Color defineColor(int fakturaType, int betald) {
        if (betald == 1) {
            return Color.GREEN;
        } else {
            return colorMap.get(fakturaType);
        }
    }

    public void defineColorMap() {
        colorMap.put(FAKTURA_TYPE__NORMAL, Color.BLUE);
        colorMap.put(FAKTURA_TYPE__KREDIT, Color.ORANGE);
        colorMap.put(FAKTURA_TYPE__KONTANT, Color.MAGENTA);
        colorMap.put(FAKTURA_TYPE__OFFERT, Color.YELLOW);
    }

}
