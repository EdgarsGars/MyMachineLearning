/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm;

import lv.edgarsgars.geneticalgorithm.selectionoperator.SelectorOperator;
import lv.edgarsgars.geneticalgorithm.crossoveroperators.CrossoverOperation;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Edgar_000
 */
public abstract class GA implements Comparator<Chromosome> {

    public enum TARGET {
        MINIMIZE, MAXIME
    }

    public TARGET target;

    public <T extends Chromosome> T[] createNewPopulation(T[] population, Class<T> type, double mutationRate, double elitismRate, SelectorOperator selection, CrossoverOperation crossover,TARGET target) {
        this.target = target;
        T[] newPopulation = (T[]) Array.newInstance(type, population.length);
        for (T a : population) {
            a.fitness = fitness(a);
        }
        Arrays.sort(population, this);
        int elites = (int) (population.length * elitismRate);
//        System.out.println("Best " + population[0].fitness + " " + population[0]);
        //  System.out.println("Worst " + population[population.length-1].fitness + " " + population[population.length-1]);
        for (int i = 0; i < elites; i++) {
            newPopulation[i] = population[i];
        }
        for (int i = elites; i < population.length; i++) {
            T parentA = selection.select(population);
            T parentB = selection.select(population);
            newPopulation[i] = crossover.getChild(parentA, parentB);
            mutate(newPopulation[i]);
        }

        return newPopulation;
    }

    @Override
    public int compare(Chromosome o1, Chromosome o2) {
        if (target == TARGET.MAXIME) {
            if (o1.fitness - o2.fitness > 0) {
                return -1;
            } else if (o1.fitness - o2.fitness < 0) {
                return 1;
            } else {
                return 0;
            }
        } else if (o1.fitness - o2.fitness > 0) {
            return 1;
        } else if (o1.fitness - o2.fitness < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public abstract double fitness(Chromosome c);

    public abstract void mutate(Chromosome c);

}
