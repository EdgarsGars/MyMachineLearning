/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import lv.edgarsgars.mathematics.MathUtils;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.CustomFunction;
import lv.edgarsgars.utils.MatrixUtils;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author Edgar_000
 */
public class BackPropogation implements TrainingMethod {

    public double learningRate = 0.2;
    public double momentum = 0.5;
    public Matrix[] prevUpdate = null;

    public CustomFunction sigmoid = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return 1.0f / (1.0f + Math.exp(-val));
        }
    };

    public CustomFunction dsigmoid = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return sigmoid.functionOf(val) * (1 - sigmoid.functionOf(val));
        }

    };

    @Override
    public double train(NeuralNetwork net, Matrix data, Matrix classes) {
        init(net);
        data = MatrixUtils.getOnes(data.getRowCount(), 1).hcat(data);
        for (int i = 0; i < 100000; i++) {
            int index = (int) (Math.random() * data.getRowCount());
            Matrix set = data.getRow(index);
            Matrix exp = classes.getRow(index);
            Matrix[] a = net.getNeurons();
            Matrix[] w = net.getWeights();
            
            a[0] = set;
            for (int l = 0; l < net.getWeights().length; l++) {
                Matrix dotv = a[l].dot(w[l]);
                Matrix act = sigmoid(dotv);
                a[l + 1] = act;
            }

            Matrix error = exp.add(a[a.length - 1].scalar(-1));
            List<Matrix> deltas = new ArrayList<Matrix>();
            deltas.add(dsigmoid(a[a.length - 1]).scalar(error));
            System.out.println("Error " + error);

            for (int l = a.length - 2; l > 0; l--) {

                Matrix dl = deltas.get(deltas.size() - 1);
                Matrix wl = w[l];
                Matrix wlt = wl.T();
                Matrix al = a[l];
                Matrix dal = dsigmoid(al);
                Matrix dot = dl.dot(wlt);
                Matrix sc = dot.scalar(dal);
                deltas.add(sc);

            }

            List<Matrix> rdeltas = new ArrayList<Matrix>();
            for (int l = deltas.size()-1; l >= 0; l--) {
                rdeltas.add(deltas.get(l));
            }
            
            for (int l =0;l<rdeltas.size();l++){
                Matrix layer = a[l];
                Matrix delta = rdeltas.get(l);
                w[l] = w[l].add(layer.T().dot(delta).scalar(learningRate));
            }
            net.setWeights(w);
        }

        return 0;
    }

    public Matrix sigmoid(Matrix m) {
        return MathUtils.applyFunction(m, sigmoid);
    }

    public Matrix dsigmoid(Matrix m) {
        return MathUtils.applyFunction(m, dsigmoid);
    }

    public void init(NeuralNetwork net) {
        if (prevUpdate == null) {
            Matrix[] w = net.getWeights();
            prevUpdate = new Matrix[w.length];
            for (int i = 0; i < prevUpdate.length; i++) {
                prevUpdate[i] = new Matrix(w[i].getRowCount(), w[i].getCollumCount());
            }
        }

    }

}
