package feedbackserver;

import jp.sagalab.jftk.Vector;

/**
 * 曖昧さを持つ力(ファジィ力)を表すクラスです。
 *
 * @author Koda
 */
public class FuzzyForce {
    
    /**
     * 力を返します
     * 
     *@return 正負を含んだ力
     * 
     */
    public double force() {
        return m_force;
    }
    /**
     * 力を返します
     * 
     *@return 力のベクトル
     * 
     */
    public Vector forceVector(){
        return m_forceVector;
    }
    /**
     * 力の大きさを返します
     *
     */
    public double forceAbs() {
        return Math.abs(m_force);
    }
    
    /**
    * X成分を返します。
    * @return X成分
    */
    public double x() {
        return m_forceVector.x();
    }
    
    /**
    * Y成分を返します。
    * @return Y成分
    */
    public double y() {
        return m_forceVector.y();
    }
    
    /**
    * Z成分を返します。
    * @return Z成分
    */
    public double z() {
        return m_forceVector.z();
    }
    
    /**
     * 位置のあいまいさ(ファジネス)を返します。
     *
     * @return 位置のあいまいさ(ファジネス)
     */
    public double fuzziness() {
        return m_fuzziness;
    }
    
    public double necessary(){
        return m_necessary;
    }
    
    public void setFuzziness(double _fuzziness) {
        m_fuzziness = _fuzziness;
    }
    
    public FuzzyForce(Vector _vector, double _necessary) {
        m_forceVector = _vector;
        m_force = _vector.length();
        m_fuzziness = _vector.fuzziness();
        m_necessary=_necessary;
    }
    public FuzzyForce(Vector _vector, double _fuzziness, double _necessary) {
        m_forceVector = _vector;
        m_force = _vector.length();
        m_fuzziness = _fuzziness;
        m_necessary=_necessary;
    }
    public FuzzyForce(double _force, double _fuzziness, double _necessary) {
        m_forceVector = null;
        m_force = _force;
        m_fuzziness = _fuzziness;
        m_necessary=_necessary;
    }
    
    public Vector necessaryForce(){
        /*
        System.out.println(" necess       "+m_forceVector.x()+
                " "+m_forceVector.y()+
                " "+m_forceVector.z()+
                " Fuzz "+ (1-m_necessary));
        */
        return m_forceVector.magnify(1 - m_necessary);
    }
    
    /** 力のベクトル */
    private final Vector m_forceVector;
    /** 力 */
    private final double m_force;
    /** 力のあいまいさ（ファジネス） */
    private double m_fuzziness;
    /** 必然性値 */
    private double m_necessary;
}
