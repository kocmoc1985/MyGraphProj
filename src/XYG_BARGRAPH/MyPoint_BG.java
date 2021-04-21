/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYG_BARGRAPH;

import XYG_BASIC.MyPoint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author KOCMOC
 */
public class MyPoint_BG extends MyPoint{

    private String barName;
    
    public MyPoint_BG(double y, double y_) {
        super(y, y_);
    }
    
    public MyPoint_BG(double y, double y_,String barName) {
        super(y, y_);
        this.barName = barName;
    }

    public String getBarName() {
        return barName;
    }
    
     @Override
    protected void drawPoint(Graphics g, Color pointColor) {
        //
        Graphics2D g2d = (Graphics2D) g;
        //
        if (highLightSet == false) {
            POINT_COLOR = pointColor;
        }

        if (POINT_COLOR_B != null) {
            POINT_COLOR = POINT_COLOR_B;
        }
        //
        g2d.setColor(POINT_COLOR);
        //
        MyPoint prevPoint = getPreviousPoint();
        //
        int x_ = x;
        int width;
        //
        if(x > 0 && prevPoint == null){
             x_ -= x;
             width = x;
        }else if(prevPoint != null && x > 0){
             x_ = prevPoint.x;
             width = x - prevPoint.x;
        }else{
            return;
        }
        //
        int y_ = y;
        int height = getSerie().myGraphXY.getHeight() - y;
        //
        x_ += 10; // margin-left
        //
        g2d.fill3DRect(x_, y_, width, height, true);
        //==================================IMPORTNAT=============================
        //Sets the size of the component which reffers to this point
        //
        this.setLocation(x_, y_);
        this.setSize(width, height);
        //
    }
    
}
