/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.geneticalgorithm;

/**
 *
 * @author Edgar_000
 */
public interface Gene {
    
    public void mutate();
    public Gene copy();
    
}
