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
public interface TrainingMethod {

    public double train(NeuralNetwork net, Matrix data, Matrix classes);

}
