package feedbackserver;

import feedbackserver.SurfaceObject.Sphere;
import feedbackserver.SurfaceObject.Surface;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.SocketException;
import jp.sagalab.jftk.Point;

import java.net.Socket;

/**
 *
 * @author koda
 */
public class FeedbackServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            //サーバ側の待受ポート番号
            int port = 9993;
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Accepting");

            //クライアントからの通信開始要求が来るまで待機
            SharedSocket.setSocket(ss.accept());
//
            
            
            //以下、クライアントからの要求発生後
            //クライアントから数値を受信
            InputStream is = SharedSocket.getSocket().getInputStream();
            SharedSocket.createDataInputStream(is);
            System.out.println("Connected");
            SharedStartTime.setStartTime(System.nanoTime() * 1e-9);
            //受信した座標を格納
            double inputX;
            double inputY;
            double inputZ;
            CalculateThread calThread = new CalculateThread();
            double oldTime = SharedStartTime.getStartTime();
            int count = 0;
            InputSurface.startList();
            while (true) {
                double time = System.nanoTime() * 1e-9;
                inputX = SharedSocket.getDataInputStream().readDouble();
                inputY = SharedSocket.getDataInputStream().readDouble();
                inputZ = SharedSocket.getDataInputStream().readDouble();
                
                
                //System.out.println("in Y "+inputY);;
                //受信した座標から点を生成
                if (time - oldTime > INPUT_TIME_SPAN) {
                    InputSurface.updateList(new Surface(new Sphere(Point.createXYZ(0, 0, 0), 1)));
                    InputPoints.updateList(Point.createXYZT(inputX, inputY, inputZ, time));
                    //受信した座標から点を生成
                    oldTime = time;
                    if (count == 0) {
                        calThread.start();
                        count++;
                    }
                }
            }
        } catch (SocketException ex) {
        }
    }
    public static double INPUT_TIME_SPAN = 0.001;
    public static double startTime;
}
