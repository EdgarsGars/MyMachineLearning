/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm;

import lv.edgarsgars.geneticalgorithm.selectionoperator.RouleteSelector;
import lv.edgarsgars.geneticalgorithm.crossoveroperators.SinglepointCrossover;
import java.util.ArrayList;
import lv.edgarsgars.geneticalgorithm.GA.TARGET;
import lv.edgarsgars.geneticalgorithm.commongenes.DoubleGene;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Vector;

/**
 *
 * @author Edgar_000
 */
public class GALinearSystemSolvinExample {

    /*
        Example for solving linear system for vector x using genetic algorithm
    
        A = [1 1; -1 2; 2 1];
        b = [2; 2; 3];
        x = [x1; x2];
        
        Ax = b;
     */
    public static void main(String[] args) {
        int populationSize = 100;
        int iterations = 5000;
        int bz;
        double mutationRate = 0.25;
        double elitism = 0.15;

        GA ga = new GA() {

            @Override
            public double fitness(Chromosome c) {
                ArrayList<DoubleGene> genes = (ArrayList<DoubleGene>) c.genes;
                double x1 = genes.get(0).getValue();
                double x2 = genes.get(1).getValue();
                Matrix A = new Matrix(new double[][]{{1, 1}, {-1, 2}, {2, 1}});
                Matrix b = new Vector(2, 2, 3).transponse();
                Matrix x = new Vector(x1, x2).transponse();

                Matrix Ax = A.dot(x);
                double dist = Math.sqrt(Math.pow(Ax.get(0, 0) - b.get(0, 0), 2) + Math.pow(Ax.get(1, 0) - b.get(1, 0), 2));
                return dist;
            }

            @Override
            public void mutate(Chromosome c) {
                c.mutateGenes(0.7);
            }
        };

        ArrayList<Chromosome> population = new ArrayList<Chromosome>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Chromosome<DoubleGene>(2, DoubleGene.class));
        }

        Chromosome[] pop = (Chromosome<DoubleGene>[]) population.toArray(new Chromosome[populationSize]);
        for (int i = 0; i < iterations; i++) {
            pop = ga.createNewPopulation(pop, Chromosome.class, mutationRate, elitism, new RouleteSelector(), new SinglepointCrossover(), TARGET.MINIMIZE);
        }

        ArrayList<DoubleGene> genes = (ArrayList<DoubleGene>) pop[0].genes;
        double x1 = genes.get(0).getValue();
        double x2 = genes.get(1).getValue();
        Matrix A = new Matrix(new double[][]{{1, 1}, {-1, 2}, {2, 1}});
        Matrix b = new Vector(2, 2, 3).transponse();
        Matrix x = new Vector(x1, x2).transponse();

        Matrix Ax = A.dot(x);
        double dist = Math.sqrt(Math.pow(Ax.get(0, 0) - b.get(0, 0), 2) + Math.pow(Ax.get(1, 0) - b.get(1, 0), 2));

        System.out.println("X = \n" + x);
    }

}
