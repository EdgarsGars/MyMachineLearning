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
public class Statistics {

    public static Vector leastSquere(Vector x, Vector y) {
        int elementCount = x.size();
        double meanx = 0;
        double meany = 0;
        double meanxy = 0;
        double meanxx = 0;

        for (int i = 0; i < elementCount; i++) {
            meanx += x.get(i);
            meany += y.get(i);
            meanxy += x.get(i) * y.get(i);
            meanxx += x.get(i) * x.get(i);
        }

        meanx /= elementCount;
        meany /= elementCount;
        meanxy /= elementCount;
        meanxx /= elementCount;

        double m = (meanx * meany - meanxy) / (meanx * meanx - meanxx);
        double b = meany - m * meanx;

        double variancey = 0;
        double sqerror = 0;

        for (int i = 0; i < elementCount; i++) {
            variancey += (y.get(i) - meany) * (y.get(i) - meany);
            sqerror += Math.pow(y.get(i) - (m * x.get(i) + b), 2);
        }
        double r = 1.0 - (sqerror / variancey);
        return new Vector(m, b, r);
    }
    
    
    

}
