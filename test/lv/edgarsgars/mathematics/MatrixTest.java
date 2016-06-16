/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lv.edgarsgars.utils.MatrixUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edgar_000
 */
public class MatrixTest {

    public MatrixTest() {
    }

    @Test
    public void testGet() {
        Matrix m = new Matrix("[1 2 3; 4 5 6;]");
        assertTrue(m.get(0, 0) == 1.0d);
        assertTrue(m.get(0, 1) == 2.0d);
        assertTrue(m.get(0, 2) == 3.0d);
        assertTrue(m.get(1, 0) == 4.0d);
        assertTrue(m.get(1, 1) == 5.0d);
        assertTrue(m.get(1, 2) == 6.0d);
    }

    @Test
    public void testSet() {
        Matrix m = new Matrix("[1 2 3; 4 5 6;]");
        m.set(-1, 0, 0);
        m.set(-2, 0, 1);
        m.set(-3, 0, 2);
        m.set(-4, 1, 0);
        m.set(-5, 1, 1);
        m.set(-6, 1, 2);

        assertTrue(m.get(0, 0) == -1.0d);
        assertTrue(m.get(0, 1) == -2.0d);
        assertTrue(m.get(0, 2) == -3.0d);
        assertTrue(m.get(1, 0) == -4.0d);
        assertTrue(m.get(1, 1) == -5.0d);
        assertTrue(m.get(1, 2) == -6.0d);
        assertTrue(m.get(1, 2) != -18.0d);

    }

    @Test
    public void testToVector() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix m = x.toVector();
        assertTrue(m.get(0, 0) == 1.0d);
        assertTrue(m.get(0, 1) == 2.0d);
        assertTrue(m.get(0, 2) == 3.0d);
        assertTrue(m.get(0, 3) == 4.0d);
        assertTrue(m.get(0, 4) == 5.0d);
        assertTrue(m.get(0, 5) == 6.0d);
    }

    @Test
    public void testGetRow_int() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix m = x.getRow(0);
        Matrix m2 = x.getRow(1);
        assertTrue(m.get(0, 0) == 1.0d);
        assertTrue(m.get(0, 1) == 2.0d);
        assertTrue(m.get(0, 2) == 3.0d);
        assertTrue(m2.get(0, 0) == 4.0d);
        assertTrue(m2.get(0, 1) == 5.0d);
        assertTrue(m2.get(0, 2) == 6.0d);
    }

    @Test
    public void testGetRow_String() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix m = x.getRow("-1");
        assertTrue(m.get(0, 0) == 4.0d);
        assertTrue(m.get(0, 1) == 5.0d);
        assertTrue(m.get(0, 2) == 6.0d);
    }

    @Test
    public void testGetCol_String() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix m = x.getCol("-1");
        assertTrue(m.get(0, 0) == 3.0d);
        assertTrue(m.get(1, 0) == 6.0d);

    }

    @Test
    public void testGetCol_int() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix m = x.getCol(0);
        assertTrue(m.get(0, 0) == 1.0d);
        assertTrue(m.get(1, 0) == 4.0d);

        Matrix m2 = x.getCol(1);
        assertTrue(m2.get(0, 0) == 2.0d);
        assertTrue(m2.get(1, 0) == 5.0d);

        Matrix m3 = x.getCol(2);
        assertTrue(m3.get(0, 0) == 3.0d);
        assertTrue(m3.get(1, 0) == 6.0d);

        Matrix m4 = x.getCol(3);
        assertTrue(m4 == null);

    }

    @Test
    public void testCopy() {
        Matrix x = new Matrix("[1 2 3; 4 5 6;]");
        Matrix y = x.copy();
        assertTrue(x != y);
        assertTrue(x.get(0, 0) == y.get(0, 0));
        assertTrue(x.get(0, 1) == y.get(0, 1));
        assertTrue(x.get(0, 2) == y.get(0, 2));
        assertTrue(x.get(1, 0) == y.get(1, 0));
        assertTrue(x.get(1, 1) == y.get(1, 1));
        assertTrue(x.get(1, 2) == y.get(1, 2));
    }

    @Test
    public void testScalar_Matrix() {
        Matrix x = new Matrix("[1 2 3; 4 5 6;]");
        Matrix s = new Matrix("[2 2 2; 2 2 2;]");
        Matrix m = x.scalar(s);

        assertTrue(m.get(0, 0) == 2.0d);
        assertTrue(m.get(0, 1) == 4.0d);
        assertTrue(m.get(0, 2) == 6.0d);
        assertTrue(m.get(1, 0) == 8.0d);
        assertTrue(m.get(1, 1) == 10.0d);
        assertTrue(m.get(1, 2) == 12.0d);
    }

    @Test
    public void testScalar_double() {
        Matrix x = new Matrix("[1 2 3; 4 5 6;]");
        Matrix m = x.scalar(3);

        assertTrue(m.get(0, 0) == 3.0d);
        assertTrue(m.get(0, 1) == 6.0d);
        assertTrue(m.get(0, 2) == 9.0d);
        assertTrue(m.get(1, 0) == 12.0d);
        assertTrue(m.get(1, 1) == 15.0d);
        assertTrue(m.get(1, 2) == 18.0d);
    }

    @Test
    public void testDot() {
        Matrix x = new Matrix("[1 2 3]");
        Matrix y = new Matrix("[4;4;4]");
        Matrix d = x.dot(y);
        assertTrue(d.get(0, 0) == 24);
        Matrix x2 = new Matrix("[1 2 3; 4 5 6]");
        Matrix y2 = new Matrix("[2 2; 1 2; 3 2]");
        Matrix d2 = x2.dot(y2);
        assertTrue(d2.get(0, 0) == 13.0d);
        assertTrue(d2.get(0, 1) == 12.0d);
        assertTrue(d2.get(1, 0) == 31.0d);
        assertTrue(d2.get(1, 1) == 30.0d);

    }

    @Test
    public void testNegate() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix y = x.negate();
        assertTrue(y.get(0, 0) == -1.0d);
        assertTrue(y.get(0, 1) == -2.0d);
        assertTrue(y.get(0, 2) == -3.0d);
        assertTrue(y.get(1, 0) == -4.0d);
        assertTrue(y.get(1, 1) == -5.0d);
        assertTrue(y.get(1, 2) == -6.0d);
        assertTrue(y.get(1, 2) != 2.0d);
    }

    @Test
    public void testGetRowCount() {
        Matrix a = new Matrix("[1;2;3;4]");
        Matrix a2 = new Matrix("[1 2;2 2;3 2;4 2; 4 2]");
        Matrix a3 = new Matrix(0, 0);

        assertTrue(a.getRowCount() == 4);
        assertTrue(a2.getRowCount() == 5);
        assertTrue(a3.getRowCount() == 0);
    }

    @Test
    public void testGetCollumCount() {
        Matrix a = new Matrix("[1;2;3;4]");
        Matrix a2 = new Matrix("[1 2;2 2;3 2;4 2; 4 2]");

        assertTrue(a.getCollumCount() == 1);
        assertTrue(a2.getCollumCount() == 2);
    }

    @Test
    public void testToString() {
        Matrix x = new Matrix("[1 2 3; 2 4 5;]");
        assertTrue(x.toString().equals("[1.0 2.0 3.0 ;2.0 4.0 5.0 ;]"));
    }

    @Test
    public void testToStringExcel() {
        Matrix x = new Matrix("[1 2 3; 2 4 5;]");
        assertTrue(x.toStringExcel().equals("1.0\t2.0\t3.0\t\n2.0\t4.0\t5.0\t\n"));
    }

    @Test
    public void testTransponse() {
        Matrix x = new Matrix("[1 2 3; 4 5 6;]");
        Matrix t = x.T();
        assertTrue(t.get(0, 0) == 1.0d);
        assertTrue(t.get(0, 1) == 4.0d);
        assertTrue(t.get(1, 0) == 2.0d);
        assertTrue(t.get(1, 1) == 5.0d);
        assertTrue(t.get(2, 0) == 3.0d);
        assertTrue(t.get(2, 1) == 6.0d);
    }

    @Test
    public void testAdd_double() {
        Matrix x = new Matrix("[1 2;3 4]");
        Matrix t = x.add(3.0);
        assertTrue(t.get(0, 0) == 4.0d);
        assertTrue(t.get(0, 1) == 5.0d);
        assertTrue(t.get(1, 0) == 6.0d);
        assertTrue(t.get(1, 1) == 7.0d);
    }

    @Test
    public void testAdd_Matrix() {
        Matrix x = new Matrix("[1 2;3 4]");
        Matrix t = x.add(new Matrix("[1 2;3 4]"));
        assertTrue(t.get(0, 0) == 2.0d);
        assertTrue(t.get(0, 1) == 4.0d);
        assertTrue(t.get(1, 0) == 6.0d);
        assertTrue(t.get(1, 1) == 8.0d);
    }

    @Test
    public void testAddTo() {
        Matrix x = new Matrix("[0 0;0 0]");
        x.addTo(1, 0, 0);
        assertTrue(x.get(0, 0) == 1.0d);
    }

    @Test
    public void testSub_double() {
        Matrix x = new Matrix("[1 2 1;3 4 1]");
        Matrix t = x.sub(1);
        assertTrue(t.get(0, 0) == 0.0d);
        assertTrue(t.get(0, 1) == 1.0d);
        assertTrue(t.get(0, 2) == 0.0d);
        assertTrue(t.get(1, 0) == 2.0d);
        assertTrue(t.get(1, 1) == 3.0d);
        assertTrue(t.get(1, 2) == 0.0d);
    }

    @Test
    public void testSub_Matrix() {
        Matrix x = new Matrix("[1 2 1;3 4 1]");
        Matrix t = x.sub(new Matrix("[1 1 1;1 1 1]"));
        assertTrue(t.get(0, 0) == 0.0d);
        assertTrue(t.get(0, 1) == 1.0d);
        assertTrue(t.get(0, 2) == 0.0d);
        assertTrue(t.get(1, 0) == 2.0d);
        assertTrue(t.get(1, 1) == 3.0d);
        assertTrue(t.get(1, 2) == 0.0d);
    }

    @Test
    public void testSize() {
        Matrix x = new Matrix("[1 2 1;3 4 1]");
        int[] size = x.size();
        assertTrue(size[0] == 2);
        assertTrue(size[1] == 3);
    }

    @Test
    public void testGetRows() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix[] rows = x.getRows();
        assertTrue(rows[0].get(0, 0) == 1.0d);
        assertTrue(rows[0].get(0, 1) == 2.0d);
        assertTrue(rows[0].get(0, 2) == 3.0d);
        assertTrue(rows[1].get(0, 0) == 4.0d);
        assertTrue(rows[1].get(0, 1) == 5.0d);
        assertTrue(rows[1].get(0, 2) == 6.0d);
    }

    @Test
    public void testSetRow() {
        Matrix x = new Matrix("[1 1 1; 1 1 1]");
        x.setRow(0, new Matrix("[0 2 4]"));
        assertTrue(x.get(0, 0) == 0.0d);
        assertTrue(x.get(0, 1) == 2.0d);
        assertTrue(x.get(0, 2) == 4.0d);
    }

    @Test
    public void testSetCol() {
        Matrix x = new Matrix("[1 1 1; 1 1 1]");
        x.setCol(0, new Matrix("[0;2 ]"));
        assertTrue(x.get(0, 0) == 0.0d);
        assertTrue(x.get(1, 0) == 2.0d);
    }

    @Test
    public void testToList() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        List list = x.toList();
        assertEquals(list.get(0), 1.0d);
        assertEquals(list.get(1), 2.0d);
        assertEquals(list.get(2), 3.0d);
        assertEquals(list.get(3), 4.0d);
        assertEquals(list.get(4), 5.0d);
        assertEquals(list.get(5), 6.0d);
    }

    @Test
    public void testToArray() {
        Matrix x = new Matrix("[1 2 3; 4 7 6]");
        Double[] arr = x.toArray();
        assertTrue(arr[0] == 1.0d);
        assertTrue(arr[1] == 2.0d);
        assertTrue(arr[2] == 3.0d);
        assertTrue(arr[3] == 4.0d);
        assertTrue(arr[4] == 7.0d);
        assertTrue(arr[5] == 6.0d);

    }

    @Test
    public void testVcat_doubleArr() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        double[] arr = new double[]{7, 8, 9};
        x = x.vcat(arr);
        assertTrue(x.get(2, 0) == 7.0d);
        assertTrue(x.get(2, 1) == 8.0d);
        assertTrue(x.get(2, 2) == 9.0d);
    }

    @Test
    public void testVcat_Matrix() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix y = new Matrix("[7 8 9; 10 11 12]");
        x = x.vcat(y);
        assertTrue(x.get(2, 0) == 7.0d);
        assertTrue(x.get(2, 1) == 8.0d);
        assertTrue(x.get(2, 2) == 9.0d);
        assertTrue(x.get(3, 0) == 10.0d);
        assertTrue(x.get(3, 1) == 11.0d);
        assertTrue(x.get(3, 2) == 12.0d);
    }

    @Test
    public void testHcat_Matrix() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix y = new Matrix("[7 8 9; 10 11 12]");
        x = x.hcat(y);
        assertTrue(x.get(0, 3) == 7.0d);
        assertTrue(x.get(0, 4) == 8.0d);
        assertTrue(x.get(0, 5) == 9.0d);
        assertTrue(x.get(1, 3) == 10.0d);
        assertTrue(x.get(1, 4) == 11.0d);
        assertTrue(x.get(1, 5) == 12.0d);
    }

    @Test
    public void testHcat_doubleArr() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        double[] y = new double[]{9, 10};
        x = x.hcat(y);
        assertTrue(x.get(0, 3) == 9.0d);
        assertTrue(x.get(1, 3) == 10.0d);
    }

    @Test
    public void testReshape() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix y = x.reshape(3, 2);
        assertTrue(y.get(0, 0) == 1.0d);
        assertTrue(y.get(0, 1) == 2.0d);
        assertTrue(y.get(1, 0) == 3.0d);
        assertTrue(y.get(1, 1) == 4.0d);
        assertTrue(y.get(2, 0) == 5.0d);
        assertTrue(y.get(2, 1) == 6.0d);
    }

    @Test
    public void testRemoveRow() {
        Matrix x = new Matrix("[1 2 3; 2 4 5]");
        x.removeRow(0);
        assertTrue("Row count should be 1", x.getRowCount() == 1);
        assertTrue("Element (0,1) should be 2.0, but is " + x.get(0, 0), x.get(0, 0) == 2.0d);
        assertTrue("Element (0,2) should be 4.0, but is " + x.get(0, 0), x.get(0, 1) == 4.0d);
        assertTrue("Element (0,3) should be 5.0, but is " + x.get(0, 0), x.get(0, 2) == 5.0d);
    }

    @Test
    public void testRemoveCol() {
        Matrix x = new Matrix("[1 2 3; 2 4 5]");
        x.removeCol(0);
        //System.out.println(x);
        assertTrue("Row count should be 2", x.getCollumCount() == 2);
        assertTrue("Element (0,0) should be 2.0, but is " + x.get(0, 0), x.get(0, 0) == 2.0d);
        assertTrue("Element (1,0) should be 4.0, but is " + x.get(1, 0), x.get(1, 0) == 4.0d);
        assertTrue("Element (1,1) should be 3.0, but is " + x.get(0, 1), x.get(0, 1) == 3.0d);
        assertTrue("Element (2,1) should be 5.0, but is " + x.get(1, 1), x.get(1, 1) == 5.0d);
    }

    @Test
    public void testIterator() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");

        Iterator<Matrix> i = x.iterator();
        Matrix r1 = i.next();
        Matrix r2 = i.next();
        assertTrue(r1.get(0, 0) == 1.0d);
        assertTrue(r1.get(0, 1) == 2.0d);
        assertTrue(r1.get(0, 2) == 3.0d);

        assertTrue(r2.get(0, 0) == 4.0d);
        assertTrue(r2.get(0, 1) == 5.0d);
        assertTrue(r2.get(0, 2) == 6.0d);

    }

    @Test
    public void testGetRowAsArray() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        double[] arr = x.getRowAsArray(0);

        assertEquals(1.0d, arr[0], Double.MIN_VALUE);
        assertEquals(2.0d, arr[1], Double.MIN_VALUE);
        assertEquals(3.0d, arr[2], Double.MIN_VALUE);
    }

    @Test
    public void testIndexOf() {
        Matrix x = new Matrix("[1 2 3 4]");
        assertTrue(x.indexOf(0, 3.0d) == 2);
        assertTrue(x.indexOf(0, 2.0d) == 1);
    }

    @Test
    public void testEquals() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix y = new Matrix("[1 2 3; 4 5 6]");
        Matrix z = new Matrix("[1 2 0 ; 1 1 1]");
        assertTrue(x.equals(y));
        assertTrue(!x.equals(z));
    }

    @Test
    public void testSum_int() {
        Matrix x = new Matrix("[1 2 3; 1 2 3]");
        Matrix sum1 = x.sum(0);
        Matrix sum2 = x.sum(1);
        Matrix sum = x.sum(0).sum(1);
        assertTrue(sum1.get(0, 0) == 2.0d);
        assertTrue(sum1.get(0, 1) == 4.0d);
        assertTrue(sum1.get(0, 2) == 6.0d);

        assertTrue(sum2.get(0, 0) == 6.0d);
        assertTrue(sum2.get(1, 0) == 6.0d);

        assertTrue(sum.get(0, 0) == 12.0d);

    }

    @Test
    public void testGet_Matrix() {
        Matrix x = new Matrix("[1 2 3; 4 5 6]");
        Matrix m = new Matrix("[0 1 0; 1 1 0]");
        Matrix rez = x.get(m);
        assertTrue(rez.get(0, 0) == 2.0d);
        assertTrue(rez.get(0, 1) == 4.0d);
        assertTrue(rez.get(0, 2) == 5.0d);

    }
    
    
    @Test
    public void testIsLogical(){
        Matrix x = new Matrix(3,3);
        Matrix y = MatrixUtils.ones(5, 5);
        Matrix z = MatrixUtils.eye(4);
        Matrix t = MatrixUtils.rand(3, 3);
        assertTrue(x.isLogical());
        assertTrue(y.isLogical());
        assertTrue(z.isLogical());
        assertFalse(t.isLogical());
    }

}
