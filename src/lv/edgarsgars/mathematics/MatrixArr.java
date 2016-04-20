/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Edgar_000
 */
public class MatrixArr implements Iterable<MatrixArr> {

    private ArrayList<double[]> matrix;

    public MatrixArr(int n, int m) {
        matrix = new ArrayList<double[]>();
        for (int i = 0; i < n; i++) {
            matrix.add(new double[m]);
        }
    }

    public MatrixArr(MatrixArr... mats) {
        this.matrix = mats[0].matrix;
        for (int i = 1; i < mats.length; i++) {
            this.matrix = vcat(mats[i]).matrix;
        }
    }

    public MatrixArr(double[] matrix) {
        this.matrix = new ArrayList<double[]>();
        this.matrix.add(new double[matrix.length]);
        for (int i = 0; i < matrix.length; i++) {
            this.matrix.get(0)[i] = matrix[i];
        }
    }

    public MatrixArr(List<Double> matrix) {
        //  this.matrix = new double[1][matrix.size()];
        //  for (int i = 0; i < matrix.size(); i++) {
        //      this.matrix[0][i] = matrix.get(i);
        // }
    }

    public MatrixArr(double[][] matrix) {
        // this.matrix = matrix;
    }

    /**
     * Input must be as in example [3 2 1; 3 2 1; 5 7.5 -3]
     *
     * @param values - the string representing vector
     */
    public MatrixArr(String values) {
        values = values.substring(1, values.length() - 1);
        String[] rows = values.split(";");
        int rowCount = rows.length;
        int colCount = (rows[0].trim().replaceAll(" +", " ").split(" ")).length;
        this.matrix = new ArrayList<double[]>();
        for (int i = 0; i < rowCount; i++) {
            this.matrix.add(new double[colCount]);
            String[] val = rows[i].trim().replaceAll(" +", " ").split(" ");
            for (int j = 0; j < colCount; j++) {
                this.matrix.get(i)[j] = (Double.parseDouble(val[j]));
            }
        }
    }

    public double get(int r, int k) {
        return matrix.get(r)[k];
    }

    public void set(double value, int r, int k) {
        matrix.get(r)[k] = value;
    }

    public MatrixArr toVector() {
        int r = matrix.size();
        int k = matrix.get(0).length;
        MatrixArr vec = new MatrixArr(1, r * k);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < k; j++) {
                vec.set(matrix.get(i)[j], 0, i * k + j);
            }
        }

        return vec;
    }

    public MatrixArr getRow(int index) {
        return new MatrixArr(matrix.get(index));
    }

    public MatrixArr getRow(String s) {
        if (s.equals("-1")) {
            return getRow(getRowCount() - 1);
        } else {
            return null;
        }
    }

    public MatrixArr getCol(String s) {
        if (s.equals("-1")) {
            return getCol(getCollumCount() - 1);
        } else {
            return null;
        }
    }

    public MatrixArr getCol(int index) {
        if (index < getCollumCount()) {
            return transponse().getRow(index).transponse();
        } else {
            System.err.println("Index is out of range " + index + " column count = " + getCollumCount());
            return null;
        }
    }

    public MatrixArr copy() {
        MatrixArr m = new MatrixArr(getRowCount(), getCollumCount());
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                m.set(matrix.get(i)[j], i, j);
            }
        }
        return m;
    }

    public MatrixArr scalar(Matrix m) {
        if (m.getCollumCount() == getCollumCount() && m.getRowCount() == getRowCount()) {
            MatrixArr cop = copy();
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

    public MatrixArr scalar(double scalar) {
        MatrixArr cop = copy();
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                cop.set(get(i, j) * scalar, i, j);
            }
        }
        return cop;
    }

    public MatrixArr dot(Matrix b) {
        MatrixArr product;
        if (getCollumCount() == b.getRowCount()) {
            product = new MatrixArr(getRowCount(), b.getCollumCount());
            for (int i = 0; i < product.getRowCount(); i++) {
                for (int j = 0; j < product.getCollumCount(); j++) {
                    for (int k = 0; k < getCollumCount(); k++) {
                        product.set(product.get(i, j) + get(i, k) * b.get(k, j), i, j);
                    }
                }
            }
        } else {
            System.err.println("Matrix collums must match others rows");
            return new MatrixArr(0, 0);
        }
        return product;
    }

    public int getRowCount() {
        return matrix.size();
    }

    public int getCollumCount() {
        return matrix.get(0).length;
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

    public MatrixArr transponse() {
        MatrixArr temp = new MatrixArr(getCollumCount(), getRowCount());
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getCollumCount(); j++) {
                temp.set(get(i, j), j, i);
            }
        }
        return temp;
    }

    public MatrixArr add(double value) {
        MatrixArr tmp = copy();
        for (int i = 0; i < tmp.getRowCount(); i++) {
            for (int j = 0; j < tmp.getCollumCount(); j++) {
                tmp.set(get(i, j) + value, i, j);
            }
        }
        return tmp;
    }

    public MatrixArr add(MatrixArr m) {
        MatrixArr d = new MatrixArr(m.getRowCount(), m.getCollumCount());
        for (int i = 0; i < d.getRowCount(); i++) {
            for (int j = 0; j < d.getCollumCount(); j++) {
                d.set(get(i, j) + m.get(i, j), i, j);
            }
        }
        return d;
    }

    public int size() {
        return getRowCount() * getCollumCount();
    }

    public MatrixArr[] getRows() {
        MatrixArr[] v = new MatrixArr[getRowCount()];
        for (int i = 0; i < v.length; i++) {
            v[i] = getRow(i);
        }
        return v;
    }

    public void setRow(int i, MatrixArr row) {
        //  Matrix m = copy();
        for (int j = 0; j < row.getCollumCount(); j++) {

            this.set(row.get(0, j), i, j);
        }
        //  return m;
    }

    public MatrixArr setCol(int i, MatrixArr col) {
        MatrixArr m = copy();
        for (int j = 0; j < col.getCollumCount(); j++) {

            m.set(col.get(j, 0), j, i);
        }
        return m;
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

    public MatrixArr vcat(double... v) {
        return vcat(new MatrixArr(v));
    }

    public MatrixArr vcat(MatrixArr v) {
        MatrixArr rez = this.copy();
        int k = v.getCollumCount();
        for (int i = 0; i < v.getRowCount(); i++) {

            double[] values = new double[k];
            double[] orig = v.getRow(i).matrix.get(0);
            System.arraycopy(orig, 0, values, 0, values.length);
            rez.matrix.add(values);
        }

        return rez;
    }

    public MatrixArr hcat(MatrixArr m) {
        MatrixArr rez = copy();
        return rez.transponse().vcat(m.transponse()).transponse();

    }

    public MatrixArr hcat(double... m) {
        return hcat(new MatrixArr(m).transponse());
    }

    public MatrixArr reshape(int n, int m) {
        MatrixArr result = new MatrixArr(n, m);

        if (getRowCount() * getCollumCount() == n * m) {
            MatrixArr v = toVector();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    result.set(v.get(0, i * m + j), i, j);
                }
            }
        }
        return result;
    }

    @Override
    public Iterator<MatrixArr> iterator() {
        return new Iterator<MatrixArr>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < getRowCount();
            }

            @Override
            public MatrixArr next() {
                return getRow(i++);
            }

            @Override
            public void remove() {
                //
            }
        };

    }
}
