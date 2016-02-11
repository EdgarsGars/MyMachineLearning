/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm.selectionoperator;

import lv.edgarsgars.geneticalgorithm.selectionoperator.SelectorOperator;
import java.util.ArrayList;
import lv.edgarsgars.geneticalgorithm.Chromosome;

/**
 *
 * @author Edgar_000
 */
public class RouleteSelector implements SelectorOperator {

    @Override
    public <T extends Chromosome> T select(T[] population) {
        do {
            int index = (int) (Math.random() * population.length);
            double p = population[index].fitness / population[0].fitness;

            if (Math.random() < p) {
                return population[index];
            }
        } while (true);
    }

}
