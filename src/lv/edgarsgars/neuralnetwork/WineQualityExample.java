/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import java.util.HashMap;
import java.util.Map;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author edgars.garsneks
 */
public class WineQualityExample {

    public static void main(String[] args) {
        Matrix whine = MatrixUtils.loadFromFile(".\\datasets\\wine\\rewine.csv", ";");
        //  Matrix red = MatrixUtils.loadFromFile(".\\datasets\\wine\\rewine.csv", ";");
        Matrix whineClasses = whine.getCol("-1");
        System.out.println("Min = " + whineClasses.min() + " Max = " + whineClasses.max());
        Matrix classes = whineClasses.conditon("x >= 7");

        whine.removeCol(whine.getCollumCount() - 1);

        whine = MatrixUtils.maxNormalization(whine);
        //Matrix C = classes;
        Matrix C = new Matrix(classes.getRowCount(), 2);
        for (int i = 0; i < classes.getRowCount(); i++) {
            if (classes.get(0, 0) == 1.0) {
                C.set(1, 1, 0);
            } else {
                C.set(1, 0, 1);
            }
        }
        // Matrix[] trainTestData = MatrixUtils.splitData(whiteWhine, classes, 0.75);
        Map<String, String> params = new HashMap<String, String>();
        params.put("maxIterations", "7000");
        params.put("minError", "0.2");
        params.put("mutationRate", "0.12");
        params.put("populationSize", "50");
        params.put("elites", "3");
        GeneticNetTrainer trainer = new GeneticNetTrainer();
        FeedForwardNet net = new FeedForwardNet(whine.getCollumCount(), 10, 10, 1, C.getCollumCount());
        Matrix[] w = trainer.train(net, whine, C, params);
        net.setWeights(w);

        System.out.println(net.feedforward(whine));

    }
}
