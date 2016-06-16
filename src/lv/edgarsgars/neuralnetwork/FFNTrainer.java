/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import java.util.Map;
import lv.edgarsgars.mathematics.Matrix;

/**
 *
 * @author edgars.garsneks
 */
public interface FFNTrainer {

    public Matrix[] train(FeedForwardNet net, Matrix trainData, Matrix trainClasses);

    public Matrix[] train(FeedForwardNet net, Matrix trainData, Matrix trainClasses, Map<String, String> params);

    class Backpropogation implements FFNTrainer {

        @Override
        public Matrix[] train(FeedForwardNet net, Matrix trainData, Matrix trainClasses) {
            return null;
        }

        /**
         *
         * @param net
         * @param trainData
         * @param trainClasses
         * @param params
         * @return
         */
        @Override
        public Matrix[] train(FeedForwardNet net, Matrix trainData, Matrix trainClasses, Map<String, String> params) {
            double learningRate = Double.parseDouble(params.get("learningRate"));
            return null;

        }

    }
}
