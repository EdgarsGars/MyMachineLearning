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
        this.matrix = new Vector[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = new Vector(matrix[i]);
        }
    }

    /**
     * Input must be as in example [3 2 1; 3 2 1; 5 7.5 -3]
     *
     * @param values - the string representing vector
     */
    public Matrix(String values) {
        values = values.substring(1, values.length() - 1);
        String[] rows = values.split(";");
        int rowCount = rows.length;
        int colCount = (rows[0].trim().replaceAll(" +", " ").split(" ")).length;
        this.matrix = new Vector[rowCount];
        for (int i = 0; i < rows.length; i++) {
            this.matrix[i] = new Vector();
            String[] val = rows[i].trim().replaceAll(" +", " ").split(" ");
            for (int j = 0; j < val.length; j++) {
                this.matrix[i].add(Double.parseDouble(val[j]));
            }
        }

    }

    public Matrix(Vector v) {
        this.matrix = new Vector[]{v.copy()};
    }

    public Matrix(Vector[] v) {
        this.matrix = new Vector[v.length];
        for (int i = 0; i < v.length; i++) {
            this.matrix[i] = v[i].copy();
        }
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
        Matrix m = new Matrix(getRowCount(), getCollumCount());
        for (int i = 0; i < getRowCount(); i++) {
            m.matrix[i] = matrix[i].copy();
        }
        return m;
    }

    public Matrix scalar(double scalar) {
        Matrix cop = copy();
        for (int i = 0; i < cop.getRowCount(); i++) {
            cop.matrix[i] = cop.matrix[i].multiply(scalar);
        }
        return cop;
    }

    public Matrix dot(Matrix b) {
        Matrix product;
        if (getCollumCount() == b.getRowCount()) {
            product = new Matrix(getRowCount(), b.getCollumCount());
            for (int i = 0; i < product.getRowCount(); i++) {
                for (int j = 0; j < product.getCollumCount(); j++) {
                    for (int k = 0; k < getCollumCount(); k++) {
                        product.set(i, j, product.get(i, j) + get(i, k) * b.get(k, j));
                    }
                }
            }
        } else {
            return new Matrix(0, 0);
        }
        return product;
    }

    public Matrix dot(Vector v) {
        Matrix b = new Matrix(v);
        return dot(b);
    }

    public int getRowCount() {
        return matrix.length;
    }

    public int getCollumCount() {
        return matrix[0].size();
    }

    @Override
    public String toString() {
        String s = "";
        for (Vector vector : matrix) {
            s += vector + "\n";
        }
        return s;
    }

    public Matrix transponse() {
        Matrix temp = new Matrix(getCollumCount(), getRowCount());
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                temp.set(j, i, get(i, j));
            }
        }
        return temp;
    }

    public Matrix add(double value) {
        Matrix tmp = copy();
        for (int i = 0; i < tmp.getRowCount(); i++) {
            for (int j = 0; j < tmp.getCollumCount(); i++) {
                tmp.set(i, j, get(i, j) + value);
            }
        }
        return tmp;
    }

    public int size() {
        return getRowCount() * getCollumCount();
    }

}
