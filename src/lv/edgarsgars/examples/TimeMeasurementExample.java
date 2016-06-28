/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.examples;

import javax.script.ScriptException;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;
import static lv.edgarsgars.utils.Utils.*;

/**
 *
 * @author Edgar
 */
public class TimeMeasurementExample {

    public static void main(String[] args) throws ScriptException {

        Matrix m = MatrixUtils.rand(5000, 5000);
        Matrix m2 = MatrixUtils.rand(5000, 5000);
        Matrix a = new Matrix("[ 4 5 6; 7 8 9]");
        Matrix b = new Matrix("[ 4 4 6; 7 0 9]");
        tic();
        Matrix res = m.conditon("x < 0.5 && x>0.3");

       // System.out.println(res);
        toc("ms");
        tic();
        System.out.println(a.conditon("x==y", b));
        toc("ms");
        tic();
        System.out.println(m.conditon("x-y < 0.001", m2));
        toc("ms");
        System.out.println("Done");
//        Matrix res = m.conditon(conditon);
//        toc("ms");
    }
}
