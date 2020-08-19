package feedbackserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.Vector;
import feedbackserver.SurfaceObject.Surface;

/**
 *
 * @author Koda
 */
public class CalculateThread extends Thread {
    
    public void run() {
        try {
            OutputStream os = SharedSocket.getSocket().getOutputStream();
            SharedSocket.createDataOutputStream(os);
            FeedbackManager feedback = new FeedbackManager();
            LinkedList<Point> pointData = new LinkedList<Point>();
            LinkedList<Surface> surfaceData = new LinkedList<Surface>();
            LinkedList<Surface> surfaceUpdated = new LinkedList<Surface>();
            //送信する力の大きさ
            //double outputFX = 0.0;
            //double outputFY = 0.0;
            //double outputFZ = 0.0;
            //plot
            
            while (pointData.size() < ConstValue.DEGREE + 1) {                
                pointData = InputPoints.getList();
            }
            
            surfaceData = InputSurface.getList();
            //System.out.println(surfaceData.size());
            
            while (InputPoints.getList() != null) {
                pointData = InputPoints.getList();
                surfaceData = InputSurface.getList();
                surfaceUpdated = feedback.Update(pointData,surfaceData);
                InputSurface.setStaticFrictionFlag(surfaceUpdated);
                
                //outputFX = MathExt.Clamp(3, -3, feedback.forceX());
                //outputFY = MathExt.Clamp(3, -3, feedback.forceY());
                //outputFZ = MathExt.Clamp(3, -3, feedback.forceZ());
                
                //surfaceData.forEach(s -> System.out.print(s.getLastStaticFrictionState()));
                
                System.out.println(
                        
                        //",
                        "px"+String.format("%1$+.10f", feedback.lastPoint.x())+
                        ",py"+String.format("%1$+.10f", feedback.lastPoint.y())+
                        ",pz"+String.format("%1$+.10f", feedback.lastPoint.z())+
                        ",fx"+String.format("%1$+.10f", feedback.sumFeedbackForce.x())+
                        ",fy"+String.format("%1$+.10f", feedback.sumFeedbackForce.y())+
                        ",fz"+String.format("%1$+.10f", feedback.sumFeedbackForce.z())
                        );
                
                //System.out.println("");
                SharedSocket.getDataOutputStream().writeDouble(feedback.sumFeedbackForce.x());
                SharedSocket.getDataOutputStream().writeDouble(feedback.sumFeedbackForce.y());
                SharedSocket.getDataOutputStream().writeDouble(feedback.sumFeedbackForce.z());
                
            }
        } catch (SocketException ex) {
        } catch (IOException ex) {
            Logger.getLogger(FeedbackServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
