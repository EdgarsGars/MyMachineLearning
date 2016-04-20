/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Statistics;

/**
 *
 * @author edgars.garsneks
 */
public class VectorUtils {

    private static int j = 0;

    public static Matrix[] sortVectorsByVector(Matrix vectors) {
        j = 0;
        Matrix[] sortedVectors = new Matrix[vectors.getRowCount()];
        List x = vectors.getRow(0).toList();

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
        sortedVectors[0] = new Matrix(x);
        System.out.println(sortingReturn);
        // now sort the list B according to the changes made with the order of
        // items in listA
        for (int i = 1; i < vectors.getRowCount(); i++) {
            List list = vectors.getRow(i).toList();
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
            sortedVectors[i] = new Matrix(list);
        }

        return sortedVectors;
    }

    

    /*
    public static Matrix loadDataFromFile(String filepath, String separator) {
        
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
    }*/

    //public static void saveToFile(Vector vec, String file, String separator) throws IOException {
        /*
        String s = "";
        for (Double double1 : vec) {
            s += double1 + separator;
        }
        PrintWriter writer = new PrintWriter(new FileWriter(new File(file)));
        writer.print(s.substring(0, s.length() - 1));
        writer.flush();
        writer.close();*/
    //}

   

}
