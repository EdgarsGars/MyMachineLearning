package lv.edgarsgars.classifiers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import lv.edgarsgars.mathematics.Matrix;

/**
 * @author edgars.garsneks
 */
public class Assigment1 {

    public static Matrix parseInput(String file) {
        String values = "[";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s = "";
            reader.readLine();
            while ((s = reader.readLine()) != null) {
                s = s.replaceAll("GP", "0").replace("MS", "1");
                s = s.replaceAll("M", "0").replace("F", "1");
                s = s.replaceAll("R", "0").replace("U", "1");
                s = s.replaceAll("GT3", "0").replace("LE3", "1");
                s = s.replaceAll("T", "0").replace("A", "1");
                s = s.replaceAll("at_home", "0").replace("\"other\"", "1").replaceAll("services", "2").replace("health", "3").replaceAll("teacher", "4");
                s = s.replaceAll("yes", "1").replace("no", "0");
                s = s.replaceAll("course", "0").replace("home", "2").replaceAll("reputation", "3");
                s = s.replaceAll("father", "0").replace("mother", "2");
                s = s.replaceAll("\"", "").replaceAll(";", " ").replace("\\n", ";").replace("\r", "").replaceAll(",", " ");
                values += s + ";";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Matrix x = new Matrix(values + "]");
        for (int i = 0; i < x.getRowCount(); i++) {

            if (x.get(i, x.getCollumCount() - 1) <= 5) {
                x.set(0, i, x.getCollumCount() - 1);
            } else if (x.get(i, x.getCollumCount() - 1) <= 10) {
                x.set(1, i, x.getCollumCount() - 1);
            } else if (x.get(i, x.getCollumCount() - 1) <= 15) {
                x.set(2, i, x.getCollumCount() - 1);
            } else if (x.get(i, x.getCollumCount() - 1) <= 20) {
                x.set(3, i, x.getCollumCount() - 1);
            }

        }
        //  System.out.println(x);
        return x;
    }

    public static synchronized void appendToFile(Matrix x, String filename) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename, true));
            for (Matrix matrix : x) {
                out.print(matrix.toStringExcel());
                out.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(Assigment1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
