package feedbackserver;

import feedbackserver.ForceCalculate;
import feedbackserver.State;
import feedbackserver.SurfaceObject.Surface;
import fuzzybeziercurve.BezierCurve;
import fuzzybeziercurve.EvaluationPointsFBCCreator;
import java.util.Arrays;
import java.util.LinkedList;
import javafx.scene.effect.Light;
import jp.sagalab.jftk.Point;
import jp.sagalab.jftk.Vector;
import jp.sagalab.jftk.curve.ParametricEvaluable;

/**
 * 静止摩擦状態を管理するクラスです
 *
 * @author koda
 */
public class FeedbackManager {

    public double[] VerticalDistance(Point[] _chank){
        double[] _d=new double[_chank.length];
        double num=1;
        for(int i=0;i<_chank.length;i++){
            double _d2=((_chank[i].x()-Center.x())*(_chank[i].x()-Center.x()))+((_chank[i].y()-Center.y())*(_chank[i].y()-Center.y()))+((_chank[i].z()-Center.z())*(_chank[i].z()-Center.z()));
            num=Math.sqrt(_d2);
            _d[i] = num-r;//(num-r) >=0 ? (num-r) : (r-num) ;
        }
        Vector _v = Vector.createXYZ(_chank[_chank.length-1].x()-Center.x(), _chank[_chank.length-1].y()-Center.y(), _chank[_chank.length-1].z()-Center.z());
        NormalVector = Vector.createXYZ(_v.x()/num, _v.y()/num, _v.z()/num);
        m_anchorX = Center.x()+(r*NormalVector.x());
        m_anchorY = Center.y()+(r*NormalVector.y());
        m_anchorZ = Center.z()+(r*NormalVector.z());
        //System.out.print("Distance"+num);
        return _d;
    }
    /** 面上か判定する */
    public boolean JudgeOnTheSurface(Point[] _chank,double _distance){
        double FuzzynessSum = 0;
        for(int i=0 ;i<_chank.length;i++){
            FuzzynessSum +=_chank[i].fuzziness();
        }
        if(Math.abs(_distance)<FuzzynessSum*(1-value)/_chank.length){//value<(1-(_distance*_chank.length/FuzzynessSum))){
        return true;            
        }
        else{return false;}
    }
    
    /**
     * 座標から静止摩擦状態の判別を行います
     *
     * @param _point 入力点列
     * @param _velocity 3軸方向のペン先の速度
     */
    /*
    public void setStaticFrictionStateFlag(LinkedList<Point> _inputData) {
        m_X = _inputData.getLast().x();
        m_Y = _inputData.getLast().y();
        m_Z = _inputData.getLast().z();

        EvaluationPointsFBCCreator epfbcc = EvaluationPointsFBCCreator.create(
                ConstValue.FSC_CONVERT_VELOCITY_COEFF, ConstValue.FSC_CONVERT_ACCELERATION_COEFF);

        //FITTING_TIME_SPAN以内にリストの長さを抑える
        BezierCurve fuzzyBezierCurve = null;
        
        if (_inputData.size() >= ConstValue.DEGREE + 1) {
            fuzzyBezierCurve = epfbcc.createFBC(_inputData.toArray(new Point[_inputData.size()]), ConstValue.DEGREE, ConstValue.FITTING_TIME_SPAN);
            //m_fuzziness=fuzzyBezierCurve.evaluateAtEnd().fuzziness();
            // 評価点列化
            int evaluateNum = (int) Math.ceil(ConstValue.FITTING_TIME_SPAN / ConstValue.DIVIDE_TIME_RESOLUTION);
            Point[] points= fuzzyBezierCurve.evaluateAll(evaluateNum, ParametricEvaluable.EvaluationType.TIME);
            // チャンクの構成点数
            int n = (int) Math.ceil(ConstValue.DIVIDE_CHUNK_LENGTH /ConstValue.DIVIDE_TIME_RESOLUTION);
            Point[] Chank = Arrays.copyOfRange(points, points.length-Math.min((int) Math.ceil(n), points.length), points.length);
            m_fuzziness = Chank[Chank.length-1].fuzziness();
            double[] VDistance=VerticalDistance(Chank);
            m_distance = VDistance[VDistance.length-1];
            //動きが静止か
            m_state =m_judger.judge(Chank,VDistance);
            //面のすぐそばにいるか
            SurfaceFlag = JudgeOnTheSurface(Chank,m_distance);
            System.out.print("Dis "+VDistance[VDistance.length-1]+"  ");
            System.out.print("Fuzz "+Chank[Chank.length-1].fuzziness());
            //System.out.print("  vx "+NormalVector.x()+" ,vy "+NormalVector.y()+" ,vz "+NormalVector.z()+"");
            //System.out.println(m_anchorX+", "+m_anchorY+", "+m_anchorZ);
            //System.out.println("Var "+m_state+", Sur "+SurfaceFlag);
        }
        //Vertical
            //System.out.println(m_staticFrictionStateFlag);
        //静止摩擦状態
        if (m_staticFrictionStateFlag==State.STAY) {
            //ファジィ力を計算
            m_fuzzyForce = CalculateForce(m_distance, m_fuzziness);
            m_ForceX = NormalVector.x()*m_fuzzyForce.force();
            m_ForceY = NormalVector.y()*m_fuzzyForce.force();
            m_ForceZ = NormalVector.z()*m_fuzzyForce.force();
            //ファジィ力が最大静止摩擦力である必然性の判別
            if (m_fuzzyForce.necessary() > ConstValue.MAX_FRICTION_FORCE_N_VALUE && m_judger.label() != State.STAY) {
                m_staticFrictionStateFlag = State.MOVE;
                m_ForceX = 0.0;
                m_ForceY = 0.0;
                m_ForceZ = 0.0;
                m_fuzzyForce.setFuzziness(0.0);
            }
        } //非静止摩擦状態
        else {
            //ファジィフラグメンテーション法による停止性の判別
            if (m_state == State.STAY && SurfaceFlag) {
                m_staticFrictionStateFlag = State.STAY;
                m_fuzzyForce = CalculateForce(m_distance, m_fuzziness);
                m_ForceX = NormalVector.x()*m_fuzzyForce.force();
                m_ForceY = NormalVector.y()*m_fuzzyForce.force();
                m_ForceZ = NormalVector.z()*m_fuzzyForce.force();
            } else {
                m_ForceX = 0.0;
                m_ForceY = 0.0;
                m_ForceZ = 0.0;
            }
        }
        System.out.println("  fx "+m_ForceX+" ,fy "+m_ForceY+" ,fz "+m_ForceZ);
    }
    */

    
    
    public Point[] chunkUpdate(LinkedList<Point> _inputdata){
        BezierCurve fuzzyBezierCurve = fbcc.createFBC(_inputdata.toArray(new Point[_inputdata.size()]), ConstValue.DEGREE, ConstValue.FITTING_TIME_SPAN);
        
        /** 評価点列化*/
        Point[] points= fuzzyBezierCurve.evaluateAll(evaluateNum, ParametricEvaluable.EvaluationType.TIME);
        Point[] _chunk = Arrays.copyOfRange(points, points.length-Math.min((int) Math.ceil(n), points.length), points.length);
        return _chunk;
    }
    public LinkedList<Surface> Update(LinkedList<Point> _pointData,LinkedList<Surface> _surfaceData) {
        if(_pointData.size() > ConstValue.DEGREE){
            chunk = chunkUpdate(_pointData);
        }
        
        lastPoint = chunk[chunk.length-1];
        /** それぞれの曲面ごとに処理する    */
        
        //LinkedList<Point[]> onSurfacesePoints = new LinkedList<Point[]>();
        //今ループにおける面に対する状態
        //nearestPoints()最近点を求める
        _surfaceData.forEach(s-> s.nearestPoints(chunk));
        
        //fuzzyline
        //_surfaceData.forEach(s-> s.setNowStaticFrictionState(CheckState.chunkPoints(chunk,s.onSurfacePoints)));
        //FlagmentationAndTouch
        _surfaceData.forEach(s-> s.setNowStaticFrictionState(CheckState.chunkPoints(chunk,s.onSurfacePoints)));
        
        //_surfaceData.forEach(s-> s.setNowStaticFrictionState(CheckState.chunkPoints(chunk,s.nearestPoints(chunk))) );
        
        //ファジイ力の計算
        _surfaceData.forEach(s-> s.calFuzzyForce());
        //力の合成
        sumFeedbackForce = Vector.createXYZ(0, 0, 0);
        _surfaceData.forEach(s-> sumFeedbackForce.compose(s.feedbackForce.necessaryForce()));
        //_surfaceData.forEach(s-> sumFeedbackForce = (s.feedbackForce.necessaryForce()));
        /*
        System.out.println("Fx "+sumFeedbackForce.x()+
                " , Fy "+sumFeedbackForce.y()+
                " , Fz "+sumFeedbackForce.z()
                );
        */
        sumFeedbackForce = MathExt.Clamp(3,sumFeedbackForce);
        
        return new LinkedList<Surface>(_surfaceData);
    }
    
    public FeedbackManager() {
        m_judger = new CheckState();
        m_state =State.STAY;
        NormalVector = Vector.createXYZ(0, 0, 0);
        m_staticFrictionStateFlag = State.MOVE;
        SurfaceFlag = true;
        m_anchorX = Double.NaN;
        m_anchorY = Double.NaN;
        m_anchorZ = Double.NaN;
        
        //lastPoint = Point.createXYZ(0, 0, 0);
        fbcc = EvaluationPointsFBCCreator.create(ConstValue.FSC_CONVERT_VELOCITY_COEFF, ConstValue.FSC_CONVERT_ACCELERATION_COEFF);
        //FITTING_TIME_SPAN以内にリストの長さを抑える
        //fuzzyBezierCurve = null;
        evaluateNum = (int) Math.ceil(ConstValue.FITTING_TIME_SPAN / ConstValue.DIVIDE_TIME_RESOLUTION);
        /** チャンクの構成点数*/
        n = (int) Math.ceil(ConstValue.DIVIDE_CHUNK_LENGTH /ConstValue.DIVIDE_TIME_RESOLUTION);
    }
    
    /** ファジィフラグメンテーションの時間解像度 */
//    private static final double DIVIDE_TIME_RESOLUTION = 0.025;
    /** ファジィフラグメンテーションのチャンク時間長 */
//    private static final double DIVIDE_CHUNK_LENGTH = 0.1;
    
    /** Bezier曲線の次数 */
//    public static final int DEGREE = 3;
    /** FSC変換の速度係数 */
//    private static final double FSC_CONVERT_VELOCITY_COEFF = 0.008581 * 6;//Geo 0.5 Bg 6
    /** FSC変換の加速度係数 */
//    private static final double FSC_CONVERT_ACCELERATION_COEFF = 0.007742 * 0.25;
    /** リストの時間 */
//    public static final double FITTING_TIME_SPAN = 0.1;
    /** 最大摩擦力 */
//    public static final double MAX_FRICTION_FORCE_VALUE = 0.5;//Geo 0.05 Bg 0.5
    /** 最大摩擦力の必然性値 */
//    public static final double MAX_FRICTION_FORCE_N_VALUE = 0.6;//Geo 0.7 Bg 0.95
    /** ペンの重さ */
//    public static final double WEIGHT = 0.06 * 9.8;
    /** 静止摩擦力計算クラス */
    /** 停止性評価クラス */
    private final CheckState m_judger;
    /** チャンクの状態 */
    private State m_state;
    private State m_stateX;
    private State m_stateY;
    private State m_stateZ;
    /** 静止摩擦状態フラグ */
    private State m_staticFrictionStateFlag;
    private boolean m_staticFrictionStateFlagX;
    private boolean m_staticFrictionStateFlagY;
    private boolean m_staticFrictionStateFlagZ;
    /** フィードバックする力の大きさ */
    private double m_Force;
    private double m_ForceX;
    private double m_ForceY;
    private double m_ForceZ;
    /** フィードバックする力の大きさ */
    private FuzzyForce m_fuzzyForce;
    private FuzzyForce m_fuzzyForceX;
    private FuzzyForce m_fuzzyForceY;
    private FuzzyForce m_fuzzyForceZ;
    /** 現在のペン先の座標[mm] */
    private Point m_Point;
    private double m_X;
    private double m_Y;
    private double m_Z;
    /** 停止時に基準点となる座標 */
    private double m_distance;
    private double m_anchorX = Double.NaN;
    private double m_anchorY = Double.NaN;
    private double m_anchorZ = Double.NaN;
    private double m_fuzziness;
    private double m_fuzzinessX;
    private double m_fuzzinessY;
    private double m_fuzzinessZ;
    /** 法線方向の単位ベクトル */
    private Vector NormalVector;
    /** 接触の閾値 */
    private final double value = 0.5;
    private boolean SurfaceFlag;
    /** 球の中心と半径 */
    private static final Point Center=Point.createXYZ(0, -110, 0);
    private static final double r = 80;
    
    public static Vector sumFeedbackForce;
    public static Point lastPoint;
    private static Point[] chunk;
    private static EvaluationPointsFBCCreator fbcc;
    //private static BezierCurve fuzzyBezierCurve;
    private static int n;
    private static int evaluateNum;
    //private static LinkedList nowSurface;
}
