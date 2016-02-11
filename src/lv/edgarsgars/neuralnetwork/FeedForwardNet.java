/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Vector;
import lv.edgarsgars.utils.MatrixUtils;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author Edgar_000
 */
public class FeedForwardNet implements NeuralNetwork {

    private Vector[] neurons;
    private Matrix[] weights;

    public FeedForwardNet(int[] sizes) {
        neurons = new Vector[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            // System.out.println("Creating layer with size " + sizes[i]);
            neurons[i] = new Vector(sizes[i]);
            System.out.println(neurons[i]);
        }
        weights = new Matrix[sizes.length - 1];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = MatrixUtils.getRandom(neurons[i].size(), neurons[i + 1].size(), -1, 1);
            //weights[i] = MatrixUtils.getOnes(neurons[i].size(), neurons[i + 1].size()).scalar(0.5);
        }
    }

    @Override
    public Vector getOutput() {
        return VectorUtils.sigmoid(neurons[neurons.length - 1].copy());
    }

    @Override
    public void setInput(Vector vector) {
        neurons[0] = vector.copy();
    }

    @Override
    public Vector predict(Vector data) {
        setInput(data);
        feedForward();
        return getOutput();
    }

    public void feedForward() {
        for (int i = 1; i < neurons.length; i++) {
            Vector neuronA = neurons[i - 1];
            if (i != 1) {
                neuronA = VectorUtils.sigmoid(neurons[i - 1]);
            }
            neurons[i] = new Vector(neurons[i].size());
            Vector neuronB = neurons[i];
            Matrix w = weights[i - 1];
            for (int a = 0; a < neuronA.size(); a++) {
                for (int b = 0; b < neuronB.size(); b++) {
                    //  System.out.println(i + " " + k + " " + r);
                    neuronB.set(b, neuronB.get(b) + w.get(a, b) * neuronA.get(a));

                  

                }
                //System.out.println("");
            }
        }
    }

    @Override
    public double train(Matrix input, Matrix output, TrainingMethod trainMethod) {
        return trainMethod.train(this, input, output);
    }

    @Override
    public Matrix[] getWeights() {
        Matrix[] m = new Matrix[weights.length];
        for (int i = 0; i < m.length; i++) {
            m[i] = weights[i].copy();
        }
        return m;
    }

    @Override
    public Vector[] getNeurons() {
        Vector[] v = new Vector[neurons.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = neurons[i].copy();
        }
        return v;
    }

    @Override
    public Vector getNeuronCounts() {
        Vector sizes = new Vector();
        for (int i = 0; i < neurons.length; i++) {
            sizes.add(neurons[i].size());
        }
        return sizes;
    }

    @Override
    public void setWeights(Matrix[] weights) {
        this.weights = weights;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < neurons.length - 1; i++) {
            s += "N" + i + " " + neurons[i] + "\n";
            s += "W" + i + " " + weights[i] + "\n";
        }
        s += "N" + (neurons.length - 1) + " " + neurons[neurons.length - 1] + "\n";
        return s;
    }

}
