/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm.commongenes;

import lv.edgarsgars.geneticalgorithm.Gene;

/**
 *
 * @author Edgar_000
 */
public class DoubleGene implements Gene {

    private double value;

    public DoubleGene() {
        mutate();
    }

    @Override
    public void mutate() {
        value += Math.random() * 2.0 - 1.0;
    }

    @Override
    public Gene copy() {
        DoubleGene g = new DoubleGene();
        g.value = value;
        return g;
    }
    
    public double getValue(){
        return value;
    }

}
