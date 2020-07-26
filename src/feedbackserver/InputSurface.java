package feedbackserver;

import feedbackserver.SurfaceObject.AxisPlane;
import feedbackserver.SurfaceObject.Plane;
import feedbackserver.SurfaceObject.Sphere;
import java.util.LinkedList;
import jp.sagalab.jftk.Point;
import feedbackserver.SurfaceObject.Surface;
import jp.sagalab.jftk.Vector;
/**
 *
 * @author nobin
 */
public class InputSurface {
    
    public static void updateList(Surface _surface) {
        synchronized (m_listLock) {
            
        }
    }
    public static void startList() {
        synchronized (m_listLock){
            m_surfaceList.add(new Surface(new Plane(Point.createXYZ(0, -20, 0), Vector.createXYZ(0, 1, 0))));
            //m_surfaceList.add(new Surface(new Plane(Point.createXYZ(0, 0, 20), Vector.createXYZ(0, 0, 1))));
            //m_surfaceList.add(new Surface(new Plane(Point.createXYZ(50, 0, 0), Vector.createXYZ(1, 0, 0))));
            //m_surfaceList.add(new Surface(new AxisPlane( Vector.createXYZ(0, 1, 0))));
            //m_surfaceList.add(Surface.createSphere(Point.createXYZ(0, 0, 0),50));
            //m_surfaceList.add(new Surface(new Sphere(Point.createXYZ(50, -20, -80),60)));
        }
    }
    
    public static LinkedList<Surface> getList() {
        synchronized (m_listLock){
            return new LinkedList<Surface>(m_surfaceList);
        }
    }
    
    public static void setStaticFrictionFlag(LinkedList<Surface> _updated) {
        synchronized(m_listLock){
            int i = 0;
            for (Surface s: _updated) {
                m_surfaceList.get(i).setLastStaticFrictionState(s.getLastStaticFrictionState());
                i++;
                //System.out.print(" S "+i+s.getLastStaticFrictionState()+" , ");
            }
        }
    }
    
    private static Object m_listLock = new Object();
    private static LinkedList<Surface> m_surfaceList = new LinkedList();
}
