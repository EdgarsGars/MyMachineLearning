/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import lv.edgarsgars.classifiers.boosting.SimpleClassifier;
import lv.edgarsgars.utils.MatrixUtils;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author edgars.garsneks
 */
public class Matrix implements Iterable<Matrix> {

    private double[][] matrix;

    public Matrix(int n, int m) {
        matrix = new double[n][m];
    }

    public Matrix(Matrix... mats) {
        this.matrix = mats[0].matrix;
        for (int i = 1; i < mats.length; i++) {
            this.matrix = vcat(mats[i]).matrix;
        }
    }

    public Matrix(double[] matrix) {
        this.matrix = new double[1][matrix.length];
        System.arraycopy(matrix, 0, this.matrix[0], 0, matrix.length);
    }

    public Matrix(List<Double> matrix) {
        this.matrix = new double[1][matrix.size()];
        for (int i = 0; i < matrix.size(); i++) {
            this.matrix[0][i] = matrix.get(i);
        }
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
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
        this.matrix = new double[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            String[] val = rows[i].trim().replaceAll(" +", " ").split(" ");
            for (int j = 0; j < colCount; j++) {
                this.matrix[i][j] = (Double.parseDouble(val[j]));
            }
        }
    }

    public double get(int r, int k) {
        return matrix[r][k];
    }

    public Matrix get(Matrix mask) {
        int dim = (int) mask.sum();
        Matrix res = new Matrix(1, dim);
        int idx = 0;
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                if (mask.get(i, j) == 1) {
                    res.set(get(i, j), 0, idx++);
                }
            }
        }
        return res;
    }

    public void set(double value, int r, int k) {
        matrix[r][k] = value;
    }

    public Matrix toVector() {
        int r = matrix.length;
        int k = matrix[0].length;
        Matrix vec = new Matrix(1, r * k);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < k; j++) {
                vec.set(matrix[i][j], 0, i * k + j);
            }
        }

        return vec;
    }

    public Matrix getRow(int index) {
        return new Matrix(matrix[index]);
    }

    public Matrix getRow(String s) {
        if (s.equals("-1")) {
            return getRow(getRowCount() - 1);
        } else {
            return null;
        }
    }

    public Matrix getCol(String s) {
        if (s.equals("-1")) {
            return getCol(getCollumCount() - 1);
        } else {
            return null;
        }
    }

    public Matrix getCol(int index) {
        if (index < getCollumCount()) {
            return T().getRow(index).T();
        } else {
            System.err.println("Index is out of range " + index + " column count = " + getCollumCount());
            return null;
        }
    }

    public Matrix copy() {
        Matrix m = new Matrix(getRowCount(), getCollumCount());
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                m.set(matrix[i][j], i, j);
            }
        }
        return m;
    }

    public Matrix scalar(Matrix m) {
        if (m.getCollumCount() == getCollumCount() && m.getRowCount() == getRowCount()) {
            Matrix cop = copy();
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getCollumCount(); j++) {
                    cop.set(get(i, j) * m.get(i, j), i, j);
                }
            }
            return cop;
        } else {
            System.err.println("Matrix dimensions do not match. " + "[" + getRowCount() + "," + getCollumCount() + "]" + "[" + m.getRowCount() + "," + m.getCollumCount() + "]");
            return null;
        }
    }

    public Matrix scalar(double scalar) {
        Matrix cop = copy();
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                cop.set(get(i, j) * scalar, i, j);
            }
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
                        product.set(product.get(i, j) + get(i, k) * b.get(k, j), i, j);
                    }
                }
            }
        } else {
            System.err.println("Matrix collums must match others rows");
            return new Matrix(0, 0);
        }
        return product;
    }

    public Matrix negate() {
        Matrix res = copy();
        return res.scalar(-1);
    }

    public int getRowCount() {
        return matrix.length;
    }

    public int getCollumCount() {
        return matrix[0].length;
    }

    @Override
    public String toString() {
        String s = "[";
        for (double[] vector : matrix) {
            for (double d : vector) {
                s += d + " ";
            }
            s += ";";

        }
        s += "]";
        return s;
    }

    public String toStringExcel() {
        String s = "";
        for (double[] vector : matrix) {
            for (double d : vector) {
                s += d + "\t";
            }
            s += "\n";

        }
        s += "";
        return s;
    }

    public Matrix T() {
        Matrix temp = new Matrix(getCollumCount(), getRowCount());
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                temp.set(get(i, j), j, i);
            }
        }
        return temp;
    }

    public Matrix add(double value) {
        Matrix tmp = copy();
        for (int i = 0; i < tmp.getRowCount(); i++) {
            for (int j = 0; j < tmp.getCollumCount(); j++) {
                tmp.set(get(i, j) + value, i, j);
            }
        }
        return tmp;
    }

    public Matrix add(Matrix m) {
        Matrix d = new Matrix(m.getRowCount(), m.getCollumCount());
        for (int i = 0; i < d.getRowCount(); i++) {
            for (int j = 0; j < d.getCollumCount(); j++) {
                d.set(get(i, j) + m.get(i, j), i, j);
            }
        }
        return d;
    }

    public void addTo(double value, int r, int k) {
        matrix[r][k] += value;
    }

    public Matrix sub(double value) {
        Matrix tmp = copy();
        for (int i = 0; i < tmp.getRowCount(); i++) {
            for (int j = 0; j < tmp.getCollumCount(); j++) {
                tmp.set(get(i, j) - value, i, j);
            }
        }
        return tmp;
    }

    public Matrix sub(Matrix m) {
        Matrix d = new Matrix(m.getRowCount(), m.getCollumCount());
        for (int i = 0; i < d.getRowCount(); i++) {
            for (int j = 0; j < d.getCollumCount(); j++) {
                d.set(get(i, j) - m.get(i, j), i, j);
            }
        }
        return d;
    }

    public int[] size() {
        return new int[]{getRowCount(), getCollumCount()};
    }

    public Matrix[] getRows() {
        Matrix[] v = new Matrix[getRowCount()];
        for (int i = 0; i < v.length; i++) {
            v[i] = getRow(i);
        }
        return v;
    }

    public void setRow(int i, Matrix row) {
        //  Matrix m = copy();
        for (int j = 0; j < row.getCollumCount(); j++) {
            set(row.get(0, j), i, j);
        }
        //  return m;
    }

    public void setCol(int i, Matrix col) {
        // Matrix m = copy();
        for (int j = 0; j < col.getRowCount(); j++) {
            set(col.get(j, 0), j, i);
        }
        // return m;
    }

    public List toList() {
        List list = new ArrayList();
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                list.add(get(i, j));
            }
        }
        return list;
    }

    public Double[] toArray() {
        return (Double[]) (toList().toArray(new Double[getRowCount() * getCollumCount()]));
    }

    public Matrix vcat(double... v) {
        return vcat(new Matrix(v));
    }

    public Matrix vcat(Matrix v) {
        int rowCount = getRowCount() + v.getRowCount();
        Matrix rez = new Matrix(rowCount, v.getCollumCount());
        for (int i = 0; i < getRowCount(); i++) {
            rez.setRow(i, getRow(i));
        }
        for (int i = getRowCount(); i < rowCount; i++) {
            rez.setRow(i, v.getRow(i - getRowCount()));
        }
        return rez;
    }

    public Matrix hcat(Matrix m) {
        Matrix rez = copy();
        return rez.T().vcat(m.T()).T();

    }

    public Matrix hcat(double... m) {
        return hcat(new Matrix(m).T());
    }

    public Matrix reshape(int n, int m) {
        Matrix result = new Matrix(n, m);

        if (getRowCount() * getCollumCount() == n * m) {
            Matrix v = toVector();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    result.set(v.get(0, i * m + j), i, j);
                }
            }
        }
        return result;
    }

    public void removeRow(int n) {
        double[][] m = new double[matrix.length - 1][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            if (i < n) {
                m[i] = matrix[i];
            } else if (i > n) {
                m[i - 1] = matrix[i];
            }
        }
        matrix = m;
    }

    public void removeCol(int n) {
        double[][] m = new double[matrix.length][matrix[0].length - 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                if (j < n) {
                    m[i][j] = matrix[i][j];
                } else if (j > n) {
                    m[i][j - 1] = matrix[i][j];
                }
            }
        }
        matrix = m;
    }

    @Override
    public Iterator<Matrix> iterator() {
        return new Iterator<Matrix>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < getRowCount();
            }

            @Override
            public Matrix next() {
                return getRow(i++);
            }

            @Override
            public void remove() {
                //
            }
        };

    }

    public double[] getRowAsArray(int i) {
        return matrix[i];
    }

    public int indexOf(int row, double value) {
        for (int i = 0; i < getCollumCount(); i++) {
            if (matrix[row][i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean equals(Matrix m) {
        boolean equal = true;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != m.matrix[i][j]) {
                    return false;
                }
            }
        }
        return equal;
    }

    public Matrix conditon(String condition) {
        ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
        Matrix y = new Matrix(getRowCount(), getCollumCount());
        try {
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getCollumCount(); j++) {
                    String myExpression = condition.replaceAll("x", "" + get(i, j));
                    y.set(se.eval(myExpression).toString().equals("true") ? 1.0 : 0.0, i, j);
                }
            }
        } catch (ScriptException ex) {
            Logger.getLogger(SimpleClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return y;
    }

    public Matrix conditon(String condition, Matrix b) {
        ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
        Matrix y = new Matrix(getRowCount(), getCollumCount());
        try {
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getCollumCount(); j++) {
                    String myExpression = condition.replaceAll("x", "" + get(i, j)).replaceAll("y", "" + b.get(i, j));
                    y.set(se.eval(myExpression).toString().equals("true") ? 1.0 : 0.0, i, j);
                }
            }
        } catch (ScriptException ex) {
            Logger.getLogger(SimpleClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return y;
    }

    public double sum() {
        return sum(0).sum(1).get(0, 0);
    }

    public Matrix sum(int dim) {
        Matrix res = null;
        if (dim == 0) {
            res = new Matrix(1, getCollumCount());
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getCollumCount(); j++) {
                    res.addTo(get(i, j), 0, j);
                }
            }
        } else if (dim == 1) {
            res = new Matrix(getRowCount(), 1);
            for (int i = 0; i < getRowCount(); i++) {
                for (int j = 0; j < getCollumCount(); j++) {
                    res.addTo(get(i, j), i, 0);
                }
            }
        }
        return res;
    }

    public double max() {
        double max = get(0, 0);
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                if (max < get(i, j)) {
                    max = get(i, j);
                }
            }
        }
        return max;
    }

    public double min() {
        return MatrixUtils.mergeSort(this)[0].get(0, 0);
    }

    public double mean() {
        double mean = 0;
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {

                mean += get(i, j);

            }
        }
        return mean / (size()[0] * size()[1]);
    }

}
