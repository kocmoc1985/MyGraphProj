/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XY_RUN;

import XYG_BASIC.MyGraphXY;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class MyGraphXY_BuhInvoice extends MyGraphXY {


    @Override
    public double defineJJ(double vvv) {
        //
        double jj;
        //
        if (vvv > 100000 && vvv < 1000000) {
            jj = 50000; // adjusted [2021-04-14]
        } else if (vvv > 10000 && vvv < 100000) {
            jj = 10000; // adjusted [2021-04-14]
        } else if (vvv > 1000 && vvv < 10000) {
            jj = 2000; // adjusted [2021-04-14]
        } else if (vvv > 100 && vvv < 1000) {
            jj = 200;
        } else if (vvv > 10 && vvv < 100) {
            jj = 5;
        } else if (vvv > 5 && vvv < 10) {
            jj = 1;
        } else if (vvv > 1 && vvv < 3) {
            jj = 0.5;
        } else if (vvv > 0 && vvv < 3) {
            jj = 0.1;
        } else {
            jj = 1;
        }
        //
        if (vvv < 1) {
            jj = 0.02;
        }
        //
        return jj;
        //
    }

}
