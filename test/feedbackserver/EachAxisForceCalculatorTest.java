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
 * @author Koda
 */
public class EachAxisForceCalculatorTest {

    public EachAxisForceCalculatorTest() {
    }

    /**
     * Test of CalculateFuzzyFriction method, of class EachAxisForceCalculator.
     */
    @Test
    public void testCalculateFuzzyFriction() {
        System.out.println("CalculateFuzzyFriction");
        double _anchor = 7.0;
        double _value = 1.0;
        double _fuzziness = 6.0;
        ForceCalculate instance = ForceCalculate.create();
        FuzzyForce ff = instance.CalculateFuzzyFriction(_anchor, _value, _fuzziness);

        double forceResult = ff.force();
        double forceExpResult = instance.RateOfSpring() * 6.0 * 0.001;

        double fuzzinessResult = ff.fuzziness();
        double fuzzinessExpResult = instance.RateOfSpring() * 6.0 * 0.001;

        assertEquals(forceExpResult, forceResult, 0.0001);
        assertEquals(fuzzinessExpResult, fuzzinessResult, 0.0001);
    }


}
