/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.MathUtils;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.CustomFunction;
import lv.edgarsgars.utils.MatrixUtils;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author Edgar_000
 */
public class FeedForwardNet implements NeuralNetwork {

    private Matrix input;
    private Matrix[] neurons;
    private Matrix[] weights;
    private CustomFunction sigmoid = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return 1.0 / (1.0 + Math.exp(-val));
        }
    };

    public FeedForwardNet(int[] sizes) {
        neurons = new Matrix[sizes.length];
        for (int i = 0; i < sizes.length - 1; i++) {
            // System.out.println("Creating layer with size " + sizes[i]);
            neurons[i] = new Matrix(sizes[i] + 1, 1);
            //  System.out.println(neurons[i]);
        }
        neurons[neurons.length - 1] = new Matrix(sizes[sizes.length - 1], 1);
        weights = new Matrix[sizes.length - 1];
        for (int i = 0; i < weights.length; i++) {
            //weights[i] = MatrixUtils.getRandom(neurons[i].size(), neurons[i + 1].size(), -1, 1);
            weights[i] = MatrixUtils.getOnes(neurons[i].size(), neurons[i + 1].size()).scalar(0.5);
        }
    }

    @Override
    public Matrix getOutput() {
        return MathUtils.applyFunction(neurons[neurons.length - 1], sigmoid);
    }

    public void feedForward() {

        neurons[0] = input;
        for (int l = 0; l < getWeights().length; l++) {
            Matrix dotv = neurons[l].dot(weights[l]);
            
            System.out.println(dotv);
            Matrix act = sigmoid(dotv);
            neurons[l + 1] = act;
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
    public Matrix[] getNeurons() {
        Matrix[] v = new Matrix[neurons.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = neurons[i].copy();
        }
        return v;
    }

    @Override
    public Matrix getNeuronCounts() {
        Matrix sizes = new Matrix(1, neurons.length);
        for (int i = 0; i < neurons.length; i++) {
            sizes.set(neurons[i].size(), 0, i);
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

    @Override
    public Matrix predict(Matrix data) {
         data = MatrixUtils.getOnes(data.getRowCount(), 1).hcat(data);
        Matrix[] y = new Matrix[data.getRowCount()];
        Matrix[] dataRows = data.getRows();
        for (int i = 0; i < dataRows.length; i++) {
            y[i] = new Matrix(1, neurons[neurons.length - 1].getCollumCount());
            setInput(dataRows[i]);
            feedForward();
        }
        return new Matrix(y);
    }

    @Override
    public Matrix getInput() {
        return input.copy();
    }

    @Override
    public void setInput(Matrix vector) {
        neurons[0] = vector;
        input = vector;
    }

    public Matrix sigmoid(Matrix m) {
        return MathUtils.applyFunction(m, sigmoid);
    }
}
