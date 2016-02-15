/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Statistics;
import lv.edgarsgars.mathematics.Vector;

/**
 *
 * @author Edgar_000
 */
public class BackPropogation implements TrainingMethod {

    public double learningRate = 0.5;
    public double momentum = 0.2;
    Matrix[] prevUpdate;

    @Override
    public double train(NeuralNetwork net, Matrix data, Matrix classes) {
        double error = 0;

        if (prevUpdate == null) {
            prevUpdate = new Matrix[net.getWeights().length];
            for (int i = 0; i < prevUpdate.length; i++) {
                prevUpdate[i] = new Matrix(net.getWeights()[i].getRowCount(), net.getWeights()[i].getCollumCount());
            }
        }

        for (int i = 0; i < data.getRowCount(); i++) {
            Vector x = data.getRow(i);
            Vector y = classes.getRow(i);

            Vector y1 = net.predict(x);

            Vector[] neurons = net.getNeurons();
            Matrix[] weights = net.getWeights();

            Vector[] errors = new Vector[net.getNeurons().length];
            for (int e = 0; e < errors.length; e++) {
                errors[e] = new Vector(net.getNeurons()[e].size());
            }
            Matrix[] newWeights = new Matrix[net.getWeights().length];
            for (int w = 0; w < newWeights.length; w++) {
                newWeights[w] = new Matrix(net.getWeights()[w].getRowCount(), net.getWeights()[w].getCollumCount());
            }
            //TODO init this shit
            error = 0;
            //delta for output layer
            for (int j = 0; j < y.size(); j++) {
                error += 0.5f * Math.pow(Math.abs(y1.get(j) - y.get(j)), 2);
                errors[errors.length - 1].set(j, Statistics.dsigmoid(neurons[neurons.length - 1].get(j)) * (y1.get(j) - y.get(j)));
            }
            //System.out.println("Error " + error);
            // Delta for hidden
            for (int layer = neurons.length - 2; layer > 0; layer--) {
                Vector A = neurons[layer];
                Vector B = neurons[layer + 1];
                Matrix W = weights[layer];
                for (int a = 0; a < A.size(); a++) {
                    for (int b = 0; b < B.size(); b++) {
                        errors[layer].set(a, errors[layer].get(a) + errors[layer + 1].get(b) * W.get(a, b));
                    }
                    errors[layer].set(a, errors[layer].get(a) * Statistics.dsigmoid(A.get(a)));
                }
            }

            //weights
            for (int layer = 1; layer < neurons.length; layer++) {
                Vector B = neurons[layer];

                for (int b = 0; b < B.size(); b++) {
                    Vector A = neurons[layer - 1];
                    for (int a = 0; a < A.size(); a++) {
                        if (layer == 1) {
                            newWeights[layer - 1].set(a, b, neurons[layer - 1].get(a) * errors[layer].get(b));
                        } else {
                            newWeights[layer - 1].set(a, b, Statistics.sigmoid(neurons[layer - 1].get(a)) * errors[layer].get(b));
                        }
                        prevUpdate[layer - 1].set(a, b, learningRate * newWeights[layer - 1].get(a, b) + momentum * prevUpdate[layer - 1].get(a, b));
                        net.getWeights()[layer - 1].set(a, b, newWeights[layer - 1].get(a, b) + prevUpdate[layer - 1].get(a, b));
                    }
                }

            }
            net.setWeights(newWeights);

        }

        return error;
    }

}
