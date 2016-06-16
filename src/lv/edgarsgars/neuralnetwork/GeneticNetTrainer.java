/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.neuralnetwork;

import java.util.HashMap;
import java.util.Map;
import lv.edgarsgars.mathematics.MathUtils;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author edgars.garsneks
 */
public class GeneticNetTrainer implements FFNTrainer {

    private Matrix[] originalWeights;
    private FeedForwardNet net;

    private Matrix population;
    private Matrix x;
    private Matrix y;
    private double mutationRate;
    private int maxIterations;
    private double minError;
    private int elites;
    private int populationSize;

    private void init(FeedForwardNet net, Map<String, String> params) {

        populationSize = params.containsKey("populationSize") ? Integer.parseInt(params.get("populationSize")) : 100;
        mutationRate = params.containsKey("mutationRate") ? Double.parseDouble(params.get("mutationRate")) : 0.12;
        maxIterations = params.containsKey("mutationRate") ? Integer.parseInt(params.get("maxIterations")) : 1000;
        minError = params.containsKey("minError") ? Double.parseDouble(params.get("mutationRate")) : 0.0001;
        elites = params.containsKey("elites") ? Integer.parseInt(params.get("elites")) : 0;
        this.net = net;

        originalWeights = net.getWeights();
        int geneCount = 0;
        for (Matrix w : originalWeights) {
            geneCount += w.getRowCount() * w.getCollumCount();
        }
        population = MatrixUtils.rand(populationSize, geneCount, -5, 5);
    }

    @Override
    public Matrix[] train(FeedForwardNet net, Matrix x, Matrix y) {
        System.out.println("Train");
        init(net, new HashMap<String, String>());
        this.x = x;
        this.y = y;
        population = createNewPopulation();
        return null;
    }

    private Matrix createNewPopulation() {
        Matrix newGen = new Matrix(0, 0);
        Matrix fit = fitness();
        Matrix[] sorted = MatrixUtils.mergeSort(fit.T());
        for (int i = 0; i < elites; i++) {
            newGen = newGen.vcat(population.getRow((int) sorted[1].get(0, i)));
        }

        while (newGen.getRowCount() < populationSize - populationSize * 0.1) {
            int[] parentIDX = getParents(fit);
            Matrix parentA = population.getRow(parentIDX[0]);
            Matrix parentB = population.getRow(parentIDX[1]);
            newGen = newGen.vcat(getChilds(parentA, parentB));
        }

        newGen = newGen.vcat(MatrixUtils.rand(populationSize - newGen.getRowCount(), population.getCollumCount()));

        return newGen;
    }

    private int[] getParents(Matrix sortedFit) {
        int[] parents = new int[]{1, 1};
        int i = 0;
        do {
            int idx = (int) (Math.random() * sortedFit.getRowCount());
            if (Math.random() < sortedFit.get(0, 0) / sortedFit.get(idx, 0)) {
                parents[i++] = idx;
            }
        } while (i != 2);
        return parents;
    }

    private Matrix fitness() {
        Matrix fit = new Matrix(population.getRowCount(), 1);
        for (int i = 0; i < population.getRowCount(); i++) {
            net.setWeights(genesToWeights(population.getRow(i)));
            Matrix v = net.feedforward(x);
            double error = MathUtils.pow(v.sub(y), 2).sum() / y.getCollumCount();
            fit.set(error, i, 0);
        }
        return fit;
    }

    @Override
    public Matrix[] train(FeedForwardNet net, Matrix x, Matrix y, Map<String, String> params) {
        init(net, params);
        this.x = x;
        this.y = y;
        double error = 1;
        for (int i = 0; i < maxIterations && minError < error; i++) {
            population = createNewPopulation();
            double minErr = fitness().min();
            if (minErr != error) {
                error = minErr;
                System.out.println("[" + i + "] Error " + error);
            }

        }

        System.out.println("Error " + error);
        return genesToWeights(population.getRow(0));
    }

    private Matrix[] genesToWeights(Matrix genes) {
        int idx = 0;
        Matrix[] weights = new Matrix[originalWeights.length];
        for (int i = 0; i < originalWeights.length; i++) {
            int r = originalWeights[i].getRowCount();
            int k = originalWeights[i].getCollumCount();
            weights[i] = genes.get("0:0", idx + ":" + (idx + ((r * k) - 1))).reshape(r, k);
            idx += (r * k);
        }
        return weights;
    }

    public Matrix getChilds(Matrix parentA, Matrix parentB) {
        Matrix childs = new Matrix(2, parentA.getCollumCount());
        int idx = (int) ((parentA.getCollumCount() - 1) * Math.random());
        childs.setRow(0, parentA.get("0:0", "0:" + idx).hcat(parentB.get("0:0", (idx + 1) + ":" + (parentB.getCollumCount() - 1))));
        childs.setRow(1, parentB.get("0:0", "0:" + idx).hcat(parentA.get("0:0", (idx + 1) + ":" + (parentB.getCollumCount() - 1))));

        childs = childs.add(childs.conditon("Math.random() < " + mutationRate).scalar(MatrixUtils.rand(childs.getRowCount(), childs.getCollumCount(), -1.5, 1.5)));

        if (Math.random() < mutationRate) {
            childs.set(Math.random() * 2 - 1, (int) (Math.random() * 2), (int) (Math.random() * childs.getCollumCount()));
        }

        return childs;
    }

}
