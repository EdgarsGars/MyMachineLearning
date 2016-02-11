/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Vector;

/**
 *
 * @author Edgar_000
 */
public interface NeuralNetwork {

    public Vector getOutput();

    public void setInput(Vector vector);

    public Vector predict(Vector data);

    public double train(Matrix data, Matrix classes, TrainingMethod trainMethod);

    public Matrix[] getWeights();

    public Vector[] getNeurons();

    public Vector getNeuronCounts();
    
    public void setWeights(Matrix[] weights);

}
