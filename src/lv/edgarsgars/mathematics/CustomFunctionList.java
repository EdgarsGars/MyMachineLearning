/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import lv.edgarsgars.utils.CustomFunction;

/**
 *
 * @author Edgar_000
 */
public class CustomFunctionList {

    public static CustomFunction sin = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return Math.sin(val);
        }
    };

    public static CustomFunction cos = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return Math.cos(val);
        }
    };

    public static CustomFunction tan = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return Math.tan(val);
        }
    };

    public static CustomFunction sqrt = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return Math.sqrt(val);
        }
    };

    public static CustomFunction abs = new CustomFunction() {
        @Override
        public double functionOf(double val) {
            return Math.abs(val);
        }
    };

    

}
