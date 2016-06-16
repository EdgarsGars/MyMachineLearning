/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author edgars.garsneks
 */
public class FeedForwardNet {

    private Matrix[] weights;
    private Matrix[] layers;

    public FeedForwardNet(int... structure) {
        initializeNet(structure);
    }

    private void initializeNet(int... structure) {
        layers = new Matrix[structure.length];
        weights = new Matrix[structure.length - 1];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = MatrixUtils.rand(structure[i], structure[i + 1], -1, 1);
            //weights[i] = MatrixUtils.ones(structure[i], structure[i + 1]).scalar(0.5);
            //System.out.println("i=" + i + " " + weights[i]);
        }
        //weights[0] = new Matrix("[2 -1;-1 2]");
        // weights[1] = new Matrix("[2 0; 0 2]");
        //    }
        System.out.println("Layers " + layers.length);
        //}
    }

    public Matrix[] getWeights() {
        return weights;
    }

    public void setWeights(Matrix[] weights) {
        this.weights = weights;
    }

    public Matrix feedforward(Matrix x) {

        Matrix y = new Matrix(x.getRowCount(), weights[weights.length - 1].getCollumCount());

        for (int r = 0; r < x.getRowCount(); r++) {
            layers[0] = x.getRow(r);

            for (int i = 0; i < layers.length - 1; i++) {
                layers[i + 1] = ActivationFunctions.sigmoid(layers[i].dot(weights[i]));
            }
            y.setRow(r, layers[layers.length - 1]);
        }
        return y;
    }

}
