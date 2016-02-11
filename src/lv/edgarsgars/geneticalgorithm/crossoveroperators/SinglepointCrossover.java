/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm.crossoveroperators;

import java.util.ArrayList;
import java.util.List;
import lv.edgarsgars.geneticalgorithm.Chromosome;

/**
 *
 * @author Edgar_000
 */
public class SinglepointCrossover implements CrossoverOperation {

    @Override
    public <T extends Chromosome> T getChild(T parentA, T parentB) {
        List genes = new ArrayList<>();
        int pivot = (int) (Math.random() * parentA.genes.size());
        for (int i = 0; i < parentA.genes.size(); i++) {
            if (i < pivot) {
                genes.add(parentA.genes.get(i));
            } else {
                genes.add(parentB.genes.get(i));
            }
        }
        Chromosome chromosome = new Chromosome(genes.size(), parentA.geneClass);
        chromosome.init(genes);
        return (T) chromosome;

    }

}
