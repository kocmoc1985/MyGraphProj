/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XY_BUH_INVOICE;

import XYG_BASIC.XyGraph_Basic;
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

    private final String KEY_MAIN__VALUE;
    public static final String KEY__FAKTURA_NR = "fakturanr";
    public static final String KEY__FAKTURA_NR_ALT = "fakturanr_alt";
    public static final String KEY__FAKTURA_TYPE = "fakturatyp";
    public static final String KEY__FAKTURA_BETALD = "betald";
    public static final String KEY__FAKTURA_MAKULERAD = "makulerad";
    public static final String KEY__FAKTURA_FORFALLODATUM = "forfallodatum";
    public static final String KEY__IS_PERSON = "is_person";
    public static final String KEY__RUTAVDRAG = "rutavdrag";
    public static final String KEY__FAKTURA_KUND = "namn";
    public static final String KEY__OMVANT_SKATT = "omvant_skatt";
    public static final String KEY__IS_SENT_FAKTURA = "sent_with_email";
    public static final String KEY__IS_PRINTED = "is_printed";
    public static final String KEY__KOMMENT = "important_komment";
    public static final String KEY__KUND_KATEGORI = "kund_kategori";

    public static final String NICK__FAKTURA_KUND = "KUND";
    public static final String KUND_KATEGORI__EU_EUR = "EU EUR";
    public static final String KUND_KATEGORI__UTL_EUR = "UTL EUR";
    
    public HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>();
    public HashMap<String, String> fakturaTypeMap = new HashMap<String, String>();

    public XyGraph_BuhInvoice(String title, String keyMainValue, MyGraphXY xy, int displayMode, String dateNow, String dateFormat) {
        super(title, xy, displayMode);
        this.DATE_NOW = dateNow;
        this.DATE_FORMAT = dateFormat;
        this.KEY_MAIN__VALUE = keyMainValue;
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
            int fakturaTyp = Integer.parseInt(map.get(KEY__FAKTURA_TYPE));
            int betald = Integer.parseInt(map.get(KEY__FAKTURA_BETALD));
            int makulerad = Integer.parseInt(map.get(KEY__FAKTURA_MAKULERAD));
            String forfallodatum = map.get(KEY__FAKTURA_FORFALLODATUM);
            boolean forfallen = isForfallen(fakturaTyp, betald, makulerad, forfallodatum);
            int is_person = Integer.parseInt(map.get(KEY__IS_PERSON));
            boolean is_rut = isRut(map.get(KEY__RUTAVDRAG));
            boolean is_omvant_skatt = isOmvantSkatt(map.get(KEY__OMVANT_SKATT));
            int is_sent = Integer.parseInt(map.get(KEY__IS_SENT_FAKTURA));
            int is_printed = Integer.parseInt(map.get(KEY__IS_PRINTED));
            String komment = map.get(KEY__KOMMENT);
            String kund_kategori = map.get(KEY__KUND_KATEGORI);
            //
            Color color = defineColor(fakturaTyp, betald, forfallen, is_person, makulerad);
            MyPoint p = new MyPoint((int) val, val, color);
            //
            //
            if (komment != null && komment.isEmpty() == false) {
                p.addPointInfo("ANTECKNING", komment);
            }
            //
            if(kund_kategori.equals(KUND_KATEGORI__EU_EUR) || kund_kategori.equals(KUND_KATEGORI__UTL_EUR)){
                 p.addPointInfo("VALUTA", "EUR");
            }
            //
            if (makulerad == 1 && is_person == 1) {
                p.setPointDrawRectInitial(true);
                p.addPointInfo("MAKULERAD", "Ja");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            } else if (makulerad == 1 && is_person == 0) {
                p.setPointColorInitial(Color.WHITE);
                p.addPointInfo("MAKULERAD", "Ja");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            }
            //
            //
            if (betald == 1 && is_person == 1) {
                p.setPointDrawRectInitial(true);
                p.addPointInfo("BETALD", "Ja");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            } else if (betald == 1 && is_person == 0) {
                p.addPointInfo("BETALD", "Ja");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            }
            //
            //
            if (forfallen && is_person == 1) {
                p.setPointDrawRectInitial(true);
                p.addPointInfo("FÖRFALLEN", "Ja");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            } else if (forfallen && is_person == 0) {
                p.addPointInfo("FÖRFALLEN", "Ja");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            }
            //
            //====================================================
            //
            if (is_person == 1) {
                p.setPointDrawRectInitial(true);
                p.addPointInfo("PRIVATPERSON", "Ja");
            }
            //
            //
            if ((is_sent == 0 && is_printed == 0) && is_person == 1 && betald == 0) {
                p.setPointRectBorder(Color.RED, true);
                p.addPointInfo("SKICKAT / UTSKRIVEN", "Nej");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            } else if ((is_sent == 0 && is_printed == 0) && is_person == 0 && betald == 0) {
                p.setPointBorder(Color.RED, true);
                p.addPointInfo("SKICKAT / UTSKRIVEN", "Nej");
                addData_help(p, map, hashMapKeysInfo);
                continue;
            }
            //
            //
            if (is_rut) {
                p.setPointRectBorder(Color.WHITE, true);
                p.addPointInfo("RUT / ROT", "Ja");
            }
            //
            if (is_omvant_skatt) {
                p.setPointBorder(Color.WHITE, true);
                p.addPointInfo("OMVÄND MOMS", "Ja");
            }
            //
            //
            addData_help(p, map, hashMapKeysInfo);
            //
            //
        }
        //
    }

    private void addData_help(MyPoint p, HashMap<String, String> map, String[] hashMapKeysInfo) {
        //
        String faktura_kund = map.get(KEY__FAKTURA_KUND);
        String kund_kategori = map.get(KEY__KUND_KATEGORI);
        int fakturaNr = Integer.parseInt(map.get(KEY__FAKTURA_NR));
        int fakturaNrAlt = Integer.parseInt(map.get(KEY__FAKTURA_NR_ALT));
        int fakturaTyp = Integer.parseInt(map.get(KEY__FAKTURA_TYPE));
        double val = Double.parseDouble(map.get(KEY_MAIN__VALUE));
        //
        if(kund_kategori.equals(KUND_KATEGORI__EU_EUR) || kund_kategori.equals(KUND_KATEGORI__UTL_EUR)){
           p.addPointInfo(NICK__FAKTURA_KUND, "" + faktura_kund + " (EUR)"); 
        }else{
           p.addPointInfo(NICK__FAKTURA_KUND, "" + faktura_kund); 
        }
        //
        p.addPointInfo(KEY__FAKTURA_NR.toUpperCase(), "" + fakturaNr);
        //
        if (fakturaNrAlt != 0) {
            p.addPointInfo(KEY__FAKTURA_NR_ALT.toUpperCase().replaceAll("_", " "), "" + fakturaNrAlt);
        }
        //
        p.addPointInfo("FAKTURATYP", fakturaTypeMap.get("" + fakturaTyp));
        p.addPointInfo(KEY_MAIN__VALUE, "" + val);
        //
        for (String infoKey : hashMapKeysInfo) {
            //OBS!OBS! YES! "fakturadatum" & "forfallodatum" are added here
            p.addPointInfo(infoKey, map.get(infoKey));
        }
        //
        addPointWithDiffMarkerPointsDelete(p, true);
        //
    }

    public boolean isOmvantSkatt(String omvantskatt) {
        double omvand = Double.parseDouble(omvantskatt);
        return omvand != 0;
    }

    public boolean isRut(String rutavdrag) {
        double rut_total = Double.parseDouble(rutavdrag);
        return rut_total != 0;
    }

    public boolean isForfallen(int fakturaType, int betald, int makulerad, String forfallodatum) {
        boolean forfallen = HelpAA.compareDates(DATE_NOW, DATE_FORMAT, forfallodatum, DATE_FORMAT);
        if (forfallen && fakturaType == 0 && betald == 0 && makulerad == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void defineFakturaTypeMap() {
        fakturaTypeMap.put("" + FAKTURA_TYPE__NORMAL, "NORMAL");
        fakturaTypeMap.put("" + FAKTURA_TYPE__KREDIT, "KREDIT");
        fakturaTypeMap.put("" + FAKTURA_TYPE__KONTANT, "KONTANT");
        fakturaTypeMap.put("" + FAKTURA_TYPE__OFFERT, "OFFERT");
    }

    public Color defineColor(int fakturaType, int betald, boolean forfallen, int isPerson, int makulerad) {
        if (makulerad == 1) {
            return Color.WHITE;
        } else if (betald == 1) {
            return Color.GREEN;
        } else if (forfallen) {
            return Color.RED;
        } else if (fakturaType == 0 && isPerson == 1) {
            return Color.CYAN;
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
        this.myGraphXY.DRAW_MARKER_INFO_ONLY = true;
        this.setMarkerInfo(1);
//        this.setDrawMarker(false); // Will not work for the "BuhInvoice" branch
//        this.setMarkerDotted(true); // Will not work for the "BuhInvoice" branch
//        this.setMarkerAutoReset(false); // Will not work for the "BuhInvoice" branch
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
        PointHighLighter.addSerieSingle(serie);
        //
//        myGraphXY.SHOW_SCALE = false; // OBS! To not to show scaling on Y and X axises
    }

}
