/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.ml.assigments;

import java.awt.Color;
import java.awt.Graphics;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author Edgar_000
 */
public class ConfMatrixPanel extends javax.swing.JPanel {

    private Matrix conf = new Matrix(4, 4);

    /**
     * Creates new form ConfMatrixPanel
     */
    public ConfMatrixPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Matrix[] sort = MatrixUtils.mergeSort(conf.toVector());
        double min = sort[0].get(0, 0);
        double max = sort[0].get(0, sort[0].getCollumCount() - 1);
        int w = getWidth() / conf.getRowCount();
        int h = getHeight() / conf.getRowCount();
        for (int i = 0; i < conf.getRowCount(); i++) {
            for (int j = 0; j < conf.getRowCount(); j++) {

                g.setColor(getRGBForValue(min , max, conf.get(i, j)));
                g.fillRect(j * w, i * h, w, h);
                g.setColor(Color.BLACK);
                g.drawRect(j * w, i * h, w, h);
                g.setColor(Color.BLACK);
                g.drawString("" + conf.get(i, j), (int) (j * w + w / 2), (int) (i * h + h / 2));
            }
        }

    }

    public void setConfusionMatrix(Matrix x) {
        this.conf = x;
    }

    public Color getRGBForValue(double min, double max, double value) {

        min = Math.floor(min);
        max = Math.floor(max);
        double ratio = 2 * (value - min) / (max - min);
        int b = (int) (Math.max(0, 255 * (1 - ratio)));
        int r = (int) (Math.max(0, 255 * (ratio - 1)));
        int g = 255 - b - r;
        return new Color(r, g, b);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
