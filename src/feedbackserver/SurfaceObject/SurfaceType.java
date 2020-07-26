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
public interface SurfaceType {
    public Point nearest(Point _p);
    public Vector normal(Point _p);
}
