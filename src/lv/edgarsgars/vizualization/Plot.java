/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.vizualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lv.edgarsgars.mathematics.Vector;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author edgars.garsneks
 */
public class Plot extends JPanel {

    private Point xBounds = new Point(-1, 1);
    private Point yBounds = new Point(-1, 1);
    private Point center = new Point(0, 0);
    private Point plotSize = new Point(400, 400);
    private Vector xdata = new Vector();
    private Vector ydata = new Vector();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(10, 10);
        Graphics2D g2d = ((Graphics2D) g);

        int w = 400;
        int h = 400;

        double rangeX = Math.abs(xBounds.x - xBounds.y);
        double rangeY = Math.abs(yBounds.x - yBounds.y);

        double ratiox = w / rangeX;
        double ratioy = h / rangeY;

        center.x = (int) (Math.abs(-xBounds.x) * ratiox);
        center.y = (int) (Math.abs(-yBounds.y) * ratioy);

        System.out.println("ratiox " + ratiox);
        System.out.println("ratioy " + ratioy);

        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.black);
        g2d.drawLine(0, center.y, w, center.y);
        g2d.drawLine(center.x, 0, center.x, h);
        g.setColor(Color.black);
        g2d.fillOval(center.x, center.y, 2, 2);

        for (int i = 0; i < rangeX; i++) {
            //g2d.drawString("x,y", (int) (ratiox * i), center.y - 10);
            double value = (int) (ratiox * i) / ratiox + xBounds.x;

            g2d.fillRect((int) (ratiox * i), center.y - 5, 1, 10);
            g2d.drawString("" + value, (int) ratiox * i, center.y - 10);
        }

        for (int i = 0; i < rangeY; i++) {
            //g2d.drawString("x,y", (int) (ratiox * i), center.y - 10);
            double value = (int) (ratioy * i) / ratioy + yBounds.x;

            g2d.fillRect(center.x-5, (int) (ratiox * i), 10, 1);
            //g2d.drawString("" + value, (int) ratiox * i, center.y - 10);
        }

        g.setColor(Color.red);
        for (int i = 1; i < xdata.size(); i++) {
            double x0 = Math.abs(xdata.get(i - 1) - xBounds.x) * ratiox;
            double y0 = Math.abs(ydata.get(i - 1) - yBounds.y) * ratioy;
            double x1 = Math.abs(xdata.get(i) - xBounds.x) * ratiox;
            double y1 = Math.abs(ydata.get(i) - yBounds.y) * ratioy;
            g2d.drawLine((int) x0, (int) y0, (int) x1, (int) y1);
            // g2d.fillOval((int) x - 5, (int) y - 5, 10, 10);
            // g2d.drawString("(" + xdata.get(i) + "," + ydata.get(i) + ")", (int) x - 10, -(int) y - 10);
        }

    }

    public static void showPlot(Vector x, Vector y) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Plot p = new Plot();
        frame.setSize(400, 400);
        p.setSize(400, 400);
        frame.setContentPane(p);
        frame.setVisible(true);
        p.initPlot(x, y);
        p.repaint();

    }

    private void initPlot(Vector x, Vector y) {
        Vector[] sortedVectors = VectorUtils.sortVectorsByVector(new Vector[]{x, y});

        xBounds.x = (int) Math.round(x.minValue());
        xBounds.y = (int) Math.round(x.maxValue());
        yBounds.x = (int) Math.round(y.minValue());
        yBounds.y = (int) Math.round(y.maxValue());
        this.xdata = sortedVectors[0];
        this.ydata = sortedVectors[1];

    }

}
