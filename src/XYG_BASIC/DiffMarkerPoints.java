/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BASIC;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JTextField;

/**
 *
 * @author KOCMOC
 */
public class DiffMarkerPoints {

    public final MySerie serie;
    public final MyGraphXY myGraphXY;
    public MyPoint MARKER_POINT_A;
    public MyPoint MARKER_POINT_B;
    public HashMap<String, JTextField> outPutMap = new HashMap<String, JTextField>();
    public static final String CALC_SUMM = "SUMM";
    public static final String CALC_AVERAGE = "AV";
    public CursorDiff CURSOR_A;
    public CursorDiff CURSOR_B;
    public ArrayList<DiffMarkerAction> diffMarkerActionListeners = new ArrayList<DiffMarkerAction>();

    public DiffMarkerPoints(MySerie serie, MyGraphXY graphXY) {
        this.serie = serie;
        this.myGraphXY = graphXY;
        CURSOR_A = new CursorDiff(this, myGraphXY, serie, "CURSOR A");
        CURSOR_B = new CursorDiff(this, myGraphXY, serie, "CURSOR B");
    }

    public void addDiffMarkersSetListener(DiffMarkerAction dma) {
        this.diffMarkerActionListeners.add(dma);
    }

    public void addDiffMarkerOutPutComponent(String name, JTextField jtf) {
        outPutMap.put(name, jtf);
    }

    public ArrayList<MyPoint> getPoints() {
        ArrayList list = new ArrayList();

        if (MARKER_POINT_A != null) {
            list.add(MARKER_POINT_A);
        }

        if (MARKER_POINT_B != null) {
            list.add(MARKER_POINT_B);
        }

        return list;
    }

    public ArrayList<CursorDiff> getCursors() {
        ArrayList list = new ArrayList();
        list.add(CURSOR_A);
        list.add(CURSOR_B);

        return list;
    }

    public void add(MyPoint point) {
        if (MARKER_POINT_A == null && MARKER_POINT_B == null) {
            MARKER_POINT_A = point;
            reset("Caller: add(...)");
        } else if (MARKER_POINT_A == null && MARKER_POINT_B != null) {
            MARKER_POINT_A = point;
            go();
        } else if (MARKER_POINT_A != null && MARKER_POINT_B == null) {
            //
            if (MARKER_POINT_A.getPointIndex() > point.getPointIndex()) {
                MARKER_POINT_B = MARKER_POINT_A;
                MARKER_POINT_A = point;
            } else {
                MARKER_POINT_B = point;
            }
            //
            go();
            //
        } else if (MARKER_POINT_A != null && MARKER_POINT_B != null) {
            MARKER_POINT_A = point;
            MARKER_POINT_B = null;
            reset("Caller: add b ()");
        }

        CURSOR_A.setPoint(MARKER_POINT_A);
        CURSOR_B.setPoint(MARKER_POINT_B);

        CURSOR_A.drawCursor(myGraphXY.getGraphics());
        CURSOR_B.drawCursor(myGraphXY.getGraphics());

        myGraphXY.repaint();
    }

    public void go() {
        addProperties();
//        System.out.println("SUMM: " + calcSum());
//        System.out.println("AV: " + calcAv());
        if (outPutMap.size() > 0) {
            calcAndShow(CALC_SUMM);
            calcAndShow(CALC_AVERAGE);
        }

        for (DiffMarkerAction diffMarkerAction : diffMarkerActionListeners) {
            diffMarkerAction.markersSet(myGraphXY, MARKER_POINT_A, MARKER_POINT_B);
        }

    }

    public void remove(MyPoint point) {
//        reset("Caller: remove()");
        reset("removeB");
        if (MARKER_POINT_A == point) {
            MARKER_POINT_A = null;
        } else if (MARKER_POINT_B == point) {
            MARKER_POINT_B = null;
        }

//        System.out.println("" + toString());
    }

    public void removeB(MyPoint point) {
        remove(point);
        CURSOR_A.setPoint(MARKER_POINT_A);
        CURSOR_B.setPoint(MARKER_POINT_B);

        CURSOR_A.drawCursor(myGraphXY.getGraphics());
        CURSOR_B.drawCursor(myGraphXY.getGraphics());

//        System.out.println("" + toString());
    }

    public boolean contains(MyPoint point) {
        if (MARKER_POINT_A == point || MARKER_POINT_B == point) {
            return true;
        } else {
            return false;
        }
    }

    public boolean bothMarkersSet() {
        if (MARKER_POINT_A != null && MARKER_POINT_B != null) {
            return true;
        } else {
            return false;
        }
    }

    public MySerie getSerie() {
        return serie;
    }

    public boolean bothExist() {
        if (MARKER_POINT_A != null && MARKER_POINT_B != null) {
            return true;
        } else {
            return false;
        }
    }

    public void reset(String caller) {
//        System.out.println("Reset Caller: " + caller);
        for (MyPoint myPoint : draw_rect_points_list) {
            myPoint.setPointDrawRect(false);
            myPoint.resetPointColor();
        }
        //
        draw_rect_points_list = new ArrayList<MyPoint>();
        //
        for (DiffMarkerAction diffMarkerAction : diffMarkerActionListeners) {
            diffMarkerAction.markersUnset(myGraphXY);
        }
    }
    public ArrayList<MyPoint> draw_rect_points_list = new ArrayList<MyPoint>();

    public void addProperties() {
        if (bothExist()) {
            for (int i = MARKER_POINT_A.getPointIndex(); i <= MARKER_POINT_B.getPointIndex(); i++) {
                MyPoint mp = serie.getSerie().get(i);
                mp.setPointColor(Color.MAGENTA);
                mp.setPointDrawRect(true);
                draw_rect_points_list.add(mp);
            }
        }
    }

    public void calcAndShow(String name) {
        if (name.equals(CALC_SUMM)) {
            showOutPut(name, calcSum());
        } else if (name.equals(CALC_AVERAGE)) {
            showOutPut(name, calcAv());
        } else {
            System.out.println("NO SUCH CALC EXIST: " + name);
        }
    }

    public void showOutPut(String name, double value) {
        JTextField jtf = outPutMap.get(name);
        if (jtf != null) {
            jtf.setText("" + value);
        } else {
            System.out.println("NO OUTPUT COMPONENT");
        }

    }

    public double calcSum() {
        double sum = 0;
        if (bothExist()) {
            for (int i = MARKER_POINT_A.getPointIndex(); i <= MARKER_POINT_B.getPointIndex(); i++) {
                sum += serie.getSerie().get(i).y_Display;
            }
        }
        return sum;
    }

    public double calcAv() {
        double sum = 0;
        int c = 0;
        if (bothExist()) {
            for (int i = MARKER_POINT_A.getPointIndex(); i <= MARKER_POINT_B.getPointIndex(); i++) {
                c++;
                sum += serie.getSerie().get(i).y_Display;
            }
        }
        return (sum / c);
    }

    @Override
    public String toString() {
        return "MARKER_1: " + MARKER_POINT_A + "   /  MARKER_2: " + MARKER_POINT_B;
    }
}
