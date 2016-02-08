/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

/**
 *
 * @author Edgar_000
 */
public class Test {
    
    public static void main(String[] args) {
        Vector vec = new Vector(3.3, 4.1, 5, 2);
        Vector vec2 = new Vector(vec);
        System.out.println(vec.sum(vec2));
        System.out.println(vec.subtract(vec2));
        System.out.println(vec.multiply(3));
        System.out.println(vec.equals(vec));
        System.out.println(vec.equals(vec2.multiply(0.3)));
    }
}
