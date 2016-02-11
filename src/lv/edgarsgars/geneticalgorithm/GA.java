/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm;

import lv.edgarsgars.geneticalgorithm.selectionoperator.SelectorOperator;
import lv.edgarsgars.geneticalgorithm.crossoveroperators.CrossoverOperation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import sun.org.mozilla.javascript.internal.Evaluator;

/**
 *
 * @author Edgar_000
 */
public abstract class GA implements Comparator<Chromosome> {

    public <T extends Chromosome> T[] createNewPopulation(T[] population, Class<T> type,double mutationRate, double elitismRate, SelectorOperator selection,CrossoverOperation crossover) {
        
        T[] newPopulation = (T[]) Array.newInstance(type, population.length);
        for (T a : population) {
            a.fitness = fitness(a);
        }
        Arrays.sort(population, this);
        int elites = (int) (population.length * elitismRate);
        System.out.println("Best " + population[0].fitness + " " + population[0]);
        //  System.out.println("Worst " + population[population.length-1].fitness + " " + population[population.length-1]);
        for (int i = 0; i < elites; i++) {
            newPopulation[i] = population[i];
        }
        for (int i = elites; i < population.length; i++) {
            T parentA = selection.select(population);
            T parentB = selection.select(population);
            newPopulation[i] = crossover.getChild(parentA, parentB);
            for (int a = 0; a < newPopulation[i].genes.size(); a++) {
                if (Math.random() < mutationRate) {
                    Gene g = (Gene) newPopulation[i].genes.get(a);
                    g.mutate();
                }
            }
        }

        return newPopulation;
    }

    public abstract int compare(Chromosome o1, Chromosome o2);
    public abstract double fitness(Chromosome c);

}
