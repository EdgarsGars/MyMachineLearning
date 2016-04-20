/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.classifiers.knn;

import java.util.HashMap;
import java.util.HashSet;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author Edgar_000
 */
public class KnnClassifier {

    protected int k = 1;
    protected HashSet<Double> classes = new HashSet<Double>();
    protected Matrix points;
    protected Matrix pointClasses;

    public KnnClassifier(int dim) {
        points = new Matrix(0, dim);
        pointClasses = new Matrix(0, 0);
    }

    public void train(Matrix x, Matrix y) {
        points = points.vcat(x);
        pointClasses = pointClasses.vcat(y);
        for (int i = 0; i < y.getRowCount(); i++) {
            classes.add(y.get(i, 0));
        }
    }

    public Matrix predict(Matrix x, int k) {
        Matrix y = new Matrix(x.getRowCount(), 1);

        for (int j = 0; j < x.getRowCount(); j++) {
            Matrix d = MatrixUtils.distances(x.getRow(j), points, "euclid");
            Matrix[] r = MatrixUtils.mergeSort(d);
            int[] nn = new int[classes.size()];
            int maxClass = 0;
            for (int i = 1; i <= Math.min(k, r.length); i++) {
                int idx = (int) ((r[1].get(0, i)));
                double ci = pointClasses.get(idx, 0);
                nn[(int) ci]++;
                if (nn[maxClass] < nn[(int) ci]) {
                    maxClass = (int) ci;
                }
            }

            int maxN = 0;
            for (int i = 0; i < nn.length; i++) {
                if (nn[i] == nn[maxClass]) {
                    maxN++;
                }
            }
            if (maxN > 1) {
                /*
                for (int i = 1; i < r[1].getCollumCount()+1; i++) {
                    int idx = (int) ((r[1].get(0, i)));
                    double ci = pointClasses.get(idx, 0);
                    if (nn[(int) ci] == nn[maxClass]) {
                        maxClass = (int) ci;
                        break;
                    }

                }
                */  maxClass = (int) (predict(x.getRow(j), k - 1).get(0, 0));
            }
            y.set(maxClass, j, 0);

        }
        return y;
    }

    public Matrix[] getPointsByClass() {
        Matrix[] pointsPerClass = new Matrix[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            pointsPerClass[i] = new Matrix(0, points.getCollumCount());
            for (int c = 0; c < pointClasses.getRowCount(); c++) {
                if ((int) (pointClasses.get(c, 0)) == i) {
                    pointsPerClass[i] = pointsPerClass[i].vcat(points.getRow(c));
                }
            }
        }
        return pointsPerClass;
    }

}
