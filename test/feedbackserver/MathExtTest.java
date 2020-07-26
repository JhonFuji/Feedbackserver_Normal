/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedbackserver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author akito
 */
public class MathExtTest {

    public MathExtTest() {
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
     * Test of Clamp method, of class EachAxisForceCalculator.
     */
    @Test
    public void testClamp() {
        System.out.println("Clamp");
        double _max = 10.0;
        double _min = -10.0;
        double _x = 15.0;
        double expResult = 10.0;
        double result = MathExt.Clamp(_max, _min, _x);
        assertEquals(expResult, result, 0.0);
    }

}
