/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedbackserver.SurfaceObject;

import feedbackserver.FeedbackManager;
import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.Vector;

/**
 *
 * @author nobin
 */
public class AxisPlane implements SurfaceType{
    
    public AxisPlane(Vector _Normal){
        m_normal=_Normal;
        m_a = _Normal.x();
        m_b = _Normal.y();
        m_c = _Normal.z();
        sqABC = m_a*m_a + m_b*m_b + m_c*m_c;
    }
    
    @Override
    public Point nearest(Point _p) {
        m_d = m_a*FeedbackManager.lastPoint.x() + m_b*FeedbackManager.lastPoint.y() + m_c*FeedbackManager.lastPoint.z();
        double k = -(m_a*_p.x() + m_b*_p.y() + m_c*_p.z() + m_d)/sqABC;
        return Point.createXYZ(_p.x()+m_a*k, _p.y()+m_b*k, _p.z()+m_c*k);
    }
    @Override
    public Vector normal(Point _p){
        return m_normal;
    }
    
    private Vector m_normal;
    private double m_a;
    private double m_b;
    private double m_c;
    private double m_d;
    private double sqABC;
}
