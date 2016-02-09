/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

}
