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
        Matrix a = new Matrix(new double[][]{{3,2},{3,3},{3,2}});
        Matrix b = new Matrix(new double[][]{{3,3,3},{3,2,1}});
        Matrix c = a.product(b);
        System.out.println(c);
        
        
        
        
    }
}
