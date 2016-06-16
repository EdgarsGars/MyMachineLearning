/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.Matrix;

/**
 *
 * @author edgars.garsneks
 */
public class ActivationFunctions {

    public static Matrix sigmoid(Matrix x) {
        Matrix result = new Matrix(x.getRowCount(), x.getCollumCount());
        for (int i = 0; i < result.getRowCount(); i++) {
            for (int j = 0; j < result.getCollumCount(); j++) {
                double val = 1.0 / (1.0 + Math.exp(-1.0 * x.get(i, j)));
                result.set(val, i, j);
            }
        }
        return result;
    }
}
