package fuzzybeziercurve;

import jp.sagalab.jftk.Point;

/**
 * 評価点からFBCを生成するためのクラスです。
 *
 * @author Koda
 */
public class EvaluationPointsFBCCreator {

    /**
     * このクラスのインスタンスを生成します。
     *
     * @param _fbcConvertVelocityCoeff FSC変換の速度係数
     * @param _fbcConvertAccelerationCoeff FSC変換の加速度係数
     * @return インスタンス
     */
    public static EvaluationPointsFBCCreator create(double _fbcConvertVelocityCoeff, double _fbcConvertAccelerationCoeff) {
        if (Double.isNaN(_fbcConvertVelocityCoeff)) {
            throw new IllegalArgumentException();
        }
        if (Double.isNaN(_fbcConvertAccelerationCoeff)) {
            throw new IllegalArgumentException();
        }
        return new EvaluationPointsFBCCreator(_fbcConvertVelocityCoeff, _fbcConvertAccelerationCoeff);
    }

    public BezierCurve createFBC(Point[] _points, int _degree, double _fittingTimeSpan) {
        // 3次ベジェ近似補間
        BezierCurve bezier = BezierCurveInterpolator.fit(_points, _degree, _fittingTimeSpan);
        // ファジネスを付加してFBC化
        BezierCurve fbc = FuzzyBezierCurveCreator.create(bezier, _fittingTimeSpan, m_fscConvertVelocityCoeff, m_fscConvertAccelerationCoeff);
        return fbc;

    }

    private EvaluationPointsFBCCreator(double _fscConvertVelocityCoeff, double _fscConvertAccelerationCoeff) {
        m_fscConvertVelocityCoeff = _fscConvertVelocityCoeff;
        m_fscConvertAccelerationCoeff = _fscConvertAccelerationCoeff;
    }

    /**
     * FBC変換の速度係数
     */
    private final double m_fscConvertVelocityCoeff;
    /**
     * FBC変換の加速度係数
     */
    private final double m_fscConvertAccelerationCoeff;
}
