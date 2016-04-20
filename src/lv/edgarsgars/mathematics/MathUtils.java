/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import lv.edgarsgars.utils.CustomFunction;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author Edgar_000
 */
public class MathUtils {

    public static Matrix applyFunction(Matrix x, CustomFunction fun) {
        Matrix result = new Matrix(x.getRowCount(),x.getCollumCount());
        for (int i = 0; i < result.getRowCount(); i++) {
            for (int j = 0; j < result.getCollumCount(); j++) {
                result.set(fun.functionOf(x.get(i, j)), i, j);
            }
        }
        return result;
    }

    public static Matrix pow(Matrix m, double pow) {
        Matrix result = new Matrix();
        for (int i = 0; i < result.getRowCount(); i++) {
            for (int j = 0; j < result.getCollumCount(); j++) {
                result.set(Math.pow(m.get(i, j), pow), i, j);
            }
        }
        return result;
    }

    public static Matrix sin(Matrix x) {
        return applyFunction(x, CustomFunctionList.sin);
    }

    public static Matrix cos(Matrix x) {
        return applyFunction(x, CustomFunctionList.cos);
    }

    public static Matrix sqrt(Matrix x) {
        return applyFunction(x, CustomFunctionList.sqrt);
    }

    public static Matrix abs(Matrix x) {
        return applyFunction(x, CustomFunctionList.abs);
    }
}
