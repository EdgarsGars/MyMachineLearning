/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Statistics;

/**
 *
 * @author Edgar_000
 */
public class NeuralNetworkTest {

    public static void main(String[] args) {
        int[] setup = new int[]{2, 2, 1};
        Matrix input = new Matrix("[1 0;0 1; 1 1; 0 0]");
        Matrix output = new Matrix("[1;1;0;0]");
        NeuralNetwork net = new FeedForwardNet(setup);

        double error = net.train(input, output, new BackPropogation());
        
        System.out.println("[1 0] " + net.predict(new Matrix("[1 0]")));
        System.out.println("[1 1] " + net.predict(new Matrix("[1 1]")));
        System.out.println("[0 0] " + net.predict(new Matrix("[0 0]")));
        System.out.println("[0 1] " + net.predict(new Matrix("[0 1]")));
    }
}
