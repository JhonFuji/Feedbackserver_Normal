package feedbackserver;
import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.Vector;
/**
 * 静止摩擦力を計算します
 *
 * @author koda
 */
public class ForceCalculate {
    
    public static FuzzyForce ForceCalculate(Point _anchor, Point _last) {
        
        Vector force = Vector.createSE(_last, _anchor).magnify(ConstValue.SPRING_CONSTANT * 0.001);
        double fFuzziness = ConstValue.SPRING_CONSTANT * _last.fuzziness();
        
        return new FuzzyForce(force, fFuzziness, calculateNecessary(force.length(), fFuzziness));
    }
    /**
     * 1次元での静止摩擦力を計算します
     *
     * @param _anchor 拘束点の座標[mm]
     * @param _last 現在の点の座標[mm]
     * @param _fuzziness 位置ファジネス[mm]
     *
     * @return ファジネスを持つ静止摩擦力[N]
     */
    public static FuzzyForce ForceCalculate(double _anchor, double _last, double _fuzziness) {
        
        double distance = (_anchor - _last) * 0.001;
        double force = ConstValue.SPRING_CONSTANT * distance;
        double fFuzziness = ConstValue.SPRING_CONSTANT * _fuzziness * 0.001;
        
        return new FuzzyForce(force, fFuzziness, calculateNecessary(force, fFuzziness));
    }
    /**
     * 1次元での静止摩擦力を計算します
     *
     * @param _distance 拘束点との距離[mm]
     * @param _fuzziness 位置ファジネス[mm]
     *
     * @return ファジネスを持つ静止摩擦力[N]
     */
    public static FuzzyForce ForceCalculate(double _distance,double _fuzzyness){
        
        double force = ConstValue.SPRING_CONSTANT*_distance*(-0.001);
        double fFuzziness = ConstValue.SPRING_CONSTANT*_fuzzyness*0.001;
        
        return new FuzzyForce(force, fFuzziness, calculateNecessary(force, fFuzziness));
    }
    private static double calculateNecessary(double _f, double _fuzziness){
        return MathExt.Clamp(1, 0, (Math.abs(_f) - ConstValue.MAX_FRICTION_FORCE_VALUE) / (_fuzziness));
    }
}
