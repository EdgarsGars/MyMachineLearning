/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.vizualization;

import javax.swing.JFrame;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.ml.assigments.ConfMatrixPanel;

/**
 *
 * @author Edgar_000
 */
public class Visual {
    
    public static void showConfusionMatrix(Matrix conf) {
        showConfusionMatrix(conf,400);
    }
    
    public static void showConfusionMatrix(Matrix conf, int size) {
        JFrame frame = new JFrame("Confusion matrix");
        ConfMatrixPanel panel = new ConfMatrixPanel();
        panel.setConfusionMatrix(conf);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(size, size);
        
        frame.setVisible(true);
    }
    
}
