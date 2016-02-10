/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Edgar_000
 */
public class Vector implements Iterable<Double> {

    private ArrayList<Double> vector = new ArrayList<>();

    public Vector(int size) {
        for (int i = 0; i < size; i++) {
            add(0);
        }
    }

    public Vector(String pattern) {
        String[] params = pattern.split(":");
        double from = Double.parseDouble(params[0]);
        double step = Double.parseDouble(params[1]);
        double to = Double.parseDouble(params[2]);
        for (double d = from; d < to; d += step) {
            vector.add(d);
        }
    }

    public Vector(double... values) {
        add(values);
    }

    public Vector(List<Double> values) {
        for (Double value : values) {
            add(value);
        }
    }

    public Vector(Vector vector) {
        this.vector = new ArrayList<>();
        for (int i = 0; i < vector.size(); i++) {
            this.vector.add(vector.get(i));
        }
    }

    public void add(double value) {
        vector.add(value);
    }

    public void add(double... values) {
        for (double value : values) {
            this.vector.add(value);
        }
    }
    
    public void add(Vector vec) {
        for (double value : vec) {
            this.vector.add(value);
        }
    }

    public void set(int i, double value) {
        this.vector.set(i, value);
    }

    @Override
    public String toString() {
        return this.vector.toString();
    }

    public double get(int index) {
        return vector.get(index);
    }

    public int size() {
        return vector.size();
    }

    public Vector sum(Vector vec) {
        Vector sum = new Vector(vec.size());
        for (int i = 0; i < this.vector.size(); i++) {
            sum.add(vec.get(i) + vector.get(i));
        }
        return sum;
    }

    public Vector subtract(Vector vec) {
        Vector subtract = new Vector(vec.size());
        for (int i = 0; i < this.vector.size(); i++) {
            subtract.add(vec.get(i) - vector.get(i));
        }
        return subtract;
    }

    public Vector multiply(double k) {
        Vector rez = new Vector(vector.size());
        for (int i = 0; i < vector.size(); i++) {
            rez.add(vector.get(i) * k);
        }
        return rez;
    }

    @Override
    public Iterator<Double> iterator() {
        return vector.iterator();
    }

    public boolean equals(Vector vec) {
        for (int i = 0; i < vec.size(); i++) {
            if (vec.get(i) != vector.get(i)) {
                return false;
            }
        }
        return true;
    }

    public Vector remove(int index) {
        Vector copy = this.copy();
        copy.vector.remove(index);
        return copy;
    }

    public Vector removeAll(double value) {
        Vector copy = this.copy();
        while (copy.vector.contains(value)) {
            copy.vector.remove(value);
        }
        return copy;
    }

    public Vector copy() {
        Vector cop = new Vector();
        for (double d : vector) {
            cop.add(d);
        }
        return cop;

    }

    public double minValue() {
        double min = vector.get(0);
        for (double thi : this) {
            if (thi < min) {
                min = thi;
            }
        }
        return min;
    }

    public double maxValue() {
        double max = vector.get(0);
        for (double thi : this) {
            if (thi > max) {
                max = thi;
            }
        }
        return max;
    }

    public double mean() {
        double sum = 0;
        for (Double thi : this) {
            sum += thi;
        }
        return sum / (double) size();
    }

    public ArrayList<Double> asList() {
        return this.copy().vector;
    }

}
