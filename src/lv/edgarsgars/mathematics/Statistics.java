/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author edgars.garsneks
 */
public class Statistics {
    /*
    public static Vector leastSquare(Vector x, Vector y) {
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

    public static double sigmoid(double d) {
        return 1.0 / (1.0 + Math.exp(-d));
    }

    public static double dsigmoid(double d) {
        return sigmoid(d) * (1.0 - sigmoid(d));
    }

    public static Vector[] hist(Vector v) {
        Map<Double, Integer> hist = new HashMap<>();
        for (Double d : v) {
            if (hist.get(d) != null) {
                hist.put(d, hist.get(d) + 1);
            } else {
                hist.put(d, 1);
            }
        }
        Vector[] res = new Vector[]{new Vector(), new Vector()};
        for (Double d : hist.keySet()) {
            res[0].add(d);
            res[1].add(hist.get(d));
        }

        return res;
    }

    public static double expectedValue(Vector v) {
        Vector[] hist = hist(v);
        Vector probabilty = hist[1].multiply(1.0 / hist[1].totalSum());
        double exp = 0;
        for (int i = 0; i < hist[0].size(); i++) {
            exp += probabilty.get(i) * hist[0].get(i);
        }
        return exp;
    }

    public static Vector[] probabilityDistribution(Vector v) {
        Vector[] rez = hist(v);
        rez[1] = rez[1].multiply(1.0 / rez[1].totalSum());
        return rez;
    }

    public static double varriance(Vector v) {
        double var = 0;
        double mean = v.mean();
        for (Double double1 : v) {
            var += Math.pow(double1 - mean, 2);
        }
        return var / ((double) v.size()-1.0);
    }
    
    public static double std(Vector v){
        return Math.sqrt(varriance(v));
    }*/

}
