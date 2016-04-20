/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.Matrix;

/**
 *
 * @author Edgar_000
 */
public interface NeuralNetwork {

    public Matrix getOutput();

    public Matrix getInput();

    public void setInput(Matrix vector);

    public Matrix predict(Matrix data);


    public double train(Matrix data, Matrix classes, TrainingMethod trainMethod);

    public Matrix[] getWeights();

    public Matrix[] getNeurons();

    public Matrix getNeuronCounts();

    public void setWeights(Matrix[] weights);

}
