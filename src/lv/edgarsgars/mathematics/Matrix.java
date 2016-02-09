/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

/**
 *
 * @author edgars.garsneks
 */
public class Matrix {

    private double[][] matrix;

    public Matrix(int n, int m) {
        matrix = new double[m][n];
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double get(int k, int r) {
        return matrix[r][k];
    }

    public void set(int k, int r, double value) {
        matrix[r][k] = value;
    }

    public Vector toVector() {
        Vector v = new Vector();
        for (double[] d : matrix) {
            v.add(d);
        }
        return v;
    }

    public Vector getRow(int index) {
        return new Vector(matrix[index]);
    }

    public Vector getCol(int index) {
        Vector v = new Vector();
        for (int i = 0; i < matrix[0].length; i++) {
            v.add(matrix[i][index]);
        }
        return v;
    }

    public Matrix copy() {
        Matrix m = new Matrix(matrix.length, matrix[0].length);
        for (int r = 0; r < matrix.length; r++) {
            for (int k = 0; k < matrix[0].length; k++) {
                m.set(r, k, matrix[r][k]);
            }
        }
        return m;
    }

    public Matrix scalar(double scalar) {
        Matrix cop = copy();
        for (int r = 0; r < matrix.length; r++) {
            for (int k = 0; k < matrix[0].length; k++) {
                cop.set(r, k, cop.get(r, k) * scalar);
            }
        }
        return cop;
    }

    public Matrix product(Matrix b) {
        Matrix product;
        if (getN() == b.getM()) {
            product = new Matrix(b.getM(), getN());
            for (int j = 0; j < product.getM(); j++) {
                for (int i = 0; i < product.getN(); i++) {
                    for (int k = 0; k < getN(); k++) {
                        product.set(i, j, product.get(i, j) + get(i, k) * b.get(k, j));
                    }
                }
            }
        } else {
            return new Matrix(0, 0);
        }
        return product;
    }

    public int getN() {
        return matrix[0].length;
    }

    public int getM() {
        return matrix.length;
    }

    @Override
    public String toString() {
        String s = "";
        for (int j = 0; j < getM(); j++) {
            for (int i = 0; i < getN(); i++) {
                s += matrix[j][i] + " ";
            }
            s += "\n";
        }
        return s;
    }

}
