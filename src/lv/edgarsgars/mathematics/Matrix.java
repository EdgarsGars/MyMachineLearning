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

    private Vector[] matrix;

    public Matrix(int n, int m) {
        matrix = new Vector[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = new Vector(m);
        }
    }

    public Matrix(double[][] matrix) {
        // this.matrix = matrix;
    }

    public double get(int r, int k) {
        return matrix[r].get(k);
    }

    public void set(int r, int k, double value) {
        matrix[r].set(k, value);
    }

    public Vector toVector() {
        Vector v = new Vector();
        for (Vector vec : matrix) {
            v.add(vec);
        }
        return v;
    }

    public Vector getRow(int index) {
        return matrix[index].copy();
    }

    public Vector getCol(int index) {
        Vector v = new Vector();
        for (int i = 0; i < matrix[0].size(); i++) {
            v.add(matrix[i].get(index));
        }
        return v;
    }

    public Matrix copy() {
        Matrix m = new Matrix(0,0);
        for (int i = 0; i < getM(); i++) {
            m.matrix[i] = matrix[i].copy();
        }
        return m;
    }

    public Matrix scalar(double scalar) {
        Matrix cop = copy();
        for (int r = 0; r < getN(); r++) {
            for (int k = 0; k < getM(); k++) {
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
        return matrix.length;
    }

    public int getM() {
        return matrix[0].size();
    }

    @Override
    public String toString() {
        String s = "";
        for (int j = 0; j < getM(); j++) {
            for (int i = 0; i < getN(); i++) {
                s += matrix[j].get(i) + " ";
            }
            s += "\n";
        }
        return s;
    }

}
