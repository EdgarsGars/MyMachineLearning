/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.utils;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Edgar
 */
public class Utils {

    private static long tic;

    public static void tic() {
        tic = System.nanoTime();
    }

    public static void toc() {
        System.out.println("Elapsed time " + (System.nanoTime() - tic) + " ns");
    }

    /**
     * Prints out elapsed time in timeunits
     *
     * @param units - valid options ns,ms,s,min
     */
    public static void toc(String units) {
        //TODO
        switch (units) {
            case "ms":
                System.out.println("Elapsed time " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - tic) + " ms");
                break;
            case "s":
                System.out.println("Elapsed time " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - tic) + " s");
                break;
            case "min":
                System.out.println("Elapsed time " + TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - tic) + " min");
                break;
        }

    }

}
