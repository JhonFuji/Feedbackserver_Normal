/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedbackserver.SurfaceObject;

import feedbackserver.ConstValue;
import static feedbackserver.ForceCalculate.ForceCalculate;
import feedbackserver.FuzzyForce;
import feedbackserver.State;
import jdk.nashorn.internal.ir.BreakNode;
import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.Vector;

/**
 *
 * @author nobin
 */
public class Surface {
    
    public static Surface createSphere(Point _center,double _radius){
        return new Surface(new Sphere(_center, _radius));
    }
    public static Surface createPlane(Point _a,Point _b,Point _c){
        return new Surface(new Plane(_a, _b, _c));
    }
    
    
    public Point[] nearestPoints(Point[] _chunk){
        onSurfacePoints = new Point[_chunk.length];
        for(int n = 0; n < onSurfacePoints.length; n++ ){
            onSurfacePoints[n] = surfce.nearest(_chunk[n]);
        }
        lastPoint = _chunk[_chunk.length-1];
        /*
        System.out.println("nearest  "+
                onSurfacePoints[onSurfacePoints.length-1].x()+" "+
                onSurfacePoints[onSurfacePoints.length-1].y()+" "+
                onSurfacePoints[onSurfacePoints.length-1].z()+" "
        );
        */
        return onSurfacePoints;
    }
    public void calFuzzyForce(){
        
        feedbackForce = ForceCalculate(onSurfacePoints[onSurfacePoints.length-1], lastPoint);
        
        if(nowStaticFrictionState==State.MOVE){
            if(lastStaticFrictionState==State.MOVE){
                feedbackForce = new FuzzyForce(new Vector(0, 0, 0), 0);
            }
            else if(feedbackForce.forceAbs() > ConstValue.MAX_FRICTION_FORCE_VALUE){
                feedbackForce = new FuzzyForce(new Vector(0, 0, 0), 0);
                lastStaticFrictionState = State.MOVE;
            }
            else{
                lastStaticFrictionState = State.STAY;
            }
        }
        else{
            lastStaticFrictionState = State.STAY;
        }
        
        /*System.out.println(lastStaticFrictionState+"  , fx "+
                String.format("%1$+.10f", feedbackForce.forceVector().x())+" , fy "+
                String.format("%1$+.10f", feedbackForce.forceVector().y())+" , fz "+
                String.format("%1$+.10f", feedbackForce.forceVector().z())+" , "+
                feedbackForce.necessary());
        */
    }
    
    public void setLastStaticFrictionState(State _lastStaticFrictionState){
        lastStaticFrictionState = _lastStaticFrictionState;
        System.out.print(lastStaticFrictionState+",");
    }
    
    public State getLastStaticFrictionState(){
        return lastStaticFrictionState;
    }
    
    public void setNowStaticFrictionState(State _nowStaticFrictionState){
        nowStaticFrictionState = _nowStaticFrictionState;
    }
    
    public State getNowStaticFrictionState(){
        return nowStaticFrictionState;
    }
    
    public Surface(SurfaceType _surface){
        surfce = _surface;
        lastStaticFrictionState = State.MOVE;
        nowStaticFrictionState = State.MOVE;
    }
    private Point lastPoint;
    private SurfaceType surfce;
    private State lastStaticFrictionState;
    private State nowStaticFrictionState;
    public Point[] onSurfacePoints;
    public FuzzyForce feedbackForce;
}
