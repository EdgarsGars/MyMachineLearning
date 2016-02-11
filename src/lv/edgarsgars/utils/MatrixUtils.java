/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.utils;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Vector;

/**
 *
 * @author Edgar_000
 */
public class MatrixUtils {

    public static Matrix getRandom(int n, int m) {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getCollumCount(); j++) {
                matrix.set(i, j, Math.random());
            }
        }
        return matrix;
    }

    public static Matrix getRandom(int n, int m, double min, double max) {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getCollumCount(); j++) {
                matrix.set(i, j, Math.random() * (max - min) + min);
            }
        }
        return matrix;
    }

    public static Matrix getIdentical(int n) {
        Matrix matrix = new Matrix(n, n);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            matrix.set(i, i, 1);
        }
        return matrix;
    }

    public static Matrix getOnes(int n, int m) {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getCollumCount(); j++) {
                matrix.set(i, j, 1);
            }
        }
        return matrix;
    }

    public static Matrix sin(Matrix matrix) {
        Vector[] result = new Vector[matrix.getRowCount()];
        for (int i = 0; i < result.length; i++) {
            result[i] = VectorUtils.sin(matrix.getRow(i));
        }
        return new Matrix(result);
    }

    public static Matrix reshape(Matrix matrix, int n, int m) {
        if (matrix.size() == n * m) {
            Vector v = matrix.toVector();
            return VectorUtils.reshape(v, n, m);
        } else {
            return matrix;
        }
    }

    public static Matrix loadDataFromFile(String file, String seperator) {
        return new Matrix(VectorUtils.loadDataFromFile(file, seperator));
    }
    
    

}
