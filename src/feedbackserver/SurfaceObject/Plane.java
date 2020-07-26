/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedbackserver.SurfaceObject;

import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.Vector;

/**
 *
 * @author nobin
 */
public class Plane implements SurfaceType{
    
    public Plane(Point _surfaceP,Vector _Normal){
        m_normal=_Normal.magnify(1/_Normal.length());
        m_a = m_normal.x();
        m_b = m_normal.y();
        m_c = m_normal.z();
        m_d = -(m_a*_surfaceP.x() + m_b*_surfaceP.y() + m_c*_surfaceP.z());
        sqABC = m_a*m_a + m_b*m_b + m_c*m_c;
    }
    
    public Plane(Point _surfaceA,Point _surfaceB,Point _surfaceC){
        
    }
    
    @Override
    public Point nearest(Point _p) {
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
