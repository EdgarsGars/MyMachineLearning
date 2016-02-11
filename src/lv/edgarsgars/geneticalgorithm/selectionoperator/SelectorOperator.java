/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm.selectionoperator;

import java.util.ArrayList;
import lv.edgarsgars.geneticalgorithm.Chromosome;

/**
 *
 * @author Edgar_000
 */
public interface SelectorOperator {
    
    public <T extends Chromosome> T select(T[] population);
}
