package feedbackserver;

import java.util.LinkedList;
import jp.sagalab.jftk.Point;

/**
 *
 * @author koda
 */
public class InputPoints {

    public static void updateList(Point _point) {
        synchronized (m_listLock) {
            m_inputPoints.add(_point);

            for (int i = 0; i < m_inputPoints.size(); ++i) {
                if (m_inputPoints.getLast().time() - ConstValue.FITTING_TIME_SPAN
                        > m_inputPoints.getFirst().time()) {
                    m_inputPoints.remove();
                } else {
                    break;
                }
            }
        }
    }

    public static LinkedList<Point> getList() {
        synchronized (m_listLock) {
            return new LinkedList<Point>(m_inputPoints);
        }
    }

    private static Object m_listLock = new Object();
    private static LinkedList<Point> m_inputPoints = new LinkedList();
}
