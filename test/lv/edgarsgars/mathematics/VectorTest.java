/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.mathematics;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edgar_000
 */
public class VectorTest {

    public VectorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class Vector.
     */
    @Test
    public void testAdd_double() {
        Vector vector = new Vector();
        double expected = 5;

        assertTrue(vector.size() == 0);

        vector.add(expected);

        assertTrue(expected == vector.get(0));
        assertTrue(vector.size() == 1);

        double value2 = 3;
        vector.add(value2);
        assertTrue(value2 == vector.get(1));

    }

    /**
     * Test of add method, of class Vector.
     */
    @Test
    public void testAdd_doubleArr() {
        Vector vector = new Vector();
        Vector vector2 = new Vector(3, 4, 5);
        double[] v = new double[]{3, 4, 5, 6};
        vector.add(v);
        vector2.add(v);
        assertTrue(vector.get(0) == v[0] && vector.get(1) == v[1] && vector.get(2) == v[2] && vector.get(3) == v[3]);
        assertTrue(vector2.get(3) == v[0] && vector2.get(4) == v[1] && vector2.get(5) == v[2] && vector2.get(6) == v[3]);
    }

    /**
     * Test of set method, of class Vector.
     */
    @Test
    public void testSet() {
        Vector vector = new Vector(3);
        vector.set(0, 1);
        vector.set(1, 2);
        vector.set(2, 3);

        assertTrue(vector.get(0) == 1 && vector.get(1) == 2 && vector.get(2) == 3);

    }

    /**
     * Test of toString method, of class Vector.
     */
    @Test
    public void testToString() {
        Vector vector = new Vector(3, 2, 1);
        assertEquals("[3.0,2.0,1.0]", vector.toString());
    }

    /**
     * Test of get method, of class Vector.
     */
    @Test
    public void testGet() {
        Vector v = new Vector(3, 5, 3, 2);
        assertEquals(3.0, v.get(0));
        assertEquals(5.0, v.get(1));
        assertEquals(3.0, v.get(2));
        assertEquals(2.0, v.get(3));
    }

    /**
     * Test of size method, of class Vector.
     */
    @Test
    public void testSize() {
        Vector v = new Vector(3, 5, 2, 3, 4);
        assertEquals(5, v.size());

    }

    /**
     * Test of sum method, of class Vector.
     */
    @Test
    public void testSum() {

    }

    /**
     * Test of subtract method, of class Vector.
     */
    @Test
    public void testSubtract() {

    }

    /**
     * Test of multiply method, of class Vector.
     */
    @Test
    public void testMultiply() {

    }

    /**
     * Test of iterator method, of class Vector.
     */
    @Test
    public void testIterator() {

    }

    /**
     * Test of equals method, of class Vector.
     */
    @Test
    public void testEquals() {

    }

}
