/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.classifiers.knn;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import lv.edgarsgars.mathematics.Matrix;

/**
 *
 * @author Edgar_000
 */
public class CnnClassifier extends KnnClassifier {
    
    private Matrix originalPoints;
    private Matrix originalClasses;
    private ArrayList<Integer> outliers = new ArrayList<Integer>();
    
    public CnnClassifier(int k, int dim) {
        super(dim);
        
    }

    /*
    private void reducePoints() {
        long startTime = System.nanoTime();

        for (int i = 0; i < originalPoints.getRowCount(); i++) {
            Matrix x = new Matrix(0,0);
            Matrix y =  new Matrix(0,0);
            for (int j = 0; j < originalPoints.getRowCount(); j++) {
                if (j != i || !outliers.contains(i)) {
                    x = x.vcat(originalPoints.getRow(j));
                    y = y.vcat(originalClasses.getRow(j));
                }
            }
            points = x;
            pointClasses = y;
            if (predict(originalPoints.getRow(i)).get(0, 0) != originalClasses.get(i, 0)) {
                outliers.add(i);
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Time to execute " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        startTime = System.nanoTime();
        points = new Matrix(0, 0);
        pointClasses = new Matrix(0, 0);
        for (int i = 0; i < originalPoints.getRowCount(); i++) {
            if (!outliers.contains(i)) {
                points = points.vcat(originalPoints.getRow(i));
                pointClasses = pointClasses.vcat(originalClasses.getRow(i));
            }
        }
        k = 1;
        boolean newPrototypes = true;
        boolean[] b = new boolean[originalPoints.getRowCount()];
        while (newPrototypes) {
            newPrototypes = false;
            for (int i = 0; i < originalPoints.getRowCount(); i++) {
                if (!b[i]) {
                    if (predict(originalPoints.getRow(i)).get(0, 0) != originalClasses.get(i, 0)) {
                        points = points.vcat(originalPoints.getRow(i));
                        pointClasses = pointClasses.vcat(originalClasses.getRow(i));
                        b[i] = true;
                        newPrototypes = true;
                    }
                }
            }
        }

        endTime = System.nanoTime();
        System.out.println("Time to execute " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        System.out.println("samples = " + points);
        System.out.println("samplesC = " + pointClasses.toString());
    }
     */
    private void reducePoints() {
        Matrix pointsAfterOutliers = new Matrix(0, 0);
        Matrix classesAfterOutliers = new Matrix(0, 0);
        for (int i = 0; i < originalPoints.getRowCount(); i++) {
            Matrix x = originalPoints.copy();
            Matrix y = originalClasses.copy();
            x.removeRow(i);
            y.removeRow(i);
            points = x;
            pointClasses = y;
            if (predict(originalPoints.getRow(i)).get(0, 0) == originalClasses.get(i, 0)) {
                pointsAfterOutliers = pointsAfterOutliers.vcat(originalPoints.getRow(i));
                classesAfterOutliers = classesAfterOutliers.vcat(originalClasses.getRow(i));
            }
        }
        k = 1;
        
        boolean newPrototypes = true;
        boolean[] b = new boolean[pointsAfterOutliers.getRowCount()];
        int index = (int) (Math.random() * pointsAfterOutliers.getRowCount());
        int idx2 = (int) (Math.random() * pointsAfterOutliers.getRowCount());
        points = pointsAfterOutliers.getRow(index).vcat(pointsAfterOutliers.getRow(idx2));
        pointClasses = classesAfterOutliers.getRow(index).vcat(classesAfterOutliers.getRow(idx2));
        while (newPrototypes) {
            newPrototypes = false;
            for (int i = 0; i < pointsAfterOutliers.getRowCount(); i++) {
                if (!b[i]) {
                    if (predict(pointsAfterOutliers.getRow(i)).get(0, 0) != classesAfterOutliers.get(i, 0)) {
                        points = points.vcat(pointsAfterOutliers.getRow(i));
                        pointClasses = pointClasses.vcat(classesAfterOutliers.getRow(i));
                        //  System.out.println("Point " + i + " is classified incorrect " + points.getRowCount());
                        b[i] = true;
                        newPrototypes = true;
                    }
                }
            }
        }
        
        System.out.println("Reduced from " + originalPoints.getRowCount() + " to " + points.getRowCount() + " points");
        
        System.out.println("samples = " + points);
        System.out.println("samplesC = " + pointClasses.toString());
    }
    
    public void train(Matrix x, Matrix y) {
        super.train(x, y);
        originalPoints = points.copy();
        originalClasses = y.copy();
        reducePoints();
    }
    
    public Matrix predict(Matrix x) {
        return super.predict(x, 1);
    }
    
    @Deprecated
    public Matrix predict(Matrix x, int k) {
        return super.predict(x, 1);
    }
}
