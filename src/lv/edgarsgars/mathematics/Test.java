/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import lv.edgarsgars.utils.VectorUtils;
import lv.edgarsgars.vizualization.Plot;

/**
 *
 * @author Edgar_000
 */
public class Test {

    public static void main(String[] args) {
        Vector x = new Vector("-10:0.1:10");
        Vector y = VectorUtils.pow(x,5);
        Plot.showPlot(x, y);
        
        
        
        
    }
}
