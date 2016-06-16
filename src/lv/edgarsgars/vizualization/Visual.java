/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.vizualization;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.ml.assigments.ConfMatrixPanel;

/**
 *
 * @author Edgar_000
 */
public class Visual {

    public static void showConfusionMatrix(Matrix conf) {
        showConfusionMatrix(conf, 400);
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

    public static void bwshow(final Matrix x, int width, int height, String title, final boolean gridon) {
        JFrame frame = new JFrame(title);
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.lightGray);
                int plotSizeW = getWidth() / 3;
                int plotSizeH = getHeight() / 3;
                g.fillRect(0, 0, getWidth(), getHeight());
                int w = plotSizeW / x.getCollumCount();
                int h = plotSizeH / x.getRowCount();
                for (int i = 0; i < x.getRowCount(); i++) {
                    for (int j = 0; j < x.getCollumCount(); j++) {
                        if (x.isLogical()) {
                            if (x.get(i, j) == 1.0) {
                                g.setColor(Color.WHITE);
                            } else {
                                g.setColor(Color.BLACK);
                            }
                        } else {
                            int v = (int) ((x.get(i, j) / x.max()) * 255);
                            //   System.out.println(v);
                            Color c = new Color(v, v, v);
                            g.setColor(c);
                        }
                        g.fillRect(plotSizeW + j * w, plotSizeH + i * h, w, h);
                        if (gridon) {
                            g.setColor(Color.DARK_GRAY);
                            g.drawRect(plotSizeW + j * w, plotSizeH + i * h, w, h);
                        }

                    }
                }
            }
        };
        frame.setContentPane(panel);
        // frame.setSize(50 * x.getCollumCount(), 50 * x.getRowCount());
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void bwshow(Matrix x, String title) {
        bwshow(x, 50 * x.getCollumCount(), 40 * x.getRowCount(), title, true);
    }

}
