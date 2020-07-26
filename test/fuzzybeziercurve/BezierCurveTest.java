/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzybeziercurve;

import fuzzybeziercurve.BezierCurve;
import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.curve.Range;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author akito
 */
public class BezierCurveTest {

    @Test
    public void testDiffirentiate() {
        BezierCurve input = BezierCurve.create(new Point[]{
            Point.createXYZ(-2.0, 0.0, 0.0),
            Point.createXYZ(-1.0, 0.0, 0.0),
            Point.createXYZ(0.0, 2.0, 0.0),
            Point.createXYZ(1.0, 0.0, 0.0),
            Point.createXYZ(2.0, 0.0, 0.0)},
                Range.create(0, 1));

        Assert.assertEquals(4, input.differentiate().controlPoints()[0].x(), 1e-10);
        Assert.assertEquals(4, input.differentiate().controlPoints()[1].x(), 1e-10);
        Assert.assertEquals(4, input.differentiate().controlPoints()[2].x(), 1e-10);
        Assert.assertEquals(4, input.differentiate().controlPoints()[3].x(), 1e-10);

        Assert.assertEquals(4.0, input.differentiate().evaluate(0.0).x(), 1e-10);
        Assert.assertEquals(4.0, input.differentiate().evaluate(0.25).x(), 1e-10);
        Assert.assertEquals(4.0, input.differentiate().evaluate(0.5).x(), 1e-10);
        Assert.assertEquals(4.0, input.differentiate().evaluate(0.75).x(), 1e-10);
        Assert.assertEquals(4.0, input.differentiate().evaluate(1.0).x(), 1e-10);

        Assert.assertEquals(0.0, input.differentiate().evaluate(0.0).y(), 1e-10);
        Assert.assertEquals(2.25, input.differentiate().evaluate(0.25).y(), 1e-10);
        Assert.assertEquals(0.0, input.differentiate().evaluate(0.5).y(), 1e-10);
        Assert.assertEquals(-2.25, input.differentiate().evaluate(0.75).y(), 1e-10);
        Assert.assertEquals(0.0, input.differentiate().evaluate(1.0).y(), 1e-10);
    }

}
