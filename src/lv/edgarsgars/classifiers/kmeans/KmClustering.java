/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.classifiers.kmeans;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author edgars.garsneks
 */
public class KmClustering {

    /**
     *
     * @param data
     * @param clusterCount
     * @param maxIterations
     * @return
     */
    public static Matrix[] cluster(Matrix data, int clusterCount, int maxIterations) {
        int iterations = 0;
        Matrix labels = new Matrix(data.getRowCount(), 1);
        Matrix centroids = MatrixUtils.rand(clusterCount, data.getCollumCount());
        boolean isDone = false;
        while (!isDone && iterations < maxIterations) {
            iterations++;
            Matrix[] distances = new Matrix[clusterCount];
            for (int i = 0; i < clusterCount; i++) {
                distances[i] = MatrixUtils.distances(centroids.getRow(i), data, "euclid");
            }

            Matrix means = new Matrix(clusterCount, data.getCollumCount());
            Matrix n = new Matrix(clusterCount, 1);
            for (int i = 0; i < data.getRowCount(); i++) {
                int minClass = 0;
                for (int cl = 0; cl < clusterCount; cl++) {
                    if (distances[cl].get(0, i) < distances[minClass].get(0, i)) {
                        minClass = cl;
                    }
                }
                labels.set(minClass, i, 0);
                means.setRow(minClass, means.getRow(minClass).add(data.getRow(i)));
                n.addTo(1, minClass, 0);
            }
            for (int i = 0; i < n.getRowCount(); i++) {
                means.setRow(i, means.getRow(i).scalar(1.0 / n.get(i, 0) + 0.000000000000000001));
            }
            // means = means.scalar(MathUtils.pow(n, -1));
            isDone = centroids.equals(means);
            centroids = means;
        }
        return new Matrix[]{labels, centroids};
    }

}
