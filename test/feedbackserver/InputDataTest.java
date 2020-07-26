/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedbackserver;

import java.util.ArrayList;
import java.util.LinkedList;
import jp.sagalab.jftk.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author koda
 */
public class InputDataTest {

    public InputDataTest() {
    }

    /**
     * Test of updateList method, of class InputData.
     */
    @Test
    public void testUpdateList() {
        System.out.println("updateList");
        InputPoints.updateList(Point.createXYZT(1, 0, 0, 1.01));
        InputPoints.updateList(Point.createXYZT(2, 0, 0, 1.03));
        InputPoints.updateList(Point.createXYZT(3, 0, 0, 1.05));
        InputPoints.updateList(Point.createXYZT(4, 0, 0, 1.07));
        InputPoints.updateList(Point.createXYZT(5, 0, 0, 1.09));
        InputPoints.updateList(Point.createXYZT(6, 0, 0, 1.11));
        InputPoints.updateList(Point.createXYZT(7, 0, 0, 1.13));
        InputPoints.updateList(Point.createXYZT(8, 0, 0, 1.15));
        LinkedList<Point> expResult = new LinkedList<Point>();
        expResult.add(Point.createXYZT(3, 0, 0, 1.05));
        expResult.add(Point.createXYZT(4, 0, 0, 1.07));
        expResult.add(Point.createXYZT(5, 0, 0, 1.09));
        expResult.add(Point.createXYZT(6, 0, 0, 1.11));
        expResult.add(Point.createXYZT(7, 0, 0, 1.13));
        expResult.add(Point.createXYZT(8, 0, 0, 1.15));
        for (int i = 0; i < expResult.size(); ++i) {
            assertEquals(expResult.get(i), InputPoints.getList().get(i));
        }
    }
}
