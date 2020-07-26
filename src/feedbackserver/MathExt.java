package feedbackserver;

import jp.sagalab.jftk.Vector;

/**
 *
 * @author Koda
 */
public class MathExt {
    
    /**
     * 軸を考慮したVectorClamp
     * 
     * @param _max
     * @param _vector
     * @return _vector * magnification
     */
    public static Vector Clamp(double _max, Vector _vector){
        //x方向
        double mag = magnification(_max, _vector.x());
        //y方向
        mag = Math.min(mag , magnification(_max, _vector.y()));
        //z方向
        mag = Math.min(mag, magnification(_max, _vector.z()));
        return _vector.magnify(mag);
    }
    
    /**
     * 値を引数の範囲内に制限します
     *
     * @param _max 制限範囲の最大値
     * @param _min 制限範囲の最小値
     * @param _x 制限する値
     *
     * @return 制限された値
     */
    public static double Clamp(double _max, double _min, double _x) {
        return Math.max(_min, Math.min(_max, _x));
    }
    /**
     * a<bのときb*c=aとなるcを返す
     * @param a
     * @param b
     * @return 
     */
    private static double magnification(double a, double b){
       return a / Math.max(Math.abs(a), Math.abs(b));
    }
}
