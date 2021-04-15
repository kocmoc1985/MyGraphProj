/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XY_RUN;

import XYG_BASIC.HelpAA;
import XYG_BASIC.MyGraphXY;
import XYG_BASIC.MyPoint;
import XYG_BASIC.MySerie;
import XYG_BASIC.PointHighLighter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class XyGraph_BuhInvoice extends XyGraph_Basic {

    private final String DATE_NOW;
    private final String DATE_FORMAT;
    
    // Corresponds to DBEraser -> BuhInvoice -> DB.public static final String STATIC__FAKTURA_TYPE_NORMAL__NUM = "0";
    public static final int FAKTURA_TYPE__NORMAL = 0;
    public static final int FAKTURA_TYPE__KREDIT = 1;
    public static final int FAKTURA_TYPE__KONTANT = 2;
    public static final int FAKTURA_TYPE__OFFERT = 3;

    public static final String KEY_MAIN__VALUE = "total_ink_moms";
    public static final String KEY__FAKTURA_NR = "fakturanr";
    public static final String KEY__FAKTURA_TYPE = "fakturatyp";
    public static final String KEY__FAKTURA_BETALD = "betald";
    public static final String KEY__FAKTURA_MAKULERAD = "makulerad";
    public static final String KEY__FAKTURA_FORFALLODATUM = "forfallodatum";
    public static final String KEY__FAKTURA_KUND = "namn";

    public HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>();
    public HashMap<String, String> fakturaTypeMap = new HashMap<String, String>();

    public XyGraph_BuhInvoice(String title, MyGraphXY xy, int displayMode,String dateNow,String dateFormat) {
        super(title, xy, displayMode);
        this.DATE_NOW = dateNow;
        this.DATE_FORMAT = dateFormat;
        init();
    }

    private void init() {
        defineColorMap();
        defineFakturaTypeMap();
        MyGraphXY.ADD_POINT_INFO_BASIC = false;
    }

    public void addData(ArrayList<HashMap<String, String>> list, String[] hashMapKeysInfo) {
        //
        if (list == null || list.isEmpty()) {
            return;
        }
        //
        for (int i = list.size() - 1; i >= 0; i--) {
            //
            HashMap<String, String> map = list.get(i);
            //
            double val = Double.parseDouble(map.get(KEY_MAIN__VALUE));
            int fakturaNr = Integer.parseInt(map.get(KEY__FAKTURA_NR));
            int fakturaTyp = Integer.parseInt(map.get(KEY__FAKTURA_TYPE));
            int betald = Integer.parseInt(map.get(KEY__FAKTURA_BETALD));
            int makulerad = Integer.parseInt(map.get(KEY__FAKTURA_MAKULERAD));
            String forfallodatum = map.get(KEY__FAKTURA_FORFALLODATUM);
            boolean forfallen = isForfallen(fakturaTyp, betald, makulerad,forfallodatum);
            String faktura_kund = map.get(KEY__FAKTURA_KUND);
            Color color = defineColor(fakturaTyp, betald,forfallen);
            //
            MyPoint p = new MyPoint((int) val, val, color);
            //
            if (betald == 1) {
                p.setPointDrawRect(true);
                p.addPointInfo("BETALD", "Ja");
            }
            //
            if (forfallen) {
                p.setPointDrawRect(true);
                p.addPointInfo("FÖRFALLEN", "Ja");
            }
            //
            //
            p.addPointInfo("KUND", "" + faktura_kund);
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
//        for (HashMap<String, String> map : list) {
//            
//            //
//        }
        //
    }

    public boolean isForfallen(int fakturaType, int betald, int makulerad,String forfallodatum) {
        boolean forfallen = HelpAA.compareDates(DATE_NOW, DATE_FORMAT, forfallodatum, DATE_FORMAT);
        if(forfallen && fakturaType == 0 && betald == 0 && makulerad == 0){
            return true;
        }else{
            return false;
        }
    }

    public void defineFakturaTypeMap() {
        fakturaTypeMap.put("" + FAKTURA_TYPE__NORMAL, "NORMAL");
        fakturaTypeMap.put("" + FAKTURA_TYPE__KREDIT, "KREDIT");
        fakturaTypeMap.put("" + FAKTURA_TYPE__KONTANT, "KONTANT");
        fakturaTypeMap.put("" + FAKTURA_TYPE__OFFERT, "OFFERT");
    }

    public Color defineColor(int fakturaType, int betald, boolean forfallen) {
        if (betald == 1) {
            return Color.GREEN;
        } else if (forfallen) {
            return Color.RED;
        } else {
            return colorMap.get(fakturaType);
        }
    }

    public void defineColorMap() {
        colorMap.put(FAKTURA_TYPE__NORMAL, Color.BLUE);
        colorMap.put(FAKTURA_TYPE__KREDIT, Color.ORANGE);
        colorMap.put(FAKTURA_TYPE__KONTANT, Color.MAGENTA);
        colorMap.put(FAKTURA_TYPE__OFFERT, Color.DARK_GRAY);
    }

    @Override
    public void initializeA() {
        //
        this.setTitleSize(20, true);
        this.setTitleSize(20, true);
        this.setTitleColor(Color.black);
//        this.setBorderHeadAndFootComponents(BorderFactory.createLineBorder(Color.darkGray));
        this.setHeadHeight(0.1);
        //
        // setAxisScaling(...) & setDrawGrid(...) influence each other!
        this.setAxisScaling(true, true);
//        this.setDrawGrid(true);
//        this.setDisableScalingWhenGrid();
        this.setGridColor(Color.black);
        this.setScaleXYaxisLength(1.2); // 1.2

        //
//        this.setBackgroundColorOfGraph(Color.WHITE); // it is white by default
        //
        this.setDrawMarker(false);
        this.setMarkerDotted(true);
        this.setMarkerInfo(1);
        this.setMarkerAutoReset(false);
        //
    }

    @Override
    public void initializeB() {
        //
        serie = new MySerie(getTitle());
        //
        serie.setDrawPoints(true);
        serie.setPointThickness(1.5);
//        serie.setPointHighLightColor(Color.red);
//        serie.setPointColor(Color.red);
        //
        serie.setDrawLines(true);
        serie.setLineThickness(1);
        serie.setLineDotted();
        serie.setCurveColor(Color.BLUE);
        serie.setOverallScale(true);
        //
        this.addSerie(serie, true, this);
        //
        PointHighLighter.addSerie(serie);
        //
//        myGraphXY.SHOW_SCALE = false; // OBS! To not to show scaling on Y and X axises
    }

}
