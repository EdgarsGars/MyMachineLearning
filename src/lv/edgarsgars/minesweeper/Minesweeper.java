/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.minesweeper;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.mathematics.Vector;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author Edgar_000
 */
public class Minesweeper {

    private Matrix field;
    private Matrix numbers;
    private static final double MINE = -1.0;

    public Minesweeper(int n, int m, int mineCount) {
        field = new Matrix(n, m);
        numbers = new Matrix(n, m);
        for (int i = 0; i < mineCount; i++) {
            int r, k;
            do {
                r = (int) (Math.random() * n);
                k = (int) (Math.random() * m);
            } while (field.get(r, k) == MINE);
            field.set(r, k, MINE);
        }

        for (int i = 0; i < numbers.getRowCount(); i++) {
            for (int j = 0; j < numbers.getCollumCount(); j++) {
                numbers.set(i, j, mineCount(i, j));
            }
        }

        System.out.println(field);
        System.out.println(numbers);
    }

    public int mineCount(int r, int k) {
        int count = 0;
        if (field.get(r, k) != MINE) {
            for (int i = r - 1; i <= r + 1; i++) {
                for (int j = k - 1; j <= k + 1; j++) {

                    if (i >= 0 && i < field.getRowCount() && j >= 0 && j < field.getCollumCount() && field.get(i, j) == MINE) {
                        System.out.println("Checking " + i + " " + j);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public Vector guessVector(Vector vec, int i) {
        Vector fv = field.toVector();
        Vector nu = numbers.toVector();
        if (fv.get(i) != MINE) {
            vec.set(i, nu.get(i));
        } else {
            return null;
        }
        return vec;
    }

    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(9, 9, 5);
        Vector guess = new Vector(25);
        guess = m.guessVector(guess, 3);
        System.out.println(VectorUtils.reshape(guess,5,5));
    }
}
