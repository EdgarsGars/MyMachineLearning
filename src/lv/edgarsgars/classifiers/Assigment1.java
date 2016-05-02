/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.classifiers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import lv.edgarsgars.classifiers.knn.CnnClassifier;
import lv.edgarsgars.classifiers.knn.KnnClassifier;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author Edgar_000
 */
public class Assigment1 {

    public static void main(String[] args) throws InterruptedException {
        int it = 100;
        ExecutorService exe;
        Matrix stundetMat = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\student-mat.csv");
        Matrix stundetPor = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\student-por.csv");
        //Matrix both = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\both.csv");
        Matrix data = stundetMat.vcat(stundetPor);
        int classCollum = data.getCollumCount() - 1;
        final Matrix classes = data.getCol(classCollum);
        //System.out.println(MatrixUtils.cov(MatrixUtils.zscore(data).T()).toStringExcel());
        data.removeCol(classCollum);

        final Matrix normalizedData = MatrixUtils.zscore(data);

        for (int k = 1; k < 10; k++) {
            for (int n = 1; n < 32; n++) {
                final Matrix result = new Matrix("[" + k + " " + n + " " + 0 + "]");
                exe = Executors.newFixedThreadPool(7);
                for (int iterations = 0; iterations < it; iterations++) {
                    final int K = k, N = n;
                    exe.submit(new Runnable() {
                        @Override
                        public void run() {
                            Matrix pcaData = MatrixUtils.pca(normalizedData.T(), N).T();
                            Matrix[] trainTestData = MatrixUtils.splitData(pcaData, classes, 0.75);
                            KnnClassifier knn = new KnnClassifier(pcaData.getCollumCount());
                            knn.train(trainTestData[0], trainTestData[1]);
                            Matrix y = knn.predict(trainTestData[2], K);

                            Matrix conf = MatrixUtils.confusionMatrix(trainTestData[3], y);
                            //  System.out.println("trainX = " + trainTestData[0] + ";");
                            //  System.out.println("trainY = " + trainTestData[1] + ";");
                            //   System.out.println("testX = " + trainTestData[2] + ";");
                            //   System.out.println("testY = " + trainTestData[3] + ";");
                            //   System.out.println("y = " + y + ";");

                            //  System.out.println(conf.toStringExcel());
                            double error = 0;
                            for (int i = 0; i < conf.getCollumCount(); i++) {
                                error += conf.get(i, i);
                            }
                            error = 1.0 - error / (double) y.getRowCount();
                            //System.out.println("Error\t=\t" + (1.0 - error / (double) y.getRowCount()));
                            result.addTo(error, 0, 2);
                            //System.out.println("Done");
                        }
                    });

                }
                exe.shutdown();
                exe.awaitTermination(1, TimeUnit.DAYS);
                // System.out.println("" + (k * n) / (8.0 * 31.0) + "%");

                result.set(result.get(0, 2) / (double) it, 0, 2);
                //System.out.println(result);
                appendToFile(result, "./resultsManhatan.txt");
                System.out.println(result);
            }
        }
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
