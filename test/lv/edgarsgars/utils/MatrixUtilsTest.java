/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.utils;

import lv.edgarsgars.mathematics.MathUtils;
import lv.edgarsgars.mathematics.Matrix;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edgar_000
 */
public class MatrixUtilsTest {

    public MatrixUtilsTest() {
    }

    @Test
    public void testGetRandom_int_int() {
        Matrix x1 = MatrixUtils.rand(8, 9);
        Matrix x2 = MatrixUtils.rand(8, 9);
        assertTrue(x1.getRowCount() == 8);
        assertTrue(x2.getCollumCount() == 9);
        assertNotEquals(x1, x2);
    }

    @Test
    public void testGetRandom_4args() {
        Matrix x = MatrixUtils.rand(2, 3, -10, 10);
        assertTrue(x.getRowCount() == 2);
        assertTrue(x.getCollumCount() == 3);
        assertTrue(x.get(0, 0) >= -10 && x.get(0, 0) <= 10);
        assertTrue(x.get(0, 1) >= -10 && x.get(0, 1) <= 10);
        assertTrue(x.get(0, 2) >= -10 && x.get(0, 2) <= 10);
        assertTrue(x.get(1, 0) >= -10 && x.get(1, 0) <= 10);
        assertTrue(x.get(1, 1) >= -10 && x.get(1, 1) <= 10);
        assertTrue(x.get(1, 2) >= -10 && x.get(1, 2) <= 10);

    }

    @Test
    public void testGetIdentical() {
        Matrix x = MatrixUtils.eye(3);
        assertTrue(x.getRowCount() == 3);
        assertTrue(x.getCollumCount() == 3);
        assertTrue(x.get(0, 0) == 1 && x.get(1, 1) == 1 && x.get(2, 2) == 1);
        assertTrue(x.get(0, 1) == 0 && x.get(0, 2) == 0 && x.get(1, 0) == 0 && x.get(1, 2) == 0 && x.get(2, 0) == 0 && x.get(2, 1) == 0);
        Matrix y = MatrixUtils.eye(9);
        assertTrue(y.getRowCount() == 9);
        assertTrue(y.getCollumCount() == 9);
        for (int r = 0; r < 9; r++) {
            for (int k = 0; k < 9; k++) {
                assertTrue(y.get(r, k) == ((r == k) ? 1 : 0));
            }
        }
    }

    @Test
    public void testGetOnes() {
        Matrix x = MatrixUtils.ones(5, 6);
        for (int r = 0; r < 5; r++) {
            for (int k = 0; k < 6; k++) {
                assertTrue(x.get(r, k) == 1);
            }
        }
    }

    @Test
    public void testReshape() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix y = MatrixUtils.reshape(x, 3, 2);
        assertTrue(y.size()[0] == 3 && y.size()[1] == 2);
        assertTrue(y.get(0, 0) == 1.0d);
        assertTrue(y.get(0, 1) == 2.0d);
        assertTrue(y.get(1, 0) == 3.0d);
        assertTrue(y.get(1, 1) == 4.0d);
        assertTrue(y.get(2, 0) == 5.0d);
        assertTrue(y.get(2, 1) == 6.0d);
    }

    @Test
    public void testHreverse() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix expected = new Matrix("[3 2 1; 6 5 4]");
        assertTrue(MatrixUtils.hreverse(x).equals(expected));
    }

    @Test
    public void testHshift() {
        Matrix x = new Matrix("[1 2 3 4 5 6]");
        Matrix t1 = new Matrix("[6 1 2 3 4 5]");
        Matrix t2 = new Matrix("[5 6 1 2 3 4]");
        Matrix t3 = new Matrix("[2 3 4 5 6 1]");
        Matrix t4 = new Matrix("[3 4 5 6 1 2]");
        assertTrue(MatrixUtils.hshift(x, 1).equals(t1));
        assertTrue(MatrixUtils.hshift(x, 2).equals(t2));
        assertTrue(MatrixUtils.hshift(x, -1).equals(t3));
        assertTrue(MatrixUtils.hshift(x, -2).equals(t4));
    }

    @Test
    public void testEuclidD() {
        Matrix x = new Matrix("[1 1]");
        Matrix y = new Matrix("[4 5]");
        assertTrue(MatrixUtils.euclidD(x, y) == 5.0d);
        assertTrue(MatrixUtils.euclidD(x, x) == 0.0d);
    }

    @Test
    public void testDistanceMatrix() {
        Matrix x = new Matrix("[0 0; 0 1; 0 2; 1 1; 100 100]");
        Matrix y = MatrixUtils.distanceMatrix(x, "euclid");
        Matrix exptected = new Matrix("[0.0 1.0 2.0 1.4142135623730951 141.4213562373095 ;1.0 0.0 1.0 1.0 140.71602609511115 ;2.0 1.0 0.0 1.4142135623730951 140.0142849854971 ;1.4142135623730951 1.0 1.4142135623730951 0.0 140.0071426749364 ;141.4213562373095 140.71602609511115 140.0142849854971 140.0071426749364 0.0 ;]");
        assertTrue(y.equals(exptected));
    }

    @Test
    public void testDistances() {
        Matrix p = new Matrix("[0 0]");
        Matrix points = new Matrix("[3 4; 4 3; 12 16; 18 24]");
        Matrix expected = new Matrix("[5.0 5.0 20.0 30.0]");
        assertTrue(expected.equals(MatrixUtils.distances(p, points, "euclid")));
        //TODO manhatan
    }

    @Test
    public void testSort() {
        Matrix x = new Matrix("[3 2 4 1 5 6]");
        Matrix[] sorted = MatrixUtils.sort(x, "asc");
        assertTrue(sorted[0].equals(new Matrix("[1.0 2.0 3.0 4.0 5.0 6.0]")));
        assertTrue(sorted[1].equals(new Matrix("[3.0 1.0 0.0 2.0 4.0 5.0]")));
    }

    @Test
    public void testGetRangeVector() {
        Matrix x = MatrixUtils.getRangeVector("0:1:6");
        Matrix x2 = MatrixUtils.getRangeVector("-5:1:6");
        Matrix x3 = MatrixUtils.getRangeVector("-1:0.1:1.1");

        Matrix t1 = new Matrix("[0 1 2 3 4 5]");
        Matrix t2 = new Matrix("[-5 -4 -3 -2 -1 0 1 2 3 4 5 ]");
        Matrix t3 = new Matrix("[-1.0 -0.9 -0.8 -0.7 -0.6 -0.5 -0.3999999999999999 -0.29999999999999993 -0.19999999999999996 -0.09999999999999998 0.0 0.10000000000000009 0.20000000000000018 0.30000000000000004 0.40000000000000013 0.5 0.6000000000000001 0.7000000000000002 0.8 0.9000000000000001 1.0 ;]");

        assertTrue(x.equals(t1));
        assertTrue(x2.equals(t2));
        assertTrue(x3.equals(t3));
    }

    @Test
    public void testMergeSort() {
        Matrix x = new Matrix("[3 2 4 1 5 6]");
        Matrix[] sorted = MatrixUtils.mergeSort(x);
        assertTrue(sorted[0].equals(new Matrix("[1.0 2.0 3.0 4.0 5.0 6.0]")));
        assertTrue(sorted[1].equals(new Matrix("[3.0 1.0 0.0 2.0 4.0 5.0]")));
    }

    @Test
    public void testLoadFromFile() {
    }

    @Test
    public void testUnique() {
        Matrix x = new Matrix("[1 1 1 2 2 3 4; 1 1 1 2 2 9 4]");
        Matrix uniques = MatrixUtils.unique(x);
        Matrix expected = new Matrix("[1.0 2.0 3.0 4.0 9.0]");
        assertTrue(uniques.equals(expected));
    }

    @Test
    public void testConfusionMatrix() {
        Matrix x = new Matrix("[1;1;0;0;0;3;3;3;2;2]");
        Matrix y = new Matrix("[0;1;0;1;0;2;3;2;0;3]");
        Matrix conf = MatrixUtils.confusionMatrix(x, y);
        assertTrue(conf.equals(new Matrix("[2.0 1.0 0.0 0.0 ;1.0 1.0 0.0 0.0 ;1.0 0.0 0.0 1.0 ;0.0 0.0 2.0 1.0 ;]")));
    }

    @Test
    public void testZscore() {
    }

    @Test
    public void testMaxNormalization() {
    }

    @Test
    public void testMeanc() {
    }

    @Test
    public void testMean() {
    }

    @Test
    public void testStd() {
    }

    @Test
    public void testVar() {
    }

    @Test
    public void testSplitData() {
    }

    @Test
    public void testEuclidM() {
    }

    @Test
    public void testCov_Matrix() {
        Matrix x = new Matrix("[1 2 3 4; 4 5 6 4; 7 8 9 4]");
        Matrix cov = MatrixUtils.cov(x.T());
        Matrix expected = new Matrix("[9 9 9 0;9 9 9 0;9 9 9 0;0 0 0 0]");
        assertTrue(cov.equals(expected));
    }

    @Test
    public void testCov_Matrix_Matrix() {

    }

    @Test
    public void testCorr_Matrix_Matrix() {
        Matrix x = new Matrix("[1 2 3 4; 4 5 6 4]");
    }

    @Test
    public void testCorr_Matrix() {

    }

    @Test
    public void testEig() {
        Matrix x = new Matrix("[1 2 3 ;4 5 6; 7 8 9]");
        Matrix c = MatrixUtils.cov(x.T());
        System.out.println("COV=" + c);
        Matrix[] VD = MatrixUtils.eig(c);
        //System.out.println(VD[0].toStringExcel());
        // System.out.println(VD[1].toStringExcel());

    }

    @Test
    public void testDet() {
    }

    @Test
    public void testTrace() {
    }

    @Test
    public void testPca() {
    }

    @Test
    public void testAnd() {
        Matrix x = new Matrix("[ 1 0; 0 1]");
        Matrix y = new Matrix("[1 1;0 0]");
        Matrix expected = new Matrix("[1 0; 0 0]");
        assertTrue(expected.equals(MatrixUtils.and(x, y)));
    }
    
    @Test
    public void testOr() {
        Matrix x = new Matrix("[ 1 0; 0 1]");
        Matrix y = new Matrix("[1 1;0 0]");
        Matrix expected = new Matrix("[1 1; 0 1]");
        assertTrue(expected.equals(MatrixUtils.or(x, y)));
    }
    
    
    @Test
    public void testXor() {
        Matrix x = new Matrix("[ 1 0; 0 1]");
        Matrix y = new Matrix("[1 1;0 0]");
        Matrix expected = new Matrix("[0 1; 0 1]");
        assertTrue(expected.equals(MatrixUtils.xor(x, y)));
    }

}
