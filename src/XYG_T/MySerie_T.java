/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_T;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class MySerie_T extends ArrayList<MyPoint_T> {
    
    private String name;
    
    public MySerie_T(String name) {
        this.name = name;
    }
    
    public void addPoint(MyPoint_T point) {
        this.add(point);
    }
    
    public void draw(Graphics g) {
        for (MyPoint_T myPoint_T : this) {
            myPoint_T.draw(g);
        }
    }

    public String getName() {
        return name;
    }
    
    
}
