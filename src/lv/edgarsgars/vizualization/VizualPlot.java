/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.vizualization;

import lv.edgarsgars.mathematics.Vector;

/**
 *
 * @author edgars.garsneks
 */
public interface VizualPlot {

    public void showPlot();

    public void showPlot(Vector x, Vector... y);
    

}
