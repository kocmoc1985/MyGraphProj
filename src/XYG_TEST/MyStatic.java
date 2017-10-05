/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_TEST;

import java.awt.BasicStroke;

/**
 *
 * @author KOCMOC
 */
public class MyStatic {
    
    public static final float[] LINE_DOTTED = new float[]{10.0f, 6.0f};
    public static final float[] LINE_NOT_DOTTED = new float[]{10.0f, 6.0f};

    public static BasicStroke getGridStroke() {
        return new BasicStroke(
                1.0f, //thickness of the line
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                1.0f,
                new float[]{1.0f, 1.0f}, //// to make dotted {10.0f, 6.0f} // undoted {1.0f, 1.0f}
                0.0f);
    }
    
    public static BasicStroke getDottedStroke(){
        return new BasicStroke(
                2.0f, //thickness of the line
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                1.0f,
                LINE_DOTTED, //// to make dotted {10.0f, 6.0f} // undoted {1.0f, 1.0f}
                0.0f);
    }
}
