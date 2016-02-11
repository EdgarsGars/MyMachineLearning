/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Statistics;
import lv.edgarsgars.mathematics.Vector;

/**
 *
 * @author edgars.garsneks
 */
public class VectorUtils {

    private static int j = 0;

    public static Vector[] sortVectorsByVector(Vector[] vectors) {
        j = 0;
        Vector[] sortedVectors = new Vector[vectors.length];
        List x = vectors[0].asList();

        final ArrayList<Integer> sortingReturn = new ArrayList<>();

        Collections.sort(x, new Comparator<Double>() {

            @Override
            public int compare(Double lhs, Double rhs) {
                // TODO Auto-generated method stub
                int returning = lhs.compareTo(rhs);
                sortingReturn.add(returning);
                return returning;
            }

        });
        sortedVectors[0] = new Vector(x);
        System.out.println(sortingReturn);
        // now sort the list B according to the changes made with the order of
        // items in listA
        for (int i = 1; i < vectors.length; i++) {
            List list = vectors[i].asList();
            Collections.sort(list, new Comparator<Double>() {
                @Override
                public int compare(Double lhs, Double rhs) {
                    // TODO Auto-generated method stub

                    // comparator method will sort the second list also according to
                    // the changes made with list a
                    int returning = sortingReturn.get(j);
                    j++;
                    return returning;
                }

            });
            sortedVectors[i] = new Vector(list);
        }

        return sortedVectors;
    }

    public static Vector pow(Vector vec, double pow) {
        Vector powered = new Vector();
        for (Double x : vec) {
            powered.add(Math.pow(x, pow));
        }
        return powered;
    }

    public static Vector sin(Vector vec) {
        Vector sinvec = new Vector();
        for (Double val : vec) {
            sinvec.add(Math.sin(val));
        }
        return sinvec;
    }

    public static Vector cos(Vector vec) {
        Vector cosvec = new Vector();
        for (Double val : vec) {
            cosvec.add(Math.cos(val));
        }
        return cosvec;
    }

    public static Vector sigmoid(Vector vec) {
        Vector sigm = new Vector();
        for (Double x : vec) {
            sigm.add(Statistics.sigmoid(x));
        }
        return sigm;
    }

    public static Vector dsigmoid(Vector vec) {
        Vector dsigm = new Vector();
        for (Double x : vec) {
            dsigm.add(Statistics.dsigmoid(x));
        }
        return dsigm;
    }

    public static Matrix reshape(Vector v, int n, int m) {
        Matrix result = new Matrix(n, m);
        if (v.size() == n * m) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    result.set(i, j, v.get(i * m + j));
                }
            }
        }
        return result;
    }

    public static Vector[] loadDataFromFile(String filepath, String separator) {
        ArrayList<String[]> values = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                values.add(line.split(separator));
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VectorUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VectorUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector[] rez = new Vector[values.size()];
        for (int i = 0; i < rez.length; i++) {
            rez[i] = new Vector();
            for (int j = 0; j < values.get(i).length; j++) {
                rez[i].add(Double.parseDouble(values.get(i)[j]));
            }
        }
        return rez;
    }

}
