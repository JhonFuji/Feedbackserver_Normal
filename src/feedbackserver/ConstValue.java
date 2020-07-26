package feedbackserver;

import jp.sagalab.jftk.TruthValue;

/**
 *定数値
 * 
 * @author nobin
 */
public class ConstValue {
    
    /** ファジィフラグメンテーションの時間解像度 */
    public static final double DIVIDE_TIME_RESOLUTION = 0.025;
    /** ファジィフラグメンテーションのチャンク時間長 */
    public static final double DIVIDE_CHUNK_LENGTH = 0.1;
    
    /** FSC変換の速度係数 */
    public static final double FSC_CONVERT_VELOCITY_COEFF = 0.008581 * 6;//Geo 0.5 Bg 6
    /** FSC変換の加速度係数 */
    public static final double FSC_CONVERT_ACCELERATION_COEFF = 0.007742 * 0.25;
    
    /** リストの時間 */
    public static final double FITTING_TIME_SPAN = 0.1;
    /** Bezier曲線の次数 */
    public static final int DEGREE = 3;
    
    /** ファジィフラグメンテーションの閾値 */
    public static final TruthValue DIVIDE_THRESHOLD = TruthValue.create(0.45, 0.45);
    
    /** 最大摩擦力 */
    public static final double MAX_FRICTION_FORCE_VALUE = 0.65;//Geo 0.05 Bg 0.5
    /** 最大摩擦力の必然性値 */
    public static final double MAX_FRICTION_FORCE_N_VALUE = 1;//Geo 0.7 Bg 0.95
    
    /** ばね定数[N/m] */
    public static final double SPRING_CONSTANT = 60;//Geo 65 Bg 350;
    /** ペンの重さ */
    public static final double WEIGHT = 0.04 * 9.8;
}
