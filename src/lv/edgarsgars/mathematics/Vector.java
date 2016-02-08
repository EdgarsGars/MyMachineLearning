/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar_000
 */
public class Vector implements Iterable<Double> {

    private ArrayList<Double> vector = new ArrayList<>();

    public Vector(int size) {
        vector.ensureCapacity(size);
    }

    public Vector(double... values) {
        add(values);
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
}
