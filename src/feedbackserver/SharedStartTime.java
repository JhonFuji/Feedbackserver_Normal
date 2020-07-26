package feedbackserver;

/**
 *
 * @author Koda
 */
public class SharedStartTime {

    public synchronized static void setStartTime(double _time) {
        synchronized (m_timeLock) {
            m_startTime = _time;
        }
    }

    public synchronized static double getStartTime() {
        synchronized (m_timeLock) {
            return m_startTime;
        }
    }
    private static Object m_timeLock = new Object();
    private static double m_startTime = 0.0;
}
