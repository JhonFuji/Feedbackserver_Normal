/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedbackserver.SurfaceObject;

import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.Vector;
import feedbackserver.SurfaceObject.SurfaceType;

/**
 *
 * @author nobin
 */
public class Sphere implements SurfaceType{
    
    public Sphere(Point _center,double _radius){
        m_c=_center;
        m_r=_radius;
    }

    @Override
    public  Point nearest(Point _p) {
        double m_d = m_c.distance(_p);
        //double m_d = Vector.createSE(m_c, _p).length();
        Point m_n = Point.createXYZ(m_c.x()+(_p.x()-m_c.x())*m_r/m_d
                , m_c.y()+(_p.y()-m_c.y())*m_r/m_d
                , m_c.z()+(_p.z()-m_c.z())*m_r/m_d);
        return m_n;
    }
    @Override
    public Vector normal(Point _p){
        return Vector.createSE(m_c, _p);
    }
    
    public double distance(Point _p){
        return Vector.createSE(m_c, _p).length();
    }
    
    private Point m_c;
    private double m_r;
}
