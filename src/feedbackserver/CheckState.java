package feedbackserver;

import java.util.Arrays;
import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.TruthValue;
import fuzzybeziercurve.BezierCurve;
import jp.sagalab.jftk.curve.ParametricEvaluable;
import feedbackserver.State;
import feedbackserver.IncludeIn;
import jp.sagalab.jftk.Vector;

/**
 * ファジィベジェ曲線の静止を検出します
 *
 * @author koda
 */
public class CheckState {
    
    /**
     * チャンクの状態を返します。
     *
     * @param _chunk チャンク
     * @param _onSurfacePoints 面上の点
     * @return チャンクの状態
     */
    public static State chunkPoints(Point[] _chunk, Point[] _onSurfacePoints){
        //必然性値,可能性値の計算
        double nec = 1;
        double pos = 1;
        for (int i = 0; i < _chunk.length - 1; ++i) {
            TruthValue tv = IncludeIn.thisPotherPF(_chunk[i], _onSurfacePoints[i], _chunk[_chunk.length-1].fuzziness());
            nec = Math.min(nec, tv.necessity());
            pos = Math.min(pos, tv.possibility());
        }
        return selectState(nec);
    }
    /**
     * チャンクの状態を返します。
     *
     * @param _chunk チャンク
     * @param _onSurfacePoints 面上の点
     * @return チャンクの状態
     */
    public static State chunkPoints(Point[] _chunk, Point[] _onSurfacePoints, Vector _normal){
        //必然性値,可能性値の計算
        double nec = 1;
        double pos = 1;
        
        double distanceLast = -_chunk[_chunk.length-1].distance(_onSurfacePoints[_chunk.length-1]);
        if(_normal.dot(Vector.createSE(_onSurfacePoints[_chunk.length-1],_chunk[_chunk.length-1])) < 0){distanceLast = 0-distanceLast;}
        
        for (int i = 0; i < _chunk.length - 1; i++) {
            TruthValue tv = IncludeIn.thisFDotherFD(
                    _chunk[_chunk.length-1].fuzziness(), distanceLast, 
                    _chunk[i].fuzziness(),_chunk[i].distance(_onSurfacePoints[i]));
            nec = Math.min(nec, tv.necessity());
            pos = Math.min(pos, tv.possibility());
        }
        nec = Math.min(nec, IncludeIn.thisPotherP(_chunk[_chunk.length-1], _onSurfacePoints[_chunk.length-1]).necessity());
        return selectState(nec);
    }
    /**
     * チャンクの状態を返します。
     *
     * @param _chunk チャンク
     * @param _distance 面との距離
     * @return チャンクの状態
     */
    public static State chunkDistances(Point[] _chunk,double[] _distance) {
        //必然性値,可能性値の計算
        double nec = 1;
        double pos = 1;
        for (int i = 0; i < _chunk.length - 1; ++i) {
            TruthValue tv = IncludeIn.thisFDotherFD(_chunk[_chunk.length-1].fuzziness(),_distance[_chunk.length-1], _chunk[i].fuzziness(),_distance[i]);
            nec = Math.min(nec, tv.necessity());
            pos = Math.min(pos, tv.possibility());
        }
        return selectState(nec, pos);
    }
    
    
    
    static private State selectState(double _nec){
        if (ConstValue.DIVIDE_THRESHOLD.necessity() < _nec ) {
            return State.STAY;
        }
        else{
            return State.MOVE;
        }
    }
    static private State selectState(double _nec, double _pos){
        if (ConstValue.DIVIDE_THRESHOLD.necessity() < _nec && ConstValue.DIVIDE_THRESHOLD.possibility() < _pos) {
            return State.STAY;
        }
        else{
            return State.MOVE;
        }
    }
}
