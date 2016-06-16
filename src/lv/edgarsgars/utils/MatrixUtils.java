/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import lv.edgarsgars.mathematics.Matrix;

/**
 *
 * @author Edgar_000
 */
public class MatrixUtils {

    public static ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");

    public static Matrix rand(int n, int m) {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getCollumCount(); j++) {
                matrix.set(Math.random(), i, j);
            }
        }
        return matrix;
    }

    public static Matrix rand(int n, int m, double min, double max) {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getCollumCount(); j++) {
                matrix.set(Math.random() * (max - min) + min, i, j);
            }
        }
        return matrix;
    }

    public static Matrix eye(int n) {
        Matrix matrix = new Matrix(n, n);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            matrix.set(1, i, i);
        }
        return matrix;
    }

    public static Matrix ones(int n, int m) {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getCollumCount(); j++) {
                matrix.set(1, i, j);
            }
        }
        return matrix;
    }

    public static Matrix reshape(Matrix matrix, int n, int m) {
        if (matrix.size()[0] * matrix.size()[1] == n * m) {
            Matrix v = matrix.toVector();
            return v.reshape(n, m);
        } else {
            return matrix;
        }
    }

    public static Matrix hreverse(Matrix m) {
        Matrix[] r = m.T().getRows();
        Matrix[] n = new Matrix[r.length];
        for (int i = 0; i < r.length; i++) {
            n[i] = r[r.length - 1 - i];
        }
        return new Matrix(n).T();
    }

    public static Matrix hshift(Matrix m, int amount) {
        Matrix r = m.copy();
        int s = m.getCollumCount();
        int n = amount % s;
        for (int i = 0; i < s; i++) {
            int index = i + n;
            if (index < 0) {
                index += s;
            } else if (index >= s) {
                index -= s;
            }
            for (int j = 0; j < m.getRowCount(); j++) {
                r.set(m.get(j, i), j, index);
            }
        }
        return r;
    }

    public static double euclidD(Matrix x, Matrix y) {
        double d = 0;
        for (int i = 0; i < x.getCollumCount(); i++) {
            d += Math.pow(y.get(0, i) - x.get(0, i), 2);
        }
        return Math.sqrt(d);
    }

    public static Matrix distanceMatrix(Matrix x, String distanceType) {
        Matrix[] coord = x.getRows();
        Matrix distances = new Matrix(x.getRowCount(), x.getRowCount());
        for (int i = 0; i < coord.length; i++) {
            for (int j = i + 1; j < coord.length; j++) {
                if (distanceType.equals("euclid")) {
                    double d = MatrixUtils.euclidD(coord[i], coord[j]);
                    distances.set(d, i, j);
                    distances.set(d, j, i);
                }
            }
        }
        return distances;
    }

    public static Matrix distances(Matrix fromPoint, Matrix tillPoints, String distType) {
        Matrix dist = new Matrix(1, tillPoints.getRowCount());
        for (int i = 0; i < dist.getCollumCount(); i++) {
            if (distType.equals("euclid")) {
                dist.set(euclidD(fromPoint, tillPoints.getRow(i)), 0, i);
            } else if (distType.equals("manhatan")) {
                dist.set(euclidM(fromPoint, tillPoints.getRow(i)), 0, i);
            }
        }
        return dist;
    }

    public static Matrix[] sort(Matrix x, String order) {
        Matrix indexes = getRangeVector("0:1:" + x.getCollumCount());
        Matrix values = x.copy();
        if (order.equals("asc")) {
            boolean changed = false;
            while (!changed) {
                changed = true;
                for (int i = 0; i < values.getCollumCount() - 1; i++) {
                    if (values.get(0, i) > values.get(0, i + 1)) {
                        double ti = indexes.get(0, i);
                        indexes.set(indexes.get(0, i + 1), 0, i);
                        indexes.set(ti, 0, i + 1);

                        double vi = values.get(0, i);
                        values.set(values.get(0, i + 1), 0, i);
                        values.set(vi, 0, i + 1);
                        changed = false;
                    }
                }
            }
        } else {

        }

        return new Matrix[]{values, indexes};
    }

    //0:1:15
    public static Matrix getRangeVector(String vec) {
        String[] s = vec.split(":");
        double start = Double.parseDouble(s[0]);
        double step = Double.parseDouble(s[1]);
        double end = Double.parseDouble(s[2]);
        int n = (int) (Math.abs((end - start) / step));
        Matrix m = new Matrix(1, n);
        for (int i = 0; i < n; i++) {
            m.set(start + i * step, 0, i);
        }
        return m;
    }

    public static Matrix[] mergeSort(Matrix x) {
        Matrix temp = new Matrix(1, x.getCollumCount());
        Matrix a = x.copy();
        Matrix idx = MatrixUtils.getRangeVector("0:1:" + temp.getCollumCount());
        // System.out.println("idx " + idx);
        Matrix idxtemp = new Matrix(1, x.getCollumCount());
        mergeSort(a, temp, idx, idxtemp, 0, x.getCollumCount() - 1);
        return new Matrix[]{a, idx};
    }

    private static void mergeSort(Matrix a, Matrix tmp, Matrix idx, Matrix idxtemp, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tmp, idx, idxtemp, left, center);
            mergeSort(a, tmp, idx, idxtemp, center + 1, right);
            merge(a, tmp, idx, idxtemp, left, center + 1, right);
        }
    }

    private static void merge(Matrix a, Matrix tmp, Matrix idx, Matrix idxtemp, int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while (left <= leftEnd && right <= rightEnd) {
            if (a.get(0, left) <= a.get(0, right)) {
                tmp.set(a.get(0, left++), 0, k++);
                idxtemp.set(idx.get(0, left - 1), 0, k - 1);
            } else {
                tmp.set(a.get(0, right++), 0, k++);
                idxtemp.set(idx.get(0, right - 1), 0, k - 1);
            }
        }

        while (left <= leftEnd) // Copy rest of first half
        {
            tmp.set(a.get(0, left++), 0, k++);
            idxtemp.set(idx.get(0, left - 1), 0, k - 1);
        }

        while (right <= rightEnd) // Copy rest of right half
        {
            tmp.set(a.get(0, right++), 0, k++);
            idxtemp.set(idx.get(0, right - 1), 0, k - 1);
        }

        // Copy tmp back
        for (int i = 0; i < num; i++, rightEnd--) {
            a.set(tmp.get(0, rightEnd), 0, rightEnd);
            idx.set(idxtemp.get(0, rightEnd), 0, rightEnd);
        }
    }

    public static Matrix loadFromFile(String filename, String delimiter) {
        ArrayList<String> rows = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String s = "";
            while ((s = reader.readLine()) != null) {
                rows.add(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Matrix data = new Matrix(rows.size(), rows.get(0).split(delimiter, -1).length);
        for (int i = 0; i < rows.size(); i++) {
            String[] s = rows.get(i).split(delimiter, -1);
            for (int j = 0; j < s.length; j++) {
                data.set(Double.parseDouble(s[j]), i, j);
            }

        }
        return data;
    }

    public static Matrix unique(Matrix x) {
        HashSet<Double> uniques = new HashSet<Double>(x.toList());
        ArrayList<Double> values = new ArrayList<Double>();
        for (Double v : uniques) {
            values.add(v);
        }
        return mergeSort(new Matrix(values))[0];
    }

    public static Matrix confusionMatrix(Matrix expected, Matrix y) {
        Matrix uniques = sort(unique(expected), "asc")[0];
        // System.out.println(uniques + "->");
        Matrix conf = new Matrix(uniques.getCollumCount(), uniques.getCollumCount());
        for (int i = 0; i < y.getRowCount(); i++) {
            int y1 = uniques.indexOf(0, y.get(i, 0));
            int y2 = uniques.indexOf(0, expected.get(i, 0));

            conf.set(conf.get(y1, y2) + 1, y1, y2);
        }
        return conf;
    }

    public static Matrix zscore(Matrix x) {
        Matrix normalized = new Matrix(x.getRowCount(), x.getCollumCount());

        for (int i = 0; i < x.getCollumCount(); i++) {
            Matrix col = x.getCol(i).T();
            double mean = mean(col);
            double std = std(col);
            for (int j = 0; j < x.getRowCount(); j++) {
                normalized.set((x.get(j, i) - mean) / (std + Double.MIN_VALUE), j, i);
            }
        }
        return normalized;
    }

    public static Matrix maxNormalization(Matrix x) {
        Matrix normalized = new Matrix(x.getRowCount(), x.getCollumCount());

        for (int i = 0; i < x.getCollumCount(); i++) {
            Matrix[] col = mergeSort(x.getCol(i).T());
            double max = col[0].get(0, col[0].getCollumCount() - 1);
            if (max == 0) {
                max = Double.MIN_VALUE;
            }

            for (int j = 0; j < x.getRowCount(); j++) {
                normalized.set(x.get(j, i) / max, j, i);
            }
        }
        return normalized;
    }

    public static Matrix meanc(Matrix x) {
        Matrix means = new Matrix(1, x.getCollumCount());
        for (int i = 0; i < means.getCollumCount(); i++) {
            means.set(mean(x.getCol(i)), 0, i);
        }
        return means;
    }

    public static double mean(Matrix x) {
        double sum = 0;
        for (int i = 0; i < x.getCollumCount(); i++) {
            sum += x.get(0, i);
        }
        return sum / (double) x.getCollumCount();
    }

    public static double std(Matrix x) {
        return Math.sqrt(var(x));
    }

    public static double var(Matrix x) {
        double var = 0;
        double mean = mean(x);
        for (int i = 0; i < x.getCollumCount(); i++) {
            var += Math.pow(x.get(0, i) - mean, 2);
        }
        return var / ((double) x.getCollumCount() - 1.0);
    }

    public static Matrix[] splitData(Matrix x, Matrix c, double trainPercentage) {
        Matrix uniqueClasses = unique(c);
        Matrix[] dataPerClass = new Matrix[uniqueClasses.getCollumCount()];

        for (int i = 0; i < dataPerClass.length; i++) {
            dataPerClass[i] = new Matrix(0, x.getCollumCount());
        }

        for (int j = 0; j < x.getRowCount(); j++) {
            int cl = (int) c.get(j, 0);
            dataPerClass[cl] = dataPerClass[cl].vcat(x.getRow(j));
        }

        int sampleSize = x.getRowCount();
        int trainSamples = (int) (sampleSize * trainPercentage);
        Matrix trainData = new Matrix(trainSamples, x.getCollumCount());
        Matrix trainClasses = new Matrix(trainSamples, 1);
        Matrix testData = new Matrix(0, x.getCollumCount());
        Matrix testClasses = new Matrix(0, 1);

        int traini = 0;
        int testi = 0;
        for (int cl = 0; cl < dataPerClass.length; cl++) {
            int samplesFromClass = (int) (dataPerClass[cl].getRowCount() * trainPercentage);
            int testFromClass = dataPerClass[cl].getRowCount() - samplesFromClass;
            for (int i = 0; i < samplesFromClass; i++) {
                int idx = (int) (Math.random() * dataPerClass[cl].getRowCount());
                trainData.setRow(traini, dataPerClass[cl].getRow(idx));
                dataPerClass[cl].removeRow(idx);
                trainClasses.set(cl, traini++, 0);
            }

            for (int i = 0; i < dataPerClass[cl].getRowCount(); i++) {
                testData = testData.vcat(dataPerClass[cl].getRow(i));
                testClasses = testClasses.vcat(new Matrix("[" + cl + ";]"));
            }

        }
        return new Matrix[]{trainData, trainClasses, testData, testClasses};

    }

    public static double euclidM(Matrix x, Matrix y) {
        double d = 0;
        for (int i = 0; i < x.getCollumCount(); i++) {
            d += Math.abs(y.get(0, i) - x.get(0, i));
        }
        return d;
    }

    public static Matrix cov(Matrix x) {
        Matrix cov = new Matrix(x.getRowCount(), x.getRowCount());
        for (int i = 0; i < x.getRowCount(); i++) {
            for (int j = 0; j < x.getRowCount(); j++) {
                cov.set(cov(x.getRow(i), x.getRow(j)), i, j);
            }
        }

        return cov;
    }

    public static double cov(Matrix x, Matrix y) {
        double m1 = mean(x);
        double m2 = mean(y);
        double c = 0;
        for (int i = 0; i < x.getCollumCount(); i++) {
            c += (x.get(0, i) - m1) * (y.get(0, i) - m2);
        }
        return c / (double) (x.getCollumCount() - 1.0);

    }

    public static double corr(Matrix x, Matrix y) {
        double covariation = cov(x, y);
        double sigma1 = var(x);
        double sigma2 = var(y);
        return covariation / (sigma1 * sigma2);
    }

    //http://www.cmi.ac.in/~ksutar/NLA2013/iterativemethods.pdf
    public static Matrix[] eig(Matrix a) {
        int n = a.getRowCount();
        double theta = 0;
        Matrix D = a.copy();
        Matrix S = MatrixUtils.eye(n);
        boolean flag = false;
        do {
            flag = false;
            /* Max off dioganal */
            int i = 0, j = 0;
            double max = Math.abs(D.get(i, j));
            for (int p = 0; p < n; p++) {
                for (int q = 0; q < n; q++) {
                    if (q != p && max < Math.abs(D.get(p, q))) {
                        max = Math.abs(D.get(p, q));
                        i = p;
                        j = q;
                    }
                }
            }
            //Rotation angle
            if (D.get(i, i) == D.get(j, j)) {
                if (D.get(i, j) > 0) {
                    theta = Math.PI / 4.0;
                } else {
                    theta = -(Math.PI / 4.0);
                }
            } else {
                theta = 0.5 * Math.atan((2.0 * D.get(i, j)) / (D.get(i, i) - D.get(j, j)));
            }
            //
            Matrix sl = MatrixUtils.eye(n);
            sl.set(Math.cos(theta), i, i);
            sl.set(sl.get(i, i), j, j);
            sl.set(Math.sin(theta), j, i);
            sl.set(-sl.get(j, i), i, j);

            Matrix slt = sl.T();
            D = slt.dot(D).dot(sl);
            S = S.dot(sl);

            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if (i != j && Math.abs(D.get(i, j)) > 0.00001) {
                        flag = true;
                    }
                }
            }
        } while (flag);
        return new Matrix[]{S, D};
    }

    public static double det(Matrix x) {
        if (x.getRowCount() == x.getCollumCount()) {
            return det(x, x.getRowCount());
        } else {
            return 0;
        }
    }

    private static double det(Matrix m, int n) {
        double det = 0;
        if (n == 1) {
            return m.get(0, 0);
        } else if (n == 2) {
            return m.get(0, 0) * m.get(1, 1) - m.get(0, 1) * m.get(1, 0);
        } else {
            int k = 0;
            Matrix c = new Matrix(n, n);
            for (int p = 0; p < n; p++) {
                int h = 0;
                k = 0;
                for (int i = 1; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (j == p) {
                            continue;
                        }
                        c.set(m.get(i, j), h, k);
                        k++;
                        if (k == n - 1) {
                            h++;
                            k = 0;
                        }
                    }
                }
                det = det + m.get(0, p) * Math.pow((-1), p) * det(c, n - 1);
            }
        }
        return det;
    }

    public static Matrix diag(Matrix x) {
        Matrix diag = new Matrix(1, x.getCollumCount());
        for (int i = 0; i < x.getCollumCount(); i++) {
            diag.set(x.get(i, i), 0, i);
        }
        return diag;
    }

    //http://sebastianraschka.com/Articles/2014_pca_step_by_step.html#eig_vec
    public static Matrix[] pca(Matrix x, int eigenValues) {
        Matrix cov = cov(x);
        Matrix[] eig = eig(cov);
        Matrix[] sorted_eigvalues = mergeSort(diag(eig[1]));
        sorted_eigvalues[0] = hreverse(sorted_eigvalues[0]);
        sorted_eigvalues[1] = hreverse(sorted_eigvalues[1]);
        //System.out.println("Sorted eig " + sorted_eigvalues[1]);
        Matrix w = new Matrix(cov.getRowCount(), eigenValues);
        for (int i = 0; i < eigenValues; i++) {
            int colIdx = (int) (sorted_eigvalues[1].get(0, i));
            w.setCol(i, eig[0].getCol(colIdx));
        }

        Matrix y = w.T().dot(x);
        //System.out.println("W=" + w.toStringExcel());

        return new Matrix[]{y, sorted_eigvalues[1].get("0:0", "0:" + eigenValues)};
    }

    public static Matrix and(Matrix a, Matrix b) {
        if (!a.isLogical() || !b.isLogical()) {
            System.err.println("Matrixes must be logical..");
        }
        return a.conditon("x == 1 && y == 1", b);
    }

    public static Matrix or(Matrix a, Matrix b) {
        if (!a.isLogical() || !b.isLogical()) {
            System.err.println("Matrixes must be logical..");
        }
        return a.conditon("x == 1 || y == 1", b);
    }

    public static Matrix xor(Matrix a, Matrix b) {
        if (!a.isLogical() || !b.isLogical()) {
            System.err.println("Matrixes must be logical..");
        }
        return a.conditon("x != y", b);
    }

    
}
