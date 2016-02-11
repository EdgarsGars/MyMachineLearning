/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import lv.edgarsgars.utils.MatrixUtils;
import lv.edgarsgars.utils.VectorUtils;

/**
 *
 * @author Edgar_000
 */
public class Test {

    public static void main(String[] args) {

        Matrix a = MatrixUtils.getIdentical(3);
        System.out.println(a);
        Matrix b = MatrixUtils.getOnes(3, 3);
        System.out.println(b);
        Matrix db = MatrixUtils.getOnes(3, 3).scalar(3.3);
        System.out.println(db);
        Matrix c = MatrixUtils.getRandom(3, 3);
        System.out.println(c);
        Matrix d = MatrixUtils.getRandom(5, 5, -1, 1);
        System.out.println(d);
        Matrix m = new Matrix("[3 2 1; 3 2 1; 2 2 -3.2; 2     -3 -2.343 3]");
        System.out.println(m);
        Vector vec = new Vector("0:0.1:1");
        System.out.println(vec);
        System.out.println("");
        Matrix z = VectorUtils.reshape(new Vector("0:1:15"), 3, 5);
        System.out.println(z);

        Matrix m2 = new Matrix("[1 2 3 4 ; 5 6 7 8]");
        System.out.println(MatrixUtils.reshape(m2, 4, 2));

        Matrix fromFile = MatrixUtils.loadDataFromFile("./testData.txt", ",");
        System.out.println(fromFile);

    }
}
