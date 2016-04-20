/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.classifiers;

import java.io.BufferedReader;
import java.io.FileReader;
import lv.edgarsgars.classifiers.knn.CnnClassifier;
import lv.edgarsgars.classifiers.knn.KnnClassifier;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author Edgar_000
 */
public class Assigment1 {

    public static void main(String[] args) {

        //   for (int k = 1; k < 9; k++) {
        //      for (int n = 1; n < 32; n++) {
        int k = 3;
        Matrix stundetMat = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\student-mat.csv");
        Matrix stundetPor = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\student-mat.csv");

        Matrix data = stundetMat.vcat(stundetPor);
        int classCollum = data.getCollumCount() - 1;

        Matrix normalizedData = MatrixUtils.maxNormalization(data);
        Matrix classes = data.getCol(classCollum);

        normalizedData.removeCol(classCollum);
        // normalizedData = MatrixUtils.pca(normalizedData.transponse(),6).transponse();
        //  System.out.println(normalizedData.toStringExcel());
        Matrix[] trainTestData = MatrixUtils.splitData(normalizedData, classes, 0.75);
        KnnClassifier knn = new KnnClassifier(normalizedData.getCollumCount());
       // KnnClassifier knn = new CnnClassifier(k, normalizedData.getCollumCount());
        knn.train(trainTestData[0], trainTestData[1]);
        Matrix y = knn.predict(trainTestData[2], k);
        Matrix conf = MatrixUtils.confusionMatrix(y, trainTestData[3]);
        //  System.out.println("trainX = " + trainTestData[0] + ";");
        //  System.out.println("trainY = " + trainTestData[1] + ";");
        //  System.out.println("testX = " + trainTestData[2] + ";");
        //  System.out.println("testY = " + trainTestData[3] + ";");
        //  System.out.println("y = " + y + ";");

        System.out.println(conf.toStringExcel());
        double error = 0;
        for (int i = 0; i < conf.getCollumCount(); i++) {
            error += conf.get(i, i);
        }
        System.out.println(k + "\t" + error / (double) trainTestData[2].getRowCount());

        // }
//}
    }

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
                s = s.replaceAll("\"", "").replaceAll(";", " ").replace("\\n", ";").replace("\r", "");
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

}
