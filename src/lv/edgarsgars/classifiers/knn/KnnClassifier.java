package lv.edgarsgars.classifiers.knn;

import java.util.HashSet;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 * K-nearest neighbor classifier implementation as a object for single training
 * and multiple uses
 *
 * @author edgars.garsneks
 */
public class KnnClassifier {

    protected int k = 1;
    protected HashSet<Double> classes = new HashSet<Double>();
    protected Matrix points;
    protected Matrix pointClasses;

    /**
     * Trains KNN classifier by adding all new training points to dataset
     *
     * @param x training data with points specified as rows
     * @param y corresponding point classes
     */
    public void train(Matrix x, Matrix y) {
        if (points == null) {
            points = new Matrix(0, x.getCollumCount());
            pointClasses = new Matrix(0, 0);
        }
        points = points.vcat(x);
        pointClasses = pointClasses.vcat(y);
        for (int i = 0; i < y.getRowCount(); i++) {
            classes.add(y.get(i, 0));
        }
    }

    /**
     * Classifies given data matrix with point as rows
     *
     * @param x data matrix
     * @param k K - param
     * @return Assigned classes for data points
     */
    public Matrix predict(Matrix x, int k) {
        Matrix y = new Matrix(x.getRowCount(), 1);
        //For each point
        for (int j = 0; j < x.getRowCount(); j++) {
            //Calculates distance to all training points
            Matrix d = MatrixUtils.distances(x.getRow(j), points, "manhatan");
            //Sorts distances return indexes and values
            Matrix[] r = MatrixUtils.mergeSort(d);
            //Bins for counting classes
            int[] nn = new int[classes.size()];
            int maxClass = 0;
            //Count k neigbours classes
            for (int i = 0; i < Math.min(k, r[0].getCollumCount()); i++) {
                int idx = (int) ((r[1].get(0, i)));
                double ci = pointClasses.get(idx, 0);
                if (nn[maxClass] < ++nn[(int) ci]) {
                    maxClass = (int) ci;
                }
            }
            //Check if neighbor count is equal
            int maxN = 0;
            for (int i = 0; i < nn.length; i++) {
                if (nn[i] == nn[maxClass]) {
                    maxN++;
                }
            }
            //If there is equal count re-run with K-1
            if (maxN > 1) {
                maxClass = (int) (predict(x.getRow(j), k - 1).get(0, 0));
            }
            y.set(maxClass, j, 0);

        }
        return y;
    }

}
